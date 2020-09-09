package com.sgcc.pda.framelibrary.protocol698.apdu.data;

import com.sgcc.pda.framelibrary.utils.NumberConvert;

/**
 * Created by Tsinling on 2019/1/21 11:03
 * Description:
 */
public abstract class Data implements IData {
    public String TAG = this.getClass().getSimpleName();

    public abstract int dataType();

    public String dataTypeStr() {
        return dataType() == -1 ? "" : NumberConvert.toHexStr(dataType(), 2);
    }



    public Integer parseInt(String codeStr) {
        return parseInt(codeStr, 10, null);
    }

    public Integer parseInt(String codeStr, Integer defaultValue) {
        return parseInt(codeStr, 10, defaultValue);
    }

    public Integer parseInt(String codeStr, int radix, Integer defaultValue) {
        return NumberConvert.parseInt(codeStr, radix, defaultValue);
    }

    public byte[] hexStringToBytes(String hexString) {
        return NumberConvert.hexStringToBytes(hexString);
    }
    protected void checkNull(String binString) {
        if (null == binString || binString.length() == 0) {
            throw new NullPointerException("参数异常,不能为null");
        }
    }

    protected Integer parseInt(String numStr, int radix) {
        try {
            return Integer.parseInt(numStr, radix);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    protected boolean isHexUnsignedStr(String valueHexStr) {
        return NumberConvert.isHexUnsignedStr(valueHexStr);
    }

    protected int toUnsignedInt(byte x) {
        return ((int) x) & 0xff;
    }




}
