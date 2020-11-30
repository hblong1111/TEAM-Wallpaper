package com.longhb.myapplication.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.longhb.myapplication.databinding.AdapterCategoryDetailBinding;
import com.longhb.myapplication.model.ImageDetail;
import com.longhb.myapplication.ui.activity.ImageDetailActivity;
import com.longhb.myapplication.utils.Conts;

import java.io.Serializable;
import java.util.List;

public class ListImageAdapter extends RecyclerView.Adapter<ListImageAdapter.ViewHolder> {
    private List<ImageDetail> list;

    public ListImageAdapter(List<ImageDetail> list) {
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

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ImageDetailActivity.class);
            intent.putExtra(Conts.CODE_PUT_LIST_IMAGE, (Serializable) list);
            intent.putExtra(Conts.CODE_PUT_CUSOR, position);
            view.getContext().startActivity(intent);
        });
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

        public void bind(ImageDetail detail) {
            binding.setImage(detail);
            binding.executePendingBindings();
        }
    }
}
