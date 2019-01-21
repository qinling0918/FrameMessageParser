package com.sgcc.pda.framelibrary.securityunit;


import org.jf.util.SparseArray;

/**
 *  数据单元
 */
public class DataUnit {

    public int byteSize ; // 字节数
    public String dataName; // 数据名称
    public String dataInstructions; // 数据释义
    public String datatype; // 数据格式


    public DataUnit(int byteSize, String dataName, String dataInstructions, String datatype) {
        this.byteSize = byteSize;
        this.dataName = dataName;
        this.dataInstructions = dataInstructions;
        this.datatype = datatype;

    }

    public DataUnit(int byteSize, String dataName, String dataInstructions) {
        this(byteSize,dataName,dataInstructions,"HEX");
    }

    public DataUnit(int byteSize, String dataName) {
        this(byteSize,dataName,"","HEX");
    }
}
