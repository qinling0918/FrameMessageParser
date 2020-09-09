package com.sgcc.pda.framelibrary.protocol698.apdu;

import com.sgcc.pda.framelibrary.FormatAble;

/**
 * Created by qinling on 2018/6/29 18:21
 * Description:
 */
public interface IAPDUParser extends FormatAble {
    /**
     * 获取类型
     *
     * @return
     */
    int getClassify();



}
