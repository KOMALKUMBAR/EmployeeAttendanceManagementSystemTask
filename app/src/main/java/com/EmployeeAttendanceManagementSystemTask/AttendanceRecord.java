package com.EmployeeAttendanceManagementSystemTask;

public class AttendanceRecord  {
    String name, location, checkInTime;
    byte[] imagePath;

    public AttendanceRecord(String name, String location, String checkInTime, String inTime, String timeOut, byte[] imagePath) {
        this.name = name;
        this.location =  location;
        this.checkInTime = checkInTime;
        this.imagePath = imagePath;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public byte[] getImagePath() {
        return imagePath;
    }

    public void setImagePath(byte[] imagePath) {
        this.imagePath = imagePath;
    }


}
