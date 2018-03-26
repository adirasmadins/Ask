package com.ask.ask;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.elmargomez.typer.Font;
import com.elmargomez.typer.Typer;

import org.json.JSONArray;

import java.util.HashMap;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
    implements ProfileFragment.OnFragmentInteractionListener,
        RequestsFragment.OnFragmentInteractionListener,
        MatchesFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener,
        AboutFragment.OnFragmentInteractionListener {

    private Button ask;
    private CollapsingToolbarLayout mToolbar;


    private CardView card;
    private Toolbar tb;

    private DrawerLayout navigationDrawerLayout;
    private NavigationView navigationView;

    // requests
    public static HashMap<String, Request> requestHashMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //----------

        //navigation bar "button"/slide
        Toolbar navigationToolbar = findViewById(R.id.toolbarid);
        setSupportActionBar(navigationToolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_navigation_menu);

        //for navigation bar
        navigationDrawerLayout = findViewById(R.id.navigation_drawer_layout);

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        setTitle(menuItem.getTitle());
                        navigationDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        final Context mainContext = this.getApplicationContext();

        FetchRequests process = new FetchRequests("https://ask-capa.herokuapp.com/api/requests", this.getApplicationContext());
        process.requestJsonReader(new RequestsCallback() {
            @Override
            public void onSuccess(JSONArray jsonArrayRequests) {
                // handle JSONOBJECT response
                requestHashMap = JsonParser.JsonArrayRequestsToHashMapRequests(jsonArrayRequests);
                for (String each : requestHashMap.keySet()) {
                    Log.d("KEY", each);

                    // display adapater
                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(mainContext, requestHashMap);
                    RecyclerView myView =  (RecyclerView)findViewById(R.id.recyclerview);
                    myView.setHasFixedSize(true);
                    myView.setAdapter(adapter);

                    LinearLayoutManager llm = new LinearLayoutManager(mainContext);
                    llm.setOrientation(LinearLayoutManager.HORIZONTAL);
                    myView.setLayoutManager(llm);
                }
            }
        });
        //----------

        //TODO: replace the user_data, item_data, request_data arrays with actual database
        //Creating an array of users

//        User user_data[] = new User[]
//                {
//                        new User(5, "bob123", "password",
//                                "bob@gmail.com", "Bob", "Builder",
//                                "Construction worker looking for some stuff",
//                                "https://en.wikipedia.org/wiki/Bob_the_Builder#/media/File:Bob_the_builder.jpg",
//                                "678-456-9831", 25, 1, "711-2880 Nulla St." +
//                                "Mankato, Mississippi 96522", R.drawable.bob_profile),
//
//                        new User(6, "jim123", "password",
//                                "jim@gmail.com", "Jim", "Jimmy",
//                                "Just a dude, doing dude things",
//                                "https://en.wikipedia.org/wiki/Bob_the_Builder#/media/File:Bob_the_builder.jpg",
//                                "770-783-2923", 29, 1, "606-3727 Ullamcorper. Street " +
//                                "Roseville, NH 11523", R.drawable.jim_profile),
//
//                        new User(7, "nancy123", "password",
//                                "nancy@gmail.com", "Nancy", "Yancy",
//                                "Just a dudette, doing dudette things",
//                                "https://en.wikipedia.org/wiki/Bob_the_Builder#/media/File:Bob_the_builder.jpg",
//                                "404-719-3817", 26, 0, "Ap #867-859 Sit Rd. Azusa, New York 39531",
//                                R.drawable.nancy_profile),
//
//                        new User(8, "karen123", "password",
//                                "karen@gmail.com", "Karen", "Barron",
//                                "Just a girl, doing girl things",
//                                "https://en.wikipedia.org/wiki/Bob_the_Builder#/media/File:Bob_the_builder.jpg",
//                                "678-820-8638", 29, 0, "7292 Dictum Av. San Antonio, MI 47096",
//                                R.drawable.karen_profile),
//
//                        new User(9, "john123", "password",
//                                "john@gmail.com", "John", "Smith",
//                                "I'm confident in my password strength",
//                                "https://en.wikipedia.org/wiki/Bob_the_Builder#/media/File:Bob_the_builder.jpg",
//                                "770-293-3621", 31, 1, "191-103 Integer Rd. " +
//                                "Corona, New Mexico 08219",
//                                R.drawable.john_profile)
//                };
//
        //Creating an array of items

        Item item_data[] = new Item[]
                {
                        new Item(5, "Golf Club", null,
                                5.00, null, R.mipmap.item_golfclub),
                        new Item(6, "Pot", null,
                                10.00, null, R.mipmap.item_pot),
                        new Item(7, "Sleeping Bag", null,
                                7.00, null, R.mipmap.item_sleepingbag),
                        new Item(8, "Surfboard", null,
                                12.00, null, R.mipmap.item_surfboard),
                        new Item(9, "Tent", null,
                                5.00, null, R.mipmap.item_tent)
                };

        //convert item_array into Hashmap

        HashMap<String, Item> itemHashMap = new HashMap<String, Item>();

        for (Item e : item_data) {
            itemHashMap.put(""+e.getItem_id(), e);
        }
//
//        //Creating an array of Requests
//
//        Request request_data[] = new Request[]
//                {
//                        new Request(user_data[0], item_data[0], "6/10/18", "6/12/18",
//                                "I want a golf club for one weekend," +
//                                        " price can be negotiable" ),
//                        new Request(user_data[1], item_data[1], "7/05/18", "7/06/18",
//                                "I am traveling for a day and " +
//                                        "need a pot to cook some food" ),
//                        new Request(user_data[2], item_data[2], "7/15/18", "7/18/18",
//                                "I am camping for a couple of days and need" +
//                                        " a sleeping bag"),
//                        new Request(user_data[3], item_data[3], "6/12/18", "6/13/18",
//                                "I want to go surfing for the day"),
//                        new Request(user_data[4], item_data[4], "7/24/18", "7/26/18",
//                                "I need a tent for the weekend, preferably" +
//                                        " for one person")
//                };
//
//
//

        //creating Ask button and the intent
        ask = (Button) findViewById(R.id.askBtn);
//
        //creating collapsing toolbar and adding title
        mToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        mToolbar.setTitle(getTitle());
        mToolbar.setExpandedTitleTextAppearance(R.style.expandingToolbar);
        mToolbar.setCollapsedTitleTextAppearance(R.style.collapsingToolbar);

        //changing title font
        Typeface font = Typer.set(this).getFont(Font.ROBOTO_THIN);
        mToolbar.setExpandedTitleTypeface(font);
        mToolbar.setCollapsedTitleTypeface(font);

        //creating the card
        card = (CardView) findViewById(R.id.requestCard);

        ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RequestActivity.class);
                startActivity(intent);
            }
        });
    }


    /**
     * Opens the navigation bar drawer when pressed or when left to right slide is made.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navigationDrawerLayout.openDrawer(GravityCompat.START); //for animation
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Determines the menuItem that was selected and goes to the corresponding fragments.
     * @param menuItem
     */
    private void selectDrawerItem(MenuItem menuItem) {
        Fragment requestedFragment = null;
        Class requestedFragmentClass = null;
        switch (menuItem.getItemId()) {
            case R.id.fragment_home:
                Log.d("selectDrawerItem", "2a");
//                requestedFragmentClass = HomeFragment.class;
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return;
            case R.id.fragment_profile:
                Log.d("selectDrawerItem", "2b");
                requestedFragmentClass = ProfileFragment.class;
                break;
            case R.id.fragment_requests:
                Log.d("selectDrawerItem", "2c");
                requestedFragmentClass = RequestsFragment.class;
                break;
            case R.id.fragment_matches:
                Log.d("selectDrawerItem", "2d");
                requestedFragmentClass = MatchesFragment.class;
                break;
////            case R.id.fragment_items:
////                Log.d("selectDrawerItem", "2e");
////                requestedFragmentClass = ItemsFragment.class;
////                break;
            case R.id.fragment_settings:
                Log.d("selectDrawerItem", "2f");
                requestedFragmentClass = SettingsFragment.class;
                break;
            case R.id.fragment_about:
                Log.d("selectDrawerItem", "2g");
                requestedFragmentClass = AboutFragment.class;
                break;
            default:
                Log.d("selectDrawerItem", "2g");
        }

        //make sure a valid fragment was selected, else just return
        try {
            requestedFragment = (Fragment) requestedFragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        //replace existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame_layout, requestedFragment);
        fragmentTransaction.addToBackStack(null); //TODO: press back to go to home immediately like Gmail
        fragmentTransaction.commit();
        // set item as selected to persist highlight
        menuItem.setChecked(true);
        // close drawer when item is tapped
        setTitle(menuItem.getTitle());
        navigationDrawerLayout.closeDrawers();
    }

    /**
     *
     */
    @Override
    public void onFragmentInteraction(Uri uri){
        //this is used to communicate with other fragments. Figure out how and if necessary
        //send data I think
    }

}