package com.EmployeeAttendanceManagementSystemTask;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SummaryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SummaryAdapter summaryAdapter;
    private AttendanceDBHelper attendanceDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        recyclerView = findViewById(R.id.recyclerViewAttendance);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        attendanceDBHelper = new AttendanceDBHelper(this);

        loadAttendanceData();
    }

    private void loadAttendanceData() {
        Cursor cursor = attendanceDBHelper.getAllAttendance();

        if (cursor == null || cursor.getCount() == 0) {
            Toast.makeText(this, "No attendance records found", Toast.LENGTH_SHORT).show();
            return;
        }

        List<AttendanceRecord> records = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String latitude = cursor.getString(cursor.getColumnIndexOrThrow("latitude"));
            String longitude = cursor.getString(cursor.getColumnIndexOrThrow("longitude"));
            String timeIn = cursor.getString(cursor.getColumnIndexOrThrow("time_in"));
            String timeOut = cursor.getString(cursor.getColumnIndexOrThrow("time_out"));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));

            records.add(new AttendanceRecord(name, latitude, longitude,timeIn,timeOut, image));
        }
        cursor.close();

        summaryAdapter = new SummaryAdapter(records);
        recyclerView.setAdapter(summaryAdapter);
    }
}