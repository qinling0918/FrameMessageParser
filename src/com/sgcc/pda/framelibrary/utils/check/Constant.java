package com.sgcc.pda.framelibrary.utils.check;



/**
 * @author:fubiao
 * @Date: 2018/3/28
 * 安全插件电表操作常量
 */
public class Constant {

    public static final boolean HAVE_RESUM = false;

    public static final int SAFE_UNIT_DEFAULT = 0;//安全单元默认参数
    public static final int SAFE_UNIT1 = 1;//安全单元1
    public static final int SAFE_UNIT2 = 2;//安全单元2

    /**
     * 安全单元版本
     */
    public static volatile int SECURITY_VERSION = SAFE_UNIT_DEFAULT;

    public static final String SY_IDAUTH = "01";//私钥身份认证
    public static final String CONTROL_AUTHRITY = "02";//控制
    public static final String PARAM2 = "03";//2类参数
    public static final String RECHARGE = "04";//充值参数
    public static final String PARAM1 = "05";//1类参数
    public static final String RATE1 = "06";//第1套费率
    public static final String RATE2 = "07";//第2套费率
    public static final String SY_INFRA = "08";//私钥红外
    public static final String GY_INFRA = "09";//公钥红外
    public static final String INFRA_AUTHRITY = "11";//红外



    public static final String Meter645 = "01";//645电表
    public static final String Meter1376 = "02";//1376.1
    public static final String Meter698 = "03";//698电表

    public static final String Infra = "01";//普通红外
    public static final String LaserInfra = "02";//激光红外
    public static final String Serial = "04";//串口


    public static final int StandAlone = 0;//独立模式
    public static final int DependOn = 1;//依赖模式


    public static final String THINTA_COMM = "thinta_comm";
    public static final String COMM_IP = "comm_ip";//IP地址
    public static final String COMM_PORT = "comm_port";//端口号

    public static final String LOGIN_ = "LOGIN_";
    public static final String LOGIN_UID = "LOGIN_UID";


    public static final String EQUAL = "1";
    public static final String MORETHANEND = "2";
    public static final String LESSTHANBEGIN = "3";
    public static final String BETWEEN = "4";

    public static final String CESAM_VERSION = "00";//CESAM_VERSION公钥  密钥版本第一个字节
    public static final String YESAM_VERSION = "00";//YESAM_VERSION公钥   密钥版本第一个字节
    public static final String GY_TYPE = "00";//公钥
    public static final String SY_TYPE = "01";//私钥

    /**
     * 红外
     */
    public static final String COMM_TYPE_INFRA = "01";
    /**
     * 激光红外
     */
    public static final String COMM_TYPE_ON_INFRA = "02";
    /**
     * 蓝牙
     */
    public static final String COMM_TYPE_BLUE_TOOTH = "03";
    /**
     * 485
     */
    public static final String COMM_TYPE_RS485 = "04";
    /**
     * RS232串口
     */
    public static final String COMM_TYPE_RS232 = "05";
    /**
     * Wifi
     */
    public static final String COMM_TYPE_WIFI = "06";
    /**
     * 645
     */
    public static final String METER_645 = "01";
    /**
     * Q/GDW 1376.1
     */
    public static final String METER_376 = "02";
    /**
     * 698
     */
    public static final String METER_698 = "03";


    public enum TaskType {
        /**
         * 任务类型
         */
        METER_CHECK_TIME(01,"01", "现场校时"),
        METER_READ_TIME(02, "02","时钟读取"),
        METER_SWITCH_OFF(03, "03","跳闸"),
        METER_SWITCH_ON(04, "04","合闸"),
        METER_READ_DATA(05, "05","现场抄读"),
        METER_POWER_RELEASE(07, "07","保电解除"),
        METER_RECHARGE(10, "10","充值"),
        METER_ACCOUNT_OPEN(11,"11", "开户"),
        METER_DATE_ADJUST(20, "20","结算日调整"),
        METER_PRICE_ADJUST(21, "21","电价调整"),
        TERMINAL_CHECK_TIME(0x0531, "0531","现场校时"),
        TERMINAL_READ_TIME(0x0C02, "0C02","时钟读取"),
        TERMINAL_READ_DATA(0x0A00, "0A00","终端数据读取"),;
        int value;
        String name;
        String code;

        public int getValue() {
            return value;
        }
        public String getCode() {
            return code;
        }
        TaskType(int value, String code, String name) {
            this.value = value;
            this.code = code;
            this.name = name;
        }
    }

    public enum MeterType {
        /**
         * 电表类型
         */
        METER_09(9, "09"),
        METER_13(13, "13"),
        METER_698(698, "698"),
        METER_97(97, "97"),;
        int value;
        String name;

        public int getValue() {
            return value;
        }

        MeterType(int value, String name) {
            this.value = value;
            this.name = name;
        }
    }
    public enum OptCode {
        /**
         * * 电价调整 操作类型
         *  01 阶梯电价调整，暂时未实现
         *  02 费率调整，
         *  04 时段调整，
         *  06 时段费率调整，未测试
         *  08 时区调整，前置不支持
         *  12 时区时段调整
         *  14 时区时段费率调整
         *  15 时区时段费率阶梯电价调整， 阶梯与费率基本不存在同时调整的可能
         */
        LADDER_PRICE(1, "01","阶梯电价调整"),
        RATE(2, "02","费率调整"),
        TIME_PROID(4, "04","时段调整"),
        TIME_PROID_RATE(6, "06","时段费率调整"),
        TIME_ZONE(8, "08","时区调整"),
        TIME_ZONE_TIME_PROID(12, "12","时区时段调整"),
        TIME_ZONE_TIME_PROID_RATE(14, "14","时区时段费率调整"),
        TIME_ZONE_TIME_PROID_RATE_LADDER_PRICE(15, "15","时区时段费率阶梯电价调整"),
        ;
        int code;
        String name;
        String value;

        public String getValue() {
            return value;
        }
        public int getCode() {
            return code;
        }

        OptCode(int code, String value, String name) {
            this.code = code;
            this.value = value;
            this.name = name;
        }
    }
  /*  public enum CommType {
        *//**
         * @param value
         *//*
        INFRA(1, new A1BInfra()),
        LASER_INFRA(2, new A1BInfra()),
        BLUETOOTH(3, new A1BInfra()),
        Serial485(4, new A1BSerial()),
        RS232(5, new A1BSerial()),
        WIFI(6, new A1BInfra());
        private int value;

        public ICommunicate getCommunicate() {
            return communicate;
        }

        private ICommunicate communicate;

        CommType(int value, ICommunicate communicate) {
            this.value = value;
            this.communicate = communicate;
        }

        public int getValue() {
            return value;
        }


        public CommonMeter getCommonMeter645() {
            //初始化红外业务接口
            IBaseProtocol<P645Frame> p645 = new P645Protocol();
            IP645EssentialMethod infra645 = new CommonP645EssentialMethod(p645, getCommunicate());
            return new CommonMeter(infra645);

        }
        public CommonMeter698 getCommonMeter698() {
           return new CommonMeter698(new CommonP698EssentialMethod(getCommunicate()));

        }
        public static CommType getCommType(int commType) {
            for (CommType commType2 : CommType.values()) {
                if (commType2.getValue() == commType) {
                    return commType2;
                }
            }
            return  CommType.INFRA;
        }

    }

*/
    //数据分类
    public static final String ORDERBY_NUM = "1";//数量
    public static final String ORDERBY_DATA = "2";//信息
    public static final String ORDERBY_TIME = "3";//切换时间

    //698读写类型
    public static final String OPT_698_READ = "0";//698读取
    public static final String OPT_698_WRITE = "1";//698设置
    //645操作类型
    public static final String TYPE_FL_09_1 = "1";//09第一套费率
    public static final String TYPE_FL_09_2 = "2";//09第二套费率
    public static final String TYPE_FL_CHANGETIME = "3";//费率切换时间
    public static final String TYPE_JT_NUM = "4";// 总阶梯数
    public static final String TYPE_JT_09_1 = "5";//09第一套阶梯
    public static final String TYPE_JT_09_2 = "6";//09第二套阶梯
    public static final String TYPE_JT_CHANGETIME = "7";//阶梯切换时间
    public static final String TYPE_SQ_NUM = "8";//时区数
    public static final String TYPE_SQ_1 = "9";//09第一套时区
    public static final String TYPE_SQ_2 = "10";//09第二套时区
    public static final String TYPE_SQ_CHANGETIME = "11";//时区切换时间
    public static final String TYPE_SD_NUM = "12";//时段数
    public static final String TYPE_SDB_NUM = "13";//日时段表数
    public static final String TYPE_SD_1 = "14";//09第一套时段
    public static final String TYPE_SD_2 = "15";//09第二套时段
    public static final String TYPE_SD_CHANGETIME = "16";//时段切换时间
    public static final String TYPE_FL_13 = "17";//13费率信息
    public static final String TYPE_SQ_13_1 = "18";//13第一套时区 不再用
    public static final String TYPE_SQ_13_2 = "19";//13第二套时区 不再用
    public static final String TYPE_SD_13_1 = "20";//13第一套时段 不再用
    public static final String TYPE_SD_13_2 = "21";//13第二套时段 不再用
    public static final String TYPE_JT_13 = "22";//13阶梯电价信息
    public static final String TYPE_JB = "23";//冀北电价信息
    //698操作类型
    public static final String TYPE_698_FL_NUM = "1";//费率数量
    public static final String TYPE_698_FL_DATA = "2";//费率信息
    public static final String TYPE_698_FL_CHANGETIME = "3";//费率切换时间
    public static final String TYPE_698_SQSD_NUM = "4";//时区时段数量设置
    public static final String TYPE_698_SQ_DATA = "5";//时区信息
    public static final String TYPE_698_SQ_CHANGETIME = "6";//时区切换时间
    public static final String TYPE_698_SD_DATA = "7";//时段信息
    public static final String TYPE_698_SD_CHANGETIME = "8";//时段切换时间
    public static final String TYPE_698_FL_READ = "9";//费率读取信息  废弃 不使用
    public static final String TYPE_698_JT_NUM = "10";//阶梯数量设置
    public static final String TYPE_698_JT_DATA = "11";//阶梯数据
    public static final String TYPE_698_JT_CHANGETIME = "12";//阶梯切换时间


    //当前运行时区 时段
    public static final String TIME_ZONE_1 = "0";  //第一套
    public static final String TIME_ZONE_2 = "1";  //第二套
    //当前运行费率
    public static final String FL_ZONE_1 = "0";  //第一套
    public static final String FL_ZONE_2 = "1";  //第二套
    //当前运行阶梯
    public static final String JT_ZONE_1 = "0";  //第一套
    public static final String JT_ZONE_2 = "1";  //第二套

}

