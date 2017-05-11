package dao.cn.com.talkvip.bean;

import java.util.List;

/**
 * @name dao.cn.com.talkvip.bean
 * @class name：TalkVip
 * @class describe
 * @anthor uway QQ:343251588
 * @time 2017/5/8 15:51
 * @change uway
 * @chang 2017/5/8 15:51
 * @class describe
 */
/*{"result":"success","data":{"url":"\/public\/logo\/StartPage.jpg"},"code":"8888","msg":"\u6210\u529f\uff01"}*/
public class Display {

    private  String success;
    private  String code;
    private  String msg;
    private List<Aurl> data;

    public Display() {
    }

    public Display(String success, String code, String msg, List<Aurl> data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Aurl> getData() {
        return data;
    }

    public void setData(List<Aurl> data) {
        this.data = data;
    }
}
