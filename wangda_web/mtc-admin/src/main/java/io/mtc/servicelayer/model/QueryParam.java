package io.mtc.servicelayer.model;

/**
 * Created by majun on 2018/9/3.
 */
public class QueryParam {

    private String select;

    private String filter;

    private String orderby;

    private Integer top;

    private Integer skip;

    public String getFilter() {
        return filter;
    }

    public String getOrderby() {
        return orderby;
    }

    public String getSelect() {
        return select;
    }

    public Integer getSkip() {
        return skip;
    }

    public Integer getTop() {
        return top;
    }

    public QueryParam(Builder builder){
        this.select = builder.select;
        this.filter = builder.filter;
        this.orderby = builder.orderby;
        this.top = builder.top;
        this.skip = builder.skip;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public static class Builder{
        private String select;

        private String filter;

        private String orderby;

        private Integer top;

        private Integer skip;

        public QueryParam build() {
            return new QueryParam(this);
        }


        public Builder select(String select){
            this.select = select;
            return this;
        }

        public Builder filter(String filter){
            this.filter = filter;
            return this;
        }

        public Builder orderby(String orderby){
            this.orderby = orderby;
            return this;
        }

        public Builder top(Integer top){
            this.top = top;
            return this;
        }

        public Builder skip(Integer skip){
            this.skip = skip;
            return this;
        }

    }

}
