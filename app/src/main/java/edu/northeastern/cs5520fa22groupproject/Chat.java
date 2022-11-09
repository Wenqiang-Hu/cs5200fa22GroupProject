package edu.northeastern.cs5520fa22groupproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import edu.northeastern.cs5520fa22groupproject.model.Message;

public class Chat extends AppCompatActivity {
    ListView listView;
    ImageView sendHappy;
    ImageView sendSad;
    Set<String> set;
    DatabaseReference ref;
    DatabaseReference proRef;
    ArrayList<String> al = new ArrayList<>();
    ArrayList<Message> message_arraylist = new ArrayList<>();


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
//                            System.out.println("=======> " + snapshot);
                            String stickerValue = snapshot.child("sticker").getValue(String.class);
                            String currUsername = snapshot.child("user").getValue(String.class);
                            String currTime = snapshot.child("timeString").getValue(String.class);

                            System.out.println("currTime ------------>    " + currTime);


                            if (stickerValue.equals("happy")) {
                                message_arraylist.add(new Message(R.drawable.happy, currUsername, currTime));
                            } else {
                                message_arraylist.add(new Message(R.drawable.sad, currUsername, currTime));
                            }
                            messageAdapter.notifyDataSetChanged();
                            set.add(snapshot.getKey());
                            createNotificationChannel();
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
                UserDetails.setHappyUsed(UserDetails.getHappyUsed() + 1);
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
                FirebaseDatabase.getInstance().getReference().child("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("happyUsed").setValue(String.valueOf(UserDetails.getHappyUsed()+1));
            }
        });

        sendSad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDetails.setSadUsed(UserDetails.getSadUsed() + 1);
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
                FirebaseDatabase.getInstance().getReference().child("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("sadUsed").setValue(String.valueOf(UserDetails.getSadUsed() +1));
            }
        });


        scrollMyListViewToBottom();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.BASE){
            CharSequence name = "You got a new sticker";
            String description = "This is a notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Chat Notification", name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

//        Intent intent = new Intent(this, ReceiveNotificationActivity.class);
//        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
//        PendingIntent callIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(),
//                new Intent(this, FakeCallActivity.class), 0);
//
//        String channelId = getString(R.string.channel_id);
//        NotificationCompat.Builder notifyBuild = new NotificationCompat.Builder(this, channelId)
//                //"Notification icons must be entirely white."
//                .setSmallIcon(R.drawable.happy)
//                .setContentTitle("New mail from " + "test@test.com")
//                .setContentText("Subject")
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                // hide the notification after its selected
//                .setAutoCancel(true)
//                .addAction(R.drawable.happy, "Call", callIntent)
//                .setContentIntent(pIntent);
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        // // notificationId is a unique int for each notification that you must define
//        notificationManager.notify(0, notifyBuild.build());
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