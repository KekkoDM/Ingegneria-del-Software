package com.example.cinemates.testing;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.example.cinemates.MainActivity;
import com.example.cinemates.activities.ResultsActivity;
import com.example.cinemates.adapters.FriendsAdapter;
import com.example.cinemates.adapters.SearchUserAdapter;
import com.example.cinemates.classes.Utente;
import com.example.cinemates.fragments.HomeFragment;
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestAlreadyFriends {


    @Test
    public void alreadyFriendsTrue(){

        Utente friend = new Utente("vincy99");
        MainActivity ma = new MainActivity();
        MainActivity.selectedFragment = new HomeFragment();
        ArrayList<Utente> friends = new ArrayList<>();

        friends.add(friend);
        FriendsAdapter friendsAdapter = new FriendsAdapter(MainActivity.selectedFragment.getContext(),friends);

        SearchUserAdapter sa = new SearchUserAdapter( MainActivity.selectedFragment.getContext(),friends);

        Assert.assertTrue(sa.alreadyFriends(friend));
    }


    @Test
    public void alreadyFriendsFalse(){

        Utente friend = new Utente("vincy99");
        MainActivity ma = new MainActivity();
        MainActivity.selectedFragment = new HomeFragment();
        ArrayList<Utente> friends = new ArrayList<>();

        friends.add(friend);
        FriendsAdapter friendsAdapter = new FriendsAdapter(MainActivity.selectedFragment.getContext(),friends);

        SearchUserAdapter sa = new SearchUserAdapter( MainActivity.selectedFragment.getContext(),friends);

        Utente user = new Utente("balto");
        Assert.assertFalse(sa.alreadyFriends(user));
    }

    @Test(expected = NullPointerException.class)
    public void alreadyFriendsListFriendsNull(){

        Utente friend = new Utente("vincy99");
        MainActivity ma = new MainActivity();
        MainActivity.selectedFragment = new HomeFragment();
        ArrayList<Utente> friends = new ArrayList<>();

        friends.add(friend);
        FriendsAdapter friendsAdapter = new FriendsAdapter(MainActivity.selectedFragment.getContext(),null);

        SearchUserAdapter sa = new SearchUserAdapter( MainActivity.selectedFragment.getContext(),friends);

        Utente user = new Utente("balto");
        Assert.assertTrue(sa.alreadyFriends(user));
    }

    @Test(expected = Error.class)
    public void alreadyFriendsListSearchNull(){

        Utente friend = new Utente("vincy99");
        MainActivity ma = new MainActivity();
        MainActivity.selectedFragment = new HomeFragment();
        ArrayList<Utente> friends = new ArrayList<>();

        friends.add(friend);
        FriendsAdapter friendsAdapter = new FriendsAdapter(MainActivity.selectedFragment.getContext(),friends);

        SearchUserAdapter sa = new SearchUserAdapter( MainActivity.selectedFragment.getContext(),null);

        Assert.assertFalse(sa.alreadyFriends(friend));
    }

    @Test(expected = NullPointerException.class)
    public void alreadyFriendsUserNull(){

        Utente friend = new Utente("vincy99");
        MainActivity ma = new MainActivity();
        MainActivity.selectedFragment = new HomeFragment();
        ArrayList<Utente> friends = new ArrayList<>();

        friends.add(friend);
        FriendsAdapter friendsAdapter = new FriendsAdapter(MainActivity.selectedFragment.getContext(),friends);

        SearchUserAdapter sa = new SearchUserAdapter( MainActivity.selectedFragment.getContext(),friends);

        Assert.assertFalse(sa.alreadyFriends(null));
    }

    @Test
    public void alreadyFriendsNoFriend(){

        Utente friend = new Utente("vincy99");
        MainActivity ma = new MainActivity();
        MainActivity.selectedFragment = new HomeFragment();
        ArrayList<Utente> friends = new ArrayList<>();

        FriendsAdapter friendsAdapter = new FriendsAdapter(MainActivity.selectedFragment.getContext(),friends);
        SearchUserAdapter sa = new SearchUserAdapter( MainActivity.selectedFragment.getContext(),friends);

        Assert.assertFalse(sa.alreadyFriends(friend));
    }

    @Test(expected = NullPointerException.class)
    public void alreadyFriendsFriendListNullSearchNull(){

        Utente friend = new Utente("vincy99");
        MainActivity ma = new MainActivity();
        MainActivity.selectedFragment = new HomeFragment();


        FriendsAdapter friendsAdapter = new FriendsAdapter(MainActivity.selectedFragment.getContext(),null);
        SearchUserAdapter sa = new SearchUserAdapter( MainActivity.selectedFragment.getContext(),null);

        Assert.assertFalse(sa.alreadyFriends(friend));
    }

    @Test(expected = NullPointerException.class)
    public void alreadyFriendsFriendListNullSearchNullFriendNull(){

        MainActivity ma = new MainActivity();
        MainActivity.selectedFragment = new HomeFragment();


        FriendsAdapter friendsAdapter = new FriendsAdapter(MainActivity.selectedFragment.getContext(),null);
        SearchUserAdapter sa = new SearchUserAdapter( MainActivity.selectedFragment.getContext(),null);

        Assert.assertFalse(sa.alreadyFriends(null));
    }

    @Test
    public void alreadyFriendsFalseMultiFriends(){

        MainActivity ma = new MainActivity();
        MainActivity.selectedFragment = new HomeFragment();
        ArrayList<Utente> friends = new ArrayList<>();

        friends.add(new Utente("vincy99"));
        friends.add(new Utente("balto"));

        FriendsAdapter friendsAdapter = new FriendsAdapter(MainActivity.selectedFragment.getContext(),friends);
        SearchUserAdapter sa = new SearchUserAdapter( MainActivity.selectedFragment.getContext(),friends);

        Assert.assertFalse(sa.alreadyFriends(new Utente("PerCoca")));
    }

    @Test
    public void alreadyFriendsTrueMultiFriends(){

        MainActivity ma = new MainActivity();
        MainActivity.selectedFragment = new HomeFragment();
        ArrayList<Utente> friends = new ArrayList<>();

        friends.add(new Utente("vincy99"));
        friends.add(new Utente("balto"));
        friends.add(new Utente("Carlo"));

        FriendsAdapter friendsAdapter = new FriendsAdapter(MainActivity.selectedFragment.getContext(),friends);
        SearchUserAdapter sa = new SearchUserAdapter( MainActivity.selectedFragment.getContext(),friends);

        Assert.assertTrue(sa.alreadyFriends(new Utente("balto")));
    }

    @Test
    public void alreadyFriendsMultiFriendsNoResult(){

        MainActivity ma = new MainActivity();
        MainActivity.selectedFragment = new HomeFragment();
        ArrayList<Utente> friends = new ArrayList<>();

        friends.add(new Utente("vincy99"));
        friends.add(new Utente("balto"));
        friends.add(new Utente("Carlo"));

        FriendsAdapter friendsAdapter = new FriendsAdapter(MainActivity.selectedFragment.getContext(),friends);
        friends.clear();
        SearchUserAdapter sa = new SearchUserAdapter( MainActivity.selectedFragment.getContext(),friends);

        Assert.assertFalse(sa.alreadyFriends(new Utente("balto")));
    }


    @Test
    public void alreadyFriendsTrueMultiFriendsMultiResult(){

        MainActivity ma = new MainActivity();
        MainActivity.selectedFragment = new HomeFragment();
        ArrayList<Utente> friends = new ArrayList<>();

        friends.add(new Utente("vincy99"));
        friends.add(new Utente("balto"));
        friends.add(new Utente("Carlo"));

        FriendsAdapter friendsAdapter = new FriendsAdapter(MainActivity.selectedFragment.getContext(),friends);
        friends.clear();
        friends.add(new Utente("io"));
        friends.add(new Utente("tu"));
        friends.add(new Utente("vincy99"));
        friends.add(new Utente("noi"));
        SearchUserAdapter sa = new SearchUserAdapter( MainActivity.selectedFragment.getContext(),friends);

        Assert.assertTrue(sa.alreadyFriends(new Utente("vincy99")));
    }

    @Test
    public void alreadyFriendsFalseMultiFriendsMultiResult(){

        MainActivity ma = new MainActivity();
        MainActivity.selectedFragment = new HomeFragment();
        ArrayList<Utente> friends = new ArrayList<>();

        friends.add(new Utente("vincy99"));
        friends.add(new Utente("balto"));
        friends.add(new Utente("Carlo"));

        FriendsAdapter friendsAdapter = new FriendsAdapter(MainActivity.selectedFragment.getContext(),friends);
        friends.clear();
        friends.add(new Utente("io"));
        friends.add(new Utente("tu"));
        friends.add(new Utente("vincy99"));
        friends.add(new Utente("noi"));
        SearchUserAdapter sa = new SearchUserAdapter( MainActivity.selectedFragment.getContext(),friends);

        Assert.assertFalse(sa.alreadyFriends(new Utente("balto")));
    }

    @Test
    public void alreadyFriendsFalseMultiFriendsMultiResultUserVuoto(){

        MainActivity ma = new MainActivity();
        MainActivity.selectedFragment = new HomeFragment();
        ArrayList<Utente> friends = new ArrayList<>();

        friends.add(new Utente("vincy99"));
        friends.add(new Utente("balto"));
        friends.add(new Utente("Carlo"));

        FriendsAdapter friendsAdapter = new FriendsAdapter(MainActivity.selectedFragment.getContext(),friends);
        friends.clear();
        friends.add(new Utente("io"));
        friends.add(new Utente("tu"));
        friends.add(new Utente("vincy99"));
        friends.add(new Utente("noi"));
        SearchUserAdapter sa = new SearchUserAdapter( MainActivity.selectedFragment.getContext(),friends);

        Assert.assertFalse(sa.alreadyFriends(new Utente("")));
    }
}
