package com.sgcc.pda.framelibrary.protocol698.apdu.data;


import com.sgcc.pda.framelibrary.utils.NumberConvert;
import org.jf.util.SparseArray;
import org.jf.util.SparseIntArray;

/**
 * Created by qinling on 2018/5/22 15:07
 * Description:
 */
public class DataManager {
    private SparseIntArray dataStrLength = new SparseIntArray();

    private static class SingleTon {
        private static DataManager instance = new DataManager();
    }

    public static DataManager getInstance(/*int type*/) {
        return SingleTon.instance;
    }

    private DataManager() {
        initLengths();
    }

    /**
     * 每种数据类型的长度  （是字符串长度，不是字节数）
     *
     * -1 长度不定,在数据类型后紧接着获取的就是长度
     * -2 在数据类型后紧接着获取的不是长度，而是元素个数，然后再每一个去读取每个元素的长度
     * -3 组合数据类型，类似与结构体（-2），无法从后续字节读取元素个数，但该类型本身就有元素个数 例如 SID（元素个数=2）,SID_MAC（3），
     * -4 bitString。需要随后的数据长度/8
     * -5 ROAD       OAD(08) +   SEQUENCE OF OAD  (02) + (N*8)
     * -6 Region  ENUM(02) + DATA(?) + DATA(?)
     * -7 Rsd       choice(02) +  Selector1(?)
     * -8 csd       choice(02)  OAD(08)/ROAD(?)
     * -9 MS       choice(02)  + ?

     */
    private void initLengths() {
        dataStrLength.put(0, 0);// NULL
        dataStrLength.put(1, -2);//array
        dataStrLength.put(2, -2);//structure
        dataStrLength.put(3, 2); //bool  // todo  长度是1 还是2 ？
        dataStrLength.put(4, -4);//bit-string
        dataStrLength.put(5, 8);//double-long
        dataStrLength.put(6, 8);// double-long-unsigned
        dataStrLength.put(9, -2);//octet-string
        dataStrLength.put(10, -1);//VisibleString.class
        dataStrLength.put(12, -1);//UTF8String.class
        dataStrLength.put(15, 2);//Integer.class
        dataStrLength.put(16, 4);//Long.class
        dataStrLength.put(17, 2);//Unsigned.class
        dataStrLength.put(18, 4);//LongUnsigned.class
        dataStrLength.put(20, 16);//Long64.class
        dataStrLength.put(21, 16);//Long64Unsigned.class
        dataStrLength.put(22, 2);//Enum.class
        dataStrLength.put(23, 8);//Float32.class
        dataStrLength.put(24, 16);//Float64.class
        dataStrLength.put(25, 20);//DateTime.class
        dataStrLength.put(26, 10);//DateTime.class
        dataStrLength.put(27, 6);//Time.class
        dataStrLength.put(28, 14);//DateTimeS.class
        dataStrLength.put(80, 4);//OI.class
        dataStrLength.put(81, 8);//OAD.class

        dataStrLength.put(82, -5);//ROAD.class
        dataStrLength.put(83, 8);//OMD.class
        dataStrLength.put(84, 6);//TI.class
        dataStrLength.put(85, -1);//TSA.class
        dataStrLength.put(86, -1);//MAC.class
        dataStrLength.put(87, -1);//RN.class
        dataStrLength.put(88, -6);//Region.class
        dataStrLength.put(89, 4);//ScalerUnit.class   integer（2）+enum（2）

        dataStrLength.put(90, -7);//RSD.class
        dataStrLength.put(91, -8);//CSD.class
        dataStrLength.put(92, -1);//MS.class
        dataStrLength.put(93, -3);//SID.class
        dataStrLength.put(94, -3);//SID_MAC.class
        dataStrLength.put(95, 10);//COMDCB.class
        dataStrLength.put(96, -1);//RCSD.class
    }




    @Deprecated
    public int getDataStrLength(int dataType) {
        return dataStrLength.get(dataType);
    }

    /**
     *  根据 16进制的数据类型 获取相对的数据长度
     * @param dataTypeHexStr
     * @return
     */

    public int getDataStrLength(java.lang.String dataTypeHexStr) {
        if (dataTypeHexStr.length() != 2 || !NumberConvert.isHexStr(dataTypeHexStr)) {
            throw new IllegalArgumentException("参数异常,需要1字节 16进制字符串");
        }
        return dataStrLength.get(Integer.parseInt(dataTypeHexStr, 16));
    }
    /** 第一个数据标识开始到最后
     * 例如:8501 00 400C0200 01 0205110411081108110411100000
     * 此处应该是     0205110411081108110411100000*/
    /**
     * getDataStrLength("02","0205110411081108110411100000")
     *
     * @param dataTypeHexStr 数据标识
     * @param byteAfterDataTypeHexStr 第一个数据标识开始到最后
     * @return 对应数据类型的长度  xiao
     */
    public int getDataStrLength(java.lang.String dataTypeHexStr, java.lang.String byteAfterDataTypeHexStr) {
        if (byteAfterDataTypeHexStr.length() != 2 || !NumberConvert.isHexStr(byteAfterDataTypeHexStr)) {
            throw new IllegalArgumentException("参数异常,至少需要1字节 16进制字符串");
        }
        if (dataTypeHexStr.length() != 2 || !NumberConvert.isHexStr(dataTypeHexStr)) {
            throw new IllegalArgumentException("参数异常,需要1字节 16进制字符串");
        }

        return dataStrLength.get(Integer.parseInt(dataTypeHexStr, 16));
    }
}
