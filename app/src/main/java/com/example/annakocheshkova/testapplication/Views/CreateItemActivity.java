package com.example.annakocheshkova.testapplication.Views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.annakocheshkova.testapplication.Controllers.AlarmController;
import com.example.annakocheshkova.testapplication.Controllers.TaskController;
import com.example.annakocheshkova.testapplication.Models.AlarmInfo;
import com.example.annakocheshkova.testapplication.Models.Task;
import com.example.annakocheshkova.testapplication.R;

import java.util.Calendar;

/**
 * a view of the activity, which allows user to create a new task
 */
public class CreateItemActivity extends AppCompatActivity {

    TaskController cc;
    AlarmController ac;
    Toolbar toolbar;
    TextView textS;
    CheckBox rem;
    CheckBox interv;
    Spinner sp;
    int intervalDuration = 60 * 1000;
    int id = 0;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);
        getViews();
        getControllers();
        setContent();
    }

    void getControllers(){
        cc = new TaskController(this);
        ac = new AlarmController(this);
    }

    void setContent(){
        id = getIntent().getIntExtra("id", 0);
        task = new Task();
        if (id>0)
            task = cc.get(id);
        ActionBar actionBar = getSupportActionBar();
        if (id>0)
        {
            if (actionBar!=null)
                actionBar.setTitle(R.string.edit);
            textS.setText(task.getName());
            rem.setChecked(task.hasAlarms());
            if (task.hasAlarms())
            {
                AlarmInfo alarm = ac.get(task.getID());
                interv.setChecked(alarm.getInterval()>0);
                //TODO Handle calendar bug
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
        else
            if (actionBar!=null)
                actionBar.setTitle(R.string.edit);
        if (actionBar!=null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        ArrayAdapter<String> a = new ArrayAdapter(this,android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.intervals));
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(a);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View itemClicked, int position,
                                       long id) {
                if (position == 0)
                    intervalDuration = 60 * 1000;
                if (position == 1)
                    intervalDuration = 60*60*1000;
                if (position == 2)
                    intervalDuration = 60*60*1000*24;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });
    }

    void getViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        textS = (EditText)this.findViewById(R.id.name_edittext);
        rem = (CheckBox)this.findViewById(R.id.enable_reminder);
        setSupportActionBar(toolbar);
        sp = (Spinner)this.findViewById(R.id.spinner);
        interv = (CheckBox)this.findViewById(R.id.interval);
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
        Task newCat;
        if (id>0) {
            newCat = task;
            newCat.setName(textS.getText().toString());
        }
        else
            newCat = new Task(textS.getText().toString());
        ScheduleNotification(newCat, (id>0));
        //TODO Change method signature
        startActivity(new Intent(this, MainTasksActivity.class));
    }

    void ScheduleNotification(Task newCat, boolean update) {
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
        int interval = (interv.isChecked())?10:-1;
        cc.addOrUpdateTask(newCat, intervalDuration, calendar, interval, update, rem.isChecked());
    }
}
