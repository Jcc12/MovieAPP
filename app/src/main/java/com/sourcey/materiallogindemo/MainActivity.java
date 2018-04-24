package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.OnClick;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final static int LOGINCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, LOGINCODE);

        findViewById(R.id.BindCard).setOnClickListener(this);
        findViewById(R.id.SelectSeat).setOnClickListener(this);
        findViewById(R.id.imageView).setOnClickListener(this);

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
                Intent intent_seat = new Intent(this, SeatActivity.class);
                startActivity(intent_seat);
                break;
            case R.id.imageView:
                Intent intent_movie = new Intent(this, MovieListActivity.class);
                startActivity(intent_movie);
                break;

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
}
