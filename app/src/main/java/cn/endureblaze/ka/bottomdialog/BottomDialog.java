package cn.endureblaze.ka.bottomdialog;

import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

public class BottomDialog extends BaseBottomDialog {
    private ViewConvertListener convertListener;

    public static BottomDialog init() {
        return new BottomDialog();
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
    public void convertView(ViewHolder holder, BaseBottomDialog dialog) {
        if (convertListener != null) {
            convertListener.convertView(holder, dialog);
        }
    }

    public BottomDialog setTheme(@StyleRes int theme) {
        this.theme = theme;
        return this;
    }

    public BottomDialog setLayoutId(@LayoutRes int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    public BottomDialog setConvertListener(ViewConvertListener convertListener) {
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
