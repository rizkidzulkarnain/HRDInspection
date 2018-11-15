package id.co.ultrajaya.hrdinspection.webservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import id.co.ultrajaya.hrdinspection.clsumum.Config;
/**
 * Created by it-aris on 01/02/2017.
 */

public class Get_SP_MV {
    private Object resultsRequestSOAP = null;
    private static final String SOAP_ACTION = "http://tempuri.org/SP_GET_MV";
    private static final String METHOD_NAME = "SP_GET_MV";
    private static final String NAMESPACE = "http://tempuri.org/";

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
            String alistnilai =  ikey + "|" + amv + "|" + "1" + "|" + "0" + "|" + iothval + "|" + "" + "|" + "";
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

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);
            int TimeOut=30000;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Config.Companion.getUrlDefault(),TimeOut);
            try
            {
                System.setProperty("http.keepAlive", "false");
                androidHttpTransport.call(SOAP_ACTION, envelope);
                resultsRequestSOAP =  envelope.getResponse();
                SoapObject response = (SoapObject) envelope.bodyIn;
                String aNilai = response.getPropertyAsString(0).toString();
                aerrmsg = response.getPropertyAsString(1).toString();
                if (aerrmsg.equals( "anyType{}"))
                {
                    setIHasil(aNilai);
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
