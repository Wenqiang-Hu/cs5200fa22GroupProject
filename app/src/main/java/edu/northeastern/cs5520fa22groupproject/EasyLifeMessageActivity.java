package edu.northeastern.cs5520fa22groupproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.northeastern.cs5520fa22groupproject.Adapter.EasyLifeMessageAdapter;
import edu.northeastern.cs5520fa22groupproject.model.EasyLifeChat2;
import edu.northeastern.cs5520fa22groupproject.model.EasyLifeChatroom;

public class EasyLifeMessageActivity extends AppCompatActivity {

    CircleImageView chatroom_image;
    TextView chatroomname;
    FirebaseUser fuser;

    String chatId;

    Intent intent;

    DatabaseReference reference;

    ImageButton btn_send;
    EditText text_send;

    EasyLifeMessageAdapter easyLifeMessageAdapter;
    List<EasyLifeChat2> mchat;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_life_chat_message);

//        Toolbar toolbar = findViewById(R.id.toolbar_easylife);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//                // and this
//                startActivity(new Intent(EasyLifeMessageActivity.this, EasyLifeMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//            }
//        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        chatroom_image = findViewById(R.id.chatroom_image);
        chatroomname = findViewById(R.id.chatroomName);
        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);

        intent = getIntent();
        chatId = intent.getStringExtra("id");
        System.out.println("------- chatId ------- " + chatId);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        System.out.println("------- fuser ------- " + fuser);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                notify = true;
                String msg = text_send.getText().toString();
                if (!msg.equals("")){
                    sendMessage(fuser.getUid(), chatId, msg);
                } else {
                    Toast.makeText(EasyLifeMessageActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });

        //DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        reference = FirebaseDatabase.getInstance().getReference("/EasyLife/Chatroom").child(chatId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                EasyLifeChatroom easyLifeChatroom = dataSnapshot.getValue(EasyLifeChatroom.class);
                chatroomname.setText(easyLifeChatroom.getRoomname());

                // set the image of the toolbar
                if (easyLifeChatroom.getId().equals("gameRoom")) {
                    chatroom_image.setImageResource(R.drawable.easylife_chatroom_game);
                }

                else if (easyLifeChatroom.getId().equals("musicRoom")) {
                    chatroom_image.setImageResource(R.drawable.easylife_chatroom_music);
                }

                else if (easyLifeChatroom.getId().equals("sportRoom")) {
                    chatroom_image.setImageResource(R.drawable.easylife_chatroom_sport);
                }

                else if (easyLifeChatroom.getId().equals("fashionRoom")) {
                    chatroom_image.setImageResource(R.drawable.easylife_chatroom_fashion);
                }

                else if (easyLifeChatroom.getId().equals("movieRoom")) {
                    chatroom_image.setImageResource(R.drawable.easylife_chatroom_movie);
                }

                readMessages(fuser.getUid(), chatId, "dummy");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String sender, final String receiver, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("senderId", sender);
//        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
//        hashMap.put("isseen", false);

        hashMap.put("username", EasyLifeUserDetails.getUsername());

        reference.child("/EasyLife/Chatroom/" + chatId + "/message").push().setValue(hashMap);
    }

    private void readMessages(final String myid, final String chatroomId, final String imageurl) {
        mchat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("/EasyLife/Chatroom/" + chatroomId + "/message");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    EasyLifeChat2 chat = snapshot.getValue(EasyLifeChat2.class);
                    mchat.add(chat);
                }
                easyLifeMessageAdapter = new EasyLifeMessageAdapter(EasyLifeMessageActivity.this, mchat);
                recyclerView.setAdapter((easyLifeMessageAdapter));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


}
