package top.guyi.test;

import top.guyi.iot.ipojo.module.h2.JdbcRepository;
import top.guyi.iot.ipojo.module.h2.where.condition.converter.AnyWhereConditionTypeConverter;
import top.guyi.iot.ipojo.module.h2.where.condition.converter.NullWhereConditionTypeConverter;
import top.guyi.iot.ipojo.module.h2.where.condition.converter.WhereConditionTypeConverter;
import top.guyi.iot.ipojo.module.h2.where.condition.type.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws SQLException {
//        JdbcRepository<User> repository = new JdbcRepository<User>() {
//            @Override
//            protected Class<User> entityClass() {
//                return User.class;
//            }
//        };
//
//        List<WhereConditionTypeConverter> converters = new LinkedList<>();
//        converters.add(new AnyWhereConditionTypeConverter());
//        converters.add(new NullWhereConditionTypeConverter());
//        repository.setConverters(converters);
//
//        Map<String, WhereConditionType> types = new HashMap<>();
//        WhereConditionType type = new EqWhereCondition();
//        types.put(type.forType(),type);
//        type = new GtAndEqWhereCondition();
//        types.put(type.forType(),type);
//        type = new GtWhereCondition();
//        types.put(type.forType(),type);
//        type = new LtAndEqWhereCondition();
//        types.put(type.forType(),type);
//        type = new LtWhereCondition();
//        types.put(type.forType(),type);
//        type = new NotWhereCondition();
//        types.put(type.forType(),type);
//        repository.setTypes(types);
//
//        repository
//                .where("name","张三").eq()
//                .or().name("age").value(null).not()
//                .delete();


    }

}
