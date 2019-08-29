package cn.endureblaze.kirby.resources.adapter;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class ResPagerAdapter extends PagerAdapter
{
    private List<View> mViewList;
    private List<String> mTitleList;

    public ResPagerAdapter(List<View> mViewList,List<String> mTitleList)
    {
        this.mViewList = mViewList;
        this.mTitleList = mTitleList;
    }

    @Override
    public int getCount()
    {
        return mViewList.size();//页卡数
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return view == object;//官方推荐写法
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        container.addView(mViewList.get(position));//添加页卡
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object)
    {
        container.removeView(mViewList.get(position));//删除页卡
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return mTitleList.get(position);//页卡标题
    }
}