package com.sgcc.pda.framelibrary.protocol698.apdu.obj;


import com.sgcc.pda.framelibrary.utils.RecoverableString;
import org.jf.util.SparseArray;

/**
 * Created by qinling on 2019/5/16 17:25
 * Description: 冻结类   暂未利用上
 */
public class Freeze extends Obj {
    private static final String INFO =
            "5000\t9\t瞬时冻结\n" +
            "5001\t9\t秒冻结\n" +
            "5002\t9\t分钟冻结\n" +
            "5003\t9\t小时冻结\n" +
            "5004\t9\t日冻结\n" +
            "5005\t9\t结算日冻结\n" +
            "5006\t9\t月冻结\n" +
            "5007\t9\t年冻结\n" +
            "5008\t9\t时区表切换冻结\n" +
            "5009\t9\t日时段表切换冻结\n" +
            "500A\t9\t费率电价切换冻结\n" +
            "500B\t9\t阶梯切换冻结\n" +
            "5011\t9\t阶梯结算冻结";

    private static SparseArray<Inner> varInners = new SparseArray<>();

    static {
        String[] vars = INFO.split("\n");
        for (String var : vars) {
            String[] varArr = var.split("\t");
            int objNo = Integer.parseInt(varArr[0], 16);
            int interfClass = Integer.parseInt(varArr[1]);
            String objName = varArr[2];
            String unit = varArr[3];
            // int attributes = Integer.parseInt(varArr[0].substring(2), 16);
            Inner inner = new Inner(objName, interfClass);
            varInners.put(objNo, inner);
        }
    }

    private final int logicNo;

    public Freeze(String oadHexStr, RecoverableString objValueHexStr) {
        super(oadHexStr, objValueHexStr);
        String oiStr = oadHexStr.substring(0, 4);
        logicNo = Integer.parseInt(oiStr, 16);
        Inner inner = varInners.get(logicNo);
        this.objName = inner.objName;
        this.interfClass = inner.interfClass;

       //this.formatString = format();
    }

    @Override
    public String toFormatString() {
        return objName;
    }
}
