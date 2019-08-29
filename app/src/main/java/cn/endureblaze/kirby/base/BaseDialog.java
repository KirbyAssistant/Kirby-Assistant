package cn.endureblaze.kirby.base;

import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import cn.endureblaze.kirby.omgdialog.OMGDialog;
import cn.endureblaze.kirby.omgdialog.ViewConvertListener;
import cn.endureblaze.kirby.omgdialog.ViewHolder;

public class BaseDialog extends OMGDialog {
    private ViewConvertListener convertListener;

    public static BaseDialog init() {
        return new BaseDialog();
    }

    @Override
    public int initTheme() {
        return theme;
    }

    @Override
    public int intLayoutId() {
        return layoutId;
    }

    @Override
    public void convertView(ViewHolder holder, OMGDialog dialog) {
        if (convertListener != null) {
            convertListener.convertView(holder, dialog);
        }
    }

    public BaseDialog setTheme(@StyleRes int theme) {
        this.theme = theme;
        return this;
    }

    public BaseDialog setLayoutId(@LayoutRes int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    public BaseDialog setConvertListener(ViewConvertListener convertListener) {
        this.convertListener = convertListener;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            convertListener = savedInstanceState.getParcelable("listener");
        }
    }

    /**
     * 保存接口
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("listener", convertListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        convertListener = null;
    }
}
