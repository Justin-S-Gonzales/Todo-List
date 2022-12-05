package com.example.todolist.task_classes;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.Folders;
import com.example.todolist.MainActivity;
import com.example.todolist.R;
import com.example.todolist.taskList;
import com.example.todolist.task_classes.Folder;    //imports user-defined Folder class
import java.util.ArrayList;

public class recyclerAdapterFolder extends RecyclerView.Adapter<recyclerAdapterFolder.MyViewHolder>{
    private ArrayList<Folder> folder_list_copy;

    //constructor
    public recyclerAdapterFolder(ArrayList<Folder> folder_list_copy)
    {
        this.folder_list_copy = folder_list_copy;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Button folder_button;
        private Context context;

        public MyViewHolder(Context context, View view) {
            super(view);
            this.folder_button = (Button) view.findViewById(R.id.folderButton);
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
                Folder folder_temp = folder_list_copy.get(position);
                // We can access the data within the views

                if(!MainActivity.delete_mode_active) {    //if not in delete mode
                    //set current_folder_index to this index
                    MainActivity.current_folder_index = position;

                    //move to taskList
                    Intent intent = new Intent(context, taskList.class);
                    context.startActivity(intent);
                }else{
                    if(position > 1)    //if not on the all or completed folders
                    {
                        //delete message (make sure the user's okay with deleting all the tasks in the folder
                        final Dialog dialog = new Dialog(context);
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
                                //delete the folder (java has garbage collection so no need to worry about the tasks list)
                                MainActivity.folder_list.remove(position);

                                dialog.dismiss();   //get rid of the dialog.

                                //update recycler
                                recyclerAdapterFolder.this.notifyItemRemoved(position);
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
                    else
                    {   //tell user you can't delete that folder
                        if(position == 0)   //all folder
                            Toast.makeText(context, "The 'ALL' folder cannot be deleted.", Toast.LENGTH_SHORT).show();
                        else    //completed folder
                            Toast.makeText(context, "The 'COMPLETED' folder cannot be deleted.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    @NonNull
    @Override
    public recyclerAdapterFolder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.folder_format,parent,false);
        return new MyViewHolder(parent.getContext(),itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapterFolder.MyViewHolder holder, int position) {
        String folder_name = folder_list_copy.get(position).getName();
        holder.folder_button.setText(folder_name);
        holder.folder_button.setBackgroundColor(folder_list_copy.get(position).getColor());
    }

    @Override
    public int getItemCount() {
        return folder_list_copy.size();
    }

}
