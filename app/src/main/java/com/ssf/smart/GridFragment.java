package com.ssf.smart;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GridFragment extends Fragment {

    @Bind(R.id.gridview)
    GridView gridview;
    int index;

    private ArrayList<GridBean> list;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public void setListener(OnFragmentListener mListener) {
        this.mListener = mListener;
    }

    private OnFragmentListener mListener;

    public static GridFragment newInstance(ArrayList<GridBean> param1, int index) {
        GridFragment fragment = new GridFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, index);
        fragment.setArguments(args);
        return fragment;
    }

    public GridFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //获取传值参数
            list = (ArrayList<GridBean>) getArguments().getSerializable(ARG_PARAM1);
            index = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridview.setAdapter(new GridAdapter(getActivity(), list));
        gridview.requestFocus();
        gridview.setFocusable(true);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onFragmentInteraction(parent, view, index + position, id);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    public interface OnFragmentListener {
        void onFragmentInteraction(AdapterView<?> parent, View view, int position, long id);
    }

    public class GridAdapter extends BaseAdapter {
        List<GridBean> list;
        Context context;

        public GridAdapter(Context context, List<GridBean> list) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public GridBean getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_layout, null);
            }
            TextView fButton = (TextView) convertView.findViewById(R.id.textView);
            fButton.setText(list.get(position).getTitle());
            String color = list.get(position).getColor();
            boolean is = color.startsWith("#");
            try {
                fButton.setBackgroundColor(Color.parseColor((is ? "" : "#") + list.get(position).getColor()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
        }
    }


}
