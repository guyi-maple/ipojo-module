package top.guyi.iot.ipojo.module.h2.entity;

import top.guyi.iot.ipojo.application.utils.StringUtils;

import java.util.UUID;

public class IdGeneratorFactory {

    public static IdGenerator<String> stringIdGenerator = new IdGenerator<String>() {
        @Override
        public String get() {
            return UUID.randomUUID().toString().replaceAll("-","");
        }
    };

    public static IdGenerator<Integer> integerIdGenerator = new IdGenerator<Integer>() {
        @Override
        public Integer get() {
            return Integer.parseInt(StringUtils.randomIntString(16));
        }
    };

}
