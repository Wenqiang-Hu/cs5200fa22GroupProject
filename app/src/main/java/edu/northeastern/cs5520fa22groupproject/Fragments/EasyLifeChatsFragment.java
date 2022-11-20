package edu.northeastern.cs5520fa22groupproject.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520fa22groupproject.Adapter.EasyLifeChatroomAdapter;
import edu.northeastern.cs5520fa22groupproject.R;
import edu.northeastern.cs5520fa22groupproject.model.EasyLifeChatroom;


public class EasyLifeChatsFragment extends Fragment {

    private RecyclerView recyclerView;
    private EasyLifeChatroomAdapter easyLifeChatroomAdapter;
    private List<EasyLifeChatroom> mEasyLifeChatroom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_easy_life_chats, container, false);


        View view = inflater.inflate(R.layout.fragment_easy_life_chats, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_easylife_chat_lobby);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mEasyLifeChatroom = new ArrayList<>();

        readChatrooms();
        return view;
    }

    public void readChatrooms() {
        //DatabaseReference reference = FirebaseDatabase.getInstance().getReference("/EasyLife/Chatroom");
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference reference = rootRef.child("EasyLife").child("Chatroom");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mEasyLifeChatroom.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String chatroomIntro = snapshot.child("intro").getValue(String.class);
                    String chatroomLogo = snapshot.child("logo").getValue(String.class);
                    String chatroomname = snapshot.child("roomname").getValue(String.class);
                    String chatroomId = snapshot.child("id").getValue(String.class);
                    EasyLifeChatroom easyLifeChatroom = new EasyLifeChatroom(chatroomId, chatroomIntro, chatroomLogo, chatroomname);

                    mEasyLifeChatroom.add(easyLifeChatroom);
                }

                easyLifeChatroomAdapter = new EasyLifeChatroomAdapter(getContext(), mEasyLifeChatroom);
                recyclerView.setAdapter(easyLifeChatroomAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }
}