package com.sgcc.pda.framelibrary.protocol698.apdu.obj;

import com.sgcc.pda.framelibrary.FormatAble;
import com.sgcc.pda.framelibrary.protocol698.apdu.data.DataManager;
import com.sgcc.pda.framelibrary.utils.RecoverableString;


import java.util.Locale;

/**
 * Created by qinling on 2019/5/10 11:47
 * Description:  A.3　显示类对象
 */
public class Dispaly extends Obj {

    public static void main(String[] args) {
       // System.out.println(new Dispaly("F3010215", "02025B004001020011010000").toFormatString());
    }
    private String formatString;
    public Dispaly(String oadHexStr, RecoverableString objValueHexStr) {
        super(oadHexStr, objValueHexStr);
        formatString = format();
    }


    @Override
    public String toFormatString() {
        return formatString;
    }

    private String format() {
        switch (attributes) {
            case 0x02:
                if (index == 0) {
                    // objValueHexStr.substring(0,2) 肯定是 01 ---> array
                    // 获取Array 元素个数。
                    int size = Integer.parseInt(objValueHexStr.substring(2, 4), 16);
                  //  String str = objValueHexStr.getCurrentSting();
                    StringBuilder stringBuilder = new StringBuilder();
                    DisplyObj[] displyObjs = new DisplyObj[size];
                    for (int i = 0; i < size; i++) {
                        displyObjs[i] = new DisplyObj(objValueHexStr);
                       // str = str.substring(displyObjs[i].byteSize * 2);
                       // objValueHexStr.substring(displyObjs[i].byteSize * 2);
                        stringBuilder
                                .append(String.format(Locale.CHINA, "第%d屏: \n", i + 1))
                                .append(displyObjs[i].toFormatString())
                                .append("\n");
                    }
                    return stringBuilder.toString();
                } else {
                    DisplyObj displyObj = new DisplyObj(objValueHexStr);
                    return String.format(Locale.CHINA, "第%d屏: \n%s",
                            index, displyObj.toFormatString());
                }
            case 0x03:
                long time = getNum();
                if (time == 0) return "由外部触发显示时间";
                if (time == -1) return "";
                return String.format(Locale.CHINA, "显示时间： %s ", getFormatValueWithUnit(time, "秒", 0));
            case 0x04:
                String[] paramsName = new String[]{"当前显示的总对象数", "可设置的最大显示数", ""};
                Integer[] nums = new Integer[2];
                StringBuilder stringBuilder = new StringBuilder();
                // F3000400
                if (index == 0) {
                    // structure 去除 0202
                    String str = objValueHexStr.substring(4);
                    for (int i = 0; i < nums.length; i++) {
                        nums[i] = getNum(getValueByDataType()).intValue();
                        stringBuilder.append(paramsName[i]).append(": ").append(nums[i] == -1? "无该信息":nums[i]).append("\n");
                    }
                } else {  // F3000401   F3000402
                    try {
                        long num =  getNum();
                        stringBuilder.append(paramsName[index-1]).append(": ").append(num== -1? "无该信息":num).append("\n");
                    } catch (Throwable e) {
                        stringBuilder.append("");
                    }

                }
                return stringBuilder.toString();
            // return String.format(Locale.CHINA, "日期时间： %s ", new DateTimeS(value).format());
            default:
                return objName + " 暂无法解析";
        }
    }


    public static class DisplyObj implements FormatAble {
        private final static int CHOICE_OAD = 0;
        private final static int CHOICE_ROAD = 1;
        final int choice;
        final String oad;
        final String[] oads; // 只有 ROAD时才存在
        final Integer number;
        public final int byteSize;

        public DisplyObj(RecoverableString string) throws IndexOutOfBoundsException, NumberFormatException {
            // 把 标识数据类型格式的 前三个字节去除。
            // 此处固定为 02025B 标识 STRUCTURE 其元素由两个 其中第一个为CSD
           string.substring(6);

            this.choice = Integer.parseInt(string.substring(0, 2), 16);

            if (choice == CHOICE_OAD) {
                this.oad = string.substring(0, 8);
                this.oads = new String[0];
                this.number = getNumber(string.getCurrentSting());
                this.byteSize = 10;  // 若CSD 标识的是 oad ，则 该类型字节数为 10
            } else {
                this.oad = string.substring(0, 8);
                int theNumOfRoad = Integer.parseInt(string.substring(0, 2), 16);
                this.oads = new String[theNumOfRoad];
               // string = string.substring(12);
                for (int i = 0; i < oads.length; i++) {
                    oads[i] = string.substring(0, 8);
                   // string = string.substring(8);
                }
                this.number = getNumber(string.getCurrentSting());
                this.byteSize = 10 + oads.length * 4 + 1; // 若是 ROAD，则多出来 4*n+1个字节
            }

        }

        private Integer getNumber(String substring) {

            try {
                int dataSize = DataManager.getInstance().getDataByteSize(substring);
                return Integer.parseInt(substring.substring(2, 2 + dataSize * 2));
            } catch (Throwable e) {
                System.out.println(e);
                return null;
            }
        }

        @Override
        public String toFormatString() {
            StringBuilder stringBuilder = new StringBuilder();
            if (choice == CHOICE_OAD) {
                stringBuilder
                        .append("显示对象: ")
                        .append(oad)
                        .append("\n屏序号: ")
                        .append(number);

            } else if (choice == CHOICE_ROAD) {
                stringBuilder
                        .append(" 显示对象: ")
                        .append(oad)
                        .append(oads.length > 0 ? "（" : "");

                for (int i = 0; i < oads.length; i++) {
                    stringBuilder.append(oads[i])
                            .append(i == oads.length - 1 ? "）" : ",");
                }
                stringBuilder.append("\n屏序号: ")
                        .append(number);
            } else {
                stringBuilder.append("解析失败");
            }
            return stringBuilder.toString();
        }
    }



}
