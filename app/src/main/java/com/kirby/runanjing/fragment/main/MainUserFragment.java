package com.kirby.runanjing.fragment.main;

import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v4.content.*;
import android.support.v4.util.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import cn.bmob.v3.*;
import cn.bmob.v3.exception.*;
import cn.bmob.v3.listener.*;
import com.bumptech.glide.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.activity.*;
import com.kirby.runanjing.bmob.*;
import com.kirby.runanjing.untils.*;

import com.kirby.runanjing.R;
import android.*;
public class MainUserFragment extends Fragment
{
	private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
	private View view;
	private MainActivity m;
	private MyUser u;
	private ImageView userHead;
	private String name;
	private String email;
	private String id;
	private CardView card;
	private Button edit_email;
	private Button edit_password;

	private IntentFilter intentFilter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        view = inflater.inflate(R.layout.main_user, container, false);
		m = (MainActivity)getActivity();
		u = BmobUser.getCurrentUser(MyUser.class);
		initUser(view);
		return view;
	}

	private void initUser(View view)
	{
		localBroadcastManager = localBroadcastManager.getInstance(getContext());
		intentFilter = new IntentFilter();
		intentFilter.addAction("com.kirby.download.CHANGE_USERHEAD");
		localReceiver = new LocalReceiver();
        //注册本地广播监听器
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);
		name = u.getUsername();
		email = u.getEmail();
		id = u.getObjectId();
		TextView userName=(TextView)view.findViewById(R.id.user_name);
		TextView userId=(TextView)view.findViewById(R.id.user_id);
		TextView userTime=(TextView)view.findViewById(R.id.user_data);
		TextView userEmail=(TextView)view.findViewById(R.id.user_email);
		card = (CardView)view.findViewById(R.id.cardview);
		edit_email = (Button)view.findViewById(R.id.edit_email);
		edit_password = (Button)view.findViewById(R.id.edit_password);	
		edit_email.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					userEditEmail();
				}			
			});
		edit_password.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					userEditPassword();
				}
			});
		userHead = (ImageView)view.findViewById(R.id.user_head);
		try
		{
			if (u.getUserHead().getFileUrl() != null)
			{
				Glide
					.with(getContext())
					.load(u.getUserHead().getFileUrl())
					.into(userHead);
			}
		}
		catch (Exception e)
		{}
		userName.setText(u.getUsername());
		userId.setText("id:" + u.getObjectId());
		userTime.setText(getActivity().getResources().getString(R.string.register_time) + ":" + u.getCreatedAt());
		userEmail.setText(getActivity().getResources().getString(R.string.user_email) + ":" + u.getEmail());
		userHead.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
					{
						ActivityCompat.requestPermissions(getActivity(), new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
					}
					else
					{
						Pair<View, String> userHeadPair=new Pair<View,String>(userHead, "userHead");
						Pair<View, String> cardPair= new Pair<View,String>(card, "card");
						Pair<View, String> editEmailPair= new Pair<View,String>(edit_email, "email");
						Pair<View, String> editPassPair= new Pair<View,String>(edit_password, "pass");
						Intent intent = new Intent(getActivity(), HeadActivity.class);			
						ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), userHeadPair, cardPair, editEmailPair, editPassPair);
						startActivityForResult(intent,3, options.toBundle());
					}
				}
			});
	}

	private void userEditEmail()
	{
		LayoutInflater lay_1 =getActivity().getLayoutInflater();
		final View modification_email_layout = lay_1.inflate(R.layout.dialog_modification_email, null);
		new AlertDialog.Builder(getActivity())
			.setTitle(R.string.email_title)
			.setView(modification_email_layout) 
			.setPositiveButton(R.string.dia_yes, new
			DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					EditText 修改邮箱_原邮箱=(EditText)modification_email_layout.findViewById(R.id.修改邮箱_原邮箱);
					EditText 修改邮箱_新邮箱=(EditText)modification_email_layout.findViewById(R.id.修改邮箱_新邮箱);
					String edit_原邮箱=修改邮箱_原邮箱.getText().toString();
					String edit_新邮箱=修改邮箱_新邮箱.getText().toString();
					if (edit_原邮箱.isEmpty() || edit_新邮箱.isEmpty())
					{
						Toast.makeText(getActivity(), R.string.is_null, Toast.LENGTH_SHORT).show();
					}
					else
					{
						if (email.equals(edit_原邮箱))
						{
							MyUser 邮箱=new MyUser();
							邮箱.setEmail(edit_新邮箱);
							邮箱.update(id, new UpdateListener() {

									@Override
									public void done(BmobException e)
									{
										if (e == null)
										{
											Toast.makeText(getActivity(), R.string.edit_true, Toast.LENGTH_SHORT).show();
											u.logOut();
											//finish();
											m.open();
										}
										else
										{
											Toast.makeText(getActivity(), R.string.edit_false + e.getMessage(), Toast.LENGTH_SHORT).show();
										}
									}

								});
						}
						else
						{
							Toast.makeText(getActivity(), R.string.email_false, Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		)					
			.setNegativeButton(R.string.dia_cancel, null)
			.show();
	}
	private void userEditPassword()
	{
		LayoutInflater lay_2 =getActivity().getLayoutInflater();
		final View modification_password_layout = lay_2.inflate(R.layout.dialog_modification_password, null);
		new AlertDialog.Builder(getActivity())
			.setTitle(R.string.password_title)
			.setView(modification_password_layout) 
			.setPositiveButton(R.string.dia_yes, new
			DialogInterface.OnClickListener()
			{

				private int text;
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					EditText 修改密码_原密码=(EditText)modification_password_layout.findViewById(R.id.修改密码_原密码);
					EditText 修改密码_新密码=(EditText)modification_password_layout.findViewById(R.id.修改密码_新密码);
					EditText 修改密码_验证=(EditText)modification_password_layout.findViewById(R.id.修改密码_验证);
					String edit_原密码=修改密码_原密码.getText().toString();
					String edit_新密码=修改密码_新密码.getText().toString();
					String edit_验证=修改密码_验证.getText().toString();
					if (edit_原密码.isEmpty() || edit_新密码.isEmpty() || edit_验证.isEmpty())
					{
						Toast.makeText(getActivity(), R.string.is_null, Toast.LENGTH_SHORT).show();
					}
					else
					{
						if (edit_新密码.equals(edit_验证))
						{
							final MyUser pas = new MyUser();
							pas.updateCurrentUserPassword(edit_原密码, edit_新密码, new UpdateListener(){
									@Override
									public void done(BmobException e)
									{
										if (e == null)
										{
											Toast.makeText(getActivity(), R.string.edit_true, Toast.LENGTH_SHORT).show();
											u.logOut();
											//finish();
											m.open();
										}
										else
										{
											Toast.makeText(getActivity(), R.string.edit_false + e.getMessage(), Toast.LENGTH_SHORT).show();
										}
									}
								});
						}
						else
						{
							Toast.makeText(getActivity(), R.string.password_false, Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		)					
			.setNegativeButton(R.string.dia_cancel, null)
			.show();
	}
	private class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
			m.open();
        }
    }
}
