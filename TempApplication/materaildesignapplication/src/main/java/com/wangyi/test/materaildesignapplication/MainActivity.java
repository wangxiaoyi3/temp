package com.wangyi.test.materaildesignapplication;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.wangyi.test.materaildesignapplication.fragment.ContactFragment;
import com.wangyi.test.materaildesignapplication.fragment.FocusFragment;
import com.wangyi.test.materaildesignapplication.fragment.MessageFragment;
import com.wangyi.test.materaildesignapplication.fragment.ZoneFragment;

import java.lang.reflect.Field;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Toolbar toolbar;
    private NavigationView nav_view;
    private DrawerLayout drawer_layout;
    private CircleImageView head_imageView;
    private FragmentTabHost tabhost;
    //定义一个布局
    private LayoutInflater layoutInflater;
    //定义数组来存放Fragment界面
    private Class fragment[] = {MessageFragment.class, ContactFragment.class, FocusFragment.class, ZoneFragment.class};
    //定义数组来存放导航图标
    private int tabImage[] = {R.drawable.message_color_change, R.drawable.contact_color_change, R.drawable.focus_color_change, R.drawable.zone_color_change};
    //tab选项卡的文字
    private String tabText[] = {"消息", "联系人", "看点", "动态"};

    private ColorStateList colorStateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nav_view = (NavigationView) findViewById(R.id.nav_view);
        head_imageView = (CircleImageView) findViewById(R.id.head_imageView);
        head_imageView.setOnClickListener(this);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        //用ActionBarDrawerToggle实现监听
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                //设置content跟随滑动
                View content = drawer_layout.getChildAt(0);
                int offset = (int) (drawerView.getWidth() * slideOffset);
                content.setTranslationX(offset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        };
        //设置三条杠
//        toggle.syncState();
        drawer_layout.addDrawerListener(toggle);
//
//        Resources resources = this.getResources();
//        Drawable drawable = resources.getDrawable(R.mipmap.ic_launcher, null);
//        CircleDrawable circleDrawable = new CircleDrawable(drawable, this, 48);

        //setNavigationIcon法必须在DrawerLayout和ToolBar绑定之后调用才有效果
//        toolbar.setNavigationIcon(circleDrawable);
//        toolbar.setNavigationIcon(R.drawable.ic_qq);
        nav_view.setNavigationItemSelectedListener(this);
        //设置侧滑范围为屏幕的十分之六
        setDrawerLeftEdgeSize(this, drawer_layout, 0.6f);

        //实例化布局对象
        layoutInflater = LayoutInflater.from(this);
        //实例化FragmentTabHost对象，得到tabHost
        tabhost = (FragmentTabHost) findViewById(R.id.tabhost);
        tabhost.setup(this,getSupportFragmentManager(), R.id.app_tabcontent);
        //去掉tab之间的分割线
        tabhost.getTabWidget().setDividerDrawable(android.R.color.transparent);
        //得到fragment的个数
        int count = fragment.length;
        for (int i=0; i<count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = tabhost.newTabSpec(tabText[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            tabhost.addTab(tabSpec, fragment[i], null);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_imageView:
                drawer_layout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        return true;
    }

    /**
     * 滑动范围控制
     *
     * @param activity
     * @param drawerLayout
     * @param displayWidthPercentage 占全屏的份额0~1
     */
    private void setDrawerLeftEdgeSize(Activity activity, DrawerLayout drawerLayout, float displayWidthPercentage) {
        if (activity == null || drawerLayout == null) return;
        try {
            // 找到 ViewDragHelper 并设置 Accessible 为true
            Field leftDraggerField =
                    drawerLayout.getClass().getDeclaredField("mLeftDragger");//Right
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);

            // 找到 edgeSizeField 并设置 Accessible 为true
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);

            // 设置新的边缘大小
            Point displaySize = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(displaySize);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (displaySize.x *
                    displayWidthPercentage)));
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index){
        View view = layoutInflater.inflate(R.layout.tab_item, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon_iv);
        imageView.setImageResource(tabImage[index]);

        TextView textView = (TextView) view.findViewById(R.id.tab_text_tv);
        textView.setText(tabText[index]);
        colorStateList = getResources().getColorStateList(R.color.text_color_change, null);
        if (colorStateList != null) {
            textView.setTextColor(colorStateList);
        }
        return view;
    }
}
