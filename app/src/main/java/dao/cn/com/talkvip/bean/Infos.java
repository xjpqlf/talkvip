package dao.cn.com.talkvip.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @name dao.cn.com.talkvip.bean
 * @class name：TalkVip
 * @class describe
 * @anthor uway QQ:343251588
 * @time 2017/5/10 10:08
 * @change uway
 * @chang 2017/5/10 10:08
 * @class describe
 */

public class Infos implements Serializable {
     private  List<Custom> msg;

    public Infos() {
    }

    public List<Custom> getMsg() {
        return msg;
    }

    public void setMsg(List<Custom> msg) {
        this.msg = msg;
    }

    public Infos(List<Custom> msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Infos{" +
                "msg=" + msg +
                '}';
    }




}
