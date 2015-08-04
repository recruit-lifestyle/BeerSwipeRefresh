package jp.co.recruit_lifestyle.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import jp.co.recruit_lifestyle.android.widget.BeerSwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {

  private ListView mListview;

  private BeerSwipeRefreshLayout mBeerSwipeRefreshLayout;

  @Override protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initView();
    setSampleData();
  }

  private void initView() {
    mBeerSwipeRefreshLayout = (BeerSwipeRefreshLayout) findViewById(R.id.main_beer);
    mBeerSwipeRefreshLayout.setOnRefreshListener(new BeerSwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        //Toast.makeText(getApplicationContext(), "onRefresh", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
          @Override public void run() {
            mBeerSwipeRefreshLayout.setRefreshing(false);
            //Toast.makeText(getApplicationContext(), "setRefreshing(false)", Toast.LENGTH_SHORT).show();
          }
        }, 7000);
      }
    });
    mListview = (ListView) findViewById(R.id.main_list);
  }

  private void setSampleData() {
    ArrayList<String> sampleArrayStr = new ArrayList<>();
    for (int i = 0; i < 60; i++) {
      sampleArrayStr.add("");
    }
    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, sampleArrayStr);
    mListview.setAdapter(adapter);
  }
}
