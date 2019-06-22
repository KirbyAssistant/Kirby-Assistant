package cn.endureblaze.ka.me.login;

import android.app.*;
import android.os.*;
import androidx.core.app.*;
import androidx.appcompat.app.*;
import androidx.appcompat.widget.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;
import androidx.cardview.widget.CardView;
import cn.bmob.v3.*;
import cn.bmob.v3.exception.*;
import cn.bmob.v3.listener.*;
import cn.endureblaze.ka.*;
import cn.endureblaze.ka.utils.*;

import androidx.appcompat.app.AlertDialog;
import cn.endureblaze.ka.helper.*;
import cn.endureblaze.ka.base.*;
import cn.endureblaze.ka.main.*;
import cn.endureblaze.ka.me.user.MainUserFragment;

public class MainLoginFragment extends BaseFragment
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
		Button login_btn=(Button)view.findViewById(R.id.login_btn);
		TextView register_btn=(TextView)view.findViewById(R.id.register_btn);
		TextView forgetpassword=(TextView)view.findViewById(R.id.forgetpassword);
		final TextView loginOrRegister=(TextView)view.findViewById(R.id.sw_login_register);
		login_card=(CardView)view.findViewById(R.id.login);
		register_card=(CardView)view.findViewById(R.id.register);
		loginOrRegister.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					//判断是不是注册状态
					if(register_card.getVisibility()==8){
						//切换到注册
						login_card.setVisibility(8);
						register_card.setVisibility(0);
						loginOrRegister.setText(getActivity().getResources().getString(R.string.have_user));
					}
					else
					{
						//切换到登录
						login_card.setVisibility(0);
						register_card.setVisibility(8);
						loginOrRegister.setText(getActivity().getResources().getString(R.string.not_have_user));
					}
				}
			});
		//从edittext里获取字符串
		final EditText login_username=(EditText)view.findViewById(R.id.login_username);
		final EditText login_passworld=(EditText)view.findViewById(R.id.login_passworld);
		login_btn.setOnClickListener(new View.OnClickListener(){

				private ProgressDialog loginProgress;
				@Override
				public void onClick(View v)
				{
					loginProgress = new ProgressDialog(getActivity());
					loginProgress.setCanceledOnTouchOutside(false);
					loginProgress.setMessage(getResources().getString(R.string.login));
					loginProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					loginProgress.show();
					//获取的字符串转化为string数据类型
					String str_username=login_username.getText().toString();
					String str_passworld=login_passworld.getText().toString();
					//判断是否为空
					if (str_passworld.isEmpty() || str_username.isEmpty())
					{
						loginProgress.dismiss();
						Toast.makeText(getActivity(), getActivity().getString(R.string.is_null), Toast.LENGTH_SHORT).show();
					}
					else
					{
						//使用BmobUser类进行登陆
						final BmobUser bu2 = new BmobUser();
						bu2.setUsername(str_username);
						bu2.setPassword(str_passworld);
						bu2.login(new SaveListener<BmobUser>() {
								@Override
								public void done(BmobUser bmobUser, BmobException e)
								{
									if (e == null)
									{
										loginProgress.dismiss();
										Toast.makeText(getActivity(),getActivity().getString(R.string.login_success), Toast.LENGTH_SHORT).show();
										m.replaceFragment(new MainUserFragment());
									}
									else
									{
										loginProgress.dismiss();
										Toast.makeText(getActivity(), getActivity().getString(R.string.login_fail)+e.getErrorCode()+e.toString(), Toast.LENGTH_SHORT).show();
									}
								}
							});
					}
				}
			});
		register_btn.setOnClickListener(new View.OnClickListener(){

				private ProgressDialog registerProgress;
				@Override
				public void onClick(View v)
				{
					registerProgress = new ProgressDialog(getActivity());
				    registerProgress.setCanceledOnTouchOutside(false);
					registerProgress.setMessage(getResources().getString(R.string.register));
					registerProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					registerProgress.show();
					//从实例化布局的edittext中获取字符串并转化为string数据
								EditText register_username=(EditText)view.findViewById(R.id.register_username);
								EditText register_email=(EditText)view.findViewById(R.id.register_email);
								EditText register_password=(EditText)view.findViewById(R.id.register_password);
								EditText register_password_again=(EditText)view.findViewById(R.id.register_password_again);
								final String str_username=register_username.getText().toString();
								String str_email=register_email.getText().toString();
								final String str_passworld=register_password.getText().toString();
								String str_passworld_again=register_password_again.getText().toString();
								//判断是否为空
								if (str_username.isEmpty() || str_email.isEmpty() || str_passworld.isEmpty() || str_passworld_again.isEmpty())
								{
									registerProgress.dismiss();
									Toast.makeText(getActivity(), getActivity().getString(R.string.is_null), Toast.LENGTH_SHORT).show();
								}
								else
								{
									if(EmailUtil.checkEmail(str_email)==false){
										registerProgress.dismiss();
										Toast.makeText(getActivity(), R.string.email_fail, Toast.LENGTH_SHORT).show();							
									}else{
									//判断两次的密码是否一样
									if (str_passworld.equalsIgnoreCase(str_passworld_again))
									{
										//使用BmobUser类进行注册
										BmobUser myUser=new BmobUser();
										myUser.setUsername(str_username);
										myUser.setPassword(str_passworld);
										myUser.setEmail(str_email);
										myUser.signUp(new SaveListener<BmobUser>() {
												@Override
												public void done(BmobUser s, BmobException e)
												{
													if (e == null)
													{
														registerProgress.dismiss();
														login_username.setText(str_username);
														login_passworld.setText(str_passworld);
														login_card.setVisibility(0);
														register_card.setVisibility(8);
														loginOrRegister.setText(getActivity().getResources().getString(R.string.not_have_user));
														Toast.makeText(getActivity(), getActivity().getString(R.string.register_success), Toast.LENGTH_SHORT).show();
													}
													else
													{
														registerProgress.dismiss();
														Toast.makeText(getActivity(), getActivity().getString(R.string.register_fail)+e.toString(), Toast.LENGTH_SHORT).show();
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
						}}
					);
		forgetpassword.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					final String email = "nihaocun@163.com";
					BmobUser.resetPasswordByEmail(email, new UpdateListener() {

							@Override
							public void done(BmobException e) {
								if(e==null){
									Toast.makeText(getActivity(),"重置密码请求成功，请到" + email + "邮箱进行密码重置操作",Toast.LENGTH_SHORT).show();
								}else{
									Toast.makeText(getActivity(),"失败:" + e.getMessage(),Toast.LENGTH_SHORT).show();
								}
							}
						});
				}
			});
			
		LayoutAnimationController controller = LayoutAnimationHelper.makeLayoutAnimationController();
		ViewGroup viewGroup = (ViewGroup)view.findViewById(R.id.root_view);
		viewGroup.setLayoutAnimation(controller);
		viewGroup.scheduleLayoutAnimation();
		PlayAnimUtil.playLayoutAnimation(LayoutAnimationHelper.getAnimationSetFromBottom(),false);
}
}
