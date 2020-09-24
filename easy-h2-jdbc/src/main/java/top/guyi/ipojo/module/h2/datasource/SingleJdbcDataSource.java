package top.guyi.ipojo.module.h2.datasource;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class SingleJdbcDataSource extends JdbcDataSource {

    private NoCloseConnection connection;

    public void setDBFilePath(String path){
        this.setURL(String.format("jdbc:h2:%s;FILE_LOCK=NO",path));
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()){
            Properties info = new Properties();
            info.setProperty("user", this.getUser());
            info.put("password", this.getPassword());
            connection = new NoCloseConnection(this.getURL(),info);
            connection.setAutoCommit(false);
        }
        return this.connection;
    }

    public void closeConnection() throws SQLException {
        if (this.connection != null){
            this.connection.realClose();
        }
    }
}
