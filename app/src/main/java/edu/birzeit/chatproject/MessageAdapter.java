package edu.birzeit.chatproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<MessageModel> messageList;

    public MessageAdapter(List<MessageModel> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        MessageModel message = messageList.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        ImageView senderImage;
        TextView messageTextView;
        TextView time;
        TextView senderName;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            senderImage = itemView.findViewById(R.id.senderImage);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            time = itemView.findViewById(R.id.editTextTime);
            senderName = itemView.findViewById(R.id.senderName);
        }

        public void bind(MessageModel message) {
            senderImage.setImageResource(R.drawable.photocamera);
            messageTextView.setText(message.getMessageText());
            time.setText(message.getTimestamp() + "");
            senderName.setText(message.getSenderId());//Change it to name using sql
        }
    }
}
