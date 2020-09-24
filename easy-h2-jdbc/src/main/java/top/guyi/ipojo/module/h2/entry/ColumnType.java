package top.guyi.ipojo.module.h2.entry;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ColumnType {

    private String type;
    private int defaultLength;

    public String getString(int length){
        return String.format(this.type,length);
    }

    public String getString(){
        return this.getString(this.defaultLength);
    }
}
