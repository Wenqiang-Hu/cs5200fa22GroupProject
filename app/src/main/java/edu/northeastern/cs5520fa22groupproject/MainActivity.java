package edu.northeastern.cs5520fa22groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button atYourService = findViewById((R.id.atyourservice));
        atYourService.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent clicky = new Intent(MainActivity.this, AtYourService.class);
                startActivity(clicky);
            }
        });
    }
}