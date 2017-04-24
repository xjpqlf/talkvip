package dao.cn.com.talkvip.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import dao.cn.com.talkvip.R;
import dao.cn.com.talkvip.bean.Custom;
import dao.cn.com.talkvip.bean.CustomFrist;
import dao.cn.com.talkvip.view.activity.Detail;

/**
 * Created by uway on 2016/9/10.
 */
public class InfoAdapter extends BaseAdapter {

    private List<CustomFrist> list;
    private Context context;

    public InfoAdapter(Context context, List<CustomFrist> list) {
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
                    R.layout.item_custom, null);

            holder.name = (TextView) convertView
                    .findViewById(R.id.tv_name);
            holder.mobile= (TextView) convertView
                    .findViewById(R.id.tv_mobile);
            holder.dc = (TextView) convertView
                    .findViewById(R.id.tv_dc);
    holder.iv= (ImageView) convertView.findViewById(R.id.iv_item_go);
    holder.guid= (RelativeLayout) convertView.findViewById(R.id.rl_guid);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Custom custom=list.get(position).getCustom();

        holder.mobile.setText(custom.getMobile());
        holder.name.setText(custom.getId());
        holder.dc.setText(custom.getInputtime());
        if (list.get(position).isFirst()){

            holder.guid.getChildAt(0).setVisibility(View.VISIBLE);
        }else{
            holder.guid.getChildAt(0).setVisibility(View.GONE);
        }


holder.iv.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent( context, Detail.class);
        intent.putExtra("id",position);

        context.startActivity(intent);
    }
});
        return convertView;
    }
    class ViewHolder {
        TextView name;
        TextView  mobile;
        TextView dc;
        ImageView iv;
      RelativeLayout guid;

    }



}
