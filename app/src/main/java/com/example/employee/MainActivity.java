package com.example.employee;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView searchText;
    private ImageButton searchButton;
    private ImageButton showButton;
    private ImageButton insertButton;
    private RadioGroup hideSlide;
    private RadioButton idButton;
    private RadioButton nameButton;
    private RecyclerView displayList;
    private Spinner spinner;
    /// dataBase
//    public static myDataBase DB;
//    private SQLiteDatabase db = null;
    private adapter ad;
    /// animation
    private Animation open;
    private Animation close;
    private Animation slide_up;
    private Animation slide_down;
    private boolean ifOpen = false;
    private int shortAnimationDuration;
    private TextView dataMain;
    private BroadcastReceiver act;
    private Context context;


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
        dataMain = findViewById(R.id.dataMain);


        context = this;
//// animation
        displayList = findViewById(R.id.displayList);
        open = AnimationUtils.loadAnimation(this, R.anim.open);
        close = AnimationUtils.loadAnimation(this, R.anim.close);
        slide_up = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        slide_down = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);


        Intent i = new Intent(getApplicationContext(), storeService.class);
        i.setAction(storeService.ACTION_SHOW_ALL_EMPLOYEE);
        i.putExtra("update", "no");
        startService(i);

        insertButton.setOnClickListener(this);
        showButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        idButton.setOnClickListener(this);
        nameButton.setOnClickListener(this);

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
                        case storeService.ACTION_INSERT_EMPLOYEE: {
                            update();
                            break;
                        }
                        case storeService.ACTION_SHOW_ALL_EMPLOYEE: {
                            ArrayList<emp> fg = (ArrayList<emp>) intent.getSerializableExtra(storeService.data);


                            if (fg.isEmpty()) {
                                displayList.setVisibility(View.GONE);
                                dataMain.setVisibility(View.VISIBLE);
                            } else {
                                displayList.setVisibility(View.VISIBLE);
                                dataMain.setVisibility(View.GONE);
                            }
                            if (intent.getStringExtra("update").equals("no")) {
                                ad = new adapter(context, fg);
                                displayList.setAdapter(ad);
                                displayList.setLayoutManager(new LinearLayoutManager(context));
                                break;
                            } else {
                                ad.updateData(fg);
                            }

                        }
                    }


                }
            }
        };

        IntentFilter y = new IntentFilter();

        y.addAction(storeService.ACTION_DELETE_EMPLOYEE);
        y.addAction(storeService.ACTION_MODIFY_EMPLOYEE);
        y.addAction(storeService.ACTION_INSERT_EMPLOYEE);
        y.addAction(storeService.ACTION_SHOW_ALL_EMPLOYEE);

        registerReceiver(act, y);


    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    @Override
    public void onClick(View view) {
        if (view == insertButton) {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this, R.style.bottomSheatTheme);
            final View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.insert_layout,
                    (ScrollView) findViewById(R.id.insertView));

            spinner = bottomSheetView.findViewById(R.id.genderEditText);
            final String[] gen = new String[1];
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.numbers, R.layout.colorss);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String text = adapterView.getItemAtPosition(i).toString();
                    gen[0] = text;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            bottomSheetView.findViewById(R.id.insertSaveButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView name = bottomSheetView.findViewById(R.id.nameEditText);
                    TextView id = bottomSheetView.findViewById(R.id.idEditText);
                    TextView salary = bottomSheetView.findViewById(R.id.salaryEditText);
                    TextView sales = bottomSheetView.findViewById(R.id.salesEditText);
                    TextView rate = bottomSheetView.findViewById(R.id.CommissionEditText);

                    boolean isempty = false;
                    if (name.getText().toString().isEmpty()) {
                        anim(name);
                        isempty = true;
                    }
                    if (id.getText().toString().isEmpty()) {
                        anim(id);
                        isempty = true;
                    }

                    if (salary.getText().toString().isEmpty()) {
                        anim(salary);
                        isempty = true;
                    }
                    if (sales.getText().toString().isEmpty()) {
                        anim(sales);
                        isempty = true;
                    }
                    if (rate.getText().toString().isEmpty()) {
                        anim(rate);
                        isempty = true;
                    }
                    if (isempty) return;

                    Object[] info = {Integer.parseInt(id.getText().toString()),
                            name.getText().toString(),
                            gen[0].equals("Male") ? 'm' : 'f',
                            Float.parseFloat(salary.getText().toString()),
                            Float.parseFloat(sales.getText().toString()),
                            Float.parseFloat(rate.getText().toString())
                    };


                    Intent i = new Intent(getApplicationContext(), storeService.class);
                    i.setAction(storeService.ACTION_INSERT_EMPLOYEE);
                    i.putExtra(storeService.info, info);
                    startService(i);

                    bottomSheetDialog.dismiss();
                }
            });

            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
        }
        if (view == showButton) {
            if (ifOpen) {
                close();
            } else {
                open();
            }
        }
        if (view == idButton) {
            searchText.setHint(R.string.id_search);
            searchText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        if (view == nameButton) {
            searchText.setHint(R.string.name_search);
            searchText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        }
        if (view == searchButton) {

            if (searchText.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter value ", Toast.LENGTH_LONG).show();
                return;
            }

            Intent i = new Intent(getApplicationContext(), storeService.class);
            i.setAction(storeService.ACTION_SEARCH);
            int CheckedRadioButtonId = hideSlide.getCheckedRadioButtonId();
            i.putExtra(storeService.Checked_Radio_ButtonId, CheckedRadioButtonId);
            i.putExtra(storeService.Search_content, searchText.getText().toString());
            startService(i);

        }
    }

    private void update() {

//        ArrayList<emp> fg = DB.getAllData();

//        if (fg.isEmpty()) {
//            displayList.setVisibility(View.GONE);
//            dataMain.setVisibility(View.VISIBLE);
//        } else {
//            displayList.setVisibility(View.VISIBLE);
//            dataMain.setVisibility(View.GONE);
//        }
//        ad.updateData(fg);

        Intent i = new Intent(getApplicationContext(), storeService.class);
        i.setAction(storeService.ACTION_SHOW_ALL_EMPLOYEE);
        i.putExtra("update", "yes");
        startService(i);

    }

    @SuppressLint("WrongConstant")
    private void anim(TextView obj) {
        ObjectAnimator anim = ObjectAnimator.ofInt(obj, "backgroundColor",
                Color.TRANSPARENT, getResources().getColor(R.color.illuminatingEmerald), Color.TRANSPARENT);
        anim.setDuration(1000);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(2);
        anim.start();
    }

    public void open() {
        showButton.startAnimation(open);
        hideSlide.startAnimation(slide_up);
        showButton.setRotation(180);
        ifOpen = true;


        hideSlide.setAlpha(0f);
        hideSlide.setVisibility(View.VISIBLE);
        hideSlide.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null);
    }

    public void close() {
        showButton.startAnimation(close);
        hideSlide.startAnimation(slide_down);

        showButton.setRotation(0);
        ifOpen = false;


        hideSlide.animate()
                .alpha(0f)
                .setDuration(shortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        hideSlide.setVisibility(View.INVISIBLE);
                    }
                });
    }
}