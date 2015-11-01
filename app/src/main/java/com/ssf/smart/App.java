package com.ssf.smart;

import java.util.List;

/**
 * Created by Android on 2015/10/31.
 */
public class App {


    /**
     * status : 1
     * data : [{"Color":"#0168b7","Title":"前厅叫号","Url":"testxc1.ivcvc.com/admin.php?app=screen&sid=3"},{"Color":"#e33000","Title":"前厅菜单","Url":"http://caidan.mifen517.com"},{"Color":"#00bc9e","Title":"后厨提醒","Url":"testxc1.ivcvc.com/admin.php?app=screen&act=h&sid=3"}]
     * msg :
     */

    private int status;
    private String msg;
    /**
     * Color : #0168b7
     * Title : 前厅叫号
     * Url : testxc1.ivcvc.com/admin.php?app=screen&sid=3
     */

    private List<DataEntity> data;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private String Color;
        private String Title;
        private String Url;

        public void setColor(String Color) {
            this.Color = Color;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public void setUrl(String Url) {
            this.Url = Url;
        }

        public String getColor() {
            return Color;
        }

        public String getTitle() {
            return Title;
        }

        public String getUrl() {
            return Url;
        }
    }
}
