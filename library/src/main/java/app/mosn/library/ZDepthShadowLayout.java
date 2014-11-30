package app.mosn.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;


public class ZDepthShadowLayout extends FrameLayout {
    public static final String TAG = "ZDepthShadowLayout";

    protected ShadowView mShadowView;
    protected int mShadowZDepthPadding;

    protected int mAttrShape;
    protected int mAttrZDepth;
    protected int mAttrZDepthPadding;
    protected long mAttrZDepthAnimDuration;
    protected boolean mAttrZDepthDoAnimation;

    public ZDepthShadowLayout(Context context) {
        this(context, null);
    }

    public ZDepthShadowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZDepthShadowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    protected void init(AttributeSet attrs, int defStyle) {
        setClipToPadding(false);

        // Load attributes
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ZDepthShadow, defStyle, 0);
        mAttrShape = typedArray.getInt(R.styleable.ZDepthShadow_z_depth_shape, ShadowView.DEFAULT_ATTR_SHAPE);
        mAttrZDepth = typedArray.getInt(R.styleable.ZDepthShadow_z_depth, ShadowView.DEFAULT_ATTR_ZDEPTH);
        mAttrZDepthPadding = typedArray.getInt(R.styleable.ZDepthShadow_z_depth_padding, ShadowView.DEFAULT_ATTR_ZDEPTH_PADDING);
        mAttrZDepthAnimDuration = typedArray.getInt(R.styleable.ZDepthShadow_z_depth_animDuration, ShadowView.DEFAULT_ATTR_ZDEPTH_ANIM_DURATION);
        mAttrZDepthDoAnimation = typedArray.getBoolean(R.styleable.ZDepthShadow_z_depth_doAnim, ShadowView.DEFAULT_ATTR_ZDEPTH_DO_ANIMATION);
        typedArray.recycle();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (getChildCount() != 0 && getChildAt(0) instanceof ShadowView) {
            mShadowView = (ShadowView) getChildAt(0);
        } else {
            mShadowView = new ShadowView(getContext());
            mShadowView.setShape(mAttrShape);
            mShadowView.setZDepth(mAttrZDepth);
            mShadowView.setZDepthPadding(mAttrZDepthPadding);
            mShadowView.setZDepthAnimDuration(mAttrZDepthAnimDuration);
            mShadowView.setZDepthDoAnimation(mAttrZDepthDoAnimation);
            addView(mShadowView, 0);
        }

        mShadowZDepthPadding = mShadowView.getZDepthPadding();
        setPadding(mShadowZDepthPadding, mShadowZDepthPadding, mShadowZDepthPadding, mShadowZDepthPadding);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int childCount = getChildCount();

        int maxChildViewWidth = 0;
        int maxChildViewHeight = 0;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (maxChildViewWidth  < child.getMeasuredWidth())  maxChildViewWidth  = child.getMeasuredWidth();
            if (maxChildViewHeight < child.getMeasuredHeight()) maxChildViewHeight = child.getMeasuredHeight();
        }

        // その他の View のうち最も大きいサイズに padding を足して measure を呼び出す
        maxChildViewWidth  += mShadowZDepthPadding * 2; // 左右の padding を加算する
        maxChildViewHeight += mShadowZDepthPadding * 2; // 上下の padding を加算する
        mShadowView.measure(
                MeasureSpec.makeMeasureSpec(maxChildViewWidth,  MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(maxChildViewHeight, MeasureSpec.EXACTLY)
        );
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int width  = right - left;
        int height = bottom - top;
        mShadowView.layout(0, 0, width, height);
    }

    public int getWidthWithoutShadow() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    public int getHeightWithoutShadow() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    public void setShape(int shape) {
        mShadowView.setShape(shape);
        invalidate();
    }

    public void setZDepth(ZDepth zDepth) {
        mShadowView.setZDepth(zDepth);
        invalidate();
    }

    public void setZDepthPadding(ZDepth zDepth) {
        mShadowView.setZDepthPadding(zDepth);
        invalidate();
    }

    public void setZDepthAnimDuration(long duration) {
        mShadowView.setZDepthAnimDuration(duration);
    }

    public void changeZDepth(ZDepth zDepth) {
        mShadowView.changeZDepth(zDepth);
    }
}
