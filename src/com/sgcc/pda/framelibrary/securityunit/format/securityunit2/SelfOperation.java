package com.sgcc.pda.framelibrary.securityunit.format.securityunit2;


import com.sgcc.pda.framelibrary.securityunit.format.ISecurityFrameFormat;
import org.jf.util.SparseArray;


/**
 * Created by qinling on 2018/5/14 15:06
 * Description:  00 安全单元自身操作类
 */
public class SelfOperation implements ISecurityFrameFormat {
    @Override
    public String getMainSignInfo() {
        return "安全单元自身操作类";
    }

    @Override
    public String getCommandResponseInfo(int response_commandCode) {
        return response_commandCode < 0x80
                ? safeUnitCommands.get(response_commandCode)
                : safeUnitResponses.get(response_commandCode);
    }

    @Override
    public String getStatusInfo(int response_commandCode, int statusCode) {
        String error = response_commandCode < 0x80 ? "请求帧无状态码" : "";
        return safeUnitErrors.get(response_commandCode, new SparseArray<>()).get(statusCode, error);
    }

    @Override
    public String getDataFormat(int response_commandCode, int statusCode, String data) {/*
      String[] patterns = new String[]{

      }*/
        return "";
    }

    private static class SingleTon {
        private static SelfOperation instance = new SelfOperation();
    }

    public static SelfOperation getInstance() {
        return SingleTon.instance;
    }


    private SparseArray<SparseArray<String>> safeUnitErrors = new SparseArray<>(); // 状态码
    private SparseArray<String> safeUnitCommands = new SparseArray<>(); // 命令码
    private SparseArray<String> safeUnitResponses = new SparseArray<>(); // 响应码

    // private SparseArray<String> safeUnitformats = new SparseArray<>(); // 响应码

    public SelfOperation() {
        initSelfOperationErrors(); // 初始化状态码
        initSelfOperationCommand_Response();// 初始化命令码与响应码
        // initSelfOperationFormat();// 初始化命令码与响应码
    }

   /* private void initSelfOperationFormat() {
        safeUnitformats.put(0x8C, getSafeUnitVersionCodeResponseFormat());
        safeUnitformats.put(0x8A, transparentForwardingESAMResponseFormat());
        safeUnitformats.put(0x89, readKeyDataResponseFormat());
        safeUnitformats.put(0x88, storeKeyDataResponseFormat());
        safeUnitformats.put(0x87, safeUnitTwoReleaseResponseFormat());
        safeUnitformats.put(0x86, safeUnitOneReleaseResponseFormat());
        safeUnitformats.put(0x85, unLockSecurityUnitResponseFormat());
        safeUnitformats.put(0x84, lockSecurityUnitResponseFormat());
        safeUnitformats.put(0x83, changePasswordResponseFormat());
        safeUnitformats.put(0x82, checkUserPasswordResponseFormat());
        safeUnitformats.put(0x81, getSafeUnitInfoResponseFormat());
    }*/

   /* private String getSafeUnitInfoResponseFormat() {
        return "";
    }

    private String changePasswordResponseFormat() {
    }

    private String checkUserPasswordResponseFormat() {
    }

    private String lockSecurityUnitResponseFormat() {
    }

    private String unLockSecurityUnitResponseFormat() {
    }

    private String safeUnitOneReleaseResponseFormat() {
    }

    private String safeUnitTwoReleaseResponseFormat() {
    }

    private String storeKeyDataResponseFormat() {
    }

    private String readKeyDataResponseFormat() {
    }

    private String transparentForwardingESAMResponseFormat() {
    }

    private String getSafeUnitVersionCodeResponseFormat() {
    }*/

    /**
     * // 初始化命令码与响应码
     */
    private void initSelfOperationCommand_Response() {
        safeUnitCommands.put(0x0C, "获取安全单元版本号");
        safeUnitCommands.put(0x0A, "透明转发ESAM指令");
        safeUnitCommands.put(0x09, "读取关键数据");
        safeUnitCommands.put(0x08, "存储关键数据");
        safeUnitCommands.put(0x07, "二次发行安全单元");
        safeUnitCommands.put(0x06, "一次发行安全单元");
        safeUnitCommands.put(0x05, "解锁安全单元");
        safeUnitCommands.put(0x04, "锁定安全单元");
        safeUnitCommands.put(0x03, "修改操作员密码");
        safeUnitCommands.put(0x02, "验证操作员密码");
        safeUnitCommands.put(0x01, "获取安全单元信息");
        for (int i = 0; i < safeUnitCommands.size(); i++) {
            safeUnitResponses.put((safeUnitCommands.keyAt(i) + 0x80), safeUnitCommands.valueAt(i));
        }

    }

    /**
     * 初始化状态码
     */
    private void initSelfOperationErrors() {
        safeUnitErrors.put(0x8C, getSafeUnitVersionCodeErrors());
        safeUnitErrors.put(0x8A, transparentForwardingESAMErrors());
        safeUnitErrors.put(0x89, readKeyDataErrors());
        safeUnitErrors.put(0x88, storeKeyDataErrors());
        safeUnitErrors.put(0x87, safeUnitTwoReleaseErrors());
        safeUnitErrors.put(0x86, safeUnitOneReleaseErrors());
        safeUnitErrors.put(0x85, unLockSecurityUnitErrors());
        safeUnitErrors.put(0x84, lockSecurityUnitErrors());
        safeUnitErrors.put(0x83, changePasswordErrors());
        safeUnitErrors.put(0x82, checkUserPasswordErrors());
        safeUnitErrors.put(0x81, getSafeUnitInfoErrors());
    }

    /**
     * 获取安全单元信息
     *
     * @return SparseArray<Integer               ,                               String>
     */
    private SparseArray<String> getSafeUnitInfoErrors() {
        SparseArray<String> errors = new SparseArray<>();
        errors.put(0x00, "获取安全单元信息成功");
        errors.put(0x01, "安全单元有问题");
        errors.put(0x02, "获取C-ESAM序列号失败");
        errors.put(0x03, "获取操作者代码失败");
        errors.put(0x04, "获取权限和权限掩码失败");
        errors.put(0x05, "获取操作者信息失败");
        errors.put(0x06, "获取Y-ESAM信息失败");
        errors.put(0x07, "获取转加密剩余次数失败");
        errors.put(0x08, "获取密钥版本失败");
        errors.put(0x09, "获取主站证书失败");
        errors.put(0x0a, "获取终端证书失败");
        return errors;
    }

    /**
     * 验证操作员密码
     *
     * @return SparseArray<Integer               ,                               String>
     */
    private SparseArray<String> checkUserPasswordErrors() {
        SparseArray<String> errors = new SparseArray<>();
        errors.put(0x00, "验证操作员密码成功");
        errors.put(0x01, "获取密码密文、最大密码尝试次数、剩余密码尝试次数失败");
        errors.put(0x02, "最大密码尝试次数前后不相等");
        errors.put(0x03, "剩余密码尝试次数前后不相等");
        errors.put(0x04, "剩余密码次数为0");
        errors.put(0x05, "解密密码密文失败");
        errors.put(0x06, "密码尝试次数减1失败");
        errors.put(0x07, "密码不一致");
        errors.put(0x08, "恢复最大密码尝试次数失败");
        errors.put(0x09, "获取YESAM序列号失败");
        errors.put(0x0a, "获取YESAM序列号随机数失败");
        errors.put(0x0b, "加密随机数失败");
        errors.put(0x0c, "外部认证失败");
        return errors;
    }

    /**
     * 修改操作员密码
     *
     * @return SparseArray<Integer               ,                               String>
     */
    private SparseArray<String> changePasswordErrors() {
        SparseArray<String> errors = new SparseArray<>();
        errors.put(0x00, "修改操作员密码成功");
        errors.put(0x01, "获取密码密文、最大密码尝试次数、剩余密码尝试次数失败");
        errors.put(0x02, "最大密码尝试次数前后不相等");
        errors.put(0x03, "剩余密码尝试次数前后不相等");
        errors.put(0x04, "剩余密码次数为0");
        errors.put(0x05, "解密密码密文失败");
        errors.put(0x06, "密码不一致返回剩余密码尝试次数");
        errors.put(0x07, "恢复最大密码尝试次数失败");
        errors.put(0x08, "加密明文密码失败");
        errors.put(0x09, "修改密码失败");
        errors.put(0x0a, "获取YESAM序列号随机数失败");
        errors.put(0x0b, "获取YESAM随机数失败");
        errors.put(0x0c, "加密随机数失败");
        errors.put(0x0d, "外部认证失败");
        errors.put(0x0e, "解密修改后密码密文失败");
        errors.put(0x0f, "新修改密码和输入密码不一致");
        return errors;
    }

    /**
     * 锁定安全单元
     *
     * @return SparseArray<Integer               ,                               String>
     */
    private SparseArray<String> lockSecurityUnitErrors() {
        SparseArray<String> errors = new SparseArray<>();
        errors.put(0x00, "锁定安全单元成功");
        errors.put(0x01, "外部认证失败");
        errors.put(0x02, "清零失败");
        return errors;
    }

    /**
     * 解锁安全单元
     *
     * @return SparseArray<Integer               ,                               String>
     */
    private SparseArray<String> unLockSecurityUnitErrors() {
        SparseArray<String> errors = new SparseArray<>();
        errors.put(0x00, "解锁安全单元成功");
        errors.put(0x01, "外部认证失败");
        errors.put(0x02, "获取密码尝试次数失败");
        errors.put(0x03, "修改最大密码尝试次数失败");
        errors.put(0x04, "加密明文密码失败");
        errors.put(0x05, "修改操作员密码失败");
        return errors;
    }

    /**
     * 一次发行安全单元
     *
     * @return SparseArray<Integer               ,                               String>
     */
    private SparseArray<String> safeUnitOneReleaseErrors() {
        SparseArray<String> errors = new SparseArray<>();
        errors.put(0x00, "一次发行安全单元成功");
        errors.put(0x01, "一般错误");
        errors.put(0x02, "ESAM没有返回0x55");
        errors.put(0x03, "ESAM返回错误码");
        return errors;
    }

    /**
     * 二次发行安全单元
     *
     * @return SparseArray<Integer               ,                               String>
     */
    private SparseArray<String> safeUnitTwoReleaseErrors() {
        SparseArray<String> errors = new SparseArray<>();
        errors.put(0x00, "二次发行安全单元成功");
        errors.put(0x01, "一般错误");
        errors.put(0x02, "ESAM没有返回0x55");
        errors.put(0x03, "ESAM返回错误码");
        return errors;
    }

    /**
     * 存储关键数据
     *
     * @return SparseArray<Integer               ,                               String>
     */
    private SparseArray<String> storeKeyDataErrors() {
        SparseArray<String> errors = new SparseArray<>();
        errors.put(0x00, "存储关键数据成功");
        errors.put(0x01, "存储失败");

        return errors;
    }

    /**
     * 读取关键数据
     *
     * @return SparseArray<Integer               ,                               String>
     */
    private SparseArray<String> readKeyDataErrors() {
        SparseArray<String> errors = new SparseArray<>();
        errors.put(0x00, "读取关键数据成功");
        errors.put(0x01, "读取失败");

        return errors;
    }

    /**
     * 透明转发ESAM指令
     *
     * @return SparseArray<Integer               ,                               String>
     */
    private SparseArray<String> transparentForwardingESAMErrors() {
        SparseArray<String> errors = new SparseArray<>();
        errors.put(0x00, "透明转发ESAM指令成功");
        errors.put(0x01, "一般错误");
        errors.put(0x02, "ESAM没有返回0x55");
        errors.put(0x03, "ESAM返回错误码");
        return errors;
    }

    /**
     * 获取安全单元版本号
     *
     * @return SparseArray<Integer               ,                               String>
     */
    private SparseArray<String> getSafeUnitVersionCodeErrors() {
        SparseArray<String> errors = new SparseArray<>();
        errors.put(0x00, "获取安全单元版本号成功");
        errors.put(0x01, "获取失败");
        return errors;
    }


}