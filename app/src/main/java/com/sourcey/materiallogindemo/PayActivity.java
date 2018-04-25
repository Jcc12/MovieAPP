package com.sourcey.materiallogindemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PayActivity extends AppCompatActivity {

    SocketForAll mySocket;
    String cardnumber,money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        mySocket = (SocketForAll) PayActivity.this.getApplication();
        try {
            mySocket.initNew("Q");
        } catch (Exception e) {
            e.printStackTrace();
        }

        mySocket.lock.lock();
        try {
            mySocket.con.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mySocket.lock.unlock();
        if (mySocket.seat.substring(0,1).equals("M")){
            cardnumber = mySocket.seat.substring(1,mySocket.seat.indexOf(':'));
            money = mySocket.seat.substring(mySocket.seat.indexOf(':') + 1);
            dialog();
        }
        else
            dialog1();
    }
    private void dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);

        builder.setMessage("You have spent " + money + " yuan from your card:"+cardnumber);

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void dialog1(){
        AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);

        builder.setMessage("Payment not successful");

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
