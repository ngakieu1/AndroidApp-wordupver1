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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PackAdapter extends RecyclerView.Adapter<PackAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<PackModel> packModels;
    public PackAdapter(Context context, ArrayList<PackModel> packModels, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.packModels = packModels;
        this.recyclerViewInterface = recyclerViewInterface;

    }

    @NonNull
    @Override
    public PackAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);

        return new PackAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull PackAdapter.MyViewHolder holder, int position) {

        holder.pakVocab.setText(packModels.get(position).getPackName());
        holder.imageView.setImageResource(packModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return packModels.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView pakVocab;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView2);
            pakVocab = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
