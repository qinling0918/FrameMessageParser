package com.sgcc.pda.framelibrary.protocol698.apdu;


import com.sgcc.pda.framelibrary.utils.NumberConvert;
import com.sgcc.pda.framelibrary.utils.TextUtils;

/**
 * Created by qinling on 2019/5/28 17:07
 * Description:
 */
public abstract class APDUParser implements IAPDUParser {

    public static final int PARSER_SUCCESS = 0;
    public static final String PARSER_SPLITER = "_";
    private GetAPDUParser parser;
    protected final String apduStr;

    private APDUParser(String apduStr, ParseResult parseResult) {
        this.apduStr = apduStr;
        this.parseResult = parseResult;
        this.parseCode = parseResult.getCode();
    }

    public final int parseCode;
    public final ParseResult parseResult;

    public APDUParser(String apduStr) {
        this.apduStr = apduStr;
        this.parseResult = checkAPDUData(apduStr);
        this.parseCode = parseResult.getCode();
    }

    /**
     * @param apduStr 698帧 数据域
     * @return ParseResult
     */
    private ParseResult checkAPDUData(String apduStr) {
        if (TextUtils.isEmpty(apduStr)) {
            return ParseResult.ERROR_NULL;
        }
        if (!NumberConvert.isHexStr(apduStr)) {
            return ParseResult.ERROR_NOT_HEX;
        }
        int len = apduStr.length();
        if (len % 2 != 0) {
            return ParseResult.ERROR_NOT_BYTES;
        }
        if (len < 18) {
            return ParseResult.ERROR_LENGTH;
        }
        int classify = Integer.parseInt(apduStr.substring(0, 2), 16);
        if (classify != getClassify()) {
            return ParseResult.ERROR_PARSER_NOT_MATCH;
        }
        // int type = getResponseType(apduStr);  // 获取数据单元类型
        return ParseResult.SUCCESS;
    }

    public enum ParseResult {
        SUCCESS(0X00, "解析成功"),
        ERROR_NULL(-1, " 数据为空"),
        ERROR_NOT_HEX(-2, " 数据不是16进制"),
        ERROR_NOT_BYTES(-3, " 字符串长度不是2的倍数"),
        ERROR_LENGTH(-4, " 格式不正确，该相应数据字节数小于9字节"),
        ERROR_PARSER_NOT_MATCH(-5, " 解析器不匹配，该解析器只能解析 读取响应帧（85）"),
        ERROR_UNKNOWN_TYPE(-6, " 读取响应的数据类型错误 ");

        ParseResult(int code, String errorMsg) {
            this.code = code;
            this.errorMsg = errorMsg;
        }

        private int code;

        public int getCode() {
            return code;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        private String errorMsg;

    }


    public static APDUParser ERROR_PARSER(ParseResult parseResult, final String errorData) {
        return new APDUParser(errorData) {
            @Override
            public int getClassify() {
                return 0;
            }

            @Override
            public String toFormatString() {
                return errorData;
            }

        };
    }

    public static APDUParser ERROR_PARSER(final String errorData) {
        return ERROR_PARSER(ParseResult.ERROR_UNKNOWN_TYPE, errorData);
    }

    public static APDUParser ERROR_PARSER(ParseResult parseResult) {
        return ERROR_PARSER(parseResult, parseResult.errorMsg);
    }
}
