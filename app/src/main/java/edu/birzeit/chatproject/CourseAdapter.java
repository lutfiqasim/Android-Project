package edu.birzeit.chatproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private List<CourseModel> courses;
    private Context context;
    private FirebaseDatabase database;

    public CourseAdapter(List<CourseModel> courses, Context context) {
        this.courses = courses;
        this.context = context;
        database = FirebaseDatabase.getInstance();
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_card, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        CourseModel course = courses.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition(); // Get the clicked item position
                CourseModel course = courses.get(position); // Get the clicked course
                Log.d("ref", course.getCourseRef());
                openPDFFile(course.getCourseRef());
                // Access the PDFs node in the Firebase database for the selected course
//                DatabaseReference pdfsRef = database.getReference().child(course.getCouresName()).child("PDFs");

//                pdfsRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.exists()) {
//                            DataSnapshot pdfSnapshot = dataSnapshot.getChildren().iterator().next(); // Get the first PDF snapshot
//                            Log.e("HERE", "HERE");
//                            String pdfName = pdfSnapshot.child("name").getValue(String.class);
//                            String pdfReference = pdfSnapshot.child("reference").getValue(String.class);
//                            courses.add(new CourseModel(pdfName,));
//                            // Perform any action with the PDF name and reference (e.g., open the PDF URL)
//                            Log.d("PDF Name", pdfName);
//                            Log.d("PDF Reference", pdfReference);
//
//                            // Open the PDF file using an appropriate PDF viewer app
////                            openPDFFile(pdfReference);
//                            Uri webpage = Uri.parse(pdfReference);
//                            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            Log.d("URL", pdfReference);
//                            // Verify if there's an activity available to handle the intent
//                            if (intent.resolveActivity(context.getPackageManager()) != null) {
//                                context.startActivity(intent);
//                            } else {
//                                Toast.makeText(context, "No web browser app found", Toast.LENGTH_SHORT).show();
//                            }
//                        }
////                        notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                        Toast.makeText(context, "Error retrieving PDF data", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }

        });

        holder.bind(course);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        private TextView courseNameTextView;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseNameTextView = itemView.findViewById(R.id.course_name);
        }

        public void bind(CourseModel course) {
            courseNameTextView.setText(course.getCouresName());
        }
    }

    private void openPDFFile(String pdfUrl) {
        try {

            Uri webpage = Uri.parse(pdfUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Verify if there's an activity available to handle the intent
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "No web browser app found", Toast.LENGTH_SHORT).show();
//                            }
            }
        } catch (Exception e) {
            Toast.makeText(context, "Error opening page", Toast.LENGTH_SHORT).show();
        }


    }
}
