package com.example.todolist.task_classes;

import java.util.Calendar;

public class Task
{
    private String Title;       //title of the task
    private Calendar due_time;  //date and time the task is due
    private String repeat_data; //determines if data should be repeated or not, and how

    //constructor
    public Task(String title, Calendar due_time, String repeat_data) {
        Title = title;
        this.due_time = due_time;
        this.repeat_data = repeat_data;
    }

    //getters and setters
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Calendar getDue_time() {
        return due_time;
    }

    public void setDue_time(Calendar due_time) {
        this.due_time = due_time;
    }

    public String getRepeat_data() {
        return repeat_data;
    }

    public void setRepeat_data(String repeat_data) {
        this.repeat_data = repeat_data;
    }
}
