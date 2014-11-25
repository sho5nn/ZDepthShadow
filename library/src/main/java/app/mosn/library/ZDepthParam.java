package app.mosn.library;

import android.content.Context;
import android.graphics.Color;


public class ZDepthParam {

    public int mColorAlphaShadowAbove; // alpha to black
    public int mColorAlphaShadowBelow; // alpha to black

    public float mOffsetYAbovePx; // px
    public float mOffsetYBelowPx; // px

    public float mBlurRadiusAbovePx; // px
    public float mBlurRadiusBelowPx; // px

    public void initZDepth(Context context, ZDepth zDepth) {
        mColorAlphaShadowAbove = zDepth.getColorAlphaShadowAbove();
        mColorAlphaShadowBelow = zDepth.getColorAlphaShadowBelow();
        mOffsetYAbovePx = zDepth.getOffsetYAbovePx(context);
        mOffsetYBelowPx = zDepth.getOffsetYBelowPx(context);
        mBlurRadiusAbovePx = zDepth.getBlurRadiusAbovePx(context);
        mBlurRadiusBelowPx = zDepth.getBlurRadiusBelowPx(context);
    }

    public int getColorShadowAbove() {
        return Color.argb(mColorAlphaShadowAbove, 0, 0, 0);
    }

    public int getColorShadowBelow() {
        return Color.argb(mColorAlphaShadowBelow, 0, 0, 0);
    }
}
