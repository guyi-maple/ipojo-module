package top.guyi.ipojo.module.h2.datasource;

import org.h2.jdbcx.JdbcDataSource;
import top.guyi.ipojo.module.stream.publisher.Publisher;

public interface JdbcDataSourceProvider {

    void provide(Publisher<JdbcDataSource> publisher);

}
