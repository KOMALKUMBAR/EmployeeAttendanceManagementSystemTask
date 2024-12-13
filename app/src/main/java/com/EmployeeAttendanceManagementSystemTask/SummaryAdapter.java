package com.EmployeeAttendanceManagementSystemTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.AttendanceViewHolder> {
    private final List<AttendanceRecord> records;

    public SummaryAdapter(List<AttendanceRecord> records) {
        this.records = records;
    }


    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.summary_item_layout, parent, false);
        return new AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewHolder holder, int position) {
        AttendanceRecord record = records.get(position);

        holder.txtEmployeeName.setText(record.getName());
        holder.txtCheckInTime.setText("Check-In: " + record.getCheckInTime());

        // Load image from file path
        Bitmap imageBitmap = BitmapFactory.decodeByteArray(record.getImagePath(), 0, record.getImagePath().length);
        holder.imgEmployeePhoto.setImageBitmap(imageBitmap);
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    static class AttendanceViewHolder extends RecyclerView.ViewHolder {
        TextView txtEmployeeName, txtCheckInTime;
        ImageView imgEmployeePhoto;

        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEmployeeName = itemView.findViewById(R.id.tvEmployeeNameItem);
            txtCheckInTime = itemView.findViewById(R.id.tvCheckInTimeItem);
            imgEmployeePhoto = itemView.findViewById(R.id.imgAttendanceItem);
        }
    }
}
