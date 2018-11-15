package id.co.ultrajaya.hrdinspection.webservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import id.co.ultrajaya.hrdinspection.clsumum.Config;

/**
 * Created by aris on 6/15/2017.
 */

public class Exec_sql {
    private Object resultsRequestSOAP = null;
    private static final String SOAP_ACTION = "http://tempuri.org/SP_GET_MV";
    private static final String METHOD_NAME = "SP_GET_MV";
    private static final String NAMESPACE = "http://tempuri.org/";
    private String _at1;
    private String _at2;
    private String _at3;
    private String _at4;
    private String _at5;
    private String _at6;
    private String _at7;
    private String _at8;
    private String _at9;
    private String _at10;
    private String _at11;
    private String _at12;

    private String _at13;
    private String _at14;
    private String _at15;
    private String _at16;
    private String _at17;
    private String _at18;
    private String _at19;
    private String _at20;
    private String _at21;
    private String _at22;
    private String _at23;
    private String _at24;
    public String get_at1()
    {
        return _at1;
    }
    public String get_at2()
    {
        return _at2;
    }
    public String get_at3()
    {
        return _at3;
    }
    public String get_at4()
    {
        return _at4;
    }
    public String get_at5()
    {
        return _at5;
    }
    public String get_at6()
    {
        return _at6;
    }
    public String get_at7()
    {
        return _at7;
    }
    public String get_at8()
    {
        return _at8;
    }
    public String get_at9()
    {
        return _at9;
    }
    public String get_at10()
    {
        return _at10;
    }
    public String get_at11()
    {
        return _at11;
    }
    public String get_at12()
    {
        return _at12;
    }

    public String get_at13() {
        return _at13;
    }

    public String get_at14() {
        return _at14;
    }

    public String get_at15() {
        return _at15;
    }

    public String get_at16() {
        return _at16;
    }

    public String get_at17() {
        return _at17;
    }

    public String get_at18() {
        return _at18;
    }

    public String get_at19() {
        return _at19;
    }

    public String get_at20() {
        return _at20;
    }

    public String get_at21() {
        return _at21;
    }

    public String get_at22() {
        return _at22;
    }

    public String get_at23() {
        return _at23;
    }

    public String get_at24() {
        return _at24;
    }

    private boolean get_MstProdResult;

    public void setGet_MstProdResult( boolean get_MstProdResult ) {
        this.get_MstProdResult = get_MstProdResult;
    }

    public boolean getGet_MstProdResult() {
        return get_MstProdResult;
    }

    private String iHasil;

    public void setIHasil( String iHasil ) {
        this.iHasil = iHasil;
    }

    public String getIHasil() {
        return iHasil;
    }

    private String iErrMsg;

    public void setIErrMsg( String iErrMsg ) {
        this.iErrMsg = iErrMsg;
    }

    public String getIErrMsg() {
        return iErrMsg;
    }
    //ikey As String, inamasp As String, idict As String, iothval As String, ierrmsg As String
    public void get_sp(String iAccount, String isql ) {
        try {

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

//            Dim amv As String = Chr(254)
//            Dim alistparam() As String = {"@KEY", "@RECORD", "@SID", "@ACTION", "@OTH_VAL", "@ERR_MSG", "@DISP_MSG"}
//            Dim alistnilai() As String = {ikey, amv, "1", "0", iothval, "", ""}
//            Dim alistarah() As String = {"inout", "in", "in", "in", "in", "out", "out"}
//            aval = mymidle.SP_GET_MV("DISTUJ", alistparam, alistnilai, alistarah, inamasp, idict, ierrmsg, "test")
//            Return aval
            String amv;

            String alistparam = "@SQL";
            String alistnilai =  isql;
            String alistarah =  "in" ;
            String aerrmsg = "";
            request.addProperty("iServer", iAccount);
            request.addProperty("iListParam", alistparam);
            request.addProperty("iListNilai", alistnilai);
            request.addProperty("iarah", alistarah);
            request.addProperty("iSptrans", "[IT].[HELPSELECT]");
            request.addProperty("iDict", "other");
            request.addProperty("iErrmsg", aerrmsg);
            request.addProperty("iKet", "test");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);
            int TimeOut=30000;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Config.Companion.getUrl(),TimeOut);
            try
            {
                System.setProperty("http.keepAlive", "false");
                androidHttpTransport.call(SOAP_ACTION, envelope);
                resultsRequestSOAP =  envelope.getResponse();
                SoapObject response = (SoapObject) envelope.bodyIn;
                String aNilai = response.getPropertyAsString(0).toString();
                aerrmsg = response.getPropertyAsString(1).toString();
                if (aerrmsg.equals( "anyType{}")) {
                    setIHasil(aNilai);
                }
                else {
                    setIErrMsg(aerrmsg);
                }
            }
            catch (Exception aE)
            {
                setIErrMsg(aE.toString());
            }
        } catch (Exception e) {
            setIErrMsg(e.toString());
        }
    }
    public void SetMv_dari_Hasil()
    {
        String[] adata = iHasil.split(String.valueOf(Config.Companion.getAM()));
        _at1 = adata[0];
        _at2 = adata[1];
        _at3 = adata[2];
        _at4 = adata[3];
        _at5 = adata[4];
        _at6 = adata[5];
        _at7 = adata[6];
        _at8 = adata[7];
        _at9 = adata[8];
        _at10 = adata[9];
        _at11 = adata[10];
        _at12 = adata[11];
        _at13 = adata[12];
        _at14 = adata[13];
        _at15 = adata[14];
        _at16 = adata[15];
        _at17 = adata[16];
        _at18 = adata[17];
        _at19 = adata[18];
        _at20 = adata[19];
        _at21 = adata[20];
        _at22 = adata[21];
        _at23 = adata[22];
        _at24 = adata[23];

    }
}
