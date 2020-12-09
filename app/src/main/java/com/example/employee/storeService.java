package com.example.employee;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.util.Objects;


public class storeService extends IntentService {

    //  action names
    public static final String ACTION_SHOW_ALL_EMPLOYEE = "displayRow";
    public static final String ACTION_DELETE_EMPLOYEE = "deleteRow";
    public static final String ACTION_INSERT_EMPLOYEE = "insertRow";
    public static final String ACTION_MODIFY_EMPLOYEE = "modifyRow";
    public static final String ACTION_SEARCH = "searchRow";

    //  parameters

    public static final String info = "info";
    public static final String person = "person";
    public static final String Position = "safePosition";
    public static final String salary = "salary";
    public static final String sale = "sale";
    public static final String rate = "rate";
    public static final String Search_content = "searchContent";
    public static final String Search_name = "searchName";
    public static final String Checked_Radio_ButtonId = "CheckedRadioButtonId";


    public storeService() {
        super("storeService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
//        Intent intent = new Intent(context, storeService.class);
//        intent.setAction(ACTION_FOO);
//        intent.putExtra(EXTRA_PARAM1, param1);
//        intent.putExtra(EXTRA_PARAM2, param2);
//        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
//        Intent intent = new Intent(context, storeService.class);
//        intent.setAction(ACTION_BAZ);
//        intent.putExtra(EXTRA_PARAM1, param1);
//        intent.putExtra(EXTRA_PARAM2, param2);
//        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            switch (action) {
                case ACTION_DELETE_EMPLOYEE: {
                    emp param_person = (emp) intent.getSerializableExtra(person);
                    int safePosition = intent.getIntExtra(Position, 0);
                    if (param_person != null) {
                        deleteAction(param_person, safePosition);
                        break;
                    }
                }
                case ACTION_MODIFY_EMPLOYEE: {
                    emp param_person = (emp) intent.getSerializableExtra(person);
                    float param_salary = intent.getFloatExtra(salary, 0);
                    float param_sale = intent.getFloatExtra(sale, 0);
                    float param_rate = intent.getFloatExtra(rate, 0);
                    int safePosition = intent.getIntExtra(Position, 0);
                    modifyAction(Objects.requireNonNull(param_person), param_salary, param_sale, param_rate, safePosition);
                    break;
                }
                case ACTION_INSERT_EMPLOYEE: {
                    Object[] param_info = (Object[]) intent.getSerializableExtra(info);
                    insertAction(param_info);
                    break;
                }
                case ACTION_SEARCH: {
                    int CheckedRadioButtonId = intent.getIntExtra(Checked_Radio_ButtonId, -11);
                    String searchContent = intent.getStringExtra(Search_content);

                    Intent search = new Intent(this, searchActivity.class);

                    if (CheckedRadioButtonId == R.id.idButton) {
                        int id = Integer.parseInt(searchContent != null ? searchContent : "0");
                        search.putExtra("cox", MainActivity.DB.search(id));
                    } else {
                        String name = searchContent;
                        search.putExtra("cox", MainActivity.DB.search(name));
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(search);
                }
            }
        }
    }

    private void modifyAction(emp param_person, float param_salary, float param_sale, float param_rate, int safePosition) {
        MainActivity.DB.update(param_person.getId(), param_salary, param_sale, param_rate);

        Intent intent = new Intent();
        intent.setAction(ACTION_MODIFY_EMPLOYEE);
        intent.putExtra(Position, safePosition);
        intent.putExtra(salary, param_salary);
        intent.putExtra(sale, param_sale);
        intent.putExtra(rate, param_rate);
        sendBroadcast(intent);

    }


    private void deleteAction(emp param_person, int safePosition) {

        MainActivity.DB.delete(param_person.getId());

        Intent intent = new Intent();
        intent.setAction(ACTION_DELETE_EMPLOYEE);
        intent.putExtra(Position, safePosition);
        sendBroadcast(intent);


    }

    private void insertAction(Object[] info) {
        MainActivity.DB.insert(info);

        Intent intent = new Intent();
        intent.setAction(ACTION_INSERT_EMPLOYEE);

        sendBroadcast(intent);
    }
}


