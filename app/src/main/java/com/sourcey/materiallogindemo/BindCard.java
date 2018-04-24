package com.sourcey.materiallogindemo;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindCard extends AppCompatActivity {
    private static final String TAG = "SaveCardActivity";

    @BindView(R.id.cardNumber) EditText cardNumber;
    @BindView(R.id.cardpwd) EditText password;
    @BindView(R.id.btn_save) Button save_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_card);

        ButterKnife.bind(this);

        save_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                save();
            }
        });
    }
    private int bindcardConnect(String id, String password)
    {
        SocketForAll mySocket = (SocketForAll) BindCard.this.getApplication();
        try {
            mySocket.init();
            Socket socket=mySocket.getSocket();
            DataOutputStream Out=mySocket.getRemoteOut();
            DataInputStream In=mySocket.getRemoteIn();

            Out.writeUTF("C" + id + ":" + password);
            Out.flush();

            String s = In.readUTF();
            while(s.length()==0) s = In.readUTF();

            switch(s.charAt(0)){
                case '1':
                    return 1;
                case '0':
                    return -1;
                default:
                    return -1;
            }
        } catch(IOException e){
            System.out.println(e.getMessage() + ": Failed to connect to server.");
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    public void save() {
        Log.d(TAG, "Save_card");

        if (!validate()) {
            onSaveFailed();
            return;
        }

        save_btn.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(BindCard.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String _cardNumber = cardNumber.getText().toString();
        String _password = password.getText().toString();

        // TODO: Implement your own authentication logic here.
        final int code=bindcardConnect(_cardNumber,_password);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if(code==1) {
                            onSaveSuccess();
                            progressDialog.dismiss();
                        }
                        else {
                            onSaveFailed();
                            progressDialog.dismiss();
                        }
                    }
                }, 3000);
    }

    public boolean validate() {
        boolean valid = true;

        String _cardnumber = cardNumber.getText().toString();
        String _password = password.getText().toString();

        if (_cardnumber.isEmpty()) {
            cardNumber.setError("enter a valid card number");
            valid = false;
        } else {
            cardNumber.setError(null);
        }

        if (_password.isEmpty() || _password.length() != 6) {
            password.setError("Invalid password.");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }
    @OnClick(R.id.btn_save)
    public void gotoLast(){
        finish();
    }
    public void onSaveSuccess() {
        save_btn.setEnabled(true);
        finish();
    }

    public void onSaveFailed() {
        Toast.makeText(getBaseContext(), "Save card failed", Toast.LENGTH_LONG).show();

        save_btn.setEnabled(true);
    }
}
