package com.kirby.runanjing.fragment.main;

import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;
import cn.bmob.v3.*;
import cn.bmob.v3.exception.*;
import cn.bmob.v3.listener.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.activity.*;
import com.kirby.runanjing.bmob.*;
import java.lang.reflect.*;
import android.support.v7.widget.*;

public class MainLoginFragment extends Fragment
{
	private View view;
	private MainActivity m;
	private AlertDialog register_dialog;

	private CardView login_card;

	private CardView register_card;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        view=inflater.inflate(R.layout.main_login, container, false);
		m=(MainActivity)getActivity();
		initLogin(view);
		return view;
	}

	private void initLogin(final View view)
	{
		Button 登录=(Button)view.findViewById(R.id.登录);
		TextView 注册=(TextView)view.findViewById(R.id.注册);
		Button 忘记密码=(Button)view.findViewById(R.id.忘记密码);
		final TextView 切换=(TextView)view.findViewById(R.id.sw_login_register);
		login_card=(CardView)view.findViewById(R.id.login);
		register_card=(CardView)view.findViewById(R.id.register);
		切换.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					//判断是不是注册状态
					if(register_card.getVisibility()==8){
						//切换到注册
						login_card.setVisibility(8);
						register_card.setVisibility(0);
						切换.setText(getActivity().getResources().getString(R.string.have_user));
					}
					else
					{
						//切换到登录
						login_card.setVisibility(0);
						register_card.setVisibility(8);
						切换.setText(getActivity().getResources().getString(R.string.not_have_user));
					}
				}
			});
		//从edittext里获取字符串
		final EditText 登录_用户名=(EditText)view.findViewById(R.id.登录_用户名);
		final EditText 登录_密码=(EditText)view.findViewById(R.id.登录_密码);
		登录.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v)
				{
					//获取的字符串转化为string数据类型
					String editText_用户名=登录_用户名.getText().toString();
					String editText_密码=登录_密码.getText().toString();
					//判断是否为空
					if (editText_用户名.isEmpty() || editText_密码.isEmpty())
					{
						Toast.makeText(getActivity(), getActivity().getString(R.string.is_null), Toast.LENGTH_SHORT).show();
					}
					else
					{
						//使用BmobUser类进行登陆
						final MyUser bu2 = new MyUser();
						bu2.setUsername(editText_用户名);
						bu2.setPassword(editText_密码);
						bu2.login(new SaveListener<BmobUser>() {
								@Override
								public void done(BmobUser bmobUser, BmobException e)
								{
									if (e == null)
									{
										Toast.makeText(getActivity(),getActivity().getString(R.string.login_susses), Toast.LENGTH_SHORT).show();
										m.open();
									}
									else
									{
										Toast.makeText(getActivity(), getActivity().getString(R.string.login_fail), Toast.LENGTH_SHORT).show();
									}
								}
							});
					}
				}
			});
		注册.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v)
				{
					//从实例化布局的edittext中获取字符串并转化为string数据
								EditText 注册_用户名=(EditText)view.findViewById(R.id.注册_用户名);
								EditText 邮箱=(EditText)view.findViewById(R.id.邮箱);
								EditText 注册_密码=(EditText)view.findViewById(R.id.注册_密码);
								EditText 重复密码=(EditText)view.findViewById(R.id.重复密码);
								final String editText_用户名=注册_用户名.getText().toString();
								String editText_邮箱=邮箱.getText().toString();
								final String editText_密码=注册_密码.getText().toString();
								String editText_重复密码=重复密码.getText().toString();
								//判断是否为空
								if (editText_用户名.isEmpty() || editText_邮箱.isEmpty() || editText_密码.isEmpty() || editText_重复密码.isEmpty())
								{
									Toast.makeText(getActivity(), getActivity().getString(R.string.is_null), Toast.LENGTH_SHORT).show();
								}
								else
								{
									//判断两次的密码是否一样
									if (editText_密码.equalsIgnoreCase(editText_重复密码))
									{
										//使用BmobUser类进行注册
										MyUser myUser=new MyUser();
										myUser.setUsername(editText_用户名);
										myUser.setPassword(editText_密码);
										myUser.setEmail(editText_邮箱);
										myUser.signUp(new SaveListener<MyUser>() {
												@Override
												public void done(MyUser s, BmobException e)
												{
													if (e == null)
													{
														登录_用户名.setText(editText_用户名);
														登录_密码.setText(editText_密码);
														login_card.setVisibility(0);
														register_card.setVisibility(8);
														切换.setText(getActivity().getResources().getString(R.string.not_have_user));
														Toast.makeText(getActivity(), getActivity().getString(R.string.register_susses), Toast.LENGTH_SHORT).show();
													}
													else
													{
														
														Toast.makeText(getActivity(), getActivity().getString(R.string.register_fail), Toast.LENGTH_SHORT).show();
													}
												}
											});
									}
									else
									{
										
										Toast.makeText(getActivity(), getActivity().getString(R.string.password), Toast.LENGTH_SHORT).show();
									}
								}
							}
						}
					);
}
}
