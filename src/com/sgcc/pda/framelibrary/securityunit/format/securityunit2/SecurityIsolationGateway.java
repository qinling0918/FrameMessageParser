package com.sgcc.pda.framelibrary.securityunit.format.securityunit2;


import com.sgcc.pda.framelibrary.securityunit.SecurityUnitFrame;
import com.sgcc.pda.framelibrary.securityunit.format.DataFormat;
import com.sgcc.pda.framelibrary.securityunit.format.ISecurityFrameFormat;

/**
 * Created by qinling on 2018/5/14 15:02
 * Description:  现场服务终端与安全隔离网关交互命令
 * 主功能码：03H
 *
 */
public class SecurityIsolationGateway implements ISecurityFrameFormat {
 /*implements ISecurityIsolationGateway {
    @Override
    public int mainSign() {
        return 3;
    }

    @Override
    public String mainSignStr() {
        return "03";
    }*/

    @Override
    public String getMainSignInfo() {
        return null;
    }

    @Override
    public String getCommandResponseInfo(int response_commandCode) {
        return null;
    }

    @Override
    public String getStatusInfo(int response_commandCode, int statusCode) {
        return null;
    }

    @Override
    public String getDataFormat(int response_commandCode, int statusCode, String data) {
        return null;
    }
}
