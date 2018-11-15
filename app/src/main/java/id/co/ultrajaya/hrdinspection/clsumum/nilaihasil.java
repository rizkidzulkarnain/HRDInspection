package id.co.ultrajaya.hrdinspection.clsumum;

/**
 * Created by aris on 9/18/2017.
 */

public class nilaihasil {
      String DispMsg = "";
      String ErrMsg = "";
    Boolean Hasil= false;

    public void setDispMsg(String dispMsg) {
        DispMsg = dispMsg;
    }

    public void setErrMsg(String errMsg) {
        ErrMsg = errMsg;
    }

    public void setHasil(Boolean hasil) {
        Hasil = hasil;
    }

    public Boolean getHasil() {
        return Hasil;
    }

    public String getDispMsg() {
        return DispMsg;
    }

    public String getErrMsg() {
        return ErrMsg;
    }
}
