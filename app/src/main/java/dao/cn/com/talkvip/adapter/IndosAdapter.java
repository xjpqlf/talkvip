package dao.cn.com.talkvip.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import dao.cn.com.talkvip.R;
import dao.cn.com.talkvip.bean.CustomFrist;
import dao.cn.com.talkvip.view.activity.Detail;

public class IndosAdapter extends RecyclerView.Adapter<IndosAdapter.ViewHolder> implements View.OnClickListener{
    private List<CustomFrist> list;
    private Context context;
    public IndosAdapter(Context context, List<CustomFrist> list) {
        this.context = context;
        this.list = list;
    }
    private OnItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,  int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_custom, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.name.setText(list.get(position).getCustom().getId());
        viewHolder.mobile.setText(list.get(position).getCustom().getMobile());
        viewHolder.dc.setText(list.get(position).getCustom().getName());
        if (list.get(position).isFirst()){

            viewHolder.guid.getChildAt(0).setVisibility(View.VISIBLE);
        }else{
            viewHolder.guid.getChildAt(0).setVisibility(View.GONE);
        }
     viewHolder.rl.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent( context, Detail.class);
             intent.putExtra("id",list.get(position).getCustom().getId());
             intent.putExtra("name",list.get(position).getCustom().getName());
             intent.putExtra("mobile",list.get(position).getCustom().getMobile());
             intent.putExtra("type","未拨打");


             context.startActivity(intent);
         }
     });

        //将position保存在itemView的Tag中，以便点击时进行获取
        viewHolder.itemView.setTag(position);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    //获取数据的数量
    @Override
    public int getItemCount() {
        return list.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public  TextView  mobile;
        public  TextView dc;
        public  ImageView iv;
        public  RelativeLayout guid;
        public  RelativeLayout rl;

        public ViewHolder(View view){
            super(view);

            name = (TextView) view
                    .findViewById(R.id.tv_name);
           mobile= (TextView) view
                    .findViewById(R.id.tv_mobile);
          dc = (TextView) view
                    .findViewById(R.id.tv_dc);
        iv= (ImageView) view.findViewById(R.id.iv_item_go);
           guid= (RelativeLayout) view.findViewById(R.id.rl_guid);
        rl= (RelativeLayout) view.findViewById(R.id.rl_gotode);

        }


    }
}