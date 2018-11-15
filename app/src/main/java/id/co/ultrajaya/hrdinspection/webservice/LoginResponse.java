package id.co.ultrajaya.hrdinspection.webservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Vector;


public class LoginResponse {
    String response = "";

    private Object resultsRequestSOAP = null;
    //    private static final String SOAP_ACTION = "http://tempuri.org/Get_UserLogin";
    private static final String SOAP_ACTION = "http://tempuri.org/Login";
    //    private static final String METHOD_NAME = "Get_UserLogin";
    private static final String METHOD_NAME = "Login";
    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String URL = "http://b2b.ultrajaya.co.id/ws_middle3/service.asmx";


    public boolean LoginResult;

    public void setLoginResult(boolean LoginResult) {
        this.LoginResult = LoginResult;
    }

    public boolean getLoginResult() {
        return LoginResult;
    }
    private String iNama;

    public void setINama(String iNama) {
        this.iNama = iNama;
    }

    public String getINama() {
        return iNama;
    }
    private String iAccount;

    public void setIAccount(String iAccount) {
        this.iAccount = iAccount;
    }

    public String getIAccount() {
        return iAccount;
    }
    private String iPrefprod;

    public void setIPrefprod(String iPrefprod) {
        this.iPrefprod = iPrefprod;
    }

    public String getIPrefprod() {
        return iPrefprod;
    }
    private String iVersion;

    public void setIVersion(String iVersion) {
        this.iVersion = iVersion;
    }

    public String getIVersion() {
        return iVersion;
    }
    private String iErrMsg;

    public void setIErrMsg(String iErrMsg) {
        this.iErrMsg = iErrMsg;
    }

    public String getIErrMsg() {
        return iErrMsg;
    }

    public void Login(String IIdUser, String iPasswd, String iNama, String iAccount, String iPrefprod, String iVersion, String iErrMsg) {
        try {

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("IIdUser", IIdUser);
            request.addProperty("iPasswd", iPasswd);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                resultsRequestSOAP = envelope.getResponse();

                Vector a = new Vector();
                a = (Vector) resultsRequestSOAP;
                Object[] obj = new Object[a.size()];
                a.copyInto(obj);
                setLoginResult(Boolean.parseBoolean(obj[0].toString()));
                if (getLoginResult()) {
                    setINama(obj[1].toString());
                    setIAccount(obj[2].toString());
                    setIPrefprod(obj[3].toString());
                    setIVersion(obj[4].toString());
                    setIErrMsg(obj[5].toString());
                } else {
                    setIErrMsg(obj[1].toString());
                }

            } catch (Exception e) {
                setIErrMsg(e.toString());
            }
        } catch (Exception e) {
            setIErrMsg(e.toString());
        }
    }

    public void Login(String iuser, String iPasswd ){//, String iAccount, String iPrefprod, String iVersion, String iErrMsg) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            String anama = "";
            String adeflogin = "";
            String aerr = "";

            request.addProperty("iuser", iuser);
            request.addProperty("iPasswd", iPasswd);
            request.addProperty("inama", anama);
            request.addProperty("iDefLogin", adeflogin);
            request.addProperty("iErrmsg", aerr);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            try {



                androidHttpTransport.call(SOAP_ACTION, envelope);
                resultsRequestSOAP = envelope.getResponse();


//                androidHttpTransport.call(SOAP_ACTION, envelope);
//                resultsRequestSOAP = envelope.getResponse();

                Vector a = new Vector();
                a = (Vector) resultsRequestSOAP;
                Object[] obj = new Object[a.size()];
                a.copyInto(obj);
                setLoginResult(Boolean.parseBoolean(obj[0].toString()));
                if (getLoginResult()) {
                    setINama(obj[1].toString());
                    setIAccount(obj[2].toString());
                    setIErrMsg(obj[3].toString());
                } else {
                    setIErrMsg(obj[3].toString());
                }

            } catch (Exception e) {
                setIErrMsg(e.toString());
            }
        } catch (Exception e) {
            setIErrMsg(e.toString());
        }
    }


}
