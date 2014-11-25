package app.mosn.library.shadow;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;

import app.mosn.library.ZDepthParam;

public class ShadowRect implements Shadow {

    private ShapeDrawable mShadowAbove;
    private ShapeDrawable mShadowBelow;

    private Rect mRectShadowAbove;
    private Rect mRectShadowBelow;

    public ShadowRect() {
        mRectShadowAbove = new Rect();
        mRectShadowBelow = new Rect();
    }

    @Override
    public void setParameter(ZDepthParam parameter, int left, int top, int right, int bottom) {
        mRectShadowAbove.left   = left;
        mRectShadowAbove.top    = (int) (top    + parameter.mOffsetYAbovePx);
        mRectShadowAbove.right  = right;
        mRectShadowAbove.bottom = (int) (bottom + parameter.mOffsetYAbovePx);

        mRectShadowBelow.left   = left;
        mRectShadowBelow.top    = (int) (top    + parameter.mOffsetYBelowPx);
        mRectShadowBelow.right  = right;
        mRectShadowBelow.bottom = (int) (bottom + parameter.mOffsetYBelowPx);

        mShadowAbove = new ShapeDrawable(new RectShape());
        mShadowAbove.getPaint().setColor(Color.argb(parameter.mColorAlphaShadowAbove, 0, 0, 0));
        if (0 < parameter.mBlurRadiusAbovePx) mShadowAbove.getPaint().setMaskFilter(new BlurMaskFilter(parameter.mBlurRadiusAbovePx, BlurMaskFilter.Blur.NORMAL));

        mShadowBelow = new ShapeDrawable(new RectShape());
        mShadowBelow.getPaint().setColor(Color.argb(parameter.mColorAlphaShadowBelow, 0, 0, 0));
        if (0 < parameter.mBlurRadiusBelowPx) mShadowBelow.getPaint().setMaskFilter(new BlurMaskFilter(parameter.mBlurRadiusBelowPx, BlurMaskFilter.Blur.NORMAL));
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawRect(mRectShadowBelow, mShadowBelow.getPaint());
        canvas.drawRect(mRectShadowAbove, mShadowAbove.getPaint());
    }
}
