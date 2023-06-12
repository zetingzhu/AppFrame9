package com.zzt.zt_rv_hsc;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zzt.aaa.RVNestHorizontalScrollView;

/**
 * @author: zeting
 * @date: 2023/4/20
 */
public class MRvViewHolder extends RecyclerView.ViewHolder {
    TextView tv_item_name, tv_item_1, tv_item_2, tv_item_3, tv_item_4, tv_item_5, tv_item_6;

    RVNestHorizontalScrollView right_nest_hsv ;
    public MRvViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_item_name = itemView.findViewById(R.id.tv_item_name);
        tv_item_1 = itemView.findViewById(R.id.tv_item_1);
        tv_item_2 = itemView.findViewById(R.id.tv_item_2);
        tv_item_3 = itemView.findViewById(R.id.tv_item_3);
        tv_item_4 = itemView.findViewById(R.id.tv_item_4);
        tv_item_5 = itemView.findViewById(R.id.tv_item_5);
        tv_item_6 = itemView.findViewById(R.id.tv_item_6);
        right_nest_hsv = itemView.findViewById(R.id.right_nest_hsv);
    }

}
