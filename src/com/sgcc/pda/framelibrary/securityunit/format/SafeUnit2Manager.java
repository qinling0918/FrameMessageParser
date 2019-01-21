package com.sgcc.pda.framelibrary.securityunit.format;

import android.support.annotation.NonNull;

import com.sgcc.pda.framelibrary.securityunit.format.securityunit2.SelfOperation;
import com.sgcc.pda.framelibrary.utils.TextUtils;
import org.jf.util.SparseArray;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by qinling on 2018/5/22 15:07
 * Description:
 */
public class SafeUnit2Manager {
    public static final String MAINSIGN_SELF_OPERATION = "00"; // 自身操作
    public static final String MAINSIGN_MANAGER_SYSTEM = "01"; // 管理系统交互
    public static final String MAINSIGN_ELECTRIC_ENERGY_METER = "02"; // 电能表
    public static final String MAINSIGN_SECURITY_ISOLATION_GATEWAY = "03"; // 隔离网关
    public static final String MAINSIGN_ELECTRONIC_SEAL = "04"; // 电子封印
    public static final String MAINSIGN_ELECTRONIC_TAGS = "05"; // 电子标签
    public static final String MAINSIGN_PERIPHERALS = "06"; // 外设
    public static final String MAINSIGN_UPGRADE = "FE"; // 升级


    public static final int CUSTOM_ERROR_NUMBER_FORMAT = -1; // 当前仅响应升级命令，只用于红外认证命令*/


    private String mainSign;
    private HashMap<String, String> errorsMap = new HashMap<>();
    private HashMap<String, String> mainSignInfos = new HashMap<>();
    private SparseArray<SparseArray<String>> safeUnitErrors = new SparseArray<>();
    private ISecurityFrameFormat format;

    private static class SingleTon {
        private static SafeUnit2Manager instance = new SafeUnit2Manager();
    }

    public static SafeUnit2Manager getInstance(String mainSign) {
        return SingleTon.instance.init(mainSign);
    }

    private SafeUnit2Manager init(String mainSign) {
        this.mainSign = mainSign;
        init();// 初始化信息
        return this;
    }

    /**
     * 初始化主功能标识信息
     */
    private void initMainSignInfo() {
        mainSignInfos.put("00", "安全单元自身操作类");
        mainSignInfos.put("01", "现场服务终端与管理系统交互类");
        mainSignInfos.put("02", "现场服务终端与电能表的交互命令");
        mainSignInfos.put("03", "现场服务终端与安全隔离网关交互命令");
        mainSignInfos.put("04", "现场服务终端与电子封印交互命令");
        mainSignInfos.put("05", "现场服务终端与电子标签交互命令");
        mainSignInfos.put("06", "现场服务终端与外设交互命令");
        mainSignInfos.put("FE", "安全单元升级命令");

    }

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

        errors.put(CUSTOM_ERROR_NUMBER_FORMAT, "响应码或者状态码 字符串转数字失败");
        return errors;
    }

    private void initPatterns(String mainSign) {
    }


    private SafeUnit2Manager() {
        initsecurityUnit2UnNormalStatusErrors();
        initMainSignInfo();
    }

    private void init() {
        switch (mainSign) {// 自身操作
            case MAINSIGN_SELF_OPERATION:
                format = SelfOperation.getInstance();
                //safeUnitErrors = format.getSafeUnitErrors();
                break;
            case MAINSIGN_MANAGER_SYSTEM:// 管理系统交互
               // initManagerSystem();
                break;
            case MAINSIGN_ELECTRIC_ENERGY_METER:// 电能表
                initElectricEnergyMeter();
                break;
            case MAINSIGN_SECURITY_ISOLATION_GATEWAY:// 隔离网关
               // initSecurityIsolationGateway();
                break;
            case MAINSIGN_ELECTRONIC_SEAL:// 电子封印
               // initElectricSeal();
                break;
            case MAINSIGN_ELECTRONIC_TAGS:// 电子标签
              //  initElectricTags();
                break;
            case MAINSIGN_PERIPHERALS:// 外设
              //  initPeripherals();
                break;
            case MAINSIGN_UPGRADE:// 升级
             //   initUpgrade();
                break;
            default:
                break;

        }
    }

    /* private void initSelfOperation() {
         initSelfOperationErrors();
     }

     private void initUpgrade() {
     }

     private void initPeripherals() {
     }

     private void initElectricTags() {
     }

     private void initElectricSeal() {
     }

     private void initManagerSystem() {
     }

     private void initSecurityIsolationGateway() {
     }*/
    private void initElectricEnergyMeter() {
       // initElectricEnergyMeterErrors();
    }

   /* private void initElectricEnergyMeterErrors() {
        safeUnitErrors.put(0x81, meter698safTransmissionDecryption());
    }*/


    /***************现场服务终端与电能表的交互命令  ElectricEnergyMeter  *************/



    /**
     * 安全单元 异常状态响应时，紧跟的两个字节的含义解释
     *
     * @return
     */
    private void initsecurityUnit2UnNormalStatusErrors() {
        errorsMap.put("9000", "成功");
        errorsMap.put("6D00", "INS不支持");
        errorsMap.put("6E00", "CLA不支持");
        errorsMap.put("6A86", "P1P2不正确");
        errorsMap.put("6A82", "LC长度错误");
        errorsMap.put("6A81", "LE超长");
        errorsMap.put("6A80", "使用条件不满足");
        errorsMap.put("6985", "数据域错误");
        errorsMap.put("6581", "存储器故障");
        errorsMap.put("6988", "计算错误");
        errorsMap.put("6989", "MAC错误");
        errorsMap.put("6980", "密钥不存在");
        errorsMap.put("6981", "文件不存在");
        errorsMap.put("6982", "会话未建立");
        errorsMap.put("6910", "置离线计数器");

    }


    /******************************  公开API *********************/


    public SafeUnit2Manager setMainSign(String mainSign) {
        this.mainSign = mainSign;
        return this;
    }

    public String getMainSignInfo() {
       // return mainSignInfos.get(mainSign.toUpperCase(Locale.CHINA));
        return format.getMainSignInfo();
    }

    public String getStatusInfo(int responseCode, int statusCode) {
       // SparseArray<String> errors  = safeUnitErrors.get(responseCode,getCommonErrors());
      //  return errors.get(statusCode,"未知错误");
        return format.getStatusInfo(responseCode,statusCode);
    }

    public String getStatusInfo(String responseCode, String statusCode) {
        try {
            return getStatusInfo(Integer.parseInt(responseCode, 16), Integer.parseInt(statusCode, 16));
        } catch (NumberFormatException e) {
            return getCommonError(CUSTOM_ERROR_NUMBER_FORMAT);
        }
    }

    public String  getCommandResponseInfo(String response_commandCode) {
        try {
            return getCommandResponseInfo(Integer.parseInt(response_commandCode, 16));
        } catch (NumberFormatException e) {
            return getCommonError(CUSTOM_ERROR_NUMBER_FORMAT);
        }
    }
    public String  getCommandResponseInfo(int response_commandCode) {
        return format.getCommandResponseInfo(response_commandCode);
    }


    public String getCommonError(int errorCode) {
        return getCommonErrors().get(errorCode,"未知错误");
    }
}
