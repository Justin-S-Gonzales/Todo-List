package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.todolist.task_classes.Folder;
import com.example.todolist.task_classes.recyclerAdapterFolder;

import java.util.ArrayList;

public class Folders extends AppCompatActivity {
    private ArrayList<Folder> folder_list_copy = MainActivity.folder_list;
    private RecyclerView recyclerView;

    Button trashButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folders);

        trashButton = findViewById(R.id.folder_trash);

        trashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!MainActivity.delete_mode_active)
                {   //turn on delete mode
                    MainActivity.delete_mode_active = true;

                    //user notification
                    Toast.makeText(getApplicationContext(), "Delete mode activated.\nTap on a folder to delete it.", Toast.LENGTH_LONG).show();

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

        recyclerView = findViewById(R.id.task_list_recycler);

        setAdapter();
    }

    void showDeleteDialog() {
        final Dialog dialog = new Dialog(Folders.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);   //disable default title
        dialog.setCancelable(true); //user can disable dialog by clicking outside of it.
        dialog.setContentView(R.layout.delete_folder_dialog);

        //Initialize dialog views.
        Button yesButton = dialog.findViewById(R.id.yes_button);
        Button noButton = dialog.findViewById(R.id.no_button);

        //on clicking the yes button, this onClick function will be called
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();   //get rid of the dialog. (functionality coming later)
            }
        });

        //on clicking the no button, this onClick function will be called
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();   //get rid of the dialog. (functionality coming later)
            }
        });

        dialog.show();
    }

    public void createNewFolder(View view)
    {
        //turn off delete mode (in case it's on)
        MainActivity.delete_mode_active = false;

        Intent intent = new Intent(this, createFolder.class);
        startActivity(intent);
    }

    private void setAdapter()
    {
        recyclerAdapterFolder adapter = new recyclerAdapterFolder(folder_list_copy);
        RecyclerView.LayoutManager layout_manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layout_manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}