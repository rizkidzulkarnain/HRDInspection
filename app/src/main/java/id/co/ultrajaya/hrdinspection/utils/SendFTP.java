package id.co.ultrajaya.hrdinspection.utils;

import android.content.Context;

import org.jibble.simpleftp.SimpleFTP;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;

import java.io.File;

import it.sauronsoftware.ftp4j.FTPClient;

public class SendFTP {
    public static String sendFotoFTP(String iFile, String iHost, int iPort, String iUsername, String iPassword, String iDestFolder, String iNamaFile) {
        String aErr = "";
        FTPClient client = new FTPClient();
        try {
            client.connect(iHost, 21);
            client.login(iUsername, iPassword);
            client.setType(FTPClient.TYPE_BINARY);
            client.changeDirectory(iDestFolder);

            try {
                client.upload(new File(iFile));
            } catch (Exception ex) {
                ex.printStackTrace();
                aErr = "Error saat ftp 0" + ex.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            aErr = "Error saat ftp 1" + ex.toString();
            try {
                client.disconnect(true);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        //SimpleFTP ftp = new SimpleFTP();
        /*try {
         *//* ex : ftp.connect("b2b.ultrajaya.co.id", 21, "safefoto", "ultra2006");*//*
            ftp.connect(iHost, iPort, iUsername, iPassword);
            ftp.bin();
            ftp.cwd(iDestFolder);
            try {
                boolean result = ftp.stor(new File(iFile));
                if (result) {
                    aErr = "";
                }
            } catch (Exception ex) {
                aErr = "Error saat ftp 0 " + ex.toString();
            }
            ftp.disconnect();
        } catch (Exception e) {
            aErr = "Error saat ftp 1 " + e.toString();
        }*/
        return aErr;
    }
}
