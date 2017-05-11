package dao.cn.com.talkvip.bean;

import java.util.List;

/**
 * @name dao.cn.com.talkvip
 * @class name：TalkVIp
 * @class describe
 * @anthor uway QQ:343251588
 * @time 2017/3/28 10:54
 * @change uway
 * @chang 2017/3/28 10:54
 * @class describe
 */

public class Data {

private List<Custom> list;

  private   String total;
   private String totalpage;
  private   String page;

    public Data(List<Custom> list, String total, String totalpage, String page) {
        this.list = list;
        this.total = total;
        this.totalpage = totalpage;
        this.page = page;
    }

    public Data() {
    }

    public List<Custom> getList() {
        return list;
    }

    public void setList(List<Custom> list) {
        this.list = list;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(String totalpage) {
        this.totalpage = totalpage;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
