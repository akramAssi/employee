package com.example.employee;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class searchActivity extends AppCompatActivity {

    private Button backButton;
    private RecyclerView display;
    private adapter ad;
    private TextView dataMain;
    private BroadcastReceiver act;
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
        } else {
            display.setVisibility(View.VISIBLE);
            dataMain.setVisibility(View.GONE);
        }
        ad = new adapter(this, questions);
        display.setAdapter(ad);
        display.setLayoutManager(new LinearLayoutManager(this));

        act = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String action = intent.getAction();
                if (action != null) {
                    switch (action) {
                        case storeService.ACTION_DELETE_EMPLOYEE: {
                            int position = intent.getIntExtra(storeService.Position, 0);
                            ad.Remove(position);
                            break;
                        }
                        case storeService.ACTION_MODIFY_EMPLOYEE: {
                            int position = intent.getIntExtra(storeService.Position, 0);
                            float salary = intent.getFloatExtra(storeService.salary, 0);
                            float sale = intent.getFloatExtra(storeService.sale, 0);
                            float rate = intent.getFloatExtra(storeService.rate, 0);
                            ad.modify(position, sale, rate, salary);
                            break;
                        }

                    }


                }
            }
        };

        IntentFilter yx = new IntentFilter();

        yx.addAction(storeService.ACTION_DELETE_EMPLOYEE);
        yx.addAction(storeService.ACTION_MODIFY_EMPLOYEE);
        yx.addAction(storeService.ACTION_INSERT_EMPLOYEE);

        registerReceiver(act, yx);


    }


}