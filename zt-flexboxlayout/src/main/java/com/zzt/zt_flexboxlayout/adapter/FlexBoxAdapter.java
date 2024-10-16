package com.zzt.zt_flexboxlayout.adapter;


import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.zzt.zt_flexboxlayout.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zeting
 * @date: 2024/10/12
 */
public class FlexBoxAdapter extends RecyclerView.Adapter<FlexBoxAdapter.MyViewHolder> {
    private static final String TAG = FlexBoxAdapter.class.getSimpleName();
    List<String> mList = new ArrayList<>();

    public FlexBoxAdapter(List<String> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flex_box_layout, parent, false);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof FlexboxLayoutManager.LayoutParams) {
            Log.d(TAG, "是这个数据吧 FlexboxLayoutManager.LayoutParams ");

            ((FlexboxLayoutManager.LayoutParams) layoutParams).setFlexBasisPercent(0.5f);
        }

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String s = mList.get(holder.getAbsoluteAdapterPosition());
        holder.tv_item.setText(s);


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_item = itemView.findViewById(R.id.tv_item);
        }
    }
}
