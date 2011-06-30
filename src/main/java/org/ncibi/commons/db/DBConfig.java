package org.ncibi.commons.db;

import org.ncibi.commons.config.Configuration;
import org.ncibi.commons.config.InvalidConfigurationException;

/**
 * Holds configuration properties for databases. This class provides a mechanism
 * to provide configuraton parameters for multiple databases, each with separate
 * hosts, ports, drivers, etc... The databases may be configured with a dbname
 * to separate out database specific functionality.
 * <p>
 * NOTE: This class is still dependent on SQL server. The getDatabaseUrl()
 * function would need to be changed to allow this to work with another
 * database.
 * <p>
 * The properties are:
 * <ul>
 * <li>db.host - The database server host. (deprecated)</li>
 * <li>db.name - The database name. (deprecated)</li>
 * <li>db.port - The port to use. (deprecated)</li>
 * <li>db.url - The url for the database connection.</li>
 * <li>db.instance - The database instance (not always configured).</li>
 * <li>db.username - The username to use to login to the database. </li>
 * <li>db.password - The password for the login connection. </li>
 * <li>db.driverclass - The driverclass to configure (for JDBC). </li>
 * </ul>
 * <p>
 * The properties are referred to in 3 different name configurations:
 * <p>
 * If dbname is specified then appends the name. For example mimi.db.host.
 * <p>
 * If no dbname is specified then uses property with out name, for example
 * db.host.
 * <p>
 * A default can be configured for parameters that are shared across multiple
 * databases. In this case add default, eg default.db.host.
 * <p>
 * The order of precedence is check the dbname.property, then the property, then
 * finally check the default.property. The default.property is only checked if a
 * dbname was configured.
 * 
 * @author gtarcea
 * 
 */
public class DBConfig
{
    /**
     * The names of the database properties are configured here.
     */
    private static final String DATABASE_HOST_PROPERTY = "db.host";
    private static final String DATABASE_NAME_PROPERTY = "db.name";
    private static final String DATABASE_PORT_PROPERTY = "db.port";
    private static final String DATABASE_URL_PROPERTY = "db.url";
    private static final String DATABASE_INSTANCE_PROPERTY = "db.instance";
    private static final String DATABASE_USERNAME_PROPERTY = "db.username";
    private static final String DATABASE_PASSWORD_PROPERTY = "db.password";
    private static final String DATABASE_DRIVER_CLASS = "db.driverclass";

    /**
     * The database name to append to properties.
     */
    private final String dbname;

    /**
     * The configuration object containing the property settings.
     */
    private final Configuration dbconfiguration;

    /**
     * Constructor that takes a property file name and dbname.
     * 
     * @param propertiesFile
     *            The property file holding the database properties.
     * @param dbname
     *            The name of the database.
     * @throws InvalidConfigurationException
     *             If it can't read the configuration file.
     */
    public DBConfig(final String propertiesFile, final String dbname) throws InvalidConfigurationException
    {
        dbconfiguration = new Configuration(propertiesFile);
        this.dbname = dbname;
    }

    /**
     * Constructor that takes an existing configuration object and a database
     * name.
     * 
     * @param config
     *            The configuration object.
     * @param dbname
     *            The database name.
     */
    public DBConfig(final Configuration config, final String dbname)
    {
        dbconfiguration = config;
        this.dbname = dbname;
    }

    /**
     * Private method that does the actual work of finding a db property value.
     * It first checks using the dbname, and only if dbname is set and no value
     * was found does it search using default appended to the name. The
     * unadorned property is only ever searched if dbname is null.
     * 
     * @param property
     *            The property to look up.
     * @return The value found or null if no value was found for the property.
     */
    private String getDBProperty(final String property)
    {
        String value = null;

        if (dbname != null)
        {
            value = dbconfiguration.getProperty(dbname + "." + property);
            if (value == null)
            {
                value = dbconfiguration.getProperty("default." + property);
            }
        }
        else
        {
            value = dbconfiguration.getProperty(property);
        }

        return value;
    }

    /**
     * The public getProperty(), calls private getDBProperty().
     * 
     * @param property
     *            The property name to look up.
     * @return The value found or null if not value was found for the property.
     */
    public String getProperty(final String property)
    {
        return getDBProperty(property);
    }

    /**
     * Constructs the database connection url. TODO: sqlserver is hard coded in
     * here. fix this.
     * 
     * @return The SQL Server url connection string.
     */
    public String getDatabaseUrl()
    {
        final String url = getProperty(DATABASE_URL_PROPERTY);
        
        if (url != null)
        {
            return url;
        }
        
        /*
         *  Support for the old method of constructing the URL. This should be
         *  removed at some point in the future.
         */
        
        final String host = getProperty(DATABASE_HOST_PROPERTY);
        final String name = getProperty(DATABASE_NAME_PROPERTY);
        final String port = getProperty(DATABASE_PORT_PROPERTY);
        final String instance = getProperty(DATABASE_INSTANCE_PROPERTY);
        String connectStr = "";

        if (instance != null)
        {
            connectStr = "jdbc:sqlserver://" + host + "\\" + instance + ";databaseName=" + name
                    + ";selectMethod=cursor;";
        }
        else
        {
            connectStr = "jdbc:sqlserver://" + host + ":" + port + ";databaseName=" + name
                    + ";selectMethod=cursor;";
        }

        return connectStr;
    }

    /**
     * Gets the user login account for the database.
     * 
     * @return The user login.
     */
    public String getDatabaseUsername()
    {
        return getProperty(DATABASE_USERNAME_PROPERTY);
    }

    /**
     * Gets the user login account password for the database.
     * 
     * @return The password.
     */
    public String getDatabasePassword()
    {
        return getProperty(DATABASE_PASSWORD_PROPERTY);
    }

    /**
     * Gets the driver class if specified.
     * 
     * @return The driver class
     */
    public String getSqlDriverClass()
    {
        return getProperty(DATABASE_DRIVER_CLASS);
    }
}
