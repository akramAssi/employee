package com.example.employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView searchText;
    private ImageButton searchButton;
    private ImageButton showButton;
    private ImageButton insertButton;
    private RadioGroup hideSlide;
    private RadioButton idButton;
    private RadioButton nameButton;
    private RecyclerView displayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchButton = findViewById(R.id.searchButton);
        searchText = findViewById(R.id.searchText);
        showButton = findViewById(R.id.showButton);
        insertButton = findViewById(R.id.insertButton);
        hideSlide = findViewById(R.id.hideSlide);
        idButton = findViewById(R.id.idButton);
        nameButton = findViewById(R.id.nameButton);
        displayList = findViewById(R.id.displayList);

        adapter ad= new adapter(this);
        displayList.setAdapter(ad);
        displayList.setLayoutManager(new LinearLayoutManager(this));

        insertButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == insertButton)
        {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this, R.style.bottomSheatTheme);
            View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.insert_layout,
                    (ScrollView)findViewById(R.id.insertView));

            bottomSheetView.findViewById(R.id.insertSaveButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bottomSheetDialog.dismiss();
                }
            });

            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
        }
    }
}