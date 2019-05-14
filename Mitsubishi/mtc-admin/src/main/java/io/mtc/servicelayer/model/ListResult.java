package io.mtc.servicelayer.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jun_ma on 2016/4/14.
 */
public class ListResult<T> implements Serializable {

    private static final long serialVersionUID = 7755240062951738731L;

    private List<T> value;

    @JSONField(name = "odata.count")
    private int total;

    public List<T> getValue() {
        return value;
    }

    public void setValue(List<T> value) {
        this.value = value;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
