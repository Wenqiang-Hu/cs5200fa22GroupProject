package edu.northeastern.cs5520fa22groupproject;

//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//public class Chat extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat);
//    }
//}


import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Chat extends AppCompatActivity {
    LinearLayout layout;
    ScrollView scrollView;
    Firebase reference1;
    Firebase reference2;
    ImageView sendHappy;
    ImageView sendSad;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");
    StorageReference reference = FirebaseStorage.getInstance().getReference();
    Uri happyUri;
    Uri sadUri;
    String happyUrl;
    String sadUrl;

    private NotificationManagerCompat notificationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        layout = (LinearLayout) findViewById(R.id.layout1);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        sendHappy = (ImageView) findViewById(R.id.imageView);
        sendSad = (ImageView) findViewById(R.id.imageView2);

        notificationManager = NotificationManagerCompat.from(this);

        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://chatroom-c1076-default-rtdb.firebaseio.com/message/" + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://chatroom-c1076-default-rtdb.firebaseio.com/message/" + UserDetails.chatWith + "_" + UserDetails.username);

        happyUrl = "";
        sadUrl = "";
        sendHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (happyUrl == "") {
                    happyUri = Uri.parse("android.resource://edu.northeastern.cs5520fa22groupproject/" + R.drawable.happy);
                    sendHappy.setImageURI(happyUri);
                    uploadToFirebase(happyUri);
                    happyUrl = "---";
                    // return;
                } else {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", "****" + happyUrl);
                    map.put("user", UserDetails.username);
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                }
                sendOnChannel(view);
            }
        });

        sendSad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sadUrl == "") {
                    System.out.println("image 2 pressed on if");
                    sadUri = Uri.parse("android.resource://edu.neu.northeastern.cs5520fa22groupproject/" + R.drawable.happy);
                    sendSad.setImageURI(sadUri);
                    uploadToFirebase(sadUri);
                    sadUrl = "---";
                    // return;
                } else {
                    System.out.println("image 2 pressed on else");
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", "****" + sadUrl);
                    map.put("user", UserDetails.username);
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                }
                sendOnChannel(view);
            }
        });


        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();
                Date date = new Date();
                String messageTime = new SimpleDateFormat().format(date);
                if (userName.equals(UserDetails.username)) {
                    addMessageBox("You\t\t\t" + messageTime, 1);
                } else {
                    addMessageBox(UserDetails.chatWith + "\t\t\t" + messageTime, 2);
                }
                if (message.length() > 2 && message.charAt(0) == '*' && message.charAt(1) == '*') {
                    if (userName.equals(UserDetails.username)) {
                        addMessageBox(message, 1);
                    } else {
                        addMessageBox(message, 2);
                    }

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void uploadToFirebase(Uri uri) {
        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Model model = new Model(uri.toString());
                        String modelId = root.push().getKey();
                        root.child(modelId).setValue(model);

                        String newUrl = uri.toString();
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("message", "****" + newUrl);
                        map.put("user", UserDetails.username);
                        reference1.push().setValue(map);
                        reference2.push().setValue(map);
                        if (happyUrl == "---") {
                            happyUrl = newUrl;
                        }
                        if (sadUrl == "---") {
                            sadUrl = newUrl;
                        }
                    }
                });
            }
        });
    }

    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }

    public void addMessageBox(String message, int type) {
        if (message.charAt(0) == '*') {
            // This is an image
            ImageView imageView = new ImageView(Chat.this);
            String url = message.substring(4);
            Picasso.get().load(url).into(imageView);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 0, 10);
            imageView.setLayoutParams(lp);
            if (type == 1) {
                imageView.setBackgroundResource(R.drawable.rounded_corner1);
            } else {
                imageView.setBackgroundResource(R.drawable.rounded_corner2);
            }

            layout.addView(imageView);
            scrollView.fullScroll(View.FOCUS_DOWN);

        } else {
            // This is a text
            TextView textView = new TextView(Chat.this);
            textView.setText(message);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 0, 0);
            textView.setLayoutParams(lp);
            layout.addView(textView);
            scrollView.fullScroll(View.FOCUS_DOWN);
        }
    }



    public void sendOnChannel(View v) {
        android.app.Notification notification = new NotificationCompat.Builder(this, Notification.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Send a new sticker")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }
}
