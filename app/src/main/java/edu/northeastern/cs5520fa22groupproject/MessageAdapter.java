package edu.northeastern.cs5520fa22groupproject;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

import edu.northeastern.cs5520fa22groupproject.model.Message;

public class MessageAdapter extends ArrayAdapter<Message> {
    private Context mContext;
    private int mResource;

    public MessageAdapter(@NonNull Context context, int resource, @NonNull List<Message> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource, parent, false);

        ImageView imageView = convertView.findViewById(R.id.message_img);
        imageView.setImageResource(getItem(position).getImage());

        TextView txtName = convertView.findViewById(R.id.message_user);
        txtName.setText(getItem(position).getUser());

        TextView txtTime = convertView.findViewById(R.id.message_time);
        txtTime.setText(getItem(position).getDate());
        return convertView;
    }
}
