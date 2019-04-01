package com.sgcc.pda.framelibrary.protocol698.apdu.data;


import android.support.annotation.IntRange;
import com.google.gson.internal.$Gson$Types;
import com.sgcc.pda.framelibrary.protocol698.Frame698;
import com.sgcc.pda.framelibrary.protocol698.Frame698Separator;
import com.sgcc.pda.framelibrary.utils.NumberConvert;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;

/**
 * Created by qinling on 2018/5/11 18:22
 * Description:
 */
public class ScalerUnit extends Data implements Serializable {
    private static final long serialVersionUID = 4470047718751627220L;

    public static void main(String[] args) {
        BigInteger integer = new BigInteger("10");
        BigInteger intege = new BigInteger("7");

        //  System.out.println(new ScalerUnit(255,33).getUnit().toString());
        //  String frameStr = "fefefefe68c600e3050100000000000083ae0000900082010b85030350040200020020210200000010020001071c07e302050000000105060000000606000000000600000000060000000006000000061c07e302060000000105060000000606000000000600000000060000000006000000061c07e302070000000105060000000606000000000600000000060000000006000000061c07e302080000000105060000000606000000000600000000060000000006000000061c07e302090000000105060000000606d3f316";
        String frameStr1 = "fefefefe68c600e3050100000000000083ae0000900082010b85030350040200020020210200000020020001071c07e3020c0000000105060000000006000000000600000000060000000006000000001c07e3020d0000000105060000000006000000000600000000060000000006000000001c07e3020e0000000105060000000006000000000600000000060000000006000000001c07e3020f0000000105060000000006000000000600000000060000000006000000001c07e302100000000105060000000006bef016";
        String frameStr2 = "fefefefe687300e3050100000000000093a40140000000000600000000060000000006000000001c07e302110000000105060000000006000000000600000000060000000006000000001c07e302120000000105060000000006000000000600000000060000000006000000000000010004c1238b0df9fd16";
      //  String frameStr3 = "900082010b85030350040200020020210200000020020001071c07e3020c0000000105060000000006000000000600000000060000000006000000001c07e3020d0000000105060000000006000000000600000000060000000006000000001c07e3020e0000000105060000000006000000000600000000060000000006000000001c07e3020f0000000105060000000006000000000600000000060000000006000000001c07e3021000000001050600000000060000000000600000000060000000006000000001c07e302110000000105060000000006000000000600000000060000000006000000001c07e302120000000105060000000006000000000600000000060000000006000000000000010004c1238b0d";
        // Frame698 frame698 = new Frame698().parse(frameStr1,frameStr2).reBuild();
       // Frame698Separator.Parser parse = new Frame698Separator().parse(frameStr1,frameStr2);
       // System.out.println(parse.getLinkDataStr());

        // resver();


    }

    public static abstract class JSZPCallcak<T> {
        Type mType = getSuperclassTypeParameter(getClass());


        static Type getSuperclassTypeParameter(Class<?> subclass) {
            //通过反射得到泛型参数
            //Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            //ParameterizedType参数化类型，即泛型
            ParameterizedType parameterized = (ParameterizedType) superclass;
            //getActualTypeArguments获取参数化类型的数组，泛型可能有多个
            //将Java 中的Type实现,转化为自己内部的数据实现,得到gson解析需要的泛型
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public abstract void onError(Throwable e);

        public abstract void onSuccess(T response, String json);
    }


    public int getPow() {
        return pow;
    }

    public void setPow(int pow) {
        this.pow = pow;
    }

    public Unit getUnit() {
        return unit;
    }

    public String getUnitStr() {
        return unit.getUnit();
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    private int pow;
    private Unit unit;

    private String hexStr;

    public ScalerUnit(String hexStr) {
        byte[] bytes = hexStringToBytes(hexStr);
        if (null == bytes || bytes.length < 2) {
            throw new IndexOutOfBoundsException("16进制字符串格式有误");
        }
        this.hexStr = hexStr;
        this.pow = Byte.toUnsignedInt(bytes[0]);
        this.unit = Unit.getInstance().getUnit(Byte.toUnsignedInt(bytes[1]));
    }

    public ScalerUnit(@IntRange(from = 0, to = 255) int pow, Unit unit) {
        this.pow = pow;
        this.unit = unit;
        this.hexStr = NumberConvert.toHexStr(Math.abs(pow), 2) + Integer.toHexString(unit.getCode());
        //  System.out.println(hexStr);
    }

    public ScalerUnit(@IntRange(from = 0, to = 255) int pow, @IntRange(from = 0, to = 255) int unitCode) {
        this(pow, Unit.getInstance().getUnit(unitCode));
    }

    @Override
    public int dataType() {
        return 89;
    }

    @Override
    public String toHexString() {
        return hexStr;
    }

    @Override
    public String format() {
        return "";
    }


}
