package com.example.HealthyCampus.common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.utils.DialogUtil;
import com.example.HealthyCampus.module.Find.Recipes.Customization.Fragment.CustomizationFragment;
import com.example.HealthyCampus.module.Find.Recipes.Recommend.RecommendFragment;

public class RecipesPagerAdapter extends FragmentPagerAdapter {

    private final String[] TITLES;
    private Fragment[] fragments;
    private Context context;

    public RecipesPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        TITLES = context.getResources().getStringArray(R.array.recipes_sections);
        fragments = new Fragment[TITLES.length];
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null) {
            switch (position) {
                case 0:
                    fragments[position] = CustomizationFragment.getInstance();
                    break;
                case 1:
                    fragments[position] = RecommendFragment.getInstance();
                    break;
                default:
                    break;
            }
        }
//        Log.e("RecipesPagerA" + "123456", "12345");
//        Log.e("RecipesPagerA" + "123456", "fragments[position] "+fragments[position] );
        return fragments[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    public void refreshUI() {
//        ((DiagnosisListFragment) fragments[1]).refreshUI();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
    }
}
