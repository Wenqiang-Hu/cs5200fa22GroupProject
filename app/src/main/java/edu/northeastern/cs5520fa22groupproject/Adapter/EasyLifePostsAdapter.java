package edu.northeastern.cs5520fa22groupproject.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import edu.northeastern.cs5520fa22groupproject.R;
import edu.northeastern.cs5520fa22groupproject.model.Post;

public class EasyLifePostsAdapter extends RecyclerView.Adapter<EasyLifePostsAdapter.ViewHolder>{
    private Context context;
    private List<Post> posts;

    public EasyLifePostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.easylife_post_item, parent, false);
        return new EasyLifePostsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        String temp = post.getIcon();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/EasyLife/Users/" + post.getUser());

        holder.user.setText(post.getUser());
        holder.context.setText(post.getContext());
        if (holder.icon.equals("default")) {

            holder.icon.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(context).load(post.getIcon()).into(holder.icon);
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView user;
        public TextView context;
        public ImageView icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.tv_post_user);
            context = itemView.findViewById(R.id.tv_post_context);
            icon = itemView.findViewById(R.id.image_user_icon);
        }
    }
}