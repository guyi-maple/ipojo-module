package top.guyi.iot.ipojo.module.h2.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@AllArgsConstructor
public enum ColumnTypeConverter {

    STRING(String.class, "varchar(%s)", 255, new ResultSetTypeConverter<String>(){
        @Override
        public String convert(String columnName, ResultSet rs) throws SQLException {
            return rs.getString(columnName);
        }
    }),
    INTEGER(Integer.class, "bigint(%s)", 16, new ResultSetTypeConverter<Integer>(){
        @Override
        public Integer convert(String columnName, ResultSet rs) throws SQLException {
            return rs.getInt(columnName);
        }
    }),
    INTEGER_BASE(int.class, "bigint(%s)", 16, new ResultSetTypeConverter<Integer>(){
        @Override
        public Integer convert(String columnName, ResultSet rs) throws SQLException {
            return rs.getInt(columnName);
        }
    }),

    LONG(Long.class, "bigint(%s)", 32, new ResultSetTypeConverter<Long>(){
        @Override
        public Long convert(String columnName, ResultSet rs) throws SQLException {
            return rs.getLong(columnName);
        }
    }),
    LONG_BASE(long.class, "bigint(%s)", 32, new ResultSetTypeConverter<Long>(){
        @Override
        public Long convert(String columnName, ResultSet rs) throws SQLException {
            return rs.getLong(columnName);
        }
    }),

    DOUBLE(Double.class, "decimal(%s,2)", 16, new ResultSetTypeConverter<Double>() {
        @Override
        public Double convert(String columnName, ResultSet rs) throws SQLException {
            return rs.getDouble(columnName);
        }
    }),
    DOUBLE_BASE(double.class, "decimal(%s,2)", 16, new ResultSetTypeConverter<Double>() {
        @Override
        public Double convert(String columnName, ResultSet rs) throws SQLException {
            return rs.getDouble(columnName);
        }
    }),

    FlOAT(Float.class, "decimal(%s,2)", 16, new ResultSetTypeConverter<Float>() {
        @Override
        public Float convert(String columnName, ResultSet rs) throws SQLException {
            return rs.getFloat(columnName);
        }
    }),
    FlOAT_BASE(float.class, "decimal(%s,2)", 16, new ResultSetTypeConverter<Float>() {
        @Override
        public Float convert(String columnName, ResultSet rs) throws SQLException {
            return rs.getFloat(columnName);
        }
    }),

    BOOLEAN(Boolean.class, "boolean", 1, new ResultSetTypeConverter<Boolean>() {
        @Override
        public Boolean convert(String columnName, ResultSet rs) throws SQLException {
            return rs.getBoolean(columnName);
        }
    }),
    BOOLEAN_BASE(boolean.class, "boolean", 1, new ResultSetTypeConverter<Boolean>() {
        @Override
        public Boolean convert(String columnName, ResultSet rs) throws SQLException {
            return rs.getBoolean(columnName);
        }
    });

    private Class<?> type;
    private String columnType;
    private int defaultLength;
    private ResultSetTypeConverter<? extends Serializable> resultSetTypeConverter;

    public String getColumnTypeValue(){
        return this.getColumnTypeValue(this.defaultLength);
    }
    public String getColumnTypeValue(int length){
        return String.format(this.columnType,length);
    }

    public static ColumnTypeConverter getByType(Class<?> type){
        for (ColumnTypeConverter value : values()) {
            if (value.type.equals(type)){
                return value;
            }
        }
        return null;
    }

}
