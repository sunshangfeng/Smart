package com.ssf.smart;

/**
 * Created by Android on 2015/10/30.
 */
public class Login {
    /**
     * status : 1
     * data : {"CompanyName":"人人湘","CompanyID":"1","StoresIDString":"1,2,3","Uid":"1"}
     */

    private int status;

    /**
     * CompanyName : 人人湘
     * CompanyID : 1
     * StoresIDString : 1,2,3
     * Uid : 1
     */

    private DataEntity data;
    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public DataEntity getData() {
        return data;
    }


    @Override
    public String toString() {
        return "Login{" +
                "status=" + status +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataEntity {

        private String CompanyName;
        private String CompanyID;
        private String StoresIDString;
        private String Uid;


        public void setCompanyName(String CompanyName) {
            this.CompanyName = CompanyName;
        }

        public void setCompanyID(String CompanyID) {
            this.CompanyID = CompanyID;
        }

        public void setStoresIDString(String StoresIDString) {
            this.StoresIDString = StoresIDString;
        }

        public void setUid(String Uid) {
            this.Uid = Uid;
        }

        public String getCompanyName() {
            return CompanyName;
        }

        public String getCompanyID() {
            return CompanyID;
        }

        public String getStoresIDString() {
            return StoresIDString;
        }

        public String getUid() {
            return Uid;
        }

        @Override
        public String toString() {
            return "DataEntity{" +
                    "CompanyName='" + CompanyName + '\'' +
                    ", CompanyID='" + CompanyID + '\'' +
                    ", StoresIDString='" + StoresIDString + '\'' +
                    ", Uid='" + Uid + '\'' +
                    '}';
        }
    }


}
