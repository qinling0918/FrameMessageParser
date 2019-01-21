package com.sgcc.pda.framelibrary.securityunit.format;

import com.sgcc.pda.framelibrary.securityunit.SecurityUnitFrame;
import com.sgcc.pda.framelibrary.utils.TextUtils;

import java.util.Locale;

/**
 * Created by qinling on 2018/6/7 9:23
 * Description:
 */
public class SecurityUnitFrameFormat  {


    private SecurityUnitFrame.Parser parser;
    private String frameStr;
    public SecurityUnitFrameFormat(SecurityUnitFrame frame) {
        this(frame.toHexString());
    }

    public SecurityUnitFrameFormat(String frameStr) {
        this.frameStr = frameStr;
        parser = new SecurityUnitFrame.Parser(frameStr);
    }

    /**
     * 格式化
     * @return String
     */
    public String format() {
        return parser.parseCode==0  // 根据解析码状态，当为0 时解析成功
                ? formatNormal()
                : formatError();
    }

    /**
     * 报文能正常解析
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
                        "%s"  ;
        String command_responseStr = parser.isResponseFrame()?"响应帧":"请求帧";
        String frameLen = frameStr.substring(2,6);
        String mainSin = parser.getMainSign();
        String command_responseCode = parser.getCommandResponseCode();
        String command_response = parser.isResponseFrame()?"响应码 A":"控制码 C";
        String statusCode = parser.getStatusCode();
        String status = parser.isResponseFrame()?"  状态码 S ":"";
        String data = parser.getData();
        String dataStr = TextUtils.isEmpty(data)?"数据域为空\n":""+data+"  数据域\n";
        return String.format(Locale.CHINA,DEFAULT_PATTERN,
                command_responseStr,frameStr,
                frameStr.substring(0,2), // 帧起始码
                frameLen , Integer.parseInt(frameLen,16),//帧长度 L ， 16进制的帧长度
                mainSin,getMainSignInstructions(mainSin), // 主功能标识 + 释义
                command_responseCode,command_response,getCommandResponseCodeInstructions(), // 控制码C或者响应码A + 释义
                statusCode+status,getStatusCodeInstructions()+(TextUtils.isEmpty(status+getStatusCodeInstructions())?"":"\n"), // 状态码STATUS或者"" + 释义
                dataStr,// 数据域 DATA
                frameStr.substring(frameStr.length()-4,frameStr.length()-2), // 帧校验 CS
                frameStr.substring(frameStr.length()-2,frameStr.length()), // 帧结束码 E6H
                getDataFormat()
        );
    }

    /**
     *  获取链路数据域格式化字符串
     * @return
     */
    private String getDataFormat() {
        String dataStr = parser.getData();
        if (TextUtils.isEmpty(dataStr)) return "";
        return "数据域解析如下：\n  %s\n";
    }

    /**
     * 获取状态码释义
     * @return  String
     */
    private String getStatusCodeInstructions() {
        return parser.isResponseFrame()
                ?getStatusCodeInstructions(parser.getMainSign(),parser.getCommandResponseCode(),parser.getStatusCode())
                :"";
    }

    private String getStatusCodeInstructions(String mainSign,String response, String status) {
        return SafeUnit2Manager.getInstance(mainSign).getStatusInfo(response,status);
    }

    /**
     * 获取响应码码或者控制码释义
     * @return  String
     */
    private String getCommandResponseCodeInstructions() {
        // 02,03,04,05,06
        return SafeUnit2Manager.getInstance(parser.getMainSign()).getCommandResponseInfo(parser.getCommandResponseCode());
    }



    /**
     * 获取主功能标识释义
     * @param mainSign
     * @return  String
     */
    private String getMainSignInstructions(String mainSign) {
        return SafeUnit2Manager.getInstance(mainSign).getMainSignInfo();
    }

    /**
     * 报文解析失败
     * @return
     */
    private String formatError() {
          final String ERROR_PATTERN =
                "安全单元帧解析失败 ：" +
                        "错误码： %d   " +  // 错误码
                        "错误原因： %s " +  // 错误原因
                        "";
        return String.format(Locale.CHINA,ERROR_PATTERN,
                parser.parseCode, // 错误码
                parser.getErrorStr(parser.parseCode)); // 错误原因
    }



}