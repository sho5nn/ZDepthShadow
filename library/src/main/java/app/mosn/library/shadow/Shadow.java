package app.mosn.library.shadow;

import android.graphics.Canvas;

import app.mosn.library.ZDepthParam;


public interface Shadow {
    public void setParameter(ZDepthParam parameter, int left, int top, int right, int bottom);
    public void onDraw(Canvas canvas);
}
