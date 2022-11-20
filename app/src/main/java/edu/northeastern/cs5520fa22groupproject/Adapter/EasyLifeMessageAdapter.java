package edu.northeastern.cs5520fa22groupproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import edu.northeastern.cs5520fa22groupproject.R;
import edu.northeastern.cs5520fa22groupproject.model.EasyLifeChat2;
import edu.northeastern.cs5520fa22groupproject.model.EasyLifeChatroom;

public class EasyLifeMessageAdapter extends RecyclerView.Adapter<EasyLifeMessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private List<EasyLifeChat2> mChat;
    private List<EasyLifeChatroom> mEasyLifeChatrooms;

    FirebaseUser fuser;
    //private boolean ischat;

    public EasyLifeMessageAdapter(Context mContext, List<EasyLifeChat2> mChat){
        this.mContext = mContext;
        this.mChat = mChat;
    }

    @NonNull
    @Override
    public EasyLifeMessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == MSG_TYPE_RIGHT) {
            view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EasyLifeMessageAdapter.ViewHolder holder, int position) {
        EasyLifeChat2 chat = mChat.get(position);
        holder.show_message.setText(chat.getMessage());
        holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        holder.username_in_chatroom.setText(chat.getUsername());
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView show_message;
        public ImageView profile_image;
        public TextView username_in_chatroom;
//        public TextView txt_seen;

        public ViewHolder(View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            username_in_chatroom = itemView.findViewById(R.id.username_inside_room);
//            txt_seen = itemView.findViewById(R.id.txt_seen);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSenderId().equals(fuser.getUid())) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }



}
