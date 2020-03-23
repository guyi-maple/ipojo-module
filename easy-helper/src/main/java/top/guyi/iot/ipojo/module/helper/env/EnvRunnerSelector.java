package top.guyi.iot.ipojo.module.helper.env;

import top.guyi.iot.ipojo.application.ApplicationContext;
import top.guyi.iot.ipojo.application.annotation.Component;
import top.guyi.iot.ipojo.application.annotation.Resource;
import top.guyi.iot.ipojo.application.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Component
public class EnvRunnerSelector {

    @Resource
    private ApplicationContext applicationContext;

    private Map<String,EnvRunner> runners = new HashMap<>();

    public void putRunner(String key,String value,EnvRunner runner){
        this.runners.put(String.format("%s-%s",key,value),runner);
    }

    public <T> T selectAndRun(String key,Object parameter){
        String value = applicationContext.getEnv().get(key);
        if (StringUtils.isEmpty(value)){
            value = "default";
        }
        String id = String.format("%s-%s",key,value);
        EnvRunner runner = this.runners.get(id);
        if (runner != null){
            return (T) runner.run(parameter);
        }
        return null;
    }

}
