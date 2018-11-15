package id.co.ultrajaya.hrdinspection.clsumum;

import id.co.ultrajaya.hrdinspection.webservice.GET_SP_MV_DET;
import id.co.ultrajaya.hrdinspection.webservice.Get_SP_MV;

/**
 * Created by it-aris on 01/02/2017.
 */

public class Cuser {
    private String _IdUser = "";
    private String _Passwd;
    private String _NamaUser;
    private String _ErrMsg = "";

    public void set_IdUser(String ival) {
        _IdUser = ival;
    }

    public String get_IdUser() {
        return _IdUser;
    }

    public void set_NamaUser(String _NamaUser) {
        this._NamaUser = _NamaUser;
    }

    public String get_NamaUser() {
        return _NamaUser;
    }

    public void set_Passwd(String _Passwd) {
        this._Passwd = _Passwd;
    }

    public String get_Passwd() {
        return _Passwd;
    }

    public void set_ErrMsg(String _ErrMsg) {
        this._ErrMsg = _ErrMsg;
    }

    public String get_ErrMsg() {
        return _ErrMsg;
    }

    public nilaihasil GetServerDbDanVersi(String kodeApp) {
        nilaihasil aval = new nilaihasil();
        aval.setHasil(false);

        String aerrmsg = "";
        String adispmsg = "";
        String amv;
        String aData = "";

        try {
            Get_SP_MV aspget = new Get_SP_MV();
            amv = Character.toString(Config.Companion.getAM());

            aspget.get_sp("MSAFE", "1", kodeApp, amv, "[dbo].[GETVERSI]", "OTH", "");
            aerrmsg = aspget.getIErrMsg();
            if (aerrmsg == null) {
                aData = aspget.getIHasil();
                mvitem amvitem = new mvitem();
                amvitem.SetData(aData);
                Config.Companion.setVERSION(amvitem.GetData(2));
                aval.setHasil(true);
            } else {
                aval.setErrMsg(aspget.getIErrMsg());
            }
        } catch (Exception ex) {
            aval.setErrMsg(ex.toString());
        }
        return aval;
    }
}

