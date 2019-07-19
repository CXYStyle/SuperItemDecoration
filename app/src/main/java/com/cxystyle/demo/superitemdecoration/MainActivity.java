package com.cxystyle.demo.superitemdecoration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

  private RadioGroup mRg1;
  private RadioGroup mRg2;
  private RadioGroup mRg3;
  private RadioGroup mRg4;

  private AppCompatButton btn;

  protected int mLayoutManager = 1;
  protected int mMode = 1;
  protected int mOrientation = RecyclerView.VERTICAL;
  protected boolean mReverse = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mRg1 = findViewById(R.id.rg1);
    mRg2 = findViewById(R.id.rg2);
    mRg3 = findViewById(R.id.rg3);
    mRg4 = findViewById(R.id.rg4);

    mRg1.setOnCheckedChangeListener(this);
    mRg2.setOnCheckedChangeListener(this);
    mRg3.setOnCheckedChangeListener(this);
    mRg4.setOnCheckedChangeListener(this);

    btn = findViewById(R.id.btn);
    btn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, DemoActivity.class);
        intent.putExtra("layoutmanager", mLayoutManager);
        intent.putExtra("mode", mMode);
        intent.putExtra("orientation", mOrientation);
        intent.putExtra("reverse", mReverse);
        startActivity(intent);
      }
    });

  }

  @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
    switch (checkedId) {
      case R.id.linear:
        mLayoutManager = 1;
        break;
      case R.id.grid:
        mLayoutManager = 2;
        break;
      case R.id.divide:
        mMode = 1;
        break;
      case R.id.section:
        mMode = 2;
        break;
      case R.id.vertical:
        mOrientation = RecyclerView.VERTICAL;
        break;
      case R.id.horizontal:
        mOrientation = RecyclerView.HORIZONTAL;
        break;
      case R.id.istrue:
        mReverse = true;
        break;
      case R.id.isfalse:
        mReverse = false;
        break;
      default:
        break;
    }
  }
}
