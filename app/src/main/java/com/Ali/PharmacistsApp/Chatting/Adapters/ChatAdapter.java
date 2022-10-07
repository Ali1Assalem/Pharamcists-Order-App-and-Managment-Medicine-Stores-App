package com.Ali.PharmacistsApp.Chatting.Adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Ali.PharmacistsApp.Chatting.Models.ChatMessage;
import com.Ali.PharmacistsApp.databinding.ItemContainerRecivedMessageBinding;
import com.Ali.PharmacistsApp.databinding.ItemContainerSentMessageBinding;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final List<ChatMessage> chatMessages;
    private Bitmap reciverProfileImage;
    private final String senderId;

    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;

    public void setReciverProfileImage(Bitmap bitmap){
        reciverProfileImage=bitmap;
    }

    public ChatAdapter(List<ChatMessage> chatMessages, Bitmap reciverProfileImage, String senderId) {
        this.chatMessages = chatMessages;
        this.reciverProfileImage = reciverProfileImage;
        this.senderId = senderId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT){
            return new SentMessageViewHolder(
                    ItemContainerSentMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,false)
            );
        }else {
           return new RecivedMessageViewHolder(ItemContainerRecivedMessageBinding
            .inflate(LayoutInflater.from(parent.getContext())
            ,parent,false));

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==VIEW_TYPE_SENT){
            ((SentMessageViewHolder) holder).setData(chatMessages.get(position));
        }else {
            ((RecivedMessageViewHolder) holder).setData(chatMessages.get(position),reciverProfileImage);
        }
    }


    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chatMessages.get(position).senderId.equals(senderId)){
            return VIEW_TYPE_SENT;
        }else
            return VIEW_TYPE_RECEIVED;
    }

    static class SentMessageViewHolder extends RecyclerView.ViewHolder{
        private final ItemContainerSentMessageBinding binding;

        SentMessageViewHolder(ItemContainerSentMessageBinding itemContainerSentMessageBinding){
            super(itemContainerSentMessageBinding.getRoot());
            binding=itemContainerSentMessageBinding;
        }

        void setData(ChatMessage chatMessage){
            binding.textMessage.setText(chatMessage.message);
            binding.textDateTime.setText(chatMessage.dateTime);
        }
    }

    static class RecivedMessageViewHolder extends RecyclerView.ViewHolder{

        private final ItemContainerRecivedMessageBinding binding;

        RecivedMessageViewHolder(ItemContainerRecivedMessageBinding itemContainerRecivedMessageBinding){
            super(itemContainerRecivedMessageBinding.getRoot());
            binding=itemContainerRecivedMessageBinding;
        }

        void setData(ChatMessage chatMessage,Bitmap receiverProfileImage){
            binding.textMessage.setText(chatMessage.message);
            binding.textDateTime.setText(chatMessage.dateTime);
            if (receiverProfileImage != null){
                binding.imageProfile.setImageBitmap(receiverProfileImage);
            }
        }
    }
}
