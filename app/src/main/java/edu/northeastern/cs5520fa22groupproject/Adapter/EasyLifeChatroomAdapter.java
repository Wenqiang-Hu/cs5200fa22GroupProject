package edu.northeastern.cs5520fa22groupproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.cs5520fa22groupproject.EasyLifeMessageActivity;
import edu.northeastern.cs5520fa22groupproject.R;
import edu.northeastern.cs5520fa22groupproject.model.EasyLifeChatroom;

public class EasyLifeChatroomAdapter extends RecyclerView.Adapter<EasyLifeChatroomAdapter.ViewHolder> {
    private Context mContext;
    private List<EasyLifeChatroom> mEasyLifeChatrooms;
    //private boolean ischat;

    public EasyLifeChatroomAdapter(Context mContext, List<EasyLifeChatroom> mEasyLifeChatrooms){
        this.mEasyLifeChatrooms = mEasyLifeChatrooms;
        this.mContext = mContext;
        //this.ischat = ischat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.easylife_chatroom_items, parent, false);
        return new EasyLifeChatroomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EasyLifeChatroom easyLifeChatroom = mEasyLifeChatrooms.get(position);
        String tmp = easyLifeChatroom.getLogoPath();

        holder.chatroom_name.setText(easyLifeChatroom.getRoomname());
        holder.chatroom_intro.setText(easyLifeChatroom.getIntro());

        // set chatroom logo
        if (easyLifeChatroom.getId().equals("gameRoom")) {
            holder.chatroom_image.setImageResource(R.drawable.easylife_chatroom_game);
        }

        else if (easyLifeChatroom.getId().equals("musicRoom")) {
            holder.chatroom_image.setImageResource(R.drawable.easylife_chatroom_music);
        }

        else if (easyLifeChatroom.getId().equals("sportRoom")) {
            holder.chatroom_image.setImageResource(R.drawable.easylife_chatroom_sport);
        }

        else if (easyLifeChatroom.getId().equals("fashionRoom")) {
            holder.chatroom_image.setImageResource(R.drawable.easylife_chatroom_fashion);
        }

        else if (easyLifeChatroom.getId().equals("movieRoom")) {
            holder.chatroom_image.setImageResource(R.drawable.easylife_chatroom_movie);
        }


        // to do chat window
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EasyLifeMessageActivity.class);
                intent.putExtra("id", easyLifeChatroom.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEasyLifeChatrooms.size();
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
