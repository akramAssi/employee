package com.example.employee;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.MyViewHolder> {
    private Context cox;


    private ArrayList<emp> person;

    public adapter(Context cox, ArrayList<emp> person) {
        this.cox = cox;
        this.person = person;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(cox);
        View view = inflater.inflate(R.layout.row_emp, parent, false);
        return new MyViewHolder(view);
    }

    public void insertData(List<emp> insertList) {
        MyDiffCallback diffutil = new MyDiffCallback(person, insertList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffutil);

        person.addAll(insertList);
        diffResult.dispatchUpdatesTo(this);
    }

    public void updateData(List<emp> newList) {
        MyDiffCallback diffutil = new MyDiffCallback(person, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffutil);

        person.clear();
        person.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

    public void Remove(int safePosition) {
        if (safePosition != RecyclerView.NO_POSITION) {
            person.remove(safePosition);
            notifyDataSetChanged();
        }
    }

    public void modify(int safePosition, float sales, float rate, float salary) {
        person.get(safePosition).setSales(sales);
        person.get(safePosition).setSalary(salary);
        person.get(safePosition).setRate(rate);
        notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {


        holder.idText.setText("" + person.get(position).getId());

        holder.personImage.setImageResource((person.get(position).getGender().equals("Male")) ? R.drawable.man : R.drawable.woman);
        holder.name.setText(person.get(position).getName());
        holder.genderText.setText(person.get(position).getGender());
        holder.salaryText.setText("" + person.get(position).getSalary());
        holder.salesText.setText("" + person.get(position).getSales());
        holder.rateText.setText("" + person.get(position).getRate());
        final int safePosition = holder.getAdapterPosition();
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(cox, R.style.bottomSheatTheme);
                @SuppressLint("InflateParams") View bottomSheetView = LayoutInflater.from(cox).inflate(R.layout.mod_layout, null);

                final TextView x = bottomSheetView.findViewById(R.id.m_salaryEditText);
                x.setText("" + person.get(safePosition).getSalary());

                final TextView x2 = bottomSheetView.findViewById(R.id.m_salesEditText);
                x2.setText("" + person.get(safePosition).getSales());

                final TextView x3 = bottomSheetView.findViewById(R.id.m_CommissionEditText);
                x3.setText("" + person.get(safePosition).getRate());
                bottomSheetView.findViewById(R.id.modifySaveButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isempty = false;
                        if (x.getText().toString().isEmpty()) {
                            anim(x);
                            isempty = true;
                        }
                        if (x2.getText().toString().isEmpty()) {
                            anim(x2);
                            isempty = true;
                        }
                        if (x3.getText().toString().isEmpty()) {
                            anim(x3);
                            isempty = true;
                        }
                        if (isempty) return;
                        float salary = Float.parseFloat(x.getText().toString());
                        float sales = Float.parseFloat(x2.getText().toString());
                        float rate = Float.parseFloat(x3.getText().toString());


                        Intent i = new Intent(cox, storeService.class);
                        i.setAction(storeService.ACTION_MODIFY_EMPLOYEE);
                        i.putExtra(storeService.person, person.get(safePosition));
                        i.putExtra(storeService.Position, safePosition);
                        i.putExtra(storeService.salary, salary);
                        i.putExtra(storeService.sale, sales);
                        i.putExtra(storeService.rate, rate);
                        cox.startService(i);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialogBox =new Dialog(cox,R.style.bottomSheatTheme);
                dialogBox.setContentView(R.layout.delete_layout);
                TextView name = dialogBox.findViewById(R.id.nameEmployee);
                Button okButton =dialogBox.findViewById(R.id.okButton);
                Button cancelButton = dialogBox.findViewById(R.id.cancelButton);

                name.setText(person.get(safePosition).getName());
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(cox, storeService.class);
                        i.setAction(storeService.ACTION_DELETE_EMPLOYEE);
                        i.putExtra(storeService.person, person.get(safePosition));
                        i.putExtra(storeService.Position, safePosition);
                        cox.startService(i);

                        dialogBox.dismiss();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBox.dismiss();
                    }
                });
                dialogBox.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return person.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView personImage,deleteButton,editButton;
        TextView name,idText,genderText,rateText,salaryText,salesText;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            personImage = itemView.findViewById(R.id.personImage);
            name = itemView.findViewById(R.id.name);
            idText = itemView.findViewById(R.id.idText);
            genderText = itemView.findViewById(R.id.genderText);
            rateText = itemView.findViewById(R.id.rateText);
            salaryText = itemView.findViewById(R.id.salaryText);
            salesText = itemView.findViewById(R.id.salesText);

        }

        public Object findViewById(int modifyView) {
            return findViewById(modifyView);
        }
    }

    @SuppressLint("WrongConstant")
    private void anim(TextView obj) {
        ObjectAnimator anim = ObjectAnimator.ofInt(obj, "backgroundColor",
                Color.TRANSPARENT, cox.getResources().getColor(R.color.illuminatingEmerald), Color.TRANSPARENT);
        anim.setDuration(1000);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(2);
        anim.start();
    }
}
