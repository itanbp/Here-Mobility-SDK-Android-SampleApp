package com.nil.test.sdk.sampleapp.happy_ride;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nil.test.sdk.sampleapp.R;

import java.util.ArrayList;

/**
 * Created by itanbp on 06/06/2018.
 */
public class StickersAdapter extends RecyclerView.Adapter<StickersAdapter.ViewHolder> {

    private ArrayList<Integer> stickerIcons;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;


    StickersAdapter(Context context, ArrayList<Integer> stickerIcons) {
        this.mInflater = LayoutInflater.from(context);
        this.stickerIcons = stickerIcons;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView stickerImage = (ImageView) mInflater.inflate(R.layout.sticker_item, parent, false);
        return new ViewHolder(stickerImage);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int drawableId = stickerIcons.get(position);
        holder.stickerIcon.setImageResource(drawableId);
    }


    @Override
    public int getItemCount() {
        return stickerIcons.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView stickerIcon;

        ViewHolder(View itemView) {
            super(itemView);
            stickerIcon = (ImageView)itemView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
