package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.MyViewHolder> {
    private ArrayList<MyTask> tasksList;
    private Context context;
    private ITaskItemListener taskItemListener;

    public MyTaskAdapter(Context context, ArrayList<MyTask> tasksList, ITaskItemListener taskItemListener) {
        this.tasksList = tasksList;
        this.context = context;
        this.taskItemListener = taskItemListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_task_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyTask myTask = tasksList.get(position);
        holder.tvTitle.setText(myTask.getTitle());
        holder.tvContent.setText(myTask.getContent());
        holder.cbComplete.setChecked(myTask.getComplete() != 0);

        holder.myRow.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("myTask", myTask);
            context.startActivity(intent);
        });

        holder.cbComplete.setOnCheckedChangeListener((view, checked) -> {
            if (taskItemListener != null) {
                taskItemListener.handleTaskCompleteCheckedChange(myTask, checked);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (tasksList != null) {
            return tasksList.size();
        }

        return 0;
    }

    public interface ITaskItemListener {
        void handleTaskCompleteCheckedChange(MyTask myTask, boolean isChecked);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CardView myRow;
        private TextView tvTitle, tvContent;
        private CheckBox cbComplete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            myRow = itemView.findViewById(R.id.my_task_item_layout);
            tvTitle = itemView.findViewById(R.id.tv_task_title);
            tvContent = itemView.findViewById(R.id.tv_task_content);
            cbComplete = itemView.findViewById(R.id.cb_task_complete);
        }
    }
}
