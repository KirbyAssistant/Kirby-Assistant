package cn.endureblaze.kirby.main.behavior;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import com.google.android.material.snackbar.Snackbar;

public class BottomNavigationBehavior<V extends View>  extends CoordinatorLayout.Behavior<V> {

        private int lastStartedType;
        private ValueAnimator offsetAnimator;
        private boolean isSnappingEnabled = true;

        public BottomNavigationBehavior (Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public BottomNavigationBehavior () {
            super();
        }

        public void setSnappingEnabled(boolean isEnabled) {
            isSnappingEnabled = isEnabled;
            lastStartedType = 0;
            if (offsetAnimator != null) {
                offsetAnimator.cancel();
                offsetAnimator = null;
            }
        }

        public void expand(CoordinatorLayout coordinatorLayout, V child) {

            int[] consumed = new int[2];

            boolean curIsSnappingEnabled = isSnappingEnabled;
            if(curIsSnappingEnabled) {
                setSnappingEnabled(false);
            }

            onNestedPreScroll(coordinatorLayout, child, null, 0, -1000, consumed, ViewCompat.TYPE_TOUCH);

            if(curIsSnappingEnabled) {
                setSnappingEnabled(true);
            }
        }

        @Override
        public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View directTargetChild,
                                           @NonNull View target, int axes, int type) {

            if (axes != ViewCompat.SCROLL_AXIS_VERTICAL)
                return false;

            lastStartedType = type;
            if(offsetAnimator!= null) {
                offsetAnimator.cancel();
            }

            return true;
        }

        @Override
        public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int type) {
            if (!isSnappingEnabled)
                return ;

            // add snap behaviour
            // Logic here borrowed from AppBarLayout onStopNestedScroll code

            if (lastStartedType == ViewCompat.TYPE_TOUCH || type == ViewCompat.TYPE_NON_TOUCH) {

                // find nearest seam
                float currTranslation = child.getTranslationY();
                float childHalfHeight = child.getHeight() * 0.5f;

                if (currTranslation >= childHalfHeight) {
                    animateBarVisibility(child, false); // translate down
                } else {
                    animateBarVisibility(child, true); // translate up
                }

            }
        }

        @Override
        public void onNestedPreScroll (@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull V child,
                                       @NonNull View target,
                                       int dx,
                                       int dy,
                                       @NonNull int[] consumed,
                                       int type) {

            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
            child.setTranslationY(Math.max(0f, Math.min(child.getHeight(), child.getTranslationY() + dy)));
        }


        @Override
        public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull V child, @NonNull View dependency) {
            if(dependency instanceof Snackbar.SnackbarLayout) {
                updateSnackBar(child, (Snackbar.SnackbarLayout)dependency);
            }

            return super.layoutDependsOn(parent, child, dependency);
        }

        private void updateSnackBar(View child, Snackbar.SnackbarLayout snackBarLayout) {
            if(snackBarLayout.getLayoutParams() instanceof CoordinatorLayout.LayoutParams) {
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)snackBarLayout.getLayoutParams();

                params.setAnchorId(child.getId());
                params.anchorGravity = Gravity.TOP;
                params.gravity = Gravity.TOP;
                snackBarLayout.setLayoutParams(params);
            }
        }

        private void animateBarVisibility(final View child, boolean isVisible) {
            if (offsetAnimator == null) {
                offsetAnimator = new ValueAnimator();
                offsetAnimator.setInterpolator(new DecelerateInterpolator());
                offsetAnimator.setDuration(150L);
                offsetAnimator.addUpdateListener(animation -> child.setTranslationY((float)animation.getAnimatedValue()));
            } else {
                offsetAnimator.cancel();
            }

            float targetTranslation = isVisible ? 0f : child.getHeight();
            offsetAnimator.setFloatValues(child.getTranslationY(), targetTranslation);
            offsetAnimator.start();
        }
}