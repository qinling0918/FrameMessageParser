package com.sgcc.pda.framelibrary.securityunit.format;

import android.support.annotation.NonNull;
import com.sgcc.pda.framelibrary.securityunit.DataUnit;
import com.sgcc.pda.framelibrary.utils.NumberConvert;
import com.sgcc.pda.framelibrary.utils.TextUtils;
import org.jaxen.function.IdFunction;
import org.jetbrains.annotations.Nullable;
import org.jf.util.SparseArray;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by qinling on 2018/5/22 15:07
 * Description:
 */
public class SafeUnit2InfoManager {
    public static final String MAINSIGN_SELF_OPERATION = "00"; // 自身操作
    public static final String MAINSIGN_MANAGER_SYSTEM = "01"; // 管理系统交互
    public static final String MAINSIGN_ELECTRIC_ENERGY_METER = "02"; // 电能表
    public static final String MAINSIGN_SECURITY_ISOLATION_GATEWAY = "03"; // 隔离网关
    public static final String MAINSIGN_ELECTRONIC_SEAL = "04"; // 电子封印
    public static final String MAINSIGN_ELECTRONIC_TAGS = "05"; // 电子标签
    public static final String MAINSIGN_PERIPHERALS = "06"; // 外设
    public static final String MAINSIGN_UPGRADE = "FE"; // 升级


    public static final int CUSTOM_ERROR_NO_ERR =0; // 正常
    public static final int CUSTOM_ERROR_NUMBER_FORMAT = -1; // 16进制字符串转数字时出现异常
    public static final int CUSTOM_ERROR_STRING_NULL = -2; // 字符串为空
    public static final int CUSTOM_ERROR_STRING_NOT_HEX = -3; // 字符串非16进制
    public static final int CUSTOM_ERROR_STRING_LENGTH = -4; // 字符串长度存在问题


    private String mainSign;
    private HashMap<String, String> esamErrors = new HashMap<>();
    private SparseArray<String> mainSignInfos = new SparseArray<>();
    private SparseArray<String> cammandInfos = new SparseArray<>();
    private SparseArray<String> responseInfos = new SparseArray<>();
    private SparseArray<DataUnit[]> dataInfos = new SparseArray<>();
    private ISecurityFrameFormat format;

    private static class SingleTon {
        private static SafeUnit2InfoManager instance = new SafeUnit2InfoManager();
    }

    public static SafeUnit2InfoManager getInstance() {
        return SingleTon.instance;
    }

    private SafeUnit2InfoManager() {
        init();
    }

    private void init() {
        // 安全单元 异常状态响应时，紧跟的两个字节的含义解释
        initsecurityUnit2UnNormalStatusErrors();
        // 初始化主功能标识信息
        initMainSignInfos();
        // 初始化命令码或者响应码
        initCammand_ResponseInfos();
        // 初始化数据域信息
       // initDataInfos();

    }
    /**
     *  * 获取主功能码的释义
     * @param mainSignCode 主功能码(int)
     * @return String
     */
    public String getMainSignInfo(int mainSignCode) {
        return mainSignInfos.get(mainSignCode, getCommonError(mainSignCode));
    }

    /**
     *  * 获取主功能码的释义
     * @param mainSignCodeStr 主功能码（HEX）
     * @return String
     */
    public String getMainSignInfo(String mainSignCodeStr) {
        int check =  checkStringWithDefault(mainSignCodeStr);
        return check == CUSTOM_ERROR_NO_ERR // 若是
                ? getMainSignInfo(Integer.parseInt(mainSignCodeStr))
                :getMainSignInfo(check);
    }

    /**
     * 检查字符串格式
     * @param mainSignCodeStr 主功能码 (HEX String)
     * @return int 0 为正常， 其他未错误码
     */
    private int checkStringWithDefault(String mainSignCodeStr) {
        if (TextUtils.isEmpty(mainSignCodeStr)) return CUSTOM_ERROR_STRING_NULL;
        if (mainSignCodeStr.length() % 2 != 0) return CUSTOM_ERROR_STRING_LENGTH;
        if (!NumberConvert.isHexStr(mainSignCodeStr)) return CUSTOM_ERROR_STRING_NOT_HEX;
        return CUSTOM_ERROR_NO_ERR;
    }

    /**
     * 检查字符串格式是否正确
     * @param str 被检查的字符串
     * @return true  格式符合
     *          false ： 字符串为空，字符串长度不是偶数，不是16进制格式
     */
    private boolean checkString(String str) {
        return checkStringWithDefault(str) == CUSTOM_ERROR_NO_ERR;
    }

    /**
     * 获取响应码或者命令码的释义
     * @param mainSignCode 主功能码 （1B）
     * @param cammand_responseCode 命令码或者响应码(1B)
     * @return
     */
    public String getCammand_ResponseInfos(int mainSignCode, int cammand_responseCode) {
        int main_cammand_response = (mainSignCode << 2) + cammand_responseCode;
        return cammand_responseCode < 0x80
                ? cammandInfos.get(main_cammand_response, "")
                : responseInfos.get(main_cammand_response,"");
    }

    /**
     * 获取响应码或者命令码的释义
     * @param mainSignCodeStr 主功能码 （1B）
     * @param cammand_responseCodeStr 命令码或者响应码(1B)
     * @return
     */
    public String getCammand_ResponseInfos(String mainSignCodeStr, String cammand_responseCodeStr) {
        if (checkString(mainSignCodeStr) && checkString(cammand_responseCodeStr)){
            return getCammand_ResponseInfos(Integer.parseInt(mainSignCodeStr,16),
                    Integer.parseInt(cammand_responseCodeStr,16));
        }
       return "";
    }

    /**
     * * 获取响应码或者命令码的释义
     * @param mainSignCammandResponseCodeStr  主功能码+命令码或者响应码（2 字节）
     * @return String
     */
    public String getCammand_ResponseInfos(String mainSignCammandResponseCodeStr) {
        if (mainSignCammandResponseCodeStr.length()!=4)return "";
        String mainSignCodeStr = mainSignCammandResponseCodeStr.substring(0,2);
        String cammand_responseCodeStr = mainSignCammandResponseCodeStr.substring(2,4);
        return getCammand_ResponseInfos(mainSignCodeStr,cammand_responseCodeStr);
    }

    /**
     *
     * @param mainSignCodeStr
     * @param cammand_responseCodeStr
     * @param statusCode
     * @return
     */
    public String getDataFormatt(String mainSignCodeStr, String cammand_responseCodeStr,String statusCode) {
        if (checkString(mainSignCodeStr) && checkString(cammand_responseCodeStr)){
            return getCammand_ResponseInfos(Integer.parseInt(mainSignCodeStr,16),
                    Integer.parseInt(cammand_responseCodeStr,16));
        }
        return "";
    }
    /**
     * 初始化主功能标识信息
     */
    private void initMainSignInfos() {
        mainSignInfos.put(0x00, "安全单元自身操作类");
        mainSignInfos.put(0x01, "现场服务终端与管理系统交互类");
        mainSignInfos.put(0x02, "现场服务终端与电能表的交互命令");
        mainSignInfos.put(0x03, "现场服务终端与安全隔离网关交互命令");
        mainSignInfos.put(0x04, "现场服务终端与电子封印交互命令");
        mainSignInfos.put(0x05, "现场服务终端与电子标签交互命令");
        mainSignInfos.put(0x06, "现场服务终端与外设交互命令");
        mainSignInfos.put(0xFE, "安全单元升级命令");

    }

    private void initCammand_ResponseInfos() {
        /***************安全单元自身操作类    *************/
        cammandInfos.put(0x0001, "获取安全单元信息");
        cammandInfos.put(0x0002, "验证操作员密码");
        cammandInfos.put(0x0003, "修改操作员密码");
        cammandInfos.put(0x0004, "锁定安全单元");
        cammandInfos.put(0x0005, "解锁安全单元");
        cammandInfos.put(0x0006, "一次发行安全单元");
        cammandInfos.put(0x0007, "二次发行安全单元");
        cammandInfos.put(0x0008, "存储关键数据");
        cammandInfos.put(0x0009, "读取关键数据");
        cammandInfos.put(0x000A, "透明转发ESAM指令");
        cammandInfos.put(0x000C, "获取安全单元版本号");

        /***************现场服务终端与管理系统交互类    *************/
        cammandInfos.put(0x0101, "获取随机数");
        cammandInfos.put(0x0102, "应用层身份认证（非对称密钥协商）");
        cammandInfos.put(0x0103, "应用层会话密钥加密算MAC");
        cammandInfos.put(0x0104, "应用层会话密钥解密验MAC");
        cammandInfos.put(0x0105, "转加密初始化");
        cammandInfos.put(0x0106, "置离线计数器");
        cammandInfos.put(0x0107, "本地密钥计算MAC");
        cammandInfos.put(0x0108, "本地密钥验证MAC");
        cammandInfos.put(0x0109, "会话密钥计算MAC");
        cammandInfos.put(0x010A, "会话密钥验证MAC");

        /***************现场服务终端与电能表的交互命令    *************/
        cammandInfos.put(0x0201, "电能表红外认证（13、698）");
        cammandInfos.put(0x0202, "远程身份认证（09、13）");
        cammandInfos.put(0x0203, "电能表控制（09、13）");
        cammandInfos.put(0x0204, "电能表设参（09、13）");
        cammandInfos.put(0x0205, "电能表校时（09、13）");
        cammandInfos.put(0x0206, "电能表密钥更新(09)");
        cammandInfos.put(0x0207, "电能表密钥更新(13)");
        cammandInfos.put(0x0208, "电能表开户充值（09、13）");
        cammandInfos.put(0x0209, "698电能表会话协商");
        cammandInfos.put(0x020A, "698电能表会话协商验证");
        cammandInfos.put(0x020B, "698电能表安全数据生成");
        cammandInfos.put(0x020C, "698电能表安全传输解密");
        cammandInfos.put(0x020D, "698电能表抄读数据验证");
        cammandInfos.put(0x020E, "698电能表抄读ESAM参数验证");
        cammandInfos.put(0x020F, "698电能表校时");
        cammandInfos.put(0x0210, "农排表控制");
        cammandInfos.put(0x0211, "农排表设参");
        cammandInfos.put(0x0212, "农排表校时");
        cammandInfos.put(0x0213, "农排表开户充值");

        /***************现场服务终端与安全隔离网关交互命令    *************/
        cammandInfos.put(0x0301, "链路层非对称密钥协商");
        cammandInfos.put(0x0302, "链路层会话密钥加密计算MAC");
        cammandInfos.put(0x0303, "链路层会话密钥解密验证MAC");
        cammandInfos.put(0x0304, "链路层会话密钥计算MAC");
        cammandInfos.put(0x0305, "链路层会话密钥验证MAC");

        /***************现场服务终端与电子封印交互命令    *************/
        cammandInfos.put(0x0401, "电子封印读认证生成token1");
        cammandInfos.put(0x0402, "电子封印读认证验证token2");
        cammandInfos.put(0x0403, "加密数据地址（读）");
        cammandInfos.put(0x0404, "解密读回数据");
        cammandInfos.put(0x0405, "电子封印写认证生成token1");
        cammandInfos.put(0x0406, "电子封印写认证验证token2");
        cammandInfos.put(0x0407, "加密数据地址（写）");
        cammandInfos.put(0x0408, "加密写数据");
        cammandInfos.put(0x0409, "解密执行结果");
        cammandInfos.put(0x040A, "加密密钥更新数据");

        /*************** 现场服务终端与电子标签交互命令    *************/
        cammandInfos.put(0x0501, "电子标签读认证生成token1");
        cammandInfos.put(0x0502, "电子标签读认证验证token2");
        cammandInfos.put(0x0503, "电子标签MAC验证");
        cammandInfos.put(0x0504, "电子标签解密");

        /***************现场服务终端与外设交互命令    *************/
        cammandInfos.put(0x0601, "外设密钥协商");
        cammandInfos.put(0x0602, "外设密钥协商确认");
        cammandInfos.put(0x0603, "会话密钥加密计算MAC");
        cammandInfos.put(0x0604, "会话密钥解密验证MAC");
        cammandInfos.put(0x0605, "会话密钥计算MAC");
        cammandInfos.put(0x0606, "会话密钥验证MAC");

        /***************安全单元升级命令    *************/
        cammandInfos.put(0xFE01, "升级命令1");
        cammandInfos.put(0xFE02, "升级命令2");
        cammandInfos.put(0xFE03, "升级命令3");
        cammandInfos.put(0xFE04, "指定程序跳转命令");

        for (int i = 0; i < cammandInfos.size(); i++) {
            responseInfos.put((cammandInfos.keyAt(i) + 0x80), cammandInfos.valueAt(i));
        }
    }

    public DataUnit[] getDataUnits(String... dataUnitStrs) {
        if (dataUnitStrs == null) return null;
        int length = dataUnitStrs.length;
        if (length == 0) return null;
        DataUnit[] dataUnits = new DataUnit[length];
        for (int i = 0; i < length; i++) {
            String[] dataUnitArr = dataUnitStrs[i].split("_");
            dataUnits[i] = new DataUnit(Integer.parseInt(dataUnitArr[1], 16), dataUnitArr[0]);
        }
        return dataUnits;
    }

    /**
     *   dataInfos.put(0x0081, getDataUnits(
     *                 "安全单元状态字_1_HEX_",
     *                 "软件版本号_3",
     *                 "硬件版本号_3",
     *                 "C-ESAM序列号_8",
     *                 "操作者代码_4",
     *                 "权限_1",
     *                 "权限掩码_8",
     *                 "操作者信息_30",
     *                 "Y-ESAM序列号_8",
     *                 "Y-ESAM对称密钥密钥版本_16",
     *                 "主站证书版本号_1",
     *                 "终端证书版本号_1",
     *                 "主站证书序列号_16",
     *                 "终端证书序列号_16",
     *                 "当前计数器_4",
     *                 "转加密剩余次数_4",
     *                 "标签密钥版本_8",
     *                 "主站证书数据_-1", // 用-1 表示 2+N
     *                 "主站证书数据_-1"
     *         ));
     */


    /**
     * 获取安全单元工作状态
     *
     * @return
     */
   /* private String getSafeUnitStatusByte() {
    }*/
    @NonNull
    private SparseArray<String> getCommonErrors() {
        SparseArray<String> errors = new SparseArray<>();
        errors.put(0x00, "正常响应");
        errors.put(0xF1, "帧效验错误");
        errors.put(0xF2, "帧长度不符");
        errors.put(0xF3, "操作员权限不够");
        errors.put(0xF4, "安全模块错误-操作员卡操作失败");
        errors.put(0xF5, "安全模块错误-业务卡操作错误");
        errors.put(0xF6, "安全单元与业务卡不同步");
        errors.put(0xF7, "当前仅响应升级命令，只用于红外认证命令");

        errors.put(CUSTOM_ERROR_NUMBER_FORMAT, " 16进制字符串转数字时出现异常");
        errors.put(CUSTOM_ERROR_STRING_NULL, " 字符串为空");
        errors.put(CUSTOM_ERROR_STRING_NOT_HEX, " 字符串非16进制");
        errors.put(CUSTOM_ERROR_STRING_LENGTH, " 字符串长度存在问题");
        return errors;
    }




    /**
     * 安全单元 异常状态响应时，紧跟的两个字节的含义解释
     *
     * @return
     */
    private void initsecurityUnit2UnNormalStatusErrors() {
        esamErrors.put("9000", "成功");
        esamErrors.put("6D00", "INS不支持");
        esamErrors.put("6E00", "CLA不支持");
        esamErrors.put("6A86", "P1P2不正确");
        esamErrors.put("6A82", "LC长度错误");
        esamErrors.put("6A81", "LE超长");
        esamErrors.put("6A80", "使用条件不满足");
        esamErrors.put("6985", "数据域错误");
        esamErrors.put("6581", "存储器故障");
        esamErrors.put("6988", "计算错误");
        esamErrors.put("6989", "MAC错误");
        esamErrors.put("6980", "密钥不存在");
        esamErrors.put("6981", "文件不存在");
        esamErrors.put("6982", "会话未建立");
        esamErrors.put("6910", "置离线计数器");

    }


    /******************************  公开API *********************/


    public String getMainSignInfo() {
        // return mainSignInfos.get(mainSign.toUpperCase(Locale.CHINA));
        return format.getMainSignInfo();
    }

    public String getStatusInfo(int responseCode, int statusCode) {
        // SparseArray<String> errors  = safeUnitErrors.get(responseCode,getCommonErrors());
        //  return errors.get(statusCode,"未知错误");
        return format.getStatusInfo(responseCode, statusCode);
    }

    public String getStatusInfo(String responseCode, String statusCode) {
        try {
            return getStatusInfo(Integer.parseInt(responseCode, 16), Integer.parseInt(statusCode, 16));
        } catch (NumberFormatException e) {
            return getCommonError(CUSTOM_ERROR_NUMBER_FORMAT);
        }
    }

    public String getCommandResponseInfo(String response_commandCode) {
        try {
            return getCommandResponseInfo(Integer.parseInt(response_commandCode, 16));
        } catch (NumberFormatException e) {
            return getCommonError(CUSTOM_ERROR_NUMBER_FORMAT);
        }
    }

    public String getCommandResponseInfo(int response_commandCode) {
        return format.getCommandResponseInfo(response_commandCode);
    }


    public String getCommonError(int errorCode) {
        return getCommonErrors().get(errorCode, "未知错误");
    }
}
