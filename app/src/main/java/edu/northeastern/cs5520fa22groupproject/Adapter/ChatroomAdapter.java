package edu.northeastern.cs5520fa22groupproject.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.northeastern.cs5520fa22groupproject.R;
import edu.northeastern.cs5520fa22groupproject.model.Chatroom;

public class ChatroomAdapter extends RecyclerView.Adapter<ChatroomAdapter.ViewHolder> {
    private Context mContext;
    private List<Chatroom> mChatrooms;
    //private boolean ischat;

    public ChatroomAdapter(Context mContext, List<Chatroom> mChatrooms){
        this.mChatrooms = mChatrooms;
        this.mContext = mContext;
        //this.ischat = ischat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chatroom_item, parent, false);
        return new ChatroomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chatroom chatroom = mChatrooms.get(position);
        String tmp = chatroom.getLogoPath();

        holder.chatroom_name.setText(chatroom.getRoomname());
        holder.chatroom_intro.setText(chatroom.getIntro());

        if (chatroom.getRoomname().equals("Love Game")) {
            holder.chatroom_image.setImageResource(R.drawable.easylife_chatroom_game);
        }

        else if (chatroom.getRoomname().equals("Love Music")) {
            holder.chatroom_image.setImageResource(R.drawable.easylife_chatroom_music);
        }

        else if (chatroom.getRoomname().equals("Love Sport")) {
            holder.chatroom_image.setImageResource(R.drawable.easylife_chatroom_sport);
        }
    }

    @Override
    public int getItemCount() {
        return mChatrooms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView chatroom_name;
        public ImageView chatroom_image;
        public TextView chatroom_intro;
//        private ImageView img_on;
//        private ImageView img_off;
//        private TextView last_msg;

        public ViewHolder(View itemView) {
            super(itemView);

            chatroom_name = itemView.findViewById(R.id.chatRoom_name);
            chatroom_image = itemView.findViewById(R.id.chatRoom_logo);
            chatroom_intro = itemView.findViewById(R.id.chatRoom_intro);
//            img_on = itemView.findViewById(R.id.img_on);
//            img_off = itemView.findViewById(R.id.img_off);
//            last_msg = itemView.findViewById(R.id.last_msg);
        }
    }


}
