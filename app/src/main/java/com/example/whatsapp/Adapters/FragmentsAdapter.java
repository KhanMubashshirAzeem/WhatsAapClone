package com.example.whatsapp.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.whatsapp.Fragments.Calls;
import com.example.whatsapp.Fragments.Chats;
import com.example.whatsapp.Fragments.Status;

public class FragmentsAdapter extends FragmentPagerAdapter {

    public FragmentsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new Chats();
            case 1:
                return new Status();
            case 2:
                return new Calls();
            default:
                return new Chats();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position==0)
        {
            title = "Chats";
        }
        if (position==1)
        {
            title = "Status";
        }
        if (position==2)
        {
            title = "Calls";
        }
        return title;
    }
}
