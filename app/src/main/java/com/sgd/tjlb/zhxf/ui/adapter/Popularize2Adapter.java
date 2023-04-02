package com.sgd.tjlb.zhxf.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.entity.PopularizeData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Popularize2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   private List<PopularizeData> mDatas = new ArrayList<>();

   @NonNull
   @Override
   public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popularize, parent, false);
      return new PopularizeViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

   }

   @Override
   public int getItemCount() {
      return mDatas.size();
   }

   public void initData(List<PopularizeData> tempDatas){
      if (tempDatas != null){
         this.mDatas = tempDatas;
      }
      notifyDataSetChanged();
   }

   private static final class PopularizeViewHolder extends RecyclerView.ViewHolder{

      public PopularizeViewHolder(@NonNull View itemView) {
         super(itemView);
      }
   }
}
