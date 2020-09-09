package com.sgcc.pda.framelibrary.utils.check;

import java.util.List;

/**
 * 电价调整操作数据
 */
public class OptData {
    //  调时区
    /**
     * 时区数据集
     */

    private List<Date> dateList;

    /**
     * 时区切换时间
     */
    private String dateEfficient;

    //  调时段/调电价

    /**
     * 时段切换时间
     */
    private String timeEfficient;

    /**
     * 电价切换时间
     */
    private String priceEfficient;
    /**
     *  阶梯切换时间
     */
    private String ladderEfficient;
    public String getLadderEfficient() {
        return ladderEfficient;
    }

    public void setLadderEfficient(String ladderEfficient) {
        this.ladderEfficient = ladderEfficient;
    }



    /**
     * 时段类型
     */
    private List<Price> priceList;

    // 调阶梯

    /**
     *  阶梯数据数组
     */
    private List<String> theaterArray;

    /**
     *  阶梯电价数组（0.0000元）
     */
    private List<String> priceArray;

    /**
     * 结算日数组（MMDDhh）
     */
    private List<String> balanceDayArray;

    public List<Date> getDateList() {
        return dateList;
    }

    public void setDateList(List<Date> dateList) {
        this.dateList = dateList;
    }

    public String getDateEfficient() {
        return dateEfficient;
    }

    public void setDateEfficient(String dateEfficient) {
        this.dateEfficient = dateEfficient;
    }

    public String getTimeEfficient() {
        return timeEfficient;
    }

    public void setTimeEfficient(String timeEfficient) {
        this.timeEfficient = timeEfficient;
    }

    public String getPriceEfficient() {
        return priceEfficient;
    }

    public void setPriceEfficient(String priceEfficient) {
        this.priceEfficient = priceEfficient;
    }

    public List<Price> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<Price> priceList) {
        this.priceList = priceList;
    }

    public List<String> getTheaterArray() {
        return theaterArray;
    }

    public void setTheaterArray(List<String> theaterArray) {
        this.theaterArray = theaterArray;
    }

    public List<String> getPriceArray() {
        return priceArray;
    }

    public void setPriceArray(List<String> priceArray) {
        this.priceArray = priceArray;
    }

    public List<String> getBalanceDayArray() {
        return balanceDayArray;
    }

    public void setBalanceDayArray(List<String> balanceDayArray) {
        this.balanceDayArray = balanceDayArray;
    }

    public class Date {
        /**
         * 时区数据，（MMDD-MMDD,开始日期-结束日期）
         */
        private String dateValue;

        public String getDateValue() {
            return dateValue;
        }

        public void setDateValue(String dateValue) {
            this.dateValue = dateValue;
        }
    }

    public class Price {
        /**
         * 时段类型
         */
        private String timeType ;
        /**
         *  费率号
         */
        private String timeCode ;
        /**
         * 时段起始时间（hhmm）
         */
        private String timeBeg ;
        /**
         * 电价单价（0.0000元）
         */
        private String price ;

        public String getTimeType() {
            return timeType;
        }

        public String getTimeBeg() {
            return timeBeg;
        }

        public String getPrice() {
            return price;
        }

        public void setTimeType(String timeType) {
            this.timeType = timeType;
        }

        public void setTimeBeg(String timeBeg) {
            this.timeBeg = timeBeg;
        }

        public void setPrice(String price) {
            this.price = price;
        }
        public String getTimeCode() {
            return timeCode;
        }

        public void setTimeCode(String timeCode) {
            this.timeCode = timeCode;
        }
    }
}
