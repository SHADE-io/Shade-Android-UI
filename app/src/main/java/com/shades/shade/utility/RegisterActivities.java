package com.shades.shade.utility;

import java.util.ArrayList;

import android.app.Activity;

/**
 * This class is use for storing Activity in to a stack;
 */
public class RegisterActivities {
    /**
     * This array stores activity
     */
    public static ArrayList<Activity> activityStore = new ArrayList<Activity>();

    /**
     * This method stores Activity.
     *
     * @param context
     * @return void
     */
    public static void registerActivity(Activity context) {
        activityStore.add(context);
    }

    /**
     * This method returns all Activity classes store in activityStore
     *
     * @return ArrayList<Activity>
     */
    public static ArrayList<Activity> getAllActivities() {

        return activityStore;
    }

    /**
     * This method returns one Activity classes from top of the activityStore
     *
     * @return Activity
     */
    public static Activity getActivity() {
        if (!activityStore.isEmpty()) {
            return activityStore.get(activityStore.size() - 1);
        } else {
            return null;
        }

    }

    /**
     * This method remove all Activity classes from activityStore
     *
     * @return void
     */
    public static void removeAllActivities() {

        for (int i = 0; i < activityStore.size(); i++) {
            try {
                if (activityStore.get(i) != null) {
                    (activityStore.get(i)).overridePendingTransition(0, 0);
                    (activityStore.get(i)).finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.runFinalizersOnExit(true);
        activityStore.clear();
    }

    /**
     * This method finish one Activity class from top of the activityStore.
     *
     * @return void
     */
    public static void finishActivity() {
        try {
            if (activityStore.get(activityStore.size() - 1) != null) {
                activityStore.get(activityStore.size() - 1).overridePendingTransition(0, 0);
                activityStore.get(activityStore.size() - 1).finish();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method finish one Activity class from top of the activityStore.
     *
     * @return void
     */
    public static void removeActivity() {
        try {
            if (activityStore.get(activityStore.size() - 1) != null) {
                activityStore.remove(activityStore.size() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
