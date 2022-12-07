package edu.northeastern.cs5520fa22groupproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import edu.northeastern.cs5520fa22groupproject.model.EasyLifeUserDetails;

public class EasyLifeMakingPost extends AppCompatActivity {

    EditText editText;
    Button button;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_making_post);
        editText = findViewById(R.id.tv_postbox);
        button = findViewById(R.id.btn_making_post);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("/EasyLife");
                String context = editText.getText().toString();
                user = FirebaseAuth.getInstance().getCurrentUser();
                if (context.length() == 0) {
                    Toast.makeText(EasyLifeMakingPost.this, "Tell us your story!", Toast.LENGTH_LONG).show();
                } else {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("context", context);
                    map.put("icon", EasyLifeUserDetails.getImageURL());
                    map.put("user", EasyLifeUserDetails.getUsername());
                    databaseReference.child("Posts").push().setValue(map);
                    finish();
                }
            }
        });
    }
}