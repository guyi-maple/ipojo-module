package top.guyi.iot.ipojo.module.h2.type;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetTypeConverter<R> {

    R convert(String columnName, ResultSet rs) throws SQLException;

}
