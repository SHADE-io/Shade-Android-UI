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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.shades.shade.R;
import com.shades.shade.adapters.DrawerAdapter;
import com.shades.shade.dialogs.DialogButtonClickListener;
import com.shades.shade.dialogs.ShadeAlertDialog;
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
    private ShadeTextView txt_title;
    private ImageView batteryStatus;

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

    private ShadeAlertDialog dialog;
    public RelativeLayout layoutMain;

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            onExit();
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

    @Override
    protected void onInitDataLoad() {
        //TODO After UI Load complete, if you want to do something, do it here
    }

    public void toolbarStatusBar() {
        layoutMain = (RelativeLayout) findViewById(R.id.layoutMain);
        txt_title = (ShadeTextView) findViewById(R.id.topBar_txt_title);
        batteryStatus = (ImageView) findViewById(R.id.topBar_batteryStatus);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txt_title.setText(TITLES[0]);//Title
        batteryStatus.setImageResource(R.drawable.battery_full);//Battery statusChange
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
            case Settings:
                startActivity(new Intent(context, SettingActivity.class));
                overridePendingTransition(0, 0);
                break;

            case ChatWithUs:
                break;

            case HelpArticles:
                break;

            case LegalInformation:
                startActivity(new Intent(context, LegalInfoActivity.class));
                overridePendingTransition(0, 0);
                break;

            case ChangePassword:
                startActivity(new Intent(context, ChangePasswordActivity.class));
                overridePendingTransition(0, 0);
                break;

            case SignOut:
                onLogout();
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

        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                txt_title.setText(TITLES[position]);
                if (position < TITLES.length - 1)
                    showCheckInContextMenu(false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

    private void onExit() {
        dialog = new ShadeAlertDialog(context, getString(R.string.dialog_message_exit),
                getString(R.string.dialog_pButton_Yes), getString(R.string.dialog_nButton_No), new DialogButtonClickListener() {
            @Override
            public void onPositiveButtonClick() {
                dialog.dismissDialog();
                overridePendingTransition(0, 0);
                finish();
            }

            @Override
            public void onNegativeButtonClick() {
                dialog.dismissDialog();
            }
        });
        dialog.prepareDialog();
        dialog.showDialog();
    }

    private void onLogout() {
        dialog = new ShadeAlertDialog(context, getString(R.string.dialog_message_exit),
                getString(R.string.dialog_pButton_Yes), getString(R.string.dialog_logout), new DialogButtonClickListener() {
            @Override
            public void onPositiveButtonClick() {
                dialog.dismissDialog();
                overridePendingTransition(0, 0);
                finish();
            }

            @Override
            public void onNegativeButtonClick() {
                dialog.dismissDialog();
            }
        });
        dialog.prepareDialog();
        dialog.showDialog();
    }

    public void showCheckInContextMenu(boolean needToShow) {
        if (!needToShow) {
            if (layoutMain.getChildCount() == 2) {
                layoutMain.removeViewAt(1);
            }
            return;
        }

        if (layoutMain.getChildCount() >= 2) {
            layoutMain.removeViewAt(1);
        }
        View topBarCheckIn = LayoutInflater.from(context).inflate(R.layout.include_topbar_checkin, null);
        topBarCheckIn.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));

        topBarCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        (topBarCheckIn.findViewById(R.id.topBar_btn_cross)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCheckInContextMenu(false);
            }
        });
        (topBarCheckIn.findViewById(R.id.topBar_btn_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (frgCheckIn != null)
                    frgCheckIn.saveDataCall();
            }
        });
        layoutMain.addView(topBarCheckIn);

    }
}
