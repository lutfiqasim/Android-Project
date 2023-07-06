package edu.birzeit.chatproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ScheduleWindow extends AppCompatActivity {
    private LinearLayout mondaySlotsLayout;
    private LinearLayout tuesdaySlotsLayout;
    private LinearLayout wednesdaySlotsLayout;
    private LinearLayout thursdaySlotsLayout;
    private LinearLayout saturdaySlotsLayout;
    private ArrayList<Course> coursesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_window);
        // Find the layout containers for each day
        mondaySlotsLayout = findViewById(R.id.mondaySlotsLayout);
        tuesdaySlotsLayout = findViewById(R.id.tuesdaySlotsLayout);
        wednesdaySlotsLayout = findViewById(R.id.wednesdaySlotsLayout);
        thursdaySlotsLayout = findViewById(R.id.thursdaySlotsLayout);
        saturdaySlotsLayout = findViewById(R.id.saturdaySlotsLayout);
        SharedPreferences sharedPreferences = getSharedPreferences("courses", Context.MODE_PRIVATE);
        String courseData = sharedPreferences.getString("courses_data", null);
        if (courseData != (null) && !courseData.isEmpty()) {
            Gson gson = new Gson();
            Type courseListType = new TypeToken<ArrayList<Course>>() {
            }.getType();
            coursesList = gson.fromJson(courseData, courseListType);
            for (Course c : coursesList) {
                Log.d("courses", c.courseName);
            }
            addCourses();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        saveCoursesToSharedPreferences();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveCoursesToSharedPreferences();
    }

    private void saveCoursesToSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("courses", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String courseData = gson.toJson(coursesList);
        editor.putString("courses_data", courseData);
        editor.apply();
    }


    public void showAddCourseDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Course");
        // Inflate the custom layout for the dialog
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_course, null);
        builder.setView(dialogView);
        final EditText courseNameEditText = dialogView.findViewById(R.id.edit_course_name);
        final TimePicker courseTimeEditText = dialogView.findViewById(R.id.time_start);
        final TimePicker courseTimeEndEditText = dialogView.findViewById(R.id.time_end);
        final Spinner days = dialogView.findViewById(R.id.list_days);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String courseName = courseNameEditText.getText().toString();
                if (courseName.equals("") || courseName.equals(null)) {
                    courseNameEditText.setError("Should enter Course name");
                    Log.d("emptu", courseName);
                } else {
                    Log.d("emptu2", courseName);
                    int courseHour = courseTimeEditText.getHour();
                    int courseMinute = courseTimeEditText.getMinute();
                    int courseEndHour = courseTimeEndEditText.getHour();
                    int courseEndMinute = courseTimeEndEditText.getMinute();
                    String courseDay = days.getSelectedItem().toString();
                    // Determine which day's layout to add the course to
                    TextView timelayout;
                    Course c = new Course(courseName, courseHour, courseMinute, courseEndHour, courseEndMinute, courseDay);
                    coursesList.add(c);
                    switch (courseDay) {
                        case "Monday":
//                        dayLayout = mondaySlotsLayout;
                            switch (courseHour) {
                                case 8:
                                    timelayout = findViewById(R.id.Monday8);//+ " - " + courseHour + ":" + courseMinute
                                    timelayout.setText(courseName);
                                    break;
                                case 9:
                                    timelayout = findViewById(R.id.Monday9);
                                    timelayout.setText(courseName);//
                                    break;
                                case 10:
                                    timelayout = findViewById(R.id.Monday10);
                                    timelayout.setText(courseName);
                                    break;
                                case 11:
                                    timelayout = findViewById(R.id.Monday11);
                                    timelayout.setText(courseName);
                                    break;
                                case 12:
                                    timelayout = findViewById(R.id.Monday12);
                                    timelayout.setText(courseName);
                                    break;
                                case 1:
                                    timelayout = findViewById(R.id.Monday13);
                                    timelayout.setText(courseName);
                                    break;
                                case 2:
                                    timelayout = findViewById(R.id.Monday14);
                                    timelayout.setText(courseName);
                                    break;
                                case 3:
                                    timelayout = findViewById(R.id.Monday15);
                                    timelayout.setText(courseName);
                                    break;
                                case 4:
                                    timelayout = findViewById(R.id.Monday16);
                                    timelayout.setText(courseName);
                                    break;
                                default:
                                    return;
                            }
                            break;
                        case "Tuesday":
//                        dayLayout = tuesdaySlotsLayout;
                            switch (courseHour) {
                                case 8:
                                    timelayout = findViewById(R.id.Tuesday8);
                                    timelayout.setText(courseName);
                                    break;
                                case 9:
                                    timelayout = findViewById(R.id.Tuesday9);
                                    timelayout.setText(courseName);
                                    break;
                                case 10:
                                    timelayout = findViewById(R.id.Tuesday10);
                                    timelayout.setText(courseName);
                                    break;
                                case 11:
                                    timelayout = findViewById(R.id.Tuesday11);
                                    timelayout.setText(courseName);
                                    break;
                                case 12:
                                    timelayout = findViewById(R.id.Tuesday12);
                                    timelayout.setText(courseName);
                                    break;
                                case 1:
                                    timelayout = findViewById(R.id.Tuesday13);
                                    timelayout.setText(courseName);
                                    break;
                                case 2:
                                    timelayout = findViewById(R.id.Tuesday14);
                                    timelayout.setText(courseName);
                                    break;
                                case 3:
                                    timelayout = findViewById(R.id.Tuesday15);
                                    timelayout.setText(courseName);
                                    break;
                                case 4:
                                    timelayout = findViewById(R.id.Tuesday16);
                                    timelayout.setText(courseName);
                                    break;
                                default:
                                    return;
                            }
                            break;
                        case "Wednesday":
//                        dayLayout = wednesdaySlotsLayout;
                            switch (courseHour) {
                                case 8:
                                    timelayout = findViewById(R.id.wednesday8);
                                    timelayout.setText(courseName);
                                    break;
                                case 9:
                                    timelayout = findViewById(R.id.wednesday9);
                                    timelayout.setText(courseName);
                                    break;
                                case 10:
                                    timelayout = findViewById(R.id.wednesday10);
                                    timelayout.setText(courseName);
                                    break;
                                case 11:
                                    timelayout = findViewById(R.id.wednesday11);
                                    timelayout.setText(courseName);
                                    break;
                                case 12:
                                    timelayout = findViewById(R.id.wednesday12);
                                    timelayout.setText(courseName);
                                    break;
                                case 1:
                                    timelayout = findViewById(R.id.wednesday13);
                                    timelayout.setText(courseName);
                                    break;
                                case 2:
                                    timelayout = findViewById(R.id.wednesday14);
                                    timelayout.setText(courseName);
                                    break;
                                case 3:
                                    timelayout = findViewById(R.id.wednesday15);
                                    timelayout.setText(courseName);
                                    break;
                                case 4:
                                    timelayout = findViewById(R.id.wednesday16);
                                    timelayout.setText(courseName);
                                    break;
                                default:
                                    return;
                            }
                            break;
                        case "Thursday":
//                        dayLayout = thursdaySlotsLayout;
                            switch (courseHour) {
                                case 8:
                                    timelayout = findViewById(R.id.thursday8);
                                    timelayout.setText(courseName);
                                    break;
                                case 9:
                                    timelayout = findViewById(R.id.thursday9);
                                    timelayout.setText(courseName);
                                    break;
                                case 10:
                                    timelayout = findViewById(R.id.thursday10);
                                    timelayout.setText(courseName);
                                    break;
                                case 11:
                                    timelayout = findViewById(R.id.thursday11);
                                    timelayout.setText(courseName);
                                    break;
                                case 12:
                                    timelayout = findViewById(R.id.thursday12);
                                    timelayout.setText(courseName);
                                    break;
                                case 1:
                                    timelayout = findViewById(R.id.thursday1);
                                    timelayout.setText(courseName);
                                    break;
                                case 2:
                                    timelayout = findViewById(R.id.thursday2);
                                    timelayout.setText(courseName);
                                    break;
                                case 3:
                                    timelayout = findViewById(R.id.thursday3);
                                    timelayout.setText(courseName);
                                    break;
                                case 4:
                                    timelayout = findViewById(R.id.thursday4);
                                    timelayout.setText(courseName);
                                    break;
                                default:
                                    return;
                            }
                            break;
                        case "Saturday":
//                        dayLayout = saturdaySlotsLayout;
                            switch (courseHour) {
                                case 8:
                                    timelayout = findViewById(R.id.saturday8);
                                    timelayout.setText(courseName);
                                    break;
                                case 9:
                                    timelayout = findViewById(R.id.saturday9);
                                    timelayout.setText(courseName);
                                    break;
                                case 10:
                                    timelayout = findViewById(R.id.saturday10);
                                    timelayout.setText(courseName);
                                    break;
                                case 11:
                                    timelayout = findViewById(R.id.saturday11);
                                    timelayout.setText(courseName);
                                    break;
                                case 12:
                                    timelayout = findViewById(R.id.saturday12);
                                    timelayout.setText(courseName);
                                    break;
                                case 1:
                                    timelayout = findViewById(R.id.saturday13);
                                    timelayout.setText(courseName);
                                    break;
                                case 2:
                                    timelayout = findViewById(R.id.saturday14);
                                    timelayout.setText(courseName);
                                    break;
                                case 3:
                                    timelayout = findViewById(R.id.saturday15);
                                    timelayout.setText(courseName);
                                    break;
                                case 4:
                                    timelayout = findViewById(R.id.saturday16);
                                    timelayout.setText(courseName);
                                    break;
                                default:
                                    return;
                            }
                            break;
                        default:
                            return; // Invalid day input, do not add the course
                    }

                }
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addCourses() {
        TextView timelayout;
        for (Course c : coursesList) {
            String courseName = c.getCourseName();
            switch (c.getDay()) {
                case "Monday":
//                        dayLayout = mondaySlotsLayout;
                    switch (c.startHour) {
                        case 8:
                            timelayout = findViewById(R.id.Monday8);//+ " - " + courseHour + ":" + courseMinute
                            timelayout.setText(courseName);
                            break;
                        case 9:
                            timelayout = findViewById(R.id.Monday9);
                            timelayout.setText(courseName);//
                            break;
                        case 10:
                            timelayout = findViewById(R.id.Monday10);
                            timelayout.setText(courseName);
                            break;
                        case 11:
                            timelayout = findViewById(R.id.Monday11);
                            timelayout.setText(courseName);
                            break;
                        case 12:
                            timelayout = findViewById(R.id.Monday12);
                            timelayout.setText(courseName);
                            break;
                        case 1:
                            timelayout = findViewById(R.id.Monday13);
                            timelayout.setText(courseName);
                            break;
                        case 2:
                            timelayout = findViewById(R.id.Monday14);
                            timelayout.setText(courseName);
                            break;
                        case 3:
                            timelayout = findViewById(R.id.Monday15);
                            timelayout.setText(courseName);
                            break;
                        case 4:
                            timelayout = findViewById(R.id.Monday16);
                            timelayout.setText(courseName);
                            break;
                        default:
                            return;
                    }
                    break;
                case "Tuesday":
//                        dayLayout = tuesdaySlotsLayout;
                    switch (c.getStartHour()) {
                        case 8:
                            timelayout = findViewById(R.id.Tuesday8);
                            timelayout.setText(courseName);
                            break;
                        case 9:
                            timelayout = findViewById(R.id.Tuesday9);
                            timelayout.setText(courseName);
                            break;
                        case 10:
                            timelayout = findViewById(R.id.Tuesday10);
                            timelayout.setText(courseName);
                            break;
                        case 11:
                            timelayout = findViewById(R.id.Tuesday11);
                            timelayout.setText(courseName);
                            break;
                        case 12:
                            timelayout = findViewById(R.id.Tuesday12);
                            timelayout.setText(courseName);
                            break;
                        case 1:
                            timelayout = findViewById(R.id.Tuesday13);
                            timelayout.setText(courseName);
                            break;
                        case 2:
                            timelayout = findViewById(R.id.Tuesday14);
                            timelayout.setText(courseName);
                            break;
                        case 3:
                            timelayout = findViewById(R.id.Tuesday15);
                            timelayout.setText(courseName);
                            break;
                        case 4:
                            timelayout = findViewById(R.id.Tuesday16);
                            timelayout.setText(courseName);
                            break;
                        default:
                            return;
                    }
                    break;
                case "Wednesday":
//                        dayLayout = wednesdaySlotsLayout;
                    switch (c.getStartHour()) {
                        case 8:
                            timelayout = findViewById(R.id.wednesday8);
                            timelayout.setText(courseName);
                            break;
                        case 9:
                            timelayout = findViewById(R.id.wednesday9);
                            timelayout.setText(courseName);
                            break;
                        case 10:
                            timelayout = findViewById(R.id.wednesday10);
                            timelayout.setText(courseName);
                            break;
                        case 11:
                            timelayout = findViewById(R.id.wednesday11);
                            timelayout.setText(courseName);
                            break;
                        case 12:
                            timelayout = findViewById(R.id.wednesday12);
                            timelayout.setText(courseName);
                            break;
                        case 1:
                            timelayout = findViewById(R.id.wednesday13);
                            timelayout.setText(courseName);
                            break;
                        case 2:
                            timelayout = findViewById(R.id.wednesday14);
                            timelayout.setText(courseName);
                            break;
                        case 3:
                            timelayout = findViewById(R.id.wednesday15);
                            timelayout.setText(courseName);
                            break;
                        case 4:
                            timelayout = findViewById(R.id.wednesday16);
                            timelayout.setText(courseName);
                            break;
                        default:
                            return;
                    }
                    break;
                case "Thursday":
//                        dayLayout = thursdaySlotsLayout;
                    switch (c.getStartHour()) {
                        case 8:
                            timelayout = findViewById(R.id.thursday8);
                            timelayout.setText(courseName);
                            break;
                        case 9:
                            timelayout = findViewById(R.id.thursday9);
                            timelayout.setText(courseName);
                            break;
                        case 10:
                            timelayout = findViewById(R.id.thursday10);
                            timelayout.setText(courseName);
                            break;
                        case 11:
                            timelayout = findViewById(R.id.thursday11);
                            timelayout.setText(courseName);
                            break;
                        case 12:
                            timelayout = findViewById(R.id.thursday12);
                            timelayout.setText(courseName);
                            break;
                        case 1:
                            timelayout = findViewById(R.id.thursday1);
                            timelayout.setText(courseName);
                            break;
                        case 2:
                            timelayout = findViewById(R.id.thursday2);
                            timelayout.setText(courseName);
                            break;
                        case 3:
                            timelayout = findViewById(R.id.thursday3);
                            timelayout.setText(courseName);
                            break;
                        case 4:
                            timelayout = findViewById(R.id.thursday4);
                            timelayout.setText(courseName);
                            break;
                        default:
                            return;
                    }
                    break;
                case "Saturday":
//                        dayLayout = saturdaySlotsLayout;
                    switch (c.getStartHour()) {
                        case 8:
                            timelayout = findViewById(R.id.saturday8);
                            timelayout.setText(courseName);
                            break;
                        case 9:
                            timelayout = findViewById(R.id.saturday9);
                            timelayout.setText(courseName);
                            break;
                        case 10:
                            timelayout = findViewById(R.id.saturday10);
                            timelayout.setText(courseName);
                            break;
                        case 11:
                            timelayout = findViewById(R.id.saturday11);
                            timelayout.setText(courseName);
                            break;
                        case 12:
                            timelayout = findViewById(R.id.saturday12);
                            timelayout.setText(courseName);
                            break;
                        case 1:
                            timelayout = findViewById(R.id.saturday13);
                            timelayout.setText(courseName);
                            break;
                        case 2:
                            timelayout = findViewById(R.id.saturday14);
                            timelayout.setText(courseName);
                            break;
                        case 3:
                            timelayout = findViewById(R.id.saturday15);
                            timelayout.setText(courseName);
                            break;
                        case 4:
                            timelayout = findViewById(R.id.saturday16);
                            timelayout.setText(courseName);
                            break;
                        default:
                            return;
                    }
                    break;
                default:
                    return; // Invalid day input, do not add the course
            }
        }
    }
}
