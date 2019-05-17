package cn.endureblaze.ka.me.user;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v4.content.*;
import android.support.v4.util.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.text.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;
import cn.bmob.v3.*;
import cn.bmob.v3.exception.*;
import cn.bmob.v3.listener.*;
import cn.endureblaze.ka.*;
import cn.endureblaze.ka.base.*;
import cn.endureblaze.ka.helper.*;
import cn.endureblaze.ka.main.*;
import cn.endureblaze.ka.me.user.userhead.*;
import cn.endureblaze.ka.utils.*;
import com.bumptech.glide.*;
import com.umeng.analytics.*;
import java.io.*;
import java.net.*;

import android.support.v7.app.AlertDialog;
import android.view.View.*;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.FutureTarget;
import java.util.concurrent.ExecutionException;
public class MainUserFragment extends BaseFragment {
	private ChangeUserHeadLocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
	private View view;
	private MainActivity m;
	private ImageView userHead;
	private String name;
	private String email;
	private String id;
	private CardView card;
	private Button edit_email;
	private Button edit_password;
	private IntentFilter intentFilter;

	private Button user_logout;

	private ImageView mo_userHead;

	private RelativeLayout changeUserHead;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_user, container, false);
		m = (MainActivity)getActivity();
		initUser(view);
		return view;
	}

	private void initUser(View view) {
		localBroadcastManager = localBroadcastManager.getInstance(getActivity());
		intentFilter = new IntentFilter();
		intentFilter.addAction("com.kirby.download.CHANGE_USERHEAD");
		localReceiver = new ChangeUserHeadLocalReceiver();
        //注册本地广播监听器
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
		name = UserUtil.getCurrentUser().getUsername();
		email = UserUtil.getCurrentUser().getEmail();
		id = UserUtil.getCurrentUser().getObjectId();
		TextView userName=(TextView)view.findViewById(R.id.user_name);
		TextView userId=(TextView)view.findViewById(R.id.user_id);
		TextView userTime=(TextView)view.findViewById(R.id.user_data);
		TextView userEmail=(TextView)view.findViewById(R.id.user_email);
		card = (CardView)view.findViewById(R.id.cardview);
		edit_email = (Button)view.findViewById(R.id.edit_email);
		edit_password = (Button)view.findViewById(R.id.edit_password);	
		user_logout = (Button)view.findViewById(R.id.user_logout);	
		edit_email.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1) {
					userEditEmail();
				}			
			});
		edit_password.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1) {
					userEditPassword();
				}
			});
		user_logout.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1) {
					UserUtil.getCurrentUser().logOut();
					MobclickAgent.onProfileSignOff();
					m.open();
				}
			});
		userHead = (ImageView)view.findViewById(R.id.user_head);
		mo_userHead = (ImageView)view.findViewById(R.id.mo_user_head);
		try {
			if (UserUtil.getCurrentUser().getUserHead().getFileUrl() != null) {
				Glide
					.with(getActivity())
					.load(UserUtil.getCurrentUser().getUserHead().getFileUrl())
					//.apply(Kirby.getGlideRequestOptions())
					.asBitmap()
					.placeholder(R.drawable.buletheme)
					.fitCenter()
					.into(userHead);

				new Thread(new Runnable() {

						String pattern="5";
						@Override
						public void run() {
							Bitmap glideBitmap=GlideUtil.getGlideBitmap(getActivity(), UserUtil.getCurrentUser().getUserHead().getFileUrl());
							int scaleRatio = 0;
							if (TextUtils.isEmpty(pattern)) {
								scaleRatio = 0;
							} else if (scaleRatio < 0) {
								scaleRatio = 10;
							} else {
								scaleRatio = Integer.parseInt(pattern);
							}
							//                        下面的这个方法必须在子线程中执行
							final Bitmap blurBitmap2 = FastBlurUtil.toBlur(glideBitmap, scaleRatio);

							//                   刷新ui必须在主线程中执行
							try {
								getActivity().runOnUiThread(new Runnable(){

										@Override
										public void run() {
											mo_userHead.setScaleType(ImageView.ScaleType.CENTER_CROP);
											mo_userHead.setImageBitmap(blurBitmap2);
										}
									});
							} catch (Exception e) {}
						}
					}).start();
			}
		} catch (Exception e) {}
		userName.setText(UserUtil.getCurrentUser().getUsername());
		userId.setText("id:" + UserUtil.getCurrentUser().getObjectId());
		userTime.setText(getActivity().getResources().getString(R.string.register_time) + ":" + UserUtil.getCurrentUser().getCreatedAt());
		userEmail.setText(getActivity().getResources().getString(R.string.user_email) + ":" + UserUtil.getCurrentUser().getEmail());
		changeUserHead = (RelativeLayout)view.findViewById(R.id.change_userhead);
		changeUserHead.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1) {
					Pair<View, String> userHeadPair=new Pair<View,String>(userHead, "userHead");
					Pair<View, String> cardPair= new Pair<View,String>(card, "card");
					Pair<View, String> editPassPair= new Pair<View,String>(user_logout, "pass");
					Intent intent = new Intent(getActivity(), HeadActivity.class);			
					ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), userHeadPair, cardPair, editPassPair);
					startActivityForResult(intent, 3, options.toBundle());
				}
			});

		LayoutAnimationController controller = LayoutAnimationHelper.makeLayoutAnimationController();
		ViewGroup viewGroup = (ViewGroup)view.findViewById(R.id.root_view);
		viewGroup.setLayoutAnimation(controller);
		viewGroup.scheduleLayoutAnimation();
		PlayAnimUtil.playLayoutAnimation(LayoutAnimationHelper.getAnimationSetFromBottom(), false);
	}
	private void userEditEmail() {
		LayoutInflater lay_1 =getActivity().getLayoutInflater();
		final View modification_email_layout = lay_1.inflate(R.layout.dialog_modification_email, null);
		new AlertDialog.Builder(getActivity())
			.setTitle(R.string.modification_email)
			.setView(modification_email_layout) 
			.setPositiveButton(R.string.dia_yes, new
			DialogInterface.OnClickListener()
			{

				private ProgressDialog modificationEmailProgress;
				@Override
				public void onClick(DialogInterface dialog, int which) {
					modificationEmailProgress = new ProgressDialog(getActivity());
					modificationEmailProgress.setCanceledOnTouchOutside(false);
					modificationEmailProgress.setMessage(getResources().getString(R.string.modification_email));
					modificationEmailProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					modificationEmailProgress.show();
					EditText modification_email_old=(EditText)modification_email_layout.findViewById(R.id.modification_email_old);
					EditText modification_email_new=(EditText)modification_email_layout.findViewById(R.id.modification_email_new);
					String str_modification_email_old=modification_email_old.getText().toString();
					String str_modification_email_new=modification_email_new.getText().toString();
					if (str_modification_email_old.isEmpty() || str_modification_email_new.isEmpty()) {
						modificationEmailProgress.dismiss();
						Toast.makeText(getActivity(), R.string.is_null, Toast.LENGTH_SHORT).show();
					} else {
						if (EmailUtil.checkEmail(str_modification_email_old) == false || EmailUtil.checkEmail(str_modification_email_new) == false) {
							modificationEmailProgress.dismiss();
							Toast.makeText(getActivity(), R.string.email_fail, Toast.LENGTH_SHORT).show();
						} else {
							if (email.equals(str_modification_email_old)) {
								BmobUser modification_email=new BmobUser();
								modification_email.setEmail(str_modification_email_new);
								modification_email.update(id, new UpdateListener() {

										@Override
										public void done(BmobException e) {
											if (e == null) {
												modificationEmailProgress.dismiss();
												Toast.makeText(getActivity(), R.string.edit_true, Toast.LENGTH_SHORT).show();
												UserUtil.getCurrentUser().logOut();
												//finish();
												m.open();
											} else {
												modificationEmailProgress.dismiss();
												Toast.makeText(getActivity(), R.string.edit_false + e.getMessage(), Toast.LENGTH_SHORT).show();
											}
										}

									});
							} else {
								Toast.makeText(getActivity(), R.string.modification_email_false, Toast.LENGTH_SHORT).show();
							}
						}
					}
				}
			}
		)					
			.setNegativeButton(R.string.dia_cancel, null)
			.show();
	}
	private void userEditPassword() {
		LayoutInflater lay_2 =getActivity().getLayoutInflater();
		final View modification_password_layout = lay_2.inflate(R.layout.dialog_modification_password, null);
		new AlertDialog.Builder(getActivity())
			.setTitle(R.string.modification_password)
			.setView(modification_password_layout) 
			.setPositiveButton(R.string.dia_yes, new
			DialogInterface.OnClickListener()
			{

				private int text;

				private ProgressDialog changepasswordProgress;
				@Override
				public void onClick(DialogInterface dialog, int which) {
					changepasswordProgress = new ProgressDialog(getActivity());
					changepasswordProgress.setCanceledOnTouchOutside(false);
					changepasswordProgress.setMessage(getResources().getString(R.string.modification_password));
					changepasswordProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					changepasswordProgress.show();
					EditText modification_password_old=(EditText)modification_password_layout.findViewById(R.id.modification_password_old);
					EditText modification_password_new=(EditText)modification_password_layout.findViewById(R.id.modification_password_new);
					EditText modification_password_new_again=(EditText)modification_password_layout.findViewById(R.id.modification_password_new_again);
					String str_modification_password_old=modification_password_old.getText().toString();
					String str_modification_password_new=modification_password_new.getText().toString();
					String str_modification_password_new_again=modification_password_new_again.getText().toString();
					if (str_modification_password_old.isEmpty() || str_modification_password_new.isEmpty() || str_modification_password_new_again.isEmpty()) {
						changepasswordProgress.dismiss();
						Toast.makeText(getActivity(), R.string.is_null, Toast.LENGTH_SHORT).show();
					} else {
						if (str_modification_password_new.equals(str_modification_password_new_again)) {
							final BmobUser pas = new BmobUser();
							pas.updateCurrentUserPassword(str_modification_password_old, str_modification_password_new, new UpdateListener(){
									@Override
									public void done(BmobException e) {
										if (e == null) {
											changepasswordProgress.dismiss();
											Toast.makeText(getActivity(), R.string.edit_true, Toast.LENGTH_SHORT).show();
											UserUtil.getCurrentUser().logOut();
											//finish();
											m.open();
										} else {
											changepasswordProgress.dismiss();
											Toast.makeText(getActivity(), R.string.edit_false + e.getMessage(), Toast.LENGTH_SHORT).show();
										}
									}
								});
						} else {
							Toast.makeText(getActivity(), R.string.modification_password_false, Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		)					
			.setNegativeButton(R.string.dia_cancel, null)
			.show();
	}
	private class ChangeUserHeadLocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
			m.open();
        }
    }
	public static Bitmap netPicToBmp(String src) {
		try {
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);

			//设置固定大小
			//需要的大小
			float newWidth = 200f;
			float newHeigth = 200f;

			//图片大小
			int width = myBitmap.getWidth();
			int height = myBitmap.getHeight();

			//缩放比例
			float scaleWidth = newWidth / width;
			float scaleHeigth = newHeigth / height;
			Matrix matrix = new Matrix();
			matrix.postScale(scaleWidth, scaleHeigth);

			Bitmap bitmap = Bitmap.createBitmap(myBitmap, 0, 0, width, height, matrix, true);
			return bitmap;
		} catch (IOException e) {
			// Log exception
			return null;
		}
	}
}
