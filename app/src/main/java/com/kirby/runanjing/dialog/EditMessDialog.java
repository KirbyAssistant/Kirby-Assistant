package com.kirby.runanjing.dialog;
import android.app.*;
import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.view.*;
import android.view.inputmethod.*;
import android.widget.*;
import cn.bmob.v3.exception.*;
import cn.bmob.v3.listener.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.bmob.*;
import com.shehuan.nicedialog.*;

import com.kirby.runanjing.R;
import cn.bmob.v3.*;
import com.kirby.runanjing.fragment.main.*;
import android.text.*;

public class EditMessDialog extends BaseNiceDialog
{

	private EditText edit_编辑;

	public static EditMessDialog newInstance(String type)
	{
		Bundle bundle = new Bundle();

		EditMessDialog dialog = new EditMessDialog();
		dialog.setArguments(bundle);
		return dialog;
	}

	@Override
    public int initTheme()
	{
        return theme;
    }

	public EditMessDialog setTheme(@StyleRes int theme)
	{
        this.theme = theme;
        return this;
    }

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
	}

	@Override
	public int intLayoutId()
	{
		return R.layout.dialog_editmess;
	}

	@Override
	public void convertView(ViewHolder holder, final BaseNiceDialog edit_mess_dialog)
	{
		final MyUser u = BmobUser.getCurrentUser(MyUser.class);
		SharedPreferences mess_=getActivity().getSharedPreferences("string", 0);
		String mess= mess_.getString("Message", null);
		edit_编辑 = (EditText)holder.getView(R.id.内容_编辑);
		edit_编辑.post(new Runnable() {
				@Override
				public void run()
				{
					InputMethodManager imm =
						(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.showSoftInput(edit_编辑, 0);
				}
			});
		edit_编辑.addTextChangedListener(textWatcher);
		if (mess != null)
		{
			edit_编辑.setText(mess);
		}
		ImageView 发送=holder.getView(R.id.发送);
		发送.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v)
				{
					//获取字符串转化为string数据
					//EditText 内容=(EditText)v.findViewById(R.id.内容_编辑);
					String edit_内容 = edit_编辑.getText().toString();
					//判断是否为空
					if (edit_内容.isEmpty())
					{
						Toast.makeText(getContext(), getActivity().getString(R.string.is_null), Toast.LENGTH_SHORT).show();
					}
					else
					{			
						final ProgressDialog progressDialog = new ProgressDialog(getActivity());
						progressDialog.setMessage(getResources().getString(R.string.mess_upload));
						progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
						progressDialog.show();
						//自定义MessBmob发送留言
						MessageBmob mess = new MessageBmob();
						mess.setMessage(edit_内容);
						mess.setNickname(u.getUsername());
						mess.save(new SaveListener<String>() {
								@Override
								public void done(String objectId, BmobException e)
								{
									progressDialog.dismiss();
									if (e == null)
									{		
										SharedPreferences y=getActivity().getSharedPreferences("string", 0);
										SharedPreferences.Editor edit=y.edit();
										edit.putString("Message", "");
										edit.apply();
										edit_mess_dialog.dismiss();
										MainMessFragment main_mess=(MainMessFragment)edit_mess_dialog.getActivity().getSupportFragmentManager().findFragmentById(R.id.main_fragment);
										main_mess.getMessage();
										Toast.makeText(getActivity(), getResources().getString(R.string.mess_true) + objectId, Toast.LENGTH_SHORT).show();
									}
									else
									{
										Toast.makeText(getActivity(), getResources().getString(R.string.mess_false) + e.getMessage(), Toast.LENGTH_SHORT).show();
									}
								}
							});
					}
				}
			});
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
