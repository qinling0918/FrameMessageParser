package com.sgcc.pda.framelibrary.utils;

import java.util.Stack;

/**
 * Created by qinling on 2019/5/16 9:34
 * Description:
 */
public class RecoverableString /*implements Appendable*/ {
    private String originalString;
    private Stack<String> subStringStack;

    public RecoverableString() {
        this("");
    }

    public RecoverableString(String string) {
        this.originalString = string;
        subStringStack = new Stack<>();
    }

    public String substring(int start, int end) {
       /* if (getHistoryStringLength()+ end -start > originalString.length()){
            throw new IndexOutOfBoundsException("剩余字符串长度无法满足此次截取");
        }*/

        String subStr = getCurrentSting().substring(start, end);
        if (start > 0) {
            subStringStack.add(getCurrentSting().substring(0, start));
        }
        subStringStack.add(subStr);
        return subStr;
    }
    public String substring(int start) {
        if (start > 0) {
            subStringStack.add(getCurrentSting().substring(0, start));
        }
        return getCurrentSting();
    }
    public String subOriginalString(int start, int end) {
        return originalString.substring(start, end);
    }

    public String subOriginalString(int start) {
        return originalString.substring(start);
    }

    /**
     * 获取最初的字符串
     *
     * @return
     */
    public String getOriginalString() {
        return originalString;
    }

    /**
     * 获取当前的字符串（经过多次截取之后剩余的结果）
     *
     * @return
     */
    public String getCurrentSting() {
        return originalString.substring(getHistoryStringLength());
    }

    /**
     * 获取曾经截取掉的字符串
     *
     * @return
     */
    public String getHistoryString() {
        if (subStringStack.empty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < subStringStack.size(); i++) {
            stringBuilder.insert(0, subStringStack.get(subStringStack.size() - 1 - i));
        }
        return stringBuilder.toString();
    }

    /**
     * 获取曾经截取掉的字符串长度
     *
     * @return
     */
    public int getHistoryStringLength() {
        int length = 0;
        if (subStringStack.empty()) {
            return length;
        }
        for (int i = 0; i < subStringStack.size(); i++) {
            length += subStringStack.get(subStringStack.size() - 1 - i).length();
        }
        return length;
    }

    /* @Override
    public RecoverableString append(CharSequence charSequence) {

        return this;
    }

    @Override
    public RecoverableString append(CharSequence charSequence, int i, int i1){
        return this;
    }

    @Override
    public RecoverableString append(char c) {
        return this;
    }*/
}
