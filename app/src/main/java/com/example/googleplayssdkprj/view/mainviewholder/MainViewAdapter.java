package com.example.googleplayssdkprj.view.mainviewholder;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.googleplayssdkprj.R;
import com.example.googleplayssdkprj.dto.MainItem;
import com.example.googleplayssdkprj.dto.MainItemViewModel;
import com.example.googleplayssdkprj.view.mainview.OnMainItemClickedListener;

import java.util.List;

public class MainViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<MainItem> items;
    OnMainItemClickedListener activityChangeListener;
    OnMainItemClickedListener updateInfoListener;
    String TAG = MainViewAdapter.class.getName();

    public void setItems(List<MainItem> items) {
        this.items = items;
        notifyDataSetChanged();

    }
    public MainItem getSpecificItem (int index){
        if(items.size()==0)
            return null;
        else
            return items.get(index);
    }

    public void updateSpecificItem(MainItem item, int index){
        items.set(index,item);
        this.notifyItemChanged(index);
    }

    public MainViewAdapter(OnMainItemClickedListener activityChangeListener) {
        this.activityChangeListener = activityChangeListener;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_cardview,parent,false);
        return new MainViewHolder(v,activityChangeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MainViewHolder viewHolder = (MainViewHolder) holder;
        viewHolder.mTitle.setText(items.get(position).getTitle());
        viewHolder.mDescription.setText(items.get(position).getDescription());
        viewHolder.index = position;

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
