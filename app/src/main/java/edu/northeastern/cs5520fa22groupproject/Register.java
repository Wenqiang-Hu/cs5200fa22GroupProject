package edu.northeastern.cs5520fa22groupproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

import edu.northeastern.cs5520fa22groupproject.model.User;

public class Register extends AppCompatActivity {
    TextView input_username;
    Button btn_register;
    DatabaseReference Users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        input_username = findViewById(R.id.input_username);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        String username = input_username.getText().toString();
        String id = Users.push().getKey();

        User user = new User(id, username);

        assert id != null;
        Users.child(id).setValue(user);
        Toast.makeText(Register.this, "user added!", Toast.LENGTH_SHORT).show();

    }
}
