package com.example.HealthyCampus.common.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.HealthyCampus.framework.BaseContainerActivity;
import com.example.HealthyCampus.framework.BaseFragment;

/**
 * OK
 */
public class ActivityUtils {

    public static void startActivity(@NonNull Activity activity, @NonNull BaseFragment fragment) {
        Intent intent = new Intent(activity, BaseContainerActivity.class);
        BaseContainerActivity.setFragment(fragment);
        activity.startActivity(intent);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        if (fragmentManager != null && fragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(frameId, fragment);
            transaction.commit();

        }
    }

/*    public static void startActivityByAnimation(@NonNull Activity activity, @NonNull BaseFragment Fragment, @NonNull Bundle options) {
        Intent intent = new Intent(activity, BaseContainerActivity.class);
        BaseContainerActivity.setFragment(Fragment);
        ActivityCompat.startActivity(activity, intent, options);
    }*/


}
