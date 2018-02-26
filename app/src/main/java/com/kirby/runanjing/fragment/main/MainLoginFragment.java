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
import com.kirby.runanjing.untils.*;
import com.kirby.runanjing.bmob.*;

public class MainLoginFragment extends Fragment
{
	private View view;
	private MainActivity m;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        view=inflater.inflate(R.layout.main_login, container, false);
		m=(MainActivity)getActivity();
		initLogin(view);
		return view;
	}

	private void initLogin(View view)
	{
		Button 登录=(Button)view.findViewById(R.id.登录);
		Button 注册=(Button)view.findViewById(R.id.注册);
		Button 忘记密码=(Button)view.findViewById(R.id.忘记密码);
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
					//实例化布局
					LayoutInflater inflater = LayoutInflater.from(getActivity());
					final View 注册_layout = inflater.inflate(R.layout.dialog_register, null);
					new AlertDialog.Builder(getActivity())
						.setTitle(R.string.register)
						.setView(注册_layout)
						.setPositiveButton("确定", new
						DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								//从实例化布局的edittext中获取字符串并转化为string数据
								EditText 注册_用户名=(EditText)注册_layout.findViewById(R.id.注册_用户名);
								EditText 邮箱=(EditText)注册_layout.findViewById(R.id.邮箱);
								EditText 注册_密码=(EditText)注册_layout.findViewById(R.id.注册_密码);
								EditText 重复密码=(EditText)注册_layout.findViewById(R.id.重复密码);
								String editText_用户名=注册_用户名.getText().toString();
								String editText_邮箱=邮箱.getText().toString();
								String editText_密码=注册_密码.getText().toString();
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
					)
						.setNegativeButton(R.string.dia_cancel, null).show();
				}
			});
		忘记密码.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View v)
				{
					//没想好怎么弄
				}
			});
	}
}
