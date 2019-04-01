package com.sgcc.pda.framelibrary.protocol698.apdu.obj;

import com.sgcc.pda.framelibrary.protocol698.apdu.data.OI;
import com.sgcc.pda.framelibrary.protocol698.apdu.data.ScalerUnit;
import com.sgcc.pda.framelibrary.utils.NumberConvert;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by qinling on 2019/1/31 12:52
 * Description:
 */
public class Obj {
    protected String objName; // 对象名称
    protected int interfClass; // 接口类id
    protected OI logicName; // 对象标识
    protected int obj;
    protected String oadHexStr; // OAD 4B
    protected String ObjValueHexStr; // 对象对应字符串

    public String getObjName() {
        return objName;
    }

    public int getInterfClass() {
        return interfClass;
    }

    protected void setObjValue(String objName, int interfClass, String logicName) {
        this.objName = objName;
        this.interfClass = interfClass;
        this.logicName = new OI(logicName);
    }

    protected String getFormatWithUnit(long value, ScalerUnit scalerUnit) {
        StringBuilder foramt = new StringBuilder("#0.0");
        for (int i = 1; i < Math.abs(scalerUnit.getPow()); i++) {
            foramt.append(0);
        }
        foramt.append(scalerUnit.getUnitStr());
        DecimalFormat decimalFormat = new DecimalFormat(foramt.toString());
        return decimalFormat.format(value) ;
    }

   /* protected String getFormatWithUnit(int value, ScalerUnit scalerUnit) {
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(Math.abs(scalerUnit.getPow()));
        return format.format(value)+scalerUnit.getUnitStr() ;
    }*/



    protected void parseException(Exception e) {
    }
}
