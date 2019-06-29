package cn.endureblaze.ka.customui;

import android.animation.*;
import android.annotation.SuppressLint;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.os.*;
import android.text.*;
import android.util.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;
import androidx.annotation.Keep;
import androidx.annotation.RequiresApi;
import cn.endureblaze.ka.R;

public class RippleLayout extends RelativeLayout {
    public static final int DEFAULT_DURATION = 2000;
    public static final String RIPPLE_TYPE_FILL = "0";
    public static final String RIPPLE_TYPE_STROKE = "1";
    public static final String TAG = "RippleLayout";

    private Paint mPaint;
    private AnimatorSet mAnimatorSet;
    private boolean mIsAnimating;
    private RippleView mRippleView;


    public RippleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RippleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public RippleLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * Initializes the required ripple views
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }
        //reading the attributes
        @SuppressLint("Recycle") TypedArray attrValues = context.obtainStyledAttributes(attrs, R.styleable.RippleLayout);
        int color = attrValues.getColor(R.styleable.RippleLayout_rippleLayoutColor, getResources().getColor(android.R.color.holo_blue_bright));
        float startRadius = attrValues.getDimension(R.styleable.RippleLayout_startRadius, getMeasuredWidth());
        float endRadius = attrValues.getDimension(R.styleable.RippleLayout_endRadius, getMeasuredWidth() * 2);
        float strokeWidth = attrValues.getDimension(R.styleable.RippleLayout_strokeWidth, 4);
        int duration = attrValues.getInteger(R.styleable.RippleLayout_duration, DEFAULT_DURATION);
        String rippleType = attrValues.getString(R.styleable.RippleLayout_rippleLayoutType);
        if (TextUtils.isEmpty(rippleType)) {
            rippleType = RIPPLE_TYPE_FILL;
        }
        //initialize stuff
        initializePaint(color, rippleType, strokeWidth);
        initializeRippleView(endRadius, startRadius, strokeWidth);
        initializeAnimators(startRadius, endRadius, duration);
    }

    private void initializeRippleView(float endRadius, float startRadius, float strokeWidth) {
        mRippleView = new RippleView(getContext(), mPaint, startRadius);
        LayoutParams params = new LayoutParams(2 * (int)(endRadius + strokeWidth), 2 * (int)(endRadius + strokeWidth));
        params.addRule(CENTER_IN_PARENT, TRUE);
        addView(mRippleView, params);
        mRippleView.setVisibility(INVISIBLE);
    }

    private void initializeAnimators(float startRadius, float endRadius, int duration) {
        mAnimatorSet = new AnimatorSet();
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(mRippleView, "radius", startRadius, endRadius);
        scaleXAnimator.setRepeatCount(ValueAnimator.INFINITE);

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mRippleView, "alpha", 1f, 0f);
        alphaAnimator.setRepeatCount(ValueAnimator.INFINITE);

        mAnimatorSet.setDuration(duration);
        mAnimatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimatorSet.playTogether(scaleXAnimator, alphaAnimator);
    }

    /**
     * Starts the ripple animation
     */
    public void startRippleAnimation() {
        if (mIsAnimating) {
            //already animating
            return;
        }
        mRippleView.setVisibility(View.VISIBLE);
        mAnimatorSet.start();
        mIsAnimating = true;
    }

    /**
     * Stops the ripple animation
     */
    public void stopRippleAnimation() {
        if (!mIsAnimating) {
            //already not animating
            return;
        }
        mAnimatorSet.end();
        mRippleView.setVisibility(View.INVISIBLE);
        mIsAnimating = false;
    }

    private void initializePaint(int color, String rippleType, float strokeWidth) {
        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);
        if (rippleType.equals(RIPPLE_TYPE_STROKE)) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(strokeWidth);
        } else {
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setStrokeWidth(0);
        }
    }

    private static class RippleView extends View {
        private Paint mPaint;
        private float mRadius;

        public float getRadius() {
            return mRadius;
        }

        @Keep
        public void setRadius(float radius) {
            mRadius = radius;
            invalidate();
        }

        public RippleView(Context context, Paint p, float radius) {
            super(context);
            mPaint = p;
            mRadius = radius;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            int centerX = getWidth()/2;
            int centerY = getHeight()/2;
            canvas.drawCircle(centerX, centerY, mRadius, mPaint);
        }
    }
}
