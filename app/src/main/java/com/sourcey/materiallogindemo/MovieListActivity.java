package com.sourcey.materiallogindemo;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.dd.CircularProgressButton;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import java.util.List;



public class MovieListActivity extends AppCompatActivity implements
        DiscreteScrollView.ScrollListener<adapter.ViewHolder>,
        DiscreteScrollView.OnItemChangedListener<adapter.ViewHolder>,
        View.OnClickListener {

        private ArgbEvaluator evaluator;
        private int currentOverlayColor;
        private int overlayColor;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_movie_list);

                evaluator = new ArgbEvaluator();
                currentOverlayColor = ContextCompat.getColor(this, R.color.CurrentItemOverlay);
                overlayColor = ContextCompat.getColor(this, R.color.ItemOverlay);

                MovieList movieList = MovieList.get();
                List<Image> data = movieList.getData();
                DiscreteScrollView itemPicker = (DiscreteScrollView) findViewById(R.id.item);
                itemPicker.setAdapter(new adapter(data));
                itemPicker.addScrollListener(this);
                itemPicker.addOnItemChangedListener(this);
                itemPicker.scrollToPosition(1);

                findViewById(R.id.home).setOnClickListener(this);
                findViewById(R.id.button).setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                    case R.id.home:
                            finish();
                            break;
                    case R.id.button:
//                            CircularProgressButton circularButton = (CircularProgressButton) findViewById(R.id.button);
//                            circularButton.setIndeterminateProgressMode(true); // turn on indeterminate progress
//                            circularButton.setProgress(50); // set progress > 0 & < 100 to display indeterminate progress
                            Intent Movie = new Intent(this, Movie_Info.class);
                            startActivity(Movie);
//                            circularButton.setProgress(0); // s

                            break;

            }

        }


        @Override
        public void onScroll(
                float currentPosition,
                int currentIndex, int newIndex,
                @Nullable adapter.ViewHolder currentHolder,
                @Nullable adapter.ViewHolder newCurrent) {
                if (currentHolder != null && newCurrent != null) {
                        float position = Math.abs(currentPosition);
                        currentHolder.setOverlayColor(interpolate(position, currentOverlayColor, overlayColor));
                        newCurrent.setOverlayColor(interpolate(position, overlayColor, currentOverlayColor));
                }
        }

        @Override
        public void onCurrentItemChanged(@Nullable adapter.ViewHolder viewHolder, int adapterPosition) {
                //viewHolder will never be null, because we never remove items from adapter's list
                if (viewHolder != null) {
                        viewHolder.setOverlayColor(currentOverlayColor);
                }
        }



        private int interpolate(float fraction, int c1, int c2) {
                return (int) evaluator.evaluate(fraction, c1, c2);
        }
}
