package app.mosn.library;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import app.mosn.library.shadow.Shadow;
import app.mosn.library.shadow.ShadowOval;
import app.mosn.library.shadow.ShadowRect;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Linear;


public class ShadowView extends View {
    protected static final String TAG = "ShadowView";

    public static final int SHAPE_RECT = 0;
    public static final int SHAPE_OVAL = 1;

    public static final int DEFAULT_ATTR_SHAPE = 0;
    public static final int DEFAULT_ATTR_ZDEPTH = 1;
    public static final int DEFAULT_ATTR_ZDEPTH_PADDING = 5;

    protected Shadow mShadow;
    protected ZDepthParam mZDepthParam;
    protected int mZDepthPadding;

    protected int mAttrShape;
    protected int mAttrZDepth;
    protected int mAttrZDepthPadding;

    private TweenManager mTweenManager;

    boolean isZDepthAnimating;

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

        mTweenManager = new TweenManager();
        Tween.registerAccessor(ZDepthParam.class, new ZDepthParamAccessor());

        // Load attributes
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ZDepthShadow, defStyle, 0);
        mAttrShape = typedArray.getInt(R.styleable.ZDepthShadow_shape, DEFAULT_ATTR_SHAPE);
        mAttrZDepth = typedArray.getInt(R.styleable.ZDepthShadow_z_depth, DEFAULT_ATTR_ZDEPTH);
        mAttrZDepthPadding = typedArray.getInt(R.styleable.ZDepthShadow_z_depth_padding, DEFAULT_ATTR_ZDEPTH_PADDING);

        initShape(mAttrShape);
        initZDepth(mAttrZDepth);
        initZDepthPadding(mAttrZDepthPadding);

        typedArray.recycle();
    }

    protected void initZDepthPadding(int zDepthPaddingValue) {
        ZDepth zDepth = getZDepthWithAttributeValue(zDepthPaddingValue);
        initZDepthPadding(zDepth);
    }

    protected void initZDepthPadding(ZDepth zDepth) {
        float maxAboveBlurRadius = zDepth.getBlurRadiusAbovePx(getContext());
        float maxAboveOffset     = zDepth.getOffsetYAbovePx(getContext());
        float maxBelowBlurRadius = zDepth.getBlurRadiusBelowPx(getContext());
        float maxBelowOffset     = zDepth.getOffsetYBelowPx(getContext());

        float maxAboveSize = maxAboveBlurRadius + maxAboveOffset;
        float maxBelowSize = maxBelowBlurRadius + maxBelowOffset;

        mZDepthPadding = (int) Math.max(maxAboveSize, maxBelowSize);
    }

    protected void setZDepthPadding(ZDepth zDepth) {
        initZDepthPadding(zDepth);
    }

    protected int getZDepthPadding() {
        return mZDepthPadding;
    }

    protected void initShape(int shape) {
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

    protected void setShape(int shape) {
        initShape(shape);
    }

    protected void initZDepth(int zDepthValue) {
        ZDepth zDepth = getZDepthWithAttributeValue(zDepthValue);
        initZDepth(zDepth);
    }

    protected void initZDepth(ZDepth zDepth) {
        mZDepthParam = new ZDepthParam();
        mZDepthParam.initZDepth(getContext(), zDepth);
    }

    protected void setZDepth(int zDepthValue) {
        ZDepth zDepth = getZDepthWithAttributeValue(zDepthValue);
        setZDepth(zDepth);
    }

    protected void setZDepth(ZDepth zDepth) {
        initZDepth(zDepth);
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
                0 + mZDepthPadding,
                0 + mZDepthPadding,
                parentWidth  - mZDepthPadding,
                parentHeight - mZDepthPadding);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mShadow.onDraw(canvas);
    }

    protected void changeZDepth(final ZDepth zDepth) {

        Timeline.createParallel()
                .push(Tween.to(mZDepthParam, ZDepthParamAccessor.TWEEN_COLOR,  0.15f)
                        .target(zDepth.getColorAlphaShadowAbove(), zDepth.getColorAlphaShadowBelow())
                        .ease(Linear.INOUT))
                .push(Tween.to(mZDepthParam, ZDepthParamAccessor.TWEEN_OFFSET, 0.15f)
                        .target(zDepth.getOffsetYAbovePx(getContext()), zDepth.getOffsetYBelowPx(getContext()))
                        .ease(Linear.INOUT))
                .push(Tween.to(mZDepthParam, ZDepthParamAccessor.TWEEN_BLUR,   0.15f)
                        .target(zDepth.getBlurRadiusAbovePx(getContext()), zDepth.getBlurRadiusBelowPx(getContext()))
                        .ease(Linear.INOUT))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        Log.d(TAG, "call onEvent. Event:" + type);
                        if (type == TweenCallback.COMPLETE) {
                            Log.d(TAG, "Tween Event Complete.");
                            isZDepthAnimating = false;
                        }
                    }
                })
                .start(mTweenManager);

        isZDepthAnimating = true;

        // TODO refactoring
        new Thread(new Runnable() {
            private long lastMillis = -1;

            @Override
            public void run() {
                while (isZDepthAnimating) {
                    if (lastMillis > 0) {
                        long currentMillis = System.currentTimeMillis();
                        final float delta = (currentMillis - lastMillis) / 1000f;

                        ((Activity) getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTweenManager.update(delta);
                                // TODO tweenManager.update の後に ShapeDrawable を初期化して再描画する
                                mShadow.setParameter(mZDepthParam, 0 + mZDepthPadding, 0 + mZDepthPadding, getWidth() - mZDepthPadding, getHeight() - mZDepthPadding);
                                invalidate();
                            }
                        });

                        lastMillis = currentMillis;
                    } else {
                        lastMillis = System.currentTimeMillis();
                    }

                    try {
                        Thread.sleep(1000 / 60);
                    } catch (InterruptedException ex) {

                    }
                }
            }
        }).start();
    }
}
