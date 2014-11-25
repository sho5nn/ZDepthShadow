package app.mosn.library;

import aurelienribon.tweenengine.TweenAccessor;

public class ZDepthParamAccessor implements TweenAccessor<ZDepthParam> {

    public static final int TWEEN_COLOR  = 1;
    public static final int TWEEN_OFFSET = 2;
    public static final int TWEEN_BLUR   = 3;

    @Override
    public int getValues(ZDepthParam target, int tweenType, float[] returnValues) {

        switch (tweenType) {
            case TWEEN_COLOR:
                returnValues[0] = target.mColorAlphaShadowAbove;
                returnValues[1] = target.mColorAlphaShadowBelow;
                return 2;

            case TWEEN_OFFSET:
                returnValues[0] = target.mOffsetYAbovePx;
                returnValues[1] = target.mOffsetYBelowPx;
                return 2;

            case TWEEN_BLUR:
                returnValues[0] = target.mBlurRadiusAbovePx;
                returnValues[1] = target.mBlurRadiusBelowPx;
                return 2;

            default: return -1;
        }
    }

    @Override
    public void setValues(ZDepthParam target, int tweenType, float[] newValues) {

        switch (tweenType) {
            case TWEEN_COLOR:
                target.mColorAlphaShadowAbove = (int) newValues[0];
                target.mColorAlphaShadowBelow = (int) newValues[1];
                break;

            case TWEEN_OFFSET:
                target.mOffsetYAbovePx = newValues[0];
                target.mOffsetYBelowPx = newValues[1];
                break;

            case TWEEN_BLUR:
                target.mBlurRadiusAbovePx = newValues[0];
                target.mBlurRadiusBelowPx = newValues[1];
                break;

            default: break;
        }
    }
}
