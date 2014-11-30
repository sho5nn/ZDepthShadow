package app.mosn.library;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;


import app.mosn.library.shadow.Shadow;
import app.mosn.library.shadow.ShadowOval;
import app.mosn.library.shadow.ShadowRect;


public class ShadowView extends View {
    protected static final String TAG = "ShadowView";

    protected static final String ANIM_PROPERTY_NAME_ALPHA_ABOVE  = "alphaAbove";
    protected static final String ANIM_PROPERTY_NAME_ALPHA_BELOW  = "alphaBelow";
    protected static final String ANIM_PROPERTY_NAME_OFFSET_ABOVE = "offsetAbove";
    protected static final String ANIM_PROPERTY_NAME_OFFSET_BELOW = "offsetBelow";
    protected static final String ANIM_PROPERTY_NAME_BLUR_ABOVE   = "blurAbove";
    protected static final String ANIM_PROPERTY_NAME_BLUR_BELOW   = "blurBelow";

    protected static final int DEFAULT_ATTR_SHAPE = 0;
    protected static final int DEFAULT_ATTR_ZDEPTH = 1;
    protected static final int DEFAULT_ATTR_ZDEPTH_PADDING = 5;

    public static final int SHAPE_RECT = 0;
    public static final int SHAPE_OVAL = 1;

    protected Shadow mShadow;
    protected ZDepthParam mZDepthParam;
    protected int mZDepthPadding;

    protected int mAttrShape;
    protected int mAttrZDepth;
    protected int mAttrZDepthPadding;


    protected ShadowView(Context context) {
        super(context);
        init(null, 0);
    }

    protected ShadowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    protected ShadowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    protected void init(AttributeSet attrs, int defStyle) {
        setWillNotDraw(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        // Load attributes
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ZDepthShadow, defStyle, 0);
        mAttrShape = typedArray.getInt(R.styleable.ZDepthShadow_z_depth_shape, DEFAULT_ATTR_SHAPE);
        mAttrZDepth = typedArray.getInt(R.styleable.ZDepthShadow_z_depth, DEFAULT_ATTR_ZDEPTH);
        mAttrZDepthPadding = typedArray.getInt(R.styleable.ZDepthShadow_z_depth_padding, DEFAULT_ATTR_ZDEPTH_PADDING);

        setShape(mAttrShape);
        setZDepth(mAttrZDepth);
        setZDepthPadding(mAttrZDepthPadding);

        typedArray.recycle();
    }

    protected void setZDepthPadding(int zDepthPaddingValue) {
        ZDepth zDepth = getZDepthWithAttributeValue(zDepthPaddingValue);
        setZDepthPadding(zDepth);
    }

    protected void setZDepthPadding(ZDepth zDepth) {
        float maxAboveBlurRadius = zDepth.getBlurRadiusAbovePx(getContext());
        float maxAboveOffset     = zDepth.getOffsetYAbovePx(getContext());
        float maxBelowBlurRadius = zDepth.getBlurRadiusBelowPx(getContext());
        float maxBelowOffset     = zDepth.getOffsetYBelowPx(getContext());

        float maxAboveSize = maxAboveBlurRadius + maxAboveOffset;
        float maxBelowSize = maxBelowBlurRadius + maxBelowOffset;

        mZDepthPadding = (int) Math.max(maxAboveSize, maxBelowSize);
    }

    protected int getZDepthPadding() {
        return mZDepthPadding;
    }

    protected void setShape(int shape) {
        switch (shape) {
            case SHAPE_RECT:
                mShadow = new ShadowRect();
                break;

            case SHAPE_OVAL:
                mShadow = new ShadowOval();
                break;

            default:
                throw new IllegalArgumentException("unknown shape value.");
        }
    }

    protected void setZDepth(int zDepthValue) {
        ZDepth zDepth = getZDepthWithAttributeValue(zDepthValue);
        setZDepth(zDepth);
    }

    protected void setZDepth(ZDepth zDepth) {
        mZDepthParam = new ZDepthParam();
        mZDepthParam.initZDepth(getContext(), zDepth);
    }

    private ZDepth getZDepthWithAttributeValue(int zDepthValue) {
        switch (zDepthValue) {
            case 0: return ZDepth.Depth0;
            case 1: return ZDepth.Depth1;
            case 2: return ZDepth.Depth2;
            case 3: return ZDepth.Depth3;
            case 4: return ZDepth.Depth4;
            case 5: return ZDepth.Depth5;
            default: throw new IllegalArgumentException("unknown zDepth value.");
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);

        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);

        switch (wMode) {
            case MeasureSpec.EXACTLY:
                // NOP
                break;

            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                wSize = 0;
                break;
        }

        switch (hMode) {
            case MeasureSpec.EXACTLY:
                // NOP
                break;

            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                hSize = 0;
                break;
        }

        super.onMeasure(
                MeasureSpec.makeMeasureSpec(wSize, wMode),
                MeasureSpec.makeMeasureSpec(hSize, hMode));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int parentWidth  = (right - left);
        int parentHeight = (bottom - top);

        mShadow.setParameter(mZDepthParam,
                mZDepthPadding,
                mZDepthPadding,
                parentWidth  - mZDepthPadding,
                parentHeight - mZDepthPadding);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mShadow.onDraw(canvas);
    }

    protected void changeZDepth(ZDepth zDepth) {

        PropertyValuesHolder alphaAboveHolder  = PropertyValuesHolder
                .ofInt(ANIM_PROPERTY_NAME_ALPHA_ABOVE, mZDepthParam.mColorAlphaShadowAbove, zDepth.getColorAlphaShadowAbove());
        PropertyValuesHolder alphaBelowHolder  = PropertyValuesHolder
                .ofInt(ANIM_PROPERTY_NAME_ALPHA_BELOW, mZDepthParam.mColorAlphaShadowBelow, zDepth.getColorAlphaShadowBelow());
        PropertyValuesHolder offsetAboveHolder = PropertyValuesHolder
                .ofFloat(ANIM_PROPERTY_NAME_OFFSET_ABOVE, mZDepthParam.mOffsetYAbovePx, zDepth.getOffsetYAbovePx(getContext()));
        PropertyValuesHolder offsetBelowHolder = PropertyValuesHolder
                .ofFloat(ANIM_PROPERTY_NAME_OFFSET_BELOW, mZDepthParam.mOffsetYBelowPx, zDepth.getOffsetYBelowPx(getContext()));
        PropertyValuesHolder blurAboveHolder   = PropertyValuesHolder
                .ofFloat(ANIM_PROPERTY_NAME_BLUR_ABOVE, mZDepthParam.mBlurRadiusAbovePx, zDepth.getBlurRadiusAbovePx(getContext()));
        PropertyValuesHolder blurBelowHolder   = PropertyValuesHolder
                .ofFloat(ANIM_PROPERTY_NAME_BLUR_BELOW, mZDepthParam.mBlurRadiusBelowPx, zDepth.getBlurRadiusBelowPx(getContext()));

        ValueAnimator anim = ValueAnimator
                .ofPropertyValuesHolder(
                        alphaAboveHolder, alphaBelowHolder,
                        offsetAboveHolder, offsetBelowHolder,
                        blurAboveHolder, blurBelowHolder)
                .setDuration(150);
        anim.setInterpolator(new LinearInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int   alphaAbove  = (Integer) animation.getAnimatedValue(ANIM_PROPERTY_NAME_ALPHA_ABOVE);
                int   alphaBelow  = (Integer) animation.getAnimatedValue(ANIM_PROPERTY_NAME_ALPHA_BELOW);
                float offsetAbove = (Float) animation.getAnimatedValue(ANIM_PROPERTY_NAME_OFFSET_ABOVE);
                float offsetBelow = (Float) animation.getAnimatedValue(ANIM_PROPERTY_NAME_OFFSET_BELOW);
                float blurAbove   = (Float) animation.getAnimatedValue(ANIM_PROPERTY_NAME_BLUR_ABOVE);
                float blurBelow   = (Float) animation.getAnimatedValue(ANIM_PROPERTY_NAME_BLUR_BELOW);

                mZDepthParam.mColorAlphaShadowAbove = alphaAbove;
                mZDepthParam.mColorAlphaShadowBelow = alphaBelow;
                mZDepthParam.mOffsetYAbovePx = offsetAbove;
                mZDepthParam.mOffsetYBelowPx = offsetBelow;
                mZDepthParam.mBlurRadiusAbovePx = blurAbove;
                mZDepthParam.mBlurRadiusBelowPx = blurBelow;

                mShadow.setParameter(mZDepthParam, mZDepthPadding, mZDepthPadding, getWidth() - mZDepthPadding, getHeight() - mZDepthPadding); 

                invalidate();
             }
         });
        anim.start();
    }
}
