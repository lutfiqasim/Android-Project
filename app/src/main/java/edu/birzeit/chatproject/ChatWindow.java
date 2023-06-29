package edu.birzeit.chatproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ChatWindow extends AppCompatActivity {
    //    __________
    CardView sendbtnn;
    EditText textmsg;
    RecyclerView recyclerView;
    MessageAdapter messageAdapter;
    List<MessageModel> messageList = new ArrayList<>();

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

        //__________________
        DatabaseReference messagesRef = database.getReference().child("GroupChats").child(groupId).child("messages");
        messagesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
                messageList.add(messageModel);
                messageAdapter.notifyDataSetChanged();
                Log.d("itemCount", messageAdapter.getItemCount() + "");
                recyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                // Handle changes to existing messages if needed
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                // Handle message removal if needed
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                // Handle message movement if needed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any database error
            }
        });

        // ..
        sendbtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textmsg.getText().toString().isEmpty()) {
                    Toast.makeText(ChatWindow.this, "Enter a message first", Toast.LENGTH_SHORT).show();
                } else {
                    String msg = textmsg.getText().toString();
                    textmsg.setText("");
                    String senderId = firebaseAuth.getUid(), messageText = msg.toString();
                    Log.d("Hi", msg.toString());
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
        MessageModel message = new MessageModel(messageId, senderId, messageText);

        // Save the message to the database
        messageRef.child(messageId).setValue(message);

//        messageAdapter.notifyDataSetChanged();
    }


}