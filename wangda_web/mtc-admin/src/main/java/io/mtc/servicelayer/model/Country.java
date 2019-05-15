package io.mtc.servicelayer.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by majun on 2018/9/3.
 */
public class Country implements Serializable {

    private static final long serialVersionUID = 5052784779587712341L;

    private String Code;

    private String Name;

    private String UICCountryCode;

    private Integer AddressFormat;

    @JSONField(name = "Code")
    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    @JSONField(name = "Name")
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @JSONField(name = "UICCountryCode")
    public String getUICCountryCode() {
        return UICCountryCode;
    }

    public void setUICCountryCode(String UICCountryCode) {
        this.UICCountryCode = UICCountryCode;
    }

    @JSONField(name = "AddressFormat")
    public Integer getAddressFormat() {
        return AddressFormat;
    }

    public void setAddressFormat(Integer addressFormat) {
        AddressFormat = addressFormat;
    }

    @Override
    public String toString() {
        return "Country{" +
                "AddressFormat=" + AddressFormat +
                ", Code='" + Code + '\'' +
                ", Name='" + Name + '\'' +
                ", UICCountryCode='" + UICCountryCode + '\'' +
                '}';
    }
}
