package edu.northeastern.cs5520fa22groupproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationManager;
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

//        message_arraylist.add(new Message(R.drawable.happy, "dog1", new Date(1667778729986L)));
//        message_arraylist.add(new Message(R.drawable.sad, "sad dog", new Date(1667778729989L)));

        MessageAdapter messageAdapter = new MessageAdapter(this, R.layout.activity_message, message_arraylist);
        listView.setAdapter(messageAdapter);


        //        ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://chatroom-c1076-default-rtdb.firebaseio.com/Messages/" + UserDetails.getUsername() + "_" + UserDetails.getChatWith());
        ref = FirebaseDatabase.getInstance().getReference("Messages/" + UserDetails.getUsername() + "_" + UserDetails.getChatWith());

//        adapter = new ArrayAdapter<String>(Chat.this, android.R.layout.simple_list_item_1, al);
//        listView.setAdapter(adapter);

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


                ref.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        if (!set.contains(snapshot.getKey())) {
//                            System.out.println("=======> " + snapshot);
                            String stickerValue = snapshot.child("sticker").getValue(String.class);
                            String currUsername = snapshot.child("user").getValue(String.class);
                            String currTime = snapshot.child("timeString").getValue(String.class);

                            System.out.println("currTime ------------>    " + currTime);

//                            long currTime2 = snapshot.child("time").getValue();

//                            String stringToConvert = String.valueOf(snapshot.child("time").getValue());
//                            if (stringToConvert == null) {
//                                System.out.println("!!!!!!!!    NULL");
//                            }
//                            System.out.println("00000000   " + stringToConvert);
//                            Long convertedLong = Long.parseLong(stringToConvert);
//                            System.out.println("111111111111111111:  " + convertedLong);
//                            Date d = new Date(convertedLong);
//                            System.out.println("222222222222222222:  " + d);
//                            System.out.println("======> currTime B:  " + (snapshot.child("time").getValue() instanceof));

//                            al.add(stickerValue);
//                            adapter.notifyDataSetChanged();

                            if (stickerValue.equals("happy")) {
                                message_arraylist.add(new Message(R.drawable.happy, currUsername, currTime));
//                                message_arraylist.add(new Message(R.drawable.happy));

//                                NotificationCompat.Builder mbuilder = (NotificationCompat.Builder)
//                                        new NotificationCompat.Builder(getApplicationContext())
//                                                .setSmallIcon(R.drawable.happy)
//                                                .setContentTitle("Notification")
//                                                .setContentText("This is a notification for you");
//
//                                NotificationManager notificationManager = (NotificationManager)
//                                        getSystemService(NOTIFICATION_SERVICE);
//                                notificationManager.notify(0, mbuilder.build());
                            } else {
                                message_arraylist.add(new Message(R.drawable.sad, currUsername, currTime));
//                                message_arraylist.add(new Message(R.drawable.sad));

//                                NotificationCompat.Builder mbuilder = (NotificationCompat.Builder)
//                                        new NotificationCompat.Builder(getApplicationContext())
//                                                .setSmallIcon(R.drawable.sad)
//                                                .setContentTitle("Notification")
//                                                .setContentText("This is a notification for you");
//
//                                NotificationManager notificationManager = (NotificationManager)
//                                        getSystemService(NOTIFICATION_SERVICE);
//                                notificationManager.notify(0, mbuilder.build());
                            }
                            messageAdapter.notifyDataSetChanged();
                            set.add(snapshot.getKey());


//                            NotificationCompat.Builder mbuilder = (NotificationCompat.Builder)
//                                    new NotificationCompat.Builder(getApplicationContext())
//                                            .setSmallIcon(R.drawable.happy)
//                                            .setContentTitle("Notification")
//                                            .setContentText("This is a notification for you");
//
//                            NotificationManager notificationManager = (NotificationManager)
//                                    getSystemService(NOTIFICATION_SERVICE);
//                            notificationManager.notify(0, mbuilder.build());
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

//                NotificationCompat.Builder mbuilder = (NotificationCompat.Builder)
//                        new NotificationCompat.Builder(getApplicationContext())
//                                .setSmallIcon(R.drawable.happy)
//                                .setContentTitle("Notification")
//                                .setContentText("This is a notification for you");
//
//                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(Chat.this);
//                notificationManager.notify(0, mbuilder.build());

                Notification n = new Notification.Builder(Chat.this)
                        .setSmallIcon(R.drawable.happy)
                        .setContentTitle("New mail from " + "test@gmail.com")
                        .setContentText("Subject")
                        .build();

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0, n);

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