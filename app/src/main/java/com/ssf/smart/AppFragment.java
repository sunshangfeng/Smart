package com.ssf.smart;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ssf.smart.Constant.APIKEY;
import static com.ssf.smart.Constant.APPLIST;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.bt_left)
    ImageButton btLeft;
    @Bind(R.id.bt_right)
    ImageButton btRight;
    private PreferenceUtils preference;
    private Login login;
    private RequestQueue requestQueue;
    private ProgressDialog mProgressDialog;
    private Serializable storesID;
    private App app;
    private PreferenceUtils preferenceUtils;

    public AppFragment() {
    }

    public static AppFragment newInstance(String storesID) {
        AppFragment fragment = new AppFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, storesID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //获取传值参数
            storesID = getArguments().getSerializable(ARG_PARAM1);
        }
        preference = new PreferenceUtils(getActivity());
        login = preference.getObject(Constant.PreferenceKey.LOGIN, Login.class);
        requestQueue = Volley.newRequestQueue(getActivity());
        preferenceUtils = new PreferenceUtils(getActivity());
    }


    private void sendHttp() {
        long millis = System.currentTimeMillis();
        String time = String.valueOf(millis).substring(0, 10);
        String md5 = MD5Utils.Md5(APIKEY + time);
        Login.DataEntity dataEntity = login.getData();
        JSONObject params = new JSONObject();
        try {
            params.put("CompanyID", dataEntity.getCompanyID());
            params.put("StoresID", storesID);
            params.put("t", time);
            params.put("m", md5);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(APPLIST, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dissDialog();
                app = JSON.parseObject(response.toString(), App.class);
                if (app.getStatus() == 0) {
                    Toast(app.getMsg());
                } else {
                    showData();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dissDialog();
                Toast("请求出错");
            }
        });
        requestQueue.add(request);
        mProgressDialog = ProgressDialog.show(getActivity(), null, "加载中....");
    }

    private void showData() {
        final ArrayList<GridBean> list = new ArrayList<>();
        for (int i = 0; i < app.getData().size(); i++) {
            GridBean gridBean = new GridBean();
            gridBean.setTitle(app.getData().get(i).getTitle());
            gridBean.setColor(app.getData().get(i).getColor());
            list.add(gridBean);
        }
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getChildFragmentManager(), 2, list);
        viewpager.setAdapter(fragmentAdapter);
        if (fragmentAdapter.getCount() > 1) {
            btLeft.setVisibility(View.VISIBLE);
            btRight.setVisibility(View.VISIBLE);
        }
        fragmentAdapter.setOnItemClickListener(new FragmentAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(View view, int position, long id) {
                String url = app.getData().get(position).getUrl();
                preferenceUtils.put(Constant.PreferenceKey.URL, url);
                MainActivity activity = (MainActivity) getActivity();
                activity.clearBackStack();
                activity.replaceFragment(WebFragment.newInstance(url));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appstores, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title.setText("选择应用");
        if (app == null) {
            sendHttp();
        } else {
            showData();
        }
    }

    void dissDialog() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    void Toast(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick(R.id.bt_left)
    void left() {
        viewpager.setCurrentItem(viewpager.getCurrentItem() - 1);
    }

    @OnClick(R.id.bt_right)
    void right() {
        viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
    }
}
