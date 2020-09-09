package com.sgcc.pda.framelibrary.protocol698.apdu;

import com.sgcc.pda.framelibrary.FormatAble;
import com.sgcc.pda.framelibrary.protocol698.apdu.obj.Obj;
import com.sgcc.pda.framelibrary.utils.RecoverableString;

import java.util.ArrayList;

/**
 * 描述：
 *
 * @author Abel
 * @version 1.0
 * @date 2019/6/24 17:24
 */
public class SetAPDUParser extends APDUParser {

    public static final int SET_RESPONSE = 0x86;

    private static final int SET_RESPONSE_NORMAL = 0x01;
    private static final int SET_RESPONSE_NORMAL_LIST = 0x02;
    private static final int SET_THEN_GET_RESPONSE_NORMAL_LIST = 0x03;

    public SetAPDUParser(String apduStr) {
        super(apduStr);
    }

    /**
     * 返回responseType
     */
    private int getResponseType(String apduStr) {
        return Integer.parseInt(apduStr.substring(2, 4), 16);
    }

    /**
     * 根据Type 解析数据
     *
     * @param apduStr 要解析的数据
     * @param type    返回类型
     */
    private ArrayList<FormatAble> parseAPDUDataByType(String apduStr, int type) {
        RecoverableString afterPiidStr = new RecoverableString(apduStr);
        afterPiidStr.substring(6);
        switch (type) {
            case SET_RESPONSE_NORMAL:
                String oadStr = afterPiidStr.substring(0, 8);
                return parseSetResponseNormal(oadStr, afterPiidStr);
            case SET_RESPONSE_NORMAL_LIST:
                return parseSetResponseNormalList(afterPiidStr);
            case SET_THEN_GET_RESPONSE_NORMAL_LIST:
                // todo 暂未实现
            default:
                ArrayList<FormatAble> formatAbles = new ArrayList<>();
                formatAbles.add(Obj.ERROR("未知类型，解析出错"));
                return formatAbles;
        }

    }

    /**
     * 设置若干个对象属性的确认信息响应 解析
     *
     * @param afterPiidStr 要解析的数据
     */
    private ArrayList<FormatAble> parseSetResponseNormalList(RecoverableString afterPiidStr) {
        int itemCount = Integer.parseInt(afterPiidStr.substring(0, 2));
        ArrayList<FormatAble> formatAbles = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            String oadStr = afterPiidStr.substring(0, 8);
            formatAbles.addAll(parseSetResponseNormal(oadStr, afterPiidStr));
        }
        return formatAbles;
    }

    /**
     * 设置一个对象属性的确认信息响应 解析
     *
     * @param oadStr       OAD
     * @param afterPiidStr 要解析的数据
     */
    private ArrayList<FormatAble> parseSetResponseNormal(String oadStr, RecoverableString afterPiidStr) {
        ArrayList<FormatAble> formatAbles = new ArrayList<>();
        // 这里不是错误，仅仅是为了添加字符串
        formatAbles.add(Obj.dar(oadStr, afterPiidStr));
        // data optional
        String choice = afterPiidStr.substring(0, 2);
        // data optional  为 Null
        if ("00".equals(choice)) {
            formatAbles.add(Obj.newInstance("无操作返回数据"));
        } else if ("06".equals(choice)){
            formatAbles.add(Obj.newInstance("对象不存在"));
        } else {
            formatAbles.add(Obj.newInstance("对象不存在"));
        }
        return formatAbles;
    }

    @Override
    public int getClassify() {
        return SET_RESPONSE;
    }

    @Override
    public String toFormatString() {
        if (parseCode != 0) {
            return parseResult.getErrorMsg();
        }

        ArrayList<FormatAble> formatAbles = parseAPDUDataByType(apduStr, getResponseType(apduStr));
        if (formatAbles == null || formatAbles.isEmpty()) {
            return "暂未支持解析！";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (FormatAble formatAble : formatAbles) {
                stringBuilder.append(formatAble.toFormatString()).append("\n");
            }
            return stringBuilder.toString();
        }
    }
}