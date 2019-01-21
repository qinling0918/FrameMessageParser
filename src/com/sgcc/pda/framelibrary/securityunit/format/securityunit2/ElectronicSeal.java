package com.sgcc.pda.framelibrary.securityunit.format.securityunit2;


import com.sgcc.pda.framelibrary.securityunit.SecurityUnitFrame;
import com.sgcc.pda.framelibrary.securityunit.format.DataFormat;
import com.sgcc.pda.framelibrary.securityunit.format.ISecurityFrameFormat;

/**
 * Created by qinling on 2018/5/14 15:03
 * Description: 现场服务终端与电子封印交互命令
 * 主功能码：04H
 */
public class ElectronicSeal implements ISecurityFrameFormat /*implements IElectronicSeal*/ {



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
    /*@Override
    public int mainSign() {
        return 4;
    }

    @Override
    public String mainSignStr() {
        return "04";
    }*/
}
