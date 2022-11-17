package edu.northeastern.cs5520fa22groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button atYourService = findViewById((R.id.atyourservice));
        atYourService.setOnClickListener(this);

        Button easyLifeButton = findViewById((R.id.easylife));
        easyLifeButton.setOnClickListener(this);

        Button stickButton = findViewById(R.id.button_Stick_It);
        stickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stickButton = new Intent(MainActivity.this, StartActivity.class);
                startActivity(stickButton);
            }
        });



        Button aboutMe = findViewById((R.id.aboutButton));
        aboutMe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent aboutMe = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(aboutMe);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int theId = v.getId();
        if (theId == R.id.atyourservice) {
            Intent clicky = new Intent(MainActivity.this, AtYourService.class);
            startActivity(clicky);
        } else if (theId == R.id.easylife) {
            Intent easyLifeIntent = new Intent(MainActivity.this, EasyLifeStartActivity.class);
            startActivity(easyLifeIntent);
        }
    }
}