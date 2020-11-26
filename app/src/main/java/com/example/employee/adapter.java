package com.example.employee;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import  androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class adapter extends RecyclerView.Adapter<adapter.MyViewHolder> {
    private Context cox;

    public adapter(Context cox)
    {
        this.cox=cox;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(cox);
        View view = inflater.inflate(R.layout.row_emp,parent,false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.name.setText("Rakan Assi");
        holder.idText.setText("231231244");
        holder.genderText.setText("male");
        holder.rateText.setText(".232");
        holder.salaryText.setText("123124");
        holder.salesText.setText("55123");

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(cox, R.style.bottomSheatTheme);
                @SuppressLint("InflateParams") View bottomSheetView = LayoutInflater.from(cox).inflate(R.layout.mod_layout, null);

                bottomSheetView.findViewById(R.id.modifySaveButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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

                name.setText("ok + " + position);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
        return 12;
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
}
