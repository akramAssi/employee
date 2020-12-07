package com.example.employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;

import static com.example.employee.MainActivity.DB;

public class searchActivity extends AppCompatActivity {

    private Button backButton;
    private RecyclerView display;
    private adapter ad;
    private TextView dataMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        backButton=findViewById(R.id.backButton);
        display=findViewById(R.id.displaySearchList);
        dataMain=findViewById(R.id.dataSh);


        ////
        Intent prv =getIntent();
        int id = prv.getIntExtra("id",111);
//        myDataBase DB = (myDataBase) prv.getParcelableExtra("cox");
        ArrayList<emp> questions = (ArrayList<emp>) getIntent().getSerializableExtra("cox");

        if (questions.isEmpty()) {
            display.setVisibility(View.GONE);
            dataMain.setVisibility(View.VISIBLE);
        }
        else {
            display.setVisibility(View.VISIBLE);
            dataMain.setVisibility(View.GONE);
        }
        ad= new adapter(this ,questions);
        display.setAdapter(ad);
        display.setLayoutManager(new LinearLayoutManager(this));



    }

    private void update()
    {
        ad.updateData(DB.getAllData());
    }


}