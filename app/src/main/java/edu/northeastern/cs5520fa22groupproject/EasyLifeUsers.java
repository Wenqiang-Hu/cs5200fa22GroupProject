package edu.northeastern.cs5520fa22groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class EasyLifeUsers extends AppCompatActivity {
    ListView usersList;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;

    FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
    String currUID = currUser.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easylife_users);

        usersList = (ListView) findViewById(R.id.usersList);
        noUsersText = (TextView) findViewById(R.id.noUsersText);

        String url = "https://chatroom-c1076-default-rtdb.firebaseio.com/EasyLifeUsers.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(EasyLifeUsers.this);
        rQueue.add(request);

        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EasyLifeUserDetails.chatWith = al.get(position);
                startActivity(new Intent(EasyLifeUsers.this, EasyLifeChat.class));
            }
        });
    }

    public void  doOnSuccess(String s) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        System.out.println("======>   " + rootRef);
        DatabaseReference usersdRef = rootRef.child("EasyLifeUsers");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String uid = ds.child("id").getValue(String.class);
                    String name = ds.child("username").getValue(String.class);
                    if (!uid.equals(currUID)) {
                        al.add(name);
                        totalUsers++;
                    }else{
                        EasyLifeUserDetails.setUsername(name);
                    }
                }

                if (totalUsers <= 1) {
                    noUsersText.setVisibility(View.VISIBLE);
                    usersList.setVisibility(View.GONE);
                } else {
                    noUsersText.setVisibility(View.GONE);
                    usersList.setVisibility(View.VISIBLE);
                    usersList.setAdapter(new ArrayAdapter<String>(EasyLifeUsers.this, android.R.layout.simple_list_item_1, al));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        usersdRef.addListenerForSingleValueEvent(eventListener);
    }
}