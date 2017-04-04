package com.example.annakocheshkova.testapplication.UI.Activity;

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

import com.example.annakocheshkova.testapplication.MVC.Controller.CreateItemController;
import com.example.annakocheshkova.testapplication.MVC.View.CreateItemView;
import com.example.annakocheshkova.testapplication.Model.Task;
import com.example.annakocheshkova.testapplication.R;

import java.util.Calendar;

/**
 * a view of the activity, which allows user to create a new task
 */
public class CreateItemActivity extends AppCompatActivity implements CreateItemView {

    CreateItemController createItemController;
    Toolbar toolbar;
    TextView nameText;
    CheckBox reminderCheckBox;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);
        getViews();
        setContent();
    }

    /**
     * set configuration of the window content
     */
    void setContent() {
        createItemController = new CreateItemController(this);
        id = getIntent().getIntExtra("id", 0);
        if (id > 0)
            createItemController.onViewLoaded(id);
        ActionBar actionBar = getSupportActionBar();
        if (id > 0) {
            if (actionBar != null)
                actionBar.setTitle(R.string.edit);
        } else {
            if (actionBar != null)
                actionBar.setTitle(R.string.edit);
        }
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * onViewLoaded all the views needed to work with
     */
    void getViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nameText = (EditText)this.findViewById(R.id.name_edittext);
        reminderCheckBox = (CheckBox)this.findViewById(R.id.enable_reminder);
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
     * @param view button view
     */
    public void OnAddNewTaskClick(View view) {
        DatePicker dp = (DatePicker)this.findViewById(R.id.date_picker);
        TimePicker tp = (TimePicker)this.findViewById(R.id.time_picker);
        int hour, minute;
        if(Build.VERSION.SDK_INT < 23) {
            hour = tp.getCurrentHour();
            minute = tp.getCurrentMinute();
        } else {
            hour = tp.getHour();
            minute = tp.getMinute();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(), hour, minute);

        if (id>0) {
            createItemController.update(id, nameText.getText().toString(), calendar, reminderCheckBox.isChecked());
        } else {
            createItemController.create(nameText.getText().toString(), calendar, reminderCheckBox.isChecked());
        }
        startActivity(new Intent(this, MainTasksActivity.class));
    }

    @Override
    public void showItem(Task item) {
        nameText.setText(item.getName());
        reminderCheckBox.setChecked(item.hasAlarms());

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
