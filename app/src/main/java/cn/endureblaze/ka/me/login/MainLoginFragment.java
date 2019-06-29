package cn.endureblaze.ka.me.login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.base.BaseFragment;
import cn.endureblaze.ka.helper.LayoutAnimationHelper;
import cn.endureblaze.ka.main.MainActivity;
import cn.endureblaze.ka.me.user.MainUserFragment;
import cn.endureblaze.ka.utils.EmailUtil;
import cn.endureblaze.ka.utils.PlayAnimUtil;

import java.util.Objects;

public class MainLoginFragment extends BaseFragment
{
	private MainActivity m;

	private CardView login_card;

	private CardView register_card;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.main_login, container, false);
		m=(MainActivity)getActivity();
		initLogin(view);
		return view;
	}

	@SuppressLint("WrongConstant")
    private void initLogin(final View view)
	{
		Button login_btn= view.findViewById(R.id.login_btn);
		TextView register_btn= view.findViewById(R.id.register_btn);
		TextView forgetpassword= view.findViewById(R.id.forgetpassword);
		final TextView loginOrRegister= view.findViewById(R.id.sw_login_register);
		login_card= view.findViewById(R.id.login);
		register_card= view.findViewById(R.id.register);
		loginOrRegister.setOnClickListener(p1 -> {
			//判断是不是注册状态
			if(8 == register_card.getVisibility()){
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
		});
		//从edittext里获取字符串
		final EditText login_username= view.findViewById(R.id.login_username);
		final EditText login_passworld= view.findViewById(R.id.login_passworld);
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
						Toast.makeText(getActivity(), Objects.requireNonNull(getActivity()).getString(R.string.is_null), Toast.LENGTH_SHORT).show();
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
										Toast.makeText(getActivity(), Objects.requireNonNull(getActivity()).getString(R.string.login_success), Toast.LENGTH_SHORT).show();
										m.replaceFragment(new MainUserFragment());
									}
									else
									{
										loginProgress.dismiss();
										Toast.makeText(getActivity(), Objects.requireNonNull(getActivity()).getString(R.string.login_fail)+e.getErrorCode()+e.toString(), Toast.LENGTH_SHORT).show();
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
								EditText register_username= view.findViewById(R.id.register_username);
								EditText register_email= view.findViewById(R.id.register_email);
								EditText register_password= view.findViewById(R.id.register_password);
								EditText register_password_again= view.findViewById(R.id.register_password_again);
								final String str_username=register_username.getText().toString();
								String str_email=register_email.getText().toString();
								final String str_passworld=register_password.getText().toString();
								String str_passworld_again=register_password_again.getText().toString();
								//判断是否为空
								if (str_username.isEmpty() || str_email.isEmpty() || str_passworld.isEmpty() || str_passworld_again.isEmpty())
								{
									registerProgress.dismiss();
									Toast.makeText(getActivity(), Objects.requireNonNull(getActivity()).getString(R.string.is_null), Toast.LENGTH_SHORT).show();
								}
								else
								{
									if(!EmailUtil.checkEmail(str_email)){
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
														loginOrRegister.setText(Objects.requireNonNull(getActivity()).getResources().getString(R.string.not_have_user));
														Toast.makeText(getActivity(), getActivity().getString(R.string.register_success), Toast.LENGTH_SHORT).show();
													}
													else
													{
														registerProgress.dismiss();
														Toast.makeText(getActivity(), Objects.requireNonNull(getActivity()).getString(R.string.register_fail)+e.toString(), Toast.LENGTH_SHORT).show();
													}
												}
											});
									}
									else
									{
										
										Toast.makeText(getActivity(), Objects.requireNonNull(getActivity()).getString(R.string.password), Toast.LENGTH_SHORT).show();
									}
								}
							}
						}}
					);
		forgetpassword.setOnClickListener(p1 -> {
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
		});
			
		LayoutAnimationController controller = LayoutAnimationHelper.makeLayoutAnimationController();
		ViewGroup viewGroup = view.findViewById(R.id.root_view);
		viewGroup.setLayoutAnimation(controller);
		viewGroup.scheduleLayoutAnimation();
		PlayAnimUtil.playLayoutAnimation(LayoutAnimationHelper.getAnimationSetFromBottom(),false);
}
}
