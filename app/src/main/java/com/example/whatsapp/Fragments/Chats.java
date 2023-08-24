package com.example.whatsapp.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.whatsapp.Adapters.UsersAdapter;
import com.example.whatsapp.Models.Users;
import com.example.whatsapp.databinding.FragmentChatsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Chats extends Fragment {

    public Chats() {
        // Required empty public constructor
    }

    FragmentChatsBinding binding;
    ArrayList<Users> list = new ArrayList<>();
    FirebaseDatabase database;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentChatsBinding.inflate(inflater, container, false);

        database = FirebaseDatabase.getInstance();

        UsersAdapter adapter = new UsersAdapter(list, getContext());
        binding.chatRv.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.chatRv.setLayoutManager(layoutManager);

        database.getReference()
                .child("Users")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        list.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Users users = dataSnapshot.getValue(Users.class);
                            assert users != null;
                            users.setId(dataSnapshot.getKey());
//                    //if we uncomment this code then login user cant see him self in MainActivty recyclerview
                    if (!users.getId().equals(FirebaseAuth.getInstance().getUid())) {
                            list.add(users);
                    }

                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        return binding.getRoot();
    }

}