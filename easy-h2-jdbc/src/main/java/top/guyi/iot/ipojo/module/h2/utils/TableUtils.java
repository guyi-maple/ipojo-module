package top.guyi.iot.ipojo.module.h2.utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.osgi.service.log.Logger;
import top.guyi.iot.ipojo.application.annotation.Component;
import top.guyi.iot.ipojo.application.annotation.Resource;
import top.guyi.iot.ipojo.application.osgi.log.Log;
import top.guyi.iot.ipojo.module.h2.entry.FieldEntry;
import top.guyi.iot.ipojo.module.h2.executor.JdbcExecutor;
import top.guyi.iot.ipojo.module.h2.executor.JdbcInvoker;
import top.guyi.iot.ipojo.module.h2.executor.handler.SingleIntegerResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class TableUtils {

    @Log
    private Logger logger;
    @Resource
    private JdbcExecutor executor;

    private SingleIntegerResultHandler integerResultHandler = new SingleIntegerResultHandler();

    public void addField(String tableName,String fieldName,String type) {
        final String sql = String.format(
                "alter table %s add column `%s` %s default null",
                tableName,
                fieldName,
                type
        );
        try {
            this.executor.execute(new JdbcInvoker<Object>() {
                @Override
                public Object invoke(QueryRunner runner) throws SQLException {
                    return runner.update(sql);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
            this.logger.error("field select error {}",e.getMessage(),e);
        }
    }
    public boolean fieldExist(String tableName,String fieldName) {
        try {
            String sql = "select count(*) from information_schema.columns where table_name = ? and column_name = ?";
            Integer count = this.executor.query(
                    sql,
                    integerResultHandler,
                    Arrays.asList(tableName.toUpperCase(),fieldName.toUpperCase()));
            return count > 0;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean exist(String tableName){
        try {
            int count = executor.query(
                    "SELECT COUNT(1) sum FROM information_schema.tables where table_name = ?",
                    integerResultHandler,
                    Collections.singletonList((Object) tableName.toUpperCase())
            );
            return count >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
            this.logger.error("表存在查询失败",e);
            return false;
        }
    }

    public boolean delete(String tableName){
        try {
            executor.update(String.format("drop table %s",tableName),Collections.emptyList());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            this.logger.error("表删除查询失败",e);
            return false;
        }
    }

    public boolean create(String tableName, List<FieldEntry> columns){
        StringBuilder sql = new StringBuilder("CREATE TABLE ");
        sql.append(tableName.toUpperCase());
        sql.append(" (\n");
        for (FieldEntry field : columns) {
            sql.append("    ");
            sql.append(field.getColumnName());
            sql.append(" ");
            sql.append(field.getColumnType());
            sql.append(",\n");
        }
        String sql_run = sql.substring(0,sql.length()-2) + "\n)";
        try {
            this.logger.debug("创建表 {} \n {}",tableName.toUpperCase(),sql_run);
            executor.update(sql_run,Collections.emptyList());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            this.logger.error("表创建失败 {}",sql_run,e);
            return false;
        }
    }

}
