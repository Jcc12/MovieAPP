package com.sourcey.materiallogindemo;

import android.app.Application;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Jocey on 4/24/18.
 */

public class SocketForAll extends Application {
    private int port = 5001;
    private String host = "10.180.116.106";
    DataOutputStream remoteOut;
    DataInputStream remoteIn;
    private Socket socket = null;
    String msg;
    int code;
    byte[] data;
    String brief;
    String time;
    String price;
    String movieName = null, movieInfo = null;
    String seat =null;
    int flag = 0;
    Lock lock = new ReentrantLock(), lock2 = new ReentrantLock(), lock3 = new ReentrantLock();
    Condition con = lock.newCondition(), con2 = lock2.newCondition(), con3 = lock3.newCondition();
    public void init(String msg) throws Exception {
        this.msg = msg;

        Thread t = new connectService();
        t.start();
    }

    public void initNew(String msg) throws Exception {
        this.msg = msg;

        Thread t = new connectServiceNew();
        t.start();
    }


    private class connectService extends Thread {
        public void run(){
            Socket sock = null;
            try {
                if(socket == null || socket.isClosed()){
                    sock = new Socket(host, port);
                    socket = sock;
                    remoteOut = new DataOutputStream(socket.getOutputStream());
                    remoteIn = new DataInputStream(socket.getInputStream());
                }
                remoteOut.writeUTF(msg);
                remoteOut.flush();
                String s = remoteIn.readUTF();
                while(s.length()==0) s = remoteIn.readUTF();
                switch(s.charAt(0)){
                    case 'Y':
                        code = 1;break;
                    case 'I':
                        code = 2; break;
                    case 'P':
                        code = 3; break;
                    case 'R':
                        code = 4; break;
                    case 'L':
                        movieName = s.substring(1);
                    case 'A':
                        movieInfo = s.substring(1);
                    case '0':
                        code = 5; break;
                    case '1':
                        code = 6; break;
                    default:
                        code = 7; break;
                }
                lock3.lock();
                con3.signal();
                lock3.unlock();
//                remoteOut.writeUTF("S123:12345");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class connectServiceNew extends Thread {
        public void run(){
            try {
                if(msg.equals("Q")){
                    remoteOut.writeUTF(msg);
                    remoteOut.flush();
                    seat = remoteIn.readUTF();
                    flag=1;
                    lock.lock();
                    con.signal();
                    lock.unlock();
                }
                else {
                    remoteOut.writeUTF(msg);
                    remoteOut.flush();
                    int size = remoteIn.readInt();

                    data = new byte[size];
                    int len = 0;
                    while (len < size) {
                        len += remoteIn.read(data, len, size - len);
                    }
                    brief = remoteIn.readUTF();
                    time = remoteIn.readUTF();
                    price = remoteIn.readUTF();
                    lock2.lock();
                    con2.signal();
                    lock2.unlock();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
