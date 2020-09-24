package top.guyi.ipojo.module.h2.entry.page;

import lombok.Data;

@Data
public class PageRequest {

    private int page;
    private int pageSize = 15;

    public PageRequest(int page) {
        this.page = page;
    }

    public PageRequest(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public long getStart(){
        return this.page * this.pageSize;
    }

    public long getEnd(){
        return (this.page + 1) * this.pageSize - 1;
    }

}
