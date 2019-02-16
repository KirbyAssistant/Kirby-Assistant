package cn.endureblaze.ka.resources.cheatcode;
import android.content.*;
import android.view.*;
import android.widget.*;
import cn.endureblaze.ka.*;
import cn.endureblaze.ka.bean.*;
import java.util.*;

public class CheatCodeAdapter extends ArrayAdapter<CheatCode>
{
	private int resourceId;
	public CheatCodeAdapter(Context context, int textViewResourceId, List<CheatCode>object)
	{
		super(context, textViewResourceId, object);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		CheatCode cheatCode=getItem(position);
		View view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
		TextView cheatCode_id=(TextView)view.findViewById(R.id.cheatCode_id);
		TextView cheatCode_ny=(TextView)view.findViewById(R.id.cheatCode_ny);
		cheatCode_id.setText(cheatCode.getId());
		cheatCode_ny.setText(cheatCode.getCheatCode());
		return view;
	}
}
