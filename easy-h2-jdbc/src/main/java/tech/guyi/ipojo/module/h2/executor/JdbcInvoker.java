package tech.guyi.ipojo.module.h2.executor;

import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

public interface JdbcInvoker<T> {

    T invoke(QueryRunner runner) throws SQLException;

}
