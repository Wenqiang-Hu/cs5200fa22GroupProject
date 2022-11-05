package edu.northeastern.cs5520fa22groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sticker extends AppCompatActivity {
    EditText etName;
    EditText etRollno;
    Spinner spinnerCourses;
    Button btnInsertData;
    DatabaseReference studentDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker);

        etName = findViewById(R.id.etName);
        etRollno = findViewById(R.id.etRollno);
        spinnerCourses = findViewById(R.id.spinnerCourse);
        btnInsertData = findViewById(R.id.btnInsertData);
        studentDbRef = FirebaseDatabase.getInstance().getReference("Students");
        btnInsertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertStudentData();
            }
        });
    }

    private void insertStudentData() {
        String name = etName.getText().toString();
        String rollno = etRollno.getText().toString();
        String course = spinnerCourses.getSelectedItem().toString();

        Students students = new Students(name,rollno,course);

        studentDbRef.push().setValue(students);
        Toast.makeText(Sticker.this,"Data inserted!",Toast.LENGTH_SHORT).show();

    }
//
//    private FirebaseAnalytics mFirebaseAnalytics;
//    private FirebaseAuth mAuth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sticker);
//
//        FirebaseApp.initializeApp(this);
//        // Obtain the FirebaseAnalytics instance.
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//        // Initialize Firebase Auth
//        mAuth = FirebaseAuth.getInstance();
//    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            reload();
//        }
//    }
}