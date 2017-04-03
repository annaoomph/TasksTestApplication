package com.example.annakocheshkova.testapplication.Views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import com.example.annakocheshkova.testapplication.Controllers.TaskController;
import com.example.annakocheshkova.testapplication.Models.Task;
import com.example.annakocheshkova.testapplication.R;

import java.util.Calendar;
import java.util.List;

/**
 * a view of the activity, which allows user to create a new task
 */
public class CreateItemActivity extends AppCompatActivity implements TaskView {

    TaskController taskController;
    Toolbar toolbar;
    TextView textS;
    CheckBox rem;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);
        getViews();
        getControllers();
        setContent();
    }

    void getControllers(){
        taskController = new TaskController(this);
    }


    void setContent(){
        id = getIntent().getIntExtra("id", 0);
        if (id>0)
            taskController.get(id);
        ActionBar actionBar = getSupportActionBar();
        if (id>0)
        {
            if (actionBar!=null)
                actionBar.setTitle(R.string.edit);
        }
        else
            if (actionBar!=null)
                actionBar.setTitle(R.string.edit);
        if (actionBar!=null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    void getViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        textS = (EditText)this.findViewById(R.id.name_edittext);
        rem = (CheckBox)this.findViewById(R.id.enable_reminder);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
            startActivity(new Intent(this, MainTasksActivity.class));
        return super.onOptionsItemSelected(item);
    }

    /**
     * method responding on button click when adding the new task
     * @param v button view
     */
    public void OnAddNewTaskClick(View v){
        DatePicker dp = (DatePicker)this.findViewById(R.id.date_picker);
        TimePicker tp = (TimePicker)this.findViewById(R.id.time_picker);
        int Hour, Minute;
        if(Build.VERSION.SDK_INT < 23){
            Hour = tp.getCurrentHour();
            Minute = tp.getCurrentMinute();
        } else {
            Hour = tp.getHour();
            Minute = tp.getMinute();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(), Hour, Minute);

        if (id>0) {
            taskController.update(id, textS.getText().toString(), calendar, rem.isChecked());
        }
        else
            taskController.create(textS.getText().toString(), calendar, rem.isChecked());
        startActivity(new Intent(this, MainTasksActivity.class));
    }


    @Override
    public void showItems(List<Task> items) {
        Task task = items.get(0);
        textS.setText(task.getName());
        rem.setChecked(task.hasAlarms());

            //TODO Get alarm time and  Handle calendar bug
                /*DatePicker dp = (DatePicker)this.findViewById(R.id.datePicker);
                TimePicker tp = (TimePicker)this.findViewById(R.id.timePicker);

                Date date = new Date(alarm.getTime());

                if(Build.VERSION.SDK_INT < 23){
                    tp.setCurrentHour(date.getHours());
                    tp.setCurrentMinute(date.getMinutes());
                } else{
                    tp.setHour(date.getHours());
                    tp.setMinute(date.getMinutes());
                }
                dp.updateDate(date.getYear(), date.getMonth(),date.getDay());
                Spinner sp = (Spinner)this.findViewById(R.id.spinner);
                if (alarm.interval == 60 * 1000)
                    sp.setSelection(1);
                if (alarm.interval == 60 * 1000*60)
                    sp.setSelection(2);
                if (alarm.interval == 60 * 1000*60*24)
                    sp.setSelection(3);*/

    }
}
