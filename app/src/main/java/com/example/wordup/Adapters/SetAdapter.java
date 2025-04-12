//package com.example.wordup.Adapters;
//
//import android.view.View;
//
//import androidx.annotation.NonNull;
//
//import com.example.wordup.Activities.QuestionActivity;
//
//public class SetAdapter {
//
//    @Override
//    public void onBindViewHolder(@NonNull viewHolder holder, int position){
//        final SetModel model = list.get(position);
//        holder.binding.setName.setText(model.getSetName());
//        holder.itemView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent intent = new Intent(context, QuestionActivity.class);
//                intent.putExtra("set", model.getSetName());
//                context.startActivity(intent);
//            }
//        });
//    }
//}
