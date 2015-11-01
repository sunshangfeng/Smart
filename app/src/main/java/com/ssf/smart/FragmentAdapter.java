package com.ssf.smart;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2015/10/30.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter implements GridFragment.OnFragmentListener {

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    List<Fragment> fragments = new ArrayList<>();
    List<GridBean> dataEntitys;
    int h = 4;
    int page;


    public FragmentAdapter(FragmentManager fm, int v, List<GridBean> dataEntitys) {
        super(fm);
        this.dataEntitys = dataEntitys;
        int datasize = dataEntitys.size();
        h = v * h;
        page = datasize / h + (datasize % h == 0 ? 0 : 1);
        for (int i = 0; i < page; i++) {
            ArrayList<GridBean> list = new ArrayList<>();
            for (int j = 0; j < datasize; j++) {
                if (j / h == i) {
                    list.add(dataEntitys.get(j));
                }
            }
            GridFragment fragment = GridFragment.newInstance(list, i * h);
            fragment.setListener(this);
            fragments.add(fragment);
        }
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void onFragmentInteraction(AdapterView<?> parent, View view, int position, long id) {
        if (onItemClickListener != null)
            onItemClickListener.OnItemClickListener(view, position, id);
    }

    public interface OnItemClickListener {
        void OnItemClickListener(View view, int position, long id);
    }

}
