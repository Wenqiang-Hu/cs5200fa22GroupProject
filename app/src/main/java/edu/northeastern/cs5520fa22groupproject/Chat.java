package edu.northeastern.cs5520fa22groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.northeastern.cs5520fa22groupproject.model.Message;

public class Chat extends AppCompatActivity {

    LinearLayout layout;
    ListView listView;
    ImageView sendHappy;
    ImageView sendSad;
    Firebase chatDb;
    Set<String> set;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        listView = (ListView) findViewById(R.id.listView);
        sendHappy = (ImageView) findViewById(R.id.imageView);
        sendSad = (ImageView) findViewById(R.id.imageView2);
        ArrayList<String> al = new ArrayList<>();
        set = new HashSet<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://chatroom-c1076-default-rtdb.firebaseio.com/Messages/" + UserDetails.getUsername() + "_" + UserDetails.getChatWith());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    if (!set.contains(ds.getKey())){
                        String name = ds.child("user").getValue(String.class);
                        String sticker = ds.child("sticker").getValue(String.class);
                        al.add(sticker);
                        System.out.println(sticker);
                        set.add(ds.getKey());
                    }
                }
                //al->listView
                ArrayAdapter adapter = new ArrayAdapter<String>(Chat.this,
                        android.R.layout.simple_list_item_1);
                listView.setAdapter(adapter);
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

    }
}