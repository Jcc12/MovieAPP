
package com.sourcey.materiallogindemo;

        import android.app.AlertDialog;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.AttributeSet;
        import android.view.View;
        import android.widget.Button;

        import java.util.ArrayList;

        import static java.lang.Thread.sleep;

public class SeatActivity extends AppCompatActivity implements View.OnClickListener {
    public SeatTable seatTableView;
    public Button button;
    private Context mcontext;
    SocketForAll mySocket;


    private int rowNum = 10, colNum = 15;

    // 1: invalid seat
    private int[][] validSet = new int[rowNum][colNum];
    // 1: sold seat
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
        seatTableView.setMaxSelected(1);

       mySocket = (SocketForAll) SeatActivity.this.getApplication();
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
        if (mySocket.seat == "N"){
            InvalidCard();
            finish();
        }
        setSeatTableView();
        setCheckData();
        seatTableView.setData(rowNum, colNum);
        bindViews();


    }
    private void InvalidCard(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SeatActivity.this);

        builder.setMessage("Bank Card is not bound.");

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setSeatTableView() {

        char[] c = mySocket.seat.toCharArray();
        for (int i = 0; i < c.length; i++) {
            int row = i / colNum;
            int col = i % colNum;
            if (c[i] == '1') {
                validSet[row][col] = 0;
                soldSet[row][col] = 1;
            } else {
                validSet[row][col] = 0;
                soldSet[row][col] = 0;
            }

        }
        mySocket.flag = 0;
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
    private void SeatConfirmed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SeatActivity.this);

        builder.setMessage("Seat Confirmed!");

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                try {
                    ArrayList<String> selected = seatTableView.getSelectedSeat();
                    for (String seat : selected) {
                        String[] num = seat.split(",");
                        // 选中的行，列
                        int row = Integer.parseInt(num[0]);
                        int col = Integer.parseInt(num[1]);
                        String _num = String.valueOf(row * colNum + col);
                        mySocket.init("P" + _num);
                    }
                   SeatConfirmed();

                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }
}

