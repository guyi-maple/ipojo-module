package top.guyi.iot.ipojo.module.helper.appender;

import java.util.HashMap;
import java.util.Map;

public class ExecutiveAppenderParameter {

    private final Map<String,Object> parameters;

    public ExecutiveAppenderParameter() {
        this(new HashMap<String, Object>());
    }
    public ExecutiveAppenderParameter(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public void set(String name,Object parameter){
        this.parameters.put(name,parameter);
    }

    public <T> T get(String name){
        return (T) this.parameters.get(name);
    }


}
