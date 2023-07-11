package edu.birzeit.chatproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    String majorNameChoosen;
    ArrayList<CourseModel> courses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        majorNameChoosen = getIntent().getStringExtra("major");
//
        database = FirebaseDatabase.getInstance();
        DatabaseReference majorReference = FirebaseDatabase.getInstance().getReference().child("Major");
        try {
            FetchCourses fetchMessagesTask = new FetchCourses();
            fetchMessagesTask.execute();
        } catch (Exception e) {
            Log.d("FirebaseRetrivingData", e.toString());
        }


        recyclerView = findViewById(R.id.recList);
        courseAdapter = new CourseAdapter(courses, CourseActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(courseAdapter);
    }

    private class FetchCourses extends AsyncTask<Void, Void, List<CourseModel>> {
        @Override
        protected List<CourseModel> doInBackground(Void... params) {
            DatabaseReference majorReference = database.getReference().child("Major");

            majorReference.child(majorNameChoosen).child("PDFs").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                    Log.d("data", dataSnapshot.toString());
//                    for (DataSnapshot courseSnapshot : dataSnapshot.getChildren()) {
                    String pdfKey = dataSnapshot.getKey();
                    String pdfName = dataSnapshot.child("name").getValue(String.class);
                    String pdfReference = dataSnapshot.child("reference").getValue(String.class);


                    CourseModel courseModel = new CourseModel(pdfName, pdfReference);
                    courses.add(courseModel);
                    Log.d("name", courseModel.getCouresName());
                    Log.d("refereeee", courseModel.getCourseRef());
//                    }


                    // Notify the adapter that new data has been added
                    courseAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

                // Other ChildEventListener methods (onChildChanged, onChildRemoved, onChildMoved, onCancelled)
                // ...
            });

            return courses;
        }

        @Override
        protected void onPostExecute(List<CourseModel> courses) {
            // Update the UI with the new data (if needed)
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            // Update the UI with the new data
            courseAdapter.notifyDataSetChanged();
        }
    }


}
