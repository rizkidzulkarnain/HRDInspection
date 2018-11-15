package id.co.ultrajaya.hrdinspection.webservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import id.co.ultrajaya.hrdinspection.clsumum.Config;
/**
 * Created by it-uswa on 11/30/2016.
 */

public class SP_SAVE_MV {

    private Object resultsRequestSOAP = null;
    private static final String SOAP_ACTION = "http://tempuri.org/SP_SAVE_MV";
    private static final String METHOD_NAME= "SP_SAVE_MV";
    private static final String NAMESPACE = "http://tempuri.org/";
    private String iHasil;
    private String _DispMsg ;
    public void set_DispMsg (String iDispmsg)
    {
        _DispMsg = iDispmsg;
    }
    public String get_DispMsg(){return _DispMsg;};
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

    public void save_sp(String iAccount, String iSid, String ikey, String iMvitem, String iAction, String inamasp,
                        String iothval  ) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            String amv;
            amv =iMvitem ;

            String alistparam = "@SID|@Action|@Key|@Record|@Oth_Val|@Err_Msg|@Disp_Msg";
            String alistnilai = iSid  + "|" + iAction  + "|" + ikey + "|" + amv + "|" + iothval + "|" + "" + "|" + "";
            String alistarah =  "in" + "|" + "in" + "|" + "inout" + "|" + "in" + "|" + "in" + "|" + "out" + "|" + "out";
            String alistipe =  "" + "|" + "" + "|" + "" + "|" + "cursor" + "|" + "" + "|" + "" + "|" + "";
            String aerrmsg = "";
            request.addProperty("iServer", iAccount);
            request.addProperty("iListParam", alistparam);
            request.addProperty("iListNilai", alistnilai);
            request.addProperty("iarah", alistarah);
            request.addProperty("itipe", alistipe);
            request.addProperty("iSptrans", inamasp);
            request.addProperty("iErrmsg", aerrmsg);
            request.addProperty("iDispMsg", aerrmsg);
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
                    set_DispMsg(response.getPropertyAsString(2).toString());
                    setIErrMsg("");
                }
                else {
                    setIErrMsg(aerrmsg);
                }//
            }
            catch (Exception aE) {
                setIErrMsg(aE.toString());
            }
        } catch (Exception e) {
            setIErrMsg(e.toString());
        }
    }
}