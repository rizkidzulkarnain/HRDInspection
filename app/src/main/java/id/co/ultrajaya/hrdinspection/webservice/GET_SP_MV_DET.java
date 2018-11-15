package id.co.ultrajaya.hrdinspection.webservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import id.co.ultrajaya.hrdinspection.clsumum.Config;

public class GET_SP_MV_DET {

    private Object resultsRequestSOAP = null;
    private static final String SOAP_ACTION = "http://tempuri.org/SP_GET_MV_DET";
    private static final String METHOD_NAME = "SP_GET_MV_DET";
    private static final String NAMESPACE = "http://tempuri.org/";

    private boolean get_MstProdResult;

    public void setGet_MstProdResult( boolean get_MstProdResult ) {
        this.get_MstProdResult = get_MstProdResult;
    }

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
    public void get_sp(String iAccount, String iSid, String ikey, String iMvitem, String inamasp, String idict,
                       String iothval  ) {
        try {

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

//            Dim amv As String = Chr(254)
//            Dim alistparam() As String = {"@KEY", "@RECORD", "@SID", "@ACTION", "@OTH_VAL", "@ERR_MSG", "@DISP_MSG"}
//            Dim alistnilai() As String = {ikey, amv, "1", "0", iothval, "", ""}
//            Dim alistarah() As String = {"inout", "in", "in", "in", "in", "out", "out"}
//            aval = mymidle.SP_GET_MV("DISTUJ", alistparam, alistnilai, alistarah, inamasp, idict, ierrmsg, "test")
//            Return aval
            String amv;
            amv =iMvitem ;
            String alistparam = "@KEY|@RECORD|@SID|@ACTION|@OTH_VAL|@ERR_MSG|@DISP_MSG";
            String alistnilai =  ikey + "|" + amv + "|" + iSid + "|" + "0" + "|" + iothval + "|" + "" + "|" + "";
            String alistarah =  "inout" + "|" + "in" + "|" + "in" + "|" + "in" + "|" + "in" + "|" + "out" + "|" + "out";
            String aerrmsg = "";
            request.addProperty("iServer", iAccount);
            request.addProperty("iListParam", alistparam);
            request.addProperty("iListNilai", alistnilai);
            request.addProperty("iarah", alistarah);
            request.addProperty("iSptrans", inamasp);
            request.addProperty("iDict", idict);
            request.addProperty("iErrmsg", aerrmsg);
            request.addProperty("iKet", "test");
            request.addProperty("at1", "");
            request.addProperty("at2", "");
            request.addProperty("at3", "");
            request.addProperty("at4", "");
            request.addProperty("at5", "");
            request.addProperty("at6", "");
            request.addProperty("at7", "");
            request.addProperty("at8", "");
            request.addProperty("at9", "");
            request.addProperty("at10", "");
            request.addProperty("at11", "");
            request.addProperty("at12", "");
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);
            int TimeOut=30000;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Config.Companion.getUrl(),TimeOut);
            try
            {

                androidHttpTransport.call(SOAP_ACTION, envelope);
                resultsRequestSOAP =  envelope.getResponse();
                SoapObject response = (SoapObject) envelope.bodyIn;
                String aNilai = response.getPropertyAsString(0).toString();
                aerrmsg = response.getPropertyAsString(1).toString();
                _at1 =  response.getPropertyAsString(2).toString();
                if (aerrmsg.equals( "anyType{}")){

                    _at2 =  response.getPropertyAsString(3).toString();
                    _at3 =  response.getPropertyAsString(4).toString();
                    _at4 =  response.getPropertyAsString(5).toString();
                    _at5 =  response.getPropertyAsString(6).toString();
                    _at6 =  response.getPropertyAsString(7).toString();
                    _at7 =  response.getPropertyAsString(8).toString();
                    _at8 =  response.getPropertyAsString(9).toString();
                    _at9 =  response.getPropertyAsString(10).toString();
                    _at10 =  response.getPropertyAsString(11).toString();
                    _at11 =  response.getPropertyAsString(12).toString();
                    _at12 =  response.getPropertyAsString(13).toString();
                }
                else
                {
                    setIErrMsg(aerrmsg);
                }

//                Vector a = new Vector();
//                <SP_GET_MVResponse xmlns="http://tempuri.org/">
//                <SP_GET_MVResult>string</SP_GET_MVResult>
//                <iListNilai>
//                <string>string</string>
//                <string>string</string>
//                </iListNilai>
//                <iErrmsg>string</iErrmsg>
//                </SP_GET_MVResponse>
//
//
//                    a = (Vector) resultsRequestSOAP;
//                Object[] obj = new Object[a.size()];
//                a.copyInto(obj);
//            setGet_MstProdResult(Boolean.parseBoolean(obj[0].toString()));
//            if(getGet_MstProdResult()){
//                setIHasil((String)obj[1].toString());
//                setIErrMsg((String)obj[2].toString());
//            }else{
//                setIErrMsg((String)obj[2].toString());
//            }

            }
            catch (Exception aE)
            {
                setIErrMsg(aE.toString());
            }
        } catch (Exception e) {
            setIErrMsg(e.toString());
        }
    }
}
