package dao.cn.com.talkvip.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dao.cn.com.talkvip.R;
import dao.cn.com.talkvip.bean.Remarks;
import dao.cn.com.talkvip.utils.DebugFlags;

/**
 * Created by uway on 2016/9/10.
 */
public class RemarksAdapter extends BaseAdapter {

    private List<Remarks> list;
    private Context context;

    public RemarksAdapter(Context context, List<Remarks> list) {
        this.context = context;
        this.list = list;
    }
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.detail_item, null);

            holder.years = (TextView) convertView
                    .findViewById(R.id.tv_nian);
            holder.hour= (TextView) convertView
                    .findViewById(R.id.tv_sj);
            holder.type = (ImageView) convertView
                    .findViewById(R.id.iv_bodabeiju);
    holder.mark= (TextView) convertView.findViewById(R.id.tv_bj);
    holder.callTime= (TextView) convertView.findViewById(R.id.tv_calltime);
    holder.rlCall= (ImageView) convertView.findViewById(R.id.iv_time);
    holder.lineTop= (View) convertView.findViewById(R.id.line_tops);
    holder.lineUnder= (View) convertView.findViewById(R.id.line_unders);


            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }


      Remarks remark=list.get(position);

        holder.mark.setText(remark.getNote());
        String time=remark.getInputtime();
     String s=   getDateFromSeconds(time);
        DebugFlags.logD("时间戳"+s+"=="+time);
        String year=s.substring(0,11);
        String h=s.substring(11,s.length());
       holder.years.setText(year);
        holder.hour.setText(h);
      //  holder.callTime.setText(remark.getCallTime());
        DebugFlags.logD("通话时间"+remark.getDuration());


       if (remark.getDuration()!=null){

           long t = Long.parseLong(remark.getDuration());
           long minute=t%3600/60;
           long second=t%60;

           if (minute<10&&second<10) {
               holder.callTime.setText("0"+minute+":"+"0"+second);
           }else if (minute>=10&&second>=10){

               holder.callTime.setText(minute+":"+second);
           }else if (minute>10&&second<10){

               holder.callTime.setText(minute+":"+"0"+second);
           }else if (minute<10&&second>10){

               holder.callTime.setText("0"+minute+":"+second);
           }


           holder.type.setImageResource(R.mipmap.boda_beizhu);
           holder.rlCall.setVisibility(View.VISIBLE);

       }else
       {
           holder.callTime.setText("");
           holder.type.setImageResource(R.mipmap.jinbeizhu);
            holder.rlCall.setVisibility(View.GONE);
       }

        holder.lineTop.setBackgroundResource(R.color.grey_400);
        holder.lineUnder.setBackgroundResource(R.color.grey_400);

           if (position==0){

               holder.lineTop.setBackgroundResource(R.color.white);

          }


                    if (position==(list.size()-1)){
         holder.lineUnder.setBackgroundResource(R.color.white);

            }


        return convertView;
    }
    class ViewHolder {
        TextView years;
        TextView  hour;
        ImageView type;
        TextView  mark;
        TextView callTime;
     ImageView rlCall;
        View lineTop;
        View lineUnder;

    }

    public static String getDateFromSeconds(String seconds){
        if(seconds==null)
            return " ";
        else{
            Date date=new Date();
            try{
                date.setTime(Long.parseLong(seconds)*1000);
            }catch(NumberFormatException nfe){

            }
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(date);
        }
    }

}
