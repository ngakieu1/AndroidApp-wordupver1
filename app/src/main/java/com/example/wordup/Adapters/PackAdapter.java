//package com.example.wordup.Adapters;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.wordup.Models.PackModel;
//import com.example.wordup.R;
//import com.example.wordup.RecyclerViewInterface;
//import com.example.wordup.TwoPane.MainActivity; // MainActivity của tablet
//import com.example.wordup.TwoPane.QuestionFragment;
//
//import java.util.ArrayList;
//
//public class PackAdapter extends RecyclerView.Adapter<PackAdapter.MyViewHolder> {
//    private final RecyclerViewInterface recyclerViewInterface;
//    private final Context context;
//    private final ArrayList<PackModel> packModels;
//    private final boolean isTwoPane;
//
//    public PackAdapter(Context context, ArrayList<PackModel> packModels, RecyclerViewInterface recyclerViewInterface, boolean isTwoPane) {
//        this.context = context;
//        this.packModels = packModels;
//        this.recyclerViewInterface = recyclerViewInterface;
//        this.isTwoPane = isTwoPane;
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
//        return new MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        holder.pakVocab.setText(packModels.get(position).getPackName());
//        holder.imageView.setImageResource(packModels.get(position).getImage());
//
//        holder.itemView.setOnClickListener(v -> {
//            if (isTwoPane) {
//                // Dùng Fragment trên tablet
//                if (context instanceof MainActivity) {
//                    ((MainActivity) context).showQuestion(packModels.get(position)); // ✅ Truyền đúng PackModel
//                }
//            } else {
//                // Điện thoại: gọi qua interface để MainActivity xử lý
//                if (recyclerViewInterface != null && position != RecyclerView.NO_POSITION) {
//                    recyclerViewInterface.onItemClick(position);
//                }
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return packModels.size();
//    }
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        ImageView imageView;
//        TextView pakVocab;
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            imageView = itemView.findViewById(R.id.imageView2);
//            pakVocab = itemView.findViewById(R.id.textView);
//        }
//    }
//}

package com.example.wordup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordup.Models.PackModel;
import com.example.wordup.R;
import com.example.wordup.RecyclerViewInterface;
import com.example.wordup.TwoPane.MainActivity;

import java.util.ArrayList;

public class PackAdapter extends RecyclerView.Adapter<PackAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private final Context context;
    private final ArrayList<PackModel> packModels;
    private final boolean isTwoPane;

    public PackAdapter(Context context, ArrayList<PackModel> packModels, RecyclerViewInterface recyclerViewInterface, boolean isTwoPane) {
        this.context = context;
        this.packModels = packModels;
        this.recyclerViewInterface = recyclerViewInterface;
        this.isTwoPane = isTwoPane;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PackModel currentModel = packModels.get(position);
        holder.pakVocab.setText(currentModel.getPackName());
        holder.imageView.setImageResource(currentModel.getImage());

        // Gán model cho ViewHolder để dùng lại trong click
        holder.bind(currentModel);
    }

    @Override
    public int getItemCount() {
        return packModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView pakVocab;
        PackModel boundModel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView2);
            pakVocab = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position == RecyclerView.NO_POSITION) return;

                if (isTwoPane) {
                    if (context instanceof MainActivity) {
                        ((MainActivity) context).showQuestion(boundModel); // 🟢 Sử dụng model đã bind
                    }
                } else {
                    if (recyclerViewInterface != null) {
                        recyclerViewInterface.onItemClick(position);
                    }
                }
            });
        }

        public void bind(PackModel model) {
            this.boundModel = model;
        }
    }
}
