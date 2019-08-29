package cn.endureblaze.kirby.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import cn.endureblaze.kirby.R;

import java.util.List;

public class ThemeListAdapter extends BaseAdapter {
    private int checkItem;
    private Context context;
    private List<Integer> theme_list;

    public ThemeListAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.theme_list = list;
    }

    @Override
    public int getCount() {
        return theme_list.size();
    }

    @Override
    public Object getItem(int position) {
        return theme_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.colors_image_layout, null);
            holder = new Holder();
            holder.imageView1 = convertView.findViewById(R.id.img_1);
            holder.imageView2 = convertView.findViewById(R.id.img_2);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.imageView1.setImageResource(theme_list.get(position));
        if (checkItem == position) {
            holder.imageView2.setImageResource(R.drawable.ic_kirby_done);
        }
        return convertView;
    }
    public void setCheckItem(int checkItem) {
        this.checkItem = checkItem;
    }
    static class Holder {
        ImageView imageView1;
        ImageView imageView2;
    }
}
