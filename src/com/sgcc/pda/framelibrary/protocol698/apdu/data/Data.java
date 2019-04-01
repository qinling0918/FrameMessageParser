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

}
