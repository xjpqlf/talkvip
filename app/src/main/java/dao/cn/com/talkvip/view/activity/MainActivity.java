package dao.cn.com.talkvip.view.activity;

import android.content.Intent;
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
import dao.cn.com.talkvip.utils.ToastUtil;
import dao.cn.com.talkvip.view.fragment.ExtractedFragment;
import dao.cn.com.talkvip.view.fragment.FollowUpFragment;
import dao.cn.com.talkvip.view.fragment.ImportFragment;
import dao.cn.com.talkvip.view.fragment.NoDesireFragment;
import dao.cn.com.talkvip.view.fragment.NotCallFragments;
import dao.cn.com.talkvip.view.fragment.NotThroughFragment;


public class MainActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {

    private Toolbar toolbar;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private DrawerLayout drawerLayout;
    private long exitAppTimeCount;
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
       // View decorView = getWindow().getDecorView();
     /*   int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);*/
    drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);

        navigationView.getMenu().getItem(0).setChecked(true);


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
             //   drawerLayout.closeDrawers();
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
        tabList.add("待跟进");
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
      /*  for (int i = 0; i < tabList.size(); i++) {
            Fragment f1 = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putString("content", tabList.get(i));
            f1.setArguments(bundle);
            fragmentList.add(f1);
        }*/
      Fragment  f1=new NotCallFragments();
       Fragment  f2=new FollowUpFragment();
      Fragment  f3=new NotThroughFragment();
     Fragment  f4=new NoDesireFragment();
      Fragment  f5=new ExtractedFragment();
        Fragment  f6=new ImportFragment();
        fragmentList.add(f1);
        fragmentList.add(f2);
        fragmentList.add(f3);
        fragmentList.add(f4);
        fragmentList.add(f5);
        fragmentList.add(f6);



        TabFragmentAdapter fragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragmentList, tabList);
        viewPager.setAdapter(fragmentAdapter);//给ViewPager设置适配器
        viewPager.setOffscreenPageLimit(5);
        tabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。
        tabLayout.setTabsFromPagerAdapter(fragmentAdapter);//给Tabs设置适配器

    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        toolbar.setNavigationIcon(R.mipmap.muen);
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

    @Override
    public void onBackPressed() {

                if (System.currentTimeMillis() - exitAppTimeCount > 2000) {
                     ToastUtil.show("再按一次退出程序");
                      exitAppTimeCount = System.currentTimeMillis();
                } else {
                    super.onBackPressed();

                }


    }




}
