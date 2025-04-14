package com.example.mapmate.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.mapmate.R;
import com.example.mapmate.items.SlideItem;

import java.util.List;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SlideViewHolder> {
    private final List<SlideItem> slideItems;

    public SlideAdapter(List<SlideItem> slideItems) {
        this.slideItems = slideItems;
    }

    @NonNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_layout, parent, false);
        return new SlideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
        SlideItem slideItem = slideItems.get(position);
        Glide.with(holder.itemView.getContext()).load(slideItem.getImageUrl()).into(holder.imageView);
        holder.title.setText(slideItem.getTitle());
        holder.description.setText(slideItem.getDescription());

        // Apply animation to image
        Animation fadeInImage = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in);
        holder.imageView.startAnimation(fadeInImage);

        // Apply animation to text
        Animation textAnimation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.zoom_in);
        holder.title.startAnimation(textAnimation);
        holder.description.startAnimation(textAnimation);
    }

    @Override
    public int getItemCount() {
        return slideItems.size();
    }
    static class SlideViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, description;

        SlideViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            title = itemView.findViewById(R.id.feature_header);
            description = itemView.findViewById(R.id.feature_description);
        }
    }
}
