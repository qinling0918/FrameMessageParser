package com.sgcc.pda.framelibrary.protocol698.apdu;

/**
 * Created by qinling on 2019/5/9 17:55
 * Description:
 */
public class FrameParserException extends RuntimeException {
    public FrameParserException(Exception e) {
        super(e);
    }

    public FrameParserException() {
        super(new Exception("帧解析失败"));
    }
}
