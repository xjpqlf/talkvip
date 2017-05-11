package dao.cn.com.talkvip.bean;

/**
 * @name dao.cn.com.talkvip.bean
 * @class name：TalkVip
 * @class describe
 * @anthor uway QQ:343251588
 * @time 2017/5/5 14:23
 * @change uway
 * @chang 2017/5/5 14:23
 * @class describe
 */

public class Remarks {

   /* {list =({duration = 0;//通话时长 null为直接备注 有值为电话产生备注
        inputtime = 1473675500;//添加时间或拨打时间
        ;note = "";//备注信息}）}*/

        private  String duration;
        private  String inputtime;
        private  String note;

    public Remarks() {
    }

    public Remarks(String duration, String inputtime, String note) {
        this.duration = duration;
        this.inputtime = inputtime;
        this.note = note;
    }

    @Override
    public String toString() {
        return "Remarks{" +
                "duration='" + duration + '\'' +
                ", onputtime='" + inputtime + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String onputtime) {
        this.inputtime = onputtime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
