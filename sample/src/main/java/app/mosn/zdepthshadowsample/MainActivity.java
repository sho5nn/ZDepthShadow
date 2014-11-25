package app.mosn.zdepthshadowsample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import app.mosn.library.ZDepth;
import app.mosn.library.ZDepthShadowLayout;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ZDepthShadowLayout view1 = (ZDepthShadowLayout) findViewById(R.id.shadowView_1);
        final ZDepthShadowLayout view2 = (ZDepthShadowLayout) findViewById(R.id.shadowView_2);
        final ZDepthShadowLayout view3 = (ZDepthShadowLayout) findViewById(R.id.shadowView_3);

        findViewById(R.id.button_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view1.changeZDepth(ZDepth.Depth1);
                view2.changeZDepth(ZDepth.Depth1);
                view3.changeZDepth(ZDepth.Depth1);
            }
        });

        findViewById(R.id.button_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view1.changeZDepth(ZDepth.Depth2);
                view2.changeZDepth(ZDepth.Depth2);
                view3.changeZDepth(ZDepth.Depth2);
            }
        });

        findViewById(R.id.button_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view1.changeZDepth(ZDepth.Depth3);
                view2.changeZDepth(ZDepth.Depth3);
                view3.changeZDepth(ZDepth.Depth3);
            }
        });

        findViewById(R.id.button_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view1.changeZDepth(ZDepth.Depth4);
                view2.changeZDepth(ZDepth.Depth4);
                view3.changeZDepth(ZDepth.Depth4);
            }
        });

        findViewById(R.id.button_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view1.changeZDepth(ZDepth.Depth5);
                view2.changeZDepth(ZDepth.Depth5);
                view3.changeZDepth(ZDepth.Depth5);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
