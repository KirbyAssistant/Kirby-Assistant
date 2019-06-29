package cn.endureblaze.ka.crash;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.bottomdialog.BaseBottomDialog;
import cn.endureblaze.ka.bottomdialog.ViewHolder;
import cn.endureblaze.ka.utils.PhoneUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.Objects;

public class CrashDialog extends BaseBottomDialog
{

	private Throwable crash;

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
		 crash=(Throwable) Objects.requireNonNull(bundle).getSerializable("crash");
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
        PhoneUtil phoneInfo = new PhoneUtil(mess_dialog.getActivity());
		
		final TextView crashText= holder.getView(R.id.crashText);
		Button copy= holder.getView(R.id.copy);
		
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
		
		copy.setOnClickListener(p1 -> {
			ClipboardManager cm = (ClipboardManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CLIPBOARD_SERVICE);
			Objects.requireNonNull(cm).setText(crashText.getText());
			Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.copy_success),Toast.LENGTH_SHORT).show();
		});
	}
}
