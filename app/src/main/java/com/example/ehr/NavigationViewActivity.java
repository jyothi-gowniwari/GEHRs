package com.example.ehr;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.ehr.Adapter.ExpandableListAdapter;
import com.example.ehr.Model.ExpandedMenuModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.app.PendingIntent.getActivity;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class NavigationViewActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    ExpandableListAdapter mMenuAdapter;
    private boolean ismenutoggle;
    boolean flag = false;
    private Spinner s1, s2;
    private TextView mTitle;
    ExpandableListView expandableList;
    List<ExpandedMenuModel> listDataHeader;
    HashMap<ExpandedMenuModel, List<String>> listDataChild;
    FragmentManager fragmentManager = getFragmentManager ( );
    private LinearLayout luminary_controllers;
    private LinearLayout all_luminary, all_controllers;
    private TextView luminary, control, light1, light2,lora_control;
    private boolean mluminarySelected;
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ( );
    private Context mContext;
    private boolean mIsLoadingMore = false;
    private boolean mNoMoreToLoad = false;
    ExpandableListView expListView;
    List<String> listDataHeader1;
    private Button book_appointment;
    HashMap<String, List<String>> listDataChild1;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        final ActionBar ab = getSupportActionBar ( );
        ab.setBackgroundDrawable (new ColorDrawable(Color.parseColor ("#33AF8B")));
        /* to set the menu icon image*/

        ab.setDisplayOptions (ActionBar.DISPLAY_SHOW_CUSTOM);
        ab.setCustomView (R.layout.toolbar_spinner);
        if (ab != null) {
            ab.setDisplayShowTitleEnabled (false);
            mTitle = (TextView) findViewById (R.id.toolbar_title);
            mTitle.setText ("HOME");
            mTitle.setGravity (Gravity.CENTER);
          //  Typeface typeface = Typeface.createFromAsset(getApplicationContext ().getAssets (), "fonts/astype - Secca Light.otf");
            //mTitle.setTypeface (typeface);
        }
        ab.setHomeAsUpIndicator (R.drawable.menu);
        ab.setDisplayHomeAsUpEnabled (true);

        //ab.setTitle ("HOME");
        //ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mDrawerLayout = (DrawerLayout) findViewById (R.id.drawer);
        expandableList = (ExpandableListView) findViewById (R.id.navigationmenu);
//        book_appointment=findViewById(R.id.book_appointment);
//        book_appointment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(NavigationViewActivity.this,Book_Appointment.class);
//                startActivity(intent);
//                finish();
//            }
//        });
        NavigationView navigationView = (NavigationView) findViewById (R.id.nav_view);

        clickListener ( );
        if (navigationView != null) {
            setupDrawerContent (navigationView);
        }

        prepareListData ( );
        mMenuAdapter = new ExpandableListAdapter (this, listDataHeader, listDataChild, expandableList);

        // setting list adapter
        expandableList.setAdapter (mMenuAdapter);

        expandableList.setOnChildClickListener ( new ExpandableListView.OnChildClickListener ( ) {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                //Toast.makeText(NavigationViewActivity.this,"Bus Ticket",Toast.LENGTH_SHORT).show();
                switch (groupPosition) {

                    case 1:

                        switch (childPosition) {

                            case 0:
                                mTitle.setText ("Book Appointment");
                                mTitle.setGravity (Gravity.CENTER);
                                Fragment fragment = new BookAppointmentFragement();
                                loadFragment(fragment);
                                mDrawerLayout.closeDrawer (GravityCompat.START);
                                break;
                            case 1:
                                mTitle.setText ("Reschedule Appointment");
                                mTitle.setGravity (Gravity.CENTER);
                                Fragment fragment1 = new RescheduleAppointmentFragement();
                                loadFragment(fragment1);
                                mDrawerLayout.closeDrawer (GravityCompat.START);
                                break;
                        }
                        break;
                    case 2:
                        switch (childPosition) {
                            case 0:
                                mTitle.setText ("Patient Details");
                                mTitle.setGravity (Gravity.CENTER);
                                Fragment fragment3 = new PatientDetailsFragement();
                                loadFragment(fragment3);
                                mDrawerLayout.closeDrawer (GravityCompat.START);
                                break;
                            case 1:
                                mTitle.setText ("Complaints");
                                mTitle.setGravity (Gravity.CENTER);
                                Fragment fragment2 = new ComplaintsFragement();
                                loadFragment(fragment2);
                                mDrawerLayout.closeDrawer (GravityCompat.START);
                                break;
                            case 2:
                                mTitle.setText ("Previous Medical Information");
                                mTitle.setGravity (Gravity.CENTER);
                                Fragment fragment1 = new PreviousMedicalFragement();
                                loadFragment(fragment1);
                                mDrawerLayout.closeDrawer (GravityCompat.START);
                                break;
                        }
                        break;
                    default:
                        break;
                }
                mDrawerLayout.closeDrawer (GravityCompat.START);
                return false;
            }
        });
        expandableList.setOnGroupClickListener (new android.widget.ExpandableListView.OnGroupClickListener ( ) {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                Fragment fragment = null;
                switch (groupPosition) {
//                    case 0:
//                        mTitle.setText ("REGISTRATION");
//                        mTitle.setGravity (Gravity.CENTER);
//                        fragment = new RegistrationFragement();
//                        loadFragment(fragment);
//                        mDrawerLayout.closeDrawer (GravityCompat.START);
//                        break;

//                    case 5:
//                        mTitle.setText ("ENERGY MANAGEMENT");
//                        mTitle.setGravity (Gravity.CENTER);
//                        android.support.v4.app.FragmentTransaction eng = getSupportFragmentManager ( ).beginTransaction ( );
//                        eng.replace (R.id.dynamic_frame, new AddBuildingFragement ( ));
//                        eng.addToBackStack (null);
//                        eng.commit ( );
//                        mDrawerLayout.closeDrawer (GravityCompat.START);
//
//                        break;
//                    case 6:
//                        mTitle.setText ("LOGOUT");
//                        mTitle.setGravity (Gravity.CENTER);
////                        SharedPreferences app_preferences = PreferenceManager
////                                .getDefaultSharedPreferences(getApplicationContext ());
////                        SharedPreferences.Editor editor = app_preferences.edit();
////                        editor.clear();
////                        editor.commit();
//                        Intent mainIntent;
//                        mainIntent = new Intent(getApplicationContext (), LoginActivity.class);
//                        startActivity(mainIntent);
//                        finish();
//                        mDrawerLayout.closeDrawer (GravityCompat.START);
//                        break;
                    default:
                        break;

                }

                return false;
            }

        });



    }



    private void clickListener() {

//        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener () {
//
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v,
//                                        int groupPosition, long id) {
//                // Toast.makeText(getApplicationContext(),
//                // "Group Clicked " + listDataHeader.get(groupPosition),
//                // Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
//
//        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener () {
//
//            @Override
//            public void onGroupExpand(int groupPosition) {
//
//
//
//
//
//            }
//        });

        // Listview Group collasped listener
//        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener () {
//
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//
//
//            }
//        });

//        // Listview on child click listener
//        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener () {
//
//
//                @Override
//                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//
//                    //Toast.makeText(NavigationViewActivity.this,"Bus Ticket",Toast.LENGTH_SHORT).show();
//                    switch (groupPosition) {
//
//                        case 1:
//
//                            switch (childPosition) {
//
//                                case 0:
//                                    mTitle.setText ("LIGHT CONTROL");
//                                    mTitle.setGravity (Gravity.CENTER);
//                                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager ( ).beginTransaction ( );
//                                    ft.replace (R.id.dynamic_frame, new LightControlFragment ( ));
//                                    ft.addToBackStack (null);
//                                    ft.commit ( );
//                                   // mDrawerLayout.closeDrawer (GravityCompat.START);
//                                    // Intent intent = new Intent(getApplicationContext (), AddBuildingFragement.class);
//                                    //startActivity(intent);
//                                    //Toast.makeText(NavigationViewActivity.this,"Bus Ticket",Toast.LENGTH_SHORT).show();
//                                    break;
//                                case 1:
//                                    mTitle.setText ("LIGHT CONTROL");
//                                    mTitle.setGravity (Gravity.CENTER);
//                                    android.support.v4.app.FragmentTransaction ft1 = getSupportFragmentManager ( ).beginTransaction ( );
//                                    ft1.replace (R.id.dynamic_frame, new LightControlFragment ( ));
//                                    ft1.addToBackStack (null);
//                                    ft1.commit ( );
//                                   //1 mDrawerLayout.closeDrawer (GravityCompat.START);
//                                    // Intent intent = new Intent(getApplicationContext (), AddBuildingFragement.class);
//                                    //startActivity(intent);
//                                    //Toast.makeText(NavigationViewActivity.this,"Bus Ticket",Toast.LENGTH_SHORT).show();
//                                    break;
//
//                                case 2:
//                                    // Toast.makeText(NavigationViewActivity.this,"Electricity",Toast.LENGTH_SHORT).show();
//                                    break;
//                                case 3:
//                                    // Toast.makeText(NavigationViewActivity.this,"Water",Toast.LENGTH_SHORT).show();
//                                    break;
//
//
//                            }
//                            break;
//                        case 4:
//                            switch (childPosition) {
//
//                                case 0:
//                                    mTitle.setText ("MY PROFILE");
//                                    mTitle.setGravity (Gravity.CENTER);
//                                    android.support.v4.app.FragmentTransaction my_profile = getSupportFragmentManager ( ).beginTransaction ( );
//                                    my_profile.replace (R.id.dynamic_frame, new MyProfileFragment ( ));
//                                    my_profile.addToBackStack (null);
//                                    my_profile.commit ( );
//                                    mDrawerLayout.closeDrawer (GravityCompat.START);
//                                    // Intent intent = new Intent(getApplicationContext (), AddBuildingFragement.class);
//                                    //startActivity(intent);
//                                    //Toast.makeText(NavigationViewActivity.this,"Bus Ticket",Toast.LENGTH_SHORT).show();
//                                    break;
//
//
//                                case 1:
//                                    mTitle.setText ("MANAGE OPERATOR");
//                                    Typeface typeface = Typeface.createFromAsset(getApplicationContext ().getAssets (), "fonts/astype - Secca Light.otf");
//                                    mTitle.setTypeface (typeface);
//                                    mTitle.setGravity (Gravity.CENTER);
//                                    android.support.v4.app.FragmentTransaction ft11 = getSupportFragmentManager ( ).beginTransaction ( );
//                                    ft11.replace (R.id.dynamic_frame, new ManageOpertorFragment ( ));
//                                    ft11.addToBackStack (null);
//                                    ft11.commit ( );
//                                    mDrawerLayout.closeDrawer (GravityCompat.START);
//                                    break;
//                                case 2:
//                                    mTitle.setText ("MANAGE EMPLOYEE");
//                                    mTitle.setGravity (Gravity.CENTER);
//                                    android.support.v4.app.FragmentTransaction ft1 = getSupportFragmentManager ( ).beginTransaction ( ).addToBackStack (null);
//                                    ft1.replace (R.id.dynamic_frame, new ManageEmployeeFragment ( ));
//                                    ft1.addToBackStack (null);
//                                    ft1.commit ( );
//                                    mDrawerLayout.closeDrawer (GravityCompat.START);
//                                    break;
//
//                            }
//                            break;
//
//
//                        default:
//                            break;
//
//                    }
//                return false;
//            }
//        });
//
    }



    private void prepareListData() {
        listDataHeader = new ArrayList<ExpandedMenuModel>( );
        listDataChild = new HashMap<ExpandedMenuModel, List<String>>( );

        ExpandedMenuModel item1 = new ExpandedMenuModel ( );
        item1.setIconName ("Dashboard");
        item1.setIconImg (R.drawable.ic_account_circle_black_24dp);
        listDataHeader.add (item1);

        List<String> heading0 = new ArrayList<String>( );

        ExpandedMenuModel item2 = new ExpandedMenuModel ( );
        item2.setIconName ("Appointment Scheduling");
        item2.setIconImg (R.drawable.ic_round_local_hospital_24);
        // Adding data header
        listDataHeader.add (item2);

        List<String> heading1 = new ArrayList<String>( );
        heading1.add ("Book Appointment");
        heading1.add ("Reschedule Appointment");
        heading1.add ("Payment");

        ExpandedMenuModel item6 = new ExpandedMenuModel ( );
        item6.setIconName ("Patient Medical Information");
        item6.setIconImg (R.drawable.ic_account_circle_black_24dp);
        listDataHeader.add (item6);

        List<String> heading2 = new ArrayList<String>( );
        heading2.add ("Details");
        heading2.add ("Complaints");
        heading2.add ("Previous Medical Information");


        ExpandedMenuModel item7 = new ExpandedMenuModel ( );
        item7.setIconName ("Review & Ratings");
        item7.setIconImg (R.drawable.ic_round_rate_review_24);
        listDataHeader.add (item7);
        List<String> heading3 = new ArrayList<String>( );

        ExpandedMenuModel item8 = new ExpandedMenuModel ( );
        item8.setIconName ("Chat With Doctor");
        item8.setIconImg (R.drawable.ic_round_chat_24);
        listDataHeader.add (item8);
        List<String> heading4 = new ArrayList<String>( );

        ExpandedMenuModel item9 = new ExpandedMenuModel ( );
        item9.setIconName ("Logout");
        item9.setIconImg (R.drawable.ic_round_chat_24);
        listDataHeader.add (item9);
        List<String> heading5 = new ArrayList<String>( );

        listDataChild.put (listDataHeader.get (0), heading0);
        listDataChild.put (listDataHeader.get (1), heading1);// Header, Child data
        listDataChild.put (listDataHeader.get (2), heading2);
        listDataChild.put (listDataHeader.get (3), heading3);
        listDataChild.put (listDataHeader.get (4), heading4);
        listDataChild.put (listDataHeader.get (5), heading5);
        // listDataHeader.get (5);
        //  listDataHeader.get ()
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mDrawerLayout.closeDrawer (GravityCompat.START);
        return true;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if(ismenutoggle==true) {
                    mDrawerLayout.openDrawer (GravityCompat.START);
                    ismenutoggle=false;
                }
                else{

                    mDrawerLayout.closeDrawer (GravityCompat.START);
                    ismenutoggle=true;
                }
                return true;
                }
        return super.onOptionsItemSelected(item);
        }



    private AdapterView.OnItemClickListener mDrawerItemClickedListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                long arg3) {



        }
    };





    private DrawerLayout.DrawerListener mDrawerListener = new DrawerLayout.DrawerListener () {

        @Override
        public void onDrawerStateChanged(int status) {

        }

        @Override
        public void onDrawerSlide(View view, float slideArg) {

        }

        @Override
        public void onDrawerOpened(View view) {
            mDrawerLayout.openDrawer (view);
        }

        @Override
        public void onDrawerClosed(View view) {
            mDrawerLayout.closeDrawer (view);
        }
    };

    private void setupDrawerContent(NavigationView navigationView) {
        //revision: this don't works, use setOnChildClickListener() and setOnGroupClickListener() above instead
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        if(menuItem.isChecked ()){
                            mDrawerLayout.openDrawer (GravityCompat.START);}
                        else {
                            mDrawerLayout.closeDrawer (GravityCompat.START);
                        }


                        return true;
                    }
                });
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.dynamic_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
