package tech.guyi.ipojo.module.h2.executor.handler;

import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author guyi
 * @since 1.0.0.0
 */
public class SingleIntegerResultHandler implements ResultSetHandler<Integer> {

    @Override
    public Integer handle(ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            return resultSet.getInt(1);
        }
        return null;
    }

}
