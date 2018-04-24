package com.sourcey.materiallogindemo;

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

public class Movie_Info extends AppCompatActivity implements View.OnClickListener{



    Button movie1, movie2, movie3, movie4;
    String[] moviename;
    TextView price1, price2, price3, price4;
    TextView time1, time2, time3, time4;
    TextView brief1, brief2, brief3, brief4;
    TextView name1, name2, name3, name4;
    CircleImageView image1, image2, image3, image4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie__info);

        name1 = (TextView)findViewById(R.id.profile_name);
        name2 = (TextView)findViewById(R.id.profile_name_1);
        name3 = (TextView)findViewById(R.id.profile_name_2);
        name4 = (TextView)findViewById(R.id.profile_name_3);

        movie1 = (Button)findViewById(R.id.movie1);
        movie2 = (Button)findViewById(R.id.movie2);
        movie3 = (Button)findViewById(R.id.movie3);
        movie4 = (Button)findViewById(R.id.movie4);

        price1 = (TextView)findViewById(R.id.price_movie);
        price2 = (TextView)findViewById(R.id.price_movie_1);
        price3 = (TextView)findViewById(R.id.price_movie_2);
        price4 = (TextView)findViewById(R.id.price_movie_3);

        time1 = (TextView)findViewById(R.id.time_movie);
        time2 = (TextView)findViewById(R.id.time_movie_1);
        time3 = (TextView)findViewById(R.id.time_movie_2);
        time4 = (TextView)findViewById(R.id.time_movie_3);

        brief1 = (TextView)findViewById(R.id.details_text);
        brief2 = (TextView)findViewById(R.id.details_text_1);
        brief3 = (TextView)findViewById(R.id.details_text_2);
        brief4 = (TextView)findViewById(R.id.details_text_3);

        image1 = (CircleImageView)findViewById(R.id.profile_image);
        image2 = (CircleImageView)findViewById(R.id.profile_image_1);
        image3 = (CircleImageView)findViewById(R.id.profile_image_2);
        image4 = (CircleImageView)findViewById(R.id.profile_image_3);

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
            mySocket.init();
            Socket socket=mySocket.getSocket();
            DataOutputStream Out=mySocket.getRemoteOut();
            DataInputStream In=mySocket.getRemoteIn();

            Out.writeUTF("L" );
            Out.flush();

            String s = In.readUTF();

            s=s.substring(1);
            moviename = s.split("\\:");

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
                setContentView(R.layout.activity_movie__info1);
                break;
            case R.id.movie2:
                getInfo(1);
                setContentView(R.layout.activity_movie__info2);
                break;
            case R.id.movie3:
                getInfo(2);
                setContentView(R.layout.activity_movie__info3);
                break;
            case R.id.movie4:
                getInfo(3);
                setContentView(R.layout.activity_movie__info4);
                break;

        }

    }
    public static int byteArrayToInt(byte[] b)
    {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    public void getInfo(int a){
        SocketForAll mySocket = (SocketForAll) Movie_Info.this.getApplication();
        try {
            mySocket.init();
            Socket socket=mySocket.getSocket();
            DataOutputStream Out=mySocket.getRemoteOut();
            DataInputStream In=mySocket.getRemoteIn();



            Out.writeUTF("I"+ moviename[a] );
            Out.flush();

            String s = In.readUTF();
            s=s.substring(1);
            int size = In.readInt();
            byte[] data = new byte[size];
            int len = 0;
            while (len < size) {
                len += In.read(data, len, size - len);
            }
            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
            int image_res=byteArrayToInt(data);

            s=s.substring(s.indexOf('I')+1);
            String brief=s.substring(0,s.indexOf(':'));

            s=s.substring(s.indexOf(':')+1);
            String time=s.substring(1,s.indexOf(':'));


            s=s.substring(s.indexOf(':')+1);
            String price = s.substring(1);


            if(a==0){
                name1.setText(moviename[0]);
                brief1.setText(brief);
                time1.setText(time+" min");
                price1.setText(price+" yuan");
                image1.setImageBitmap(bmp);
            }
            else if(a==1){
                name2.setText(moviename[0]);
                brief2.setText(brief);
                time2.setText(time+" min");
                price2.setText(price+" yuan");
                image2.setImageBitmap(bmp);
            }
            else if(a==2){
                name3.setText(moviename[0]);
                brief3.setText(brief);
                time3.setText(time+" min");
                price3.setText(price+" yuan");
                image3.setImageBitmap(bmp);
            }
            else if(a==3){
                name4.setText(moviename[0]);
                brief4.setText(brief);
                time4.setText(time+" min");
                price4.setText(price+" yuan");
                image4.setImageBitmap(bmp);
            }

//arrange these value into the xml


        } catch(IOException e){
            System.out.println(e.getMessage() + ": Failed to connect to server.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
