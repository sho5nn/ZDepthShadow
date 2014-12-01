package app.mosn.zdepthshadowsample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import app.mosn.library.ZDepth;
import app.mosn.library.ZDepthShadowLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ChangeZDepthActivity extends ActionBarActivity {

    @InjectView(R.id.zDepthShadowLayout)
    ZDepthShadowLayout mZDepthShadowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_zdepth);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.button_zDepth_0)
    protected void onClickZDepth0() {
        mZDepthShadowLayout.changeZDepth(ZDepth.Depth0);
    }

    @OnClick(R.id.button_zDepth_1)
    protected void onClickZDepth1() {
        mZDepthShadowLayout.changeZDepth(ZDepth.Depth1);
    }

    @OnClick(R.id.button_zDepth_2)
    protected void onClickZDepth2() {
        mZDepthShadowLayout.changeZDepth(ZDepth.Depth2);
    }

    @OnClick(R.id.button_zDepth_3)
    protected void onClickZDepth3() {
        mZDepthShadowLayout.changeZDepth(ZDepth.Depth3);
    }

    @OnClick(R.id.button_zDepth_4)
    protected void onClickZDepth4() {
        mZDepthShadowLayout.changeZDepth(ZDepth.Depth4);
    }

    @OnClick(R.id.button_zDepth_5)
    protected void onClickZDepth5() {
        mZDepthShadowLayout.changeZDepth(ZDepth.Depth5);
    }
}
