package cn.endureblaze.kirby.user.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.base.BaseFragment;
import cn.endureblaze.kirby.data.DataBus;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class MainLoginFragment extends BaseFragment {
    private CardView login_card;

    private CardView register_card;
    private View view;
    //登录
    private MaterialButton login_btn;
    private TextInputEditText login_username;
    private TextInputEditText login_password;
    //注册
    private MaterialButton register_btn;
    private TextInputEditText register_username;
    private TextInputEditText register_email;
    private TextInputEditText register_password;
    private TextInputEditText register_password_again;

    private TextView loginOrRegister;

    private boolean is_change_theme=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_login_fragment, container, false);
        initView();
        return view;
    }

    @Override
    protected void onFragmentFirstVisible() {
        initLogin();
        initBroad();
        initRegister();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if(DataBus.isChangeTheme()) {
            initViewData();
        }
    }

    private void initBroad(){
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity()));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("cn.endureblaze.kirby.CHANGE_THEME");
        LoginLocalReceiver localReceiver = new LoginLocalReceiver();
        //注册本地广播监听器
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
    }
    //界面布局切换
    private void initView() {
        //登录界面布局
        login_btn = view.findViewById(R.id.login_btn);
        login_username = view.findViewById(R.id.login_username);
        login_password = view.findViewById(R.id.login_password);
        //注册界面布局
        register_btn = view.findViewById(R.id.register_btn);
        register_username = view.findViewById(R.id.register_username);
        register_email = view.findViewById(R.id.register_email);
        register_password = view.findViewById(R.id.register_password);
        register_password_again = view.findViewById(R.id.register_password_again);

        //切换
        loginOrRegister = view.findViewById(R.id.sw_login_register);
        login_card = view.findViewById(R.id.login);
        register_card = view.findViewById(R.id.register);
        loginOrRegister.setOnClickListener(p1 -> {
            //判断是不是注册状态
            if (View.GONE == register_card.getVisibility()) {
                //切换到注册
                login_card.setVisibility(View.GONE);
                register_card.setVisibility(View.VISIBLE);
                loginOrRegister.setText(Objects.requireNonNull(getActivity()).getResources().getString(R.string.user_have_user));
            } else {
                //切换到登录
                login_card.setVisibility(View.VISIBLE);
                register_card.setVisibility(View.GONE);
                loginOrRegister.setText(Objects.requireNonNull(getActivity()).getResources().getString(R.string.user_not_have_user));
            }
        });
    }
    private void initViewData(){
        login_username.setText(DataBus.getLoginUsername());
        login_password.setText(DataBus.getLoginPassword());
        register_username.setText(DataBus.getRegisterUsername());
        register_email.setText(DataBus.getRegisterEmail());
        register_password.setText(DataBus.getRegisterPassword());
        register_password_again.setText(DataBus.getRegisterPasswordAgain());
        if(DataBus.isLoginOrRegister()){
            login_card.setVisibility(View.VISIBLE);
            register_card.setVisibility(View.GONE);
            loginOrRegister.setText(Objects.requireNonNull(getActivity()).getResources().getString(R.string.user_not_have_user));
        }else{
            login_card.setVisibility(View.GONE);
            register_card.setVisibility(View.VISIBLE);
            loginOrRegister.setText(Objects.requireNonNull(getActivity()).getResources().getString(R.string.user_have_user));
        }
    }
    //登录逻辑
    private void initLogin(){
        login_btn.setOnClickListener(view -> new Login(getContext(),getActivity(),login_username,login_password).start());
    }
    //注册逻辑
    private void initRegister(){
        register_btn.setOnClickListener(view1 -> new Register(getContext(),getActivity(),register_username,register_email,register_password,register_password_again,login_username,login_password,login_card,register_card,loginOrRegister).start());
    }
    //广播处理
    class LoginLocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            DataBus.setChangeTheme(true);
            if (login_card.getVisibility()==View.VISIBLE){
                DataBus.setLoginOrRegister(true);
            }else{
                DataBus.setLoginOrRegister(false);
            }
            DataBus.setLoginUsername(Objects.requireNonNull(login_username.getText()).toString());
            DataBus.setLoginPassword(Objects.requireNonNull(login_password.getText()).toString());
            DataBus.setRegisterUsername(Objects.requireNonNull(register_username.getText()).toString());
            DataBus.setRegisterPassword(Objects.requireNonNull(register_password.getText()).toString());
            DataBus.setRegisterPasswordAgain(Objects.requireNonNull(register_password_again.getText()).toString());
            DataBus.setRegisterEmail(Objects.requireNonNull(register_email.getText()).toString());
        }
    }
}