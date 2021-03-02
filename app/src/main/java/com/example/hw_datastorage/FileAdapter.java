package com.example.hw_datastorage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private List<Entry> files;
    public MainActivity activity;

    public void setItems(ArrayList<Entry> items){
        files = items;
    }

    public FileAdapter(MainActivity activity, Context context) {
        this.activity = activity;
        //this.files = states;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Entry entry = files.get(position);
        holder.daily_entry.setText(entry.getText());
        holder.edit_button.setOnClickListener(v -> {
            Intent intent = new Intent(activity, EditActivity.class);
//            intent.putExtra("id", entry.getId());
//            intent.putExtra("text", entry.getText());
//            intent.putExtra("date", entry.getDate());
            intent.putExtra("entry", entry);
            activity.startActivityForResult(intent, 0);
        });
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView daily_entry;
        Button edit_button, delete_button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            daily_entry = (TextView)itemView.findViewById(R.id.file_name);
            edit_button = (Button)itemView.findViewById(R.id.edit_button);
            delete_button = (Button) itemView.findViewById(R.id.edit_button);
        }
    }
}
