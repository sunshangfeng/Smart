package com.ssf.smart;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ssf.smart.Constant.APIKEY;
import static com.ssf.smart.Constant.LOGIN;
import static com.ssf.smart.Constant.PreferenceKey;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    @Bind(R.id.username)
    EditText username;
    @Bind(R.id.password)
    EditText password;
    private RequestQueue requestQueue;

    private ProgressDialog mProgressDialog;

    private PreferenceUtils preferenceUtils;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getActivity());
        preferenceUtils = new PreferenceUtils(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.submit)
    void submit() {
        if (isNull()) return;
        sendHttp();
    }

    private boolean isNull() {
        String name = username.getText().toString();
        String pass = password.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast("用户名不能为空");
            return true;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast("密码不能为空");
            return true;
        }
        return false;
    }

    /**
     * 发送请求
     */
    private void sendHttp() {
        Map<String, String> map = new HashMap<>();
        long millis = System.currentTimeMillis();
        String time = String.valueOf(millis).substring(0, 10);
        String md5 = MD5Utils.Md5(APIKEY + time);
        map.put("u", username.getText().toString());
        map.put("p", password.getText().toString());
        map.put("t", time);
        map.put("m", md5);
        JSONObject params = new JSONObject(map);
        Log.e(LoginFragment.class.getSimpleName(),LOGIN);
        JsonObjectRequest request = new JsonObjectRequest(LOGIN, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dissDialog();
                Login login = JSON.parseObject(response.toString(), Login.class);
                if (login.getStatus() == 0) {
                    Toast(login.getMsg());
                } else {
                    preferenceUtils.putObject(PreferenceKey.LOGIN, login);
                    ((MainActivity) getActivity()).replaceFragment(AppStoresFragment.newInstance());
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
        mProgressDialog = ProgressDialog.show(getActivity(), null, "正在登录中....");
    }

    void dissDialog() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    void Toast(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }


}
