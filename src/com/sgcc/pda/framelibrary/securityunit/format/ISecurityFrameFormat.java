package com.sgcc.pda.framelibrary.securityunit.format;

import org.jf.util.SparseArray;

import java.util.Locale;


public interface ISecurityFrameFormat {
    String getMainSignInfo();

    String getCommandResponseInfo(int response_commandCode);

    String getStatusInfo(int response_commandCode, int statusCode);

    String getDataFormat(int response_commandCode, int statusCode, String data);
}
