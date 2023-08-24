package com.example.whatsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.whatsapp.Adapters.ChatAdapter;
import com.example.whatsapp.Adapters.MessagesModel;
import com.example.whatsapp.databinding.ActivityChatDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {

    ActivityChatDetailBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        final String senderId = auth.getUid();
        String recieveId = getIntent().getStringExtra("id");
        String userName = getIntent().getStringExtra("name");
        String profilePic = getIntent().getStringExtra("profilePic");

        binding.userNameChat.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.person).into(binding.profileImage);

        binding.arrowBackChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatDetailActivity.this, MainActivity.class));
            }
        });

        final ArrayList<MessagesModel> messagesModels = new ArrayList<>();
        final ChatAdapter chatAdapter = new ChatAdapter(messagesModels, this);

        binding.chatRecyclerViewChat.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ChatDetailActivity.this);
        binding.chatRecyclerViewChat.setLayoutManager(layoutManager);


        final String senderRoom = senderId + recieveId;
        final String receiverRoom = recieveId + senderId;

        database.getReference().child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messagesModels.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            MessagesModel model = dataSnapshot.getValue(MessagesModel.class);
                            messagesModels.add(model);
                        }
//                        chatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        binding.sendMessageChat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String message = binding.textMessageChat.getText().toString();
                final MessagesModel model = new MessagesModel(senderId, message);

                model.setTimeStamp(new Date().getTime());
                binding.textMessageChat.setText("");

                database.getReference().child("chats")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                database.getReference().child("chats")
                                        .child(receiverRoom)
                                        .push()
                                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                            }
                                        });
                            }
                        });
            }
        });


    }
}