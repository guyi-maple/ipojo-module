package top.guyi.iot.ipojo.module.h2.datasource;

import org.h2.jdbc.JdbcConnection;

import java.sql.SQLException;
import java.util.Properties;

public class NoCloseConnection extends JdbcConnection {

    public NoCloseConnection(String url, Properties properties) throws SQLException {
        super(url, properties);
    }

    @Override
    public void close() throws SQLException {
        this.commit();
    }

    public void realClose() throws SQLException {
        super.close();
    }

}
