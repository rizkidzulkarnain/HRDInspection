package id.co.ultrajaya.hrdinspection.webservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import id.co.ultrajaya.hrdinspection.clsumum.Config;

 /**
  * Created by it-uswa on 11/25/2016.
  */

 public class SP_GETPROC_PLSQL_DET {

     private Object resultsRequestSOAP = null;
     private static final String SOAP_ACTION = "http://tempuri.org/SP_GETPROC_PLSQL_MV_DET";
     private static final String METHOD_NAME = "SP_GETPROC_PLSQL_MV_DET";
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
     private String _at21;
     private String _at22;
     private String _at23;
     private String _at24;

     private String _dispmsg ;
     private String _ErrMsg;

     public String get_dispmsg() {return _dispmsg;}
     public String get_ErrMsg() {return _ErrMsg;}
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
     public String get_at13()
     {
         return _at13;
     }
     public String get_at14()
     {
         return _at14;
     }
     public String get_at15()
     {
         return _at15;
     }
     public String get_at16()
     {
         return _at16;
     }
     public String get_at17()
     {
         return _at17;
     }
     public String get_at18()
     {
         return _at18;
     }
     public String get_at19()
     {
         return _at19;
     }
     public String get_at20()
     {
         return _at20;
     }
     public String get_at21()
     {
         return _at21;
     }
     public String get_at22()
     {
         return _at22;
     }
     public String get_at23()
     {
         return _at23;
     }
     public String get_at24()
     {
         return _at24;
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

     public void get_sp(String iAccount, String iaction, String ikey, String imv, String isptrans) {
         try {

             SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
             request.addProperty("iServer", iAccount);
             request.addProperty("iaction", iaction);
             request.addProperty("ikey", ikey);
             request.addProperty("imv", imv);
             request.addProperty("iSptrans", isptrans);
             request.addProperty("iErrmsg", "");
             request.addProperty("iDispMsg", "");

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
             request.addProperty("at13", "");
             request.addProperty("at14", "");
             request.addProperty("at15", "");
             request.addProperty("at16", "");
             request.addProperty("at17", "");
             request.addProperty("at18", "");
             request.addProperty("at19", "");
             request.addProperty("at20", "");
             request.addProperty("at21", "");
             request.addProperty("at22", "");
             request.addProperty("at23", "");
             request.addProperty("at24", "");
             SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
             envelope.dotNet=true;
             envelope.setOutputSoapObject(request);
             int TimeOut=30000;
             HttpTransportSE androidHttpTransport = new HttpTransportSE(Config.Companion.getUrl5(),TimeOut);
             try
             {


                 androidHttpTransport.call(SOAP_ACTION, envelope);
                 resultsRequestSOAP =  envelope.getResponse();
                 SoapObject response = (SoapObject) envelope.bodyIn;
                 String aNilai = response.getPropertyAsString(0).toString();
                 _ErrMsg = response.getPropertyAsString(1).toString();
                 _dispmsg = response.getPropertyAsString(2).toString();
                 _at1 =  response.getPropertyAsString(3).toString();
                 _at2 =  response.getPropertyAsString(4).toString();
                 _at3 =  response.getPropertyAsString(5).toString();
                 _at4 =  response.getPropertyAsString(6).toString();
                 _at5 =  response.getPropertyAsString(7).toString();
                 _at6 =  response.getPropertyAsString(8).toString();
                 _at7 =  response.getPropertyAsString(9).toString();
                 _at8 =  response.getPropertyAsString(10).toString();
                 _at9 =  response.getPropertyAsString(11).toString();
                 _at10 =  response.getPropertyAsString(12).toString();
                 _at11 =  response.getPropertyAsString(13).toString();
                 _at12 =  response.getPropertyAsString(14).toString();
                 _at13 =  response.getPropertyAsString(15).toString();
                 _at14 =  response.getPropertyAsString(16).toString();
                 _at15=   response.getPropertyAsString(17).toString();
                 _at16 =  response.getPropertyAsString(18).toString();
                 _at17 =  response.getPropertyAsString(19).toString();
                 _at18 =  response.getPropertyAsString(20).toString();
                 _at19 =  response.getPropertyAsString(21).toString();
                 _at20 =  response.getPropertyAsString(22).toString();
                 _at21 =  response.getPropertyAsString(23).toString();
                 _at22 =  response.getPropertyAsString(24).toString();
                 _at23 =  response.getPropertyAsString(25).toString();
                 _at24 =  response.getPropertyAsString(26).toString();
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


