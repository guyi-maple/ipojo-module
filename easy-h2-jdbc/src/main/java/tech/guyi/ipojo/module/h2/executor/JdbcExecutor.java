package tech.guyi.ipojo.module.h2.executor;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.h2.jdbcx.JdbcDataSource;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.Logger;
import tech.guyi.ipojo.application.ApplicationContext;
import tech.guyi.ipojo.application.annotation.Component;
import tech.guyi.ipojo.application.annotation.Resource;
import tech.guyi.ipojo.application.bean.interfaces.ApplicationStartSuccessEvent;
import tech.guyi.ipojo.application.bean.interfaces.InitializingBean;
import tech.guyi.ipojo.application.osgi.log.Log;
import tech.guyi.ipojo.module.h2.datasource.JdbcDataSourceProvider;
import tech.guyi.ipojo.module.stream.Mono;
import tech.guyi.ipojo.module.stream.producer.Producer;
import tech.guyi.ipojo.module.stream.publisher.Publisher;
import tech.guyi.ipojo.module.stream.subscriber.Subscriber;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Component
public class JdbcExecutor implements InitializingBean, ApplicationStartSuccessEvent {

    @Resource
    private JdbcDataSourceProvider provider;

    @Log
    private Logger logger;
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    private JdbcDataSource dataSource;
    private QueryRunner runner;

    private Mono<Boolean> readyMono;
    private Publisher<Boolean> _publisher;

    public boolean isReady(){
        return this.runner != null;
    }

    public void await(Subscriber<Boolean> subscriber){
        if (this.readyMono == null || this.isReady()){
            subscriber.subscription(true);
        }else{
            this.readyMono.subscription(subscriber);
        }
    }

    @Override
    public void afterPropertiesSet() {
        this.readyMono = Mono.create(new Producer<Boolean>() {
            @Override
            public void produce(Publisher<Boolean> publisher) {
                _publisher = publisher;
            }
        });
        this.readyMono.open();
    }

    private List<Object> filterNull(List<?> args){
        List<Object> result = new LinkedList<>();
        for (Object arg : args) {
            if (arg != null){
                result.add(arg);
            }
        }
        return result;
    }

    public synchronized <T> T execute(JdbcInvoker<T> invoker) throws SQLException {
        return invoker.invoke(this.runner);
    }

    public int update(final String sql, final List<?> args) throws SQLException {
        return this.execute(new JdbcInvoker<Integer>(){
            @Override
            public Integer invoke(QueryRunner runner) throws SQLException {
                List<Object> parameters = filterNull(args);
                logger.debug("execute sql [{}] Parameters {}",sql,parameters);
                return runner.update(sql,parameters.toArray());
            }
        });
    }

    public <T> T query(final String sql, final ResultSetHandler<T> handler, final List<?> args) throws SQLException {
        return this.execute(new JdbcInvoker<T>() {
            @Override
            public T invoke(QueryRunner runner) throws SQLException {
                List<Object> parameters = filterNull(args);
                logger.debug("execute sql [{}] Parameters {}",sql,parameters);
                return runner.query(sql,handler,parameters.toArray());
            }
        });
    }

    @Override
    public void onStartSuccess(ApplicationContext applicationContext, BundleContext bundleContext) throws Exception {
        Mono.create(new Producer<JdbcDataSource>() {
            @Override
            public void produce(Publisher<JdbcDataSource> publisher) {
                provider.provide(publisher);
            }
        }).subscription(new Subscriber<JdbcDataSource>() {
            @Override
            public void subscription(JdbcDataSource value) {
                dataSource = value;
                runner = new QueryRunner(dataSource);
                _publisher.publish(true);
                readyMono = null;
                _publisher = null;
            }
        }).open();
    }
}
