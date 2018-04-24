package com.sourcey.materiallogindemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class SeatActivity extends AppCompatActivity implements View.OnClickListener {
    public SeatTable seatTableView;
    public Button button;
    private Context mcontext;



    private int rowNum = 10, colNum = 15;

    // 合法位置，如果为1的话，说明这个位置没了
    private int[][] validSet = new int[rowNum][colNum];
    // 卖了的位置，如果为1的话，说明这个位置卖了
    private int[][] soldSet = new int[rowNum][colNum];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mcontext = getApplicationContext();
        setContentView(R.layout.activity_seat);

        seatTableView = findViewById(R.id.seatView);
        // 设置屏幕名称
        seatTableView.setScreenName("8号厅荧幕");
        // 设置最多选中
        seatTableView.setMaxSelected(3);

        setCheckData();
        seatTableView.setData(rowNum, colNum);
        bindViews();
    }

    private void bindViews() {
        button = findViewById(R.id.btn);
        button.setOnClickListener(this);
    }

    private void setCheckData() {
        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                for (int i = 0; i < rowNum; i++) {
                    for (int j = 0; j < colNum; j++) {
                        int state = validSet[i][j];
                        if (state == 1) {
                            if (row == i && j == column) {
                                return false;
                            }
                        }
                    }
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                for (int i = 0; i < rowNum; i++) {
                    for (int j = 0; j < colNum; j++) {
                        int state = soldSet[i][j];
                        if (state == 1) {
                            if (row == i && j == column) {
                                return true;
                            }
                        }
                    }
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {

            }

            @Override
            public void unCheck(int row, int column) {

            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return new String[0];
            }


        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                ArrayList<String> selected = seatTableView.getSelectedSeat();
                for (String seat : selected) {
                    String[] num = seat.split(",");
                    // 选中的行，列
                    int row = Integer.parseInt(num[0]);
                    int col = Integer.parseInt(num[1]);
                }

        }
    }

}
