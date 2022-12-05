package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.todolist.task_classes.Folder;    //imports user-defined Folder class
import com.example.todolist.task_classes.Task;    //imports user-defined Task class

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Folder> folder_list = new ArrayList<Folder>(); //list of folders. static so you can access it anywhere
    public static ArrayList getFolderList() {
        return folder_list;
    }   //getter for folder_list. kinda useless.
    public static void setFolderList(ArrayList temp)
    {
        folder_list = temp;
    } //setter for folder_list. kinda useless.

    public static int current_folder_index;     //keeps track of the current folder for taskList. initialized in onCreate
    public static boolean delete_mode_active;   //keeps track of delete mode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //two tasks to put in the task list for debug
        /*
        ArrayList<Task> tasks = new ArrayList<Task>();
        Calendar due_time_temp = Calendar.getInstance();
        Task task1 = new Task("Take out trash",due_time_temp,"no_repeat");
        Task task2 = new Task("Call back XXXXXX",due_time_temp,"no_repeat");
        tasks.add(task1);
        tasks.add(task2);
         */

        //initialize all folder and completed folder and put them in folder_list
        Folder all_folder = new Folder(getResources().getString(R.string.folder_all_name),0xFFB3B3B3,new ArrayList());
        folder_list.add(all_folder);

        Folder completed_folder = new Folder(getResources().getString(R.string.folder_completed_name),0xFFB3B3B3,new ArrayList());
        folder_list.add(completed_folder);

        //set current_folder to all by default
        current_folder_index = 0;

        //move to Folders immediately
        Intent intent = new Intent(this, Folders.class);
        startActivity(intent);
    }
}