package com.example.annakocheshkova.testapplication.UI.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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

    /**
     * a spinner to choose when to fire alarm
     */
    Spinner spinner;

    /**
     * editText which displays the date chosen by user
     */
    private EditText dateDisplay;

    /**
     * edittext which displays the time chosen by user
     */
    private EditText timeDisplay;

    /**
     * year that's displayed at the moment
     */
    private int chosenYear;

    /**
     * month that's displayed at the moment
     */
    private int chosenMonth;

    /**
     * day that's displayed at the moment
     */
    private int chosenDay;

    /**
     * hour that's displayed at the moment
     */
    private int chosenHour;

    /**
     * minute that's displayed at the moment
     */
    private int chosenMinute;

    /**
     * tag by which date dialog is shown
     */
    static final int DATE_DIALOG_ID = 0;

    /**
     * tag by which time dialog is shown
     */
    static final int TIME_DIALOG_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);
        getViews();
        setContent();
    }

    /**
     * get all the views needed to work with
     */
    void getViews() {
        dateDisplay = (EditText) findViewById(R.id.date_display);
        timeDisplay = (EditText) findViewById(R.id.time_display);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nameText = (EditText)this.findViewById(R.id.name_edittext);
        reminderCheckBox = (CheckBox)this.findViewById(R.id.enable_reminder);
        spinner = (Spinner)findViewById(R.id.reminder_spinner);
        setSupportActionBar(toolbar);
    }

    /**
     * set configuration of the window content
     */
    void setContent() {
        createItemController = new CreateItemController(this);
        int id = getIntent().getIntExtra("id", -1);
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

        ImageButton setDate = (ImageButton)findViewById(R.id.set_date);
        ImageButton setTime = (ImageButton)findViewById(R.id.set_time);

        setDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        dateDisplay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        setTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });

        timeDisplay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });

        Calendar c = Calendar.getInstance();

        chosenYear = c.get(Calendar.YEAR);
        chosenMonth = c.get(Calendar.MONTH);
        chosenDay = c.get(Calendar.DAY_OF_MONTH);
        chosenHour = c.get(Calendar.HOUR_OF_DAY);
        chosenMinute = c.get(Calendar.MINUTE);

        updateDisplay();

        View checkBoxClickable = findViewById(R.id.checkbox_clickable);
        checkBoxClickable.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reminderCheckBox.setChecked(!reminderCheckBox.isChecked());
            }
        });
        String[] spinnerItems = getResources().getStringArray(R.array.reminder);
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        createItemController.onViewLoaded(id);
    }

    /**
     * update date and time shown in edittext boxes
     */
    private void updateDisplay() {
        String day = String.valueOf(chosenDay);
        if (day.length() == 1)
            day = "0" + day;
        String month = String.valueOf(chosenMonth+1);
        if (month.length() == 1)
            month = "0" + month;
        String year = String.valueOf(chosenYear);
        if (year.length() == 1)
            year = "0" + year;
        String hour = String.valueOf(chosenHour);
        if (hour.length() == 1)
            hour = "0" + hour;
        String minute = String.valueOf(chosenMinute);
        if (minute.length() == 1)
            minute = "0" + minute;
        dateDisplay.setText(new StringBuilder().append(day).append("-")
                        .append(month).append("-")
                        .append(year));
        timeDisplay.setText(new StringBuilder().append(hour).append(":")
                .append(minute));
    }

    /**
     *the callback received when the user sets the date in the dialog
     */
    private DatePickerDialog.OnDateSetListener onDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    chosenYear = year;
                    chosenMonth = monthOfYear;
                    chosenDay = dayOfMonth;
                    updateDisplay();
                }
    };

    /**
     *the callback received when the user sets the time in the dialog
     */
    private TimePickerDialog.OnTimeSetListener onTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {

                public void onTimeSet(TimePicker view, int hour, int minute) {
                    chosenHour = hour;
                    chosenMinute = minute;
                    updateDisplay();
                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, onDateSetListener, chosenYear, chosenMonth, chosenDay);
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, onTimeSetListener, chosenHour, chosenMinute, false);
        }
        return null;
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
    public void showItem(Task item, int alarmTime) {
        nameText.setText(item.getName());
        reminderCheckBox.setChecked(item.hasAlarms());
        spinner.setSelection(alarmTime);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeInMillis(item.getTime());
        chosenYear = calendar.get(Calendar.YEAR);
        chosenMonth = calendar.get(Calendar.MONTH);
        chosenDay = calendar.get(Calendar.DAY_OF_MONTH);
        chosenMinute = calendar.get(Calendar.MINUTE);
        chosenHour = calendar.get(Calendar.HOUR_OF_DAY);
        updateDisplay();
    }

    @Override
    public String getName() {
        return nameText.getText().toString();
    }

    @Override
    public int getYear() {
        return chosenYear;
    }

    @Override
    public int getMonth() {
        return chosenMonth;
    }

    @Override
    public int getDay() {
        return chosenDay;
    }

    @Override
    public int getHour() {
        return chosenHour;
    }

    @Override
    public int getMinute() {
        return chosenMinute;
    }

    @Override
    public boolean ifFireAlarm() {
        return reminderCheckBox.isChecked();
    }

    @Override
    public int getReminderTime() {
        return spinner.getSelectedItemPosition();
    }
}
