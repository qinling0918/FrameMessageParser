package com.sgcc.pda.framelibrary.protocol698.apdu.data;

public abstract class Number implements java.io.Serializable {


    /**
     *  3	布尔值（BOOLEAN）	1或0
     */
    public abstract boolean boolValue();

    /**
     *
     */
    public abstract byte integerValue();


    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = -589271435474276265L;
}
