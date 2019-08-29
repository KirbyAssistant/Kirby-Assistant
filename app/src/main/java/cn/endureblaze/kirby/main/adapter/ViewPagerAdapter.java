package cn.endureblaze.kirby.main.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import java.lang.reflect.Method;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private FragmentManager fm;
    private List<Fragment> fragments;
    private List<String> page_title;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments,List<String> page_title) {
        super(fm);
        this.fm=fm;
        this.fragments=fragments;
        this.page_title=page_title;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if (position == 3||position == 2)
            removeFragment(container,position);
        return super.instantiateItem(container, position);
    }

    private void removeFragment(ViewGroup container,int index) {
        String tag = getFragmentTag(container.getId(), index);
        Fragment fragment = fm.findFragmentByTag(tag);
        if (fragment == null)
            return;
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(fragment);
        ft.commit();
        ft = null;
        fm.executePendingTransactions();
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

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return page_title.get(position);
    }
    //禁止销毁view达到复用
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {}
    //获取碎片的tag
    private String getFragmentTag(int viewId, int index) {
        try {
            Class<FragmentPagerAdapter> cls = FragmentPagerAdapter.class;
            Class<?>[] parameterTypes = { int.class, long.class };
            Method method = cls.getDeclaredMethod("makeFragmentName",
                    parameterTypes);
            method.setAccessible(true);
            return (String) method.invoke(this, viewId, index);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}