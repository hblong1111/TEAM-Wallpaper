package com.longhb.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.longhb.myapplication.databinding.AdapterCategoryDetailBinding;
import com.longhb.myapplication.model.ImageDetailCategory;

import java.util.List;

public class ListImageAdapter extends RecyclerView.Adapter<ListImageAdapter.ViewHolder> {
    private List<ImageDetailCategory> list;

    public ListImageAdapter(List<ImageDetailCategory> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterCategoryDetailBinding binding = AdapterCategoryDetailBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterCategoryDetailBinding binding;

        public ViewHolder(@NonNull AdapterCategoryDetailBinding itemView) {
            super(itemView.getRoot());

            binding = itemView;
        }

        public void bind(ImageDetailCategory detail) {
            binding.setImage(detail);
            binding.executePendingBindings();
        }
    }
}
