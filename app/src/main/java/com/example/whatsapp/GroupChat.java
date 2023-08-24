package com.example.whatsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.whatsapp.Adapters.ChatAdapter;
import com.example.whatsapp.Adapters.MessagesModel;
import com.example.whatsapp.databinding.ActivityGroupChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class GroupChat extends AppCompatActivity {

    ActivityGroupChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        binding.arrowBackGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GroupChat.this, MainActivity.class));
                finish();
            }
        });

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final ArrayList<MessagesModel> messagesModels = new ArrayList<>();

        final String senderId = FirebaseAuth.getInstance().getUid();
        binding.groupName.setText("Android Group");

        final ChatAdapter adapter = new ChatAdapter(messagesModels, this);
        binding.groupRecyclerViewChat.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.groupRecyclerViewChat.setLayoutManager(layoutManager);

        database.getReference().child("Group chat")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messagesModels.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            MessagesModel model = dataSnapshot.getValue(MessagesModel.class);
                            messagesModels.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        binding.sendMessageGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message = binding.textMessageGroup.getText().toString();
                final MessagesModel model = new MessagesModel(senderId, message);
                model.setTimeStamp(new Date().getTime());
                binding.textMessageGroup.setText(" ");

                database.getReference().child("Group chat")
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });


            }
        });

    }


}