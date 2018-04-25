package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import butterknife.OnClick;

import static java.lang.Thread.sleep;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{//, ProgressGenerator.OnCompleteListener{

    private final static int LOGINCODE = 1;
    SocketForAll mySocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, LOGINCODE);

        findViewById(R.id.BindCard).setOnClickListener(this);
        findViewById(R.id.SelectSeat).setOnClickListener(this);
        findViewById(R.id.movieInfo).setOnClickListener(this);
        findViewById(R.id.PayInfo).setOnClickListener(this);




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 2 && requestCode == LOGINCODE){
            Intent Movie_intent = new Intent(this, MovieListActivity.class);
            startActivityForResult(Movie_intent,3);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BindCard:
                Intent intent = new Intent(this, BindCard.class);
                startActivity(intent);
                break;
            case R.id.SelectSeat:
//                mySocket.lock.lock();
                try {
//                    mySocket = (SocketForAll) MainActivity.this.getApplication();
//                    mySocket.initNew("Q");
//                    mySocket.con.wait();
//                    while(mySocket.flag==0);
                    Intent intent_seat = new Intent(this, SeatActivity.class);
                    startActivity(intent_seat);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
//                    mySocket.lock.unlock();
                }
                break;
            case R.id.movieInfo:
                Intent intent_movie = new Intent(this, MovieListActivity.class);
                startActivity(intent_movie);
                break;
            case R.id.PayInfo:
                Intent intent_pay = new Intent(this, PayActivity.class);
                startActivity(intent_pay);


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onComplete() {
//
//    }
}
