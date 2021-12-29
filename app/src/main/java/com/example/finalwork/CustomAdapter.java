package com.example.finalwork;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    Activity activity;
    private ArrayList  bill_id, bill_type, bill_amount, bill_date, bill_note;

    Animation translate_anim;

    CustomAdapter(Activity activity, Context context, ArrayList text_id, ArrayList text_type,
                  ArrayList text_amount, ArrayList text_date, ArrayList text_note){
        this.activity = activity;
        this.context = context;
        this.bill_id = text_id;
        this.bill_type = text_type;
        this.bill_amount = text_amount;
        this.bill_date = text_date;
        this.bill_note = text_note;
    }

    @NonNull
    @Override
//    负责承载每个子项的布局
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }


//    负责将CardView内的每个子项holder绑定数据
    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.txt_id.setText(String.valueOf(bill_id.get(position)));
        holder.txt_type.setText(String.valueOf(bill_type.get(position)));
        holder.txt_amount.setText(String.valueOf(bill_amount.get(position)));
        holder.txt_date.setText(String.valueOf(bill_date.get(position)));
        holder.txt_note.setText(String.valueOf(bill_note.get(position)));

        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                //通过Intent将之前输入的数据数据传到“更改”界面
                intent.putExtra("id", String.valueOf(bill_id.get(position)));
                intent.putExtra("type", String.valueOf(bill_type.get(position)));
                intent.putExtra("amount", String.valueOf(bill_amount.get(position)));
                intent.putExtra("date", String.valueOf(bill_date.get(position)));
                intent.putExtra("note", String.valueOf(bill_note.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bill_id.size();
    }

//    在ViewHolder内绑定，节省资源
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txt_id, txt_type, txt_amount, txt_date, txt_note;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_id = itemView.findViewById(R.id.text_id);
            txt_type = itemView.findViewById(R.id.text_type);
            txt_amount = itemView.findViewById(R.id.text_amount);
            txt_date = itemView.findViewById(R.id.text_date);
            txt_note = itemView.findViewById(R.id.text_note);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            //加载动画
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_amin);
            //设置动画
            mainLayout.setAnimation(translate_anim);
        }
    }
}
