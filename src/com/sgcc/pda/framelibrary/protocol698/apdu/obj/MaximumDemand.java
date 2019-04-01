package com.sgcc.pda.framelibrary.protocol698.apdu.obj;

import com.sgcc.pda.framelibrary.protocol698.apdu.data.OI;
import com.sgcc.pda.framelibrary.protocol698.apdu.data.ScalerUnit;
import org.jf.util.SparseArray;

/**
 * Created by Tsinling on 2019/1/31 17:35
 * Description:7.3.2　最大需量接口类（class_id=2）
 * 本接口类对象提供存储最大需量类信息，定义见表127　。
 * <p>
 * 组合无功最大需量的正负数是用来标志潮流的方向，
 * 组合无功最大需量从参与组合无功电能运算的象限中抽取最大值，
 * 如果来自象限3、4，以负数上传
 */
public class MaximumDemand extends Obj {
    private static final String[] INFOS = new String[]{
            "1010\t2\t正向有功最大需量\n" + // kw  double-long-unsigned
                    "1011\t2\tA相正向有功最大需量\n" +
                    "1012\t2\tB相正向有功最大需量\n" +
                    "1013\t2\tC相正向有功最大需量\n" +
                    "1020\t2\t反向有功最大需量\n" +
                    "1021\t2\tA相反向有功最大需量\n" +
                    "1022\t2\tB相反向有功最大需量\n" +
                    "1023\t2\tC相反向有功最大需量\n",
            "1030\t2\t组合无功1最大需量\n" + // kvar double-long
                    "1031\t2\tA相组合无功1最大需量\n" +
                    "1032\t2\tB相组合无功1最大需量\n" +
                    "1033\t2\tC相组合无功1最大需量\n" +
                    "1040\t2\t组合无功2最大需量\n" +
                    "1041\t2\tA相组合无功2最大需量\n" +
                    "1042\t2\tB相组合无功2最大需量\n" +
                    "1043\t2\tC相组合无功2最大需量\n",
            "1050\t2\t第一象限最大需量\n" + // kvar double-long-unsigned
                    "1051\t2\tA相第一象限最大需量\n" +
                    "1052\t2\tB相第一象限最大需量\n" +
                    "1053\t2\tC相第一象限最大需量\n" +
                    "1060\t2\t第二象限最大需量\n" +
                    "1061\t2\tA相第二象限最大需量\n" +
                    "1062\t2\tB相第二象限最大需量\n" +
                    "1063\t2\tC相第二象限最大需量\n" +
                    "1070\t2\t第三象限最大需量\n" +
                    "1071\t2\tA相第三象限最大需量\n" +
                    "1072\t2\tB相第三象限最大需量\n" +
                    "1073\t2\tC相第三象限最大需量\n" +
                    "1080\t2\t第四象限最大需量\n" +
                    "1081\t2\tA相第四象限最大需量\n" +
                    "1082\t2\tB相第四象限最大需量\n" +
                    "1083\t2\tC相第四象限最大需量\n",
            "1090\t2\t正向视在最大需量\n" + //  kVA double-long-unsigned
                    "1091\t2\tA相正向视在最大需量\n" +
                    "1092\t2\tB相正向视在最大需量\n" +
                    "1093\t2\tC相正向视在最大需量\n" +
                    "10A0\t2\t反向视在最大需量\n" +
                    "10A1\t2\tA 相反向视在最大需量\n" +
                    "10A2\t2\tB相反向视在最大需量\n" +
                    "10A3\t2\tC相反向视在最大需量\n",
            "1110\t2\t冻结周期内正向有功最大需量\n" + //  kW  double-long-unsigned
                    "1111\t2\t冻结周期内A相正向有功最大需量\n" +
                    "1112\t2\t冻结周期内B相正向有功最大需量\n" +
                    "1113\t2\t冻结周期内C相正向有功最大需量\n" +
                    "1120\t2\t冻结周期内反向有功最大需量\n" +
                    "1121\t2\t冻结周期内A相反向有功最大需量\n" +
                    "1122\t2\t冻结周期内B相反向有功最大需量\n" +
                    "1123\t2\t冻结周期内C相反向有功最大需量\n",
            "1130\t2\t冻结周期内组合无功1最大需量\n" + // kvar double-long
                    "1131\t2\t冻结周期内A相组合无功1最大需量\n" +
                    "1132\t2\t冻结周期内B相组合无功1最大需量\n" +
                    "1133\t2\t冻结周期内C相组合无功1最大需量\n" +
                    "1140\t2\t冻结周期内组合无功2最大需量\n" +
                    "1141\t2\t冻结周期内A相组合无功2最大需量\n" +
                    "1142\t2\t冻结周期内B相组合无功2最大需量\n" +
                    "1143\t2\t冻结周期内C相组合无功2最大需量\n",
            "1150\t2\t冻结周期内第一象限最大需量\n" + // kvar double-long-unsigned
                    "1151\t2\t冻结周期内A相第一象限最大需量\n" +
                    "1152\t2\t冻结周期内B相第一象限最大需量\n" +
                    "1153\t2\t冻结周期内C相第一象限最大需量\n" +
                    "1160\t2\t冻结周期内第二象限最大需量\n" +
                    "1161\t2\t冻结周期内A相第二象限最大需量\n" +
                    "1162\t2\t冻结周期内B相第二象限最大需量\n" +
                    "1163\t2\t冻结周期内C相第二象限最大需量\n" +
                    "1170\t2\t冻结周期内第三象限最大需量\n" +
                    "1171\t2\t冻结周期内A相第三象限最大需量\n" +
                    "1172\t2\t冻结周期内B相第三象限最大需量\n" +
                    "1173\t2\t冻结周期内C相第三象限最大需量\n" +
                    "1180\t2\t冻结周期内第四象限最大需量\n" +
                    "1181\t2\t冻结周期内A相第四象限最大需量\n" +
                    "1182\t2\t冻结周期内B相第四象限最大需量\n" +
                    "1183\t2\t冻结周期内C相第四象限最大需量\n",
            "1190\t2\t冻结周期内正向视在最大需量\n" + //kVA double-long-unsigned
                    "1191\t2\t冻结周期内A相正向视在最大需量\n" +
                    "1192\t2\t冻结周期内B相正向视在最大需量\n" +
                    "1193\t2\t冻结周期内C相正向视在最大需量\n" +
                    "11A0\t2\t冻结周期内反向视在最大需量\n" +
                    "11A1\t2\t冻结周期内A 相反向视在最大需量\n" +
                    "11A2\t2\t冻结周期内B相反向视在最大需量\n" +
                    "11A3\t2\t冻结周期内C相反向视在最大需量"
    };

    private static final ScalerUnit[] scalerUnits = new ScalerUnit[]{
            new ScalerUnit(-4, 28), //  kw  double-long-unsigned
            new ScalerUnit(-4, 32), // kvar double-long
            new ScalerUnit(-4, 32), // kvar double-long-unsigned
            new ScalerUnit(-4, 30), //  kVA double-long-unsigned
            new ScalerUnit(-4, 28), //  kW  double-long-unsigned
            new ScalerUnit(-4, 32), // kvar double-long
            new ScalerUnit(-4, 32), // kvar double-long-unsigned
            new ScalerUnit(-4, 30), //kVA double-long-unsigned

    };
    private static final Choice[] choices = new Choice[]{
            Choice.DOUBLE_LONG_UNSIGNED,
            Choice.DOUBLE_LONG,
            Choice.DOUBLE_LONG_UNSIGNED,
            Choice.DOUBLE_LONG_UNSIGNED,
            Choice.DOUBLE_LONG_UNSIGNED,
            Choice.DOUBLE_LONG,
            Choice.DOUBLE_LONG_UNSIGNED,
            Choice.DOUBLE_LONG_UNSIGNED,


    };

    private ScalerUnit scalerUnit;
    private Choice choice;

    private MaximumDemand(String objName, int interfClass, String logicName, Choice choice, ScalerUnit scalerUnit) {
        setObjValue(objName, interfClass, logicName);
        this.choice = choice;
        this.scalerUnit = scalerUnit;
    }

    private MaximumDemand() {
    }

    public enum Choice {
        DOUBLE_LONG(5),
        DOUBLE_LONG_UNSIGNED(6);

        int value;

        Choice(int value) {
            this.value = value;
        }
    }

    private static SparseArray<MaximumDemand> objSsparseArray = new SparseArray<>();

    static {
        for (int i = 0; i < INFOS.length; i++) {
            String energyStr = INFOS[i];
            ScalerUnit scalerUnit = scalerUnits[i];
            MaximumDemand.Choice choice = choices[i];
            String[] energyInfoArr = energyStr.split("\n");
            for (String energyInfo : energyInfoArr) {
                String[] energyArr = energyInfo.split("\t");
                int interfClass = Integer.parseInt(energyArr[1]);
                int objNo = Integer.parseInt(energyArr[0]);
                MaximumDemand obj = new MaximumDemand(energyArr[2], interfClass, energyArr[0], choice, scalerUnit);
                objSsparseArray.put(objNo, obj);
            }
        }
    }

    public static MaximumDemand newInstance(String oadHexStr) {
        MaximumDemand obj = (MaximumDemand) new Obj();
        try {
            String oiStr = oadHexStr.substring(0, 4);
            int logicNo = Integer.parseInt(oiStr, 16);
            obj = objSsparseArray.get(logicNo);
            obj.oadHexStr = oadHexStr;
            obj.logicName = new OI(oiStr);

            // 0010 0200 正向有功电能（低精度)   0010 0400 正向有功电能（高精度)
            obj.obj = Integer.parseInt(oadHexStr.substring(4, 6), 16);
            if (obj.obj == 4) { // 默认低精度，当为4时，需要调整为高精度
                // 若是位数转换 -2，则调整为 -4
                obj.scalerUnit.setPow(-4);
            }
        } catch (Exception e) {
            // 截取字符串，或者数字格式化失败
            obj.parseException(e);
        }
        return obj;
    }

  /*  public MaximumDemand newInstance(String oadHexStr, String objHexValue) {
        MaximumDemand energy = newInstance(oadHexStr);
        MaximumDemand.setRatePowerHexStr(objHexValue);
        return energy;
    }*/
}
