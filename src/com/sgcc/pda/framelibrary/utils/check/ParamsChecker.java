package com.sgcc.pda.framelibrary.utils.check;


import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sgcc.pda.framelibrary.utils.NumberConvert;
import com.sgcc.pda.framelibrary.utils.TextUtils;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;



/**
 * Created by qinling on 2019/7/24 9:49
 * Description: 参数校验工具
 */
public class ParamsChecker {
    public static void main(String[] args) {
        String json = "{\"meterAddress\":\"000000000003\",\"meterAgreement\":\"01\",\"optData\":{\"dateEfficient\":\"1907181845\",\"dateList\":[{\"dateValue\":\"0301\",\"timeCode\":\"1\"},{\"dateValue\":\"0601\",\"timeCode\":\"2\"},{\"dateValue\":\"0901\",\"timeCode\":\"3\"},{\"dateValue\":\"1201\",\"timeCode\":\"4\"}],\"optCode\":\"12\",\"priceEfficient\":\"1907181845\",\"priceList\":[{\"timeBeg\":\"0800\",\"timeCode\":\"1\",\"timeType\":\"01\"},{\"timeBeg\":\"2000\",\"timeCode\":\"1\",\"timeType\":\"02\"},{\"timeBeg\":\"0901\",\"timeCode\":\"2\",\"timeType\":\"03\"},{\"timeBeg\":\"2100\",\"timeCode\":\"2\",\"timeType\":\"04\"}],\"timeEfficient\":\"1907181845\"},\"optCode\":\"15\"}";
       String json2 = "{\"meterAddress\":\"000000000003\",\"meterAgreement\":\"01\",\"optData\":{\"balanceDayArray\":[\"010101\",\"060101\",\"100101\"],\"dateEfficient\":\"1907011513\",\"dateList\":[{\"dateValue\":\"0301\",\"timeCode\":\"1\"},{\"dateValue\":\"0601\",\"timeCode\":\"3\"},{\"dateValue\":\"0901\",\"timeCode\":\"2\"},{\"dateValue\":\"1201\",\"timeCode\":\"4\"}],\"optCode\":\"14\",\"priceArray\":[\"0.3333\",\"0.4\",\"0.5\"],\"priceEfficient\":\"1907011513\",\"priceList\":[{\"price\":\"0.1100\",\"timeBeg\":\"0801\",\"timeCode\":\"1\",\"timeType\":\"01\"},{\"price\":\"0.12\",\"timeBeg\":\"2001\",\"timeCode\":\"1\",\"timeType\":\"02\"},{\"price\":\"0.21\",\"timeBeg\":\"0802\",\"timeCode\":\"2\",\"timeType\":\"03\"},{\"price\":\"0.22\",\"timeBeg\":\"2002\",\"timeCode\":\"2\",\"timeType\":\"04\"},{\"price\":\"0.31\",\"timeBeg\":\"0803\",\"timeCode\":\"3\",\"timeType\":\"05\"},{\"price\":\"0.32\",\"timeBeg\":\"2003\",\"timeCode\":\"3\",\"timeType\":\"06\"},{\"price\":\"0.41\",\"timeBeg\":\"0804\",\"timeCode\":\"4\",\"timeType\":\"07\"},{\"price\":\"0.42\",\"timeBeg\":\"2004\",\"timeCode\":\"4\",\"timeType\":\"08\"}],\"theaterArray\":[\"0\",\"100\",\"200\"],\"timeEfficient\":\"1907011513\"},\"optCode\":\"15\"}";
        ParamsChecker paramsChecker = new ParamsChecker();
        System.out.println(paramsChecker.checkTaskData(Constant.TaskType.METER_PRICE_ADJUST,json).getErrMessage());
        System.out.println(paramsChecker.checkTaskData(Constant.TaskType.METER_PRICE_ADJUST,json2).getErrMessage());
    }

    /**
     * 传入的json 原文
     */
    private String json;

    public ParamsChecker() {

    }

    private HZErrorCode check(String json) {
        this.json = json;

        //  HZErrorCode errorCode = HZErrorCode.Error_000000;
        // 入参为空
        if (NumberConvert.isEmpty(json)) return HZErrorCode.Error_020001;
        GetBusiAuth getBusiAuth = getGetBusiAuth(json);
        // json解析失败
        if (null == getBusiAuth) return HZErrorCode.Error_020002;
        // 应用类型 ， 2字节，纯数字？？？
        String appType = getBusiAuth.getAppType();
        if (NumberConvert.isEmpty(appType)) return HZErrorCode.Error_020003;
        // 工单号
        String orderNo = getBusiAuth.getOrderNo();
        if (NumberConvert.isEmpty(orderNo)) return HZErrorCode.Error_020004;

        // 计量设备通信规约类型
        String meterAgreement = getBusiAuth.getMeterAgreement();
        if (NumberConvert.isEmpty(meterAgreement)|| meterAgreement.length()!=2) return HZErrorCode.Error_020005;
        int agreementNum = NumberConvert.parseInt(meterAgreement, -1);
        if (!(agreementNum == 1 || agreementNum == 2 || agreementNum == 3)) {
            return HZErrorCode.Error_020006;
        }
        // 保证meterAgreement 的格式为 01，02，03 ，例如：若是传1也将会被格式化为01
    /*    meterAgreement = NumberConvert.addZeroToStringLeft(meterAgreement, 2);
        // 若是 001 则格式为 01
        meterAgreement = meterAgreement.length() > 2 ? meterAgreement.substring(meterAgreement.length() - 2) : meterAgreement;
        getBusiAuth.setMeterAgreement(meterAgreement);*/

        // taskType
        String taskType = getBusiAuth.getTaskType();
        if (NumberConvert.isEmpty(taskType)) return HZErrorCode.Error_020007;
        Constant.TaskType task = getTaskTypeByCode(taskType);
        if (null == task) return HZErrorCode.Error_020008;

        // taskData
        TaskData taskData = getBusiAuth.getTaskData();
        if (null == taskData) return HZErrorCode.Error_020010;
        // 校验 任务数据
        return checkTaskData(/*getBusiAuth,*/ task, taskData.toJson());
    }

    /**
     * 校验任务数据
     *
     * @param task
     * @param taskDataStr
     * @return
     */
    public HZErrorCode checkTaskData(/*GetBusiAuth getBusiAuth,*/ Constant.TaskType task, String taskDataStr) {
        if (NumberConvert.isEmpty(taskDataStr)) return HZErrorCode.Error_020009;
        TaskData taskData = getTaskData(taskDataStr);

        if (null == taskData) return HZErrorCode.Error_020010;
        HZErrorCode errorCode = HZErrorCode.Error_000000;
        switch (task) {
            case METER_CHECK_TIME:
                // 电表现场校时 01
            case METER_READ_TIME:
                // 时钟读取 02
            case METER_SWITCH_OFF:
                // 跳闸 03
            case METER_SWITCH_ON:
                // 合闸 04
            case METER_POWER_RELEASE:
                // 保电解除 07
                // 上面几条只用到了表地址
                errorCode = checkMeterAddress(taskData);
                break;
            case METER_READ_DATA:
                // 抄读 05
                errorCode = checkMeterReadData(taskData);
                break;
            case METER_RECHARGE:
                // 充值 10
            case METER_ACCOUNT_OPEN:
                // 开户 11
                errorCode = checkRechargeAndAccountOpen(taskData);
                break;
            case METER_DATE_ADJUST:
                // 结算日调整 20
                errorCode = checkAccountDay(taskData);
                break;
            case METER_PRICE_ADJUST:
                // 电价调整 21
                errorCode = checkAdjustPrice(taskData);
                break;
            case TERMINAL_CHECK_TIME:
                // 终端现场校时 0531
            case TERMINAL_READ_TIME:
                // 终端时钟读取 0C02
                errorCode = checkTerminalLogicalAddress(taskData);
                break;
            case TERMINAL_READ_DATA:
                // 终端数据读取 0A00
                break;
            default:
                // 未知的任务类型
                errorCode = HZErrorCode.Error_020008;
                break;
        }
        //  有些校验将可以格式化的数据进行了预处理。
       /* if (null!=getBusiAuth){
            getBusiAuth.setTaskData(taskData.toJson());
        }*/
        return errorCode;
    }

    /**
     * 校验电价调整
     *
     * @param taskData 任务数据实体类
     * @return
     */
    private HZErrorCode checkAdjustPrice(TaskData taskData) {
        // 校验表地址
        HZErrorCode errorCode = checkMeterAddress(taskData);
        if (errorCode != HZErrorCode.Error_000000) return errorCode;

        // 校验操作类型
        String optCode = taskData.getOptCode();
        int optCodeNum = NumberConvert.parseInt(optCode, -1);
        if (NumberConvert.isEmpty(optCode) || optCodeNum < 0) return HZErrorCode.Error_020030;
        Constant.OptCode optType = null;
        Constant.OptCode[] optCodes = Constant.OptCode.values();
        for (Constant.OptCode opt : optCodes) {
            if (optCode.equals(opt.getValue())) {
                optType = opt;
                break;
            }
        }
        // 若是不存在该操作类型
        if (null == optType) return HZErrorCode.Error_020031;
    /*    optCode = NumberConvert.addZeroToStringLeft(optCode,2);
        // 若是 001 则格式为 01
        optCode = optCode.length() > 2 ? optCode.substring(optCode.length() - 2) : optCode;
        taskData.setOptCode(optCode);*/

        //  校验操作数据
        OptData optData = taskData.getOptData();
        if (null == optData) return HZErrorCode.Error_020032;
        return checkAdjustPriceOptData(optType, optData);

    }



    private HZErrorCode checkAdjustPriceOptData(Constant.OptCode optType, OptData optData) {
        HZErrorCode errorCode = HZErrorCode.Error_000000;
        switch (optType) {
            case LADDER_PRICE:
                // 阶梯电价调整 01
                errorCode = checkLadderPriceAdjust(optData);
                break;
            case RATE:
                // 费率调整 02
                errorCode = checkRateAdjust(optData);
                break;
            case TIME_PROID:
                // 时段调整 04
                errorCode = checkTimeProidAdjust(optData);
                break;
            case TIME_PROID_RATE:
                // 时段费率调整 06
                errorCode = checkTimeProidAndRateAdjust(optData);
                break;
            case TIME_ZONE:
                // 时区调整 08
                errorCode = checkTimeZoneAdjust(optData);
                break;
            case TIME_ZONE_TIME_PROID:
                // 时区时段调整 12
                errorCode = checkTimeZoneAndProidAdjust(optData);
                break;
            case TIME_ZONE_TIME_PROID_RATE:
                // 时区时段费率调整 14
                errorCode = checkTimeZoneProidAndRateAdjust(optData);
                break;
            case TIME_ZONE_TIME_PROID_RATE_LADDER_PRICE:
                // 时区时段费率阶梯电价调整 15
                errorCode = checkTimeZoneProidRateAndLadderPriceAdjust(optData);
                break;
            default:
                // 未知的任务类型
                errorCode = HZErrorCode.Error_020031;
                break;
        }
        //  有些校验将可以格式化的数据进行了预处理。
        // getBusiAuth.setTaskData(taskData.toJson());
        return errorCode;
    }


    /**
     * 校验阶梯电价
     *
     * @param optData
     * @return
     */
    private HZErrorCode checkLadderPriceAdjust(OptData optData) {
        // 阶梯电价
        List<String> prices = optData.getPriceArray();
        if (EmptyUtils.isEmpty(prices)) return HZErrorCode.Error_020039;
        for (int i = 0; i < prices.size(); i++) {
            if (!isNumberWithPoint(prices.get(i),4)) return HZErrorCode.Error_020039;
        }

        // 结算日
        List<String> balanceDays = optData.getBalanceDayArray();
        if (EmptyUtils.isEmpty(balanceDays)) return HZErrorCode.Error_020040;
        for (int i = 0; i < balanceDays.size(); i++) {
            // MMddHH
            String blanceDay = balanceDays.get(i);
            if (NumberConvert.isEmpty(blanceDay) || blanceDay.length()!=6)return HZErrorCode.Error_020040;
            if (NumberConvert.isEmpty(formatTimeNum(blanceDay.substring(0,4),TimeType.TIME_ZONE))
                    ||NumberConvert.isEmpty(formatTimeNum(blanceDay.substring(2,6),TimeType.TIME_BALANCE)) ) return HZErrorCode.Error_020040;
        }

        // 阶梯数据
        List<String> theates = optData.getTheaterArray();
        if (EmptyUtils.isEmpty(theates)) return HZErrorCode.Error_020041;
        for (int i = 0; i < theates.size(); i++) {
            // 最多到小数点后两位
            if (!isNumberWithPoint(theates.get(i),2)) return HZErrorCode.Error_020041;
        }

        // 校验切换时间
        if (!timeEfficientIsOk(optData.getPriceEfficient()))  return HZErrorCode.Error_020038;
        return HZErrorCode.Error_000000;
    }

    /**
     * 数据格式
     * 校验 费率
     * @param optData
     * @return
     */
    private HZErrorCode checkRateAdjust(OptData optData) {
        // 校验费率信息
        List<OptData.Price> prices = optData.getPriceList();
        if (EmptyUtils.isEmpty(prices)) return HZErrorCode.Error_020033;
        for (int i = 0; i < prices.size(); i++) {
            OptData.Price price = prices.get(i);
            if (EmptyUtils.isEmpty(price)) return HZErrorCode.Error_020033;
            // 校验费率值
            HZErrorCode errorCode = checkRateValue(price);
            if (errorCode != HZErrorCode.Error_000000) return errorCode;
            // 校验费率号
            HZErrorCode errorCode1 = checkTimeType(price);
            if (errorCode1 != HZErrorCode.Error_000000) return errorCode1;
        }

        // 校验切换时间
        if (!timeEfficientIsOk(optData.getPriceEfficient()))  return HZErrorCode.Error_020038;
        return HZErrorCode.Error_000000;
    }


    /**
     *  时段调整
     * @param optData
     * @return
     */
    private HZErrorCode checkTimeProidAdjust(OptData optData) {
        // 校验费率信息
        List<OptData.Price> prices = optData.getPriceList();
        if (EmptyUtils.isEmpty(prices)) return HZErrorCode.Error_020033;
        for (int i = 0; i < prices.size(); i++) {
            OptData.Price price = prices.get(i);
            if (EmptyUtils.isEmpty(price)) return HZErrorCode.Error_020033;
            // 校验费率号
            HZErrorCode errorCode1 = checkTimeType(price);
            if (errorCode1 != HZErrorCode.Error_000000) return errorCode1;
            // 校验日时段表号
            HZErrorCode errorCode2 = checkTimeCode(price);
            if (errorCode2 != HZErrorCode.Error_000000) return errorCode2;
            // 校验时段
            HZErrorCode errorCode3 = checkTimeBeg(price);
            if (errorCode3 != HZErrorCode.Error_000000) return errorCode3;
        }
        // 校验切换时间
        if (!timeEfficientIsOk(optData.getPriceEfficient()))  return HZErrorCode.Error_020038;
        return HZErrorCode.Error_000000;
    }

    /**
     *  校验时段 费率
     * @param optData
     * @return
     */
    private HZErrorCode checkTimeProidAndRateAdjust(OptData optData) {
        HZErrorCode errorCodeRate = checkRateAdjust(optData);
        if (errorCodeRate != HZErrorCode.Error_000000) return errorCodeRate;

        HZErrorCode errorCodeTimeProid = checkTimeProidAdjust(optData);
        if (errorCodeTimeProid != HZErrorCode.Error_000000) return errorCodeTimeProid;
        return HZErrorCode.Error_000000;
    }

    /**
     *  校验时区
     * @param optData
     * @return
     */
    private HZErrorCode checkTimeZoneAdjust(OptData optData) {
        List<OptData.Date> dates = optData.getDateList();
        if (EmptyUtils.isEmpty(dates)) return HZErrorCode.Error_020042;
        for (int i = 0; i < dates.size(); i++) {
            OptData.Date date = dates.get(i);
            if (EmptyUtils.isEmpty(date)) return HZErrorCode.Error_020042;
            // 校验时区
             String dateStr = formatTimeNum(date.getDateValue(),TimeType.TIME_ZONE);
            if (NumberConvert.isEmpty(dateStr))return HZErrorCode.Error_020042;
        }

        // 校验切换时间
        if (!timeEfficientIsOk(optData.getPriceEfficient()))  return HZErrorCode.Error_020038;
        return HZErrorCode.Error_000000;
    }

    /**
     *  校验时区时段
     * @param optData
     * @return
     */
    private HZErrorCode checkTimeZoneAndProidAdjust(OptData optData) {
        HZErrorCode errorCodeTimeZone = checkTimeZoneAdjust(optData);
        if (errorCodeTimeZone != HZErrorCode.Error_000000) return errorCodeTimeZone;

        HZErrorCode errorCodeTimeProid = checkTimeProidAdjust(optData);
        if (errorCodeTimeProid != HZErrorCode.Error_000000) return errorCodeTimeProid;
        return HZErrorCode.Error_000000;
    }

    /**
     *  校验时区时段费率
     * @param optData
     * @return
     */
    private HZErrorCode checkTimeZoneProidAndRateAdjust(OptData optData) {
        HZErrorCode errorCodeTimeZone = checkTimeZoneAdjust(optData);
        if (errorCodeTimeZone != HZErrorCode.Error_000000) return errorCodeTimeZone;

        HZErrorCode errorCodeTimeProidAndRate = checkTimeProidAndRateAdjust(optData);
        if (errorCodeTimeProidAndRate != HZErrorCode.Error_000000) return errorCodeTimeProidAndRate;
        return HZErrorCode.Error_000000;
    }

    /**
     *  校验时区时段费率阶梯电价调整
     * @param optData
     * @return
     */
    private HZErrorCode checkTimeZoneProidRateAndLadderPriceAdjust(OptData optData) {
        HZErrorCode errorCodeTimeZoneProidAndRate = checkTimeZoneProidAndRateAdjust(optData);
        if (errorCodeTimeZoneProidAndRate != HZErrorCode.Error_000000) return errorCodeTimeZoneProidAndRate;

        HZErrorCode errorCodeLadderPrice = checkLadderPriceAdjust(optData);
        if (errorCodeLadderPrice != HZErrorCode.Error_000000) return errorCodeLadderPrice;
        return HZErrorCode.Error_000000;
    }
    /**
     *  校验费率值
     * @param price
     * @return
     */
    private HZErrorCode checkRateValue(@NonNull OptData.Price price) {

        String priceValue = price.getPrice();
        // 是不是小数点后四位的数， 例如 0.1，0.11 为true， 0.11111则为false；
        if (!isNumberWithPoint(priceValue,4)) return HZErrorCode.Error_020034;
        return HZErrorCode.Error_000000;
    }

    /**
     *  校验 费率类型 通常只有  尖峰平谷四种
     * @param price 电价
     * @return
     */
    private HZErrorCode checkTimeType(@NonNull OptData.Price price) {

        String timeType = price.getTimeType();
        // 若 timeType 为空，且字符长度不为2，不是10进制字符串，返回格式错误
        if (NumberConvert.isEmpty(timeType) || timeType.length()!=2 || !NumberConvert.isDecimalStr(timeType)) {
            return HZErrorCode.Error_020035;
        }
        return HZErrorCode.Error_000000;
    }

    /**
     * 校验时段起始时间
     *
     * @param price  HHmm 0-24 0-59
     * @return
     */
    private HZErrorCode checkTimeBeg(OptData.Price price) {
        String timeBeg = price.getTimeBeg();
        // 若 timeType 为空，且字符长度不为4，另外小时与分钟超出范围
        if (NumberConvert.isEmpty(formatTimeNum(timeBeg,TimeType.TIME_PROID)) ) {
            return HZErrorCode.Error_020036;
        }
        return HZErrorCode.Error_000000;
    }

    /**
     * 校验日时段表号
     * @param price
     * @return
     */
    private HZErrorCode checkTimeCode(OptData.Price price) {
        String timeCode = price.getTimeCode();
        // 若 timeType 为空，且字符长度不为4，另外小时与分钟超出范围
        if (NumberConvert.isEmpty(timeCode) ) {
            return HZErrorCode.Error_020037;
        }
        // 日时段表号 取值范围1-8
        int timeCodeNum = NumberConvert.parseInt(timeCode,0);
        if (timeCodeNum>8 || timeCodeNum<=0) return HZErrorCode.Error_020037;
        return HZErrorCode.Error_000000;
    }


    /**
     * 校验电表抄读
     *
     * @param taskData
     * @return
     */
    private HZErrorCode checkMeterReadData(TaskData taskData) {
        // 校验表地址
        HZErrorCode errorCode = checkMeterAddress(taskData);
        if (errorCode != HZErrorCode.Error_000000) return errorCode;

        List<ReadDate> readDates = taskData.getReadDate();
        // 若抄读项集合为 空，
        if (null == readDates || readDates.size() == 0) return HZErrorCode.Error_020020;

        for (int i = 0; i < readDates.size(); i++) {
            ReadDate readDate = readDates.get(i);
            if (null == readDate) return HZErrorCode.Error_020020;
            String dataSign = readDate.getReadDataCode();
            // 若数据标识为空，长度不是8，不是16进制
            if (NumberConvert.isEmpty(dataSign) || dataSign.length() != 8 || !NumberConvert.isHexStr(dataSign)) {
                return HZErrorCode.Error_020021;
            }
            // 至 2019.07.24时 需要起始时间和结束时间的抄读项
            String[] dataSigns = new String[]{"05060101", "05060201", "0001FF00", "0002FF00", "0003FF00",
                    "0001FF01", "0002FF01", "0003FF01"};
            boolean isNeedTime = false;
            for (String dataCode : dataSigns) {
                // 是否与上述标识一致，若一致，则置为true，并跳出循环
                isNeedTime = dataSign.equalsIgnoreCase(dataCode);
                if (isNeedTime) break;
            }
            // 需要时间校验
            if (isNeedTime) {
                String startTime = readDate.getReadBeginDate();
                String endTime = readDate.getReadEndDate();
                // 时间格式为 2019-07-03
                return checkMeterReadDataTime(dataSign, startTime, endTime);

            }
        }


        return HZErrorCode.Error_000000;
    }

    /**
     * 校验抄读时 起始时间以及结束时间
     *
     * @param dataSign  数据标识
     * @param startTime 起始时间
     * @param endTime   结束时间
     *                  时间格式为 yyyy-MM-dd
     * @return
     */
    private HZErrorCode checkMeterReadDataTime(String dataSign, String startTime, String endTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String currentTime = simpleDateFormat.format(new Date());
        Date startDate = null;
        Date endDate = null;
        Date currentDate = null;
        try {
            startDate = simpleDateFormat.parse(startTime);
            endDate = simpleDateFormat.parse(endTime);
            currentDate = simpleDateFormat.parse(currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 按照yyyy-MM-dd 解析失败，故格式有误。
        if (null == startDate) return HZErrorCode.Error_020022;
        if (null == endDate) return HZErrorCode.Error_020023;

        // 冻结数据
        if (dataSign.startsWith("05")) {
            // 判断截止时间与当前时间，需要相差一天
            // 此时时年月日，故整好若是endData,before(currentDate)为 true，正好会有一天以上的差值
            if (!endDate.before(currentDate)) return HZErrorCode.Error_020024;
            // 若是起始时间 在 结束时间之后 ，不合理！
            if (startDate.after(endDate)) return HZErrorCode.Error_020025;
            // 两个时间间隔 不能大于 60天
            if (endDate.getTime() - startDate.getTime() > 60 * 24 * 3600000) return HZErrorCode.Error_020026;
        } else {
            // 起始时间与结束时间都是当前时间
            if (!(startDate.equals(endDate) && endDate.equals(currentDate))) return HZErrorCode.Error_020027;
        }

        return HZErrorCode.Error_000000;
    }

    /**
     * 校验结算日
     * 至少有一个格式正确的结算日数据
     *
     * @param taskData 任务数据对象
     * @return
     */
    private HZErrorCode checkAccountDay(TaskData taskData) {
        // 校验表地址
        HZErrorCode errorCode = checkMeterAddress(taskData);
        if (errorCode != HZErrorCode.Error_000000) return errorCode;

        // 校验结算日1，2，3
        String[] blanceDays = new String[]{taskData.getFirstDay(), taskData.getSecondDay(), taskData.getThirdDay()};
        // 默认3个结算日数据都为空
        boolean isEmpty = true;
        for (String blanceDay : blanceDays) {
            // 若是存在一个不为空的值，便会变成false
            isEmpty = isEmpty && NumberConvert.isEmpty(blanceDay);
        }

        // 若不为空,则说明至少存在一个正常格式的结算日数据
        if (isEmpty) return HZErrorCode.Error_020015;

        // 校验结算日格式
        HZErrorCode[] errorCodes = new HZErrorCode[]{HZErrorCode.Error_020016, HZErrorCode.Error_020017, HZErrorCode.Error_020018};
        for (int i = 0; i < blanceDays.length; i++) {
            // 若是数据不为空，则需要对数据格式进行校验
            if (!NumberConvert.isEmpty(blanceDays[i])) {
                // 若是结算日格式不对，会返回为空字符串
                String blanceDay = formatTimeNum(blanceDays[i], TimeType.TIME_BALANCE);
                // 为空，则说明格式不对
                if (NumberConvert.isEmpty(blanceDay)) {
                    return errorCodes[i];
                }
                // todo 将格式化好的数据重新赋值给 任务数据
                //setFormatValue(taskData, i, blanceDay);
            }
        }
        return HZErrorCode.Error_000000;
    }

    /**
     * 此处重新赋值，只会将 301 改为 0301 保证字节数为两个字节
     *
     * @param taskData
     * @param i
     * @param blanceDay
     */
    private void setFormatValue(TaskData taskData, int i, String blanceDay) {
        if (i == 0) {
            taskData.setFirstDay(blanceDay);
        }
        if (i == 1) {
            taskData.setSecondDay(blanceDay);
        }
        if (i == 2) {
            taskData.setThirdDay(blanceDay);
        }
    }

    /**
     * 校验充值与开户的参数
     *
     * @param taskData 任务数据对象。
     * @return
     */
    private HZErrorCode checkRechargeAndAccountOpen(TaskData taskData) {
        // 校验表地址
        HZErrorCode errorCode = checkMeterAddress(taskData);
        if (errorCode != HZErrorCode.Error_000000) return errorCode;

        // 校验客户编号
        String consNo = taskData.getConsNo();
        // 若为空，字符串长度不为12，不是纯10进制
        if (NumberConvert.isEmpty(consNo) || consNo.length() != 12 || !NumberConvert.isDecimalStr(consNo)) {
            return HZErrorCode.Error_020012;
        }

        // 校验充值金额
        String fillMoney = taskData.getFillMoney();
        // 解析充值金额，若是解析失败返回  -1
        double fillMoneyNum = NumberConvert.parseDouble(fillMoney, -1.0);
        // 若为空，或者 解析失败，或者充值金额为0 或者大于999999.99（若是大于999999，无法写入电表，最大为999999.99元）
        if (NumberConvert.isEmpty(fillMoney) || fillMoneyNum <= 0 || fillMoneyNum > 999999.99) {
            return HZErrorCode.Error_020013;
        }
        // fixme 是否需要将充值金额处理一下？？ 格式化一下
        if (!isMoneyNumber(fillMoney)) {
            return HZErrorCode.Error_020013;
        }

        // 校验充值次数
        String fillNum = taskData.getFillNum();
        // 若是解析失败 赋值为-1
        int fillCount = NumberConvert.parseInt(fillMoney, -1);
        // 若为空，或者 解析失败，或者充值次数小于等于0
        if (NumberConvert.isEmpty(fillNum) || fillCount <= 0) {
            return HZErrorCode.Error_020014;
        }
        return HZErrorCode.Error_000000;
    }

    /**
     * 是不是金钱
     *
     * @param str
     * @return
     */
    public static boolean isMoneyNumber(String str) {
        // 判断小数点后2位的数字的正则表达式
        return isNumberWithPoint(str,2);
    }

    public static boolean isNumberWithPoint(String str,int afterPointPos) {
        if (TextUtils.isEmpty(str)) return false;
        // 判断小数点后afterPointPos位的数字的正则表达式
        String REGEX = String.format(Locale.CHINA,"^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,%d})?$",afterPointPos);
        Pattern pattern = Pattern.compile(REGEX);
        java.util.regex.Matcher match = pattern.matcher(str);
        return match.matches();
    }

    /**
     * 校验电表地址
     *
     * @param taskData
     * @return
     */
    private HZErrorCode checkMeterAddress(TaskData taskData) {
        // 电表地址不能为空,且必须是10进制字符串
        String meterAddress = taskData.getMeterAddress();
        return NumberConvert.isEmpty(meterAddress) || !NumberConvert.isDecimalStr(meterAddress)
                ? HZErrorCode.Error_020011
                : HZErrorCode.Error_000000;
    }

    /**
     * 校验终端逻辑地址
     *
     * @param taskData
     * @return
     */
    private static final String SPLITE = "_";

    private HZErrorCode checkTerminalLogicalAddress(TaskData taskData) {

        // 终端逻辑地址不能为空,且必须是16进制字符串 长度8-9位
        String terminalLogicalAddress = taskData.getTmnlLAddr();
        // 若存在“_”,先把“_”删掉再判断是否为16进制
        terminalLogicalAddress = terminalLogicalAddress.contains(SPLITE)
                ? terminalLogicalAddress.replaceAll(SPLITE, "")
                : terminalLogicalAddress;

        return NumberConvert.isEmpty(terminalLogicalAddress)
                || !NumberConvert.isHexStr(terminalLogicalAddress)
                || terminalLogicalAddress.length() > 9
                || terminalLogicalAddress.length() < 8
                ? HZErrorCode.Error_020019
                : HZErrorCode.Error_000000;
    }

    /**
     * 将传入的 任务类型字符串与现有的任务类型比对
     *
     * @param taskType 若存在则返回，不存在返回 null
     * @return
     */
    private Constant.TaskType getTaskTypeByCode(String taskType) {
        Constant.TaskType[] taskTypes = Constant.TaskType.values();
        for (Constant.TaskType task : taskTypes) {
            // 在现有的任务类型中找不到
            if (task.getCode().equalsIgnoreCase(taskType)) {
                return task;
            }
        }
        return null;
    }


    private GetBusiAuth getGetBusiAuth(String json) {
        try {
            return new Gson().fromJson(json, GetBusiAuth.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    private TaskData getTaskData(String json) {
        try {
            return new Gson().fromJson(json, TaskData.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static class GetBusiAuth {
        public TaskData getTaskData() {
            return taskData;
        }

        /**
         * 工单号
         */
        private String orderNo;
        /**
         * 应用类型
         */
        private String appType;
        /**
         * 已经不再使用，根据taskType便可以知晓是电表还是终端
         */
        private String deviceType;
        /**
         * 电表	01	现场校时
         * 02	读取时钟
         * 03	跳闸
         * 04	合闸
         * 05	现场抄读
         * 07	保电解除
         * 10	充值
         * 11	开户
         * 20	结算日调整
         * 21	电价调整
         * 终端	0531	现场校时
         * 0C02	读取时钟
         * 0A00	终端数据读取
         */
        private String taskType;

        public String getMeterAgreement() {
            return meterAgreement;
        }

        public void setMeterAgreement(String meterAgreement) {
            this.meterAgreement = meterAgreement;
        }

        /**
         * 计量设备通信规约类型
         * 01	DL/T 645
         * 02	Q/GDW 1376.1
         * 03	DL/T 698
         */
        private String meterAgreement;
        private TaskData taskData;

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getAppType() {
            return appType;
        }

        public void setAppType(String appType) {
            this.appType = appType;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getTaskType() {
            return taskType;
        }

        public void setTaskType(String taskType) {
            this.taskType = taskType;
        }

/*      //  public String getTaskData() {
            return taskData;
        }

      //  public void setTaskData(String taskData) {
            this.taskData = taskData;
        }*/

    }

    public static final class TaskData  {
        /**
         * 表地址
         */
        private String meterAddress;
        /**
         * 截止时间,貌似一直未利用到
         */
        private String endTime;

        /**
         * 第一结算日
         */
        private String firstDay;
        /**
         * 第2结算日
         */
        private String secondDay;
        /**
         * 第3结算日
         */
        private String thirdDay;

        /**
         * 客户编号
         */
        private String consNo;
        /**
         * 充值金额 单位：元，十进制
         */
        private String fillMoney;
        /**
         * 第3结算日
         */
        private String fillNum;

        /**
         * 终端校时,终端时钟读取,终端数据读取
         * 终端逻辑地址
         */
        private String tmnlLAddr;
        /**
         * 多数据集
         * 终端数据读取
         */
        private List<Data> datas;
        /**
         * 电表数据抄读
         */
        private List<ReadDate> readDate;

        public String getOptCode() {
            return optCode;
        }

        public void setOptCode(String optCode) {
            this.optCode = optCode;
        }

        public OptData getOptData() {
            return optData;
        }

        public void setOptData(OptData optData) {
            this.optData = optData;
        }

        /**
         * 电价调整 操作类型
         * 01 阶梯电价调整，暂时未实现
         * 02 费率调整，
         * 04 时段调整，
         * 06 时段费率调整，未测试
         * 08 时区调整，前置不支持
         * 12 时区时段调整
         * 14 时区时段费率调整
         * 15 时区时段费率阶梯电价调整， 阶梯与费率基本不存在同时调整的可能
         */
        private String optCode;
        /**
         * 操作类型
         */
        private OptData optData;

        public String getMeterAddress() {
            return meterAddress;
        }

        public void setMeterAddress(String meterAddress) {
            this.meterAddress = meterAddress;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getFirstDay() {
            return firstDay;
        }

        public void setFirstDay(String firstDay) {
            this.firstDay = firstDay;
        }

        public String getSecondDay() {
            return secondDay;
        }

        public void setSecondDay(String secondDay) {
            this.secondDay = secondDay;
        }

        public String getThirdDay() {
            return thirdDay;
        }

        public void setThirdDay(String thirdDay) {
            this.thirdDay = thirdDay;
        }

        public String getConsNo() {
            return consNo;
        }

        public void setConsNo(String consNo) {
            this.consNo = consNo;
        }

        public String getFillMoney() {
            return fillMoney;
        }

        public void setFillMoney(String fillMoney) {
            this.fillMoney = fillMoney;
        }

        public String getFillNum() {
            return fillNum;
        }

        public void setFillNum(String fillNum) {
            this.fillNum = fillNum;
        }

        public String getTmnlLAddr() {
            return tmnlLAddr;
        }

        public void setTmnlLAddr(String tmnlLAddr) {
            this.tmnlLAddr = tmnlLAddr;
        }

        public List<Data> getDatas() {
            return datas;
        }

        public void setDatas(List<Data> datas) {
            this.datas = datas;
        }

        public List<ReadDate> getReadDate() {
            return readDate;
        }

        public void setReadDate(List<ReadDate> readDate) {
            this.readDate = readDate;
        }

        public String toJson() {
            return new Gson().toJson(this);
        }
    }

    public static final class Data {
        /**
         * 数据标识
         */
        private String dataItem;

        public String getDataItem() {
            return dataItem;
        }

        public void setDataItem(String dataItem) {
            this.dataItem = dataItem;
        }
    }

    public static final class ReadDate {
        /**
         * 抄读数据项编码，抄当前电能示数、其他参数时无抄读起始、截止日期值
         */
        private String readDataCode;
        /**
         * 本次抄读起始日期 yyyy-MM-dd
         */
        private String readBeginDate;
        /**
         * 本次抄读截止日期 yyyy-MM-dd
         */
        private String readEndDate;

        public String getReadDataCode() {
            return readDataCode;
        }

        public void setReadDataCode(String readDataCode) {
            this.readDataCode = readDataCode;
        }

        public String getReadBeginDate() {
            return readBeginDate;
        }

        public void setReadBeginDate(String readBeginDate) {
            this.readBeginDate = readBeginDate;
        }

        public String getReadEndDate() {
            return readEndDate;
        }

        public void setReadEndDate(String readEndDate) {
            this.readEndDate = readEndDate;
        }
    }


    public enum TimeType {
        /**
         * 时区 1月到12月  1-31天
         */
        TIME_ZONE(1, 12, 1, 31),

        /**
         * 时段 0-24时，0-59秒
         */
        TIME_PROID(0, 24, 0, 59),
        /**
         * 结算日
         */
        TIME_BALANCE(1, 31, 0, 24),;
        private int start_first;
        private int end_first;

        private int start_second;
        private int end_second;

        TimeType(int start_first, int end_first, int start_second, int end_second) {
            this.start_first = start_first;
            this.end_first = end_first;
            this.start_second = start_second;
            this.end_second = end_second;
        }
    }

    private static String formatTimeNum(String numStr, TimeType timeType) {
       /* if (NumberConvert.isEmpty(numStr) || numStr.length() < 3 || numStr.length() > 4) {
            return "";
        }*/
        if (NumberConvert.isEmpty(numStr) || numStr.length() != 4) {
            return "";
        }
        try {
            String format = String.format("%04d", Integer.parseInt(numStr));
            int firstMum = Integer.parseInt(format.substring(0, 2));
            int secondMum = Integer.parseInt(format.substring(2, 4));
            return (firstMum >= timeType.start_first && firstMum <= timeType.end_first)
                    && (secondMum >= timeType.start_second && secondMum <= timeType.end_second)
                    ? format : "";
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "";
        }
    }

    @StringDef({yyyyMMddHHmmss, yyMMddHHmm,})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TimeFormat {
    }
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String yyMMddHHmm = "yyMMddHHmm";
    /**
     * 时间格式是否正确
     *
     * @param timeEfficient 切换时间
     * @param timeFormat 时间格式
     * @return
     */
    private boolean timeIsOk(String timeEfficient,@TimeFormat String timeFormat) {
        if (NumberConvert.isEmpty(timeEfficient) || timeEfficient.length()!=timeFormat.length()) return false;
        SimpleDateFormat format = new SimpleDateFormat(timeFormat,Locale.CHINA);
        try {
            Date date = format.parse(timeEfficient);
            return null != date;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 切换时间格式是否正确
     *
     * @param timeEfficient 切换时间
     * @return
     */
    private boolean timeEfficientIsOk(String timeEfficient) {
       return timeIsOk(timeEfficient,yyMMddHHmm);
    }
}
