package top.guyi.iot.ipojo.module.helper.appender;

/**
 * @author guyi
 * 附加执行器
 */
public interface ExecutiveAppender {

    /**
     * 执行排序
     * @return 排序值
     */
    int order();

    /**
     * 执行标识
     * @return 执行标识
     */
    String key();

    /**
     * 执行
     * @param parameter 参数
     */
     void execute(ExecutiveAppenderParameter parameter);

}
