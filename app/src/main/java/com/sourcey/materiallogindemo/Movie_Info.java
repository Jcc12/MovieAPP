package com.sourcey.materiallogindemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;


import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.Thread.sleep;

public class Movie_Info extends AppCompatActivity implements View.OnClickListener{

    Button movie1, movie2, movie3, movie4;
    String[] moviename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie__info);


        movie1 = (Button)findViewById(R.id.movie1);
        movie2 = (Button)findViewById(R.id.movie2);
        movie3 = (Button)findViewById(R.id.movie3);
        movie4 = (Button)findViewById(R.id.movie4);

        movieinfoConnect();

        findViewById(R.id.movie1).setOnClickListener(this);
        findViewById(R.id.movie2).setOnClickListener(this);
        findViewById(R.id.movie3).setOnClickListener(this);
        findViewById(R.id.movie4).setOnClickListener(this);

    }

    private void movieinfoConnect()
    {
        SocketForAll mySocket = (SocketForAll) Movie_Info.this.getApplication();
        try {

            mySocket.init("L");
            final ProgressDialog progressDialog = new ProgressDialog(Movie_Info.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
//            new android.os.Handler().postDelayed(
//                    new Runnable() {
//                        public void run() {
//                            progressDialog.dismiss();
//                        }
//                    }, 3000);
            mySocket.lock3.lock();
            mySocket.con3.await();
            mySocket.lock3.unlock();
            progressDialog.dismiss();
            moviename = mySocket.movieName.split("\\:");


            movie1.setText(moviename[0]);
            movie2.setText(moviename[1]);
            movie3.setText(moviename[2]);
            movie4.setText(moviename[3]);

        } catch(IOException e){
            System.out.println(e.getMessage() + ": Failed to connect to server.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.movie1:
                getInfo(0);

                break;
            case R.id.movie2:
                getInfo(1);

                break;
            case R.id.movie3:
                getInfo(2);

                break;
            case R.id.movie4:
                getInfo(3);

                break;

        }

    }

    public void getInfo(int a){
        Intent intent = new Intent();
        intent.putExtra("name", moviename[a]);
        intent.setClass(this, movieInfo1.class);
        startActivity(intent);
    }
}
