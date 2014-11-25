package app.mosn.library;

import android.content.Context;

import app.mosn.library.utils.DisplayUtils;

public enum ZDepth {

    Depth0( // TODO
            0,
            0,
            0,
            0,
            0,
            0
    ),

    Depth1(
            30, // alpha to black
            61, // alpha to black
            1.0f, // dp
            1.0f, // dp
            1.5f, // dp
            1.0f  // dp
    ),
    Depth2(
            40,
            58,
            3.0f,
            3.0f,
            3.0f,
            3.0f
    ),
    Depth3(
            48,
            58,
            10.0f,
            6.0f,
            10.0f,
            3.0f
    ),
    Depth4(
            64,
            56,
            14.0f,
            10.0f,
            14.0f,
            5.0f
    ),
    Depth5(
            76,
            56,
            19.0f,
            15.0f,
            19.0f,
            6.0f
    );

    public final int colorAlphaShadowAbove; // alpha to black
    public final int colorAlphaShadowBelow; // alpha to black

    public final float offsetYAbove; // dp
    public final float offsetYBelow; // dp

    public final float blurRadiusAbove; // dp
    public final float blurRadiusBelow; // dp

    private ZDepth(int colorAlphaShadowAbove, int colorAlphaShadowBelow, float offsetYAbove, float offsetYBelow, float blurRadiusAbove, float blurRadiusBelow) {
        this.colorAlphaShadowAbove = colorAlphaShadowAbove;
        this.colorAlphaShadowBelow = colorAlphaShadowBelow;
        this.offsetYAbove = offsetYAbove;
        this.offsetYBelow = offsetYBelow;
        this.blurRadiusAbove = blurRadiusAbove;
        this.blurRadiusBelow = blurRadiusBelow;
    }

    public int getColorAlphaShadowAbove() {
        return colorAlphaShadowAbove;
    }

    public int getColorAlphaShadowBelow() {
        return colorAlphaShadowBelow;
    }

    public float getOffsetYAbovePx(Context context) {
        return DisplayUtils.convertDpToPx(context, offsetYAbove);
    }

    public float getOffsetYBelowPx(Context context) {
        return DisplayUtils.convertDpToPx(context, offsetYBelow);
    }

    public float getBlurRadiusAbovePx(Context context) {
        return DisplayUtils.convertDpToPx(context, blurRadiusAbove);
    }

    public float getBlurRadiusBelowPx(Context context) {
        return DisplayUtils.convertDpToPx(context, blurRadiusBelow);
    }
}
