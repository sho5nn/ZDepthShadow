package app.mosn.library.shadow;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

import app.mosn.library.ZDepthParam;

public class ShadowOval implements Shadow {

    private ShapeDrawable mShadowAbove;
    private ShapeDrawable mShadowBelow;

    private RectF mRectShadowAbove;
    private RectF mRectShadowBelow;

    public ShadowOval() {
        mRectShadowAbove = new RectF();
        mRectShadowBelow = new RectF();
    }

    @Override
    public void setParameter(ZDepthParam parameter, int left, int top, int right, int bottom) {
        mRectShadowAbove.left   = left;
        mRectShadowAbove.top    = top    + parameter.mOffsetYAbovePx;
        mRectShadowAbove.right  = right;
        mRectShadowAbove.bottom = bottom + parameter.mOffsetYAbovePx;

        mRectShadowBelow.left   = left;
        mRectShadowBelow.top    = top    + parameter.mOffsetYBelowPx;
        mRectShadowBelow.right  = right;
        mRectShadowBelow.bottom = bottom + parameter.mOffsetYBelowPx;

        mShadowAbove = new ShapeDrawable(new OvalShape());
        mShadowAbove.getPaint().setColor(Color.argb(parameter.mColorAlphaShadowAbove, 0, 0, 0));
        if (0 < parameter.mBlurRadiusAbovePx) mShadowAbove.getPaint().setMaskFilter(new BlurMaskFilter(parameter.mBlurRadiusAbovePx, BlurMaskFilter.Blur.NORMAL));

        mShadowBelow = new ShapeDrawable(new OvalShape());
        mShadowBelow.getPaint().setColor(Color.argb(parameter.mColorAlphaShadowBelow, 0, 0, 0));
        if (0 < parameter.mBlurRadiusBelowPx) mShadowBelow.getPaint().setMaskFilter(new BlurMaskFilter(parameter.mBlurRadiusBelowPx, BlurMaskFilter.Blur.NORMAL));
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawOval(mRectShadowBelow, mShadowBelow.getPaint());
        canvas.drawOval(mRectShadowAbove, mShadowAbove.getPaint());
    }
}
