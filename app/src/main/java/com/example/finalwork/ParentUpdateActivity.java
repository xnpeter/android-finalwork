package com.example.finalwork;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;


import com.example.finalwork.fragment.AddExpendFragment;
import com.example.finalwork.fragment.AddIncomeFragment;
import com.example.finalwork.fragment.UpdateExpendFragment;
import com.example.finalwork.fragment.UpdateIncomeFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ParentUpdateActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private UpdateIncomeFragment updateIncomeFragment;
    private UpdateExpendFragment updateExpendFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_update);

        mViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.inout_tab);

        updateIncomeFragment = new UpdateIncomeFragment();
        updateExpendFragment = new UpdateExpendFragment();

        mTabLayout.setupWithViewPager(mViewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),0);
        viewPagerAdapter.addFragment(updateIncomeFragment,"收入");
        viewPagerAdapter.addFragment(updateExpendFragment,"支出");
        mViewPager.setAdapter(viewPagerAdapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }
}