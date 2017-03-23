package com.shades.shade.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.shades.shade.R;
import com.shades.shade.adapters.DrawerAdapter;
import com.shades.shade.fragments.FragmentCheckIn;
import com.shades.shade.fragments.FragmentDashboard;
import com.shades.shade.fragments.FragmentHistory;
import com.shades.shade.model.DrawerItem;
import com.shades.shade.utility.AppConstant;
import com.shades.shade.widgets.PagerSlidingTabStrip;
import com.shades.shade.widgets.ShadeTextView;

import java.util.ArrayList;

public class HomeActivity extends ShadeBaseActivity {

    private Context context;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;

    private RecyclerView recyclerViewDrawer;
    private RecyclerView.Adapter adapterDrawer;
    private ShadeTextView userEmail;

    private ViewPager pager;
    private PagerSlidingTabStrip tabs;
    private final String[] TITLES = {"DASHBOARD", "HISTORY", "CHECK IN"};
    private FragmentDashboard frgDashboard;
    private FragmentHistory frgHistory;
    private FragmentCheckIn frgCheckIn;

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
            overridePendingTransition(0, 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onUiLayout() {
        setContentView(R.layout.activity_home);
        context = HomeActivity.this;
    }

    @Override
    protected void onUiComponentInit() {
        toolbarStatusBar();
        navigationDrawer();
        loadDrawerItems();
        loadTabPager();
    }

    @Override
    protected void onListenersInit() {

    }

    @Override
    protected void onListenersRemove() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    public void toolbarStatusBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void navigationDrawer() {
        // Cast drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        userEmail = (ShadeTextView) findViewById(R.id.user_email);

        // Fix right margin to 56dp (portrait)
        View drawer = findViewById(R.id.drawerMenuPanel);
        ViewGroup.LayoutParams layoutParams = drawer.getLayoutParams();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutParams.width = displayMetrics.widthPixels - (56 * Math.round(displayMetrics.density));
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutParams.width = displayMetrics.widthPixels + (20 * Math.round(displayMetrics.density)) - displayMetrics.widthPixels / 2;
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

    }

    private void loadDrawerItems() {
        recyclerViewDrawer = (RecyclerView) findViewById(R.id.recyclerViewDrawer);
        recyclerViewDrawer.setHasFixedSize(true);
        recyclerViewDrawer.setLayoutManager(new LinearLayoutManager(context));

        ArrayList<DrawerItem> drawerItems = new ArrayList<>();
        final String[] drawerTitles = getResources().getStringArray(R.array.drawer);
        final TypedArray drawerIcons = getResources().obtainTypedArray(R.array.drawerIcons);
        for (int i = 0; i < drawerTitles.length; i++) {
            drawerItems.add(new DrawerItem(drawerTitles[i], drawerIcons.getDrawable(i), false));
        }
        drawerIcons.recycle();
        adapterDrawer = new DrawerAdapter(context, drawerItems);
        recyclerViewDrawer.setAdapter(adapterDrawer);
        recyclerViewDrawer.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View rowItem = rv.findChildViewUnder(e.getX(), e.getY());
                if (rowItem != null && e.getAction() == MotionEvent.ACTION_UP) {
                    onRecyclerItemClickerListner(rv.getChildAdapterPosition(rowItem));
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    private void onRecyclerItemClickerListner(int childAdapterPosition) {
        switch (AppConstant.DrawerItems.values()[childAdapterPosition]) {
            case ChatWithUs:
                break;

            case Sensor:
                break;

            case Notifications:
                break;

            case ChangePassword:
                startActivity(new Intent(context, ForgotPasswordActivity.class));
                overridePendingTransition(0, 0);
                break;

            case LegalInformation:
                break;

            case Help:
                break;

            case SignOut:
                break;
        }
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }
    }

    private void loadTabPager() {
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabs.setDrawBox(false);
        tabs.setViewPager(pager);
    }

    public class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    frgDashboard = FragmentDashboard.newInstance(position, context);
                    return frgDashboard;
                case 1:
                    frgHistory = FragmentHistory.newInstance(position, context);
                    return frgHistory;
                case 2:
                    frgCheckIn = FragmentCheckIn.newInstance(position, context);
                    return frgCheckIn;
            }
            return null;
        }
    }
}
