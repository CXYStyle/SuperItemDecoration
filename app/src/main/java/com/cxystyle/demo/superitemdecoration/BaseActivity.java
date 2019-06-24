package com.cxystyle.demo.superitemdecoration;

import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.IntDef;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {

    switch (item.getItemId()){
      case android.R.id.home:
        finish();
        break;
        default:
          break;
    }

    return super.onOptionsItemSelected(item);
  }
}
