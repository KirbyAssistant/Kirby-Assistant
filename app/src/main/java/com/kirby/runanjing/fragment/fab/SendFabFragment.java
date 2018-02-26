package com.kirby.runanjing.fragment.fab;

import android.app.*;
import android.view.*;
import android.widget.*;
import com.allattentionhere.fabulousfilter.*;
import com.kirby.runanjing.*;

import com.kirby.runanjing.R;
import com.kirby.runanjing.activity.*;
import android.content.*;
import android.content.SharedPreferences.*;
import android.text.*;

public class SendFabFragment extends AAH_FabulousFragment
{
	private String edit_内容;

	private EditText edit_编辑;
    public static SendFabFragment newInstance()
	{
        SendFabFragment f = new SendFabFragment();
        return f;
    }
    @Override
    public void setupDialog(Dialog dialog, int style)
	{
		final View contentView = View.inflate(getContext(), R.layout.filter_sample_view, null);
        RelativeLayout rl_content = (RelativeLayout) contentView.findViewById(R.id.edit);
		SharedPreferences mess_=getActivity().getSharedPreferences("string", 0);
		String mess= mess_.getString("Message", null);
	 edit_编辑=(EditText)contentView.findViewById(R.id.内容_编辑);
		edit_编辑.addTextChangedListener(textWatcher);
		if (mess != null)
		{
			edit_编辑.setText(mess);
		}
		contentView.findViewById(R.id.发送).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v)
				{
					//获取字符串转化为string数据
					EditText 内容=(EditText)contentView.findViewById(R.id.内容_编辑);
					edit_内容 = 内容.getText().toString();
					//判断是否为空
					if (edit_内容.isEmpty())
					{
						Toast.makeText(getContext(), getActivity().getString(R.string.is_null), Toast.LENGTH_SHORT).show();
					}
					else
					{			
						SharedPreferences y=getContext().getSharedPreferences("string", 0);
						SharedPreferences.Editor edit=y.edit();
						edit.putString("Message", edit_内容);
						edit.apply();
						closeFilter("closed");
					}
				}
			});
		setAnimationListener((AnimationListener) getActivity());
	    setPeekHeight(300);
        setViewMain(rl_content); //necessary; main bottomsheet view
        setMainContentView(contentView); // necessary; call at end before super
		//setCallbacks((Callbacks) getActivity());
		super.setupDialog(dialog, style); //call super at last
    }
	private TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
		{
			
		}

		@Override
		public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
		{
			SharedPreferences y=getContext().getSharedPreferences("string", 0);
			SharedPreferences.Editor edit=y.edit();
			edit.putString("Message", edit_编辑.getText().toString());
			edit.apply();
		}

		@Override
		public void afterTextChanged(Editable p1)
		{
			// TODO: Implement this method
		}


       

        };                    
	}
