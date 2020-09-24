package top.guyi.ipojo.module.h2.entry.page;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {

    private int page;
    private int pageSize;
    private int pageCount;
    private long total;
    private List<T> content;

    public Page(int page, int pageSize, long total, List<T> content) {
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
        this.content = content;

        if(this.total % this.pageSize == 0){
            this.pageCount = (int) this.total / this.pageSize;
        }else{
            this.pageCount = (int)((this.total - this.total % this.pageSize ) / this.pageSize + 1);
        }
    }

}
