package com.ravenchat.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.ravenchat.Holder.QBUnreadMessageHolder;
import com.ravenchat.R;
import com.quickblox.chat.model.QBChatDialog;

import java.util.ArrayList;


public class ChatDialogsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<QBChatDialog> qbChatDialogs;
    TextView txtTitle, txtMessage;
    ImageView imageView;
    LayoutInflater inflater;

    public ChatDialogsAdapter(Context context, ArrayList<QBChatDialog> qbChatDialogs) {
        this.context = context;
        this.qbChatDialogs = qbChatDialogs;
    }


    @Override
    public int getCount() {
        return qbChatDialogs.size();
    }

    @Override
    public Object getItem(int position) {
        return qbChatDialogs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_chat_dialogs, null);

            TextView txtTitle, txtMessage;
            ImageView imageView, imageUnread;

            txtMessage = (TextView)view.findViewById(R.id.list_chat_dialog_message);
            txtTitle = (TextView)view.findViewById(R.id.list_chat_dialog_title);
            imageView = (ImageView)view.findViewById(R.id.image_chatDialog);
            imageUnread = (ImageView)view.findViewById(R.id.image_unRead);

            txtMessage.setText(qbChatDialogs.get(position).getLastMessage());
            txtTitle.setText(qbChatDialogs.get(position).getName());

            ColorGenerator generator = ColorGenerator.MATERIAL;
            int randomColor = generator.getRandomColor();

            TextDrawable.IBuilder builder = TextDrawable.builder().beginConfig()
                    .withBorder(4)
                    .endConfig()
                    .round();

//            toma la primera letra y la pone de perfil
            TextDrawable drawable = builder.build(txtTitle.getText().toString().substring(0, 1).toUpperCase(), randomColor);
            imageView.setImageDrawable(drawable);

            TextDrawable.IBuilder unreadBuilder = TextDrawable.builder().beginConfig()
                    .bold()
                    .withBorder(4)
                    .endConfig()
                    .round();
            int unread_count = QBUnreadMessageHolder.getInstance().getBundle().getInt(qbChatDialogs.get(position).getDialogId());
            if (unread_count > 0){
                TextDrawable unread_drawable = builder.build(""+unread_count, Color.BLACK);
                imageUnread.setImageDrawable(unread_drawable);
            }
        }
        return view;
    }
}
