package com.example.todolist.task_classes;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.MainActivity;
import com.example.todolist.R;
import com.example.todolist.taskList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class recyclerAdapterTask extends RecyclerView.Adapter<recyclerAdapterTask.MyViewHolder>{
    private ArrayList<Folder> folder_list_copy;
    private ArrayList<Task> task_list;

    //constructor
    public recyclerAdapterTask(ArrayList<Folder> folder_list_copy)
    {
        this.folder_list_copy = folder_list_copy;
        this.task_list = folder_list_copy.get(MainActivity.current_folder_index).getTasks();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView task_title_text;
        private TextView due_date_text;
        private TextView repeat_condition;
        private ImageView checkbox;
        private Context context;

        public MyViewHolder(Context context, View view) {
            super(view);
            this.task_title_text = (TextView) view.findViewById(R.id.titleText);
            this.due_date_text = (TextView) view.findViewById(R.id.dueText);
            this.checkbox = (ImageView) view.findViewById(R.id.checkboxImageView);
            this.repeat_condition = (TextView) view.findViewById(R.id.repeatText);
            // Store the context
            this.context = context;
            // Attach a click listener to the entire row view
            view.setOnClickListener(this);
        }

        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                Task task_temp = task_list.get(position);
                // We can access the data within the views

                if(!MainActivity.delete_mode_active) {  //if delete mode isn't on
                    if (MainActivity.current_folder_index != 1) {    //if not in completed folder
                        if (Objects.equals(task_temp.getRepeat_data(), "no_repeat")) {     //if the task isn't set to repeat
                            //Show user that task was marked as complete
                            Toast.makeText(context, "Task marked as complete.", Toast.LENGTH_SHORT).show();

                            //add checked member to completed folder
                            ArrayList<Task> completed_tasks_temp = folder_list_copy.get(1).getTasks(); //completed will always be index 1, just as all will always be index 0
                            completed_tasks_temp.add(task_list.get(position));

                            //delete checked member
                            task_list.remove(position);

                            //update recyclerView
                            recyclerAdapterTask.this.notifyItemRemoved(position);
                        } else {   //if the task repeats
                            //Show user that task was marked as complete
                            Toast.makeText(context, "Task marked as complete.", Toast.LENGTH_SHORT).show();

                            //add checked member to completed folder
                            ArrayList<Task> completed_tasks_temp = folder_list_copy.get(1).getTasks(); //completed will always be index 1, just as all will always be index 0
                            completed_tasks_temp.add(task_list.get(position));

                            //repeat behavior
                            //really should have made this an enum. oh well
                            String repeat_condition = task_temp.getRepeat_data();
                            Calendar temp_cal = task_temp.getDue_time();
                            if (repeat_condition == "daily") {
                                temp_cal.add(Calendar.DAY_OF_MONTH, 1);
                                task_temp.setDue_time(temp_cal);
                            } else if (repeat_condition == "weekly") {
                                temp_cal.add(Calendar.WEEK_OF_YEAR, 1);
                                task_temp.setDue_time(temp_cal);
                            } else if (repeat_condition == "monthly") {
                                temp_cal.add(Calendar.MONTH, 1);
                                task_temp.setDue_time(temp_cal);
                            } else if (repeat_condition == "yearly") {
                                temp_cal.add(Calendar.YEAR, 1);
                                task_temp.setDue_time(temp_cal);
                            } else {
                                //default case; shouldn't happen
                            }

                            //update recyclerView
                            recyclerAdapterTask.this.notifyItemChanged(position);
                        }
                    }
                }
                else
                {   //task deletion logic
                    if (Objects.equals(task_temp.getRepeat_data(), "no_repeat")) {     //if the task isn't set to repeat
                        //Show user that task was deleted
                        Toast.makeText(context, "Task deleted.", Toast.LENGTH_SHORT).show();

                        //delete checked member
                        task_list.remove(position);

                        //update recyclerView
                        recyclerAdapterTask.this.notifyItemRemoved(position);
                    } else {   //if the task repeats
                        //this is the showDialogDelete function copied and pasted into here.
                        final Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);   //disable default title
                        dialog.setCancelable(true); //user can disable dialog by clicking outside of it.
                        dialog.setContentView(R.layout.delete_task_dialog);

                        //Initialize dialog views.
                        Button deleteOneButton = dialog.findViewById(R.id.delete_one_button);
                        Button deleteAllButton = dialog.findViewById(R.id.delete_all_button);
                        Button cancelButton = dialog.findViewById(R.id.cancel_button);

                        //on clicking the delete one button, this onClick function will be called
                        deleteOneButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Show user that task was deleted
                                Toast.makeText(context, "Task deleted.", Toast.LENGTH_SHORT).show();

                                //repeat behavior
                                String repeat_condition = task_temp.getRepeat_data();
                                Calendar temp_cal = task_temp.getDue_time();
                                if (repeat_condition == "daily") {
                                    temp_cal.add(Calendar.DAY_OF_MONTH, 1);
                                    task_temp.setDue_time(temp_cal);
                                } else if (repeat_condition == "weekly") {
                                    temp_cal.add(Calendar.WEEK_OF_YEAR, 1);
                                    task_temp.setDue_time(temp_cal);
                                } else if (repeat_condition == "monthly") {
                                    temp_cal.add(Calendar.MONTH, 1);
                                    task_temp.setDue_time(temp_cal);
                                } else if (repeat_condition == "yearly") {
                                    temp_cal.add(Calendar.YEAR, 1);
                                    task_temp.setDue_time(temp_cal);
                                } else {
                                    //default case; shouldn't happen
                                }

                                dialog.dismiss();   //(functionality coming later)

                                //update recyclerView
                                recyclerAdapterTask.this.notifyItemChanged(position);
                            }
                        });

                        //on clicking the delete all button, this onClick function will be called
                        deleteAllButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Show user that task was deleted
                                Toast.makeText(context, "All recurring tasks deleted.", Toast.LENGTH_SHORT).show();

                                //delete checked member
                                task_list.remove(position);

                                dialog.dismiss();   //(functionality coming later)

                                //update recyclerView
                                recyclerAdapterTask.this.notifyItemRemoved(position);
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
            }
        }

        void showDeleteDialog() {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);   //disable default title
            dialog.setCancelable(true); //user can disable dialog by clicking outside of it.
            dialog.setContentView(R.layout.delete_task_dialog);

            //Initialize dialog views.
            Button deleteOneButton = dialog.findViewById(R.id.delete_one_button);
            Button deleteAllButton = dialog.findViewById(R.id.delete_all_button);
            Button cancelButton = dialog.findViewById(R.id.cancel_button);

            //on clicking the delete one button, this onClick function will be called
            deleteOneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();   //(functionality coming later)
                }
            });

            //on clicking the delete all button, this onClick function will be called
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

    //recycler adapter stuff
    @NonNull
    @Override
    public recyclerAdapterTask.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_format,parent,false);
        return new MyViewHolder(parent.getContext(),itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapterTask.MyViewHolder holder, int position) {
        String task_title = task_list.get(position).getTitle();
        holder.task_title_text.setText(task_title);
        Calendar task_due_date = task_list.get(position).getDue_time();
        holder.due_date_text.setText(calendarToString(task_due_date));
        String repeat_val = task_list.get(position).getRepeat_data();
        holder.repeat_condition.setText(repeatToString(repeat_val));

        if(MainActivity.current_folder_index == 1)  //if in completed folder, set checkboxes as completed
            holder.checkbox.setImageResource(android.R.drawable.checkbox_on_background);
    }

    @Override
    public int getItemCount() {
        return task_list.size();
    }

    //methods for displaying calendar values as strings
    private String calendarToString(Calendar cal)
    {
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR);
        if(hour == 0)   //display 12 o clock correctly
            hour = 12;
        int minute = cal.get(Calendar.MINUTE);
        int am_pm = cal.get(Calendar.AM_PM);
        return makeDateString(am_pm, minute, hour, day, month, year);
    }

    private String makeDateString(int am_pm, int minute, int hour, int day, int month, int year)
    {
        String minute_display;
        if(minute < 10)
            minute_display = "0" + minute;
        else
            minute_display = String.valueOf(minute);

        if(am_pm == 0) //if in the AM, display as such
            return getMonthFormat(month) + " " + day + " " + year + ", " + hour + ":" + minute_display + " AM";
        else    //PM
           return getMonthFormat(month) + " " + day + " " + year + ", " + hour + ":" + minute_display + " PM";
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

    private String repeatToString(String repeat_val)
    {
        if(repeat_val == "no_repeat")
            return "";
        else if(repeat_val == "daily")
            return "Repeats daily";
        else if(repeat_val == "weekly")
            return "Repeats weekly";
        else if(repeat_val == "monthly")
            return "Repeats monthly";
        else if(repeat_val == "yearly")
            return "Repeats yearly";

        return "Error"; //default case; shouldn't happen
    }

}
