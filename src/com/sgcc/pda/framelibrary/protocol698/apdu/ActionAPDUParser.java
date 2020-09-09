package com.sgcc.pda.framelibrary.protocol698.apdu;



import com.sgcc.pda.framelibrary.FormatAble;
import com.sgcc.pda.framelibrary.protocol698.apdu.obj.Obj;
import com.sgcc.pda.framelibrary.utils.RecoverableString;

import java.util.ArrayList;


/**
 * Created by qinling on 2018/5/22 11:08
 * Description:
 */
public class ActionAPDUParser extends APDUParser {

    public static final int ACTION_RESPONSE = 0x87;

    private static final int ACTION_RESPONSE_NORMAL = 0x01;
    private static final int ACTION_RESPONSE_NORMAL_LIST = 0x02;
    private static final int ACTION_THEN_GET_RESPONSE_NORMAL_LIST = 0x03;


    public ActionAPDUParser(String apduStr) {
        super(apduStr);
    }


    protected int getResponseType(String apduStr) {
        return Integer.parseInt(apduStr.substring(2, 4), 16);
    }

    private ArrayList<FormatAble> parseAPDUDataByType(String apduStr, int type) {
        RecoverableString afterPiidStr = new RecoverableString(apduStr);
        // 870100800180000000000107E3051C0F3B1B030002
        afterPiidStr.substring(6);
        // 800180000000000107E3051C0F3B1B030002
        switch (type) {
            case ACTION_RESPONSE_NORMAL:
                String oadStr = afterPiidStr.substring(0, 8);
                return parseActionResponseNormal(oadStr, afterPiidStr);
            case ACTION_RESPONSE_NORMAL_LIST:
//                return parseActionResponseNormalList(afterPiidStr);
            case ACTION_THEN_GET_RESPONSE_NORMAL_LIST:
                // todo 暂未实现
            default:
                ArrayList<FormatAble> formatAbles = new ArrayList<FormatAble>();
                formatAbles.add(Obj.ERROR("未知类型，解析出错"));
                return formatAbles;
        }

    }


    private ArrayList<FormatAble> parseActionResponseNormalList(RecoverableString afterPiidStr) {
        int itemCount = Integer.parseInt(afterPiidStr.substring(0, 2));
        ArrayList<FormatAble> formatAbles = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            String oadStr = afterPiidStr.substring(0, 8);
            formatAbles.addAll(parseActionResponseNormal(oadStr, afterPiidStr));
        }
        return formatAbles;
    }


    // oadStr = 80018000
    // afterPiidStr = 0000000107E3051C0F3B1B030002
    private ArrayList<FormatAble> parseActionResponseNormal(String oadStr, RecoverableString getResult) {
        ArrayList<FormatAble> formatAbles = new ArrayList<>();
        // 这里不是错误，仅仅是为了添加字符串
        //String objName = OIManager.getInstance().getObjName(oadStr.substring(0,4));
        // formatAbles.add(Obj.newInstance(objName+": "));
        formatAbles.add(Obj.dar(oadStr, getResult));
        // data optional

        String choice = getResult.substring(0, 2);
        // data optional  为 Null
        if ("00".equals(choice)) {
            // formatAbles.add(Obj.newInstance("无操作返回数据"));
           /* StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("无操作返回数据\n")
                    .append("上报跟随信息域：")
            formatAbles.add(Obj.newInstance(stringBuilder.toString()));*/
        } else {
            formatAbles.add(Obj.newInstance("暂未支持解析"));
        }
        return formatAbles;
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

    @Override
    public int getClassify() {
        return ACTION_RESPONSE;
    }


    /**
     * 解析数据
     *
     * @param numStrUnsigned
     * @param radix
     * @return
     */
    public int parseInt(String numStrUnsigned, int radix) {
        try {
            return Integer.parseInt(numStrUnsigned, radix);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
