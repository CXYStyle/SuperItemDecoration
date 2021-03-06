package com.cxystyle.demo.superitemdecoration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> {

  private int layoutId;

  private List<Integer> datas;

  public SimpleAdapter(@LayoutRes int layoutId, List<Integer> datas) {
    this.layoutId = layoutId;
    this.datas = datas;
  }

  @NonNull @Override
  public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new SimpleViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
  }

  @Override public int getItemCount() {
    return datas.size();
  }

  @Override public void onBindViewHolder(@NonNull SimpleViewHolder holder, final int position) {
    holder.tv.setText(String.valueOf(datas.get(position)));

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Toast.makeText(v.getContext(), "click item: " + position, Toast.LENGTH_SHORT).show();
      }
    });

  }

  public class SimpleViewHolder extends RecyclerView.ViewHolder{
    TextView tv;
    public SimpleViewHolder(@NonNull View itemView) {
      super(itemView);
      tv = itemView.findViewById(R.id.tv);
    }
  }


}
