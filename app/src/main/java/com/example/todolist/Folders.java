package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Folders extends AppCompatActivity {

    Button trashButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folders);

        trashButton = findViewById(R.id.folder_trash);

        trashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteDialog();
            }
        });
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
}