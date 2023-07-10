package edu.birzeit.chatproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ChatWindow extends AppCompatActivity {
    //    __________
    CardView sendbtnn;
    EditText textmsg;
    RecyclerView recyclerView;
    MessageAdapter messageAdapter;
    List<MessageModel> messageList = new ArrayList<>();
    String senderName;

    //  _______________
    String groupId = "GroupChat1"; // this should be got based on which group user opens
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        //__________ Get views
        sendbtnn = findViewById(R.id.sendbtnn);
        textmsg = findViewById(R.id.textmsg);
        //_________
        //__________RECYCLER
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        SharedPreferences sharedPreferences = getSharedPreferences("LoggedIn", Context.MODE_PRIVATE);
//        groupId = sharedPreferences.getString("major", "Computer Science");
        senderName = sharedPreferences.getString("name", "Anonymous");

        try {
            FetchMessagesTask fetchMessagesTask = new FetchMessagesTask();
            fetchMessagesTask.execute();
        } catch (Exception e) {
            Log.d("FirebaseRetrivingData", e.toString());
        }

        sendbtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textmsg.getText().toString().isEmpty()) {
                    Toast.makeText(ChatWindow.this, "Enter a message first", Toast.LENGTH_SHORT).show();
                } else {
                    String msg = textmsg.getText().toString().trim();
                    textmsg.setText("");
                    String senderId = firebaseAuth.getUid(), messageText = msg.toString();
//                    Log.d("Hi", msg.toString());
                    addMessageToGroupChat(groupId, senderId, messageText);
                }
            }
        });
    }

    public void addMessageToGroupChat(String groupId, String senderId, String messageText) {
        // Generate a unique message ID
        DatabaseReference messageRef = database.getReference().child("GroupChats").child(groupId).child("messages");
        String messageId = messageRef.push().getKey();

        // Create a message object
        MessageModel message = new MessageModel(messageId, senderName, senderId, messageText);

        // Save the message to the database
        messageRef.child(messageId).setValue(message);
//        messageAdapter.notifyDataSetChanged();
    }

    private class FetchMessagesTask extends AsyncTask<Void, Void, List<MessageModel>> {
        @Override
        protected List<MessageModel> doInBackground(Void... params) {
            DatabaseReference messagesRef = database.getReference().child("GroupChats").child(groupId).child("messages");
//                       ________________________
            //Latch is used here as a way to tell that first data has been retrived from DB
            //to wait for the initial data retrieval from Firebase before updating the UI
            //Note: its like a way to tell that data has been retrived and start adding it UI so no update will happen before retriveing first data
//            final CountDownLatch latch = new CountDownLatch(1);//
//            messageList = new ArrayList<>();
//            ________________________
            messagesRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                    MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
                    messageList.add(messageModel);
                    // Notify the UI thread when a child is added
                    publishProgress();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @androidx.annotation.Nullable String previousChildName) {
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @androidx.annotation.Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ChatWindow.this, "Check Internet connection", Toast.LENGTH_SHORT).show();

                }
            });


            return messageList;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            // Update the UI with the new data
            messageAdapter.notifyDataSetChanged();
            //Scroll to last added data
            recyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
        }

        @Override
        protected void onPostExecute(List<MessageModel> messageList) {
            // Just Makes sure updated to latest message
            recyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
        }
    }
}