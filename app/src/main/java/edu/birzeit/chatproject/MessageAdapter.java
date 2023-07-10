package edu.birzeit.chatproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter {

    private List<MessageModel> messageList;
    final int ItemSend = 1;
    final int Itemrecived = 2;

    public MessageAdapter(List<MessageModel> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ItemSend) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sender_message, parent, false);
            return new MessageViewHolder(view);

        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
            return new MessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel message = messageList.get(position);
        if (holder.getClass() == SenderViewHolder.class) {
            SenderViewHolder viewHolder = (SenderViewHolder) holder;
            viewHolder.bind(message);

        } else {
            MessageViewHolder viewHolder = (MessageViewHolder) holder;
            viewHolder.bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {//Checks for view type of message sent or recived
        MessageModel message = messageList.get(position);
        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals((message.getSenderId()))) {
            return ItemSend;
        } else {
            return Itemrecived;
        }
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
            senderName.setText(message.getSenderName());//Change it to name using sql
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {
        ImageView senderImage;
        TextView messageTextView;
        TextView time;
        TextView senderName;

        public SenderViewHolder(@NonNull View itemView) {
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
