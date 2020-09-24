package top.guyi.ipojo.module.h2.info;

import lombok.Data;

import java.util.List;

@Data
public class TableInfo {

    private String tableName;
    private List<FieldInfo> fields;

}
