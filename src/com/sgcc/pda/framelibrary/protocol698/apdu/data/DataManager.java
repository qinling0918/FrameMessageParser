package com.sgcc.pda.framelibrary.protocol698.apdu.data;


import com.sgcc.pda.framelibrary.utils.NumberConvert;
import com.sun.javaws.exceptions.BadVersionResponseException;
import org.jf.util.SparseArray;
import org.jf.util.SparseIntArray;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

/**
 * Created by qinling on 2018/5/22 15:07
 * Description:
 */
public class DataManager {

    /**
     * 长度不定,在数据类型后紧接着获取的就是字节数
     */
    public static final int LEN_FOLLOW = -1;
    /**
     * 在数据类型后紧接着获取的不是长度，而是元素个数，然后再每一个去读取每个元素的长度
     */
    public static final int LEN_ELEMENT = -2;
    /**
     * 组合数据类型，类似与结构体（-2），无法从后续字节读取元素个数，但该类型本身就有元素个数 例如 SID（元素个数=2）,SID_MAC（3），
     */
    public static final int LEN_SID = -3;
    public static final int LEN_SID_MAC = -11;
    /**
     * 需要随后的数据长度/8
     */
    public static final int LEN_BIT_STRING = -4;
    /**
     * ROAD       OAD(08) +   SEQUENCE OF OAD  (02) + (N*8)
     */
    public static final int LEN_ROAD = -5;
    /**
     * Region  ENUM(02) + DATA(?) + DATA(?)
     */
    public static final int LEN_REGION = -6;
    /**
     *  choice(02) +  Selector1(?)
     */
    public static final int LEN_RSD = -7;
    /**
     * csd       choice(02)  OAD(08)/ROAD(?)
     */
    public static final int LEN_CSD = -8;
    /**
     * RCSD       SEQUENCE OF csd
     */
    public static final int LEN_RCSD = -9;
    /**
     * MS       choice(02)  + ?
     */
    public static final int LEN_MS = -10;

    public enum DataType{
        NULL(0, 0),// NULL
        array(1, LEN_ELEMENT),//array
        structure(2, LEN_ELEMENT),//structure
        bool(3, 1), //bool
        bit_string(4, LEN_BIT_STRING),//bit-string
        double_long(5, 4),//double-long
        double_long_unsigned(6, 4),// double-long-unsigned
        octet_string(9, LEN_FOLLOW),//octet-string
        Visible_String(10, LEN_FOLLOW),//VisibleString.class
        UTF8_String(12, LEN_FOLLOW),//UTF8String.class
        Integer(15, 1),//Integer.class
        Long(16, 2),//Long.class
        Unsigned(17, 1),//Unsigned.class
        LongUnsigned(18, 2),//LongUnsigned.class
        Long64(20, 8),//Long64.class
        Long64Unsigned(21, 8),//Long64Unsigned.class
        Enum(22, 1),//Enum.class
        Float32(23, 4),//Float32.class
        Float64(24, 8),//Float64.class
        DateTime(25, 10),//DateTime.class
        date(26, 5),//date.class
        Time(27, 3),//Time.class
        DateTimeS(28, 7),//DateTimeS.class
        OI(80, 2),//OI.class
        OAD(81, 4),//OAD.class

        ROAD(82, LEN_ROAD),//ROAD.class
        OMD(83, 4),//OMD.class
        TI(84, 3),//TI.class
        TSA(85, LEN_FOLLOW),//TSA.class
        MAC(86, LEN_FOLLOW),//MAC.class
        RN(87, LEN_FOLLOW),//RN.class
        Region(88, LEN_REGION),//Region.class
        ScalerUnit(89, 2),//ScalerUnit.class   integer（2）+enum（2）

        RSD(90, LEN_RSD),//RSD.class
        CSD(91, LEN_CSD),//CSD.class
        MS(92, LEN_MS),//MS.class
        SID(93, LEN_SID),//SID.class
        SID_MAC(94, LEN_SID_MAC),//SID_MAC.class
        COMDCB(95, 5),//CO
        // MDCB.class
        RCSD(96, LEN_RCSD);//RCSD.class

        int dateType;
        int byteSize;
        Class calzz;

        DataType(int dateType, int byteSize) {
        }
    }

    Deque<Item> datas = new ArrayDeque<>() ;
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
    private void initLengths() {
        dataStrLength.put(0, 0);// NULL
        dataStrLength.put(1, LEN_ELEMENT);//array
        dataStrLength.put(2, LEN_ELEMENT);//structure
        dataStrLength.put(3, 1); //bool
        dataStrLength.put(4, LEN_BIT_STRING);//bit-string
        dataStrLength.put(5, 4);//double-long
        dataStrLength.put(6, 4);// double-long-unsigned
        dataStrLength.put(9, LEN_FOLLOW);//octet-string
        dataStrLength.put(10, LEN_FOLLOW);//VisibleString.class
        dataStrLength.put(12, LEN_FOLLOW);//UTF8String.class
        dataStrLength.put(15, 1);//Integer.class
        dataStrLength.put(16, 2);//Long.class
        dataStrLength.put(17, 1);//Unsigned.class
        dataStrLength.put(18, 2);//LongUnsigned.class
        dataStrLength.put(20, 8);//Long64.class
        dataStrLength.put(21, 8);//Long64Unsigned.class
        dataStrLength.put(22, 1);//Enum.class
        dataStrLength.put(23, 4);//Float32.class
        dataStrLength.put(24, 8);//Float64.class
        dataStrLength.put(25, 10);//DateTime.class
        dataStrLength.put(26, 5);//DateTime.class
        dataStrLength.put(27, 3);//Time.class
        dataStrLength.put(28, 7);//DateTimeS.class
        dataStrLength.put(80, 2);//OI.class
        dataStrLength.put(81, 4);//OAD.class

        dataStrLength.put(82, LEN_ROAD);//ROAD.class
        dataStrLength.put(83, 4);//OMD.class
        dataStrLength.put(84, 3);//TI.class
        dataStrLength.put(85, LEN_FOLLOW);//TSA.class
        dataStrLength.put(86, LEN_FOLLOW);//MAC.class
        dataStrLength.put(87, LEN_FOLLOW);//RN.class
        dataStrLength.put(88, LEN_REGION);//Region.class
        dataStrLength.put(89, 2);//ScalerUnit.class   integer（2）+enum（2）

        dataStrLength.put(90, LEN_RSD);//RSD.class
        dataStrLength.put(91, LEN_CSD);//CSD.class
        dataStrLength.put(92, LEN_MS);//MS.class
        dataStrLength.put(93, LEN_SID);//SID.class
        dataStrLength.put(94, LEN_SID_MAC);//SID_MAC.class
        dataStrLength.put(95, 5);//COMDCB.class
        dataStrLength.put(96, LEN_RCSD);//RCSD.class
    }
    /**
     * 每种数据类型的字节数
     * <p>
     * -1 长度不定,在数据类型后紧接着获取的就是字节数
     * -2 在数据类型后紧接着获取的不是长度，而是元素个数，然后再每一个去读取每个元素的长度
     * -3 组合数据类型，类似与结构体（-2），无法从后续字节读取元素个数，但该类型本身就有元素个数 例如 SID（元素个数=2）,SID_MAC（3），
     * -4 bitString。需要随后的数据长度/8
     * -5 ROAD       OAD(08) +   SEQUENCE OF OAD  (02) + (N*8)
     * -6 Region  ENUM(02) + DATA(?) + DATA(?)
     * -7 Rsd       choice(02) +  Selector1(?)
     * -8 csd       choice(02)  OAD(08)/ROAD(?)
     * -9 RCSD       SEQUENCE OF csd
     * -10 MS       choice(02)  + ?
     */

  /*  private Deque<IData> getData(int dataType) {
        return ;
    }*/
    public int getDataByteSize(int dataType) {
       // DataType.valueOf()

        return dataStrLength.get(dataType);
    }

    private Integer getDataByteSize(int dataType, java.lang.String byteAfterDataTypeHexStr) {
        int size = getDataByteSize(dataType);
        return size < 0 ? getSpecialDataByteSize(dataType,size, byteAfterDataTypeHexStr) : size;
    }

    /**
     * 根据 16进制的数据类型 获取相对的数据长度
     *
     * @param dataTypeHexStr
     * @return
     */

    public Integer getDataByteSize(java.lang.String dataTypeHexStr) {
        if (dataTypeHexStr.length() < 2 || !NumberConvert.isHexStr(dataTypeHexStr)) {
            throw new IllegalArgumentException("参数异常,只少需要1字节 16进制字符串");
        }
        return getDataByteSize(Integer.parseInt(dataTypeHexStr.substring(0, 2), 16), dataTypeHexStr.substring(2));
    }

    /**
     *
     *
     * @param dataType
     * @param size
     * @param dataTypeHexStr
     * @return >0 值正确， null 或者小于0 异常，获取失败
     */
    private Integer getSpecialDataByteSize(int dataType, int size, String dataTypeHexStr) {
        Integer byteSize = size;
        String hexStr = dataTypeHexStr;
        switch (size) {
            case LEN_FOLLOW:
                byteSize=  Integer.parseInt(hexStr.substring(0, 2));
                break;
            case LEN_ELEMENT:
                int elementNum = Integer.parseInt(hexStr.substring(0, 2));
                hexStr = hexStr.substring(2);
                for (int i = 0; i < elementNum; i++) {
                    int bytes = getDataByteSize(hexStr);
                    hexStr = hexStr.substring(bytes*2);
                    byteSize += bytes;
                }
                break;

            case LEN_SID: //  标识 (double-long-unsigned 6) + 附加数据( octet-string )
               int flagSize = getDataByteSize(6);
               hexStr = hexStr.substring(flagSize*2);
                byteSize = flagSize +  getDataByteSize(9);
                break;
            case LEN_SID_MAC:

                    break;
            case LEN_BIT_STRING:
                break;
            case LEN_ROAD:
                break;
            case LEN_REGION:
                break;
            case LEN_RSD:
                break;
            case LEN_CSD:
                break;
            case LEN_RCSD:
                break;
            case LEN_MS:
                break;
            default:
                return byteSize;
        }
        return byteSize;
    }
    /** 第一个数据标识开始到最后
     * 例如:8501 00 400C0200 01 0205110411081108110411100000
     * 此处应该是     0205110411081108110411100000*/
    /**
     * getDataStrLength("02","0205110411081108110411100000")
     *
     * @param dataTypeHexStr          数据标识
     * @param byteAfterDataTypeHexStr 第一个数据标识开始到最后
     * @return 对应数据类型的长度  xiao
     */
    public int getDataByteSize(java.lang.String dataTypeHexStr, java.lang.String byteAfterDataTypeHexStr) {
        return dataStrLength.get(Integer.parseInt(dataTypeHexStr, 16));
    }


    public static class Item {
        private String hexStr;
        private IData data;
        private Class clazz;
        // Optional
    }
}
