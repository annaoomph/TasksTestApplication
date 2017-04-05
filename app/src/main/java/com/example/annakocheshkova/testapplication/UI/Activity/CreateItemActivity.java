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
import android.widget.TimePicker;

import com.example.annakocheshkova.testapplication.MVC.Controller.CreateItemController;
import com.example.annakocheshkova.testapplication.MVC.View.CreateItemView;
import com.example.annakocheshkova.testapplication.Model.Alarm.AlarmInfo;
import com.example.annakocheshkova.testapplication.Model.Task;
import com.example.annakocheshkova.testapplication.R;

import java.util.Calendar;

/**
 * a view of the activity, which allows user to create a new task
 */
public class CreateItemActivity extends AppCompatActivity implements CreateItemView {

    /**
     * controller of the view
     */
    CreateItemController createItemController;

    /**
     * toolbar with menu
     */
    Toolbar toolbar;

    /**
     * editText with name of the task
     */
    EditText nameText;

    /**
     * checkbox if fire alarm
     */
    CheckBox reminderCheckBox;

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
        int id = getIntent().getIntExtra("id", -1);
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
            finish();
        return super.onOptionsItemSelected(item);
    }

    /**
     * method responding on button click when adding the new task
     * @param view button view
     */
    public void OnAddNewTaskClick(View view) {
        createItemController.onItemEditingFinished();
        startActivity(new Intent(this, MainTasksActivity.class));
    }


    @Override
    public void showItem(Task item, AlarmInfo alarm) {
        nameText.setText(item.getName());
        reminderCheckBox.setChecked(item.hasAlarms());
        DatePicker datePicker = (DatePicker)this.findViewById(R.id.date_picker);
        TimePicker timePicker = (TimePicker)this.findViewById(R.id.time_picker);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeInMillis(alarm.getTime());
        if(Build.VERSION.SDK_INT < 23){
            timePicker.setCurrentHour(calendar.get(Calendar.HOUR));
            timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        } else{
            timePicker.setHour(calendar.get(Calendar.HOUR));
            timePicker.setMinute(calendar.get(Calendar.MINUTE));
        }
        datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public String getName() {
        return nameText.getText().toString();
    }

    @Override
    public DatePicker getDate() {
        return (DatePicker)this.findViewById(R.id.date_picker);
    }

    @Override
    public TimePicker getTime() {
        return (TimePicker)this.findViewById(R.id.time_picker);
    }

    @Override
    public boolean ifFireAlarm() {
        return reminderCheckBox.isChecked();
    }
}
