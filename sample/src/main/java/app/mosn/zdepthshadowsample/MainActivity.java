package app.mosn.zdepthshadowsample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.listView)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                new String[]{
                        "Simple ZDepthShadowLayout",
                        "Change ZDepth",
                        "OnClick",
                        "with RippleEffect"
                }));
    }

    @OnItemClick(R.id.listView)
    protected void onItemClick(int position) {
        switch (position) {
            case 0:
                break;

            case 1:
                break;

            case 2:
                break;

            case 3:
                break;
        }
    }
}
