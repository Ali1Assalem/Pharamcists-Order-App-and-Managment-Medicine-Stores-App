package com.Ali.PharmacistsApp.Chatting.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Ali.PharmacistsApp.Chatting.Listeners.ConversationListener;
import com.Ali.PharmacistsApp.Chatting.Models.ChatMessage;
import com.Ali.PharmacistsApp.Chatting.Models.User;
import com.Ali.PharmacistsApp.databinding.ItemContainerRecentConversationBinding;

import java.util.List;

public class RecentConversionAdapter extends RecyclerView.Adapter<RecentConversionAdapter.ConversionViewHolder>  {

    private List<ChatMessage> chatMessages;
    private final ConversationListener conversationListener;

    public RecentConversionAdapter(List<ChatMessage> chatMessages,ConversationListener conversationListener) {
        this.chatMessages = chatMessages;
        this.conversationListener=conversationListener;
    }

    @NonNull
    @Override
    public ConversionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversionViewHolder(
                ItemContainerRecentConversationBinding.inflate(
                        LayoutInflater.from(parent.getContext())
                        ,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ConversionViewHolder holder, int position) {
        holder.setData(chatMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }



    class ConversionViewHolder extends RecyclerView.ViewHolder{

        ItemContainerRecentConversationBinding binding;

        ConversionViewHolder(ItemContainerRecentConversationBinding itemContainerRecentConversationBinding){
            super(itemContainerRecentConversationBinding.getRoot());
            binding=itemContainerRecentConversationBinding;
        }

        void setData(ChatMessage chatMessage){
            binding.imageProfile.setImageBitmap(getConversionImage(chatMessage.conversionImage));
            binding.textName.setText(chatMessage.conversionName);
            binding.textRecentMessage.setText(chatMessage.message);
            binding.getRoot().setOnClickListener(v->{
                User user=new User();
                user.id=chatMessage.conversionId;
                user.name=chatMessage.conversionName;
                user.image= chatMessage.conversionImage;
                conversationListener.onConversationClicked(user);
            });
        }
    }

    private Bitmap getConversionImage(String encodedImage){
        byte[] bytes= Base64.decode(encodedImage,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
}
