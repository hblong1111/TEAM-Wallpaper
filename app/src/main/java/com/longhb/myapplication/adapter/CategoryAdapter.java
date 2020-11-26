package com.longhb.myapplication.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.longhb.myapplication.databinding.AdapterCategoryBinding;
import com.longhb.myapplication.model.Category;
import com.longhb.myapplication.ui.activity.CategoryDetailActivity;
import com.longhb.myapplication.utils.Conts;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Category> list;

    public CategoryAdapter(List<Category> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterCategoryBinding binding = AdapterCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), CategoryDetailActivity.class);
            intent.putExtra(Conts.CODE_PUT_ID_CATEGORY, list.get(position).getCategoryId());
            intent.putExtra(Conts.CODE_PUT_TITLE_CATEGORY, list.get(position).getCategoryName());
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterCategoryBinding binding;

        public ViewHolder(@NonNull AdapterCategoryBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bind(Category category) {
            binding.setCategory(category);
            binding.executePendingBindings();
        }
    }
}
