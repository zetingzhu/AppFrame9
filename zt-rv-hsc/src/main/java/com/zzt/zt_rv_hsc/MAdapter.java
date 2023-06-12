package com.zzt.zt_rv_hsc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zzt.aaa.RecycleViewNestHSv;

import java.util.List;

/**
 * @author: zeting
 * @date: 2023/4/20
 */
public class MAdapter extends RecyclerView.Adapter<MRvViewHolder> {
    private List<MData> mDataList;
    private View headerView = null;
    private  RecycleViewNestHSv rvNHSV ;

    public MAdapter(List<MData> mDataList , RecycleViewNestHSv rvNHSV) {
        this.mDataList = mDataList;
        this.rvNHSV = rvNHSV ;
    }

    @NonNull
    @Override
    public MRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        createHeaderView(parent);
        MRvViewHolder holder = new MRvViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_title_content, parent, false)
        );
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MRvViewHolder holder, int position) {

        MData mData = mDataList.get(holder.getAdapterPosition());
        holder.tv_item_name.setText(mData.getName());
        holder.tv_item_1.setText(mData.getDay1());
        holder.tv_item_2.setText(mData.getDay2());
        holder.tv_item_3.setText(mData.getDay3());
        holder.tv_item_4.setText(mData.getDay4());
        holder.tv_item_5.setText(mData.getDay5());
        holder.tv_item_6.setText(mData.getDay6());
        holder.right_nest_hsv.setRecycleView(rvNHSV);
    }

    @Override
    public int getItemCount() {
        if (mDataList != null) {
            return mDataList.size();
        }
        return 0;
    }

    private void createHeaderView(ViewGroup parent) {
//        if (headerView == null) {
//            headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fund_header_rank_not_money, parent, false);
//            int widthSpec = View.MeasureSpec.makeMeasureSpec(1073741823, View.MeasureSpec.AT_MOST);
//            int heightSpec = View.MeasureSpec.makeMeasureSpec(1073741823, View.MeasureSpec.AT_MOST);
//            headerView.measure(widthSpec, heightSpec);
//        }
    }
}
