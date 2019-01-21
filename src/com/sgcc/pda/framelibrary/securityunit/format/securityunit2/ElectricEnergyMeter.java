package com.sgcc.pda.framelibrary.securityunit.format.securityunit2;


import com.sgcc.pda.framelibrary.securityunit.SecurityUnitFrame;
import com.sgcc.pda.framelibrary.securityunit.format.DataFormat;
import com.sgcc.pda.framelibrary.securityunit.format.ISecurityFrameFormat;

import java.util.HashMap;

/**
 * Created by qinling on 2018/6/28 11:28
 * Description: 主功能码： 02 现场服务终端与电能表的交互命令

 */
public class ElectricEnergyMeter implements ISecurityFrameFormat {

    private HashMap<Integer, String> meter698safTransmissionDecryption() {
        HashMap<Integer, String> errors = new HashMap<>();
        errors.put(0x01, "RESPONSE不对");
        errors.put(0x02, "Y-ESAM解密明文+MAC失败");
        errors.put(0x03, "Y-ESAM解密密文失败 ");
        errors.put(0x04, "Y-ESAM解密密文+MAC失败 ");
        errors.put(0x05, "含数据验证信息数据不对 ");
        return errors;
    }


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
