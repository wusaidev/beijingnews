package com.happy.beijingnews.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：wusai
 * QQ:2713183194
 * 作用：xxx
 * Created by happy on 2017/6/1.
 */

public class NewsCenterPagerBean2 {
    public List<DataBean2> data;
    public List extend;
    public int retcode;

    public List<DataBean2> getData() {
        return data;
    }

    public void setData(List<DataBean2> data) {
        this.data = data;
    }

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public List getExtend() {
        return extend;
    }

    public void setExtend(List extend) {
        this.extend = extend;
    }

    public static class DataBean2{
        public List<ChildrenBean2> children;
        public int id;
        public String title;
        public int type;
        public String url;
        public String url1;
        public String dayurl;
        public String excurl;
        public String weekurl;

        public List<ChildrenBean2> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean2> children) {
            this.children = children;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl1() {
            return url1;
        }

        public void setUrl1(String url1) {
            this.url1 = url1;
        }

        public String getDayurl() {
            return dayurl;
        }

        public void setDayurl(String dayurl) {
            this.dayurl = dayurl;
        }

        public String getExcurl() {
            return excurl;
        }

        public void setExcurl(String excurl) {
            this.excurl = excurl;
        }

        public String getWeekurl() {
            return weekurl;
        }

        public void setWeekurl(String weekurl) {
            this.weekurl = weekurl;
        }
        public static class ChildrenBean2{
            public int id;
            public String title;
            public int type;
			public String url;
            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            
        }
    }
    /*public class ExtendBean2{
        @SerializedName("0")
        public int zero;
        @SerializedName("1")
        public int one;
        @SerializedName("2")
        public int two;
        @SerializedName("3")
        public int three;
        @SerializedName("4")
        public int fore;
        @SerializedName("5")
        public int five;
        @SerializedName("6")
        public int six;
        @SerializedName("7")
        public int seven;
        @SerializedName("8")
        public int eight;
        @SerializedName("9")
        public int nine;
        @SerializedName("10")
        public int ten;
        @SerializedName("11")
        public int eleven;
        public int getZero() {
            return zero;
        }

        public void setZero(int zero) {
            this.zero = zero;
        }

        public int getOne() {
            return one;
        }

        public void setOne(int one) {
            this.one = one;
        }

        public int getTwo() {
            return two;
        }

        public void setTwo(int two) {
            this.two = two;
        }

        public int getThree() {
            return three;
        }

        public void setThree(int three) {
            this.three = three;
        }

        public int getFore() {
            return fore;
        }

        public void setFore(int fore) {
            this.fore = fore;
        }

        public int getFive() {
            return five;
        }

        public void setFive(int five) {
            this.five = five;
        }

        public int getSix() {
            return six;
        }

        public void setSix(int six) {
            this.six = six;
        }

        public int getSeven() {
            return seven;
        }

        public void setSeven(int seven) {
            this.seven = seven;
        }

        public int getEight() {
            return eight;
        }

        public void setEight(int eight) {
            this.eight = eight;
        }

        public int getNine() {
            return nine;
        }

        public void setNine(int nine) {
            this.nine = nine;
        }

        public int getTen() {
            return ten;
        }

        public void setTen(int ten) {
            this.ten = ten;
        }

        public int getEleven() {
            return eleven;
        }

        public void setEleven(int eleven) {
            this.eleven = eleven;
        }


    }*/
}
