package dao.cn.com.talkvip.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import dao.cn.com.talkvip.R;
import dao.cn.com.talkvip.bean.Remark;

/**
 * Created by uway on 2016/9/10.
 */
public class RemarkAdapter extends BaseAdapter {

    private List<Remark> list;
    private Context context;

    public RemarkAdapter(Context context, List<Remark> list) {
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


      Remark remark=list.get(position);

        holder.mark.setText(remark.getMarkText());
        holder.years.setText(remark.getYear());
        holder.hour.setText(remark.getHour());
      //  holder.callTime.setText(remark.getCallTime());
       if ("call".equals(remark.getType())){
           holder.callTime.setText(remark.getCallTime());
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



}
