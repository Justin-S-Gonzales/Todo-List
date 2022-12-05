package com.example.todolist.task_classes;

import java.util.ArrayList;
import java.util.Vector;

public class Folder
{
    private String name;    //name of the folder
    private int color;      //color that the folder will be displayed as
    private ArrayList tasks;   //vector of tasks in the folder
    private boolean completed = false;

    //constructor
    public Folder(String name, int color, ArrayList tasks) {
        this.name = name;
        this.color = color;
        this.tasks = tasks;
        this.completed = completed;
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public ArrayList getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList tasks) {
        this.tasks = tasks;
    }
}
