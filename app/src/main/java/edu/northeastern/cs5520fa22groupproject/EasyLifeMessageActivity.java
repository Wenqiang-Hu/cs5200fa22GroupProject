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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
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


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String sender, final String receiver, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
//        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
//        hashMap.put("isseen", false);

        reference.child("/EasyLife/Chatroom/" + chatId + "/message").push().setValue(hashMap);
    }
}
