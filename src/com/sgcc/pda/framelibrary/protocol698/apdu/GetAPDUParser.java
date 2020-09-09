package com.sgcc.pda.framelibrary.protocol698.apdu;

import com.sgcc.pda.framelibrary.FormatAble;
import com.sgcc.pda.framelibrary.protocol698.apdu.obj.Energy;
import com.sgcc.pda.framelibrary.protocol698.apdu.obj.Obj;
import com.sgcc.pda.framelibrary.protocol698.apdu.obj.ParamsVariable;
import com.sgcc.pda.framelibrary.utils.RecoverableString;


import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by qinling on 2018/5/22 11:08
 * Description:
 */
public class GetAPDUParser extends APDUParser {
    public static final int PARSER_SUCCESS = 0;
    private static final String PARSER_SPLITER = "_";

    // public final Obj obj;
    //
    public static final int GET_RESPONSE = 0x85;

    private static final int GET_RESPONSE_NORMAL = 0x01;
    private static final int GET_RESPONSE_NORMAL_LIST = 0x02;
    private static final int GET_RESPONSE_RECORD = 0x03;
    private static final int GET_RESPONSER_ECORD_LIST = 0x04;
    private static final int GET_RESPONSE_NEXT = 0x05;
    private static final int GET_RESPONSE_MD5 = 0x06;

    public GetAPDUParser(String apduStr) {
        super(apduStr);
    }


    protected int getResponseType(String apduStr) {
        return Integer.parseInt(apduStr.substring(2, 4), 16);
    }

    private ArrayList<FormatAble> parseAPDUDataByType(String apduStr, int type) {
        RecoverableString afterPiidStr = new RecoverableString(apduStr);
        afterPiidStr.substring(6);
        switch (type) {
            case GET_RESPONSE_NORMAL:
                String oadStr = afterPiidStr.substring(0, 8);
                return parseGetResponseNormal(oadStr,
                        afterPiidStr);
            case GET_RESPONSE_NORMAL_LIST:
                return parseGetResponseNormalList(afterPiidStr);
            case GET_RESPONSE_RECORD:
                return parseGetResponseRecord(afterPiidStr);
           /*   case GET_RESPONSER_ECORD_LIST:
                return parseGetResponseRecordList(afterPiidStr);
            case GET_RESPONSE_NEXT:
                return parseGetResponseNext(afterPiidStr);
            case GET_RESPONSE_MD5:
                return parseGetResponseMD5(afterPiidStr);*/
            default:
                ArrayList<FormatAble> formatAbles = new ArrayList<FormatAble>();
                formatAbles.add(Obj.ERROR("未知类型，解析出错"));
                return formatAbles;
        }

    }

    /*  private Obj parseGetResponseMD5(String substring) {
          return SUCCESS;
      }

      private Obj parseGetResponseNext(String substring) {
          return SUCCESS;
      }

      private Obj parseGetResponseRecordList(String substring) {
          return SUCCESS;
      }
 */

    private ArrayList<FormatAble> parseGetResponseRecord(RecoverableString afterPiidStr) {
        // ArrayList<FormatAble> formatAbles = new ArrayList<>();
        String oad = afterPiidStr.substring(0, 8);
        // Obj obj = Obj.newInstance(oad,afterPiidStr);
        // 冻结类：

       /* if (oad.startsWith("50") || oad.startsWith("601203") || oad.startsWith("2033")) {
            return parseFreezeRecord(oad, afterPiidStr);
        } else {
            return parseGetResponseNormal(oad, afterPiidStr);
        }*/
        // return formatAbles;
        return parseFreezeRecord(oad, afterPiidStr);
    }


    private ArrayList<FormatAble> parseFreezeRecord(String oad, RecoverableString afterPiidStr) {
        ArrayList<FormatAble> formatAbles = new ArrayList<>();
      /*  Freeze freeze = new Freeze(oad,afterPiidStr);
        String freezeName = freeze.objName;*/
        int rcsdCount = Integer.parseInt(afterPiidStr.substring(0, 2), 16);
        String[] oads;
        if (rcsdCount < 0) {
            formatAbles.add(Obj.ERROR(afterPiidStr.getOriginalString() + "\n解析失败，数据格式不正确！"));
            return formatAbles;
        }

        oads = new String[rcsdCount];
        for (int i = 0; i < rcsdCount; i++) {
            // 0 为 oad , 1为 road
            String choice = afterPiidStr.substring(0, 2);
            if (choice.equals("00")) {
                oads[i] = afterPiidStr.substring(0, 8);
            } else {
                // ROAD 下， 会分 两层OAD结构。
                String oadParent = afterPiidStr.substring(0, 8);
                StringBuilder roadBuilder = new StringBuilder(oadParent);
                int oadChildCount = Integer.parseInt(afterPiidStr.substring(0, 2), 16);
                String[] oadChild = new String[oadChildCount];
                for (int j = 0; j < oadChildCount; j++) {
                    oadChild[j] = afterPiidStr.substring(0, 8);
                    roadBuilder.append(PARSER_SPLITER).append(oadChild[j]);
                }
                // ROAD 的结构 是 50040200 00100200 00200200 其中第一个是外层Oad，紧跟着的是内部的两个
                oads[i] = roadBuilder.toString();
            }
        }

        if (oads.length >= 0) {
            String choice = afterPiidStr.substring(0, 2);
            // data或者Dar对应的数据，但后缀还有其他值
            // String data_dar = getResult.substring(2);
            // 表示为DAR
            if ("00".equals(choice)) {
                formatAbles.add(Obj.dar(oad, afterPiidStr));
                return formatAbles;
            }

            int msCount = Integer.parseInt(afterPiidStr.substring(0, 2), 16);
            if (msCount < 0) {
                formatAbles.add(Obj.ERROR("解析失败！"));
                return formatAbles;
            } else if (msCount == 0) {
                for (int i = 0; i < oads.length; i++) {
                    String oi = oads[i].substring(0,4);
                    formatAbles.add(Obj.ERROR(OIManager.getInstance().getObjName(oi)+"数据记录为空！"));
                }
               // formatAbles.add(Obj.ERROR(OIManager.getInstance().getObjName(oad.substring(0,4))+ "数据记录为空！"));
              //  formatAbles.add(Obj.ERROR("数据记录为空！"));
                return formatAbles;
            }
            // 多个MS
            for (int i = 0; i < msCount; i++) {
                // 单个Ms
                for (String oadStr : oads) {
                    if (oadStr.length() == 8 && !oadStr.contains(PARSER_SPLITER)) {
                        formatAbles.addAll(parseGetResponseRecordNormal(oadStr, afterPiidStr));
                    } else {
                        String[] oadArr = oadStr.split(PARSER_SPLITER);
                        // 接下来的数据 可能是 array，也可能是structure
                        int type = Integer.parseInt(afterPiidStr.substring(0, 2), 16);
                        if (type == 0x01 || type == 0x02) {
                            int dataCount = Integer.parseInt(afterPiidStr.substring(0, 2), 16);
                            for (int j = 0; j < dataCount; j++) {
                                formatAbles.addAll(parseGetResponseRecordNormal(oadArr[j + 1], afterPiidStr));
                            }
                        }
                    }
                }
            }
        }
        return formatAbles;
    }

    /**
     * 多个对象时，new 每个对象时，都要先执行format,让每个对象都获取到自己的数据，否则会出现数据错乱问题
     *
     * @param afterPiidStr PIID以后的字符串
     * @return
     */
    private ArrayList<FormatAble> parseGetResponseNormalList(RecoverableString afterPiidStr) {
        int itemCount = Integer.parseInt(afterPiidStr.substring(0, 2));
        ArrayList<FormatAble> formatAbles = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            String oadStr = afterPiidStr.substring(0, 8);
            formatAbles.addAll(parseGetResponseNormal(oadStr, afterPiidStr));
        }
        return formatAbles;
    }


    private ArrayList<FormatAble> parseGetResponseRecordNormal(String oadStr, RecoverableString getResult) {
        ArrayList<FormatAble> formatAbles = new ArrayList<>();
        formatAbles.add(Obj.newInstance(oadStr, getResult));
        return formatAbles;
    }

    private ArrayList<FormatAble> parseGetResponseNormal(String oadStr, RecoverableString getResult) {
        ArrayList<FormatAble> formatAbles = new ArrayList<>();

        String choice = getResult.substring(0, 2);
        // data或者Dar对应的数据，但后缀还有其他值
        // 表示为DAR
        if ("00".equals(choice)) {
            formatAbles.add(Obj.dar(oadStr, getResult));
        } else {
            formatAbles.add(Obj.newInstance(oadStr, getResult));
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
                /*if (formatAble instanceof Energy){
                    Energy energy =  (Energy) formatAble;

                    System.out.println(Arrays.toString(energy.getRatePower()));
                }
                Obj obj =  (Obj) formatAble;*/
               /* String oad = obj.oadHexStr;
                OIManager.getInstance().getObjName(oad.substring(0,4));*/

                stringBuilder.append(formatAble.toFormatString()).append("\n");
            }
            return stringBuilder.toString();
        }

    }

    @Override
    public int getClassify() {
        return GET_RESPONSE;
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
