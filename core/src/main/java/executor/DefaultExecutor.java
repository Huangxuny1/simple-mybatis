package executor;

import config.Configuration;
import config.MapperStatement;
import reflect.ReflectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultExecutor implements Executor {
    private Configuration config;

    public DefaultExecutor(Configuration config) {
        this.config = config;
    }

    @Override
    public <E> List<E> query(MapperStatement mapperStatement, Object params) {
        List<E> res = new ArrayList<>();
        try {
            Class.forName(config.getJdbcDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // class not fount
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(config.getJdbcUrl(), config.getJdbcUsername(), config.getJdbcPassword());
            stmt = conn.prepareStatement(mapperStatement.getSql());
            //todo
            paramterize(stmt, params);
            rs = stmt.executeQuery();
            handlerResultSet(rs, res, mapperStatement.getResultType());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    private <E> void handlerResultSet(ResultSet rs, List<E> res, String resultType) {
        Class<?> clazz = null;

        try {
            clazz = Class.forName(resultType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            while (rs.next()) {
                Object entity = clazz.newInstance();
                ReflectionUtils.setPropToBeanFromResultSet(entity, rs);
                res.add((E) entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void paramterize(PreparedStatement statement, Object params) {
        // todo
    }
}
