package cn.endureblaze.kirby.omgdialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.*;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import cn.endureblaze.kirby.R;

import java.util.Objects;

public abstract class OMGDialog extends DialogFragment {
    private static final String MARGIN = "margin";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String DIM = "dim_amount";
    private static final String GRAVITY = "gravity";
    private static final String CANCEL = "out_cancel";
    private static final String THEME = "theme";
    private static final String ANIM = "anim_style";
    private static final String LAYOUT = "layout_id";

    private int margin;//左右边距
    private int width;//宽度
    private int height;//高度
    private float dimAmount = 0.5f;//灰度深浅
    private int gravity = Gravity.CENTER;//显示的位置
    private boolean outCancel = true;//是否点击外部取消
    @StyleRes
    protected int theme = R.style.OMGDialogStyle; // dialog主题
    @StyleRes
    private int animStyle;
    @LayoutRes
    protected int layoutId;

    public abstract int intLayoutId();

    public abstract void convertView(ViewHolder holder, OMGDialog dialog);

    public int initTheme() {
        return theme;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, initTheme());

        //恢复保存的数据
        if (savedInstanceState != null) {
            margin = savedInstanceState.getInt(MARGIN);
            width = savedInstanceState.getInt(WIDTH);
            height = savedInstanceState.getInt(HEIGHT);
            dimAmount = savedInstanceState.getFloat(DIM);
            gravity = savedInstanceState.getInt(GRAVITY);
            outCancel = savedInstanceState.getBoolean(CANCEL);
            theme = savedInstanceState.getInt(THEME);
            animStyle = savedInstanceState.getInt(ANIM);
            layoutId = savedInstanceState.getInt(LAYOUT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutId = intLayoutId();
        View view = inflater.inflate(layoutId, container, false);
        convertView(ViewHolder.create(view), this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initParams();
    }

    /**
     * 屏幕旋转等导致DialogFragment销毁后重建时保存数据
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MARGIN, margin);
        outState.putInt(WIDTH, width);
        outState.putInt(HEIGHT, height);
        outState.putFloat(DIM, dimAmount);
        outState.putInt(GRAVITY, gravity);
        outState.putBoolean(CANCEL, outCancel);
        outState.putInt(THEME, theme);
        outState.putInt(ANIM, animStyle);
        outState.putInt(LAYOUT, layoutId);
    }

    @SuppressLint("RtlHardcoded")
    private void initParams() {
        Window window = Objects.requireNonNull(getDialog()).getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            //调节灰色背景透明度[0-1]，默认0.5f
            lp.dimAmount = dimAmount;
            if (gravity != 0) {
                lp.gravity = gravity;
            }
            switch (gravity) {
                case Gravity.LEFT:
                case (Gravity.LEFT | Gravity.BOTTOM):
                case (Gravity.LEFT | Gravity.TOP):
                    if (animStyle == 0) {
                        animStyle = R.style.LeftAnimation;
                    }
                    break;
                case Gravity.TOP:
                    if (animStyle == 0) {
                        animStyle = R.style.TopAnimation;
                    }
                    break;
                case Gravity.RIGHT:
                case (Gravity.RIGHT | Gravity.BOTTOM):
                case (Gravity.RIGHT | Gravity.TOP):
                    if (animStyle == 0) {
                        animStyle = R.style.RightAnimation;
                    }
                    break;
                case Gravity.BOTTOM:
                    if (animStyle == 0) {
                        animStyle = R.style.BottomAnimation;
                    }
                    break;
                default:
                    break;

            }

            //设置dialog宽度
            if (width == 0) {
                lp.width = Utils.getScreenWidth(Objects.requireNonNull(getContext())) - 2 * Utils.dp2px(getContext(), margin);
            } else if (width == -1) {
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            } else {
                lp.width = Utils.dp2px(Objects.requireNonNull(getContext()), width);
            }

            //设置dialog高度
            if (height == 0) {
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            } else {
                lp.height = Utils.dp2px(Objects.requireNonNull(getContext()), height);
            }

            //设置dialog进入、退出的动画
            window.setWindowAnimations(animStyle);
            window.setAttributes(lp);
        }
        setCancelable(outCancel);
    }

    public OMGDialog setMargin(int margin) {
        this.margin = margin;
        return this;
    }

    public OMGDialog setWidth(int width) {
        this.width = width;
        return this;
    }

    public OMGDialog setHeight(int height) {
        this.height = height;
        return this;
    }

    public OMGDialog setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
        return this;
    }

    public OMGDialog setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public OMGDialog setOutCancel(boolean outCancel) {
        this.outCancel = outCancel;
        return this;
    }

    public OMGDialog setAnimStyle(@StyleRes int animStyle) {
        this.animStyle = animStyle;
        return this;
    }

    public OMGDialog show(FragmentManager manager) {
        FragmentTransaction ft = manager.beginTransaction();
        if (this.isAdded()) {
            ft.remove(this).commit();
        }
        ft.add(this, String.valueOf(System.currentTimeMillis()));
        ft.commitAllowingStateLoss();
        return this;
    }
}
