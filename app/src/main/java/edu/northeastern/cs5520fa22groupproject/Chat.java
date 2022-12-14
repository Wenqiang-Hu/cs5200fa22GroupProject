package edu.northeastern.cs5520fa22groupproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.firebase.client.Firebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import edu.northeastern.cs5520fa22groupproject.model.Message;

public class Chat extends AppCompatActivity {

    LinearLayout layout;
    ListView listView;
    ImageView sendHappy;
    ImageView sendSad;
    Firebase chatDb;
    Set<String> set;
    DatabaseReference ref;
    ArrayList<String> al = new ArrayList<>();
    ArrayList<Message> message_arraylist = new ArrayList<>();
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listView = (ListView) findViewById(R.id.list_view);

        MessageAdapter messageAdapter = new MessageAdapter(this, R.layout.activity_message, message_arraylist);
        listView.setAdapter(messageAdapter);


        ref = FirebaseDatabase.getInstance().getReference("Messages/" + UserDetails.getUsername() + "_" + UserDetails.getChatWith());


        sendHappy = (ImageView) findViewById(R.id.imageView);
        sendSad = (ImageView) findViewById(R.id.imageView2);

        set = new HashSet<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ref.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        if (!set.contains(snapshot.getKey())) {
                            String stickerValue = snapshot.child("sticker").getValue(String.class);
                            String currUsername = snapshot.child("user").getValue(String.class);
                            String currTime = snapshot.child("timeString").getValue(String.class);

                            if (stickerValue.equals("happy")) {
                                message_arraylist.add(new Message(R.drawable.happy, currUsername, currTime));
                            } else {
                                message_arraylist.add(new Message(R.drawable.sad, currUsername, currTime));
                            }
                            messageAdapter.notifyDataSetChanged();

                            set.add(snapshot.getKey());
                        }
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
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Messages").
                        child(UserDetails.getUsername()+"_"+UserDetails.getChatWith()).
                        push().setValue(
                        new Message(
                                "happy",
                                UserDetails.getUsername()
                        ));
                FirebaseDatabase.getInstance().getReference().child("Messages").
                        child(UserDetails.getChatWith() +"_"+UserDetails.getUsername()).
                        push().setValue(
                                new Message(
                                        "happy",
                                        UserDetails.getUsername()
                                ));
            }
        });

        sendSad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Messages").
                        child(UserDetails.getUsername()+"_"+UserDetails.getChatWith()).
                        push().setValue(
                                new Message(
                                        "sad",
                                        UserDetails.getUsername()
                                ));
                FirebaseDatabase.getInstance().getReference().child("Messages").
                        child(UserDetails.getChatWith()+ "_"+UserDetails.getUsername()).
                        push().setValue(
                                new Message(
                                        "sad",
                                        UserDetails.getUsername()
                                ));
            }
        });


        scrollMyListViewToBottom();
    }

    private void scrollMyListViewToBottom() {
        listView.post(new Runnable() {
            @Override
            public void run() {
                listView.setSelection(listView.getCount() - 1);
            }
        });
    }
}