package com.sgcc.pda.framelibrary.protocol698.apdu.obj;

import android.support.annotation.NonNull;

import com.sgcc.pda.framelibrary.FormatAble;
import com.sgcc.pda.framelibrary.protocol698.apdu.data.DateTimeS;
import com.sgcc.pda.framelibrary.utils.NumberConvert;
import com.sgcc.pda.framelibrary.utils.RecoverableString;

import com.sgcc.pda.framelibrary.utils.TextUtils;
import org.jf.util.SparseArray;

import java.util.Locale;

/**
 * Created by qinling on 2019/5/10 11:47
 * Description:  A.3　变量类对象
 */
public class Variable extends Obj {
    // 注1：三相三线电能表电压A相为Uab，B相为0，C相为Ucb；
    //      电流A相为Ia，B相为0，C相为Ic；
    //      功率因数A相为Uab与Ia的夹角余弦，B相为0，C相为Ucb与Ic的夹角余弦；
    //      相角A相为Uab与Ia的夹角，B相为0，C相为Ucb与Ic的夹角。
    //注2：电流、有功功率、无功功率、视在功率、功率因数、当前有功需量、当前无功需量、
    //      当前视在需量、表内温度按潮流方向分为正负数，正数代表输入，负数代表输出。
    //      需量一定意义上也可称作平均功率，因此它与瞬时功率一样有潮流方向的区分。表内温度存在零上和零下的区别，正数为摄氏零上，负数为摄氏零下。
    //注3：相角测量范围是0…360度。
    //注4：当前有功需量、当前无功需量、当前视在需量是最近一段时间的平均功率。
    //      组合无功最大需量的正负数是用来标志潮流的方向，
    //      组合无功最大需量从参与组合电能运算的象限中抽取最大值，
    //      如果来自象限3、4，以负数上传。
    private static final String INFO =
            "2000\t3\t电压\tV\t-1\n" +
                    "2001\t3\t电流\tA\t-3\n" +
                    "2002\t3\t电压相角\t度\t-1\n" +
                    "2003\t3\t电压电流相角\t度\t-1\n" +
                    "2004\t4\t有功功率\tW\t-1\n" +
                    "2005\t4\t无功功率\tvar\t-1\n" +
                    "2006\t4\t视在功率\tVA\t-1\n" +
                    "2007\t4\t一分钟平均有功功率\tW\t-1\n" +
                    "2008\t4\t一分钟平均无功功率\tvar\t-1\n" +
                    "2009\t4\t一分钟平均视在功率\tVA\t-1\n" +
                    "200A\t4\t功率因数\t\t-3\n" +
                    "200B\t3\t电压波形失真度\t%\t-2\n" +
                    "200C\t3\t电流波形失真度\t%\t-2\n" +
                    "200D\t5\t电压谐波含有量（总及2…n次）\t%\t-2\n" +
                    "200E\t5\t电流谐波含有量（总及2…n次）\t%\t-2\n" +
                    "200F\t6\t电网频率\tHz\t-2\n" +
                    "2010\t6\t表内温度\t℃\t-1\n" +
                    "2011\t6\t时钟电池电压\tV\t-2\n" +
                    "2012\t6\t停电抄表电池电压\tV\t-2\n" +
                    "2013\t6\t时钟电池工作时间\t分钟\t0\n" +
                    "2014\t6\t电能表运行状态字\t \t \n" + // 数据类型：array bit-string，无单位，无换算，包括电能表运行状态字1…7，见附　录　G
                    "2015\t6\t电能表跟随上报状态字\t \t \n" + // 数据类型：bit-string(SIZE(32))，无单位，无换算，见附　录　G
                    "2017\t6\t当前有功需量\tkW\t-4\n" +
                    "2018\t6\t当前无功需量\tkvar\t-4\n" +
                    "2019\t6\t当前视在需量\tkVA\t-4\n" +
                    "201A\t6\t当前电价\t元/kWh\t-4\n" +
                    "201B\t6\t当前费率电价\t元/kWh\t-4\n" +
                    "201C\t6\t当前阶梯电价\t元/kWh\t-4\n" +
                    "201E\t8\t事件发生时间\t \t \n" +
                    "2020\t8\t事件结束时间\t \t \n" +
                    "2021\t8\t数据冻结时间\t \t \n" +
                    "2022\t8\t事件记录序号\t\t0\n" +
                    "2023\t8\t冻结记录序号\t\t0\n" +
                    "2024\t8\t事件发生源\t \t \n" + // 具体对象定义
                    "2025\t8\t事件当前值\t \t \n" + // structure{事件发生次数  double-long-unsigned，事件累计时间  double-long-unsigned（单位：秒，无换算）}
                    "2026\t6\t电压不平衡率\t%\t-2\n" +
                    "2027\t6\t电流不平衡率\t%\t-2\n" +
                    "2028\t6\t负载率\t%\t-2\n" +
                    "2029\t6\t安时值\t \t \n" + // Ah 属性2安时数值∷=array 相安时值相安时值∷=double-long-unsigned，单位：Ah，换算：-2相安时值包总、A、B、C相。
                    "202A\t8\t目标服务器地址\t \t \n" + // 属性2∷=TSA
                    "202C\t8\t（当前）钱包文件\t \t \n" + // 数值∷=structure{剩余金额   double-long-unsigned（单位：元，换算：-2），购电次数   double-long-unsigned}
                    "202D\t6\t（当前）透支金额\t元\t-2\n" +
                    "202E\t6\t累计购电金额\t元\t-2\n" +
                    "2031\t6\t月度用电量\tkWh\t-2\n" +
                    "2032\t6\t阶梯结算用电量\tkWh\t-2\n" +
                    "2033\t9\t电器设备\t \t \n" + //属性 2 电器设备∷=structure{设备编号 unsigned，设备名称 visible-string，设备特征 array 特征库}特征库∷=structure{特征编号 unsigned，特征 1 long64特征 2 long64}
                    "2040\t6\t控制命令执行状态字\t \t \n" + //数据类型：bit-string(SIZE(16))，无单位，无换算
                    "2041\t6\t控制命令错误状态字\t \t \n" + //数据类型：bit-string(SIZE(16))，无单位，无换算
                    "2050\t6\t电流回路状态\t \t \n" + // 属性 2∷=array enum
                    "2100\t14\t分钟区间统计\t \t \n" + // 统计周期单位为分钟
                    "2101\t14\t小时区间统计\t \t \n" + //统计周期单位为小时
                    "2102\t14\t日区间统计\t \t \n" + //统计周期单位为日
                    "2103\t14\t月区间统计\t \t \n" +
                    "2104\t14\t年区间统计\t \t \n" +
                    "2110\t15\t分钟平均\t \t \n" +
                    "2111\t15\t小时平均\t \t \n" +
                    "2112\t15\t日平均\t \t \n" +
                    "2113\t15\t月平均\t \t \n" +
                    "2114\t15\t年平均\t \t \n" +
                    "2120\t16\t分钟极值\t \t \n" +
                    "2121\t16\t小时极值\t \t \n" +
                    "2122\t16\t日极值\t \t \n" +
                    "2123\t16\t月极值\t \t \n" +
                    "2124\t16\t年极值\t \t \n" +
                    "2131\t6\t当月A相电压合格率\t \t \n" + // 属性2（电压合格率数据）∷=structure{当日电压合格率         电压合格率，当月电压合格率         电压合格率}电压合格率∷=structure{ 电压监测时间  double-long-unsigned（单位：分钟，无换算），电压合格率   long-unsigned（单位：%，换算：-2），电压超限率   long-unsigned（单位：%，换算：-2），电压超上限时间  double-long-unsigned（单位：分钟，无换算），电压超下限时间  double-long-unsigned（单位：分钟，无换算）}
                    "2132\t6\t当月B相电压合格率\t \t \n" + // 同上
                    "2133\t6\t当月C相电压合格率\t \t \n" + // 同上
                    "2140\t2\t日最大有功功率及发生时间\t \t \n" + // 最大功率及发生时间∷=structur {最大功率值 double-long-unsigned，发生时间   date_time_s}功率单位：kW，换算：-4
                    "2141\t2\t月最大有功功率及发生时间\t \t \n" + // 同上
                    "2200\t6\t通信流量\t \t \n" + // 数值∷=structure{当日通信流量  double-long-unsigned，当月通信流量  double-long-unsigned}单位：byte，换算：0
                    "2203\t6\t供电时间\t \t \n" + // 数值∷=structure{日供电累计时间  double-long-unsigned，月供电累计时间  double-long-unsigned}单位：分钟，换算：0
                    "2204\t6\t复位次数\t \t \n" + // 数值∷=structure{日复位累计次数  long-unsigned，月复位累计次数  long-unsigned}
                    "2301\t23\t总加组1\t \t \n" +
                    "2302\t23\t总加组2\t \t \n" +
                    "2303\t23\t总加组3\t \t \n" +
                    "2304\t23\t总加组4\t \t \n" +
                    "2305\t23\t总加组5\t \t \n" +
                    "2306\t23\t总加组6\t \t \n" +
                    "2307\t23\t总加组7\t \t \n" +
                    "2308\t23\t总加组8\t \t \n" +
                    "2401\t12\t脉冲计量1\t \t \n" +
                    "2402\t12\t脉冲计量2\t \t \n" +
                    "2403\t12\t脉冲计量3\t \t \n" +
                    "2404\t12\t脉冲计量4\t \t \n" +
                    "2405\t12\t脉冲计量5\t \t \n" +
                    "2406\t12\t脉冲计量6\t \t \n" +
                    "2407\t12\t脉冲计量7\t \t \n" +
                    "2408\t12\t脉冲计量8\t \t \n" +
                    "2500\t6\t累计水（热）流量\t立方米\t-4\n" +
                    "2501\t6\t累计气流量\t立方米\t-4\n" +
                    "2502\t6\t累计热量\tJ\t-2\n" +
                    "2503\t6\t热功率\tJ/h\t-2\n" +
                    "2504\t6\t累计工作时间\t小时\t0\n" +
                    "2505\t6\t水温\t \t \n" + // 数值∷=structure{供水温度  double-long-unsigned，回水温度  double-long-unsigned}单位：℃，换算：-2。
                    "2506\t6\t（仪表）状态ST\t \t " // 数值∷=structure{阀门状态  enum{开（0），关（1），保留（2），异常（3）}，电池电压  enum{正常（0），欠压（0）}}
            ;
    private static SparseArray<VariableInner> varInners = new SparseArray<>();

    static {
        String[] vars = INFO.split("\n");
        for (String var : vars) {
            String[] varArr = var.split("\t");
            int objNo = Integer.parseInt(varArr[0], 16);
            int interfClass = Integer.parseInt(varArr[1]);
            String objName = varArr[2];
            String unit = varArr[3];
            Integer pow = getPow(varArr[4]);
            // int attributes = Integer.parseInt(varArr[0].substring(2), 16);
            VariableInner variableInner = new VariableInner(objName, interfClass, pow, unit);
            varInners.put(objNo, variableInner);
        }
    }

    private static Integer getPow(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private final VariableInner variableInner;
    private final int logicNo;
    private final String formatString;

    public Variable(String oadHexStr, RecoverableString objValueHexStr) {
        super(oadHexStr, objValueHexStr);
        String oiStr = oadHexStr.substring(0, 4);
        logicNo = Integer.parseInt(oiStr, 16);
        this.variableInner = varInners.get(logicNo);
        this.objName = variableInner.objName;
        this.interfClass = variableInner.interfClass;
        // this.attributes = Integer.parseInt(oadHexStr.substring(4, 6), 16);
        // 0010 0200 正向有功电能（低精度)   0010 0400 正向有功电能（高精度)
        this.formatString = format();
    }

    private String format() {
        if (variableInner.pow == null && TextUtils.isEmpty(variableInner.unit.trim())) {
            return getFormatStringForSpecialData();
        }
        if (interfClass == 3 && index == 0) {
            // FIXME: 2019/5/10  按照数组解析
            // 8501012000020001
            // 01011209390000
            int itemCount = Integer.parseInt(objValueHexStr.substring(2, 4), 16);
            //String string = objValueHexStr.substring(4);
            StringBuilder stringBuilder = new StringBuilder();
            if (itemCount < 3 && itemCount > 0) {
                stringBuilder.append("  注：单相表仅有A相数据\n");
            }
            for (int i = 0; i < itemCount; i++) {
               /* String dataType = objValueHexStr.substring(0, 2);
                int dataSize = DataManager.getInstance().getDataByteSize(dataType, string.substring(2));
                String value = objValueHexStr.substring(0,  dataSize * 2);
                string = string.substring(2 + dataSize * 2);*/
                stringBuilder
                        .append(getFormatStringIc3(i + 1, getValueByDataType(objValueHexStr)))
                        .append(i == itemCount - 1 ? "" : "\n");
            }

            return stringBuilder.toString();
        }
        if (interfClass == 4) {
            return getIC4();
        }

        String value = getValueByDataType();
        return getFormatStringIc3(index, value);
    }

    @NonNull
    private String getIC4() {

        if (index == 0) {
            // 01021003E81003E80000
            int itemCount = Integer.parseInt(objValueHexStr.substring(2, 4), 16);
            // 1003E81003E80000
            StringBuilder stringBuilder = new StringBuilder(objName).append(" :\n");

            for (int i = 0; i < itemCount; i++) {
                stringBuilder
                        .append(getFormatString(getValueByDataType(objValueHexStr)))
                        .append(i == itemCount - 1 ? "" : "\n");
            }

            return stringBuilder.toString();
        } else {
            return getFormatString(getValueByDataType(objValueHexStr));
        }

    }

    private String getFormatString(String valueByDataType) {
        return getFormatValueWithUnit(Long.parseLong(valueByDataType, 16),
                variableInner.unit, variableInner.pow);
    }

    @Override
    public String toFormatString() {
        return formatString;
    }

    // interface3  或者 5 专用
    @NonNull
    private String getFormatStringIc3(int index, String value) {
        return getObjName(index) + ": " + getFormatValueWithUnit(Long.parseLong(value, 16),
                variableInner.unit, variableInner.pow);
    }

    /*   @NonNull
       private String getFormatString(int index, String value) {
           return objName + ": " + getFormatValueWithUnit(Long.parseLong(value, 16),
                   variableInner.unit, variableInner.pow);
       }*/
    private String getObjName(int index) {
        String[] phases = new String[]{"", "A相", "B相", "C相"};
        try {
            return phases[index] + objName;
        } catch (IndexOutOfBoundsException e) {
            return objName;
        }
    }

    private String getFormatStringForSpecialData() {
        switch (logicNo) {

            // 时间
            case 0x201E:
            case 0x2020:
            case 0x2021:
                String valueStr = getValueByDataType();
                if ("".equals(valueStr)) {
                    return objName + ": 无该信息";
                }
                if (TextUtils.isEmpty(valueStr) || valueStr.length() < 14) {
                    return objName + ": 解析失败";
                }

                return String.format(Locale.CHINA, "%s： %s ", objName, new DateTimeS(valueStr).format());
           /* case 0x2022:
                return new ParamsVariable.Obj_Number(this,variableInner.unit,variableInner.pow).toFormatString();
             */



            case 0x2025:
            case 0x2029:
            case 0x202C:
            case 0x2200:
            case 0x2203:
            case 0x2204:
            case 0x2505:
                // 未解析
                return new Obj_Number_Structure(this).toFormatString();
            // 状态字
            // TODO: 2019/5/10  特殊解析 
            case 0x2014:
                // StringBuilder
                return getRunningStatusWord();
            //                return 8501032040020001041000000000;
            case 0x2015:
            case 0x2040:
            case 0x2041:
                // 未解析
                return objName + ": " + getValueByDataType() + "\n";
            case 0x202A:
                // 字符串  TSA , MAC
                return objName + ": " + getValueByDataType() + "\n";
           /*  case 0x2015:
                return "";*/
            // 当前钱包
        /*    case 0x202C:

                String[] paramsName = new String[]{"剩余金额", "购电次数", ""};
                Integer[] nums = new Integer[2];
                StringBuilder stringBuilder = new StringBuilder();
                // 202C0200
                if (index == 0) {
                    // structure 去除 0202
                    objValueHexStr.substring(4);
                    for (int i = 0; i < nums.length; i++) {

                        nums[i] = getNum(getValueByDataType()).intValue();
                        // str = str.substring(end);
                        stringBuilder.append(paramsName[i])
                                .append(": ")
                                .append(i == 0 ? getFormatValueWithUnit(nums[i], "元", -2) : nums[i])
                                .append(i == nums.length - 1 ? "" : "\n");
                    }
                } else {  // 202C0201   202C0202
                    try {
                        stringBuilder.append(paramsName[index - 1]).append(": ").append(getNum()).append("\n");
                    } catch (Throwable e) {
                        stringBuilder.append("");
                    }

                }
                return stringBuilder.toString();*/


            default:
                if ("".equals(getValueByDataType())) {
                    return objName + ": 无该信息";
                }
                return objName + ": 暂未支持解析";
        }


    }
    public static final class Obj_Number_Structure implements FormatAble {
        private static final String[] names2025 = new String[]{"事件发生次数\t\t0", "事件累计时间\t秒\t0"};
        private static final String[] names2029 = new String[]{"相安时值\tAh\t-2"};
        private static final String[] names202C = new String[]{"剩余金额\t元\t-2","购电次数\t\t0"};
        private static final String[] names2200 = new String[]{"当日通信流量\tbyte\t0","当月通信流量\tbyte\t0"};
        private static final String[] names2203 = new String[]{"日供电累计时间\t分钟\t0", "月供电累计时间\t分钟\t0"};
        private static final String[] names2204 = new String[]{"日复位累计次数\t\t0", "月复位累计次数\t\t0"};
        private static final String[] names2505 = new String[]{"供水温度\t℃\t-2", "回水温度\t℃\t-2"};

        private static final SparseArray<String[]> names = new SparseArray<>();

        static {
            names.put(0x2025, names2025);
            names.put(0x2029, names2029);
            names.put(0x202C, names202C);
            names.put(0x2200, names2200);
            names.put(0x2203, names2203);
            names.put(0x2204, names2204);
            names.put(0x2505, names2505);

        }

     /*   public static class ScalerUnit {
            public String unit;
            public Integer pow;

            public ScalerUnit(String unit, Integer pow) {
                this.unit = unit;
                this.pow = pow;
            }
        }

        private static final SparseArray<ParamsVariable.Obj_Number_Structure.ScalerUnit> scaleUnits = new SparseArray<>();

        static {
            scaleUnits.put(0x4007, new ParamsVariable.Obj_Number_Structure.ScalerUnit("", 0));
            scaleUnits.put(0x400C, new ParamsVariable.Obj_Number_Structure.ScalerUnit("", 0));
            scaleUnits.put(0x4019, new ParamsVariable.Obj_Number_Structure.ScalerUnit("元/kWh", -4));
            scaleUnits.put(0x401E, new ParamsVariable.Obj_Number_Structure.ScalerUnit("元", -2));
            scaleUnits.put(0x401F, new ParamsVariable.Obj_Number_Structure.ScalerUnit("元", -2));
            scaleUnits.put(0x4020, new ParamsVariable.Obj_Number_Structure.ScalerUnit("kWh", -2));
            scaleUnits.put(0x4021, new ParamsVariable.Obj_Number_Structure.ScalerUnit("kWh", -2));
            scaleUnits.put(0x4030, new ParamsVariable.Obj_Number_Structure.ScalerUnit("V", -1));
            scaleUnits.put(0x4041, new ParamsVariable.Obj_Number_Structure.ScalerUnit("", 0));
            scaleUnits.put(0x410C, new ParamsVariable.Obj_Number_Structure.ScalerUnit("", -3));
            scaleUnits.put(0x410D, new ParamsVariable.Obj_Number_Structure.ScalerUnit("", -3));
            scaleUnits.put(0x410E, new ParamsVariable.Obj_Number_Structure.ScalerUnit("", -3));
            scaleUnits.put(0x410F, new ParamsVariable.Obj_Number_Structure.ScalerUnit("", -3));
        }*/

        Variable variable;
        String formatString;

        public Obj_Number_Structure(Variable variable) {
            this.variable = variable;
            this.formatString = format();
        }

        @Override
        public String toFormatString() {
            return formatString;
        }

        @NonNull
        private String format() {
            switch (variable.attributes) {
                case 0x02:
                    String[] nameArr = names.get(variable.logicNo);
                   // ParamsVariable.Obj_Number_Structure.ScalerUnit scalerUnit = scaleUnits.get(paramsVariable.logicNo);
                    StringBuilder stringBuilder = new StringBuilder();
                    if (variable.index == 0) {
                        // 将代表 structure 的第一个字节 02 舍弃，
                        // 或者将代表 array 的第一个字节 01 舍弃，
                        // String string = paramsVariable.objValueHexStr.substring(2);
                        variable.objValueHexStr.substring(2);
                        // 获取structure 的子元素的个数
                        // 获取array 的子元素的个数
                        int itemCount = Integer.parseInt(variable.objValueHexStr.substring(0, 2), 16);
                        // 截掉表示 子元素个数的值。
                        //string = string.substring(2);
                        for (int i = 0; i < itemCount; i++) {
                            String value = variable.getValueByDataType();
                            getNumberStructureItem(nameArr[i], stringBuilder, value);
                            stringBuilder.append(i == itemCount - 1 ? "" : "\n");
                        }
                        // 表示直接取值 structure 中的某个元素
                    } else {
                        String value = variable.getValueByDataType();
                        getNumberStructureItem(nameArr[variable.index - 1], stringBuilder, value);

                    }

                    return stringBuilder.toString();
                default:
                    return variable.objName + " 暂无法解析";
            }
        }

        private void getNumberStructureItem(String s, StringBuilder stringBuilder, String value) {
            String[] name_unit_pow = s.split("\t");
            String name = name_unit_pow[0];
            String unit = name_unit_pow[1];
            Integer pow = getPow(name_unit_pow[2]);
            pow = null == pow? 0: pow;
            long num = Long.parseLong(value, 16);
            stringBuilder
                    .append(name)
                    .append(": ")
                    .append(variable.getFormatValueWithUnit(num, unit, pow));
        }

        private void getItemFormatString(StringBuilder append, ParamsVariable.Obj_Number_Structure.ScalerUnit scalerUnit, String value) {
            long num = Long.parseLong(value, 16);
            append
                    .append(": ")
                    .append(variable.logicNo == 0x4041
                            ? (num == 0 ? "0（失败）" : "1（成功）")
                            : variable.getFormatValueWithUnit(num, scalerUnit.unit, scalerUnit.pow))
                    .append("\n");
        }
    }
    private String getRunningStatusWord() {
        // 010704100000041000000410114F04100000041000000410000004100000
        if (index == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            int count = Integer.parseInt(objValueHexStr.substring(2, 4), 16);
            for (int i = 0; i < count; i++) {
                String status = getValueByDataType();
                stringBuilder.append(getRunningStatusWord(i + 1, status));
            }
            return stringBuilder.toString();
        } else {
            String status = getValueByDataType();
            return getRunningStatusWord(index, status);
        }
    }

    private String getRunningStatusWord(int wordNo, String status) {
        if ("".equals(status)) {
            return "无该信息";
        }
        String bits = NumberConvert.hexStrToBinaryStr(status);
        char[] bitChar = bits.toCharArray();
       /* for (int i = 0; i < bitChar.length; i++) {
            System.out.println(i+":  "+ (bitChar[i]=='1'));
        }*/
        //System.out.println(bits.subSequence(9,11).toString());
        // BitSet bitSet = BitSet.valueOf((byte[])new byte[]{0x04});
        //BitSet bitSet = new BitSet(1);
        // bitSet.set(0);


        StringBuilder stringBuilder = new StringBuilder("电能表运行状态字").append(wordNo);
        String[] name = new String[]{"", "", "", "(操作类)", "(A相故障状态)", "(B相故障状态)", "(C相故障状态)", "(合相故障状态)"};
      //  stringBuilder.append(name[wordNo]).append(": ").append(TextUtils.getReverse(bits, 0, bits.length())).append("\n");
        stringBuilder.append(name[wordNo]).append(": ").append(bits).append("\n");
        //NumberConvert.hexStrReverse()
        switch (wordNo) {
            case 1:
                String[] words1_1 = new String[]{"保留", "需量积算方式:区间", "时钟电池:欠压", "停电抄表电池:欠压",
                        "有功功率方向:反向", "无功功率方向:反向", "保留", "保留",
                        "控制回路错误", "ESAM错误", "保留", "保留",
                        "内部程序错误", "存储器故障或损坏", "透支状态", "时钟故障",
                };
                String[] words1_0 = new String[]{"保留", "需量积算方式:滑差", "时钟电池:正常", "停电抄表电池:正常",
                        "有功功率方向:正向", "无功功率方向:正向", "保留", "保留",
                        "控制回路正常", "ESAM正常", "保留", "保留",
                        "内部程序正常", "存储器正常", "非透支状态", "时钟正常",
                };

                for (int i = 0; i < bitChar.length; i++) {
                    String statusStr = bitChar[i] == '1' ? words1_1[i] : words1_0[i];
                    if (!"保留".equals(statusStr)) {
                        stringBuilder/*.append("bit").append(i).append(". ")*/.append("  ").append(statusStr).append("\n");
                    }
                }
                stringBuilder.append("  其他：  保留\n");
                break;
            case 2:
                String[] words2 = new String[]{
                        "A相有功功率方向", "B相有功功率方向", "C相有功功率方向", "保留",
                        "A相无功功率方向", "B相无功功率方向", "C相无功功率方向", "保留",
                        "保留", "保留", "保留", "保留", "保留", "保留", "保留", "保留",
                };

                for (int i = 0; i < bitChar.length; i++) {
                    String direction = bitChar[i] == '1' ? "反向" : "正向";
                    if (!"保留".equals(words2[i])) {
                        stringBuilder/*.append("bit").append(i).append(". ")*/.append("  ").append(words2[i]).append(": ").append(direction).append("\n");
                    }
                }
                stringBuilder.append("  其他：  保留\n");
                break;
            case 3:
                // 从左到右 bit0-bit15
                // 保电状态 114f    0001000101001111
                // 保电解除状态 1147 0001000101000111

                String[] words3 = new String[]{
                        "保留", "供电方式", "编程允许状态", "继电器状态",
                        "保留", "继电器命令状态", "预跳闸报警状态", "电能表类型",
                        "保留", "保留", "保留", "保电状态", "安全认证状态", "本地开户", "远程开户",
                };
                // 需要 bit3 + bit2
                String electricPrviderStr = bits.subSequence(2, 3).toString()+bits.subSequence(1, 2).toString();
                int electricPrvider = Integer.parseInt(electricPrviderStr, 2);
                String[] electricPrviderStrs = new String[]{"主电源", "辅助电源", "电池供电"};
                stringBuilder.append("  供电方式: ").append(electricPrviderStrs[electricPrvider]).append("\n");
                stringBuilder.append("  编程允许状态: ")
                        .append(bitChar[3] == '1' ? "有效" : "无效").append("\n");
                stringBuilder.append("  继电器状态: ")
                        .append(bitChar[4] == '1' ? "通" : "断").append("\n");
                stringBuilder.append("  继电器命令状态: ")
                        .append(bitChar[6] == '1' ? "通" : "断").append("\n");
                stringBuilder.append("  预跳闸报警状态: ")
                        .append(bitChar[7] == '1' ? "有" : "无").append("\n");
                // 需要 bit9 + bit 8
                String meterTypeBitStr = bits.subSequence(9, 10).toString()+bits.subSequence(8, 9).toString();
                int meterType = Integer.parseInt(meterTypeBitStr, 2);
                String[] meterTypeStr = new String[]{"非预付费表", "电量型预付费表", "电费型预付费表"};
                stringBuilder.append("  电能表类型: ").append(meterTypeStr[meterType]).append("\n");
                stringBuilder.append("  保电状态: ")
                        .append(bitChar[12] == '1' ? "保电" : "非保电").append("\n");
                stringBuilder.append("  安全认证状态: ")
                        .append(bitChar[13] == '1' ? "有效" : "失效").append("\n");
                stringBuilder.append("  本地开户: ")
                        .append(bitChar[14] == '1' ? "未开户" : "开户").append("\n");
                stringBuilder.append("  远程开户: ")
                        .append(bitChar[15] == '1' ? "未开户" : "开户").append("\n");


         /*       stringBuilder.append("注1：编程允许状态(bit3)：对于有编程键的电能表，此位为编程允许状态。\n" +
                        "注2：继电器状态(bit4)，指线路实际工作状态，线路处于跳闸状态时此位置1，线路处于导通状态时此位置0。\n" +
                        "注3：继电器远程拉闸命令状态(Bit6)。电能表收到主站跳闸命令时，Bit6置1；电能表跳闸后，该状态仍维持1，直到电能表解除跳闸条件，或收到主站合闸、保电命令时将该位置0。如果电能表处于保电状态时，收到远程跳闸命令，提示“拒绝操作”，该位仍置0。\n" +
                        "注4：预跳闸报警状态(Bit7)是指剩余电量/金额小于等于预置的报警阀值1或电能表收到远程报警命令时，Bit7置1，电能表报警，提示用户购电（或交费）；否则置0。\n" +
                        "注5：电能表类型有非预付费型、电量型预付费和电费型预付费三种。当电能表类型为00时是非预付费型电能表（包括远程费控电能表）；当电能表类型为01时使用电量型预付费电能表；当电能表类型为10时定义为电费型预付费电能表（包括本地费控电能表）。\n" +
                        "注6：Bit0、Bit5、Bit10、Bit11保留，置0。\n");*/

                break;
            case 4:
            case 5:
            case 6:

                String[] words4 = new String[]{
                        "失压", "欠压", "过压", "失流",
                        "过流", "过载", "功率反向", "断相",
                        "断流", "保留", "保留", "保留",
                        "保留", "保留", "保留", "保留",
                };
                if ("0000000000000000".equals(bits)) {
                    stringBuilder.append("  暂无故障\n");
                    break;
                }
                for (int i = 0; i < bitChar.length; i++) {
                    if (!"保留".equals(words4[i]) && bitChar[i] == '1') {
                        stringBuilder/*.append("bit").append(i).append(". ")*/.append("  ").append(words4[i]).append("\n");
                    }
                }

                break;
            case 7:
                String[] word7 = new String[]{
                        "电压逆相序", "电流逆相序",
                        "电压不平衡", "电流不平衡",
                        "辅助电源失电", "掉电",
                        "需量超限", "总功率因数超下限",
                        "电流严重不平衡", "开表盖故障",
                        "开端钮盖故障", "", "", "", "", "",
                };
                if ("0000000000000000".equals(bits)) {
                    stringBuilder.append("  暂无故障\n");
                    break;
                }

                for (int i = 0; i < bitChar.length; i++) {
                    if (!"".equals(word7[i]) && bitChar[i] == '1') {
                        stringBuilder/*.append("bit").append(i).append(". ")*/.append("  ").append(word7[i]).append("\n");
                    }
                }
                break;
            default:
                break;

        }
        return stringBuilder.toString();
    }

    public static final class VariableInner extends Inner {
        /**
         * 换算
         */
        public final Integer pow;
        /**
         * 单位
         */
        public final String unit;

        public VariableInner(String objName, int interfClass, Integer pow, String unit) {
            super(objName, interfClass);
            this.pow = pow;
            this.unit = unit;
        }
    }


}
