package org.ncibi.commons.db;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

public class JDBCExecuter
{
    private String url;
    private String username;
    private String passwd;
    private String driver;

    public JDBCExecuter(final DBConfig dbconfig)
    {
        this.url = dbconfig.getDatabaseUrl();
        this.driver = dbconfig.getSqlDriverClass();
        this.username = dbconfig.getDatabaseUsername();
        this.passwd = dbconfig.getDatabasePassword();
    }

    public JDBCExecuter(final String configFile, final String dbname)
    {
        this(new DBConfig(configFile, dbname));
    }

    public JDBCExecuter(String url, String driver, String username, String passwd)
    {
        this.url = url;
        this.username = username;
        this.passwd = passwd;
        this.driver = driver;
    }
    
    public JDBCExecuter(ResourceBundle rb)
    {
        this.url = rb.getString("url");
        this.driver = rb.getString("driver");
        this.passwd = rb.getString("password");
        this.username = rb.getString("username");
    }

    private static interface ResultBuilder<T>
    {
        public T processResultSet(ResultSet rs) throws SQLException;
    }

    private <T> T executeQuery(String query, ResultBuilder<T> resultBuilder)
    {
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;
        T value = null;

        try
        {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, passwd);
            statement = connection.createStatement();
            result = statement.executeQuery(query);

            value = resultBuilder.processResultSet(result);

            result.close();
            result = null;
            statement.close();
            statement = null;

        }
        catch (ClassNotFoundException e)
        {
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        finally
        {
            if (statement != null)
            {
                try
                {
                    statement.close();
                }
                catch (SQLException e)
                {
                    System.out.println(e);
                }
                statement = null;
            }
            if (connection != null)
            {
                try
                {
                    connection.close();
                }
                catch (SQLException e)
                {
                    System.out.println(e);
                }
                connection = null;
            }
        }
        return value;
    }

    public String selectSingleValue(String query) throws SQLException
    {
        return executeQuery(query, new ResultBuilder<String>()
        {
            public String processResultSet(ResultSet rs) throws SQLException
            {
                String value = "";
                while (rs.next())
                {
                    value = rs.getString(1);
                }
                return value;
            }
        });
    }

    public Map<String, String> hashResult(String query) throws SQLException
    {
        return executeQuery(query, new ResultBuilder<Map<String, String>>()
        {
            public Map<String, String> processResultSet(ResultSet rs) throws SQLException
            {
                String key = "";
                String val = "";

                Map<String, String> hashedList = new HashMap<String, String>();
                while (rs.next())
                {
                    key = String.valueOf(rs.getInt(1));
                    val = rs.getString(2);
                    hashedList.put(key, val);
                }
                return hashedList;
            }
        });
    }

    public Map<String, Map<String, String>> dbHashing(String query) throws SQLException
    {
        return executeQuery(query, new ResultBuilder<Map<String, Map<String, String>>>()
        {
            public Map<String, Map<String, String>> processResultSet(ResultSet rs) throws SQLException
            {
                String key = "";
                String val = "";

                Map<String, Map<String, String>> hashedList = new HashMap<String, Map<String, String>>();
                Map<String, String> value = new HashMap<String, String>();
                while (rs.next())
                {
                    key = rs.getString(1);
                    val = rs.getString(2);

                    if (hashedList.containsKey(key))
                    {
                        value = hashedList.get(key);
                        value.put(val, val);
                    }
                    else
                    {
                        value = new HashMap<String, String>();
                        value.put(val, val);
                    }
                    hashedList.put(key, value);
                }
                return hashedList;
            }
        });
    }

    public Map<String, Vector<String>> vectorMap(String query) throws SQLException
    {
        return executeQuery(query, new ResultBuilder<Map<String, Vector<String>>>()
        {
            public Map<String, Vector<String>> processResultSet(ResultSet rs) throws SQLException
            {
                String key = "";
                String val = "";

                Map<String, Vector<String>> hashedList = new HashMap<String, Vector<String>>();
                Vector<String> value = new Vector<String>();
                while (rs.next())
                {
                    key = rs.getString(1);
                    val = rs.getString(2);

                    if (hashedList.containsKey(key))
                    {
                        value = hashedList.get(key);
                        value.add(val);
                    }
                    else
                    {
                        value = new Vector<String>();
                        value.add(val);
                    }
                    hashedList.put(key, value);
                }
                return hashedList;
            }
        });
    }

    public Map<String, Map<String, String>> dbDoubleHashing(String query) throws SQLException
    {
        return executeQuery(query, new ResultBuilder<Map<String, Map<String, String>>>()
        {
            public Map<String, Map<String, String>> processResultSet(ResultSet rs) throws SQLException
            {
                String key = "";
                String val1 = "";
                String val2 = "";
                Map<String, Map<String, String>> doubleHash = new HashMap<String, Map<String, String>>();
                Map<String, String> hashedList1 = new HashMap<String, String>();
                Map<String, String> hashedList2 = new HashMap<String, String>();
                while (rs.next())
                {
                    key = rs.getString(1);
                    val1 = rs.getString(2);
                    val2 = rs.getString(3);

                    hashedList1.put(key, val1);
                    hashedList2.put(key, val2);
                }
                doubleHash.put("1", hashedList1);
                doubleHash.put("2", hashedList2);
                return doubleHash;
            }
        });
    }

    public Vector<String> getData(String query) throws SQLException
    {
        return executeQuery(query, new ResultBuilder<Vector<String>>()
        {
            public Vector<String> processResultSet(ResultSet rs) throws SQLException
            {
                String val = "";
                Vector<String> value = new Vector<String>();
                while (rs.next())
                {
                    val = rs.getString(1);
                    value.add(val);
                }
                return value;
            }
        });
    }

    public double[] getDoubleData(String query) throws SQLException
    {

        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;
        double[] data = new double[1];

        try
        {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, passwd);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            result = statement.executeQuery(query);

            int rowcount = 0;
            int i = 0;
            if (result.last())
            {
                rowcount = result.getRow();
                result.beforeFirst();
            }

            data = new double[rowcount];

            while (result.next())
            {
                data[i] = result.getDouble(1);
                i++;
            }

            result.close();
            result = null;
            statement.close();
            statement = null;

        }
        catch (ClassNotFoundException e)
        {
            System.out.println(e);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        finally
        {
            if (statement != null)
            {
                try
                {
                    statement.close();
                }
                catch (SQLException e)
                {
                    System.out.println(e);
                }
                statement = null;
            }
            if (connection != null)
            {
                try
                {
                    connection.close();
                }
                catch (SQLException e)
                {
                    System.out.println(e);
                }
                connection = null;
            }
        }

        return data;
    }

    public HashMap<String, String> getValues(String query) throws SQLException
    {

        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;
        String val1 = "";
        String val2 = "";
        HashMap<String, String> value = new HashMap<String, String>();

        try
        {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, passwd);
            statement = connection.createStatement();
            result = statement.executeQuery(query);

            while (result.next())
            {

                val1 = result.getString(1);
                val2 = result.getString(2);
                value.put("1", val1);
                value.put("2", val2);

            }

            result.close();
            result = null;
            statement.close();
            statement = null;

        }
        catch (ClassNotFoundException e)
        {
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        finally
        {
            if (statement != null)
            {
                try
                {
                    statement.close();
                }
                catch (SQLException e)
                {
                    System.out.println(e);
                }
                statement = null;
            }
            if (connection != null)
            {
                try
                {
                    connection.close();
                }
                catch (SQLException e)
                {
                    System.out.println(e);
                }
                connection = null;
            }
        }

        return value;
    }

    public boolean execute(String command) throws SQLException
    {
        boolean result = false;
        result = executeQuery(command, new ResultBuilder<Boolean>()
        {
            public Boolean processResultSet(ResultSet rs) throws SQLException
            {
                return true;
            }
        });
        return result;
    }

    public boolean batchExecQuery(String command, Vector<double[]> values) throws SQLException
    {

        boolean value = false;
        Connection connection = null;
        PreparedStatement stmt = null;
        try
        {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, passwd);
            connection.setAutoCommit(false);
            stmt = connection.prepareStatement(command);

            for (int i = 0; i < values.size(); i++)
            {
                double[] val = (double[]) values.get(i);
                for (int j = 0; j < val.length; j++)
                {
                    stmt.setDouble((j + 1), val[j]);
                }

                stmt.addBatch();

            }

            stmt.executeBatch();
            value = true;

            connection.commit();
            stmt.close();
            stmt = null;
            connection.close();
            connection = null;
        }
        catch (BatchUpdateException b)
        {

            System.err.println("SQLException: " + b.getMessage());
            System.err.println("SQLState:  " + b.getSQLState());
            System.err.println("Message:  " + b.getMessage());
            System.err.println("Vendor:  " + b.getErrorCode());
            System.err.println("Update counts:  ");
            value = false;
            connection.rollback();
        }
        catch (ClassNotFoundException e)
        {
        }
        catch (Exception e)
        {
            System.out.println(e);
            value = false;
            connection.rollback();
        }

        finally
        {

            if (stmt != null)
            {
                try
                {
                    stmt.close();
                }
                catch (SQLException e)
                {
                }
                stmt = null;
            }
            if (connection != null)
            {
                try
                {
                    connection.close();
                }
                catch (SQLException e)
                {
                }
                connection = null;
            }
        }

        return value;
    }

    public List<List<String>> select(String query) throws SQLException
    {
        return executeQuery(query, new ResultBuilder<List<List<String>>>()
        {
            public List<List<String>> processResultSet(ResultSet rs) throws SQLException
            {
                List<List<String>> resultList = new ArrayList<List<String>>();
                List<String> columns = new ArrayList<String>();
                List<String> recordList = null;
                ResultSetMetaData rmd = (ResultSetMetaData) rs.getMetaData();
                String value = "";
                int columnCount = rmd.getColumnCount();

                for (int i = 1; i <= columnCount; i++)
                {
                    columns.add(rmd.getColumnName(i));
                }

                while (rs.next())
                {
                    recordList = new ArrayList<String>();
                    for (int i = 1; i <= columnCount; i++)
                    {
                        value = rs.getString(i);
                        if (value == null)
                        {
                            value = "";
                        }
                        else
                        {
                            value = value.trim();
                        }
                        recordList.add(value);
                    }
                    resultList.add(recordList);
                }
                return resultList;
            }
        });
    }

    public List<String> selectSingleList(String query) throws SQLException
    {
        return executeQuery(query, new ResultBuilder<List<String>>()
        {

            public List<String> processResultSet(ResultSet rs) throws SQLException
            {
                List<String> resultList = new ArrayList<String>();
                String value = "";
                while (rs.next())
                {
                    value = rs.getString(1);
                    resultList.add(value);
                }
                return resultList;
            }
        });
    }

    public Map<String, String> selectSingleHash(String query) throws SQLException
    {
        return executeQuery(query, new ResultBuilder<Map<String, String>>()
        {
            public Map<String, String> processResultSet(ResultSet rs) throws SQLException
            {
                Map<String, String> resultList = new HashMap<String, String>();
                String value = "";
                while (rs.next())
                {
                    value = rs.getString(1);
                    resultList.put(value, value);
                }
                return resultList;
            }
        });
    }

    public boolean batchExecQuery(Vector<String> query) throws SQLException
    {

        boolean value = false;
        Connection connection = null;
        Statement statement = null;
        try
        {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, passwd);
            statement = connection.createStatement();

            connection.setAutoCommit(false);

            for (int i = 0; i < query.size(); i++)
            {
                statement.addBatch((String) query.get(i));
            }

            statement.executeBatch();
            value = true;

            connection.commit();
            statement.close();
            statement = null;
            connection.close();
            connection = null;
        }
        catch (BatchUpdateException b)
        {

            System.err.println("SQLException: " + b.getMessage());
            System.err.println("SQLState:  " + b.getSQLState());
            System.err.println("Message:  " + b.getMessage());
            System.err.println("Vendor:  " + b.getErrorCode());
            System.err.println("Update counts:  ");
            value = false;
            connection.rollback();
        }
        catch (ClassNotFoundException e)
        {
            System.out.println(e);
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println(query);
            value = false;
            connection.rollback();
        }

        finally
        {

            if (statement != null)
            {
                try
                {
                    statement.close();
                }
                catch (SQLException e)
                {
                }
                statement = null;
            }
            if (connection != null)
            {
                try
                {
                    connection.close();
                }
                catch (SQLException e)
                {
                }
                connection = null;
            }
        }

        return value;
    }
}
