package com.example.mobileinfo;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileinfo.databinding.FragmentItemBinding;
import com.example.mobileinfo.placeholder.PlaceholderContent;

import java.util.ArrayList;

 public class CamRVAdaptor extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ListModel model;
    Context context;
    ArrayList<ListModel> list;

    public CamRVAdaptor(Context context, ArrayList<ListModel> list) {
        this.context = context;
        this.list = list;
    }




     @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.fragment_item, parent, false);
            return new ContentViewHolder(view);
        } else {
            View view1= LayoutInflater.from(context).inflate(R.layout.fragment_item_tick,parent,false);
            return new TitelViewHolder(view1);
        }
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        model=list.get(position);


            if (holder instanceof ContentViewHolder) {
                ((ContentViewHolder) holder).mIdView.setText(model.Name);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    if (list.get(position).getState() == ThreeState.YES) {
                        ((ContentViewHolder) holder).mContentView.setText("YES");
                    } else if (list.get(position).getState() == ThreeState.NO) {
                        ((ContentViewHolder) holder).mContentView.setText("No");
                    } else {
                        ((ContentViewHolder) holder).mContentView.setText(model.Info + " " + model.getSuffix());
                    }
                }

            } else {

                ((TitelViewHolder)holder).aj.setText(list.get(position).titel);
            }

        }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TitelViewHolder extends RecyclerView.ViewHolder {
        TextView aj;
        public PlaceholderContent.PlaceholderItem mItem;

        public TitelViewHolder(View view) {
            super(view);
            aj = view.findViewById(R.id.item_titaAj);

        }

    }

     private class ContentViewHolder extends RecyclerView.ViewHolder {

         TextView mIdView;
         TextView mContentView;
         TextView suffix;

         ContentViewHolder(@NonNull View itemView) {
             super(itemView);
             mIdView = itemView.findViewById(R.id.item_name);
             mContentView =itemView.findViewById(R.id.content);
         }
     }

    public int getItemViewType(int position) {

        if (list.get(position).getType() == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
