package com.slider;

import android.app.Activity;
import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.slider.SlipReAdapter.ISlipClickAction;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

  RecyclerView mRecyclerView;
  List<String> data;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mRecyclerView = findViewById(R.id.recycler);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    mRecyclerView.setHasFixedSize(true);

    data = new ArrayList<>();
    data.add("会场1");
    data.add("会场2");
    data.add("会场3");
    data.add("会场4");
    data.add("会场5");

    SlipReAdapter.Builder builder = new SlipReAdapter.Builder()
        .setAdapter(new Adapter(this, data))
        .setISlipClickAction(new ISlipClickAction() {
          @Override
          public void onAction(int position) {
            data.remove(position);
          }
        })
        .setMode(SlipReAdapter.MODE_DELETE)
        .setSlipViewId(R.layout.item_remove);
    mRecyclerView.setAdapter(builder.build());
  }
}
