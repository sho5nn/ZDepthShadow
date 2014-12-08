package app.mosn.zdepthshadowsample.fam;

import android.content.Context;
import android.util.AttributeSet;

public class FloatingActionMenuToggle extends FloatingActionMenuButton {

    public FloatingActionMenuToggle(Context context) {
        super(context);
    }

    @Override
    protected void init(AttributeSet attrs, int defStyle) {
        super.init(attrs, defStyle);

        mButtonSizeDp = 56;
        mAttrShape = SHAPE_OVAL;
        mAttrZDepth = 2;
        mAttrZDepthPaddingLeft = 4;
        mAttrZDepthPaddingTop = 4;
        mAttrZDepthPaddingRight = 4;
        mAttrZDepthPaddingBottom = 4;
        mAttrZDepthDoAnimation = true;
        mAttrZDepthAnimDuration = 150;
    }
}
