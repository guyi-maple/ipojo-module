package top.guyi.iot.ipojo.module.helper.appender;

import top.guyi.iot.ipojo.application.annotation.Component;
import top.guyi.iot.ipojo.application.annotation.Resource;
import top.guyi.iot.ipojo.application.bean.interfaces.InitializingBean;

import java.util.*;

/**
 * @author guyi
 * 执行附加器管理者
 */
@Component
public class ExecutiveAppenderManager implements InitializingBean {

    @Resource
    private List<ExecutiveAppender> appenderList;

    private Map<String,List<ExecutiveAppender>> appeasers;

    private final Comparator<ExecutiveAppender> comparator = new Comparator<ExecutiveAppender>() {
        @Override
        public int compare(ExecutiveAppender o1, ExecutiveAppender o2) {
            return Integer.compare(o1.order(),o2.order());
        }
    };

    @Override
    public void afterPropertiesSet() {
        this.appeasers = new HashMap<>();
        for (ExecutiveAppender appender : this.appenderList) {
            List<ExecutiveAppender> list = this.appeasers.get(appender.key());
            if (list == null){
                list = new LinkedList<>();
            }
            list.add(appender);
            Collections.sort(list,comparator);
            this.appeasers.put(appender.key(),list);
        }
    }

    public List<ExecutiveAppender> getExecutiveAppender(String key){
        List<ExecutiveAppender> list = this.appeasers.get(key);
        return list == null ? Collections.<ExecutiveAppender>emptyList() : list;
    }

    /**
     * 调用执行器
     * @param key 标识
     * @param parameter 参数
     */
    public void execute(String key,ExecutiveAppenderParameter parameter){
        List<ExecutiveAppender> list = this.getExecutiveAppender(key);
        for (ExecutiveAppender appender : list) {
            appender.execute(parameter);
        }
    }

}
