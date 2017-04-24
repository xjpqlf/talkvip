package dao.cn.com.talkvip.bean;

/**
 * @name dao.cn.com.talkvip.bean
 * @class name：TalkVip
 * @class describe
 * @anthor uway QQ:343251588
 * @time 2017/4/21 10:45
 * @change uway
 * @chang 2017/4/21 10:45
 * @class describe
 */

public class Remark {
    private String year;
    private String hour;
    private String markText;
    private String callTime;
    private String type;

    public Remark() {
    }

    public Remark(String year, String hour, String markText, String callTime, String type) {
        this.year = year;
        this.hour = hour;
        this.markText = markText;
        this.callTime = callTime;
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMarkText() {
        return markText;
    }

    public void setMarkText(String markText) {
        this.markText = markText;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Remark{" +
                "year='" + year + '\'' +
                ", hour='" + hour + '\'' +
                ", markText='" + markText + '\'' +
                ", callTime='" + callTime + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
