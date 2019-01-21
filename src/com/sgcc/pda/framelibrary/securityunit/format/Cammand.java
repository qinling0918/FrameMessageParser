package com.sgcc.pda.framelibrary.securityunit.format;

import com.sgcc.pda.framelibrary.securityunit.DataUnit;
import com.sgcc.pda.framelibrary.securityunit.SecurityUnitFrame;

public class Cammand {
    String cammandInfo; //锁定安全单元
    DataUnit[] dataInfos;// {[操作员旧密码	3	ASCII	0-9，A-Z，a-z，空格表示密码串的结束符],[操作员新密码	3	ASCII	0-9，A-Z，a-z，空格表示密码串的结束符]}
    String[] statusInfos; //

    public Cammand(String cammandInfo, DataUnit[] dataInfos) {
        this.cammandInfo = cammandInfo;
        this.dataInfos = dataInfos;

        int start = 0;
        for (DataUnit dataInfo : dataInfos) {
            int datalength = dataInfo.byteSize;
            datalength = datalength == -1 ? Integer.parseInt(cammandInfo.substring(start,start+2),16):datalength;
            int end = start + datalength * 2;
            System.out.println(cammandInfo.substring(start, end)+"  "+dataInfo.dataName);
            start = end;
        }


    }


}
