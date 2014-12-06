package app.mosn.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;


public class ZDepthShadowLayout extends FrameLayout {
    public static final String TAG = "ZDepthShadowLayout";

    protected static final int DEFAULT_ATTR_SHAPE = 0;
    protected static final int DEFAULT_ATTR_ZDEPTH = 1;
    protected static final int DEFAULT_ATTR_ZDEPTH_PADDING = 5;
    protected static final int DEFAULT_ATTR_ZDEPTH_ANIM_DURATION = 150;
    protected static final boolean DEFAULT_ATTR_ZDEPTH_DO_ANIMATION = true;

    protected static final int SHAPE_RECT = 0;
    protected static final int SHAPE_OVAL = 1;

    protected ShadowView mShadowView;

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

        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ZDepthShadow, defStyle, 0);
        mAttrShape = typedArray.getInt(R.styleable.ZDepthShadow_z_depth_shape, DEFAULT_ATTR_SHAPE);
        mAttrZDepth = typedArray.getInt(R.styleable.ZDepthShadow_z_depth, DEFAULT_ATTR_ZDEPTH);
        mAttrZDepthPadding = typedArray.getInt(R.styleable.ZDepthShadow_z_depth_padding, DEFAULT_ATTR_ZDEPTH_PADDING);
        mAttrZDepthAnimDuration = typedArray.getInt(R.styleable.ZDepthShadow_z_depth_animDuration, DEFAULT_ATTR_ZDEPTH_ANIM_DURATION);
        mAttrZDepthDoAnimation = typedArray.getBoolean(R.styleable.ZDepthShadow_z_depth_doAnim, DEFAULT_ATTR_ZDEPTH_DO_ANIMATION);
        typedArray.recycle();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        mShadowView = new ShadowView(getContext());
        mShadowView.setShape(mAttrShape);
        mShadowView.setZDepth(mAttrZDepth);
        mShadowView.setZDepthPadding(mAttrZDepthPadding);
        mShadowView.setZDepthAnimDuration(mAttrZDepthAnimDuration);
        mShadowView.setZDepthDoAnimation(mAttrZDepthDoAnimation);
        addView(mShadowView, 0);

        int padding = mShadowView.getZDepthPadding();
        setPadding(padding, padding, padding, padding);
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
        int padding = mShadowView.getZDepthPadding();
        maxChildViewWidth  += padding * 2; // 左右の padding を加算する
        maxChildViewHeight += padding * 2; // 上下の padding を加算する
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

    public int getWidthExceptShadow() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    public int getHeightExceptShadow() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    public void changeZDepth(ZDepth zDepth) {
        mShadowView.changeZDepth(zDepth);
    }
}
