package cn.endureblaze.ka.resources.cheatcode;
import android.annotation.SuppressLint;
import android.content.*;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
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

	@NonNull
    @Override
	public View getView(int position, View convertView, @NonNull ViewGroup parent)
	{
		CheatCode cheatCode=getItem(position);
		@SuppressLint("ViewHolder") View view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
		TextView cheatCode_id= view.findViewById(R.id.cheatCode_id);
		TextView cheatCode_ny= view.findViewById(R.id.cheatCode_ny);
		cheatCode_id.setText(Objects.requireNonNull(cheatCode).getId());
		cheatCode_ny.setText(cheatCode.getCheatCode());
		return view;
	}
}
