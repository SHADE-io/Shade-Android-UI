package com.shades.shade.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.shades.shade.R;
import com.shades.shade.adapters.DrawerAdapter;
import com.shades.shade.fragments.FragmentCheckIn;
import com.shades.shade.fragments.FragmentDashboard;
import com.shades.shade.fragments.FragmentHistory;
import com.shades.shade.model.DrawerItem;
import com.shades.shade.widgets.PagerSlidingTabStrip;
import com.shades.shade.widgets.SlidingTabLayout;

import java.util.ArrayList;

public class HomeActivity extends ShadeBaseActivity {

    private Context context;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;

    private RecyclerView recyclerViewDrawer;
    private RecyclerView.Adapter adapterDrawer;


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

        // Setup Drawer Icon
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        // statusBar color behind navigation drawer
        TypedValue typedValueStatusBarColor = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueStatusBarColor, true);
        final int colorStatusBar = typedValueStatusBarColor.data;
        mDrawerLayout.setStatusBarBackgroundColor(colorStatusBar);

        // Setup RecyclerView inside drawer
        final TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        final int color = typedValue.data;

        recyclerViewDrawer = (RecyclerView) findViewById(R.id.recyclerViewDrawer);
        recyclerViewDrawer.setHasFixedSize(true);
        recyclerViewDrawer.setLayoutManager(new LinearLayoutManager(context));

        ArrayList<DrawerItem> drawerItems = new ArrayList<>();
        final String[] drawerTitles = getResources().getStringArray(R.array.drawer);
        final TypedArray drawerIcons = getResources().obtainTypedArray(R.array.drawerIcons);
        for (int i = 0; i < drawerTitles.length; i++) {
            drawerItems.add(new DrawerItem(drawerTitles[i], drawerIcons.getDrawable(i)));
        }
        drawerIcons.recycle();
        adapterDrawer = new DrawerAdapter(context, drawerItems);
        recyclerViewDrawer.setAdapter(adapterDrawer);

//        recyclerViewDrawer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//
//                for (int i = 0; i < drawerTitles.length; i++) {
//                    if (i == sharedPreferences.getInt("FRAGMENT", 0)) {
//                        ImageView imageViewDrawerIcon = (ImageView) recyclerViewDrawer.getChildAt(i).findViewById(R.id.imageViewDrawerIcon);
//                        TextView textViewDrawerTitle = (TextView) recyclerViewDrawer.getChildAt(i).findViewById(R.id.textViewDrawerItemTitle);
//                        imageViewDrawerIcon.setColorFilter(color);
//                        if (Build.VERSION.SDK_INT > 15) {
//                            imageViewDrawerIcon.setImageAlpha(255);
//                        } else {
//                            imageViewDrawerIcon.setAlpha(255);
//                        }
//                        textViewDrawerTitle.setTextColor(color);
//                        RelativeLayout relativeLayoutDrawerItem = (RelativeLayout) recyclerViewDrawer.getChildAt(i).findViewById(R.id.relativeLayoutDrawerItem);
//                        TypedValue typedValueDrawerSelected = new TypedValue();
//                        getTheme().resolveAttribute(R.attr.colorPrimary, typedValueDrawerSelected, true);
//                        int colorDrawerItemSelected = typedValueDrawerSelected.data;
//                        colorDrawerItemSelected = (colorDrawerItemSelected & 0x00FFFFFF) | 0x30000000;
//                        relativeLayoutDrawerItem.setBackgroundColor(colorDrawerItemSelected);
//
//                    } else {
//                        ImageView imageViewDrawerIcon = (ImageView) recyclerViewDrawer.getChildAt(i).findViewById(R.id.imageViewDrawerIcon);
//                        TextView textViewDrawerTitle = (TextView) recyclerViewDrawer.getChildAt(i).findViewById(R.id.textViewDrawerItemTitle);
//                        imageViewDrawerIcon.setColorFilter(getResources().getColor(R.color.md_text));
//                        if (Build.VERSION.SDK_INT > 15) {
//                            imageViewDrawerIcon.setImageAlpha(138);
//                        } else {
//                            imageViewDrawerIcon.setAlpha(138);
//                        }
//                        textViewDrawerTitle.setTextColor(getResources().getColor(R.color.md_text));
//                        RelativeLayout relativeLayoutDrawerItem = (RelativeLayout) recyclerViewDrawer.getChildAt(i).findViewById(R.id.relativeLayoutDrawerItem);
//                        relativeLayoutDrawerItem.setBackgroundColor(getResources().getColor(R.color.md_white_1000));
//                    }
//                }
//
//                // unregister listener (this is important)
//                recyclerViewDrawer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//            }
//        });
//
//
//        // RecyclerView item listener.
//        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(recyclerViewDrawer);
//        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//            @Override
//            public void onItemClick(RecyclerView parent, View view, final int position, long id) {
//
//                for (int i = 0; i < drawerTitles.length; i++) {
//                    if (i == position) {
//                        ImageView imageViewDrawerIcon = (ImageView) recyclerViewDrawer.getChildAt(i).findViewById(R.id.imageViewDrawerIcon);
//                        TextView textViewDrawerTitle = (TextView) recyclerViewDrawer.getChildAt(i).findViewById(R.id.textViewDrawerItemTitle);
//                        imageViewDrawerIcon.setColorFilter(color);
//                        if (Build.VERSION.SDK_INT > 15) {
//                            imageViewDrawerIcon.setImageAlpha(255);
//                        } else {
//                            imageViewDrawerIcon.setAlpha(255);
//                        }
//                        textViewDrawerTitle.setTextColor(color);
//                        RelativeLayout relativeLayoutDrawerItem = (RelativeLayout) recyclerViewDrawer.getChildAt(i).findViewById(R.id.relativeLayoutDrawerItem);
//                        TypedValue typedValueDrawerSelected = new TypedValue();
//                        getTheme().resolveAttribute(R.attr.colorPrimary, typedValueDrawerSelected, true);
//                        int colorDrawerItemSelected = typedValueDrawerSelected.data;
//                        colorDrawerItemSelected = (colorDrawerItemSelected & 0x00FFFFFF) | 0x30000000;
//                        relativeLayoutDrawerItem.setBackgroundColor(colorDrawerItemSelected);
//
//                    } else {
//                        ImageView imageViewDrawerIcon = (ImageView) recyclerViewDrawer.getChildAt(i).findViewById(R.id.imageViewDrawerIcon);
//                        TextView textViewDrawerTitle = (TextView) recyclerViewDrawer.getChildAt(i).findViewById(R.id.textViewDrawerItemTitle);
//                        imageViewDrawerIcon.setColorFilter(getResources().getColor(R.color.md_text));
//                        if (Build.VERSION.SDK_INT > 15) {
//                            imageViewDrawerIcon.setImageAlpha(138);
//                        } else {
//                            imageViewDrawerIcon.setAlpha(138);
//                        }
//                        textViewDrawerTitle.setTextColor(getResources().getColor(R.color.md_text));
//                        RelativeLayout relativeLayoutDrawerItem = (RelativeLayout) recyclerViewDrawer.getChildAt(i).findViewById(R.id.relativeLayoutDrawerItem);
//                        relativeLayoutDrawerItem.setBackgroundColor(getResources().getColor(R.color.md_white_1000));
//                    }
//                }
//
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        // Do something after some time
//                        setFragment(position);
//                        if (isExpand) {
//                            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
//                        }
//                    }
//                }, 250);
//                mDrawerLayout.closeDrawers();
//            }
//        });
    }


    private ViewPager pager;
    private SlidingTabLayout tabs;
    private final String[] TITLES = {"DASHBOARD", "HISTORY", "CHECK IN"};

    private void loadTabPager() {
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        tabs.setViewPager(pager);
        tabs.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));
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
                    return FragmentDashboard.newInstance(position, context);
                case 1:
                    return FragmentHistory.newInstance(position, context);
                case 2:
                    return FragmentCheckIn.newInstance(position, context);
            }
            return null;
        }
    }
}
