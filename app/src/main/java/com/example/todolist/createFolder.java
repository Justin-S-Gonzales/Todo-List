package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.todolist.task_classes.Folder;
import com.example.todolist.task_classes.Task;

import java.util.ArrayList;

public class createFolder extends AppCompatActivity {
    private ArrayList<Folder> folder_list = MainActivity.getFolderList();  //list of folders

    private EditText f_name;
    private int color = 0xFFFF3D33;  //stores the hex val of the color to be used for the folder. must be initialized to the first value of the list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_folder);

        f_name = findViewById(R.id.editName);
    }

    public void onBackButtonClicked(View view)
    {
        Intent intent = new Intent(this, Folders.class);
        startActivity(intent);
    }

    //activates when a radio button in activity_create_task is checked
    public void onRadioButtonClicked(View view)
    {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.repeat_radio_1:
                if (checked)
                    color = 0xFFFF3D33;
                break;
            case R.id.repeat_radio_2:
                if (checked)
                    color = 0xFFFF7B34;
                break;
            case R.id.repeat_radio_3:
                if (checked)
                    color = 0xFFFFF12E;
                break;
            case R.id.repeat_radio_4:
                if (checked)
                    color = 0xFF24FF1B;
                break;
            case R.id.repeat_radio_5:
                if (checked)
                    color = 0xFF11F4FF;
                break;
            case R.id.repeat_radio_6:
                if (checked)
                    color = 0xFFD32DFF;
                break;
            case R.id.repeat_radio_7:
                if (checked)
                    color = 0xFFFF4FF9;
                break;
            case R.id.repeat_radio_8:
                if (checked)
                    color = 0xFFFACFAC;
                break;
        }
    }

    public void submitData(View view)
    {
        Folder temp_folder = new Folder("Default",0,new ArrayList());

        //get title
        temp_folder.setName(f_name.getText().toString());

        //set color
        temp_folder.setColor(color);

        //put new folder in folder_list
        folder_list.add(temp_folder);

        //move back to folders screen
        Intent intent = new Intent(this, Folders.class);
        startActivity(intent);
    }
}