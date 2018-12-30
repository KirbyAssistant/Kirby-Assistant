package com.kirby.runanjing.crash;
import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.text.*;
import android.view.*;
import android.widget.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.utils.*;

import android.content.ClipboardManager;
import com.kirby.runanjing.R;
import com.umeng.analytics.*;
import com.kirby.runanjing.bottomdialog.*;
import com.kirby.runanjing.bottomdialog.*;

public class CrashDialog extends BaseBottomDialog
{

	private Throwable crash;

	private PhoneUtil phoneInfo;
	public static CrashDialog newInstance(String type,Throwable crash)
	{
		Bundle bundle = new Bundle();
		bundle.putSerializable("crash",crash);
		CrashDialog dialog = new CrashDialog();
		dialog.setArguments(bundle);
		return dialog;
	}

	@Override
    public int initTheme() {
        return theme;
    }

	public CrashDialog setTheme(@StyleRes int theme) {
        this.theme = theme;
        return this;
    }
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		 crash=(Throwable) bundle.getSerializable("crash");
	}

	@Override
	public int intLayoutId()
	{
		return R.layout.dialog_crash;
	}

	@Override
	public void convertView(ViewHolder holder, final BaseBottomDialog mess_dialog)
	{
		MobclickAgent.reportError(getActivity(), crash);
		phoneInfo = new PhoneUtil(mess_dialog.getActivity());
		
		final TextView crashText=(TextView)holder.getView(R.id.crashText);
		Button copy=(Button)holder.getView(R.id.copy);
		
		crashText.append("手机品牌:");
        crashText.append(Html.fromHtml("<font color=\"#E51C23\">" + phoneInfo.getBrand() + "</font>"));
        crashText.append("\n");
        crashText.append("手机型号:");
        crashText.append(Html.fromHtml("<font color=\"#E51C23\">" + phoneInfo.getModel() + "</font>"));
        crashText.append("\n");
        crashText.append("名称:");
        crashText.append(Html.fromHtml("<font color=\"#E51C23\">" + phoneInfo.getProduct() + "</font>"));
        crashText.append("\n");
        crashText.append("安卓版本:");
        crashText.append(Html.fromHtml("<font color=\"#E51C23\">" + phoneInfo.getAndroidVersion() + "</font>"));
        crashText.append("\n");
        crashText.append("软件版本:");
        crashText.append(Html.fromHtml("<font color=\"#E51C23\">" + phoneInfo.getAppVersion() + "</font>"));
        crashText.append("\n");

		crashText.append("错误信息: ");
        crashText.append(Html.fromHtml("<font color=\"#E51C23\">" + crash.getMessage() + "</font>"));
        crashText.append("\n");
        for (StackTraceElement stackTraceElement : crash.getStackTrace())
		{
            String className = stackTraceElement.getClassName();
            String methodName = stackTraceElement.getMethodName();
            String fileName = stackTraceElement.getFileName();
            String line = stackTraceElement.getLineNumber() + "";
            crashText.append("\t\t\t\t\t\t");
            crashText.append(Html.fromHtml("<font  color=\"#E51C23\">at</font>"));
            crashText.append("\t" + className);
            crashText.append("." + methodName);
            crashText.append("(");
            crashText.append(Html.fromHtml("<font color=\"#E51C23\">" + fileName + "</font>"));
            crashText.append(":");
            crashText.append(Html.fromHtml("<u><font color=\"#5677FC\">" + line + "</font></u>"));
            crashText.append(")");
            crashText.append("\n");
        }
		
		copy.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
					cm.setText(crashText.getText());
					Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.copy_success),Toast.LENGTH_SHORT).show();
				}
			});
	}
}
