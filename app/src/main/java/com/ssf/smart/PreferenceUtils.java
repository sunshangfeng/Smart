package com.ssf.smart;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.preference.PreferenceManager;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author cj
 */
public class PreferenceUtils {
    SharedPreferences m_prefs = null;

    public PreferenceUtils(Context context) {
        this(context, null);
    }

    public PreferenceUtils(Context context, String szPreference) {
        if (szPreference == null)
            m_prefs = PreferenceManager.getDefaultSharedPreferences(context);
        else
            m_prefs = context.getSharedPreferences(szPreference, Context.MODE_PRIVATE);
    }

    public boolean get(String key, boolean defValue) {
        return m_prefs.getBoolean(key, defValue);
    }

    public int get(String key, int defValue) {
        return m_prefs.getInt(key, defValue);
    }

    public long get(String key, long defValue) {
        return m_prefs.getLong(key, defValue);
    }

    public float get(String key, float defValue) {
        return m_prefs.getFloat(key, defValue);
    }

    public String get(String key, String defValue) {
        return m_prefs.getString(key, defValue);
    }

    public Map<String, ?> getAll() {
        return m_prefs.getAll();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Set<String> getStringSet(String key, Set<String> defValues) {
        return m_prefs.getStringSet(key, defValues);
    }

    public boolean contains(String key) {
        return m_prefs.contains(key);
    }

    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        m_prefs.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        m_prefs.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public Object getValue(String key, Object defValue) {
        if (m_prefs == null || key == null)
            throw new IllegalStateException("SharedPreference is not initialized.");

        if (defValue instanceof Boolean) {
            return m_prefs.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Long) {
            return m_prefs.getLong(key, (Long) defValue);
        } else if (defValue instanceof Float) {
            return m_prefs.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Integer) {
            return m_prefs.getInt(key, (Integer) defValue);
        } else {
            return m_prefs.getString(key, (String) defValue);
        }
    }

    public void put(String key, Object value) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put(key, value);
        put(values);
    }

    public void put(HashMap<String, Object> values) {
        if (values == null || m_prefs == null || values.isEmpty())
            return;

        Editor editor = m_prefs.edit();

        Iterator<String> keyIterator = values.keySet().iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            Object value = values.get(key);

            if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            } else if (value instanceof Float) {
                editor.putFloat(key, (Float) value);
            } else if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            }
        }

        if (!editor.commit())
            throw new IllegalArgumentException("saving failed");
    }

    public void remove(String key) {
        Editor editor = m_prefs.edit();
        editor.remove(key);
        editor.commit();
    }

    public void clear() {
        Editor editor = m_prefs.edit();
        editor.clear();
        editor.commit();
    }

    class prefWriter implements Runnable {
        String m_szKey = null;
        String m_szValue = null;

        public prefWriter(String szKey, String szValue) {
            m_szKey = szKey;
            m_szValue = szValue;
        }

        @Override
        public void run() {
            Editor editor = m_prefs.edit();
            try {
                if (m_szKey != null && m_szValue != null)
                    editor.putString(m_szKey, m_szValue);
            } catch (Exception e) {
            }
            editor.commit();
        }
    }

    //----------------------------------------------------------------------------------------------

    /**
     * 根据key自动把实体转换成String存入
     * @param key
     * @param object
     */
    public void putObject(String key, Object object) {
        String json = JSON.toJSONString(object);
        put(key, json);
    }

    /**
     * 根据key自动获取实体
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getObject(String key, Class<T> clazz) {
        String object = m_prefs.getString(key, "");
        if ("".equals(object))
            return null;
        return JSON.parseObject(object, clazz);
    }

}
