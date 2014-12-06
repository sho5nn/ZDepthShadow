package app.mosn.library.shadow;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;

import app.mosn.library.ZDepthParam;

public class ShadowRect implements Shadow {

    private ShapeDrawable mTopShadow;
    private ShapeDrawable mBottomShadow;

    private Rect mRectTopShadow;
    private Rect mRectBottomShadow;

    public ShadowRect() {
        mRectTopShadow = new Rect();
        mRectBottomShadow = new Rect();
        mTopShadow = new ShapeDrawable(new RectShape());
        mBottomShadow = new ShapeDrawable(new RectShape());
    }

    @Override
    public void setParameter(ZDepthParam param, int left, int top, int right, int bottom) {
        mRectTopShadow.left   = left;
        mRectTopShadow.top    = (int) (top    + param.mOffsetYTopShadowPx);
        mRectTopShadow.right  = right;
        mRectTopShadow.bottom = (int) (bottom + param.mOffsetYTopShadowPx);

        mRectBottomShadow.left   = left;
        mRectBottomShadow.top    = (int) (top    + param.mOffsetYBottomShadowPx);
        mRectBottomShadow.right  = right;
        mRectBottomShadow.bottom = (int) (bottom + param.mOffsetYBottomShadowPx);

        mTopShadow.getPaint().setColor(Color.argb(param.mColorAlphaTopShadow, 0, 0, 0));
        if (0 < param.mBlurRadiusTopShadowPx) {
            mTopShadow.getPaint().setMaskFilter(new BlurMaskFilter(param.mBlurRadiusTopShadowPx, BlurMaskFilter.Blur.NORMAL));
        } else {
            mTopShadow.getPaint().setMaskFilter(null);
        }

        mBottomShadow.getPaint().setColor(Color.argb(param.mColorAlphaBottomShadow, 0, 0, 0));
        if (0 < param.mBlurRadiusBottomShadowPx) {
            mBottomShadow.getPaint().setMaskFilter(new BlurMaskFilter(param.mBlurRadiusBottomShadowPx, BlurMaskFilter.Blur.NORMAL));
        } else {
            mBottomShadow.getPaint().setMaskFilter(null);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawRect(mRectBottomShadow, mBottomShadow.getPaint());
        canvas.drawRect(mRectTopShadow, mTopShadow.getPaint());
    }
}
