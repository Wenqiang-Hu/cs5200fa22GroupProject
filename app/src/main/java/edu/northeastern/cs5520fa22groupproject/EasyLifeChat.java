package edu.northeastern.cs5520fa22groupproject;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.Firebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import edu.northeastern.cs5520fa22groupproject.MessageAdapter;
import edu.northeastern.cs5520fa22groupproject.R;
import edu.northeastern.cs5520fa22groupproject.UserDetails;
import edu.northeastern.cs5520fa22groupproject.model.Message;

public class EasyLifeChat extends AppCompatActivity {

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
        setContentView(R.layout.activity_easylife_chat);

        listView = (ListView) findViewById(R.id.list_view);

        MessageAdapter messageAdapter = new MessageAdapter(this, R.layout.activity_easylife_message, message_arraylist);
        listView.setAdapter(messageAdapter);


        ref = FirebaseDatabase.getInstance().getReference("EasyLife/Messages/" + UserDetails.getUsername() + "_" + UserDetails.getChatWith());


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
                FirebaseDatabase.getInstance().getReference("EasyLife").child("Messages").
                        child(EasyLifeUserDetails.getUsername()+"_"+EasyLifeUserDetails.getChatWith()).
                        push().setValue(
                        new Message(
                                "happy",
                                EasyLifeUserDetails.getUsername()
                        ));
                FirebaseDatabase.getInstance().getReference("EasyLife").child("Messages").
                        child(EasyLifeUserDetails.getChatWith() +"_"+EasyLifeUserDetails.getUsername()).
                        push().setValue(
                                new Message(
                                        "happy",
                                        EasyLifeUserDetails.getUsername()
                                ));
            }
        });

        sendSad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Messages").
                        child(EasyLifeUserDetails.getUsername()+"_"+EasyLifeUserDetails.getChatWith()).
                        push().setValue(
                                new EasyLifeMessage(
                                        "sad",
                                        EasyLifeUserDetails.getUsername()
                                ));
                FirebaseDatabase.getInstance().getReference().child("Messages").
                        child(EasyLifeUserDetails.getChatWith()+ "_"+EasyLifeUserDetails.getUsername()).
                        push().setValue(
                                new EasyLifeMessage(
                                        "sad",
                                        EasyLifeUserDetails.getUsername()
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