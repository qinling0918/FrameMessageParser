package com.sgcc.pda.framelibrary.protocol698.apdu.obj;

import com.sgcc.pda.framelibrary.FormatAble;
import com.sgcc.pda.framelibrary.protocol698.apdu.FrameParserException;
import com.sgcc.pda.framelibrary.protocol698.apdu.OIManager;
import com.sgcc.pda.framelibrary.protocol698.apdu.data.DarManager;
import com.sgcc.pda.framelibrary.protocol698.apdu.data.DataManager;
import com.sgcc.pda.framelibrary.protocol698.apdu.data.ScalerUnit;
import com.sgcc.pda.framelibrary.utils.NumberConvert;
import com.sgcc.pda.framelibrary.utils.RecoverableString;


import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Locale;

import static com.sgcc.pda.framelibrary.protocol698.apdu.data.DataManager.LEN_BIT_STRING;
import static com.sgcc.pda.framelibrary.protocol698.apdu.data.DataManager.LEN_FOLLOW;



/**
 * Created by qinling on 2019/5/9 10:05
 * Description:
 */
public abstract class Obj implements FormatAble {

    /**
     * 对象名称
     */
    public String objName;
    /**
     * 接口类id
     */
    protected int interfClass;
    /**
     * 属性
     */
    protected int attributes;
    /**
     * 方法
     */
    protected int method;

    /**
     * OAD 4B
     */
    public String oadHexStr;
    /**
     * OAD 2B
     */
    protected String oiStr;
    /**
     * 索引
     */
    protected int index;
    /**
     * 对象对应字符串
     */
    protected RecoverableString objValueHexStr;

    protected Obj(String objName, int interfClass, String oadHexStr, RecoverableString objValueHexStr) {
        this.objName = objName;
        this.interfClass = interfClass;
        this.oadHexStr = oadHexStr;
        try {
            this.oiStr = oadHexStr.substring(0, 4);
            String binaryStr = NumberConvert.toBinaryStr(Integer
                    .parseInt(oadHexStr.substring(4, 6), 16), 8);
            String attributeTagStr = binaryStr.substring(3, 8);
            this.attributes = Integer.parseInt(attributeTagStr, 2);
            this.method = Integer.parseInt(oadHexStr.substring(4, 6), 16);
            this.index = Integer.parseInt(oadHexStr.substring(6, 8), 16);
        } catch (Throwable e) {
            this.oiStr = "";
            this.attributes = 0;
            this.method = 1;
            this.index = 0;
        }

        this.objValueHexStr = objValueHexStr;
    }

    protected Obj(String oadHexStr, RecoverableString objValueHexStr) {
        this(oadHexStr.substring(0, 4), 0, oadHexStr, objValueHexStr);
    }

    private Obj() {
        this("", 0, "", new RecoverableString(""));
    }

    protected Obj(int interfClass, String oadHexStr, RecoverableString objValueHexStr) {
        this(oadHexStr.substring(0, 4), interfClass, oadHexStr, objValueHexStr);
    }

    public static Obj newInstance(String oadHexStr, RecoverableString dataStr) {
        String oiStr = oadHexStr.substring(0, 4).toUpperCase();
        switch (oiStr.substring(0, 1).toUpperCase(Locale.CHINA)) {
            // 电能量类
            case "0":
                return new Energy(oadHexStr, dataStr);
            // 最大需量类
            case "1":
                return new MaximumDemand(oadHexStr, dataStr);
            // 变量类
            case "2":
                return new Variable(oadHexStr, dataStr);
            case "3":
                return new Event(oadHexStr, dataStr);
            case "4":
                return new ParamsVariable(oadHexStr, dataStr);
            case "8":
                return new Control(oadHexStr, dataStr);
            case "F":
                String second = oiStr.substring(1, 2).toUpperCase(Locale.CHINA);
                if ("1".equals(second)) {
                    return new ESam(oadHexStr, dataStr);
                }
            default:
                return getObj(oiStr, oadHexStr, dataStr);
        }
    }

    protected long getNum() {
        return getNum(getValueByDataType());
    }

    protected Long getNum(String value) {
        try {
            return Long.parseLong(value, 16);
        } catch (Throwable e) {
            return -1L;
        }
    }

    private static Obj getObj(String oiStr, String oadHexStr, RecoverableString dataStr) {
        switch (oiStr) {
            case "F301":
            case "F300":
                return new Dispaly(oadHexStr, dataStr);
            default:
                return ERROR("该数据项暂未实现解析");
        }
    }

    public static Obj dar(String oadHexStr, RecoverableString darStr) {
        return new DAR(oadHexStr, darStr);
    }

    protected String getValueByDataType() {
        return getValueByDataType(objValueHexStr);
    }

    protected String getValueByDataType(RecoverableString objValueHexStr) {
        String dataType = objValueHexStr.substring(0, 2);
        int dataSize = DataManager.getInstance().getDataByteSize(dataType);
        if (dataSize < 0) {
            if (dataSize == LEN_FOLLOW || dataSize == LEN_BIT_STRING) {
                dataSize = DataManager.getInstance().getDataByteSize(dataType, objValueHexStr.getCurrentSting());
                objValueHexStr.substring(2);
            } else {
                return null;
            }
        }
        return objValueHexStr.substring(0, dataSize * 2);
    }

    /**
     * 获取格式化后的数据
     *
     * @param value      数值
     * @param scalerUnit 单位
     * @return
     */
    protected String getFormatValueWithUnit(long value, ScalerUnit scalerUnit) {
        return getFormatValueWithUnit(value, scalerUnit.getUnitStr(), scalerUnit.getPow());
    }

    protected String getFormatValueWithUnit(long value, String unit, int pow) {
        double num = (value * (Math.pow(10, pow)));
        StringBuilder foramt = new StringBuilder("#0.0");
        for (int i = 1; i < Math.abs(pow); i++) {
            foramt.append(0);
        }
        foramt.append(unit);
        DecimalFormat decimalFormat = new DecimalFormat(foramt.toString());
        return decimalFormat.format(num);
    }

    FrameParserException exception = new FrameParserException();

    protected void parseException(Exception e) {
        exception = new FrameParserException(e);
    }

    public static class DAR extends Obj {

        protected DAR(String oadHexStr, RecoverableString objValueHexStr) {
            super(oadHexStr, objValueHexStr);
        }

        @Override
        public String toFormatString() {
            String methodName = getMethodName();
            StringBuilder stringBuilder = new StringBuilder();
            String dar = objValueHexStr.substring(0, 2);
            String errorMsg = DarManager.getInstance().getStatusStr(dar);
            stringBuilder.append(OIManager.getInstance().getObjName(oadHexStr.substring(0, 4)))
                    .append(methodName)
                    .append(": ")
                    .append(errorMsg);
            // 跳闸时，出现其他错误
            if ("80008100".equals(oadHexStr) && "FF".equals(dar.toUpperCase())) {
                stringBuilder.append("(该电表可能为保电状态,请先执行保电解除！)");
            }
            return stringBuilder.toString();
        }

        private String getMethodName() {
            HashMap<String, String[]> methodArrMap = new HashMap<>();
            String[] methodName8000 = new String[]{"触发告警", "解除报警", "跳闸", "合闸", "电表明文合闸"};
            String[] methodName8001 = new String[]{"投入保电", "解除保电", "解除自动保电"};
            String[] methodName8002 = new String[]{"催费告警投入", "取消催费告警"};
            String[] methodName8003_8004 = new String[]{"添加信息", "删除信息"};
            String[] methodName8103 = new String[]{"时段功控方案切换"};
            String[] methodName8106 = new String[]{"投入"};
            methodArrMap.put("8000", methodName8000);
            methodArrMap.put("8001", methodName8001);
            methodArrMap.put("8002", methodName8002);
            methodArrMap.put("8003", methodName8003_8004);
            methodArrMap.put("8004", methodName8003_8004);
            methodArrMap.put("8103", methodName8103);
            methodArrMap.put("8106", methodName8106);

            String oi = oadHexStr.substring(0, 4);
            String[] methods = methodArrMap.get(oi);
            if (method < 127 || null == methods) {
                return "";
            }
            try {
                return "(" + methods[method - 127] + ")";
            } catch (IndexOutOfBoundsException e) {
                return "";
            }

        }
    }

    public static Obj ERROR(final String errMsg) {
        return newInstance(errMsg);
    }

    public static Obj newInstance(final String errMsg) {
        return new Obj() {
            @Override
            public String toFormatString() {
                return errMsg;
            }
        };
    }

    static class Inner {
        /**
         * 对象名称
         */
        final String objName;
        /**
         * 接口类id
         */
        final int interfClass;

        Inner(String objName, int interfClass) {
            this.objName = objName;
            this.interfClass = interfClass;
        }
    }
}
