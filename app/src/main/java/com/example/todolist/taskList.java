package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolist.task_classes.Folder;
import com.example.todolist.task_classes.Task;
import com.example.todolist.task_classes.recyclerAdapterFolder;
import com.example.todolist.task_classes.recyclerAdapterTask;

import java.util.ArrayList;
import java.util.Vector;

public class taskList extends AppCompatActivity {
    private Button trashButton;
    private TextView f_name;
    private ArrayList<Folder> folder_list = MainActivity.getFolderList();  //list of folders
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        //set up trash button functionality
        trashButton = findViewById(R.id.task_trash);
        trashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!MainActivity.delete_mode_active)
                {   //turn on delete mode
                    MainActivity.delete_mode_active = true;

                    //user notification
                    Toast.makeText(getApplicationContext(), "Delete mode activated.\nTap on a task to delete it.", Toast.LENGTH_LONG).show();

                    //change background color
                    view.getRootView().setBackgroundColor(0xFFFFC0BA);
                    //change trashcan icon color
                    view.setBackgroundTintList(ColorStateList.valueOf(0xFFFFC0BA));
                }
                else
                {   //turn off delete mode
                    MainActivity.delete_mode_active = false;

                    //user notification
                    Toast.makeText(getApplicationContext(), "Delete mode deactivated.", Toast.LENGTH_SHORT).show();

                    //change background color
                    view.getRootView().setBackgroundColor(0xFFFFFFFF);
                    //change trashcan icon color
                    view.setBackgroundTintList(ColorStateList.valueOf(0xFFFFFFFF));
                }
            }
        });

        //set up task list data
        //set folder name in toolbar
        f_name = findViewById(R.id.folder_name);
        Folder folder_temp = folder_list.get(MainActivity.current_folder_index);
        f_name.setText(folder_temp.getName());

        recyclerView = findViewById(R.id.task_list_recycler); //initialize recycler

        //set color of toolbar
        toolbar = findViewById(R.id.task_list_toolbar);
        toolbar.setBackgroundColor(folder_temp.getColor());

        setAdapter();
    }

    void showDeleteDialog() {
        final Dialog dialog = new Dialog(taskList.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);   //disable default title
        dialog.setCancelable(true); //user can disable dialog by clicking outside of it.
        dialog.setContentView(R.layout.delete_task_dialog);

        //Initialize dialog views.
        Button deleteOneButton = dialog.findViewById(R.id.delete_one_button);
        Button deleteAllButton = dialog.findViewById(R.id.delete_all_button);
        Button cancelButton = dialog.findViewById(R.id.cancel_button);

        //on clicking the yes button, this onClick function will be called
        deleteOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();   //(functionality coming later)
            }
        });

        //on clicking the no button, this onClick function will be called
        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();   //(functionality coming later)
            }
        });

        //on clicking the no button, this onClick function will be called
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();   //get rid of the dialog.
            }
        });

        dialog.show();
    }

    public void onBackButtonClicked(View view)
    {
        //turn off delete mode (if it's on)
        MainActivity.delete_mode_active = false;

        Intent intent = new Intent(this, Folders.class);
        startActivity(intent);
    }

    public void createNewTask(View view)
    {
        //turn off delete mode (if it's on)
        if(MainActivity.current_folder_index != 1) {    //if not in the completed folder
            MainActivity.delete_mode_active = false;

            Intent intent = new Intent(this, createTask.class);
            startActivity(intent);
        }
        else
        {
            //tell user they cannot make new tasks in the completed folder.
            Toast.makeText(getApplicationContext(), "New tasks cannot be created in the completed folder.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setAdapter()
    {
        recyclerAdapterTask adapter = new recyclerAdapterTask(folder_list);
        RecyclerView.LayoutManager layout_manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layout_manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}