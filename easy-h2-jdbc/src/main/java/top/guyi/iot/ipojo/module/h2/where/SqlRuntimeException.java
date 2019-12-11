package top.guyi.iot.ipojo.module.h2.where;

import java.sql.SQLException;

public class SqlRuntimeException extends RuntimeException {

    public SqlRuntimeException(String message){
        super(message);
    }

    public SqlRuntimeException(SQLException e){
        this(e.getMessage());
    }

}
