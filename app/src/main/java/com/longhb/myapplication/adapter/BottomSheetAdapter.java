package com.longhb.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.longhb.myapplication.R;


public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.ViewModel> {
    String [] strings;

    public BottomSheetAdapter(String[] strings) {
        this.strings = strings;
    }

    @NonNull
    @Override
    public ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewModel(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_bottom_sheet, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewModel holder, int position) {
        holder.textView.setText(strings[position]);
    }

    @Override
    public int getItemCount() {
        return strings.length;
    }

    public class ViewModel extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewModel(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView10);
        }
    }
}
