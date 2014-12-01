package app.mosn.zdepthshadowsample;

import android.content.Intent;
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
        Intent intent = null;

        switch (position) {
            case 0:
                intent = new Intent(this, SimpleZDepthActivity.class);
                break;

            case 1:
                intent = new Intent(this, ChangeZDepthActivity.class);
                break;

            case 2:
                break;

            case 3:
                break;

            default:
                return;
        }

        startActivity(intent);
    }
}
