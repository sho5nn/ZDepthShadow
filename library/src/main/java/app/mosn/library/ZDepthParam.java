package app.mosn.library;

import android.content.Context;
import android.graphics.Color;


public class ZDepthParam {

    public int mColorAlphaTopShadow; // alpha to black
    public int mColorAlphaBottomShadow; // alpha to black

    public float mOffsetYTopShadowPx; // px
    public float mOffsetYBottomShadowPx; // px

    public float mBlurRadiusTopShadowPx; // px
    public float mBlurRadiusBottomShadowPx; // px

    public void initZDepth(Context context, ZDepth zDepth) {
        mColorAlphaTopShadow = zDepth.getColorAlphaTopShadow();
        mColorAlphaBottomShadow = zDepth.getColorAlphaBottomShadow();
        mOffsetYTopShadowPx = zDepth.getOffsetYTopShadowPx(context);
        mOffsetYBottomShadowPx = zDepth.getOffsetYBottomShadowPx(context);
        mBlurRadiusTopShadowPx = zDepth.getBlurTopShadowPx(context);
        mBlurRadiusBottomShadowPx = zDepth.getBlurBottomShadowPx(context);
    }

    public int getColorTopShadow() {
        return Color.argb(mColorAlphaTopShadow, 0, 0, 0);
    }

    public int getColorBottomShadow() {
        return Color.argb(mColorAlphaBottomShadow, 0, 0, 0);
    }
}
