package dao.cn.com.talkvip.bean;

import java.io.Serializable;

/**
 * @name dao.cn.com.talkvip.bean
 * @class name：TalkVip
 * @class describe
 * @anthor uway QQ:343251588
 * @time 2017/4/7 16:40
 * @change uway
 * @chang 2017/4/7 16:40
 * @class describe
 */

public class Custom implements Serializable{

    /*"id": "242752", //数据id
              "name": "聂喊\r\n", //数据标签
              "mobile": "18*******01", //数据手机号（加密数据）
              "inputtime": "1488443744" //状态（成功）*/
    private String id;
    private String name;
    private String mobile;
    private String inputtime;
    private String sourceid;

    public Custom() {
    }

    public Custom(String id, String name, String mobile, String inputtime, String sourceid) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.inputtime = inputtime;
        this.sourceid = sourceid;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
    }

    public String getSourceid() {
        return sourceid;
    }

    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }

    @Override
    public String toString() {
        return "Custom{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", inputtime='" + inputtime + '\'' +
                ", sourceid='" + sourceid + '\'' +
                '}';
    }
}
