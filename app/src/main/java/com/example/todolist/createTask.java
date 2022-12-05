package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.app.DatePickerDialog;
import android.widget.Button;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;

import com.example.todolist.task_classes.Folder;
import com.example.todolist.task_classes.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class createTask extends AppCompatActivity {
    private ArrayList<Folder> folder_list = MainActivity.getFolderList();  //list of folders

    //title vars
    private EditText titleText;
    //date vars
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private Calendar temp_cal;  //used in retrieving calendar data from the date picker
    //time vars
    private Button timeButton;
    int hour, minute;
    //repeat vars
    private String repeat_val = "no_repeat";    //has to be initialized like this because the code is spaghetti

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        titleText = findViewById(R.id.enterTitle);
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        timeButton = findViewById(R.id.timeButton);
        temp_cal = Calendar.getInstance();  //initialize temp_cal
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                temp_cal.set(year,month,day);   //store data
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                int year = temp_cal.get(Calendar.YEAR);
                int month = temp_cal.get(Calendar.MONTH);
                int day = temp_cal.get(Calendar.DAY_OF_MONTH);
                temp_cal.set(year,month,day,selectedHour,selectedMinute);   //store data
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
            }
        };

        // int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, /*style,*/ onTimeSetListener, hour, minute, false);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void onBackButtonClicked(View view)
    {
        //go back to taskList
        Intent intent = new Intent(this, taskList.class);
        startActivity(intent);
    }

    //activates when a radio button in activity_create_task is checked
    //sets repeat behavior for the task
    public void onRadioButtonClicked(View view)
    {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.repeat_radio_1:
                if (checked)
                    repeat_val = "no_repeat";
                    break;
            case R.id.repeat_radio_2:
                if (checked)
                    repeat_val = "daily";
                    break;
            case R.id.repeat_radio_3:
                if (checked)
                    repeat_val = "weekly";
                    break;
            case R.id.repeat_radio_4:
                if (checked)
                    repeat_val = "monthly";
                    break;
            case R.id.repeat_radio_5:
                if (checked)
                    repeat_val = "yearly";
                    break;
            default:
                repeat_val = "no_repeat";
                break;
        }
    }

    //submit the form data
    public void submitData(View view)
    {
        ArrayList<Task> task_list = folder_list.get(MainActivity.current_folder_index).getTasks();
        Task temp_task = new Task("Default Title",Calendar.getInstance(),"no_repeat");   //default values in case the user didn't enter them

        //add title
        temp_task.setTitle(titleText.getText().toString()); //default title would be a good feature

        //add due date
        temp_task.setDue_time(temp_cal);

        //add repeat data
        temp_task.setRepeat_data(repeat_val);

        //put task in folder's array
        task_list.add(temp_task);

        //if not already in the ALL folder, add the task to the all folder as well (NOTE: this isn't how the ALL folder should work, but oh well. this is a copy of the task, not the task itself.)
        ArrayList<Task> temp = MainActivity.folder_list.get(0).getTasks();
        temp.add(temp_task);

        //return to taskList
        Intent intent = new Intent(this, taskList.class);
        startActivity(intent);
    }
}