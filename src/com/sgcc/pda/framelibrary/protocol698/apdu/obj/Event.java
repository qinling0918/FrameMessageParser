package com.sgcc.pda.framelibrary.protocol698.apdu.obj;

import com.sgcc.pda.framelibrary.FormatAble;
import com.sgcc.pda.framelibrary.protocol698.apdu.OIManager;
import com.sgcc.pda.framelibrary.protocol698.apdu.data.DateTimeS;
import com.sgcc.pda.framelibrary.utils.NumberConvert;
import com.sgcc.pda.framelibrary.utils.RecoverableString;
import com.sgcc.pda.framelibrary.utils.TextUtils;

import org.jf.util.SparseArray;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by qinling on 2019/5/17 17:20
 * Description: 事件类
 */
public class Event extends Obj {
    public static final String INFO =
            "3000\t24\t电能表失压事件\t \n" +
                    "3001\t24\t电能表欠压事件\t \n" +
                    "3002\t24\t电能表过压事件\t \n" +
                    "3003\t24\t电能表断相事件\t \n" +
                    "3004\t24\t电能表失流事件\t \n" +
                    "3005\t24\t电能表过流事件\t \n" +
                    "3006\t24\t电能表断流事件\t \n" +
                    "3007\t24\t电能表功率反向事件\t \n" +
                    "3008\t24\t电能表过载事件\t \n" +
                    "3009\t7\t电能表正向有功需量超限事件\t00\n" +
                    "300A\t7\t电能表反向有功需量超限事件\t00\n" +
                    "300B\t24\t电能表无功需量超限事件\t \n" +
                    "300C\t7\t电能表功率因数超下限事件\t00\n" +
                    "300D\t7\t电能表全失压事件\t00\n" +
                    "300E\t7\t电能表辅助电源掉电事件\t00\n" +
                    "300F\t7\t电能表电压逆相序事件\t00\n" +
                    "3010\t7\t电能表电流逆相序事件\t00\n" +
                    "3011\t7\t电能表掉电事件\t00\n" +
                    "3012\t7\t电能表编程事件\t00\n" + // 编程记录事件单元
                    "3013\t7\t电能表清零事件\t00\n" +
                    "3014\t7\t电能表需量清零事件\t00\n" +
                    "3015\t7\t电能表事件清零事件\t00\n" +
                    "3016\t7\t电能表校时事件\t00\n" +
                    "3017\t7\t电能表时段表编程事件\t00\n" +
                    "3018\t7\t电能表时区表编程事件\t00\n" +
                    "3019\t7\t电能表周休日编程事件\t00\n" +
                    "301A\t7\t电能表结算日编程事件\t00\n" +
                    "301B\t7\t电能表开盖事件\t00\n" +
                    "301C\t7\t电能表开端钮盒事件\t00\n" +
                    "301D\t7\t电能表电压不平衡事件\t00\n" +
                    "301E\t7\t电能表电流不平衡事件\t00\n" +
                    "301F\t7\t电能表跳闸事件\t00\n" +
                    "3020\t7\t电能表合闸事件\t00\n" +
                    "3021\t7\t电能表节假日编程事件\t00\n" +
                    "3022\t7\t电能表有功组合方式编程事件\t00\n" +
                    "3023\t7\t电能表无功组合方式编程事件\t16\n" + // 0X16 == 22  ENUM  无功组合方式1特征字（0），无功组合方式2特征字（1）
                    "3024\t7\t电能表费率参数表编程事件\t00\n" +
                    "3025\t7\t电能表阶梯表编程事件\t00\n" +
                    "3026\t7\t电能表密钥更新事件\t00\n" +
                    "3027\t7\t电能表异常插卡事件\t00\n" +
                    "3028\t7\t电能表购电记录\t00\n" +
                    "3029\t7\t电能表退费记录\t00\n" +
                    "302A\t7\t电能表恒定磁场干扰事件\t00\n" +
                    "302B\t7\t电能表负荷开关误动作事件\t00\n" +
                    "302C\t7\t电能表电源异常事件\t00\n" +
                    "302D\t7\t电能表电流严重不平衡事件\t00\n" +
                    "302E\t7\t电能表时钟故障事件\t00\n" +
                    "302F\t7\t电能表计量芯片故障事件\t00\n" +
                    "3030\t7\t通信模块变更事件\t51\n" + // 0X51 --- 81  == oad
                    "3100\t7\t终端初始化事件\t00\n" +
                    "3101\t7\t终端版本变更事件\t00\n" +
                    "3104\t7\t终端状态量变位事件\t00\n" +
                    "3105\t7\t电能表时钟超差事件\t55\n" + // 0x55  --- 85  TSA
                    "3106\t7\t终端停/上电事件\t00\n" +
                    "3107\t7\t终端直流模拟量越上限事件\t51\n" +
                    "3108\t7\t终端直流模拟量越下限事件\t51\n" +
                    "3109\t7\t终端消息认证错误事件\t00\n" +
                    "310A\t7\t设备故障记录\t16\n" +  // 事件发生源∷=enum{终端主板内存故障（0），时钟故障(1),主板通信故障（2），485抄表故障 （3），显示板故障（4），载波通道异常 （5）内卡初始化错误  （6），ESAM错误        （7）}
                    "310B\t7\t电能表示度下降事件\t55\n" +
                    "310C\t7\t电能量超差事件\t55\n" +
                    "310D\t7\t电能表飞走事件\t55\n" +
                    "310E\t7\t电能表停走事件\t55\n" +
                    "310F\t7\t终端抄表失败事件\t55\n" +
                    "3110\t7\t月通信流量超限事件\t55\n" +
                    "3111\t7\t发现未知电能表事件\t00\n" + // 发现未知电能表事件单元
                    "3112\t7\t跨台区电能表事件\t00\n" +
                    "3114\t7\t终端对时事件\t00\n" +
                    "3115\t7\t遥控跳闸记录\t51\n" +
                    "3116\t7\t有功总电能量差动越限事件记录\t11\n" +
                    "3117\t7\t输出回路接入状态变位事件记录\t \n" + //  ???
                    "3118\t7\t终端编程记录\t00\n" +
                    "3119\t7\t终端电流回路异常事件\t16\n" + //短路(0)，开路(1
                    "311A\t7\t电能表在网状态切换事件\t00\n" +
                    "311B\t7\t终端对电表校时记录\t55\n" +
                    "311C\t7\t电能表数据变更监控记录\t55\n" +
                    "311d\t7\t电能表在线状态变化事件\t55\n" +
                    "3120\t7\t电流互感器异常事件\t16\n" + // enum{1:发生，0：恢复}
                    "3121\t7\tTA专用模块工况事件\t \n" +  // ???
                    "3140\t7\t安全事件变更记录\t00\n" +
                    "3200\t7\t功控跳闸记录\t50\n" + // OI
                    "3201\t7\t电控跳闸记录\t50\n" +
                    "3202\t7\t购电参数设置记录\t50\n" + // 0x50 -- 80   OI
                    "3203\t7\t电控告警事件记录\t50]\n" +
                    "3300\t8\t事件上报状态\t \n" +
                    "3301\t8\t标准事件记录单元\t \n" +
                    "3302\t8\t编程记录事件单元\t \n" +
                    "3303\t8\t发现未知电能表事件单元\t \n" +
                    "3304\t8\t跨台区电能表事件单元\t \n" +
                    "3305\t8\t功控跳闸记录单元\t \n" +
                    "3306\t8\t电控跳闸记录单元\t \n" +
                    "3307\t8\t电控告警事件单元\t \n" +
                    "3308\t8\t电能表需量超限事件单元\t \n" +
                    "3309\t8\t停/上电事件记录单元\t \n" +
                    "330A\t8\t遥控事件记录单元\t \n" +
                    "330B\t8\t有功总电能量差动越限事件记录单元\t \n" +
                    "330C\t8\t事件清零事件记录单元\t \n" +
                    "330D\t8\t终端对电表校时记录单元\t \n" +
                    "330E\t8\t电能表在网状态切换事件单元\t \n" +
                    "330F\t8\t电能表数据变更监控记录单元\t \n" +
                    "3310\t8\t异常插卡事件记录单元\t \n" +
                    "3311\t8\t退费事件记录单元\t \n" +
                    "3312\t8\t通信模块变更事件单元\t \n" +
                    "3313\t8\t电能表时钟超差记录单元\t \n" +
                    "3314\t8\t电能表时段表编程事件记录单元\t \n" +
                    "3315\t8\t电能表节假日编程事件记录单元\t \n" +
                    "3316\t8\t安全事件变更记录单元\t \n" +
                    "3317\t8\t电能表在线状态变化事件单元\t \n" +
                    "3318\t8\t电流互感器异常事件单元\t \n" +
                    "3320\t8\t新增上报事件列表\t \n";

    private static SparseArray<EventInner> varInners = new SparseArray<>();

    static {
        String[] vars = INFO.split("\n");
        for (String var : vars) {
            String[] varArr = var.split("\t");
            int objNo = Integer.parseInt(varArr[0], 16);
            int interfClass = Integer.parseInt(varArr[1]);
            String objName = varArr[2];
            String dataType = varArr[3];
            // int attributes = Integer.parseInt(varArr[0].substring(2), 16);
            EventInner inner = new EventInner(objName, interfClass, dataType);
            varInners.put(objNo, inner);
        }
    }

    private final int logicNo;
    private final String formatString;
    private final EventInner inner;

    public Event(String oadHexStr, RecoverableString objValueHexStr) {
        super(oadHexStr, objValueHexStr);
        String oiStr = oadHexStr.substring(0, 4);
        logicNo = Integer.parseInt(oiStr, 16);
        inner = varInners.get(logicNo);
        this.objName = inner.objName;
        this.interfClass = inner.interfClass;

        this.formatString = format();
    }

    public static final class EventInner extends Inner {
        String dataType;

        public EventInner(String objName, int interfClass, String dataType) {
            super(objName, interfClass);
            this.dataType = dataType;
        }
    }

    private String format() {
        if (interfClass == 7) {
            return new IC7(this).toFormatString();
        } else {
            return formatSpecial();
        }
        //  return "暂不支持解析";
    }

    private String formatSpecial() {
        switch (logicNo) {
            // 上报状态
            case 0x3300:
                return new Obj_3300(this).toFormatString();
            //  事件清零事件记录单元
            case 0x330C:
                return new Obj_3300(this).toFormatString();
            case 0x3320:
                return new Obj_3320(this).toFormatString();
            default:
                return "暂不支持解析";
        }

    }

    private static class ReportStatus implements FormatAble {
        private final String result;
        private String oad;
        private int status;
        public int byteSize = 7;

        public ReportStatus(String reportStatus) {
            result = reportStatus.substring(0, 2);

            this.oad = reportStatus.substring(2, 10);
            String statusStr = reportStatus.substring(12, 14);
            this.status = Integer.parseInt(statusStr, 16);

        }


        @Override
        public String toFormatString() {

            return "通道： " + oad + "\n" +
                    "上报状态: \n" + getReportStr(status) + "\n";
        }


        public String getReportStr(int status) {
            String[] reportArr = new String[]{
                    "事件发生上报标识", "事件发生上报确认标识",
                    "事件结束（恢复）上报标识", "事件结束（恢复）上报确认标识"
            };
            String binaryStr = NumberConvert.toBinaryStr(status, 4);
            System.out.println(binaryStr);
            char[] chars = binaryStr.toCharArray();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < reportArr.length; i++) {
                String result = reportArr[i].substring(reportArr[i].length() - 4, reportArr[i].length() - 2);
                stringBuilder.append(" ").append(i + 1).append(".").append(reportArr[i]).append(": ").append(chars[3 - i] == '0' ? "未" : "已").append(result).append("\n");
            }

            return stringBuilder.toString();
        }
    }

    public static final class Obj_3300 implements FormatAble {
        Obj paramsVariable;
        String formatString;


        public Obj_3300(Obj paramsVariable) {
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

                    StringBuilder stringBuilder = new StringBuilder(paramsVariable.objName).append(": ");
                    int result = Integer.parseInt(paramsVariable.objValueHexStr.substring(0, 2), 16);
                    if (result == 0) {
                        return stringBuilder.append("暂无数据").toString();
                    }
                    // Array
                    if (paramsVariable.index == 0) {
                        // 直接逃过 01（Array） ，截取之后表示元素个数的值
                        int itemCount = Integer.parseInt(paramsVariable.objValueHexStr.substring(2, 4), 16);
                        for (int i = 0; i < itemCount; i++) {
                            ReportStatus status = new ReportStatus(paramsVariable.objValueHexStr.getCurrentSting());
                            paramsVariable.objValueHexStr.substring(status.byteSize * 2);
                            // structure
                            stringBuilder.append(status.toFormatString());
                            /*int structureItemCount = Integer.parseInt(paramsVariable.objValueHexStr.substring(0,2),
                                    16)  ;*/
                        }
                    } else {
                        ReportStatus status = new ReportStatus(paramsVariable.objValueHexStr.getCurrentSting());
                        paramsVariable.objValueHexStr.substring(status.byteSize * 2);
                        // structure
                        stringBuilder.append(status.toFormatString());
                    }
                    return stringBuilder.toString();
                default:
                    return paramsVariable.objName + " 暂无法解析";
            }
        }
    }

    @Override
    public String toFormatString() {
        return formatString;
    }

    private static class IC7 implements FormatAble {
        private Obj event;
        private String formatString;

        public IC7(Obj event) {
            this.event = event;
            this.formatString = format();
        }

        public String format() {

            switch (event.attributes) {

                case 0x02:

                    if (event.index == 0x00) {

                    } else {

                    }
                    return "";
                // 关联对象属性表
                case 3:
                    StringBuilder stringBuilder3 = new StringBuilder(event.objName).append("\n");
                    if (event.index == 0) {
                        int itemCount = Integer.parseInt(event.objValueHexStr.substring(2, 4), 16);

                        for (int i = 0; i < itemCount; i++) {
                            stringBuilder3.append(event.getValueByDataType()).append("\n");
                            // 00
                            // 02021C07E3051414190C1C07E3051508283B0000
                        }

                    } else {
                        stringBuilder3.append(event.getValueByDataType()).append("\n");
                    }
                    return stringBuilder3.toString();

                case 0x04:
                    return String.format(Locale.CHINA, "%s： %s ", event.objName + "当前记录数",
                            Long.parseLong(event.getValueByDataType(), 16));
                           /* event.getFormatValueWithUnit(
                                    Long.parseLong(event.getValueByDataType(), 16),"",0));*/
                case 5:
                    return String.format(Locale.CHINA, "%s： %s ", event.objName + "最大记录数",
                            Long.parseLong(event.getValueByDataType(), 16));
                case 7:
                    // 85010330110A0001010102020002021C07E3051414190C1C07E3051508283B0000
                    // 010102020002021C07E3051414190C1C07E3051508283B0000
                    StringBuilder stringBuilder7 = new StringBuilder(event.objName).append("\n");
                    if (event.index == 0) {
                        int itemCount = Integer.parseInt(event.objValueHexStr.substring(2, 4), 16);
                        // 02020002021C07E3051414190C1C07E3051508283B0000
                        for (int i = 0; i < itemCount; i++) {
                            stringBuilder7.append(getCurrentValue(i));
                            // 00
                            // 02021C07E3051414190C1C07E3051508283B0000
                        }

                    } else {
                        stringBuilder7.append(getCurrentValue(event.index - 1));
                    }
                    return stringBuilder7.toString();
                case 8:
                    int enumNo = Integer.parseInt(event.getValueByDataType(), 16);
                    String[] enumStrArr = new String[]{"不上报", "事件发生上报", "事件恢复上报", "事件发生恢复均上报", "解析错误"};
                    enumNo = (enumNo != 0 && enumNo != 1 && enumNo != 2 && enumNo != 3) ? 4 : enumNo;
                    return String.format(Locale.CHINA, "%s： %s ", event.objName + "上报标识",
                            enumStrArr[enumNo]);
                case 9:
                    int flag = Integer.parseInt(event.getValueByDataType(), 16);
                    String[] flagStr = new String[]{"无效", "有效", "解析错误"};
                    flag = (flag != 0 && flag != 1) ? 2 : flag;
                    return String.format(Locale.CHINA, "%s： %s ", event.objName + "有效标识",
                            flagStr[flag]);
                case 10:
                    // 85010330110A0001010102020002021C07E3051414190C1C07E3051508283B0000
                    // 010102020002021C07E3051414190C1C07E3051508283B0000
                    StringBuilder stringBuilder = new StringBuilder(event.objName).append("\n");
                    if (event.index == 0) {
                        int itemCount = Integer.parseInt(event.objValueHexStr.substring(2, 4), 16);
                        // 02020002021C07E3051414190C1C07E3051508283B0000
                        for (int i = 0; i < itemCount; i++) {
                            stringBuilder.append(getTimeStatus(i));
                            // 00
                            // 02021C07E3051414190C1C07E3051508283B0000
                        }

                    } else {
                        stringBuilder.append(getTimeStatus(event.index - 1));
                    }
                    return stringBuilder.toString();
                default:
                    return event.objName + " 暂无法解析" + event.attributes;
            }
        }

        private String getCurrentValue(int index) {
            StringBuilder stringBuilder = new StringBuilder("上").append(index + 1).append("次当前值\n");
            // 02 Structure 02 两个元素
            int itemCountChild = Integer.parseInt(event.objValueHexStr.substring(2, 4), 16);
            String sourceOfTheEvent = event.getValueByDataType();
            // 00  sourceOfTheEvent

            // 02021C07E3051414190C1C07E3051508283B0000
            String[] timeStatus = new String[2];
            event.objValueHexStr.substring(4);
            // 1C07E3051414190C1C07E3051508283B0000
            timeStatus[0] = event.getValueByDataType();
            // 1C07E3051508283B0000
            timeStatus[1] = event.getValueByDataType();
            // 0000
            String count = TextUtils.isEmpty(timeStatus[0]) ? "暂无相关数据" : Long.parseLong(timeStatus[1], 16) + "次";
            String time = TextUtils.isEmpty(timeStatus[1]) ? "暂无相关数据" : Long.parseLong(timeStatus[1], 16) + "秒";

            stringBuilder.append("事件发生源： ")
                    .append(TextUtils.isEmpty(sourceOfTheEvent) ? "暂无相关数据" : sourceOfTheEvent)
                    .append("\n")
                    // .append("时间状态：\n")
                    .append("事件发生次数: ")
                    .append(count)
                    .append("\n")
                    .append("事件累计时间: ")
                    .append(time)
                    .append("\n");
            return stringBuilder.toString();
        }

        private String getTimeStatus(int index) {
            StringBuilder stringBuilder = new StringBuilder("上").append(index + 1).append("次时间状态\n");
            // 02 Structure 02 两个元素
            int itemCountChild = Integer.parseInt(event.objValueHexStr.substring(2, 4), 16);
            String sourceOfTheEvent = event.getValueByDataType();
            // 00  sourceOfTheEvent

            // 02021C07E3051414190C1C07E3051508283B0000
            String[] timeStatus = new String[2];
            event.objValueHexStr.substring(4);
            // 1C07E3051414190C1C07E3051508283B0000
            timeStatus[0] = event.getValueByDataType();
            // 1C07E3051508283B0000
            timeStatus[1] = event.getValueByDataType();
            // 0000
            String startTime = TextUtils.isEmpty(timeStatus[0]) ? "未发生" : new DateTimeS(timeStatus[0]).format();
            String endTime = TextUtils.isEmpty(timeStatus[1]) ? "不支持，或无最近一次记录" : new DateTimeS(timeStatus[1]).format();

            stringBuilder.append("事件发生源： ")
                    .append(TextUtils.isEmpty(sourceOfTheEvent) ? "暂无相关数据" : sourceOfTheEvent)
                    .append("\n")
                    // .append("时间状态：\n")
                    .append("最近一次发生时间: ")
                    .append(startTime)
                    .append("\n")
                    .append("最近一次结束时间: ")
                    .append(endTime)
                    .append("\n");
            return stringBuilder.toString();
        }

        @Override
        public String toFormatString() {
            return formatString;
        }
    }


    @SuppressWarnings("uncheck")
    public static final class Obj_3320 implements FormatAble {
        Obj paramsVariable;
        String formatString;

        public Obj_3320(Obj paramsVariable) {
            this.paramsVariable = paramsVariable;
            this.formatString = format();
        }

        @Override
        public String toFormatString() {
            return formatString;
        }

        private String format() {
            String tag = paramsVariable.attributes == 0x02 ? "OAD" : paramsVariable.attributes == 0x02 ? "OAD" : "元素";
            switch (paramsVariable.attributes) {

                case 0x02:
                case 0x03:
                    StringBuilder stringBuilder = new StringBuilder(paramsVariable.objName).append(": ");
                    int result = Integer.parseInt(paramsVariable.objValueHexStr.substring(0, 2), 16);
                    if (result == 0) {
                        return stringBuilder.append("暂无数据").toString();
                    }
                    // Array
                    if (paramsVariable.index == 0) {
                        // 直接逃过 01（Array） ，截取之后的标识元素个数的值
                        int itemCount = Integer.parseInt(paramsVariable.objValueHexStr.substring(2, 4), 16);
                        for (int i = 0; i < itemCount; i++) {
                            append(tag, i + 1, stringBuilder);
                        }
                    } else {
                        append(tag, paramsVariable.index, stringBuilder);
                    }
                    return stringBuilder.toString();
                default:
                    return paramsVariable.objName + " 暂无法解析";
            }
        }

        private void append(String tag, int index, StringBuilder stringBuilder) {
            String oi_obj = paramsVariable.getValueByDataType();
            stringBuilder.append(tag).append(index).append(": ").append(oi_obj)
                    .append("(")
                    .append(OIManager.getInstance().getObjName(oi_obj.substring(0, 4))).append(")\n");
        }
    }

    @SuppressWarnings("uncheck")
    public abstract static class RecordUnit implements FormatAble {

        Event obj;
        String formatString;

        public RecordUnit(Event obj) {
            this.obj = obj;
            this.formatString = format();
        }

        private String format() {
            if (obj.index == 0) {
                String dataType = obj.objValueHexStr.substring(0, 2);
                if ("00".equals(dataType)) {
                    return obj.objName + ": 暂无相关数据";
                }
                if (!"02".equals(dataType)) {
                    return obj.objName + ": 解析失败，数据格式不正确";
                }
                int structureItemCount = Integer.parseInt(obj.objValueHexStr.substring(0, 2), 16);

                StringBuilder string = new StringBuilder();
                for (int i = 0; i < structureItemCount; i++) {
                    string.append(getFormatStringByIndex(i + 1)).append("\n");
                }
                return string.toString();
            } else {
                return getFormatStringByIndex(obj.index);
            }


        }

        private String getFormatStringByIndex(int index) {
            switch (index) {
                case 1:
                    return "事件记录序号:" + obj.getNum();
                case 2:
                    return "事件发生时间:" + new DateTimeS(obj.getValueByDataType()).format();
                case 3:
                    return "事件结束时间:" + new DateTimeS(obj.getValueByDataType()).format();
                case 4:

                    String[] enum3023 = new String[]{"无功组合方式1特征字", "无功组合方式2特征字"};
                    String[] enum310A = new String[]{"终端主板内存故障", "时钟故障", "主板通信故障", "485抄表故障", "显示板故障", "载波通道异常", "内卡初始化错误", "ESAM错误"};
                    String[] enum3119 = new String[]{"短路", "开路"};
                    String[] enum3120 = new String[]{"发生", "恢复"};
                    HashMap<Integer, String[]> enums = new HashMap<>();
                    enums.put(0x3023, enum3023);
                    enums.put(0x310A, enum310A);
                    enums.put(0x3119, enum3119);
                    enums.put(0x3120, enum3120);
                    String dataType = obj.objValueHexStr.substring(0, 2);
                    if ("00".equals(dataType)) {
                        return "事件发生源: 无";
                    }
                    if (TextUtils.isEmpty(obj.inner.dataType)) {
                        return "事件发生源: 未明确";
                    }
                    if ("16".equals(dataType)) {
                        int value = (int) obj.getNum();
                        String[] enumItemArr = enums.get(obj.logicNo);
                        return "事件发生源:" + (value >= enumItemArr.length ? "无相关数据 " : enumItemArr[value]);
                    } else {
                        return "事件发生源:" + obj.getValueByDataType();
                    }

                    // 事件上报状态
                case 5:
                    return new Obj_3300(obj).toFormatString();
                default:
                    return getFormatStringByIndexAndLogicName(index);
            }
        }

        /**
         * @param index 这个index 最少为6
         * @return
         */
        private String getFormatStringByIndexAndLogicName(int index) {
            switch (obj.logicNo) {
                //
                case 0x3301:
                    return "数据格式由具体对象决定";
                case 0x3302:
                    if (index == 6) {
                        String dataType = obj.objValueHexStr.substring(0, 2);
                        if ("00".equals(dataType)) {
                            return "编程对象列表： 为空";
                        }
                        if ("01".equals(dataType)) {
                            StringBuilder stringBuilder = new StringBuilder("编程对象列表： ");
                            int arrayItemCount = Integer.parseInt(obj.objValueHexStr.substring(0, 2), 16);
                            for (int i = 0; i < arrayItemCount; i++) {
                                String objStr = obj.getValueByDataType();
                                stringBuilder.append("OAD").append(index).append(": ").append(objStr)
                                        .append("(")
                                        .append(OIManager.getInstance().getObjName(objStr.substring(0, 4))).append(")\n");
                            }
                            return stringBuilder.toString();
                        } else {
                            return obj.objName + ":" + "数据格式不正确";
                        }

                    } else {
                        return "数据格式由具体对象决定";
                    }
                case 0x3303:
                    // todo  一个搜表结果  6003
                    // 一个搜表结果∷=structure
                    //{
                    //  通信地址         TSA，
                    //  所属采集器地址   TSA，
                    //规约类型  enum
                    //{
                    //  未知          （0），
                    //DL/T645-1997  （1），
                    //DL/T645-2007  （2），
                    //DL/T698.45    （3），
                    //CJ/T 188-2004 （4）
                    //}，
                    //相位             enum{未知（0），A（1），B（2），C（3）}，
                    //信号品质         unsigned，
                    //搜到的时间       date_time_s，
                    //搜到的附加信息   array附加信息
                    //}
                    //附加信息∷=structure
                    //{
                    //对象属性描述  OAD，
                    //属性值        Data
                    //}
                    if (index == 6) {
                        return "搜表结果： 未完成解析 ";
                    }
                    return "解析失败";
                case 0x3304:
                    // todo  一个跨台区结果 解析
                    if (index == 6) {
                        return "搜表结果： 未完成解析 ";
                    }
                    return "解析失败";

                // 事件上报状态
                case 0x3305:

                    if (index == 6) {
                        return "事件发生后2分钟功率：" + obj.getFormatValueWithUnit(obj.getNum(),"W",-1);
                    }else if (index == 7){
                        String oi = obj.getValueByDataType();
                        return "控制对象：" + oi +"("+OIManager.getInstance().getObjName(oi)+")";
                    }else if (index == 8){
                        return "跳闸轮次：" + obj.getValueByDataType();
                    }else if (index == 9){
                        return "功控定值：" +  obj.getFormatValueWithUnit(obj.getNum(),"kw",-4);
                    }else if (index ==10){
                        return "跳闸发生前总加有功功率：" +  obj.getFormatValueWithUnit(obj.getNum(),"kw",-4);
                    }
                    else {
                        return "数据格式由具体对象决定";
                    }
                case 0x3306:

                   if (index == 6){
                        String oi = obj.getValueByDataType();
                        return "控制对象：" + oi +"("+OIManager.getInstance().getObjName(oi)+")";
                    }else if (index == 7){
                        return "跳闸轮次：" + obj.getValueByDataType();
                    }else if (index == 8){
                        return "电控定值：" +  obj.getFormatValueWithUnit(obj.getNum(),"kWh",-4);
                    }else if (index ==9){
                        return "跳闸发生时总加电能量：" +  obj.getFormatValueWithUnit(obj.getNum(),"kwh/元",-4);
                    }
                    else {
                        return "数据格式由具体对象决定";
                    }
                case 0x3307:

                    if (index == 6){
                        String oi = obj.getValueByDataType();
                        return "控制对象：" + oi +"("+OIManager.getInstance().getObjName(oi)+")";
                    }else if (index == 7){
                        return "电控定值：" +  obj.getFormatValueWithUnit(obj.getNum(),"kWh",-4);
                    }else {
                        return "数据格式由具体对象决定";
                    }
                case 0x3308:

                    if (index == 6){
                        return "超限期间需量最大值：" + obj.getNum();
                    }else if (index == 7){
                        return "超限期间需量最大值发生时间：" + new DateTimeS(obj.getValueByDataType()).format();
                    } else {
                        return "数据格式由具体对象决定";
                    }
                case 0x3309:
                    // fixme 可以更加完善
                     if (index == 6){
                        return "属性标志：" + obj.getValueByDataType();
                    }else {
                        return "数据格式由具体对象决定";
                    }
                case 0x330A:
                    if (index == 6){
                       String dataType =  obj.objValueHexStr.substring(2);
                       if ("00".equals(dataType)) return "控后2分钟总加组功率 :为空";
                        // 获取array 的子元素的个数
                        int itemCount = Integer.parseInt(obj.objValueHexStr.substring(0, 2), 16);
                        StringBuilder stringBuilder = new StringBuilder("控后2分钟总加组功率：\n");
                        for (int i = 0; i < itemCount; i++) {
                           // String value = obj.getValueByDataType();
                            stringBuilder.append("功率").append(i+1).append(":").append(obj.getNum())
                                    .append(i == itemCount - 1 ? "" : "\n");
                        }
                        return stringBuilder.toString();
                    }else {
                        return "数据格式由具体对象决定";
                    }

                case 0x330B:

                    if (index == 6){
                        return " 越限时对比总加组有功总电能量：" +  obj.getFormatValueWithUnit(obj.getNum(),"kWh",-4);
                    }else if (index == 7){
                        return "越限时参照总加组有功总电能量：" +  obj.getFormatValueWithUnit(obj.getNum(),"kWh",-4);
                    }else if (index == 8){
                        return " 越限时差动越限相对偏差值：" +  obj.getFormatValueWithUnit(obj.getNum(),"%",0);
                    }else if (index ==9){
                        return " 越限时差动越限绝对偏差值量：" +  obj.getFormatValueWithUnit(obj.getNum(),"kwh",-4);
                    }
                    else {
                        return "未知数据格式";
                    }
                case 0x330C:
                    if (index == 6){
                        String dataType =  obj.objValueHexStr.substring(2);
                        if ("00".equals(dataType)) return "控后2分钟总加组功率 :为空";
                        // 获取array 的子元素的个数
                        int itemCount = Integer.parseInt(obj.objValueHexStr.substring(0, 2), 16);
                        StringBuilder stringBuilder = new StringBuilder("事件清零列表：\n");
                        for (int i = 0; i < itemCount; i++) {
                            // String value = obj.getValueByDataType();
                            stringBuilder.append("OMD").append(i+1).append(":").append(obj.getValueByDataType())
                                    .append(i == itemCount - 1 ? "" : "\n");
                        }
                        return stringBuilder.toString();
                    }else {
                        return "未知数据格式";
                    }
                case 0x330D:
                    if (index == 6){
                        return "校时前时钟：" + new DateTimeS(obj.getValueByDataType()).format();
                    }else if (index == 7){
                        return "时钟误差："+obj.getFormatValueWithUnit(obj.getNum(),"秒",0);
                    } else {
                        return "未知数据格式";
                    }
                // todo
                case 0x330F:
                    if (index == 6){
                        return "暂未支持解析";
                    }else {
                        return "数据格式由具体对象决定";
                    }
                case 0x3310:
                    if (index == 6 ){
                        return "卡序列号：" + obj.getValueByDataType();
                    }else if (index == 7){
                        return "插卡错误信息字："+obj.getNum();
                    }else if (index == 8){
                        return "插卡操作命令头："+obj.getValueByDataType();
                    }else if (index == 9){
                        return "插卡错误响应状态："+obj.getNum();
                    } else {
                        return "数据格式由具体对象决定";
                    }
                case 0x3311:
                    if (index == 6){
                        return " 退费金额：" +  obj.getFormatValueWithUnit(obj.getNum(),"元",-2);
                    }
                    else {
                        return "数据格式由具体对象决定";
                    }

                case 0x3312:
                    if (index == 6 ){
                        return "模块对应的通信地址：" + obj.getValueByDataType();
                    }else if (index == 7){
                        return "变更前的模块描述符："+NumberConvert.hexStrToAsciiString(obj.getValueByDataType());
                    }else if (index == 8){
                        return "变更后的模块描述符："+NumberConvert.hexStrToAsciiString(obj.getValueByDataType());
                    }else {
                        return "未知数据格式";
                    }
                case 0x3313:
                    if (index == 6){
                        return "电能表时钟：" + new DateTimeS(obj.getValueByDataType()).format();
                    }else if (index == 7){
                        return "终端当前时钟：" + new DateTimeS(obj.getValueByDataType()).format();
                    } else {
                        return "数据格式由具体对象决定";
                    }
                case 0x3314:
                    if (index == 6){
                        return "编程时段表对象：" + obj.getValueByDataType();
                    }else if (index == 7){
                        return " 编程前时段表：" + new ParamsVariable.Obj_4016(obj).toFormatString();
                    } else {
                        return "数据格式由具体对象决定";
                    }
                case 0x3315:
                    if (index == 6){
                        return " 编程节假日对象：" + obj.getValueByDataType();
                    }else if (index == 7){
                        return "  编程前节假日内容：" + new ParamsVariable.Obj_4011(obj).toFormatString();
                    } else {
                        return "数据格式由具体对象决定";
                    }
                default:
                    return "暂未支持解析";
            }
        }


        @Override
        public String toFormatString() {
            return formatString;
        }

    }

    @SuppressWarnings("uncheck")
    public static final class Obj_3301 extends RecordUnit {
        public Obj_3301(Event paramsVariable) {
            super(paramsVariable);
        }

        @Override
        public String toFormatString() {
            return formatString;
        }

        private String format() {
            String tag = obj.attributes == 0x02 ? "OAD" : obj.attributes == 0x02 ? "OAD" : "元素";
            switch (obj.attributes) {

                case 0x02:
                case 0x03:
                    StringBuilder stringBuilder = new StringBuilder(obj.objName).append(": ");
                    int result = Integer.parseInt(obj.objValueHexStr.substring(0, 2), 16);
                    if (result == 0) {
                        return stringBuilder.append("暂无数据").toString();
                    }
                    // Array
                    if (obj.index == 0) {
                        // 直接逃过 01（Array） ，截取之后的标识元素个数的值
                        int itemCount = Integer.parseInt(obj.objValueHexStr.substring(2, 4), 16);
                        for (int i = 0; i < itemCount; i++) {
                            append(tag, i + 1, stringBuilder);
                        }
                    } else {
                        append(tag, obj.index, stringBuilder);
                    }
                    return stringBuilder.toString();
                default:
                    return obj.objName + " 暂无法解析";
            }
        }

        private void append(String tag, int index, StringBuilder stringBuilder) {
            String oi_obj = obj.getValueByDataType();
            stringBuilder.append(tag).append(index).append(": ").append(oi_obj)
                    .append("(")
                    .append(OIManager.getInstance().getObjName(oi_obj.substring(0, 4))).append(")\n");
        }
    }
}
