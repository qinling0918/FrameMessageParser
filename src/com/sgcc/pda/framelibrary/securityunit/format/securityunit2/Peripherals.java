package com.sgcc.pda.framelibrary.securityunit.format.securityunit2;


import com.sgcc.pda.framelibrary.securityunit.SecurityUnitFrame;
import com.sgcc.pda.framelibrary.securityunit.format.DataFormat;
import com.sgcc.pda.framelibrary.securityunit.format.ISecurityFrameFormat;

/**
 * Created by qinling on 2018/5/14 15:05
 * Description: 06 现场服务终端与外设交互命令
 *

 */
public class Peripherals implements ISecurityFrameFormat {
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

   /*implements IPeripherals {
    @Override
    public int mainSign() {
        return 6;
    }

    @Override
    public String mainSignStr() {
        return "06";
    }*/
}
