package com.sgcc.pda.framelibrary.protocol698.apdu;


import java.security.MessageDigest;
import java.util.Random;

public class MD5 {

    private static final String ALGORITHOM = "MD5";

    /**
     * @param password 需要加密的明文密码
     * @return 返回MD5(加盐)密文
     */
    private static String generate(String password) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(16);
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        int len = sb.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        }
        String salt = sb.toString();
        password = md5Hex(password + salt);
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = password.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }

    public static void main(String[] args) {
        String password = "091f8eef0f0d4444ba85c082c822b049";
        String  md5= "D3153BF1C440042A4C83AD28A21E5F03034644EC4B171834";

        String[] arr = new String[3];
        for (int i = 0; i < 3 ; i++) {
            System.out.println(arr[i]);
        }
        System.out.println(verify(password,md5));
    }
    /**
     * 校验密码有效性
     *
     * @param password 需要加密的明文密码
     * @param md5      已加密的MD5(加盐)密文
     * @return 返回对比结果
     */
    public static boolean verify(String password, String md5) {
      /*  password = password.toUpperCase();
        md5 = md5.toUpperCase();*/
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        if (md5 == null || md5.length() != 48) {
            return false;
        }
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5.charAt(i);
            cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
            cs2[i / 3] = md5.charAt(i + 1);
        }
        String salt = new String(cs2);
        return md5Hex(password + salt).toUpperCase().equalsIgnoreCase(new String(cs1).toUpperCase());
    }

    /**
     * 获取十六进制字符串形式的MD5摘要
     */
    private static String md5Hex(String src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance(ALGORITHOM);
            byte[] bytes = md5.digest(src.getBytes());
//			return new String(new Hex().encode(bytes));
            // 转化16进制字符串
            StringBuilder builder = new StringBuilder();
            for (byte ab : bytes) {
                String s = Integer.toHexString(ab & 0xFF);
                if (s.length() == 1) {
                    builder.append("0");
                }
                builder.append(s);
            }
            return builder.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static String encrypt(String spKey) {
        return generate(spKey);
    }


}
