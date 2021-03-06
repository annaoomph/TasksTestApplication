package com.example.annakocheshkova.testapplication.ui.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.annakocheshkova.testapplication.mvc.controller.CreateTaskController;
import com.example.annakocheshkova.testapplication.mvc.view.CreateTaskView;
import com.example.annakocheshkova.testapplication.model.Task;
import com.example.annakocheshkova.testapplication.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A view of the activity, which allows user to create a new task
 */
public class CreateTaskActivity extends BaseActivity implements CreateTaskView {

    /**
     * Controller of the view
     */
    CreateTaskController createTaskController;

    /**
     * EditText with name of the task
     */
    EditText nameText;

    /**
     * Checkbox if fire alarm
     */
    CheckBox reminderCheckBox;

    /**
     * A spinner to choose when to fire alarm
     */
    Spinner spinner;

    /**
     * EditText which displays the date chosen by user
     */
    private EditText dateDisplay;

    /**
     * Edittext which displays the time chosen by user
     */
    private EditText timeDisplay;

    /**
     * Year that's displayed at the moment
     */
    private int chosenYear;

    /**
     * Month that's displayed at the moment
     */
    private int chosenMonth;

    /**
     * Day that's displayed at the moment
     */
    private int chosenDay;

    /**
     * Hour that's displayed at the moment
     */
    private int chosenHour;

    /**
     * Minute that's displayed at the moment
     */
    private int chosenMinute;

    /**
     * A tag by which date dialog is shown
     */
    static final int DATE_DIALOG_ID = 0;

    /**
     * A tag by which time dialog is shown
     */
    static final int TIME_DIALOG_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViews();
        setContent();
    }

    @Override
    int getLayoutResId() {
        return R.layout.activity_create_item;
    }

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

    /**
     * The callback received when the user sets the date in the dialog
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
     * The callback received when the user sets the time in the dialog
     */
    private TimePickerDialog.OnTimeSetListener onTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {

                public void onTimeSet(TimePicker view, int hour, int minute) {
                    chosenHour = hour;
                    chosenMinute = minute;
                    updateDisplay();
                }
            };

    /**
     * Gets all the views needed to work with
     */
    void getViews() {
        dateDisplay = (EditText) findViewById(R.id.date_display);
        timeDisplay = (EditText) findViewById(R.id.time_display);
        nameText = (EditText)this.findViewById(R.id.name_edittext);
        reminderCheckBox = (CheckBox)this.findViewById(R.id.enable_reminder);
        spinner = (Spinner)findViewById(R.id.reminder_spinner);
    }

    /**
     * Sets configuration of the window content
     */
    void setContent() {
        createTaskController = new CreateTaskController(this);
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
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        createTaskController.onViewLoaded(getId());
    }

    @Override
    protected String getToolBarTitle() {
        return getString((getId() > 0) ? R.string.edit_item_title : R.string.enter_new_title);
    }

    /**
     * Returns the task id
     * @return task id
     */
    private int getId() {
        return getIntent().getIntExtra("id", -1);
    }

    /**
     * Updates date and time shown in edittext boxes
     */
    private void updateDisplay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(chosenYear, chosenMonth, chosenDay, chosenHour, chosenMinute);
        DateFormat dateFormat = SimpleDateFormat.getDateInstance();
        DateFormat timeFormat = SimpleDateFormat.getTimeInstance();
        Date date = new Date(calendar.getTimeInMillis());
        String dateString = dateFormat.format(date);
        String timeString = timeFormat.format(date);
        dateDisplay.setText(dateString);
        timeDisplay.setText(timeString);
    }

    /**
     * Responds to confirmation button click
     * @param view button view
     */
    public void onAddNewTaskClick(View view) {
        createTaskController.onItemEditingFinished();
    }

    @Override
    public void showItem(Task item, long timePeriod) {
        nameText.setText(item.getName());
        reminderCheckBox.setChecked(item.hasAlarm());
        int timePeriodInMinutes =  (int) timePeriod / 1000 / 60;
        int alarmPosition = 0; //position in spinner adapter
        switch (timePeriodInMinutes) {
            case 1: alarmPosition = 1; break;
            case 10: alarmPosition = 2; break;
            case 60: alarmPosition = 3; break;
            case 60 * 24: alarmPosition = 4; break;
        }
        spinner.setSelection(alarmPosition);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeInMillis(item.getTime());
        chosenYear = calendar.get(Calendar.YEAR);
        chosenMonth = calendar.get(Calendar.MONTH);
        chosenDay = calendar.get(Calendar.DAY_OF_MONTH);
        chosenMinute = calendar.get(Calendar.MINUTE) + 1;
        chosenHour = calendar.get(Calendar.HOUR_OF_DAY);
        updateDisplay();

    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void showWrongTimeError() {
        showToast(getString(R.string.incorrect_time_error));
    }

    @Override
    public void showWrongAlarmTimeError() {
        showToast(getString(R.string.incorrect_alarm_time_error));
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
    public long getReminderTime() {
        int alarmTime = spinner.getSelectedItemPosition();
        long timePeriod = 0;
        switch (alarmTime) {
            case 1:
                timePeriod = 60 * 1000;
                break;
            case 2:
                timePeriod = 60 * 1000 * 10;
                break;
            case 3:
                timePeriod = 60 * 1000 * 60;
                break;
            case 4:
                timePeriod = 60 * 1000 * 60 * 24;
                break;
        }
        return timePeriod;
    }
}
