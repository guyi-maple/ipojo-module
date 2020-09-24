package tech.guyi.ipojo.module.h2.datasource;

import org.h2.jdbcx.JdbcDataSource;
import tech.guyi.ipojo.module.stream.publisher.Publisher;

public interface JdbcDataSourceProvider {

    void provide(Publisher<JdbcDataSource> publisher);

}
