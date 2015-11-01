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

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ssf.smart.Constant.APIKEY;
import static com.ssf.smart.Constant.APPSTORES;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppStoresFragment extends Fragment {


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
    private AppStores stores;
    private FragmentAdapter fragmentAdapter;

    public AppStoresFragment() {
    }

    public static AppStoresFragment newInstance() {
        AppStoresFragment fragment = new AppStoresFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference = new PreferenceUtils(getActivity());
        login = preference.getObject(Constant.PreferenceKey.LOGIN, Login.class);
        requestQueue = Volley.newRequestQueue(getActivity());
    }


    private void sendHttp() {
        long millis = System.currentTimeMillis();
        String time = String.valueOf(millis).substring(0, 10);
        String md5 = MD5Utils.Md5(APIKEY + time);
        Login.DataEntity dataEntity = login.getData();
        JSONObject params = new JSONObject();
        try {
            params.put("CompanyID", dataEntity.getCompanyID());
            params.put("StoresIDString", dataEntity.getStoresIDString());
            params.put("Uid", dataEntity.getUid());
            params.put("t", time);
            params.put("m", md5);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(APPSTORES, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dissDialog();
                stores = JSON.parseObject(response.toString(), AppStores.class);
                if (stores.getStatus() == 0) {
                    Toast(stores.getMsg());
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
        ArrayList<GridBean> list = new ArrayList<>();
        for (int i = 0; i < stores.getData().size(); i++) {
            GridBean gridBean = new GridBean();
            gridBean.setTitle(stores.getData().get(i).getStoresName());
            gridBean.setColor(stores.getData().get(i).getColor());
            list.add(gridBean);
        }
        fragmentAdapter = new FragmentAdapter(getChildFragmentManager(), 2, list);
        viewpager.setAdapter(fragmentAdapter);
        if (fragmentAdapter.getCount() > 1) {
            btLeft.setVisibility(View.VISIBLE);
            btRight.setVisibility(View.VISIBLE);
        }
        fragmentAdapter.setOnItemClickListener(new FragmentAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(View view, int position, long id) {
                ((MainActivity) getActivity()).replaceFragment(AppFragment.newInstance(stores.getData().get(position).getStoresID()));
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
        title.setText("选择" + login.getData().getCompanyName() + "分店");
        if (stores != null) {
            showData();
        } else {
            sendHttp();
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
