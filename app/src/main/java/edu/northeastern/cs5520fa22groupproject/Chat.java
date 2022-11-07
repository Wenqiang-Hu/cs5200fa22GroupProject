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
import java.util.HashSet;
import java.util.Set;

import edu.northeastern.cs5520fa22groupproject.model.Message;

public class Chat extends AppCompatActivity {

    LinearLayout layout;
    ListView listView;
    ScrollView scrollView;
    ImageView sendHappy;
    ImageView sendSad;
    Firebase chatDb;
    Set<String> set;
    DatabaseReference ref;
    ArrayList<String> al = new ArrayList<>();
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //        ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://chatroom-c1076-default-rtdb.firebaseio.com/Messages/" + UserDetails.getUsername() + "_" + UserDetails.getChatWith());
        ref = FirebaseDatabase.getInstance().getReference("Messages/" + UserDetails.getUsername() + "_" + UserDetails.getChatWith());
        listView = (ListView) findViewById(R.id.list_view);
//        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        adapter = new ArrayAdapter<String>(Chat.this, android.R.layout.simple_list_item_1, al);
        listView.setAdapter(adapter);
//        scrollView.setAdapter(adapter);

        sendHappy = (ImageView) findViewById(R.id.imageView);
        sendSad = (ImageView) findViewById(R.id.imageView2);

        set = new HashSet<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot ds : snapshot.getChildren()) {
//                    if (!set.contains(ds.getKey())){
//                        String name = ds.child("user").getValue(String.class);
//                        String sticker = ds.child("sticker").getValue(String.class);
//                        al.add(sticker);
//                        System.out.println(sticker);
//                        set.add(ds.getKey());
//                    }
//                }

                //al->listView

                ref.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        if (!set.contains(snapshot.getKey())) {
                            System.out.println("=======> " + snapshot);
                            String value = snapshot.child("sticker").getValue(String.class);
                            al.add(value);
                            adapter.notifyDataSetChanged();
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
                // Select the last row so it will scroll into view...
                listView.setSelection(listView.getCount() - 1);
            }
        });
    }
}