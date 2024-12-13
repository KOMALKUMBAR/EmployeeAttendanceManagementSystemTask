package com.EmployeeAttendanceManagementSystemTask;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 101;

    private ImageView ivCapturedImage;
    private EditText etEmployeeName;
    private TextView tvTrackInTime, tvTrackOutTime;
    private Bitmap capturedImage;
    private LocationManager locationManager;
    private String latitude, longitude;
    private String trackInTime, trackOutTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        etEmployeeName = findViewById(R.id.etEmployeeName);
        ivCapturedImage = findViewById(R.id.ivCapturedImage);
        Button btnCaptureImage = findViewById(R.id.btnCaptureImage);
        Button btnSave = findViewById(R.id.btnSave);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        captureLocation();

        btnCaptureImage.setOnClickListener(v -> openCamera());
        btnSave.setOnClickListener(v -> btnSave());

    }

    private void btnSave() {

        Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
        startActivity(intent);

    }

    private void openCamera() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }
    }

    private void captureLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    latitude = String.valueOf(location.getLatitude());
                    longitude = String.valueOf(location.getLongitude());

                    //  saveAttendance();
                    Toast.makeText(MainActivity.this, "Location Captured", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            capturedImage = (Bitmap) data.getExtras().get("data");
            ivCapturedImage.setImageBitmap(capturedImage);
            saveAttendance(etEmployeeName.getText().toString(),capturedImage,latitude,longitude,System.currentTimeMillis()+"",trackOutTime);

        }
    }


    private void saveAttendance(String name, Bitmap image, String latitude, String longitude, String trackInTime, String trackOutTime) {
        // Convert Bitmap to byte array
        java.io.ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        // Save data to SQLite
        AttendanceDBHelper dbHelper = new AttendanceDBHelper(this);
        dbHelper.insertAttendance(name, imageBytes, latitude, longitude, trackInTime, trackOutTime);

        Toast.makeText(this, "Attendance Saved", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            captureLocation();
        } else {
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
        }

    }

}