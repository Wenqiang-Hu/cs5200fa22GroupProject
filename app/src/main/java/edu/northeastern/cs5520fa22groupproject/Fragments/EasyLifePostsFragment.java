package edu.northeastern.cs5520fa22groupproject.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.northeastern.cs5520fa22groupproject.Adapter.EasyLifePostsAdapter;
import edu.northeastern.cs5520fa22groupproject.EasyLifeMakingPost;
import edu.northeastern.cs5520fa22groupproject.R;
import edu.northeastern.cs5520fa22groupproject.model.Post;

public class EasyLifePostsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Post> postList;
    private EasyLifePostsAdapter easyLifePostsAdapter;
    private Button btn_makePost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_easy_life_post, container, false);
        recyclerView = view.findViewById(R.id.rv_post);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        btn_makePost = view.findViewById(R.id.btn_makePosts);
        btn_makePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), EasyLifeMakingPost.class));
            }
        });
        postList = new ArrayList<>();
        DisplayPosts();
        return view;
    }

    public void DisplayPosts(){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference reference = rootRef.child("EasyLife").child("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String user = snapshot.child("user").getValue(String.class);
                    String icon = snapshot.child("icon").getValue(String.class);
                    String context = snapshot.child("context").getValue(String.class);

                    Post post  = new Post(user, context, icon);

                    postList.add(post);
                }

                easyLifePostsAdapter = new EasyLifePostsAdapter(getContext(), postList);
                recyclerView.setAdapter(easyLifePostsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }
}