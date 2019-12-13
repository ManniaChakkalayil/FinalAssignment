package com.example.finalassignment;

import android.content.Context;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReadAdapter extends RecyclerView.Adapter<ReadAdapter.ViewHolder> {

    List<Reading> Items;
    Context context;

    public ReadAdapter(List<Reading> Items, Context context) {
        this.Items = Items;
        this.context = context;
    }

    @NonNull
    @Override
    public ReadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.read_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReadAdapter.ViewHolder viewHolder, int i) {
        viewHolder.x.setText("X= "+String.valueOf(Items.get(i).getX()));
        viewHolder.y.setText("Y= "+String.valueOf(Items.get(i).getY()));
        viewHolder.timestamp.setText("TimeStamp= "+Items.get(i).getTimestamp());

    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView x, y, timestamp;


        public ViewHolder(final View ItemView) {
            super(ItemView);
            x = ItemView.findViewById(R.id.x);
            y = ItemView.findViewById(R.id.y);
            timestamp = ItemView.findViewById(R.id.timestamp);


        }

    }

}
