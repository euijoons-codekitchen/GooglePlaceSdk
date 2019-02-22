package com.example.googleplayssdkprj.view.mainviewholder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.googleplayssdkprj.R;
import com.example.googleplayssdkprj.view.mainview.OnMainItemClickedListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainViewHolder extends RecyclerView.ViewHolder {


    String TAG = MainViewHolder.class.getName();
    @BindView(R.id.tv_main_cardview)
    TextView mTitle;
    @BindView(R.id.tv_main_cardview_address)
    TextView mDescription;
    int index;

    public MainViewHolder(View itemView, OnMainItemClickedListener onActivityChageListner) {
        super(itemView);


        ButterKnife.bind(this,itemView);
        itemView.setOnClickListener((view)->{
            onActivityChageListner.onMainItemClicked(index);

            //onUpdateInfoListner.onUpdateChnage(index);

        });
    }
}
