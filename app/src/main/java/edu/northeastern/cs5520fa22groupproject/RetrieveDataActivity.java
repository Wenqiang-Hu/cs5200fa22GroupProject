package edu.northeastern.cs5520fa22groupproject;

import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class RetreiveDataActivity extends AppCompatActivity {

    ListView myListview;
    List<Students> studentsList;

    DatabaseReference studentDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retreive_data);

        myListview = findViewById(R.id.myListView);
        studentsList = new ArrayList<>();

        studentDbRef = FirebaseDatabase.getInstance().getReference("Students");

        studentDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentsList.clear();

                for (DataSnapshot studentDatasnap : dataSnapshot.getChildren()) {
                    Students students = studentDatasnap.getValue(Students.class);
                    studentsList.add(students);
                }
                ListAdapter adapter = new ListAdapter(RetreiveDataActivity.this, studentsList);
                myListview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //set itemLong listener on listview item


