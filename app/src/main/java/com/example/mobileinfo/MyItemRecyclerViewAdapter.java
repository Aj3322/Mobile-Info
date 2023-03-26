package com.example.mobileinfo;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;

import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobileinfo.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.mobileinfo.databinding.FragmentItemBinding;

import java.util.ArrayList;



public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<ListModel> list;

    public MyItemRecyclerViewAdapter(Context context, ArrayList<ListModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        ListModel model=list.get(position);
        holder.mIdView.setText(model.Name);

        holder.mContentView.setText(model.Info);}


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemName;
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}