package app.mosn.zdepthshadowsample.fam;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

import app.mosn.library.ZDepth;
import app.mosn.zdepthshadowsample.R;

public class FloatingActionMenu extends FrameLayout {

    protected FloatingActionMenuToggle mToggleButton;
    protected boolean mToggle;

    protected FloatingActionMenuButton[] mMenuButtons;
    protected PointF[] mMenuButtonShowPoints;
    protected PointF[] mMenuButtonHidePoints;
    protected int[] mIconIds = {
            R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher
    };

    public FloatingActionMenu(Context context) {
        super(context);
    }

    public FloatingActionMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatingActionMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        // set floating action menu toggle
        mToggleButton = new FloatingActionMenuToggle(getContext());
        mToggleButton.setIconId(R.drawable.ic_launcher);
        mToggleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        addView(mToggleButton);

        // set floating action menu buttons
        mMenuButtons = new FloatingActionMenuButton[mIconIds.length];
        mMenuButtonShowPoints = new PointF[mIconIds.length];
        mMenuButtonHidePoints = new PointF[mIconIds.length];
        for (int i = 0; i < mIconIds.length; i++) {
            FloatingActionMenuButton btn = new FloatingActionMenuButton(getContext());
            btn.setIconId(mIconIds[i]);
            mMenuButtons[i] = btn;
            addView(btn, 0);
        }

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                hidden(false);
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        layoutToggleButton(left, top, right, bottom);
        layoutMenuButton();
    }

    protected void layoutToggleButton(int left, int top, int right, int bottom) {
        int width = mToggleButton.getMeasuredWidth();
        int height = mToggleButton.getMeasuredHeight();

        int newLeft = right - width;
        int newRight = right;
        int newTop = bottom - height;
        int newBottom = bottom;

        mToggleButton.layout(newLeft, newTop, newRight, newBottom);
    }

    protected void layoutMenuButton() {
        final int count = mMenuButtons.length;
        final int baseMenuButtonX = (int) mToggleButton.getX();
        final int baseMenuButtonY = (int) mToggleButton.getY();

        final int toggleWidth = mToggleButton.getMeasuredWidth();
        final int toggleHeight = mToggleButton.getMeasuredHeight();

        for (int i = 0; i < count; i++) {
            FloatingActionMenuButton btn = mMenuButtons[i];

            final int btnWidth = btn.getMeasuredWidth();
            final int btnHeight = btn.getMeasuredHeight();

            int beforeButtonsHeight = 0;
            for (int j = 0; j < i; j++) {
                FloatingActionMenuButton beforeBtn = mMenuButtons[j];
                beforeButtonsHeight += beforeBtn.getMeasuredHeight();
            }

            int hidePointLeft = baseMenuButtonX + Math.abs(toggleWidth - btnWidth) / 2;
            int hidePointRight = hidePointLeft + btnWidth;
            int hidePointTop = baseMenuButtonY + Math.abs(toggleHeight - btnHeight) / 2;
            int hidePointBottom =hidePointTop + btnHeight;

            int showPointLeft = hidePointLeft;
            int showPointTop = baseMenuButtonY - beforeButtonsHeight - btnHeight;

            mMenuButtonShowPoints[i] = new PointF(showPointLeft, showPointTop);
            mMenuButtonHidePoints[i] = new PointF(hidePointLeft, hidePointTop);

            btn.layout(hidePointLeft, hidePointTop, hidePointRight, hidePointBottom);
        }
    }

    public void toggle() {
        if (mToggle) {
            hidden(true);
        } else {
            show(true);
        }
        mToggle = !mToggle;
    }

    protected void show(boolean isAnimation) {
        final int count = mMenuButtons.length;
        mToggleButton.changeZDepth(ZDepth.Depth4);

        if (!isAnimation) {
            for (int i = 0; i < count; i++) {
                FloatingActionMenuButton btn = mMenuButtons[i];
                btn.setAlpha(1.0f);
                btn.setX(mMenuButtonShowPoints[i].x);
                btn.setY(mMenuButtonShowPoints[i].y);
            }
            return;
        }

        for (int i = 0; i < count; i++) {
            FloatingActionMenuButton btn = mMenuButtons[i];
            btn
                    .animate()
                    .alpha(1.0f)
                    .x(mMenuButtonShowPoints[i].x)
                    .y(mMenuButtonShowPoints[i].y)
                    .setDuration(160 + 20 * i)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .start();
        }
    }

    protected void hidden(boolean isAnimation) {
        final int count = mMenuButtons.length;
        mToggleButton.changeZDepth(ZDepth.Depth2);

        if (!isAnimation) {
            for (int i = 0; i < count; i++) {
                FloatingActionMenuButton btn = mMenuButtons[i];
                btn.setAlpha(0.0f);
                btn.setX(mMenuButtonHidePoints[i].x);
                btn.setY(mMenuButtonHidePoints[i].y);
            }
            return;
        }

        for (int i = 0; i < count; i++) {
            FloatingActionMenuButton btn = mMenuButtons[i];
            btn
                    .animate()
                    .alpha(0.0f)
                    .x(mMenuButtonHidePoints[i].x)
                    .y(mMenuButtonHidePoints[i].y)
                    .setDuration(160 + 20 * i)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .start();
        }
    }
}
