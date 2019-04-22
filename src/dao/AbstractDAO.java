package dao;

import connection.ConnectionFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    public String[][] tableData;
    public String[] columns;

    private Class<T> type;

    @SuppressWarnings("unchecked")

    public AbstractDAO()
    {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Invoked when needed SELECT query
     * @param field that represents the column
     * @return query responsible for SELECT
     */
    private String createSelectQuery(String field)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE ");
        sb.append(field);
        sb.append(" =?");
        return sb.toString();
    }


    /**
     * Invoked when an INSERT query is needed
     * @return INSERT query
     */
    private String createInsertQuery()
    {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        sb.append(" (");
        for(Field field:type.getDeclaredFields()) {i++; if(i != type.getDeclaredFields().length)sb.append(field.getName() +", "); else sb.append(field.getName());}
        sb.append(")");
        sb.append(" VALUES (");
        i = 0;
        for(Field field:type.getDeclaredFields()) {i++; if(i!= type.getDeclaredFields().length)sb.append("?, "); else sb.append("?");}
        sb.append(");");
        return sb.toString();
    }

    /**
     * Invoked when a DELETE query is needed
     * @param field represents the column
     * @param id represents the id of the deletion
     * @return DELETE query
     */
    private String createDeleteQuery(String field, int id)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE ");
        sb.append(field);
        sb.append(" = " + id);
        return sb.toString();
    }

    /**
     * @return view all query
     */
    private String createViewAllQuery()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }

    /**
     * @param field column needed to be updated
     * @param id id of object
     * @return UPDATE query
     */
    private String createUpdateQuery(String field, int id)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        sb.append(field);
        sb.append(" =? ");
        sb.append(" WHERE id = " + id);
        return sb.toString();
    }

    /**
     * Generic method of finding an object by id
     * @param id specific id
     * @return the set of objects corresponding to that id
     */
    public T findById(int id)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try
        {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        }catch (SQLException e)
        {
            LOGGER.log(Level.WARNING,type.getName() + "DAO:findById"+ e.getMessage());
        }finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * @param resultSet result of the findById query
     * @return list of objects
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();

        try
        {
            while(resultSet.next())
            {
                T instance = type.newInstance();
                for(Field field:type.getDeclaredFields())
                {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(),type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance,value);
                }
                list.add(instance);
            }
        }catch(IllegalAccessException | SQLException | InstantiationException | IntrospectionException | InvocationTargetException e)
        {
            LOGGER.log(Level.WARNING,type.getName() + "DAO:findById "+ e.getMessage());
        }
        return list;
    }

    /**
     * @param t generic parameter
     * @return the success or failure of the INSERT query
     */
    public int insert(T t)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createInsertQuery();
        try
        {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int i = 1;
            for(Field field:type.getDeclaredFields())
            {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                Method method = propertyDescriptor.getReadMethod();
                Object s = method.invoke(t);
                if (s.getClass() == Integer.class) {
                    statement.setInt(i++, Integer.parseInt(s.toString()));
                }
                else if (s.getClass() == String.class) {
                    statement.setString(i++, s.toString());
                }

            }
            System.out.println(statement.toString());
            statement.executeUpdate();
        }catch (SQLException | IllegalAccessException | InvocationTargetException | IntrospectionException e)
        {
            LOGGER.log(Level.WARNING,type.getName() + "DAO:insert "+ e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return 0;
    }

    /**
     * @param id the id of the row needed to be deleted
     * @return the success or failure of the DELETE query
     */
    public int delete(int id)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery("id",id);
        try
        {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
        }catch (SQLException  e)
        {
            LOGGER.log(Level.WARNING,type.getName() + "DAO:delete "+ e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return 0;
    }

    /**
     * @return the success or failure of the SELECT query
     */
    public int viewAll()
    {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createViewAllQuery();
        try
        {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            ResultSet rs =  statement.executeQuery();

            columns = new String[type.getDeclaredFields().length];
            tableData = new String[100][45];

            int j = 0;
            for (Field field : type.getDeclaredFields()) {
                columns[j] = field.getName();
                j++;
            }
            int i = 0;
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while(rs.next()) {
                for(int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    Object object = rs.getObject(columnIndex);
                   // System.out.printf("%s, ", object == null ? "NULL" : object.toString());
                    tableData[i][columnIndex-1] = object == null ? "NULL" : object.toString();
                }
                i++;
            }
            tableData[i+1]=null;
        }catch (SQLException  e)
        {
            LOGGER.log(Level.WARNING,type.getName() + "DAO:viewAll "+ e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return 0;
    }

    /**
     * @param field column to be changed
     * @param obj the changed value
     * @param id the id corresponding to the column
     * @return the success or failure of the UPDATE query
     */
    public int update(String field,Object obj, int id)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createUpdateQuery(field,id);
        try
        {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            if(obj.getClass() == String.class)
            {
                statement.setString(1,obj.toString());
            }else if(obj.getClass() == Integer.class)
            {
                statement.setInt(1,Integer.parseInt(obj.toString()));
            }
            System.out.println(statement.toString());
            statement.executeUpdate();
        }catch (SQLException e)
        {
            LOGGER.log(Level.WARNING,type.getName() + "DAO:insert "+ e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return 0;
    }
}
