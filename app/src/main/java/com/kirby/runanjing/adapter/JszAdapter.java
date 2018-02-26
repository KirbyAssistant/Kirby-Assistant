package com.kirby.runanjing.adapter;
import android.content.*;
import android.view.*;
import android.widget.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.untils.*;
import java.util.*;
import com.kirby.runanjing.bean.*;

public class JszAdapter extends ArrayAdapter<Jsz>
{
	private int resourceId;
	public JszAdapter(Context context, int textViewResourceId, List<Jsz>object)
	{
		super(context, textViewResourceId, object);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Jsz jsz=getItem(position);
		View view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
		TextView jsz_id=(TextView)view.findViewById(R.id.jsz_id);
		TextView jsz_ny=(TextView)view.findViewById(R.id.jsz_ny);
		jsz_id.setText(jsz.getId());
		jsz_ny.setText(jsz.getJsz());
		return view;
	}
}
