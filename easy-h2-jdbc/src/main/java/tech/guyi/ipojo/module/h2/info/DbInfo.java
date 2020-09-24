package tech.guyi.ipojo.module.h2.info;

import lombok.Data;

import java.util.List;

@Data
public class DbInfo {

    private String version;
    private List<TableInfo> tables;
    private ConnectionInfo connection;

}
