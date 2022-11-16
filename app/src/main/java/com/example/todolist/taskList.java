package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class taskList extends AppCompatActivity {

    Button trashButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        trashButton = findViewById(R.id.task_trash);

        trashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteDialog();
            }
        });
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
}