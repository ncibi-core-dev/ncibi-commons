package org.ncibi.commons.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;

import org.ncibi.commons.config.Configuration;
import org.ncibi.commons.config.InvalidConfigurationException;
import org.ncibi.commons.exception.NCIBIException;

/**
 * A JDBInstance is a cover class to hide details of interfacing to JDBC. It
 * takes care of configuring the driver and the connection from a configuration
 * file, setting up a pooling connection and handling the details executing
 * queries.
 * 
 * @author V. Glenn Tarcea
 * 
 */
public class JDBInstance
{
    /**
     * The JDBC connection.
     */
    private Connection myConnection;

    /**
     * The JDBC pool.
     */
    private PoolingDataSource dataSource;

    /**
     * The configuration object used to configure the connection.
     */
    private final DBConfig dbconfig;

    /**
     * The database login name.
     */
    private String userName = null;

    /**
     * The database login password.
     */
    private String password = null;

    /**
     * The driver class. This can be configured, but defaults to SQL Server (the
     * typical database server used at NCIBI)
     */
    private String driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // Default?

    /**
     * The connection string. This is constructed from the configuration file.
     * Below are some example strings.
     * 
     * Examples:
     * jdbc:sqlserver://ncibidb4.bicc.med.umich.edu\sqltest;databaseName
     * =pubmed;selectMethod=cursor
     * 
     * jdbc:sqlserver://ncibidb3.bicc.med.umich.edu:1433
     * ;selectMethod=cursor;databaseName=pubmed
     */
    private String connectionString = null;

    /**
     * Sets up the data source connection.
     * 
     * @return The pooling data source
     * @throws ClassNotFoundException
     */
    private PoolingDataSource setupDataSource() throws ClassNotFoundException
    {

        if (dataSource != null)
        {
            return dataSource;
        }

        try
        {
            Class.forName(driverClass);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            // log.error("Failed to get Driver class = " + driverClass);
            throw e;
        }

        // The following code was taking from example code located at:
        //
        // http://svn.apache.org/viewvc/jakarta/commons/proper/dbcp/trunk/doc/ManualPoolingDataSourceExample.java?view=co
        //

        //
        // First, we'll need a ObjectPool that serves as the
        // actual pool of connections.
        //
        // We'll use a GenericObjectPool instance, although
        // any ObjectPool implementation will suffice.
        //

        final ObjectPool connectionPool = new GenericObjectPool(null);

        //
        // Next, we'll create a ConnectionFactory that the
        // pool will use to create Connections.
        // We'll use the DriverManagerConnectionFactory,
        // using the connect string passed in the command line
        // arguments.
        //

        final ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectionString,
                userName, password);

        //
        // Now we'll create the PoolableConnectionFactory, which wraps
        // the "real" Connections created by the ConnectionFactory with
        // the classes that implement the pooling functionality.
        //

        final PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(
                connectionFactory, connectionPool, null, null, false, true);

        //
        // Finally, we create the PoolingDriver itself,
        // passing in the object pool we created.
        //

        connectionPool.setFactory(poolableConnectionFactory);

        dataSource = new PoolingDataSource(connectionPool);

        return dataSource;
    }

    /**
     * Initializes the object the first time. All the constructs need to do
     * this, so all this logic is gathered into one spot.
     * 
     * @throws NCIBIException
     *             When there are problems setting up the JDBC connection.
     */
    private void init() throws NCIBIException
    {
        userName = dbconfig.getDatabaseUsername();
        password = dbconfig.getDatabasePassword();
        connectionString = dbconfig.getDatabaseUrl();

        final String dclass = dbconfig.getSqlDriverClass();

        if (dclass != null)
        {
            driverClass = dclass;
        }

        try
        {
            final PoolingDataSource ds = setupDataSource();
            myConnection = ds.getConnection();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new NCIBIException("Unable to connect to database", e);
        }
    }

    /**
     * A constructor taking an already existing DBConfig object.
     * 
     * @param dbconfig
     *            The DBConfig object to use for configurion.
     * @throws NCIBIException
     *             When there are problems configuring JDBC.
     */
    public JDBInstance(final DBConfig dbconfig) throws NCIBIException
    {
        this.dbconfig = dbconfig;
        init();
    }

    /**
     * A constructor taking a configuration file name, and a dbname (used for
     * the internal DBConfig).
     * 
     * @param configFile
     *            Configuration file name
     * @param dbname
     *            Name of database for configuration.
     * @throws InvalidConfigurationException
     *             If the configuration is invalid.
     * @throws NCIBIException
     *             If there are problems setting up JDBC.
     */
    public JDBInstance(final String configFile, final String dbname) throws InvalidConfigurationException,
            NCIBIException
    {
        dbconfig = new DBConfig(configFile, dbname);
        init();
    }

    /**
     * A constructor taking an existing Configuration object that contains
     * database configuration information.
     * 
     * @param config
     *            The existing Configuration object.
     * @param dbname
     *            The name of the database for configuration.
     * @throws InvalidConfigurationException
     *             If the configuration is invalid.
     * @throws NCIBIException
     *             If there are problems setting up JDBC.
     */
    public JDBInstance(final Configuration config, final String dbname) throws InvalidConfigurationException,
            NCIBIException
    {
        dbconfig = new DBConfig(config, dbname);
        init();
    }

    /**
     * Executes a SQL update or query. Returns null if there are problems.
     * 
     * @param query
     *            The sql to execute.
     * @return A Statement or null (on error)
     */
    public Statement executeSql(final String query)
    {
        Statement s;

        try
        {
            s = myConnection.createStatement();
            s.executeUpdate(query);
            return s;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Execute an sql query. Returns null if there are problems.
     * 
     * @param query
     *            The sql to execute.
     * @return The resultset or null (on error)
     */
    public JDBResultSet executeQuery(final String query)
    {
        JDBResultSet myrs;
        ResultSet rs ;
        Statement s;

        try
        {
            s = myConnection.createStatement();
            rs = s.executeQuery(query);
            myrs = new JDBResultSet(s, rs);
            return myrs;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
