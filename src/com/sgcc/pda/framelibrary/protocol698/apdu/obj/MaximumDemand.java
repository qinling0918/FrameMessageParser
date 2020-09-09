package com.sgcc.pda.framelibrary.protocol698.apdu.obj;


import com.sgcc.pda.framelibrary.protocol698.apdu.data.DataManager;
import com.sgcc.pda.framelibrary.protocol698.apdu.data.DateTimeS;
import com.sgcc.pda.framelibrary.protocol698.apdu.data.ScalerUnit;
import com.sgcc.pda.framelibrary.utils.RecoverableString;

import org.jf.util.SparseArray;

/**
 * Copyright (C) 2019 SCIENCE AND TECHNOLOGY LTD.
 * <p>
 * 描述：最大需求量对象
 * 7.3.2　最大需量接口类（class_id=2）
 * 本接口类对象提供存储最大需量类信息，定义见表127　。
 * <p>
 * <p>
 * 组合无功最大需量的正负数是用来标志潮流的方向，
 * 组合无功最大需量从参与组合无功电能运算的象限中抽取最大值，
 * 如果来自象限3、4，以负数上传
 *
 * @author Abel
 * @version 1.0
 * @date 2019/5/28 18:18
 */
public class MaximumDemand extends Obj {
    /**
     * 表A.2　最大需量类对象标识定义
     */
    private static String[] INFOS = new String[]{
            "1010\t2\t正向有功最大需量\n" +
                    "1011\t2\tA相正向有功最大需量\n" +
                    "1012\t2\tB相正向有功最大需量\n" +
                    "1013\t2\tC相正向有功最大需量\n" +
                    "1020\t2\t反向有功最大需量\n" +
                    "1021\t2\tA相反向有功最大需量\n" +
                    "1022\t2\tB相反向有功最大需量\n" +
                    "1023\t2\tC相反向有功最大需量\n",
            "1030\t2\t组合无功1最大需量\n" +
                    "1031\t2\tA相组合无功1最大需量\n" +
                    "1032\t2\tB相组合无功1最大需量\n" +
                    "1033\t2\tC相组合无功1最大需量\n" +
                    "1040\t2\t组合无功2最大需量\n" +
                    "1041\t2\tA相组合无功2最大需量\n" +
                    "1042\t2\tB相组合无功2最大需量\n" +
                    "1043\t2\tC相组合无功2最大需量\n",
            "1050\t2\t第一象限最大需量\n" +
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
            "1090\t2\t正向视在最大需量\n" +
                    "1091\t2\tA相正向视在最大需量\n" +
                    "1092\t2\tB相正向视在最大需量\n" +
                    "1093\t2\tC相正向视在最大需量\n" +
                    "10A0\t2\t反向视在最大需量\n" +
                    "10A1\t2\tA 相反向视在最大需量\n" +
                    "10A2\t2\tB相反向视在最大需量\n" +
                    "10A3\t2\tC相反向视在最大需量\n",
            "1110\t2\t冻结周期内正向有功最大需量\n" +
                    "1111\t2\t冻结周期内A相正向有功最大需量\n" +
                    "1112\t2\t冻结周期内B相正向有功最大需量\n" +
                    "1113\t2\t冻结周期内C相正向有功最大需量\n" +
                    "1120\t2\t冻结周期内反向有功最大需量\n" +
                    "1121\t2\t冻结周期内A相反向有功最大需量\n" +
                    "1122\t2\t冻结周期内B相反向有功最大需量\n" +
                    "1123\t2\t冻结周期内C相反向有功最大需量\n",
            "1130\t2\t冻结周期内组合无功1最大需量\n" +
                    "1131\t2\t冻结周期内A相组合无功1最大需量\n" +
                    "1132\t2\t冻结周期内B相组合无功1最大需量\n" +
                    "1133\t2\t冻结周期内C相组合无功1最大需量\n" +
                    "1140\t2\t冻结周期内组合无功2最大需量\n" +
                    "1141\t2\t冻结周期内A相组合无功2最大需量\n" +
                    "1142\t2\t冻结周期内B相组合无功2最大需量\n" +
                    "1143\t2\t冻结周期内C相组合无功2最大需量\n",
            "1150\t2\t冻结周期内第一象限最大需量\n" +
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
            "1190\t2\t冻结周期内正向视在最大需量\n" +
                    "1191\t2\t冻结周期内A相正向视在最大需量\n" +
                    "1192\t2\t冻结周期内B相正向视在最大需量\n" +
                    "1193\t2\t冻结周期内C相正向视在最大需量\n" +
                    "11A0\t2\t冻结周期内反向视在最大需量\n" +
                    "11A1\t2\t冻结周期内A 相反向视在最大需量\n" +
                    "11A2\t2\t冻结周期内B相反向视在最大需量\n" +
                    "11A3\t2\t冻结周期内C相反向视在最大需量"
    };

    /**
     * 单位和换算等
     */
    private static ScalerUnit[] SCALER_UNITS = new ScalerUnit[]{
            // kw  double-long-unsigned
            new ScalerUnit(-4, 28),
            // kvar double-long
            new ScalerUnit(-4, 32),
            // kvar double-long-unsigned
            new ScalerUnit(-4, 32),
            // kVA double-long-unsigned
            new ScalerUnit(-4, 30),
            // kW  double-long-unsigned
            new ScalerUnit(-4, 28),
            // kvar double-long
            new ScalerUnit(-4, 32),
            // kvar double-long-unsigned
            new ScalerUnit(-4, 32),
            //kVA double-long-unsigned
            new ScalerUnit(-4, 30)
    };

    /**
     * 最大需量值 数据类型
     */
    private static Choice[] CHOICES = new Choice[]{
            Choice.DOUBLE_LONG_UNSIGNED,
            Choice.DOUBLE_LONG,
            Choice.DOUBLE_LONG_UNSIGNED,
            Choice.DOUBLE_LONG_UNSIGNED,
            Choice.DOUBLE_LONG_UNSIGNED,
            Choice.DOUBLE_LONG,
            Choice.DOUBLE_LONG_UNSIGNED,
            Choice.DOUBLE_LONG_UNSIGNED
    };

    private static SparseArray<MaximumDemandInner> sDemandSparseArray = new SparseArray<>();

    static {
        for (int i = 0, len = INFOS.length; i < len; i++) {
            String demandStr = INFOS[i];
            ScalerUnit scalerUnit = SCALER_UNITS[i];
            Choice choice = CHOICES[i];
            String[] demandArr = demandStr.split("\n");
            for (String str : demandArr) {
                String[] varArr = str.split("\t");
                int objNo = Integer.parseInt(varArr[0], 16);
                int interfClass = Integer.parseInt(varArr[1], 16);
                MaximumDemandInner inner = new MaximumDemandInner(varArr[2], interfClass, choice, scalerUnit);
                sDemandSparseArray.put(objNo, inner);
            }
        }
    }

    private ScalerUnit mScalerUnit;
    /**
     * 格式化的数据
     */
    private final String formatString;

    public MaximumDemand(String oadHexStr, RecoverableString objValueHexStr) {
        super(oadHexStr, objValueHexStr);
        try {
            String oiStr = oadHexStr.substring(0, 4);
            int logicNo = Integer.parseInt(oiStr, 16);
            MaximumDemandInner demandInner = sDemandSparseArray.get(logicNo);
            objName = demandInner.objName;
            interfClass = demandInner.interfClass;
            mScalerUnit = demandInner.mScalerUnit;
        } catch (Exception e) {
            // 截取字符串，或者数字格式化失败
            parseException(e);
        }

        this.formatString = format();
    }

    @Override
    public String toFormatString() {
        return formatString;
    }

    /**
     * 格式化单个structure
     *
     * @param value 值
     * @param time  时间
     * @return 返回格式化后的数据
     */
    private String formatString(String value, String time) {
        String builder = objName + "：" +
                getFormatValueWithUnit(Long.parseLong(value, 16), mScalerUnit) +
                "\n" +
                "发生时间：" + new DateTimeS(time).format() + "\n";
        return builder;
    }

    /**
     * 格式化最大需量数据
     *
     * @return 返回字符串
     */
    private String format() {
        StringBuilder builder = new StringBuilder();
        String classify = objValueHexStr.substring(0, 2);
        //数据类型，01是array，02是structure  array时表示全部元素（0200）  structure 表示的那个元素（0201）
        switch (classify) {
            case "01":
                int len = Integer.parseInt(objValueHexStr.substring(0, 2), 16);
                if (len > 0) {
                    for (int i = 0; i < len; i++) {
                        String innerClassify = objValueHexStr.substring(0, 2);
                        if ("02".equals(innerClassify)) {
                            int num = Integer.parseInt(objValueHexStr.substring(0, 2), 16);
                            if (num != 2) {
                                return builder.append("数据出现错误！").toString();
                            }
                            String type = objValueHexStr.substring(0, 2);
                            int typeLen = DataManager.getInstance().getDataByteSize(type);
                            String value = objValueHexStr.substring(0, typeLen * 2);
                            typeLen = DataManager.getInstance().getDataByteSize(objValueHexStr.substring(0, 2));
                            String time = objValueHexStr.substring(0, typeLen * 2);
                            builder.append(formatString(value, time));
                        } else {
                            builder.append("array的第").append(i).append("个structure为null！");
                        }
                    }
                }
                break;
            case "02":
                int num = Integer.parseInt(objValueHexStr.substring(0, 2), 16);
                if (num != 2) {
                    return builder.append("数据出现错误！").toString();
                }
                int typeLen = DataManager.getInstance().getDataByteSize(objValueHexStr.substring(0, 2));
                String value = objValueHexStr.substring(0, typeLen * 2);
                typeLen = DataManager.getInstance().getDataByteSize(objValueHexStr.substring(0, 2));
                String time = objValueHexStr.substring(0, typeLen * 2);
                builder.append(formatString(value, time));
                break;
            default:
                builder.append("返回数据为null");
                break;
        }
        return builder.toString();
    }

    /**
     * 最大需求量值 数据类型
     */
    public enum Choice {
        /**
         * 最大需求量值 double-long
         */
        DOUBLE_LONG(5),
        /**
         * 最大需求量值 double-long-unsigned
         */
        DOUBLE_LONG_UNSIGNED(6);

        int value;

        Choice(int value) {
            this.value = value;
        }
    }

    static final class MaximumDemandInner extends Obj.Inner {
        Choice mChoice;
        ScalerUnit mScalerUnit;

        MaximumDemandInner(String objName, int interfClass, Choice choice, ScalerUnit scalerUnit) {
            super(objName, interfClass);
            this.mChoice = choice;
            this.mScalerUnit = scalerUnit;
        }
    }
}
