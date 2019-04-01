package com.sgcc.pda.framelibrary.protocol698.apdu.data;

import java.util.ArrayList;

public class Array extends Data{
    private ArrayList<Data> datas;


    @Override
    public int dataType() {
        return 0x01;
    }

    @Override
    public String toHexString() {
        return null;
    }

    @Override
    public String format() {
        return null;
    }
}
