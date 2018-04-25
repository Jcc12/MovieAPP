package com.sourcey.materiallogindemo;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;
import static java.lang.Thread.sleep;

public class movieInfo1 extends AppCompatActivity {



    String Name;
    SocketForAll mySocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info1);
        Intent intent = getIntent();
        Name = intent.getStringExtra("name");

        TextView name = (TextView) findViewById(R.id.profile_name);
        TextView price = (TextView) findViewById(R.id.price_movie);
        TextView time = (TextView) findViewById(R.id.time_movie);
        TextView brief = (TextView) findViewById(R.id.details_text);
        CircleImageView image = (CircleImageView) findViewById(R.id.profile_image);

        getInfo();

        name.setText(Name);
        brief.setText(mySocket.brief);
        time.setText(mySocket.time + " min");
        price.setText(mySocket.price + " yuan");
        Bitmap bmp = BitmapFactory.decodeByteArray(mySocket.data, 0, mySocket.data.length);
        image.setImageBitmap(bmp);

    }

    public void getInfo() {
        mySocket = (SocketForAll) movieInfo1.this.getApplication();
        try {
            mySocket.initNew("I" + Name);
            mySocket.lock2.lock();
            mySocket.con2.await();
            mySocket.lock2.unlock();
//            sleep(3000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
