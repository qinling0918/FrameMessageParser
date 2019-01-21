package com.sgcc.pda.framelibrary.protocol698.apdu;

public abstract class APDU {
    public static final int NO_CLASSIFY = 0x00;
    /******* LINK_APDU  ******************/

    /**
     * 预连接请求
     */
    public static final int LINK_REQUEST = 0x01;
    /**
     * 预连接响应
     */

    public static final int LINK_RESPONSE = 0x81;

    /******* Client_APDU ******************/

    /**
     * 建立应用连接
     */
    public static final int CONNECT_REQUEST = 0x02;
    /**
     * 断开应用链接
     */
    public static final int RELEASE_REQUEST = 0x03;
    /**
     * 读取请求
     */
    public static final int GET_REQUEST = 0x05;
    /**
     * 设置请求
     */
    public static final int SET_REQUEST = 0x06;
    /**
     * 操作请求
     */
    public static final int ACTION_REQUEST = 0x07;
    /**
     * 上报应答
     */
    public static final int REPORT_RESPONSE = 0x08;
    /**
     * 代理请求
     */
    public static final int PROXY_REQUEST = 0x09;
    /**
     * Client  异常响应
     */
    public static final int ERROR_RESPONSE_CLIENT = 0x6E;

    /******* Server_APDU ******************/

    /**
     * 建立应用连接响应
     */
    public static final int CONNECT_RESPONSE = 0x82;
    /**
     * 断开应用连接响应
     */
    public static final int RELEASE_RESPONSE = 0x83;
    /**
     * 断开应用连接通知
     */
    public static final int RELEASE_NOTIFICATION = 0x84;
    /**
     * 读取响应
     */
    public static final int GET_RESPONSE = 0x85;
    /**
     * 设置响应
     */
    public static final int SET_RESPONSE = 0x86;
    /**
     * 操作响应
     */
    public static final int ACTION_RESPONSE = 0x87;
    /**
     * 上报通知
     */
    public static final int REPORT_NOTIFICATION = 0x88;
    /**
     * 代理响应
     */
    public static final int PROXY_RESPONSE = 0x89;
    /**
     * Client  异常响应
     */
    public static final int ERROR_RESPONSE_SERVER = 0xEE;

    /******* SECURITY_APDU  ******************/

    public static final int SECURITY_REQUEST = 0x10; //0x10 == 16
    public static final int SECURITY_RESPONSE = 0x90; //0X90 == 144

    //TYPE
    // 读取,操作,代理等(請求/响应)的数据类型,与mode
    public static final int NO_TYPE = 0x00;
    public static final int TYPE1 = 0x01;
    public static final int TYPE2 = 0x02;
    public static final int TYPE3 = 0x03;
    public static final int TYPE4 = 0x04;
    public static final int TYPE5 = 0x05;
    public static final int TYPE6 = 0x06;
    public static final int TYPE7 = 0x07;

    //IAPDUParser parse(String apduStr);
   // abstract String build();

}