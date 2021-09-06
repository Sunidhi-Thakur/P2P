package com.preritrajput.peertopeer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.preritrajput.peertopeer.R;

public class OnBoardingAdapter extends RecyclerView.Adapter<OnBoardingAdapter.ViewHolder> {

    private final int[] images = {R.drawable.car, R.drawable.location, R.drawable.money};
    private final int[] text1 = {R.string.view_text1,R.string.view_text3, R.string.view_text5};
    private final int[] text2 = {R.string.view_text2,R.string.view_text4,R.string.view_text6};
    private final Context ctx;

    public OnBoardingAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.slides, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.images.setImageResource(images[position]);
        holder.text1.setText(text1[position]);
        holder.text2.setText(text2[position]);

    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView images;
        TextView text1;
        TextView text2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.slier_pic);
            text1 = itemView.findViewById(R.id.slider_text1);
            text2 = itemView.findViewById(R.id.slider_text2);
        }
    }
}
