package dao.cn.com.talkvip.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import dao.cn.com.talkvip.R;
import dao.cn.com.talkvip.adapter.TabFragmentAdapter;
import dao.cn.com.talkvip.view.fragment.TabFragment;


public class MainActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {

    private Toolbar toolbar;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private DrawerLayout drawerLayout;

    private NavigationView navigationView;


    @Override
    protected void initHead() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
//        final TextInputLayout inputLayout = findView(R.id.textInput);
//        inputLayout.setHint("请输入姓名:");
//
//        EditText editText = inputLayout.getEditText();
//        editText.setHintTextColor(getResources().getColor(android.R.color.holo_blue_bright));
//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.length() > 4) {
//                    inputLayout.setErrorEnabled(true);
//                    inputLayout.setError("姓名长度不能超过4个");
//                } else {
//                    inputLayout.setErrorEnabled(false);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });




        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.nav_account:
                       startActivity(new Intent(MainActivity.this, AccountActivity.class));
                        break;
                    case R.id.nav_qustion:
                        startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
                        break;
                    case R.id.nav_setting:
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        break;
                }
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });


        initToolbar();
        initTabLayout();


    }

    private void initTabLayout() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        List<String> tabList = new ArrayList<>();
        tabList.add("未拨打");
        tabList.add("带跟进");
        tabList.add("未接通");
        tabList.add("无意愿");
        tabList.add("已提取");
        tabList.add("重点");

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置tab模式，当前为系统默认模式
        //此处代码设置无效，不知道为啥？？？xml属性是可以的
//        tabLayout.setTabTextColors(android.R.color.white, android.R.color.holo_red_dark);//设置TabLayout两种状态
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(0)));//添加tab选项卡
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(3)));
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(4)));
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(5)));



        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < tabList.size(); i++) {
            Fragment f1 = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putString("content", tabList.get(i));
            f1.setArguments(bundle);
            fragmentList.add(f1);
        }

        TabFragmentAdapter fragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragmentList, tabList);
        viewPager.setAdapter(fragmentAdapter);//给ViewPager设置适配器
        tabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。
        tabLayout.setTabsFromPagerAdapter(fragmentAdapter);//给Tabs设置适配器

    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        toolbar.setNavigationIcon(R.mipmap.ic_list_black_24dp);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOnMenuItemClickListener(this);
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
