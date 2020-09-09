package com.sgcc.pda.framelibrary.protocol698.apdu.obj;


import com.sgcc.pda.framelibrary.protocol698.apdu.OIManager;
import com.sgcc.pda.framelibrary.utils.NumberConvert;
import com.sgcc.pda.framelibrary.utils.RecoverableString;
import org.jf.util.SparseArray;

/**
 * Created by qinling on 2019/5/13 10:58
 * Description: 参变量
 */
public class Control extends Obj {
    private static final String INFO =
            "8000\t8\t远程控制\n" +
                    "8001\t8\t保电\n" +
                    "8002\t8\t催费告警\n" +
                    "8003\t11\t一般中文信息\n" +
                    "8004\t11\t重要中文信息\n" +
                    "8100\t8\t终端保安定值\n" +
                    "8101\t8\t终端功控时段\n" +
                    "8102\t8\t功控告警时间\n" +
                    "8103\t13\t时段功控\n" +
                    "8104\t13\t厂休控\n" +
                    "8105\t13\t营业报停控\n" +
                    "8106\t13\t当前功率下浮控\n" +
                    "8107\t13\t购电控\n" +
                    "8108\t13\t月电控\n" +
                    "8109\t8\t时段功控配置单元\n" +
                    "810A\t8\t厂休控配置单元\n" +
                    "810B\t8\t营业报停控配置单元\n" +
                    "810C\t8\t购电控配置单元\n" +
                    "810D\t8\t月电控配置单元\n" +
                    "810E\t8\t控制对象\n" +
                    "810F\t8\t跳闸轮次\n" +
                    "8110\t8\t电控定值\n";

    private static SparseArray<Inner> varInners = new SparseArray<>();

    static {
        String[] vars = INFO.split("\n");
        for (String var : vars) {
            String[] varArr = var.split("\t");
            int objNo = Integer.parseInt(varArr[0], 16);
            int interfClass = Integer.parseInt(varArr[1]);
            String objName = varArr[2];
            Inner variableInner = new Inner(objName, interfClass);
            varInners.put(objNo, variableInner);
        }
    }

    private final int logicNo;
    private final Inner variableInner;
    private final String formatString;

    public Control(String oadHexStr, RecoverableString objValueHexStr) {
        super(oadHexStr, objValueHexStr);
        String oiStr = oadHexStr.substring(0, 4);
        logicNo = Integer.parseInt(oiStr, 16);
        this.variableInner = varInners.get(logicNo);
        this.objName = variableInner.objName;
        this.interfClass = variableInner.interfClass;
        this.formatString = format();
    }

    @Override
    public String toFormatString() {
        return formatString;
    }

    private String format() {
        switch (logicNo) {


            case 0x810E:
                String value = getValueByDataType();
                return objName + ": " + OIManager.getInstance().getObjName(value);
            case 0x810F:
                String hex_BitString = getValueByDataType();
                return objName + ": " + hex_BitString+"("+ NumberConvert.hexStrToBinaryStr(hex_BitString)+"）";
            case 0x8110:
                return objName + ": "+ getFormatValueWithUnit(getNum(),"kWh",-4);
            default:
                return "暂未完成解析: " + objName + ":" + objValueHexStr.getCurrentSting();
        }
    }

}
