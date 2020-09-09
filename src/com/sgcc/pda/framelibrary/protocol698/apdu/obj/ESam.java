package com.sgcc.pda.framelibrary.protocol698.apdu.obj;


import com.sgcc.pda.framelibrary.protocol698.apdu.data.DataManager;
import com.sgcc.pda.framelibrary.utils.RecoverableString;
import org.jf.util.SparseArray;

/**
 * Copyright (C) 2019 SCIENCE AND TECHNOLOGY LTD.
 * <p>
 * 描述：ESAM接口类
 *
 * @author Abel
 * @version 1.0
 * @date 2019/6/26 11:25
 */
public class ESam extends Obj {
    private static final String INFO = "F100\t21\tESAM\n" +
            "F101\t8\t安全模式参数\n";

    private static SparseArray<Inner> varInners = new SparseArray<>();

    static {
        String[] vars = INFO.split("\n");
        for (String var : vars) {
            String[] varArr = var.split("\t");
            int objNo = Integer.parseInt(varArr[0], 16);
            int interfClass = Integer.parseInt(varArr[1]);
            String objName = varArr[2];
            ParamsVariable.ParamsVariableInner variableInner = new ParamsVariable.ParamsVariableInner(objName, interfClass);
            varInners.put(objNo, variableInner);
        }
    }

    private final int logicNo;
    private final Inner variableInner;
    private final String formatString;

    protected ESam(String oadHexStr, RecoverableString objValueHexStr) {
        super(oadHexStr, objValueHexStr);
        logicNo = Integer.parseInt(oiStr, 16);
        this.variableInner = varInners.get(logicNo);
        this.objName = variableInner.objName;
        this.interfClass = variableInner.interfClass;
        this.formatString = format();
    }

    @Override
    public String toFormatString() {
        return formatString;
    }

    /**
     * 格式化数据
     */
    private String format() {
        if ("F100".equals(oiStr)) {
            String classify = objValueHexStr.substring(0, 2);
            switch (attributes) {
                case 0x05:
                    if (!"06".equals(classify)) {
                        return "会话失效门限：" + getError();
                    }
                    int value = Integer.parseInt(objValueHexStr.substring(0, 8), 16);
                    return "会话失效门限：" + value + "分钟";
                default:
                    return "暂未完成解析: " + objName + ":" + objValueHexStr.getCurrentSting();
            }
        } else if ("F101".equals(oiStr)) {
            return formatF101();
        } else {
            return getError();
        }
    }

    /**
     * 返回错误信息
     */
    private String getError() {
        return "数据类型返回错误： " + objName + ":" + objValueHexStr.getCurrentSting();
    }

    /**
     * 解析 安全模式参数
     * <p>
     * 属性2（安全模式选择）∷=enum
     * {
     * 不启用安全模式参数（0），
     * 启用安全模式参数  （1）
     * }
     * 属性3（显式安全模式参数）∷=array 安全模式参数
     * 安全模式参数∷=structure
     * {
     * 对象标识   OI，
     * 安全模式   long-unsigned
     * }
     * 安全模式选择意义：0—不启用安全模式参数、默认安全模式参数，1—启用安全模式参数、默认安全模式参数。
     * 属性3为显式安全模式参数（设置值），如果对象安全性不在属性3中，则按默认安全模式参数。如果同一对象安全性在安全模式参数、默认安全模式参中均有说明，则按显式安全模式参数执行。安全模式参数、默认安全参数具体定义见附录F，安全模式定义见附录F中表F.1。
     * 属性4（SAL安全应用数据链路层参数）∷=enum
     * {
     * 不启用SAL（0），
     * 启用SAL  （1）
     * }
     * 方法1：复位（参数）
     * 参数∷=integer（0）
     * 复位时，清空属性3。
     * 方法127：增加显式安全模式参数（参数）
     * 参数∷=structure
     * {
     * 对象标识  OI，
     * 权限      long-unsigned
     * }
     * 方法128：删除显式安全模式参数（对象标识）
     * 对象标识∷=OI
     * 方法129：批量增加显式安全模式参数（array 安全模式参数）
     * 安全模式参数∷=structure
     * {
     * 对象标识    OI，
     * 安全模式    long-unsigned
     * }
     */
    private String formatF101() {
        switch (attributes) {
            case 0x02:
                return "";
            case 0x03:
                return formatSafetyMode();
            default:
                return "未知数据：" + objName + ":" + objValueHexStr.getCurrentSting();
        }
    }

    private String formatSafetyMode() {
        StringBuilder builder = new StringBuilder();
        String classify = objValueHexStr.substring(0, 2);
        if ("00".equals(classify)) {
            builder.append(objName).append(":").append("返回数据为空！");
            return builder.toString();
        }
        if (!"01".equals(classify)) {
            builder.append(getError());
            return builder.toString();
        }
        builder.append(objName).append("[").append("\n");
        int num = Integer.parseInt(objValueHexStr.substring(0, 2), 16);
        for (int i = 0; i < num; i++) {
            String innerClassify = objValueHexStr.substring(0, 2);
            if (!"02".equals(innerClassify)) {
                builder.append(getError());
                return builder.toString();
            }
            int len = Integer.parseInt(objValueHexStr.substring(0, 2), 16);
            if (len != 2) {
                builder.append(getError());
                return builder.toString();
            }
            builder.append("  显式安全模式参数: ").append("{").append("\n");
            String oneClassify = objValueHexStr.substring(0, 2);
            builder.append("    对象标识OI:")
                    .append(objValueHexStr.substring(0, DataManager.getInstance().getDataByteSize(oneClassify) * 2))
                    .append(",").append("\n");
            String twoClassify = objValueHexStr.substring(0, 2);
            builder.append("    安全模式:")
                    .append(objValueHexStr.substring(0, DataManager.getInstance().getDataByteSize(twoClassify) * 2))
                    .append("\n");

            builder.append("  }");

            if (i != num - 1) {
                builder.append(",");
            }
            builder.append("\n");
        }
        builder.append("]");
        return builder.toString();
    }
}
