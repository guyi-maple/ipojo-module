package tech.guyi.ipojo.module.h2.entity;

import tech.guyi.ipojo.application.utils.StringUtils;

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
