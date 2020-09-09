package com.sgcc.pda.framelibrary.protocol698.apdu.data;


import android.support.annotation.IntRange;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.sgcc.pda.framelibrary.protocol698.Frame698;
import com.sgcc.pda.framelibrary.protocol698.Frame698Separator;
import com.sgcc.pda.framelibrary.protocol698.apdu.GetAPDUParser;
import com.sgcc.pda.framelibrary.utils.NumberConvert;
import com.sgcc.pda.framelibrary.utils.RecoverableString;
import com.sgcc.pda.framelibrary.utils.TextUtils;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by qinling on 2018/5/11 18:22
 * Description:
 */
public class ScalerUnit extends Data implements Serializable {
    private static final long serialVersionUID = 4470047718751627220L;

    public static class A {
        public A(int b, int c) {
            this.b = b;
            this.c = c;
        }

        int b = 2;

        int c = 2;
    }

    public static class B extends A {
        int a;

        public B(int a, int b, int c) {
            super(b, c);
            this.a = a;
        }

        @Override
        public String toString() {
            return "B{" +
                    "b=" + b +
                    ", c=" + c +
                    ", a=" + a +
                    '}';
        }
    }

    private static void formatTimeZoneOrTimeProid(String apdu, List<String> startTimeList, List<Integer> rateNumList) {
        int dayTimeTableNum = NumberConvert.parseUnsignedInt(apdu.substring(18, 20), 16);
        String dayTimeStr = apdu.substring(20);

        for (int i = 0; i < dayTimeTableNum; i++) {
            String month = NumberConvert.toHexStr(
                    NumberConvert.parseUnsignedInt(dayTimeStr.substring(6, 8), 16), 2);
            String day = NumberConvert.toHexStr(
                    NumberConvert.parseUnsignedInt(dayTimeStr.substring(10, 12), 16), 2);
            int rateNum = NumberConvert.parseUnsignedInt(dayTimeStr.substring(14, 16), 16);
            String startTime = month + day;
            startTimeList.add(startTime);
            rateNumList.add(rateNum);
            dayTimeStr = dayTimeStr.substring(16);

        }
    }
    public static boolean isNumber(String str) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        java.util.regex.Matcher match = pattern.matcher(str);
        return match.matches();
    }
        public static void main(String[] args) {

       // System.out.println(new Frame698.Builder().setLinkDataStr("0501000010020000").setServerAddress("000000000001",0).toHexString());
       // System.out.println(String.format(Locale.CHINA, "C9%02x%02x%sC9%s%s9C", 1, 2,"0200" ,"1234", "ff" ));
            GetAPDUParser parser = new GetAPDUParser("85030350040200020020210200000030020001141c07e30b1e000000001c07e30c01000000001c07e30c02000000001c07e30c03000000001c07e30c04000000001c07e30c05000000001c07e30c06000000001c07e30c07000000001c07e30c08000000001c07e30c09000000001c07e30c0a000000001c07e30c0b000000001c07e30c0c000000001c07e30c0d000000001c07e30c0e000000001c07e30c0f000000001c07e30c10000000001c07e30c11000000001c07e30c12000000001c07e30c13000000000000c0e6");
         //   GetAPDUParser parser = new GetAPDUParser("8503035005020001001010020001000000010004b7f9c9d6b766");
          //  GetAPDUParser parser = new GetAPDUParser("85030350040200020020210200001010020001011c07e40410000000000000");
          //  GetAPDUParser parser = new GetAPDUParser("850303500502000100101002000101000000");
          //  parser.toFormatString();
            System.out.println( parser.toFormatString());
       //     System.out.println( new DateTimeS("00000000000000").format());
          //  System.out.println("ABC".equalsIgnoreCase("abc"));

         //System.out.println(formatTimeNum("0501","",""));
          //  String s ;
       /*  System.out.println(isNumber("0.233"));
         System.out.println(isNumber("233"));
         System.out.println(isNumber("233.1"));
         System.out.println(isNumber("0.23"));
         System.out.println(isNumber("00.23"));
        // System.out.println(formatTimeNum("0501","","0525"));

       *//* System.out.println(formatNum(1));
        System.out.println(formatNum(12.2));
        System.out.println(formatNum(22.2222));
        System.out.println(formatNum(2.222222));*//*
      *//*  System.out.println(formatNum("1"));
        System.out.println(formatNum("12.2"));
        System.out.println(formatNum("22.2222"));
        System.out.println(formatNum("2.222222"));*//*
        String timeZone = "0201";

        System.out.println(formatTimeNum("200",TimeType.TIME_PROID));
        System.out.println(formatTimeNum("2000",TimeType.TIME_PROID));
        System.out.println(formatTimeNum("0055",TimeType.TIME_PROID));
        System.out.println(formatTimeNum("201",TimeType.TIME_ZONE));
        System.out.println(formatTimeNum("2000",TimeType.TIME_ZONE));
        System.out.println(formatTimeNum("0105",TimeType.TIME_ZONE));
        ReturnParseDataBean bean = new ReturnParseDataBean(
                Arrays.asList(
                        new ReturnParseDataBean.TimeAndNum("1201", "04"),
                        new ReturnParseDataBean.TimeAndNum("0901", "03"),
                        new ReturnParseDataBean.TimeAndNum("0601", "02"),
                        new ReturnParseDataBean.TimeAndNum("0301", "01")
                ));

        System.out.println(Arrays.toString(new List[]{bean.startAndEndLsit}));*/

   /*     List<String> startTimeList = new ArrayList<>();
        List<Integer> rateNumList = new ArrayList<>();
        String apdu = "8501014015020001010402031108110111010203110811011101020311081101110102031108110111010000";
        formatTimeZoneOrTimeProid(apdu,startTimeList,rateNumList);

        for (int i = 0; i < startTimeList.size(); i++) {
            String startEnd = startTimeList.get(i) + "-" + startTimeList.get((i + 1) % startTimeList.size());

            System.out.println(startEnd+": "+ rateNumList.get(i));

        }*/
        // System.out.println(new SimpleDateFormat("MMdd").format(new Date()));
        //   testReadPrice();


     /*   formatRate(apdu,null);



        BigInteger integer = new BigInteger("10");
        BigInteger intege = new BigInteger("7");

        //  System.out.println(new ScalerUnit(255,33).getUnit().toString());
        //  String frameStr = "fefefefe68c600e3050100000000000083ae0000900082010b85030350040200020020210200000010020001071c07e302050000000105060000000606000000000600000000060000000006000000061c07e302060000000105060000000606000000000600000000060000000006000000061c07e302070000000105060000000606000000000600000000060000000006000000061c07e302080000000105060000000606000000000600000000060000000006000000061c07e302090000000105060000000606d3f316";
        String frameStr1 = "fefefefe68c600e3050100000000000083ae0000900082010b85030350040200020020210200000020020001071c07e3020c0000000105060000000006000000000600000000060000000006000000001c07e3020d0000000105060000000006000000000600000000060000000006000000001c07e3020e0000000105060000000006000000000600000000060000000006000000001c07e3020f0000000105060000000006000000000600000000060000000006000000001c07e302100000000105060000000006bef016";
        String frameStr2 = "fefefefe687300e3050100000000000093a40140000000000600000000060000000006000000001c07e302110000000105060000000006000000000600000000060000000006000000001c07e302120000000105060000000006000000000600000000060000000006000000000000010004c1238b0df9fd16";
*/
        // resver();


    }

    private static String formatNum(double num) {
        return String.format("%.4f", num);
    }

    /**
     * 将传入字符串保留四位小数
     *
     * @param numStr
     * @return
     */
    private static String formatNum(String numStr) {
        try {
            return String.format("%.4f", Double.parseDouble(numStr));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String formatTimeZoneNum(String numStr) {
        if (TextUtils.isEmpty(numStr) || numStr.length() < 3 || numStr.length() > 4) {
            return "";
        }
        try {
            return String.format("%04d", Integer.parseInt(numStr));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "";
        }
    }

    /*    private static String formatTimeProidNum(String numStr) {
           String format = formatTimeNum(numStr);
           if (TextUtils.isEmpty(format)){
               return "";
           }
            String endNum = format.substring(2,4);
        }*/
    public enum TimeType {
        /**
         *  时区 1月到12月  1-31天
         */
        TIME_ZONE(1,12,1, 31),
        TIME_BALANCE(1, 31, 0, 24),
        /**
         * 时段 0-24时，0-59秒
         */
        TIME_PROID(0,24,0, 59),;
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
        if (TextUtils.isEmpty(numStr) || numStr.length() < 3 || numStr.length() > 4) {
            return "";
        }
        try {
            String format = String.format("%04d", Integer.parseInt(numStr));
            int firstMum = Integer.parseInt(format.substring(0, 2));
            int secondMum = Integer.parseInt(format.substring(2, 4));
            return (firstMum >= timeType.start_first && firstMum <= timeType.end_first)
                    &&(secondMum >= timeType.start_second && secondMum <= timeType.end_second)
                    ? format : "";
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "";
        }
    }
    private static String formatTimeNum(String... blanceDays) {
        // 校验结算日1，2，3

        // 默认3个结算日数据都存在问题
        boolean isEmpty = true;
        for (String blanceDay : blanceDays) {
            // 若是结算日格式不对，会返回为空字符串
            blanceDay = formatTimeNum(blanceDay, TimeType.TIME_BALANCE);
            System.out.println("blanceDay is ok ： "+ !TextUtils.isEmpty(blanceDay));
            // 若是存在一个合格的值，便会变成false
            isEmpty = isEmpty && (TextUtils.isEmpty(blanceDay));
        }
        // 若为空,则说明不存在一个正常格式的结算日数据
        return isEmpty? "参数不正确": "ok";
    }

    private static void testReadPrice() {
    /*    String timeZoneapdu = "850100401402000101040203110c11011101020311031101110202031106110111030203110911011104000";
        String timeProidapdu = "8501004016020001010801080203110811001101020311141100110202031114110011020203111411001102020311141100110202031114110011020203111411001102020311141100110201080203110911001103020311151100110402031115110011040203111511001104020311151100110402031115110011040203111511001104020311151100110401080203110c110111030203110c110111030203110c110111030203110c110111030203110c110111030203110c110111030203110c110111030203110c11011103010802031100110111010203110011011101020311001101110102031100110111010203110011011101020311001101110102031100110111010203110011011101010802031106110111040203110611011104020311061101110402031106110111040203110611011104020311061101110402031106110111040203110611011104010802031112110111030203111211011103020311121101110302031112110111030203111211011103020311121101110302031112110111030203111211011103010802031100110011010203110311001102020311061100110302031109110011040203110c110011010203110f1100110202031112110011030203111511001104010802031100110011010203110311001102020311061100110302031109110011040203110c110011010203110f11001102020311121100110302031115110011040000";
        String rateApdu = "8501014018020001010406000027100600004e2006000075300600009c400000";
        readPrice(rateApdu,"2");
        readPrice(timeZoneapdu,"5");
        readPrice(timeProidapdu,"7");

        System.out.println(NumberConvert.parseUnsignedInt("10",16));

        SuccessResult.AdjustPrice adjustPrice =  new SuccessResult.AdjustPrice();
        adjustPrice.setDateList(dateList);
        adjustPrice.setPriceList(priceList);
        System.out.println(adjustPrice.toJson());*/
    }

    public static class ReturnParseDataBean {
        private static final String SPLITE = "-";
        /* //日期或时间集合
         private List<String> dateOrTimeList;
         //日时段表号或费率号集合
         private List<String> numList;*/
        private List<TimeAndNum> timeAndNums;


        private List<String> startAndEndLsit;
        //数据标识
        private String itemCode;

        public List<TimeAndNum> getTimeAndNums() {
            return timeAndNums;
        }

        /*    public ReturnParseDataBean(List<String> dateOrTimeList, List<String> numList, String itemCode) {
                this.dateOrTimeList = dateOrTimeList;
                this.numList = numList;
                this.itemCode = itemCode;
                this.startAndEndLsit = initStartAndEnd() ;
            }*/
        public ReturnParseDataBean(List<TimeAndNum> timeAndNums, String itemCode) {
            this.timeAndNums = timeAndNums;
            this.itemCode = itemCode;
            this.startAndEndLsit = initStartAndEnd();
        }

        public ReturnParseDataBean(List<TimeAndNum> timeAndNums) {
            this.timeAndNums = timeAndNums;
            this.startAndEndLsit = initStartAndEnd();
        }
 /*   public List<String> getDateOrTimeList() {
        return dateOrTimeList;
    }*/

        private List<String> initStartAndEnd() {
            List<TimeAndNum> timeAndNumList = timeAndNums;
            Collections.reverse(timeAndNumList);
            List<String> dateList = new ArrayList<>();
            for (int i = 0; i < timeAndNumList.size(); i++) {
                String timeStart = timeAndNumList.get(i).startTime;
                String timeEnd = i < timeAndNumList.size() - 1 ? timeAndNumList.get(i + 1).startTime : timeAndNumList.get(0).startTime;

                dateList.add(timeStart + SPLITE + timeEnd);
            }
            return dateList;
        }

        public String getCurrentDayTimeNo() {
            int currentTime = Integer.parseInt(new SimpleDateFormat("MMdd").format(new Date()));
            for (int i = 0; i < getStartAndEndList().size(); i++) {
                String info = getStartAndEndList().get(i);
                String[] startEnd = info.split(SPLITE);
                int start = Integer.parseInt(startEnd[0]);
                int end = Integer.parseInt(startEnd[1]);
                if (currentTime >= start && currentTime <= end) {
                    return timeAndNums.get(i).num;
                }
            }
            return "";
        }

        /**
         * 返回 MMDD(起始) - MMDD（结束） 格式
         *
         * @return
         */
        public List<String> getStartAndEndList() {
            return startAndEndLsit;
        }


        public String getItemCode() {
            return itemCode;
        }

        public void setItemCode(String itemCode) {
            this.itemCode = itemCode;
        }

        public static final class TimeAndNum implements Comparable<TimeAndNum> {
            String startTime;
            String num;
            private int numInteger;

            public TimeAndNum(String startTime, String num) {
                this.startTime = startTime;
                this.num = num;
                this.numInteger = Integer.parseInt(num);
            }

            public TimeAndNum(String startTime, int num) {
                this.startTime = startTime;
                this.num = num + "";
                this.numInteger = num;
            }

            public int getNumInteger() {
                return numInteger;
            }

            public void setNum(String num) {
                this.num = num;
            }

            @Override
            public int compareTo(@NotNull TimeAndNum timeAndNum) {
                try {
                    // return Integer.parseUnsignedInt(this.startTime) - Integer.parseUnsignedInt(timeAndNum.startTime);
                    return 0;
                } catch (Throwable e) {
                    return 0;
                }

            }
        }
    }

    public static List<SuccessResult.Price> priceList = new ArrayList<>();
    public static List<String> dateList = new ArrayList<>();
    public static List<String> rateList = new ArrayList<>();
    private static int currentDayTimeNo = 1;// 默认第一张日时段表


    private static void readPrice(String apdu, String dataType) {

        String safeType = "03"; //获取安全等级


        // 读取一条信息
        if ("8501".equals(apdu.substring(0, 4)) && apdu.length() > 16) {
            String choice = apdu.substring(14, 16);
            // 只有01时才算有数据返回
            if ("01".equals(choice)) {
                // 8501034016020001   8字节  0-16
                // 0102                        16-20
                // 0203       20-24
                // 1100       24-28
                // 1100       28-32
                // 1100       32-36

                if ("5".equals(dataType)) {//读取时区信息
                    // List<String> startTimeList = new ArrayList<>();
                    //  List<Integer> rateNumList = new ArrayList<>();

                    apdu = apdu.substring(16);
                    ReturnParseDataBean returnParseDataBean = formatTimeZoneOrTimeProid(new RecoverableString(apdu));
                    //  把起始时间转成  起始-截止的格式
                    List<ReturnParseDataBean.TimeAndNum> timeAndNums = returnParseDataBean.getTimeAndNums();
                    dateList = returnParseDataBean.getStartAndEndList();

                    try {
                        //    currentDayTimeNo = Integer.parseInt(returnParseDataBean.getCurrentDayTimeNo());
                        currentDayTimeNo = 8;
                    } catch (NumberFormatException e) {
                        //  LogUtil.e(e.getMessage());
                    }
                } else if ("2".equals(dataType)) {//读取电价信息
                    // 8501034018020001   8字节  0-16
                    // 0102                        16-20
                    // 05 12345678       20-30
                    // 05 12345678       30-40


                    int rateNum = NumberConvert.parseInt(apdu.substring(18, 20), 16, 0);
                    String dayTimeStr = apdu.substring(20);
                    // 0102 05 12345678 05 12345678
                    for (int i = 1; i <= rateNum; i++) {
                        long rate = NumberConvert.parseInt(dayTimeStr.substring(2, 10), 16, 0);
                        String rateStr = getFormatValueWithUnit(rate, "元/kWh", -4);
                        rateList.add(rateStr);
                        // System.out.println(priceList.size());
                        /*    for (SuccessResult.Price price : priceList) {
                                if (price.rateNo == i) {
                                    price.setRate(rateStr);
                                }
                            }*/
                        dayTimeStr = dayTimeStr.substring(10);
                    }


                } else if ("7".equals(dataType)) { // 读时段信息

                    apdu = apdu.substring(16);
                    ArrayList<ReturnParseDataBean> returnParseDataBeans = new ArrayList<>();

                    int dayTimeTableNum = NumberConvert.parseUnsignedInt(apdu.substring(2, 4), 16);
                    RecoverableString dayTimeStr = new RecoverableString(apdu.substring(4));
                    for (int i = 0; i < dayTimeTableNum; i++) {
                        returnParseDataBeans.add(formatTimeZoneOrTimeProid(dayTimeStr));
                    }
                    // 上面的排序就是为了尽最大的可能，先获取正确的当前日时段表号，否则按照 第一张日时段表来获取
                    ReturnParseDataBean returnParseDataBean = returnParseDataBeans.get(currentDayTimeNo - 1);

                    // LogUtil.e(new Gson().toJson(returnParseDataBeans));

                    List<ReturnParseDataBean.TimeAndNum> timeAndNums = returnParseDataBean.getTimeAndNums();
                    for (int i = 0; i < timeAndNums.size(); i++) {
                        String startEnd = timeAndNums.get(i).startTime + "-" + timeAndNums.get((i + 1) % timeAndNums.size()).startTime;
                        // 存放时间与费率号
                        priceList.add(new SuccessResult.Price(startEnd, timeAndNums.get(i).getNumInteger()));
                    }
                    if (null != rateList && rateList.size() > 0) {

                        for (SuccessResult.Price price : priceList) {
                            String rate = "";
                            try {
                                rate = rateList.get(price.rateNo - 1);
                            } catch (Throwable e) {
                                // LogUtil.e(e.getMessage());
                            }
                            price.setRate(rate);

                            //  if (price.rateNo == i) {

                            //  }
                        }
                    }

                }
            }

        /*    if (TextUtils.equals("7", dataType)) {//读取时段信息
                decodeDayData698(returnResult.getDatas().get(0));
            } else if (TextUtils.equals("2", dataType)) {//读取电价信息
                decodeData698(returnResult.getDatas().get(0));
            } else if (TextUtils.equals("5", dataType)) { // 读时区信息

            }*/
        }
    }

    public static final class SuccessResult {
        /**
         * 执行结果
         */
        private final int ret = 1;
        private AdjustPrice adjustBefore;
        private AdjustPrice adjustAfter;

        public SuccessResult(AdjustPrice adjustBefore, AdjustPrice adjustAfter) {
            this.adjustBefore = adjustBefore;
            this.adjustAfter = adjustAfter;
        }

        public SuccessResult(AdjustPrice adjustAfter) {
            this.adjustBefore = null;
            this.adjustAfter = adjustAfter;
        }

        public void setAdjustBefore(AdjustPrice adjustBefore) {
            this.adjustBefore = adjustBefore;
        }

        public void setAdjustAfter(AdjustPrice adjustAfter) {
            this.adjustAfter = adjustAfter;
        }

        public String toJson() {
            return new Gson().toJson(this);
        }

        public static final class AdjustPrice {
            public List<String> getDateList() {
                return dateList;
            }

            public List<Price> getPriceList() {
                return priceList;
            }

            public Theater getTheater() {
                return theater;
            }

            private List<String> dateList;
            private List<Price> priceList;
            private Theater theater;

            public void setCurrentPrice(String currentPrice) {
                this.currentPrice = currentPrice;
            }

            public void setCurrentRatePrice(String currentRatePrice) {
                this.currentRatePrice = currentRatePrice;
            }

            public void setCurrentLadderPrice(String currentLadderPrice) {
                this.currentLadderPrice = currentLadderPrice;
            }

            public String toJson() {
                return new Gson().toJson(this);
            }

            public void setCurrentPrice(String currentPrice, String currentRatePrice, String currentLadderPrice) {
                this.currentPrice = currentPrice;
                this.currentRatePrice = currentRatePrice;
                this.currentLadderPrice = currentLadderPrice;
            }

            private String currentPrice;
            private String currentRatePrice;
            private String currentLadderPrice;

            public AdjustPrice(List<String> dateList, List<Price> priceList, Theater theater) {
                List<String> dates = new ArrayList<>();
                List<Price> prices = new ArrayList<>();
                if (null != dateList) {
                    dates.clear();
                    dates.addAll(dateList);
                }
                if (null != priceList) {
                    prices.clear();
                    prices.addAll(priceList);
                }
                this.dateList = dates;
                this.priceList = prices;
                this.theater = null == theater ? new Theater(null, null, null) : theater;
            }

            public AdjustPrice() {
            }

            public void setDateList(List<String> dateList) {
                this.dateList = dateList;
            }

            public void setPriceList(List<Price> priceList) {
                this.priceList = priceList;
            }

            public void setTheater(Theater theater) {
                this.theater = theater;
            }
        }

        /*  public static final class Date {
              private String startTime;
              private String dayTimeNo;

          }*/
        public static final class Price {
            private String time;
            private String rate;
            private int rateNo;

            public Price(String time, String rate, int rateNo) {
                this.time = time;
                this.rate = rate;
                this.rateNo = rateNo;
            }

            public Price(String time, String rate) {
                this(time, rate, -1);
            }

            public Price(String time, int rateNo) {
                this(time, "", rateNo);
            }

            public void setRate(String rate) {
                this.rate = rate;
            }
        }

        public static final class Theater {
            private List<String> theaterArray;
            private List<String> priceArray;
            private List<String> balanceDayArray;

            public Theater(List<String> theaterArray, List<String> priceArray, List<String> balanceDayArray) {
                this.theaterArray = null == theaterArray ? new ArrayList<String>() : theaterArray;
                this.priceArray = null == priceArray ? new ArrayList<String>() : priceArray;
                this.balanceDayArray = null == balanceDayArray ? new ArrayList<String>() : balanceDayArray;
            }

            public void setTheaterArray(List<String> theaterArray) {
                this.theaterArray = theaterArray;
            }

            public void setPriceArray(List<String> priceArray) {
                this.priceArray = priceArray;
            }

            public void setBalanceDayArray(List<String> balanceDayArray) {
                this.balanceDayArray = balanceDayArray;
            }
        }
    }

    private static ReturnParseDataBean formatTimeZoneOrTimeProid(RecoverableString apdu) {
        ArrayList<ReturnParseDataBean.TimeAndNum> timeAndNums = new ArrayList<>();
        int dayTimeTableNum = NumberConvert.parseUnsignedInt(apdu.substring(2, 4), 16);
        // String dayTimeStr = apdu.substring(4);
        //   85010040150201010203 1108110111010000
        for (int i = 0; i < dayTimeTableNum; i++) {
            String monthStr = apdu.substring(6, 8);
            String dayStr = apdu.substring(2, 4);
            //  System.out.println(monthStr+": "+NumberConvert.parseInt(monthStr, 16,16));
            //  System.out.println(monthStr+": "+NumberConvert.parseUnsignedInt(monthStr, 16));
            String month = String.format(Locale.CHINA, "%02d", NumberConvert.parseInt(monthStr, 16, 16));
            String day = String.format(Locale.CHINA, "%02d", NumberConvert.parseInt(dayStr, 16, 16));

            int rateNum = NumberConvert.parseUnsignedInt(apdu.substring(2, 4), 16);
            String startTime = month + day;
            timeAndNums.add(new ReturnParseDataBean.TimeAndNum(startTime, rateNum));
            // dayTimeStr = apdu.substring(16);
        }
        return new ReturnParseDataBean(timeAndNums);
    }

    private static void formatRate(String apdu, ArrayList<ReturnParseDataBean.TimeAndNum> timeAndNums) {
        int rateNum = NumberConvert.parseInt(apdu.substring(18, 20), 16, 0);
        String dayTimeStr = apdu.substring(20);
        // 0102 05 12345678 05 12345678
        for (int i = 0; i < rateNum; i++) {

            long rate = NumberConvert.parseInt(dayTimeStr.substring(2, 10), 16, 16);
            String rateStr = getFormatValueWithUnit(rate, "元/kWh", -4);
            System.out.println(rateStr);
            dayTimeStr = dayTimeStr.substring(10);
          /*  for (ReturnParseDataBean.TimeAndNum timeAndNum : timeAndNums) {
                if (timeAndNum.getNumInteger() == i) {
                    timeAndNum.setNum(rateStr);
                }
            }*/

        }
    }

    public static String getFormatValueWithUnit(long value, String unit, int pow) {
        double num = (value * (Math.pow(10, pow)));
        StringBuilder foramt = new StringBuilder("#0.0");
        for (int i = 1; i < Math.abs(pow); i++) {
            foramt.append(0);
        }
        foramt.append(unit);
        DecimalFormat decimalFormat = new DecimalFormat(foramt.toString());
        return decimalFormat.format(num);
    }

    public static String getFormatValueWithUnit(double value, int pow) {
        double num = (value * (Math.pow(10, pow)));
        StringBuilder foramt = new StringBuilder("#0.0");
        for (int i = 1; i < Math.abs(pow); i++) {
            foramt.append(0);
        }
        DecimalFormat decimalFormat = new DecimalFormat(foramt.toString());
        return decimalFormat.format(num);
    }

    /* private static String getCurrentDayTimeNo(ReturnParseDataBean returnParseDataBean) {
        if (null != returnParseDataBean) {
            String item = returnParseDataBean.getItemCode();
            List<String> dateOrTimeList = returnParseDataBean.getDateOrTimeList();
            List<String> numList = returnParseDataBean.getNumList();
            String dateOrTime;
            String num;
            int currentInt = 0;
            int count = 0;
            try {
                if (null != dateOrTimeList && dateOrTimeList.size() > 0) {
                    switch (item) {
                        case "04010000":
                        case "04020000":
                            String currentDate = new SimpleDateFormat("MMdd").format(new Date());//得到当前日期MMdd
                            if (!TextUtils.isEmpty(currentDate)) {
                                currentInt = Integer.parseInt(currentDate);

                            }
                            for (int i = 0; i < dateOrTimeList.size(); i++) {
                                String date = dateOrTimeList.get(i);
                                System.out.println(date+"：   "+currentDate);
                                int dateOrTiemInt = Integer.parseInt(date);
                                if (currentInt >= dateOrTiemInt) {
                                    dateOrTime = date;
                                    count = i;
                                } else {
                                    break;
                                }
                            }
                            if (null != numList && numList.size() > count) {
                                num = numList.get(count);//得到日时段表号
                                return num;
                            }
                            break;


                    }

                }

            } catch (Exception e) {
                return "";
            }
        }
        return "";
    }*/
    public static abstract class JSZPCallcak<T> {
        Type mType = getSuperclassTypeParameter(getClass());


        static Type getSuperclassTypeParameter(Class<?> subclass) {
            //通过反射得到泛型参数
            //Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            //ParameterizedType参数化类型，即泛型
            ParameterizedType parameterized = (ParameterizedType) superclass;
            //getActualTypeArguments获取参数化类型的数组，泛型可能有多个
            //将Java 中的Type实现,转化为自己内部的数据实现,得到gson解析需要的泛型
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public abstract void onError(Throwable e);

        public abstract void onSuccess(T response, String json);

    }


    public int getPow() {
        return pow;
    }

    public void setPow(int pow) {
        this.pow = pow;
    }

    public Unit getUnit() {
        return unit;
    }

    public String getUnitStr() {
        return unit.getUnit();
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    private int pow;
    private Unit unit;

    private String hexStr;

    public ScalerUnit(String hexStr) {
        byte[] bytes = hexStringToBytes(hexStr);
        if (null == bytes || bytes.length < 2) {
            throw new IndexOutOfBoundsException("16进制字符串格式有误");
        }
        this.hexStr = hexStr;
        this.pow = Byte.toUnsignedInt(bytes[0]);
        this.unit = Unit.getInstance().getUnit(Byte.toUnsignedInt(bytes[1]));
    }

    public ScalerUnit(@IntRange(from = 0, to = 255) int pow, Unit unit) {
        this.pow = pow;
        this.unit = unit;
        this.hexStr = NumberConvert.toHexStr(Math.abs(pow), 2) + Integer.toHexString(unit.getCode());
        //  System.out.println(hexStr);
    }

    public ScalerUnit(@IntRange(from = 0, to = 255) int pow, @IntRange(from = 0, to = 255) int unitCode) {
        this(pow, Unit.getInstance().getUnit(unitCode));
    }

    @Override
    public int dataType() {
        return 89;
    }

    @Override
    public String toHexString() {
        return hexStr;
    }

    @Override
    public String format() {
        return "";
    }


}
