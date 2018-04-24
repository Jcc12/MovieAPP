package com.sourcey.materiallogindemo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;



    private int loginConnect(String id, String password)
    {
        SocketForAll mySocket = (SocketForAll) LoginActivity.this.getApplication();
        try {
            mySocket.init();
            Socket socket=mySocket.getSocket();
            DataOutputStream Out=mySocket.getRemoteOut();
            DataInputStream In=mySocket.getRemoteIn();

            Out.writeUTF("L" + id + ":" + password);
            Out.flush();

            String s = In.readUTF();
            while(s.length()==0) s = In.readUTF();

            switch(s.charAt(0)){
                case 'Y':
                    return 1;
                case 'I':
                    return 2;
                case 'P':
                    return 3;
                case 'R':

                    return 4;
                default:
                    return 5;
            }
        } catch(IOException e){
            System.out.println(e.getMessage() + ": Failed to connect to server.");
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.
        final int code=loginConnect(email,password);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        if(code==1) {
                            setResult(2);
                            onLoginSuccess();
                            progressDialog.dismiss();
                        }
                       else if(code == 2){
                            progressDialog.dismiss();
                            onLoginFailed_ID();
                        }
                        else if(code == 3){
                            progressDialog.dismiss();
                            onLoginFailed_pwd();
                        }
                        else if(code == 4){
                            progressDialog.dismiss();
                            onLoginFailed_repeat();
                        }
                        else {
                            progressDialog.dismiss();
                            onLoginFailed();
                        }

                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful sign up logic here
                // By default we just finish the Activity and log them in automatically
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                //this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }
    public void onLoginFailed_ID() {
        Toast.makeText(getBaseContext(), "Wrong ID", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }
    public void onLoginFailed_pwd() {
        Toast.makeText(getBaseContext(), "Wrong ID", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }
    public void onLoginFailed_repeat() {
        Toast.makeText(getBaseContext(), "Repeat Login", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 20) {
            _passwordText.setError("between 4 and 20 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
