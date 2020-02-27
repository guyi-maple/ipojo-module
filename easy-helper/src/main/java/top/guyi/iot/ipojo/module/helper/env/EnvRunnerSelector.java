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

    public void putRunner(String key,String env,EnvRunner runner){
        this.runners.put(String.format("%s-%s",key,env),runner);
    }

    public Object selectAndRun(String key,String envKey,Object parameter){
        String env = applicationContext.getEnv().get(envKey);
        if (StringUtils.isEmpty(env)){
            env = "default";
        }
        String id = String.format("%s-%s",key,env);
        EnvRunner runner = this.runners.get(id);
        if (runner != null){
            return runner.run(parameter);
        }
        return null;
    }

}
