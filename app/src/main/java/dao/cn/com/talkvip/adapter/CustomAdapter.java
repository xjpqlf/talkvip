package dao.cn.com.talkvip.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import dao.cn.com.talkvip.R;
import dao.cn.com.talkvip.bean.Custom;

/**
 * @name dao.cn.com.talkvip.bean
 * @class name：TalkVip
 * @class describe
 * @anthor uway QQ:343251588
 * @time 2017/4/7 15:39
 * @change uway
 * @chang 2017/4/7 15:39
 * @class describe
 */

public class CustomAdapter extends CommonAdapter<Custom> {
    public CustomAdapter(Context context, int layoutId, List<Custom> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Custom c, int position) {
      holder.setText(R.id.tv_name,c.getId());
      holder.setText(R.id.tv_mobile,c.getMobile());
      holder.setText(R.id.tv_dc,c.getInputtime());



    }
}
