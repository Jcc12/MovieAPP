package com.sourcey.materiallogindemo;

import android.app.Application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Jocey on 4/24/18.
 */

public class SocketForAll extends Application {
    private int port = 5001;
    private String host = "local";
    DataOutputStream remoteOut;
    DataInputStream remoteIn;
    private Socket socket = null;

    public void init() throws Exception {
            try {
                InetAddress serverAddr = InetAddress.getByName(host);
                java.net.Socket sock = new java.net.Socket(serverAddr.getHostName(), port);
                remoteOut = new DataOutputStream(sock.getOutputStream());
                remoteIn = new DataInputStream(sock.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    

    public Socket getSocket(){
        return socket;
    }
    public void setSocket(Socket socket){
        this.socket=socket;
    }

    public DataInputStream getRemoteIn(){
        return remoteIn;
    }
    public void setRemoteIn(DataInputStream in){
        this.remoteIn=in;
    }

    public DataOutputStream getRemoteOut() {
        return remoteOut;
    }

    public void setRemoteOut(DataOutputStream out){
        this.remoteOut=out;
    }

}
