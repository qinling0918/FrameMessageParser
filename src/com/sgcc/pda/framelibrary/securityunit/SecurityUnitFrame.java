package com.sgcc.pda.framelibrary.securityunit;

import android.support.annotation.NonNull;

import com.sgcc.pda.framelibrary.IFrame;
import com.sgcc.pda.framelibrary.utils.NumberConvert;
import com.sgcc.pda.framelibrary.utils.TextUtils;

import java.util.Locale;

/**
 * Created by qinling on 2018/6/7 9:23
 * Description:
 */
public class SecurityUnitFrame implements Cloneable, IFrame {

    public static final int FRAME_MAX_LENGTH = 0xFFFF;
    private final String mainSign; // 主功能标识符
    private final String command_responseCode;// 命令码或者响应码
    private final String statusCode;// 状态码
    private final String data; //数据域

    private SecurityUnitFrame(Builder builder) {
        this.mainSign = builder.mainSign;
        this.command_responseCode = builder.command_responseCode;
        this.statusCode = builder.statusCode;
        this.data = builder.data;
    }

    public SecurityUnitFrame() {
        this(new Builder());
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    @Override
    public String toString() {
        return "SecurityUnitFrame{" +
                "mainSign='" + mainSign + '\'' +
                ", command_responseCode='" + command_responseCode + '\'' +
                ", statusCode='" + statusCode + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    @Override
    public String toHexString() {
        return newBuilder().toHexString();
    }

    public String format(String securityUnitFrameStr) {
        return parse(securityUnitFrameStr).toFormatString();
    }

    public Parser parse(String securityUnitFrameStr) {
        return new Parser(securityUnitFrameStr);
    }

    public static final class Builder {
        private String mainSign; // 主功能标识符
        private String command_responseCode;// 命令码或者响应码
        private String statusCode;// 状态码
        private String data; //数据域

        public Builder() {
            // 默认为 安全单元操作类（2.0） 安全单元1.0 中除升级命令的所有命令主功能标识均为 00
            mainSign = "00";
            command_responseCode = "01";
            statusCode = "";
            data = "";
        }

        public Builder(SecurityUnitFrame securityUnit2) {
            this.mainSign = securityUnit2.mainSign;
            this.command_responseCode = securityUnit2.command_responseCode;
            this.statusCode = securityUnit2.statusCode;
            this.data = securityUnit2.data;
        }

        public SecurityUnitFrame build() {
            return new SecurityUnitFrame(this);
        }

        public String toHexString() {
            return "E9" + getLength() + getF2Data() + getCS() + "E6";
        }

        @Override
        public String toString() {
            return toHexString();
        }

        /**
         * 设置 主功能标识符
         *
         * @param mainSign 16进制 主功能标识符
         * @return
         */
        public Builder setMainSign(String mainSign) {
            this.mainSign = mainSign;
            return this;
        }

        /**
         * 设置 命令码
         *
         * @param commandCode 16进制 一字节命令码
         * @return
         */
        public Builder setCommandCode(String commandCode) {
            this.command_responseCode = commandCode;
            return this;
        }

        /**
         * 设置 响应码
         *
         * @param responseCode 16进制 一字节响应码
         * @return
         */
        public Builder setResponseCode(String responseCode) {
            this.command_responseCode = responseCode;
            return this;
        }

        /**
         * 设置 状态码
         *
         * @param statusCode 16进制 一字节状态码
         * @return
         */
        public Builder setStatusCode(String statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        /**
         * 设置 数据域
         *
         * @param data 16进制 数据域
         * @return
         */
        public Builder setData(String data) {
            this.data = data;
            return this;
        }

        /**
         * 获取主功能标识符到是数据域的数据字符串
         *
         * @return
         */
        private String getF2Data() {
            String status = isResponseFrame(command_responseCode) ? statusCode : "";
            return mainSign + command_responseCode + status + data;
        }

        /**
         * 根据命令码或者响应码位数据判断 是否需要添加状态码
         *
         * @param command_responseCode
         * @return
         */
        private boolean isResponseFrame(String command_responseCode) {
            return NumberConvert.hexStrToBinaryStr(command_responseCode).substring(0, 1).equalsIgnoreCase("1");
        }

        private String getCS() {
            return NumberConvert.getCs("E9" + getLength() + getF2Data());
        }

        /**
         * 获取帧长度域  字节高低位翻转
         *
         * @return 获取帧长度, 以2字节16进制表示
         * 出现错误:
         * 返回"0000"  帧字符串长度为奇数
         * 返回"FFFF"  帧长度超出最大限制,
         */

        private String getLength() {
            int length = getF2Data().length();
            if (length % 2 != 0) { //字符串长度不是偶数,也就是说 字节组成出现问题
                return "0000";
            }
            length /= 2; //获取字节数
            if (length > FRAME_MAX_LENGTH) {
                return "FFFF";
                //throw new IndexOutOfBoundsException("超出了协议目前现有的长度 (2^14) (16383) (3FFFH)");
            }
            String lenStr = NumberConvert.toHexStrWithAddZero(length, 4);
            return lenStr;//NumberConvert.hexStrReverse(lenStr,0,lenStr.length());
        }
    }

    public static final class Parser {
        public static final int NO_ERROR = 0;
        public static final int ERROR_NULL_OR_NOT_HEX = -1; // null或不是16进制
        public static final int ERROR_STRING_LENGTH = -2; //长度不对，最少也要12字节，或者字符串长度为奇数
        public static final int ERROR_FRAME_HEAD_TAIL_CODE = -3; // 字符串长度存在问题
        public static final int ERROR_FRAME_LENGTH_CODE = -4; // 长度域值与 该帧字符串不符合
        public static final int ERROR_FRAME_CHECK_CODE = -5; // 字符串长度存在问题
        public final String frameStr;
        /**
         * 0 正常
         * -1 null或不是16进制
         * -2 长度不对，最少也要12字节，或者字符串长度为奇数
         * -3 帧起始码、帧结束码 格式不对
         * -4 长度域值与 该帧字符串不符合
         * -5 校验值不符
         */
        public final int parseCode;

        public String getErrorStr(int parseCode) {
            switch (parseCode) {
                case NO_ERROR:
                    return "解析成功";
                case ERROR_NULL_OR_NOT_HEX:
                    return "字符串格式不对";
                case ERROR_STRING_LENGTH:
                    return "帧字符串长度不足，或者长度为奇数";
                case ERROR_FRAME_HEAD_TAIL_CODE:
                    return "帧起始码、帧结束码 格式不对";
                case ERROR_FRAME_LENGTH_CODE:
                    return "长度域值与 该帧字符串不符合";
                case ERROR_FRAME_CHECK_CODE:
                    return "校验值不符";
                default:
                    return "帧解析时出错";
            }
        }

        public Parser(String frameStr) {
            this.frameStr = frameStr;
            parseCode = checkSafeUnit2Frame(this.frameStr);
        }

        public SecurityUnitFrame reBuild() {
            if (parseCode != 0) return null;
            if (isResponseFrame()) return reBuildResponseFrame();
            return reBuildCommandFrame();
        }

        private SecurityUnitFrame reBuildResponseFrame() {
            return new SecurityUnitFrame().newBuilder()
                    .setMainSign(getMainSign())
                    .setResponseCode(getCommandResponseCode())
                    .setStatusCode(getStatusCode())
                    .setData(getData())
                    .build();
        }

        @NonNull
        private SecurityUnitFrame reBuildCommandFrame() {
            return new SecurityUnitFrame().newBuilder()
                    .setMainSign(getMainSign())
                    .setCommandCode(getCommandResponseCode())
                    .setData(getData())
                    .build();
        }

        public String getMainSign() {
            if (parseCode != 0) return parseCode + "";
            return frameStr.substring(6, 8);
        }

        public String getData() {
            if (parseCode != 0) return parseCode + "";
            int dataStartIndex = isResponseFrame() ? 12 : 10;
            return frameStr.substring(dataStartIndex, frameStr.length() - 4);
        }

        public String getStatusCode() {
            if (parseCode != 0) return parseCode + "";
            if (!isResponseFrame()) return "";
            return frameStr.substring(10, 12);
        }

        public String getCommandResponseCode() {
            if (parseCode != 0) return parseCode + "";
            return frameStr.substring(8, 10);
        }

        public boolean isResponseFrame() {
            if (parseCode != 0) return false;
            return isResponseFrame(frameStr);
        }

        private boolean isResponseFrame(String frameString) {
            return NumberConvert.hexStrToBinaryStr(frameString.substring(8, 10)).substring(0, 1).equalsIgnoreCase("1");
        }


        /**
         * @param frameString
         * @return 0 帧格式正确
         * -1 null或不是16进制
         * -2 长度不对，响应帧最少也要8字节，命令帧则最少需要7字节，此时d数据域为空,或者字符串长度为奇数
         * -3 帧起始码、帧结束码 格式不对
         * -4 长度域值与 该帧字符串不符合
         * -5 校验值不符
         */

        public int checkSafeUnit2Frame(String frameString) {
            if (TextUtils.isEmpty(frameString) || !NumberConvert.isHexStr(frameString)) return ERROR_NULL_OR_NOT_HEX;
            int len = frameString.length();
            if (len < 10) return ERROR_STRING_LENGTH;
            int frameMinLen = isResponseFrame(frameString) ? 16 : 14;
            if (len < frameMinLen || len % 2 != 0) return ERROR_STRING_LENGTH;
            if (!(frameString.substring(0, 2).equalsIgnoreCase("E9") && frameString.substring(len - 2, len).equalsIgnoreCase("E6")))
                return ERROR_FRAME_HEAD_TAIL_CODE;
            if (!frameString.substring(2, 6).equalsIgnoreCase(NumberConvert.toHexStrWithAddZero((len - 10) / 2, 4)))
                return ERROR_FRAME_LENGTH_CODE;
            // System.out.println("cs  "+NumberConvert.getCs(frameString.substring(0, len - 4))+  "      ");
            if (!(frameString.substring(len - 4, len - 2).equalsIgnoreCase(NumberConvert.getCs(frameString.substring(0, len - 4)))))
                return ERROR_FRAME_CHECK_CODE;
            return NO_ERROR;
        }

        public String toFormatString() {
            return parseCode == 0  // 根据解析码状态，当为0 时解析成功
                    ? formatNormal()
                    : formatError();
        }

        /**
         * 报文能正常解析
         *
         * @return String
         */
        private String formatNormal() {
            final String DEFAULT_PATTERN =
                    "安全单元帧报文(%s)： %s\n" +  // 响应帧或者请求帧
                            "%s  帧起始码 E9H\n" +
                            "%s  帧长度 L %d字节 (主功能标识到数据域最后一个字节结束的字节数)\n" +  //帧长度 + 释义
                            "%s  主功能标识 F  %s\n" +  //主功能标识 + 释义
                            "%s  %s  %s\n" + // 控制码C或者响应码A + 释义
                            "%s%s" + // 状态码STATUS或者"" + 释义
                            "%s" + // 数据域
                            "%s  帧校验 CS\n" +
                            "%s  帧结束码 E6H \n\n" +
                            "%s";
            String command_responseStr = isResponseFrame() ? "响应帧" : "请求帧";
            String frameLen = frameStr.substring(2, 6);
            String mainSin = getMainSign();
            String command_responseCode = getCommandResponseCode();
            String command_response = isResponseFrame() ? "响应码 A" : "控制码 C";
            String statusCode = getStatusCode();
            String status = isResponseFrame() ? "  状态码 S " : "";
            String data = getData();
            String dataStr = TextUtils.isEmpty(data) ? "数据域为空\n" : "" + data + "  数据域\n";
            return String.format(Locale.CHINA, DEFAULT_PATTERN,
                    command_responseStr, frameStr,
                    frameStr.substring(0, 2), // 帧起始码
                    frameLen, Integer.parseInt(frameLen, 16),//帧长度 L ， 16进制的帧长度
                    mainSin, getMainSignInstructions(mainSin), // 主功能标识 + 释义
                    command_responseCode, command_response, getCommandResponseCodeInstructions(), // 控制码C或者响应码A + 释义
                    statusCode + status, getStatusCodeInstructions() + (TextUtils.isEmpty(status + getStatusCodeInstructions()) ? "" : "\n"), // 状态码STATUS或者"" + 释义
                    dataStr,// 数据域 DATA
                    frameStr.substring(frameStr.length() - 4, frameStr.length() - 2), // 帧校验 CS
                    frameStr.substring(frameStr.length() - 2, frameStr.length()), // 帧结束码 E6H
                    getDataFormat()
            );
        }

        /**
         * 获取链路数据域格式化字符串
         *
         * @return
         */
        private String getDataFormat() {
            String dataStr = getData();
            if (TextUtils.isEmpty(dataStr)) return "";
            return "数据域解析如下：\n  %s\n";
        }

        /**
         * 获取状态码释义
         *
         * @return String
         */
        private String getStatusCodeInstructions() {
            return isResponseFrame()
                    ? getStatusCodeInstructions(getMainSign(), getCommandResponseCode(), getStatusCode())
                    : "";
        }

        private String getStatusCodeInstructions(String mainSign, String response, String status) {
            return "";
            //return SafeUnit2Manager.getInstance(mainSign).getStatusInfo(response,status);
        }

        /**
         * 获取响应码码或者控制码释义
         *
         * @return String
         */
        private String getCommandResponseCodeInstructions() {
            // 02,03,04,05,06
            // return SafeUnit2Manager.getInstance(getMainSign()).getCommandResponseInfo(getCommandResponseCode());
            return "";
        }


        /**
         * 获取主功能标识释义
         *
         * @param mainSign
         * @return String
         */
        private String getMainSignInstructions(String mainSign) {
            return "";
            // return SafeUnit2Manager.getInstance(mainSign).getMainSignInfo();
        }

        /**
         * 报文解析失败
         *
         * @return
         */
        private String formatError() {
            final String ERROR_PATTERN =
                    "安全单元帧解析失败 ：" +
                            "错误码： %d   " +  // 错误码
                            "错误原因： %s " +  // 错误原因
                            "";
            return String.format(Locale.CHINA, ERROR_PATTERN,
                    parseCode, // 错误码
                    getErrorStr(parseCode)); // 错误原因
        }


    }


}