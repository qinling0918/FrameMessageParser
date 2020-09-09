package com.sgcc.pda.framelibrary.protocol698.apdu.obj;

import android.support.annotation.NonNull;
import com.sgcc.pda.framelibrary.FormatAble;
import com.sgcc.pda.framelibrary.protocol698.apdu.data.DataManager;
import com.sgcc.pda.framelibrary.protocol698.apdu.data.DateTimeS;
import com.sgcc.pda.framelibrary.utils.NumberConvert;
import com.sgcc.pda.framelibrary.utils.RecoverableString;
import org.jf.util.SparseArray;


import java.util.HashMap;
import java.util.Locale;

/**
 * Created by qinling on 2019/5/13 10:58
 * Description: 参变量
 */
public class ParamsVariable extends Obj {
    private static final String INFO =
            "4000\t8\t日期时间\n" +
                    "4001\t8\t通信地址\n" +
                    "4002\t8\t表号\n" +
                    "4003\t8\t客户编号\n" +
                    "4004\t8\t设备地理位置\n" +
                    "4005\t8\t组地址\n" +
                    "4006\t8\t时钟源\n" +
                    "4007\t8\tLCD参数\n" +
                    "4008\t8\t备用套时区表切换时间\n" +
                    "4009\t8\t备用套日时段切换时间\n" +
                    "400A\t8\t备用套分时费率切换时间\n" +
                    "400B\t8\t备用套阶梯电价切换时间\n" +
                    "400C\t8\t时区时段数\n" +
                    "400D\t8\t阶梯数\n" +
                    "400E\t8\t谐波分析次数\n" +
                    "400F\t8\t密钥总条数\n" +
                    "4010\t8\t计量元件数\n" +
                    "4011\t8\t公共假日表\n" +
                    "4012\t8\t周休日特征字\n" +
                    "4013\t8\t周休日釆用的日时段表号\n" +
                    "4014\t8\t当前套时区表\n" +
                    "4015\t8\t备用套时区表\n" +
                    "4016\t8\t当前套日时段表\n" +
                    "4017\t8\t备用套日时段表\n" +
                    "4018\t8\t当前套费率电价\n" +
                    "4019\t8\t备用套费率电价\n" +
                    "401A\t8\t当前套阶梯电价\n" +
                    "401B\t8\t备用套阶梯电价\n" +
                    "401C\t8\t电流互感器变比\n" +
                    "401D\t8\t电压互感器变比\n" +
                    "401E\t8\t报警金额限值\n" +
                    "401F\t8\t其它金额限值\n" +
                    "4020\t8\t报警电量限值\n" +
                    "4021\t8\t其它电量限值\n" +
                    "4022\t6\t插卡状态字\n" +
                    "4024\t8\t剔除\n" +
                    "4025\t8\t采集器远程升级结果表\n" +
                    "4026\t8\t采集器升级结果\n" +
                    "4030\t8\t电压合格率参数\n" +
                    "4040\t8\tTA 专用模块\n" +
                    "4041\t8\t电流回路监测使能\n" +
                    "4100\t8\t最大需量周期\n" +
                    "4101\t8\t滑差时间\n" +
                    "4102\t8\t校表脉冲宽度\n" +
                    "4103\t8\t资产管理编码\n" +
                    "4104\t8\t额定电压\n" +
                    "4105\t8\t额定电流/基本电流\n" +
                    "4106\t8\t最大电流\n" +
                    "4107\t8\t有功准确度等级\n" +
                    "4108\t8\t无功准确度等级\n" +
                    "4109\t8\t电能表有功常数\n" +
                    "410A\t8\t电能表无功常数\n" +
                    "410B\t8\t电能表型号\n" +
                    "410C\t8\tABC各相电导系数\n" +
                    "410D\t8\tABC各相电抗系数\n" +
                    "410E\t8\tABC各相电阻系数\n" +
                    "410F\t8\tABC各相电纳系数\n" +
                    "4111\t8\t软件备案号\n" +
                    "4112\t8\t有功组合方式特征字\n" +
                    "4113\t8\t无功组合方式1特征字\n" +
                    "4114\t8\t无功组合方式2特征字\n" +
                    "4116\t8\t结算日\n" +
                    "4117\t8\t期间需量冻结周期\n" +
                    "4202\t8\t级联通信参数\n" +
                    "4204\t8\t终端广播校时\n" +
                    "4300\t19\t电气设备\n" +
                    "4307\t19\t水表\n" +
                    "4308\t19\t气表\n" +
                    "4309\t19\t热表\n" +
                    "4310\t19\t锁具设备\n" +
                    "4400\t20\t应用连接\n" +
                    "4401\t8\t认证密码\n" +
                    "4500\t25\t公网通信模块1\n" +
                    "4501\t25\t公网通信模块2\n" +
                    "4510\t26\t以太网通信模块1\n" +
                    "4511\t26\t以太网通信模块2\n" +
                    "4512\t26\t以太网通信模块3\n" +
                    "4513\t26\t以太网通信模块4\n" +
                    "4514\t26\t以太网通信模块5\n" +
                    "4515\t26\t以太网通信模块6\n" +
                    "4516\t26\t以太网通信模块7\n" +
                    "4517\t26\t以太网通信模块8\n" +
                    "4520\t8\t公网远程通信多接入点备用通道\n" +
                    "4800\t11\t电器设备集合\n";

    private static SparseArray<ParamsVariableInner> varInners = new SparseArray<>();

    static {
        String[] vars = INFO.split("\n");
        for (String var : vars) {
            String[] varArr = var.split("\t");
            int objNo = Integer.parseInt(varArr[0], 16);
            int interfClass = Integer.parseInt(varArr[1]);
            String objName = varArr[2];
            ParamsVariableInner variableInner = new ParamsVariableInner(objName, interfClass);
            varInners.put(objNo, variableInner);
        }
    }

    private final int logicNo;
    private final ParamsVariableInner variableInner;
    private final String formatString;

    public static final class ParamsVariableInner extends Inner {
        public ParamsVariableInner(String objName, int interfClass) {
            super(objName, interfClass);
        }
    }

    public ParamsVariable(String oadHexStr, RecoverableString objValueHexStr) {
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
            case 0x4000:
                return new Obj_4000(this).toFormatString();
            case 0x4001:
            case 0x4002:
            case 0x4003:
                return new Obj_String(this).toFormatString();
            case 0x4004:
                return format4004();
            case 0x4005:
                return format4005();
            case 0x4008:
            case 0x4009:
            case 0x400A:
            case 0x400B:
                return new Obj_DateTimeS(this).toFormatString();
            case 0x4007:
            case 0x400C:
            case 0x401E:
            case 0x401F:
            case 0x4020:
            case 0x4021:
            case 0x4030:
            case 0x4041:
            case 0x410C:
            case 0x410D:
            case 0x410E:
            case 0x410F:
                return new Obj_Number_Structure(this).toFormatString();
            case 0x400D:
            case 0x400E:
            case 0x400F:
            case 0x4010:
            case 0x4013:
            case 0x401C:
            case 0x401D:
                return new Obj_Number(this).toFormatString();
            case 0x4100:
            case 0x4101:
                return new Obj_Number(this, "分钟", 0).toFormatString();
            case 0x4102:
                return new Obj_Number(this, "毫秒", 0).toFormatString();
            case 0x4109:
                return new Obj_Number(this, "imp/kWh", 0).toFormatString();
            case 0x410A:
                return new Obj_Number(this, "imp/kvarh", 0).toFormatString();
            case 0x4103:
            case 0x4104:
            case 0x4105:
            case 0x4106:
            case 0x4107:
            case 0x4108:
            case 0x410B:
            case 0x4111:
            case 0x4401:
                return new Obj_VisiableString(this).toFormatString();
            // 公共假日
            case 0x4011:
                return new Obj_4011(this).toFormatString();
            // 当前套时区表
            case 0x4014:
                //备用套时区表
            case 0x4015:
                return new Obj_4014(this).toFormatString();
            case 0x4016:
            case 0x4017:
                return new Obj_4016(this).toFormatString();
            case 0x401A:
            case 0x401B:
                return new Obj_401A(this).toFormatString();
            case 0x4024:
                return format4024();
            case 0x4300:
                return new Obj_4300(this).toFormatString();
            case 0x4012:
            case 0x4022:
            case 0x4112:
            case 0x4113:
            case 0x4114:
                return new Obj_Words(this).toFormatString();
            case 0x4116:
                return format4116();
            case 0x4117:
                return format4117();
            case 0x4202:
                return format4202();
            case 0x4018:
            case 0x4019:
                return format4018();
            default:
                return "暂未完成解析: " + objName + ":" + objValueHexStr.getCurrentSting();
        }
    }

    /**
     * 4000 的解析
     */
    public static final class Obj_4000 implements FormatAble {
        ParamsVariable paramsVariable;
        String formatString;

        Obj_4000(ParamsVariable paramsVariable) {
            this.paramsVariable = paramsVariable;
            this.formatString = format();
        }

        @Override
        public String toFormatString() {
            return formatString;
        }

        private String format() {
            switch (paramsVariable.attributes) {
                // date_time_s
                case 0x02:
                    return String.format(Locale.CHINA, "%s： %s ",
                            paramsVariable.objName,
                            new DateTimeS(paramsVariable.getValueByDataType()).format());
                // 校时模式
                case 0x03:
                    HashMap<Integer, String> modes = new HashMap<>();
                    modes.put(0, "主站授时");
                    modes.put(1, "终端精确校时");
                    modes.put(2, "北斗/GPS");
                    modes.put(0xff, "其它");
                    return String.format(Locale.CHINA, "%s： %s ",
                            paramsVariable.objName + "(校时模式)",
                            modes.get(Integer.parseInt(paramsVariable.getValueByDataType(), 16)));
                case 0x04:
                    String[] attributesArr = new String[]{
                            "最近心跳时间总个数", "最大值剔除个数", "最小值剔除个数", "通讯延时阈值", "最少有效个数"
                    };
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(paramsVariable.objName).append("(精准校时参数) : ");

                    if (paramsVariable.index == 0) {
                        // 此处先删除 标识 5个元素的structure 即 0205 这两个字节
                        paramsVariable.objValueHexStr.substring(4);
                        for (int i = 0; i < 5; i++) {
                            // 每个值 字符串
                            getItemValue(stringBuilder.append(attributesArr[i]), paramsVariable.objValueHexStr, i);
                        }
                        return stringBuilder.toString();
                    } else {
                        getItemValue(stringBuilder.append(attributesArr[paramsVariable.index - 1]), paramsVariable.objValueHexStr, paramsVariable.index - 1);
                        return stringBuilder.toString();
                    }

                default:
                    return paramsVariable.objName + " 暂无法解析";
            }
        }

        private void getItemValue(StringBuilder append, RecoverableString str, int i) {
            String valueStr = paramsVariable.getValueByDataType(str);
            int num = Integer.parseInt(valueStr, 16);
            append
                    .append(" : ")
                    .append(i == 3 ? num : num + "秒").append("\n");
            str.substring(valueStr.length() + 2);
        }
    }

    /**
     * 解析OI 4004 设备地理位置
     *
     * @return 返回4004字符串
     */
    private String format4004() {
        if (attributes != 2) {
            return objName + " ：暂无法解析";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(objName).append(":").append("{").append("\n");
        String classify = objValueHexStr.substring(0, 2);
        if (index == 0) {
            if ("02".equals(classify)) {
                int num = Integer.parseInt(objValueHexStr.substring(0, 2), 16);
                for (int i = 0; i < num; i++) {
                    String innerClassify = objValueHexStr.substring(0, 2);
                    if (i == 0 && "02".equals(innerClassify)) {
                        formatLongitudeStructure(builder);
                    } else if (i == 1 && "02".equals(innerClassify)) {
                        formatLatitudeStructure(builder);
                    } else if (i == 2 && "06".equals(innerClassify)) {
                        formatHeightStructure(builder);
                    }
                }
            } else {
                builder.append("对象有误！");
            }
        } else if (index == 1) {
            formatLongitudeStructure(builder);
        } else if (index == 2) {
            formatLatitudeStructure(builder);
        } else if (index == 3) {
            formatHeightStructure(builder);
        } else {
            builder.append("未知项:").append(objValueHexStr.getCurrentSting());
        }
        builder.append("}").append("\n");
        return builder.toString();
    }

    /**
     * 格式化设备地理位置中 经度
     *
     * @param builder 格式化
     */
    private void formatLongitudeStructure(StringBuilder builder) {
        builder.append("  经度:").append("{").append("\n");
        int num = Integer.parseInt(objValueHexStr.substring(0, 2), 16);
        if (num == 4) {
            int direction = Integer.parseInt(objValueHexStr.substring(2, 4), 16);
            String directionStr = "";
            if (direction == 0) {
                directionStr = "E";
            } else {
                directionStr = "W";
            }
            builder.append("    方位:").append(directionStr).append(",").append("\n");
            int degree = Integer.parseInt(objValueHexStr.substring(2, 4), 16);
            builder.append("    度:").append(degree).append(",").append("\n");
            int minute = Integer.parseInt(objValueHexStr.substring(2, 4), 16);
            builder.append("    分:").append(minute).append(",").append("\n");
            int second = Integer.parseInt(objValueHexStr.substring(2, 4), 16);
            builder.append("    秒:").append(second);
        }
        builder.append("\n").append("  }").append("\n");
    }

    /**
     * 格式化设备地理位置中 纬度
     *
     * @param builder 格式化
     */
    private void formatLatitudeStructure(StringBuilder builder) {
        builder.append("  纬度:").append("{").append("\n");
        int num = Integer.parseInt(objValueHexStr.substring(0, 2), 16);
        if (num == 4) {
            int direction = Integer.parseInt(objValueHexStr.substring(2, 4), 16);
            String directionStr = "";
            if (direction == 0) {
                directionStr = "S";
            } else {
                directionStr = "N";
            }
            builder.append("    方位:").append(directionStr).append(",").append("\n");
            int degree = Integer.parseInt(objValueHexStr.substring(2, 4), 16);
            builder.append("    度:").append(degree).append(",").append("\n");
            int minute = Integer.parseInt(objValueHexStr.substring(2, 4), 16);
            builder.append("    分:").append(minute).append(",").append("\n");
            int second = Integer.parseInt(objValueHexStr.substring(2, 4), 16);
            builder.append("    秒:").append(second);
        }
        builder.append("\n").append("  }").append("\n");
    }

    /**
     * 格式化设备地理位置中 高度
     *
     * @param builder 格式化
     */
    private void formatHeightStructure(StringBuilder builder) {
        builder.append("  高度:");
        long value = Long.parseLong(objValueHexStr.substring(0, 8), 16);
        builder.append(value).append("cm").append("\n");
    }

    /**
     * 解析OI 4005 组地址
     * <p>
     * 属性2∷=array octet-string
     *
     * @return 返回4005字符串
     */
    private String format4005() {
        if (attributes != 2) {
            return objName + " ：暂无法解析";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(objName).append(":").append("[").append("\n");
        String classify = objValueHexStr.substring(0, 2);
        if ("01".equals(classify)) {
            int num = Integer.parseInt(objValueHexStr.substring(0, 2));
            for (int i = 0; i < num; i++) {
                String innerClassify = objValueHexStr.substring(0, 2);
                if ("09".equals(innerClassify)) {
                    formatOnlyAddress(builder);
                } else {
                    builder.append("对象数据有误！")
                            .append(objValueHexStr.getCurrentSting())
                            .append("\n");
                }
            }
        } else if ("09".equals(classify)) {
            formatOnlyAddress(builder);
        } else {
            builder.append("对象有误！")
                    .append(objValueHexStr.getCurrentSting())
                    .append("\n");
        }
        builder.append("]").append("\n");
        return builder.toString();
    }

    /**
     * 格式化单个组地址
     */
    private void formatOnlyAddress(StringBuilder builder) {
        int len = Integer.parseInt(objValueHexStr.substring(0, 2));
        if (len <= 0) {
            builder.append("对象有误！").append(objValueHexStr.getCurrentSting()).append("\n");
        } else {
            String address = objValueHexStr.substring(0, len * 2);
            builder.append("  地址:").append(address).append("\n");
        }
    }

    /**
     * 解析OI 4006 时钟源
     * <p>
     * 属性2（只读）∷=structure
     * {
     * 时钟源  enum
     * {
     * 内部（0），时钟芯片（1），互联网时钟（2），
     * 卫星时钟（3），长波时钟（4）
     * }，
     * 状态  enum{可用（0），不可用（1）}
     * }
     *
     * @return 返回4006字符串
     */
    private String format4006() {
        if (attributes != 2) {
            return objName + " ：暂无法解析";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(objName).append(":").append("[").append("\n");
        String classify = objValueHexStr.substring(0, 2);
        builder.append("]").append("\n");
        return builder.toString();
    }

    // 时间格式
    public static final class Obj_DateTimeS implements FormatAble {
        ParamsVariable paramsVariable;
        String formatString;

        public Obj_DateTimeS(ParamsVariable paramsVariable) {
            this.paramsVariable = paramsVariable;
            this.formatString = format();
        }

        @Override
        public String toFormatString() {
            return formatString;

        }

        private String format() {
            switch (paramsVariable.attributes) {
                case 0x02:
                    return String.format(Locale.CHINA, "%s： %s ", paramsVariable.objName,
                            new DateTimeS(paramsVariable.getValueByDataType()).format());
                default:
                    return paramsVariable.objName + " 暂无法解析";
            }
        }
    }

    // 字符串
    public static final class Obj_Words implements FormatAble {
        String[] word4012 = new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        String[] word4112 = new String[]{"正向有功", "正向有功", "反向有功", "反向有功"};
        String[] word4113_4114 = new String[]{"I象限", "I象限", "II象限", "II象限", "III象限", "III象限", "IV象限", "IV象限"};
        ParamsVariable paramsVariable;
        String formatString;

        public Obj_Words(ParamsVariable paramsVariable) {
            this.paramsVariable = paramsVariable;
            this.formatString = format();
        }

        @Override
        public String toFormatString() {
            return formatString;
        }

        private String format() {
            switch (paramsVariable.attributes) {
                case 0x02:
                    String bits = NumberConvert.hexStrToBinaryStr(paramsVariable.getValueByDataType());
                    char[] bitChar = bits.toCharArray();
                    StringBuilder stringBuilder = new StringBuilder(paramsVariable.objName).append("\n");

                    switch (paramsVariable.logicNo) {
                        case 0x4012:
                            for (int i = 0; i < word4012.length; i++) {
                                stringBuilder.append(word4012[i])
                                        .append(": ")
                                        .append(bitChar[i] == 1 ? "工作" : "休息")
                                        .append("\n");
                            }
                            break;
                        case 0x4112:

                            for (int i = 0; i < word4112.length; i++) {
                                String name = i % 2 == 0 ? "加" : "减";
                                stringBuilder.append(word4112[i])
                                        .append(": ")
                                        .append(bitChar[i] == 1 ? "" : "不")
                                        .append(name)
                                        .append("\n");
                            }
                            break;
                        case 0x4113:
                        case 0x4114:
                            for (int i = 0; i < word4113_4114.length; i++) {
                                String name = i % 2 == 0 ? "加" : "减";
                                stringBuilder.append(word4113_4114[i])
                                        .append(": ")
                                        .append(bitChar[i] == 1 ? "" : "不")
                                        .append(name)
                                        .append("\n");
                            }
                            break;
                        // 插卡状态
                        case 0x4022:
                            // 需要 bit1 + bit 0
                            String statusBitStr = bits.subSequence(1, 2).toString() + bits.subSequence(0, 1).toString();
                            int status = Integer.parseInt(statusBitStr, 2);
                            String[] statusStr = new String[]{"未知", "成功", "失败"};
                            if (status >= 0 && status <= 2) {
                                stringBuilder.append("插卡状态： ").append(statusStr[status]);
                            } else {
                                stringBuilder.append("插卡状态： ").append("未知");
                            }

                            break;
                        default:
                            return paramsVariable.objName + "暂未解析";
                    }

                    return stringBuilder.toString();
                default:
                    return paramsVariable.objName + " 暂无法解析";
            }
        }
    }

    // 字符串
    public static final class Obj_String implements FormatAble {
        ParamsVariable paramsVariable;
        String formatString;

        public Obj_String(ParamsVariable paramsVariable) {
            this.paramsVariable = paramsVariable;
            this.formatString = format();
        }

        @Override
        public String toFormatString() {
            return formatString;
        }

        private String format() {
            switch (paramsVariable.attributes) {
                case 0x02:
                    return String.format(Locale.CHINA, "%s： %s ", paramsVariable.objName, paramsVariable.getValueByDataType());
                default:
                    return paramsVariable.objName + " 暂无法解析";
            }
        }
    }

    // 字符串
    public static final class Obj_VisiableString implements FormatAble {
        ParamsVariable paramsVariable;
        String formatString;

        public Obj_VisiableString(ParamsVariable paramsVariable) {
            this.paramsVariable = paramsVariable;
            this.formatString = format();
        }

        @Override
        public String toFormatString() {
            return formatString;
        }

        private String format() {
            switch (paramsVariable.attributes) {
                case 0x02:
                    String visiableString = NumberConvert.hexStrToAsciiString(paramsVariable.getValueByDataType());
                    return String.format(Locale.CHINA, "%s： %s ", paramsVariable.objName, visiableString);
                default:
                    return paramsVariable.objName + " 暂无法解析";
            }
        }
    }

    public static final class Obj_Number implements FormatAble {
        Obj paramsVariable;
        String formatString;
        String unit;
        int pow;


        public Obj_Number(Obj paramsVariable) {
            this(paramsVariable, "", 0);
        }

        public Obj_Number(Obj paramsVariable, String unit, int pow) {
            this.paramsVariable = paramsVariable;
            this.unit = unit;
            this.pow = pow;
            this.formatString = format();
        }

        @Override
        public String toFormatString() {
            return formatString;
        }

        private String format() {
            switch (paramsVariable.attributes) {
                case 0x02:
                    return String.format(Locale.CHINA, "%s： %s ", paramsVariable.objName,
                            paramsVariable.getFormatValueWithUnit(
                                    Long.parseLong(paramsVariable.getValueByDataType(), 16), unit, pow)
                    );
                default:
                    return paramsVariable.objName + " 暂无法解析";
            }
        }
    }

    public static final class Obj_4300 implements FormatAble {
        ParamsVariable paramsVariable;
        RecoverableString objValueHexStr;
        String formatString;

        public Obj_4300(ParamsVariable paramsVariable) {
            this.paramsVariable = paramsVariable;
            this.objValueHexStr = paramsVariable.objValueHexStr;
            this.formatString = format();
        }

        @Override
        public String toFormatString() {
            return formatString;
        }

        private String format() {
            switch (paramsVariable.attributes) {
                //版本信息∷=structure
                //{
                //厂商代码       visible-string(SIZE (4))，
                //软件版本号     visible-string(SIZE (4))，
                //软件版本日期   visible-string(SIZE (6))，
                //硬件版本号     visible-string(SIZE (4))，
                //硬件版本日期   visible-string(SIZE (6))，
                //厂家扩展信息   visible-string(SIZE (8))
                //}
                case 0x03:
                    //objValueHexStr.substring(4 + 4 + 6 + 4 + 6 + 8);
                    // String[] results = NumberConvert.split(objValueHexStr.getOriginalString(), 4, 4, 6, 4, 6, 8);
                    String[] nameArr = new String[]{
                            "厂商代码", "软件版本号", "软件版本日期", "硬件版本号", "硬件版本日期", "厂家扩展信息"
                    };
                    StringBuilder stringBuilder = new StringBuilder();
                    if (paramsVariable.index == 0) {
                        // 将代表 structure 的第一个字节 02 舍弃，
                        // 或者将代表 array 的第一个字节 01 舍弃，
                        // String string = paramsVariable.objValueHexStr.substring(2);
                        paramsVariable.objValueHexStr.substring(2);
                        // 获取structure 的子元素的个数
                        // 获取array 的子元素的个数
                        int itemCount = Integer.parseInt(paramsVariable.objValueHexStr.substring(0, 2), 16);
                        // 截掉表示 子元素个数的值。
                        //string = string.substring(2);
                        for (int i = 0; i < itemCount; i++) {
                            String value = paramsVariable.getValueByDataType(paramsVariable.objValueHexStr);
                            // 将下一次的其实位置仍从 0开始，将已经取到值的字符串删除。
                            // 此处 value.length() + 2 （一字节的数据类型）
                            //  string = string.substring(value.length() + 2);

                            stringBuilder.append(getItemFormatString(nameArr[i], value));
                        }
                        // 表示直接取值 structure 中的某个元素
                    } else {
                        String value = paramsVariable.getValueByDataType();
                        stringBuilder.append(getItemFormatString(nameArr[paramsVariable.index - 1], value));
                    }

                    return stringBuilder.toString();
                case 0x04:
                    String dataType = objValueHexStr.substring(0, 2);
                    int dataSize = DataManager.getInstance().getDataByteSize(dataType);
                    //  String value = objValueHexStr.substring(2, 2 + dataSize * 2);
                    String value = objValueHexStr.substring(0, dataSize * 2);

                    return String.format(Locale.CHINA, "生产日期： %s ", new DateTimeS(value).format());
                default:
                    return paramsVariable.objName + " 暂无法解析";
            }
        }

        private String getItemFormatString(String name, String value) {
            value = NumberConvert.hexStrToAsciiString(value.trim());
            return new StringBuilder().append(name).append(": ").append(value).append("\n").toString();
        }
    }

    public static final class Obj_Number_Structure implements FormatAble {
        private static final String[] names4007 = new String[]{"上电全显时间(s)", "按键时背光点亮时间(s)", "显示查看背光点亮时间(s)", "无电按键屏幕驻留最大时间(s)", "显示电能小数位数", "显示功率（最大需量）小数位数", "液晶①②字样意义(0显示当前套、备用套时段，1显示当前套、备用套费率)"};
        private static final String[] names400C = new String[]{"年时区数(p≤14)", "日时段表数（q≤8）", "日时段数(每日切换数)（m≤14）", "费率数（k≤63）", "公共假日数（n≤254)"};
        private static final String[] names4018 = new String[]{"费率电价"};
        private static final String[] names4019 = new String[]{"费率电价"};
        private static final String[] names401E = new String[]{"报警金额限值1", "报警金额限值2"};
        private static final String[] names401F = new String[]{"透支金额限值", "囤积金额限值", "合闸允许金额限值"};
        private static final String[] names4020 = new String[]{"报警电量限值1", "报警电量限值2"};
        private static final String[] names4021 = new String[]{"囤积电量限值", "透支电量限值", "合闸允许电量限值"};
        private static final String[] names4030 = new String[]{"电压考核上限", "电压考核下限", "电压合格上限", "电压合格下限"};
        private static final String[] names4041 = new String[]{"A 相使能", "B 相使能", "C 相使能"};
        private static final String[] names410C = new String[]{"A 相电导", "B 相电导", "C 相电导"};
        private static final String[] names410D = new String[]{"A 相电抗", "B 相电抗", "C 相电抗"};
        private static final String[] names410E = new String[]{"A 相电阻", "B 相电阻", "C 相电阻"};
        private static final String[] names410F = new String[]{"A 相电纳", "A 相电纳", "A 相电纳"};
        private static final SparseArray<String[]> names = new SparseArray<>();

        static {
            names.put(0x4007, names4007);
            names.put(0x400C, names400C);
            names.put(0x4019, names4019);
            names.put(0x4018, names4018);
            names.put(0x401E, names401E);
            names.put(0x401F, names401F);
            names.put(0x4020, names4020);
            names.put(0x4021, names4021);
            names.put(0x4030, names4030);
            names.put(0x4041, names4041);
            names.put(0x410C, names410C);
            names.put(0x410D, names410D);
            names.put(0x410E, names410E);
            names.put(0x410F, names410F);
        }

        public static class ScalerUnit {
            public String unit;
            public Integer pow;

            public ScalerUnit(String unit, Integer pow) {
                this.unit = unit;
                this.pow = pow;
            }
        }

        private static final SparseArray<ScalerUnit> scaleUnits = new SparseArray<>();

        static {
            scaleUnits.put(0x4007, new ScalerUnit("", 0));
            scaleUnits.put(0x400C, new ScalerUnit("", 0));
            scaleUnits.put(0x4019, new ScalerUnit("元/kWh", -4));
            scaleUnits.put(0x401E, new ScalerUnit("元", -2));
            scaleUnits.put(0x401F, new ScalerUnit("元", -2));
            scaleUnits.put(0x4020, new ScalerUnit("kWh", -2));
            scaleUnits.put(0x4021, new ScalerUnit("kWh", -2));
            scaleUnits.put(0x4030, new ScalerUnit("V", -1));
            scaleUnits.put(0x4041, new ScalerUnit("", 0));
            scaleUnits.put(0x410C, new ScalerUnit("", -3));
            scaleUnits.put(0x410D, new ScalerUnit("", -3));
            scaleUnits.put(0x410E, new ScalerUnit("", -3));
            scaleUnits.put(0x410F, new ScalerUnit("", -3));
        }

        ParamsVariable paramsVariable;
        String formatString;

        public Obj_Number_Structure(ParamsVariable paramsVariable) {
            this.paramsVariable = paramsVariable;
            this.formatString = format();
        }

        @Override
        public String toFormatString() {
            return formatString;
        }

        @NonNull
        private String format() {
            switch (paramsVariable.attributes) {
                case 0x02:
                    String[] nameArr = names.get(paramsVariable.logicNo);
                    ScalerUnit scalerUnit = scaleUnits.get(paramsVariable.logicNo);
                    StringBuilder stringBuilder = new StringBuilder();
                    if (paramsVariable.index == 0) {
                        // 将代表 structure 的第一个字节 02 舍弃，
                        // 或者将代表 array 的第一个字节 01 舍弃，
                        // String string = paramsVariable.objValueHexStr.substring(2);
                        paramsVariable.objValueHexStr.substring(2);
                        // 获取structure 的子元素的个数
                        // 获取array 的子元素的个数
                        int itemCount = Integer.parseInt(paramsVariable.objValueHexStr.substring(0, 2), 16);
                        // 截掉表示 子元素个数的值。
                        //string = string.substring(2);
                        for (int i = 0; i < itemCount; i++) {
                            String value = paramsVariable.getValueByDataType(paramsVariable.objValueHexStr);
                            // 将下一次的其实位置仍从 0开始，将已经取到值的字符串删除。
                            // 此处 value.length() + 2 （一字节的数据类型）
                            //  string = string.substring(value.length() + 2);

                            getItemFormatString(stringBuilder.append(nameArr[i]), scalerUnit, value);
                        }
                        // 表示直接取值 structure 中的某个元素
                    } else {
                        String value = paramsVariable.getValueByDataType();
                        getItemFormatString(stringBuilder.append(nameArr[paramsVariable.index - 1]), scalerUnit, value);
                    }

                    return stringBuilder.toString();
                default:
                    return paramsVariable.objName + " 暂无法解析";
            }
        }

        private void getItemFormatString(StringBuilder append, ScalerUnit scalerUnit, String value) {
            long num = Long.parseLong(value, 16);
            append
                    .append(": ")
                    .append(paramsVariable.logicNo == 0x4041
                            ? (num == 0 ? "0（失败）" : "1（成功）")
                            : paramsVariable.getFormatValueWithUnit(num, scalerUnit.unit, scalerUnit.pow))
                    .append("\n");
        }
    }

    public static final class Obj_4014 implements FormatAble {
        ParamsVariable paramsVariable;
        String formatString;

        public Obj_4014(ParamsVariable paramsVariable) {
            this.paramsVariable = paramsVariable;
            this.formatString = format();
        }

        public static final class TimeZone implements FormatAble {
            private int month;
            private int day;
            private int dayTimeZoneNo;
            public static final int byteSize = 8;

            public TimeZone(String value) {
                // 020311XX11XX11XX
                this.month = Integer.parseInt(value.substring(6, 8), 16);
                this.day = Integer.parseInt(value.substring(10, 12), 16);
                this.dayTimeZoneNo = Integer.parseInt(value.substring(14, 16), 16);
            }

            @Override
            public String toFormatString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("月: ").append(month).append(", ")
                        .append("日: ").append(day).append(",")
                        .append("日时段表号: ").append(dayTimeZoneNo).append(".");
                return stringBuilder.toString();
            }
        }

        @Override
        public String toFormatString() {
            return formatString;
        }

        @NonNull
        private String format() {
            switch (paramsVariable.attributes) {
                case 0x02:
                    StringBuilder stringBuilder = new StringBuilder(paramsVariable.objName).append(":\n");
                    if (paramsVariable.index == 0) {

                        int itemCount = Integer.parseInt(paramsVariable.objValueHexStr.substring(2, 4), 16);
                        // String string = paramsVariable.objValueHexStr.substring(4);
                        String string = paramsVariable.objValueHexStr.getCurrentSting();
                        // TimeZone[] timeZones = new TimeZone[itemCount];
                        for (int i = 0; i < itemCount; i++) {
                            getTimeZoneFormatString(stringBuilder, new TimeZone(string), i);
                            // string = string.substring(TimeZone.byteSize * 2);
                            paramsVariable.objValueHexStr.substring(TimeZone.byteSize * 2);
                        }
                    } else {
                        String string = paramsVariable.objValueHexStr.getCurrentSting();
                        getTimeZoneFormatString(stringBuilder, new TimeZone(string), paramsVariable.index);

                    }
                    return stringBuilder.toString();
                default:
                    return paramsVariable.objName + " 暂无法解析";
            }
        }

        private void getTimeZoneFormatString(StringBuilder stringBuilder, TimeZone timeZone, int i) {
            stringBuilder
                    .append("第").append(i + 1)
                    .append("时区（ ")
                    .append(timeZone.toFormatString())
                    .append(")\n");
        }
    }

    public static final class Obj_4011 implements FormatAble {
        Obj paramsVariable;
        String formatString;

        public Obj_4011(Obj paramsVariable) {
            this.paramsVariable = paramsVariable;
            this.formatString = format();
        }

        public static final class PublicDate implements FormatAble {
            private int year;
            private int month;
            private int day_of_month;
            private int day_of_week;
            private int rateNo;
            public static final int byteSize = 10;

            public PublicDate(String value) {
                // 02021AXXXX XX XX XX11XX
                this.year = Integer.parseInt(value.substring(6, 10), 16);
                this.month = Integer.parseInt(value.substring(10, 12), 16);
                this.day_of_month = Integer.parseInt(value.substring(12, 14), 16);
                this.day_of_week = Integer.parseInt(value.substring(14, 16), 16);
                this.rateNo = Integer.parseInt(value.substring(18, 20), 16);
            }

            @Override
            public String toFormatString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("日期: ").append(year).append("年")
                        .append(month).append("月").append(day_of_month).append("日").append(";")
                        .append("日时段表号: ").append(rateNo).append(".");
                return stringBuilder.toString();
            }
        }

        @Override
        public String toFormatString() {
            return formatString;

        }

        @NonNull
        private String format() {
            /**
             * 属性2∷=array 公共假日
             公共假日∷=structure
             {
             日期        date，
             日时段表号  unsigned
             }
             */
            switch (paramsVariable.attributes) {
                case 0x02:

                    // 0102 0108 0203  ****
                    StringBuilder stringBuilder = new StringBuilder(paramsVariable.objName).append(":\n");
                    if (paramsVariable.index == 0) {
                        // 日时段表的个数
                        int itemCountDay = Integer.parseInt(paramsVariable.objValueHexStr.substring(2, 4), 16);
                        for (int i = 0; i < itemCountDay; i++) {
                            // 时段的元素个数
                            stringBuilder.append(new PublicDate(paramsVariable.objValueHexStr.getCurrentSting()).toFormatString());
                            paramsVariable.objValueHexStr.substring(PublicDate.byteSize * 2);
                        }

                        // TimeZone[] timeZones = new TimeZone[itemCount];

                    } else {
                        stringBuilder.append(new PublicDate(paramsVariable.objValueHexStr.getCurrentSting()).toFormatString());
                        paramsVariable.objValueHexStr.substring(PublicDate.byteSize * 2);
                    }
                    return stringBuilder.toString();
                default:
                    return paramsVariable.objName + " 暂无法解析";
            }
        }
    }

    public static final class Obj_4016 implements FormatAble {
        Obj paramsVariable;
        String formatString;

        public Obj_4016(Obj paramsVariable) {
            this.paramsVariable = paramsVariable;
            this.formatString = format();
        }

        public static final class TimeZone implements FormatAble {
            private int hour;
            private int minute;
            private int rateNo;
            public static final int byteSize = 8;

            public TimeZone(String value) {
                // 020311XX11XX11XX
                this.hour = Integer.parseInt(value.substring(6, 8), 16);
                this.minute = Integer.parseInt(value.substring(10, 12), 16);
                this.rateNo = Integer.parseInt(value.substring(14, 16), 16);
            }

            @Override
            public String toFormatString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("时: ").append(hour).append(", ")
                        .append("分: ").append(minute).append(",")
                        .append("费率号: ").append(rateNo).append(".");
                return stringBuilder.toString();
            }
        }

        @Override
        public String toFormatString() {
            return formatString;

        }

        @NonNull
        private String format() {
            /**
             * 属性2∷=array 日时段表
             日时段表∷=array 时段
             时段∷=structure
             {
             时     unsigned，
             分     unsigned，
             费率号 unsigned
             }
             */
            switch (paramsVariable.attributes) {
                case 0x02:

                    // 0102 0108 0203  ****
                    StringBuilder stringBuilder = new StringBuilder(paramsVariable.objName).append(":\n");
                    if (paramsVariable.index == 0) {
                        // 日时段表的个数
                        int itemCountDay = Integer.parseInt(paramsVariable.objValueHexStr.substring(2, 4), 16);
                        for (int i = 0; i < itemCountDay; i++) {
                            // 时段的元素个数
                            int itemCount = Integer.parseInt(paramsVariable.objValueHexStr.substring(2, 4), 16);
                            getItemFormatString(stringBuilder, itemCount, paramsVariable.objValueHexStr, i);
                        }

                        // TimeZone[] timeZones = new TimeZone[itemCount];

                    } else {
                        int itemCount = Integer.parseInt(paramsVariable.objValueHexStr.substring(2, 4), 16);
                        getItemFormatString(stringBuilder, itemCount, paramsVariable.objValueHexStr, paramsVariable.index);

                    }
                    return stringBuilder.toString();
                default:
                    return paramsVariable.objName + " 暂无法解析";
            }
        }

        private void getItemFormatString(StringBuilder stringBuilder, int itemCount, RecoverableString string, int index) {
            stringBuilder.append("第").append(index).append("日时段表： \n");
            for (int i = 0; i < itemCount; i++) {
                stringBuilder.append("  ").append(getTimeZoneFormatString(new TimeZone(string.getCurrentSting()), i)).append("\n");
                string.substring(TimeZone.byteSize * 2);
            }
        }

        private String getTimeZoneFormatString(TimeZone timeZone, int i) {
            StringBuilder stringBuilder = new StringBuilder();
            return stringBuilder
                    .append("第").append(i + 1)
                    .append("时段（ ")
                    .append(timeZone.toFormatString())
                    .append(")").toString();
        }
    }

    public static final class Obj_401A implements FormatAble {
        ParamsVariable paramsVariable;
        RecoverableString valueString;
        // 阶梯值
        String[] ladderValues;
        // 阶梯电价
        String[] ladderPrices;
        // 结算日
        SettlementDate[] settlementDates;

        public Obj_401A(ParamsVariable paramsVariable) {

            this.paramsVariable = paramsVariable;
            this.valueString = paramsVariable.objValueHexStr;
            // 0203010606000000030600000007060000000B060000000F060000001306000000170107...
            if (paramsVariable.index == 0) {
                // 去除 0203 标识三个元素的structure ，紧接着 0106
                paramsVariable.objValueHexStr.substring(4);
                // 0106 06000000030600000007060000000B060000000F06000000130600000017... 4+ 6*5*2
                // 获取第一截 字符串长度 ，因为数据类型为 double-long-unsigned 是四个字节，加上前面的数据标识 为5个字节
                ladderValues = initLadderValues(paramsVariable.objValueHexStr);

                ladderPrices = initLadderPrices(paramsVariable.objValueHexStr);
                settlementDates = initSettlementDates(paramsVariable.objValueHexStr);

                // 0106****
            } else if (paramsVariable.index == 1) {
                ladderValues = initLadderValues(paramsVariable.objValueHexStr);
            } else if (paramsVariable.index == 2) {
                ladderPrices = initLadderPrices(paramsVariable.objValueHexStr);
            } else if (paramsVariable.index == 3) {
                settlementDates = initSettlementDates(paramsVariable.objValueHexStr);
            }

        }

        private String[] initLadderValues(RecoverableString objValueHexStr) {
            return initLadderInfo(objValueHexStr, "kWh", -2);
        }

        private String[] initLadderPrices(RecoverableString objValueHexStr) {
            return initLadderInfo(objValueHexStr, "元/kWh", -4);
        }

        private String[] initLadderInfo(RecoverableString valueString, String unit, int pow) {
            int itemCount = Integer.parseInt(valueString.substring(2, 4), 16);
            // valueString.substring(4);
            String[] ladderValues = new String[itemCount];
            for (int i = 0; i < itemCount; i++) {
                long num = Long.parseLong(paramsVariable.getValueByDataType(valueString), 16);
                ladderValues[i] = paramsVariable.getFormatValueWithUnit(num, unit, pow);
                // double-long-unsigned 是四个字节，加上前面的数据标识 为5个字节
                // valueString.substring(10);
            }
            return ladderValues;
        }

        private SettlementDate[] initSettlementDates(RecoverableString valueString) {
            int itemCount = Integer.parseInt(valueString.substring(2, 4), 16);
            // valueString.substring(4);
            SettlementDate[] settlementDates = new SettlementDate[itemCount];
            for (int i = 0; i < itemCount; i++) {
                settlementDates[i] = new SettlementDate(valueString.getCurrentSting());
                // double-long-unsigned 是四个字节，加上前面的数据标识 为5个字节
                //string = string.substring(SettlementDate.byteSize * 2);
                valueString.substring(SettlementDate.byteSize * 2);
            }
            return settlementDates;
        }

        public static final class SettlementDate implements FormatAble {
            private int month;
            private int day;
            private int hour;
            public static final int byteSize = 8;

            public SettlementDate(String value) {
                // 020311XX11XX11XX
                this.month = Integer.parseInt(value.substring(6, 8), 16);
                this.day = Integer.parseInt(value.substring(10, 12), 16);
                this.hour = Integer.parseInt(value.substring(14, 16), 16);
            }

            @Override
            public String toFormatString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("月: ").append(month).append(", ")
                        .append("日: ").append(day).append(",")
                        .append("时: ").append(hour).append(".");
                return stringBuilder.toString();
            }
        }

        @Override
        public String toFormatString() {
            /**
             * 属性2∷=array 日时段表
             日时段表∷=array 时段
             时段∷=structure
             {
             时     unsigned，
             分     unsigned，
             费率号 unsigned
             }
             */
            switch (paramsVariable.attributes) {
                case 0x02:
                    // 0102 0108 0203  ****
                    StringBuilder stringBuilder = new StringBuilder(paramsVariable.objName).append(":\n");
                    stringBuilder.append(getLadderValuesFormat()).append(getLadderPricesFormat()).append(getSettlementDatesFormat());
                    return stringBuilder.toString();
                default:
                    return paramsVariable.objName + " 暂无法解析";
            }
        }

        private String getLadderValuesFormat() {
            if (ladderValues == null || ladderValues.length == 0) {
                return "";
            }
            StringBuilder stringBuilder = new StringBuilder(" 阶梯值数组: \n");
            for (int i = 0; i < ladderValues.length; i++) {
                stringBuilder.append("  ").append(ladderValues[i]).append("\n");
            }
            return stringBuilder.toString();
        }

        private String getLadderPricesFormat() {
            if (ladderPrices == null || ladderPrices.length == 0) {
                return "";
            }
            StringBuilder stringBuilder = new StringBuilder(" 阶梯电价数组: \n");
            for (int i = 0; i < ladderPrices.length; i++) {
                stringBuilder.append("  ").append(ladderPrices[i]).append("\n");
            }
            return stringBuilder.toString();
        }

        private String getSettlementDatesFormat() {
            if (settlementDates == null || settlementDates.length == 0) {
                return "";
            }
            StringBuilder stringBuilder = new StringBuilder(" 阶梯结算日数组: \n");
            for (int i = 0; i < settlementDates.length; i++) {
                stringBuilder.append("  ").append(settlementDates[i].toFormatString()).append("\n");

            }
            return stringBuilder.toString();
        }
    }

    /**
     * 解析OI 4116 结算日
     * <p>
     * 属性2（配置参数）∷=array 结算日日期
     * 结算日日期∷=structure
     * {
     * 日  unsigned，
     * 时  unsigned
     * }
     */
    private String format4116() {
        if (attributes != 2) {
            return objName + " ：暂无法解析";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(objName).append(":{").append("\n");
        //数据类型
        String classify = objValueHexStr.substring(2, 4);
        if ("01".equals(classify)) {
            //array中数据的数量
            int num = Integer.parseInt(objValueHexStr.substring(0, 2), 16);
            for (int i = 0; i < num; i++) {
                String innerClassify = objValueHexStr.substring(0, 2);
                if (!"02".equals(innerClassify)) {
                    builder.append("数据类型有误！");
                    break;
                }
                formatSettlementDateStructure(builder);
                builder.append("\n");
            }
        } else if ("02".equals(classify)) {
            formatSettlementDateStructure(builder);
        } else {
            builder.append(objName)
                    .append(" ：数据类型有误 (")
                    .append(objValueHexStr.getCurrentSting())
                    .append(")")
                    .append("\n");
        }
        builder.append("}").append("\n");
        return builder.toString();
    }

    /**
     * 格式化4116 结算日日期Structure
     *
     * @return 结算日日期Structure字符串
     */
    private void formatSettlementDateStructure(StringBuilder builder) {
        int len = Integer.parseInt(objValueHexStr.substring(0, 2), 16);
        builder.append("结算日日期:[");
        for (int i = 0; i < len; i++) {
            if (i == 0) {
                builder.append("日:");
            } else if (i == 1) {
                builder.append("时:");
            } else {
                builder.append("未知:");
            }
            String typeOfData = objValueHexStr.substring(0, 2);
            int lenOfData = DataManager.getInstance().getDataByteSize(typeOfData);
            int value = Integer.parseInt(objValueHexStr.substring(0, lenOfData * 2), 16);
            builder.append(value);
            if (i != len - 1) {
                builder.append(",");
            }
        }

        builder.append("]");
    }

    /**
     * 解析OI 4117 期间需量冻结周期
     * <p>
     * 配置参数 TI 0x54 参数类型
     * TI∷=SEQUENCE
     * {
     * 单位  ENUMERATED
     * {
     * 秒      （0），
     * 分      （1），
     * 时      （2），
     * 日      （3），
     * 月      （4），
     * 年      （5）
     * }，
     * 间隔值  long-unsigned
     * }
     * <p>
     * 54030002
     */
    private String format4117() {
        if (attributes != 2) {
            return objName + " ：暂无法解析";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(objName).append(":{").append("\n");
        String classify = objValueHexStr.substring(2, 4);
        // 0x54 TI数据类型 84
        if ("54".equals(classify)) {
            int unit = Integer.parseInt(objValueHexStr.substring(0, 2), 16);
            long value = Long.parseLong(objValueHexStr.substring(0, 4), 16);
            builder.append("  单位:").append(getUnit(unit)).append("\n")
                    .append("  间隔值:").append(value).append("\n");

        } else {
            builder.append(objName)
                    .append(" ：数据类型有误 (")
                    .append(objValueHexStr.getCurrentSting())
                    .append(")")
                    .append("\n");
        }

        builder.append("}").append("\n");
        return builder.toString();
    }

    /**
     * 获取4117 单位
     *
     * @param unit 单位对应值
     * @return 单位
     */
    private String getUnit(int unit) {
        String strUnit = "";
        switch (unit) {
            default:
            case 0:
                strUnit = "秒";
                break;
            case 1:
                strUnit = "分";
                break;
            case 2:
                strUnit = "时";
                break;
            case 3:
                strUnit = "日";
                break;
            case 4:
                strUnit = "月";
                break;
            case 5:
                strUnit = "年";
                break;
        }
        return strUnit;
    }

    /**
     * 解析OI 4024 剔除
     * <p>
     * 属性2∷=enum {剔除投入（1），剔除解除（2）}
     *
     * @return 4024字符串
     */
    private String format4024() {
        if (attributes != 2) {
            return objName + " ：暂无法解析";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(objName).append(":{").append("\n");
        String classify = objValueHexStr.substring(2, 4);
        // 0x16 是enum数据类型 既是22
        if (!"16".equals(classify)) {
            builder.append("数据类型错误！").append("\n");
        } else {
            int value = Integer.parseInt(objValueHexStr.substring(0, 2), 16);
            String strExclude = "未知";
            if (value == 1) {
                strExclude = "剔除投入";
            } else if (value == 2) {
                strExclude = "剔除解除";
            }
            builder.append(strExclude).append("\n");
        }
        builder.append("}").append("\n");
        return builder.toString();
    }

    /**
     * 解析OI 4202 级联通信参数
     * <p>
     * 属性2∷=structure
     * {
     * 级联标志            bool，
     * 级联通信端口号      OAD，
     * 总等待超时（10ms）  long-unsigned，
     * 字节超时（10ms）    long-unsigned，
     * 重发次数            unsigned，
     * 巡测周期（min）     unsigned，
     * 级联（被）端口数    unsigned，
     * 级联（被）终端地址  array TSA
     * }
     *
     * @return 4202字符串
     */
    private String format4202() {
        // structure 包含8个参数
        final int numOfParams = 8;
        if (attributes != 2) {
            return objName + " ：暂无法解析";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(objName).append(":{").append("\n");
        String classify = objValueHexStr.substring(2, 4);
        if (!"02".equals(classify)) {
            builder.append("  对象错误!").append("\n");
        } else {
            int len = Integer.parseInt(objValueHexStr.substring(0, 2), 16);
            if (len != numOfParams) {
                builder.append("  structure 参数个数不正确！").append("\n");
            } else {
                int paramsLen = DataManager.getInstance().getDataByteSize(objValueHexStr.substring(0, 2));
                builder.append("  级联标志:")
                        .append(getNum(objValueHexStr.substring(0, paramsLen * 2)))
                        .append(",").append("\n");
                paramsLen = DataManager.getInstance().getDataByteSize(objValueHexStr.substring(0, 2));
                builder.append("  级联通信端口:")
                        .append(objValueHexStr.substring(0, paramsLen * 2))
                        .append(",").append("\n");
                paramsLen = DataManager.getInstance().getDataByteSize(objValueHexStr.substring(0, 2));
                builder.append("  总等待超时（10ms）：")
                        .append(getNum(objValueHexStr.substring(0, paramsLen * 2)))
                        .append(",").append("\n");
                paramsLen = DataManager.getInstance().getDataByteSize(objValueHexStr.substring(0, 2));
                builder.append("  字节超时（10ms）：")
                        .append(getNum(objValueHexStr.substring(0, paramsLen * 2)))
                        .append(",").append("\n");
                paramsLen = DataManager.getInstance().getDataByteSize(objValueHexStr.substring(0, 2));
                builder.append("  重发次数：")
                        .append(getNum(objValueHexStr.substring(0, paramsLen * 2)))
                        .append(",").append("\n");
                paramsLen = DataManager.getInstance().getDataByteSize(objValueHexStr.substring(0, 2));
                builder.append("  巡测周期（min）：")
                        .append(getNum(objValueHexStr.substring(0, paramsLen * 2)))
                        .append("min")
                        .append(",").append("\n");
                paramsLen = DataManager.getInstance().getDataByteSize(objValueHexStr.substring(0, 2));
                builder.append("  级联（被）端口数：")
                        .append(getNum(objValueHexStr.substring(0, paramsLen * 2)))
                        .append(",").append("\n");
                String innerClassify = objValueHexStr.substring(0, 2);
                builder.append("  级联（被）终端地址：");
                if (!"01".equals(innerClassify)) {
                    builder.append("参数类型错误！").append("\n");
                } else {
                    builder.append("[").append("\n");
                    int num = Integer.parseInt(objValueHexStr.substring(0, 2), 16);
                    for (int i = 0; i < num; i++) {
                        builder.append("    地址:");
                        innerClassify = objValueHexStr.substring(0, 2);
                        if (!"55".equals(innerClassify)) {
                            builder.append("数据不正确！").append("\n");
                            break;
                        } else {
                            paramsLen = Integer.parseInt(objValueHexStr.substring(0, 2), 16);
                            builder.append(objValueHexStr.substring(0, paramsLen * 2));
                        }
                        if (i != num - 1) {
                            builder.append(",");
                        }
                        builder.append("\n");
                    }
                    builder.append("  ]").append("\n");
                }
            }
        }
        builder.append("}").append("\n");
        return builder.toString();
    }

    /**
     * 解析4018 当前套费率电价和4019 备用套费率电价
     * <p>
     * 属性2（只读）∷=array 费率电价
     * 费率电价∷=double-long-unsigned
     * 单位：元/kWh，换算：-4
     *
     * @return 字符串
     */
    private String format4018() {
        if (attributes != 2) {
            return objName + " ：暂无法解析";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(objName).append(":[").append("\n");
        //数据类型
        String classify = objValueHexStr.substring(2, 4);
        if ("01".equals(classify)) {
            //array中数据的数量
            int num = Integer.parseInt(objValueHexStr.substring(0, 2), 16);
            for (int i = 0; i < num; i++) {
                String innerClassify = objValueHexStr.substring(0, 2);
                if (!"06".equals(innerClassify)) {
                    builder.append("数据类型有误！");
                    break;
                }
                int valueLen = DataManager.getInstance().getDataByteSize(innerClassify);
                int value = Integer.parseInt(objValueHexStr.substring(0, valueLen * 2), 16);
                builder.append("  费率电价：")
                        .append(getFormatValueWithUnit(value, "元/kWh", -4));
                if (i != num - 1) {
                    builder.append(",");
                }
                builder.append("\n");
            }
        } else {
            builder.append(objName)
                    .append(" ：数据类型有误 (")
                    .append(objValueHexStr.getCurrentSting())
                    .append(")")
                    .append("\n");
        }
        builder.append("]").append("\n");
        return builder.toString();
    }

    /**
     * 解析4016 当前套日时段表和4017 备用套日时段表
     * <p>
     * 属性2∷=array 日时段表
     * 日时段表∷=array 时段
     * 时段∷=structure
     * {
     * 时     unsigned，
     * 分     unsigned，
     * 费率号 unsigned
     * }
     * 费率号：该时段采用的费率号。
     *
     * @return 字符串
     */
    private String format4016() {
        if (attributes != 2) {
            return objName + " ：暂无法解析";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(objName).append(":[").append("\n");
        //数据类型
        String classify = objValueHexStr.substring(2, 4);
        if ("01".equals(classify)) {
            //array中数据的数量
            int num = Integer.parseInt(objValueHexStr.substring(0, 2), 16);
            for (int i = 0; i < num; i++) {
                String innerClassify = objValueHexStr.substring(0, 2);
                if ("01".equals(innerClassify)) {
                    int innerNum = Integer.parseInt(objValueHexStr.substring(0, 2), 16);
                    for (int j = 0; j < innerNum; j++) {
                        String innerClass = objValueHexStr.substring(0, 2);
                        if (!"02".equals(innerClass)) {
                            builder.append("数据类型有误！");
                            break;
                        }
                        format4016Structure(builder);
                    }
                } else if ("02".equals(innerClassify)) {
                    format4016Structure(builder);
                } else {
                    builder.append("数据类型有误！");
                    break;
                }
            }
        } else if ("02".equals(classify)) {
            format4016Structure(builder);
        } else {
            builder.append(objName)
                    .append(" ：数据类型有误 (")
                    .append(objValueHexStr.getCurrentSting())
                    .append(")")
                    .append("\n");
        }
        builder.append("]").append("\n");
        return builder.toString();
    }

    /**
     * 解析4016和4017中的structure
     * <p>
     * 时段∷=structure
     * {
     * 时     unsigned，
     * 分     unsigned，
     * 费率号 unsigned
     * }
     */
    private void format4016Structure(StringBuilder builder) {
        builder.append("  时段:{").append("\n");
        int len = Integer.parseInt(objValueHexStr.substring(0, 2), 16);
        if (len != 3) {
            builder.append("属性个数错误！").append("\n");
        } else {
            String classify = objValueHexStr.substring(0, 2);
            int valueLen = DataManager.getInstance().getDataByteSize(classify);
            builder.append("    时:").append(valueLen).append("h").append("\n");
            classify = objValueHexStr.substring(0, 2);
            valueLen = DataManager.getInstance().getDataByteSize(classify);
            builder.append("    分:").append(valueLen).append("m").append("\n");
            classify = objValueHexStr.substring(0, 2);
            valueLen = DataManager.getInstance().getDataByteSize(classify);
            builder.append("    费率号:").append(valueLen).append("\n");
        }
        builder.append("  }").append("\n");
    }
}
