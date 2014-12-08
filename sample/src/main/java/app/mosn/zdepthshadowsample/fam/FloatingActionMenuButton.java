package app.mosn.zdepthshadowsample.fam;

import android.content.Context;

import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;

import app.mosn.zdepthshadowlayout.ZDepthShadowLayout;
import app.mosn.zdepthshadowlayout.utils.DisplayUtils;
import app.mosn.zdepthshadowsample.R;

public class FloatingActionMenuButton extends ZDepthShadowLayout {

    protected int mIconId;
    protected int mButtonSizeDp = 40;
    protected int mIconSizeDp = 24;

    public FloatingActionMenuButton(Context context) {
        super(context);
        init(null, 0);
    }

    @Override
    protected void init(AttributeSet attrs, int defStyle) {
        super.init(attrs, defStyle);

        mAttrShape = SHAPE_OVAL;
        mAttrZDepth = 2;
        mAttrZDepthPaddingLeft = 3;
        mAttrZDepthPaddingTop = 3;
        mAttrZDepthPaddingRight = 3;
        mAttrZDepthPaddingBottom = 3;
        mAttrZDepthDoAnimation = true;
        mAttrZDepthAnimDuration = 150;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        // set Button LayoutParams
        int buttonSize = DisplayUtils.convertDpToPx(getContext(), mButtonSizeDp);
        int iconSize = DisplayUtils.convertDpToPx(getContext(), mIconSizeDp);

        setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        // set Background Circle
        ImageView bgImage = new ImageView(getContext());
        bgImage.setLayoutParams(new LayoutParams(buttonSize, buttonSize));
        bgImage.setImageResource(R.drawable.drawable_circle);
        addView(bgImage);

        // set Icon
        Drawable icon = getResources().getDrawable(mIconId);
        LayoutParams iconLP = new LayoutParams(iconSize, iconSize);
        iconLP.gravity = Gravity.CENTER;

        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(iconLP);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageDrawable(icon);
        addView(imageView);
    }

    protected void setIconId(int iconId) {
        mIconId = iconId;
    }
}
