package edu.northeastern.cs5520fa22groupproject.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520fa22groupproject.Adapter.ChatroomAdapter;
import edu.northeastern.cs5520fa22groupproject.R;
import edu.northeastern.cs5520fa22groupproject.model.Chatroom;


public class EasyLifeChatsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ChatroomAdapter chatroomAdapter;
    private List<Chatroom> mChatroom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_easy_life_chats, container, false);

        View view = inflater.inflate(R.layout.fragment_easy_life_chats, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mChatroom = new ArrayList<>();

        readUsers();
        return view;
    }

    public void readUsers() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chatroom");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChatroom.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chatroom chatroom = snapshot.getValue(Chatroom.class);

                    mChatroom.add(chatroom);
                }

                chatroomAdapter = new ChatroomAdapter(getContext(), mChatroom);
                recyclerView.setAdapter(chatroomAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }
}