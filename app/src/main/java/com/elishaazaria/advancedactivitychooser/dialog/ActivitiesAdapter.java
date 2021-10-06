package com.elishaazaria.advancedactivitychooser.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elishaazaria.advancedactivitychooser.R;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ViewHolder> {

    private static final int marginDivider = 10;

    private final ActivityTile[] mData;
    private final LayoutInflater mInflater;
    private final int size;

    // data is passed into the constructor
    ActivitiesAdapter(Context context, int size, ActivityTile[] data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.size = size;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.tile_activity_action, parent, false);

        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();


//                lp.setMargins(10, 10, 10, 10);
//
//
        view.setPadding(size / marginDivider, size / marginDivider,
                size / marginDivider, size / marginDivider);
        lp.width = lp.height = size;
        view.setLayoutParams(lp);

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.updateUI(mData[position]);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.length;
    }


    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final View root;

        ViewHolder(View itemView) {
            super(itemView);
            root = itemView;
        }

        public void updateUI(ActivityTile data) {
            ImageView iconView = root.findViewById(R.id.icon);
            iconView.setImageDrawable(data.icon);

            TextView labelView = root.findViewById(R.id.label);
            labelView.setText(data.label);

            root.setOnClickListener(v -> data.onClick.onClick(data));
        }
    }

    // convenience method for getting data at click position
    ActivityTile getItem(int id) {
        return mData[id];
    }
}

