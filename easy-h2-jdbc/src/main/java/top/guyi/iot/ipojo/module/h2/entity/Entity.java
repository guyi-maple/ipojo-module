package top.guyi.iot.ipojo.module.h2.entity;

import java.io.Serializable;

public interface Entity<ID extends Serializable> {

    ID getId();

    void setId(ID id);

    IdGenerator<ID> idGenerator();

}
