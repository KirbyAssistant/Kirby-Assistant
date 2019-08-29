package cn.endureblaze.kirby.user.info;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import cn.bmob.v3.BmobUser;
import cn.ednureblaze.glidecache.GlideCache;
import cn.endureblaze.kirby.Kirby;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.base.BaseFragment;
import cn.endureblaze.kirby.user.info.avatar.UserAvatarActivity;
import cn.endureblaze.kirby.util.UserUtil;
import com.bumptech.glide.Glide;

import java.util.Objects;

public class MainUserInfoFragment extends BaseFragment {
    private boolean CHANGE_HEAD=false;
    private ImageView userAvatar,blur_user_avatar;

    private Button modify_email,modify_password,user_logout;
    private View view;
    private LocalBroadcastManager localBroadcastManager;
    private TextView userName,userId,userTime,userEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.main_user_info_fragment, container, false);
       initView();
       return view;
    }

    @Override
    protected void onFragmentFirstVisible() {
        initUserInfo();
        initBroad();
    }

    private void initBroad(){
        localBroadcastManager = LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity()));
    }

    private void initView(){
        userName= view.findViewById(R.id.user_name);
        userId= view.findViewById(R.id.user_id);
        userTime= view.findViewById(R.id.user_data);
        userEmail= view.findViewById(R.id.user_email);
        CardView card = view.findViewById(R.id.cardview);

        modify_email = view.findViewById(R.id.edit_email);
        modify_password = view.findViewById(R.id.edit_password);
        user_logout = view.findViewById(R.id.user_logout);

        userAvatar = view.findViewById(R.id.user_avatar);
        blur_user_avatar = view.findViewById(R.id.mo_user_avatar);
    }

    @SuppressLint("SetTextI18n")
    private void initUserInfo() {
        String email = UserUtil.getCurrentUser().getEmail();
        String id = UserUtil.getCurrentUser().getObjectId();

        modify_email.setOnClickListener(p1 -> userModifyEmail());
        modify_password.setOnClickListener(p1 -> userModifyPassword());

        user_logout.setOnClickListener(p1 -> {
            BmobUser.logOut();
            Intent intent = new Intent("cn.endureblaze.kirby.USER_LOGOUT");
            intent.putExtra("type", "user_logout");
            localBroadcastManager.sendBroadcast(intent);
        });

        try {
            if (UserUtil.getCurrentUser().getUserAvatar().getFileUrl() != null) {
                Glide
                        .with(Objects.requireNonNull(getActivity()))
                        .load(UserUtil.getCurrentUser().getUserAvatar().getFileUrl())
                        .apply(Kirby.getGlideRequestOptions())
                        .into(userAvatar);
                GlideCache.setBlurImageViaGlideCache(getActivity(), blur_user_avatar,UserUtil.getCurrentUser().getUserAvatar().getFileUrl(),"5");
            }
        } catch (Exception ignored) {}

        userName.setText(UserUtil.getCurrentUser().getUsername());
        userId.setText("id:" + UserUtil.getCurrentUser().getObjectId());
        userTime.setText(Objects.requireNonNull(getActivity()).getResources().getString(R.string.user_register_time) + ":" + UserUtil.getCurrentUser().getCreatedAt());
        userEmail.setText(getActivity().getResources().getString(R.string.user_email) + ":" + UserUtil.getCurrentUser().getEmail());

        RelativeLayout changeUserAvatar = view.findViewById(R.id.change_user_avatar);

        changeUserAvatar.setOnClickListener(p1 -> {
            Pair<View, String> userAvatarPair= new Pair<>(userAvatar, "userAvatar");
            Pair<View, String> logoutPair= new Pair<>(user_logout, "logout");
            Intent intent = new Intent(getActivity(), UserAvatarActivity.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), userAvatarPair, logoutPair);
            getActivity().startActivityForResult(intent, 3, options.toBundle());
        });
    }

    private void userModifyEmail() { new UserModifyEmail(getActivity()).start();}
    private void userModifyPassword() { new UserModifyPassword(getActivity(),localBroadcastManager).start();}

}