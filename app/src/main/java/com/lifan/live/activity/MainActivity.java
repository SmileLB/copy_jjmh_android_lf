package com.lifan.live.activity;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gyf.barlibrary.ImmersionBar;
import com.lifan.base.router.RouterMap;
import com.lifan.base.ui.BaseActivity;
import com.lifan.comic.adapter.MyPagerAdapter;
import com.lifan.comic.common.constants.Constants;
import com.lifan.comic.fragment.FiveFragment;
import com.lifan.comic.fragment.FourFragment;
import com.lifan.comic.fragment.OneFragment;
import com.lifan.comic.fragment.ThreeFragment;
import com.lifan.comic.fragment.TwoFragment;
import com.lifan.comic.util.enentbus.EventBusHelper;
import com.lifan.live.R;
import com.lifan.live.present.MainContract;
import com.lifan.live.present.MainPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import q.rorbin.badgeview.QBadgeView;

@Route(path = RouterMap.COMIC_MAIN_ACTIVITY)
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.IMainView {

    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.vp_home_pager)
    ViewPager mViewPager;
    @BindView(R.id.bv_home_navigation)
    BottomNavigationView mBottomNavigationView;

    private String[] mTabLables;
    private int[] mTabIcons;

    private int currentIndex = -1;
    private MyPagerAdapter adapter;

    @Override
    protected void initData(Bundle savedInstanceState) {
        initBottomNavigationView(savedInstanceState);
        initViewPager();
    }

    private void initViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new OneFragment());
        fragments.add(new TwoFragment());
        fragments.add(new ThreeFragment());
        fragments.add(new FourFragment());
        fragments.add(new FiveFragment());
        adapter = new MyPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(fragments.size());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                //将滑动到的页面对应的 menu 设置为选中状态
//                if(mBottomNavigationView.getC){
//
//                }
                mBottomNavigationView.getMenu().getItem(i).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initBottomNavigationView(Bundle savedInstanceState) {
        // 取消BottomNavigationView底部tab图片默认着色
        mBottomNavigationView.setItemIconTintList(null);
        //去除选中状态图片放大的效果
        mBottomNavigationView.setItemTextAppearanceActive(R.style.comic_bottom_selected_text);
        mBottomNavigationView.setItemTextAppearanceInactive(R.style.comic_bottom_normal_text);

        mTabLables = new String[]{"推荐", "分类", "发现", "书架", "我的"};
        mTabIcons = new int[]{R.drawable.recommend, R.drawable.find, R.drawable.search, R.drawable.bookshelf, R.drawable.mine};
        Menu menu = mBottomNavigationView.getMenu();
        for (int i = 0; i < mTabLables.length; i++) {
            menu.add(0, i, i, mTabLables[i]);
            MenuItem item = menu.findItem(i);
            item.setIcon(mTabIcons[i]);
        }
        int index = savedInstanceState == null ? 0 : savedInstanceState.getInt(Constants.IntentKey.INDEX, 0);

        mBottomNavigationView.getMenu().getItem(index).setChecked(true);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case 0:
                        mViewPager.setCurrentItem(0);
                        return true;
                    case 1:
                        mViewPager.setCurrentItem(1);
                        return true;
                    case 2:
                        mViewPager.setCurrentItem(2);
                        return true;
                    case 3:
                        mViewPager.setCurrentItem(3);
                        return true;
                    case 4:
                        mViewPager.setCurrentItem(4);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainPresenter setPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(MainActivity.this).init();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(Constants.IntentKey.INDEX, currentIndex);
        super.onSaveInstanceState(outState);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(EventBusHelper helper) {
        // 占位，只为了能生成 MyEventBusIndex 索引类
        // 如果项目中已经有用到 @Subscribe 去注解方法，这个方法可以直接删除
    }

}
