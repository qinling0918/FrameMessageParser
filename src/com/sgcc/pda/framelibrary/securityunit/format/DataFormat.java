package com.sgcc.pda.framelibrary.securityunit.format;

import com.sgcc.pda.framelibrary.protocol698.Frame698;
import com.sgcc.pda.framelibrary.securityunit.SecurityUnitFrame;

/**
 * Created by qinling on 2018/10/19 15:31
 * Description:
 */
public  abstract class DataFormat /*implements ISecurityFrameFormat*/{
    public final String mainSignInstructions; // 主功能标识符
    public final String dataFormat; //数据域
    public final String command_responseInstructions; //请求码或者响应码
    public final String statusInstructions; //状态码

    protected SecurityUnitFrame.Parser parser;

    public DataFormat(SecurityUnitFrame.Parser parser) {

        this.parser = parser;
        mainSignInstructions = getMainSignInstructions();
        command_responseInstructions = getCommandResponseCodeInstructions();
        statusInstructions = getStatusCodeInstructions();
        dataFormat = getDataFormat();
    }

    public abstract String getMainSignInstructions();
    public abstract String getDataFormat() ;
    public abstract String getCommandResponseCodeInstructions() ;
    public abstract String getStatusCodeInstructions() ;

    public static final DataFormat DEFAULT = new DataFormat(null) {
        @Override
        public String getMainSignInstructions() {

           new Frame698.Builder().build().toHexString();

            return "";
        }

        @Override
        public String getDataFormat() {
            return "";
        }

        @Override
        public String getCommandResponseCodeInstructions() {
            return "";
        }

        @Override
        public String getStatusCodeInstructions() {
            return "";
        }
    };
}
