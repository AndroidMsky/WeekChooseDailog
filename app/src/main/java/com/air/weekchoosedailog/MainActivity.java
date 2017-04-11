package com.air.weekchoosedailog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.air.weekchoose.DateUtil;
import com.air.weekchoose.SelectWeekPopupWindow;

import java.text.ParseException;

public class MainActivity extends AppCompatActivity {
    private int nowY;
    private int nowM;
    private int nowD;

    private int startY;
    private int startM;
    private int startD;

    private int endY;
    private int endM;
    private int endD;
    public static String selDate;

    private TextView tvDate;
    private SelectWeekPopupWindow leftPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDate = (TextView) findViewById(R.id.textView2);
        setDateRange();
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataView();
            }
        });

    }

    public void showDataView() {
        initPopupWindow();
        if (startY == 0) {

            leftPopupWindow.show(nowY, nowM, nowD, tvDate, new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    leftPopupWindow.diss();
                }
            });
        } else {

            leftPopupWindow.show(startY, startM, startD, tvDate, new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    leftPopupWindow.diss();
                }
            });
        }

    }

    private void initPopupWindow()

    {


        if (leftPopupWindow == null) {
            leftPopupWindow = new SelectWeekPopupWindow(this, onLeftSelectItemClick, nowY, nowM, nowD);
            leftPopupWindow.setZXStyle(false);
        }

    }

    SelectWeekPopupWindow.OnSelectItemClickListencer onLeftSelectItemClick = new SelectWeekPopupWindow.OnSelectItemClickListencer() {
        @Override
        public void onSelectItemClick(View view, int position) {
            startY = leftPopupWindow.yearData;
            startM = leftPopupWindow.monthData;
            startD = leftPopupWindow.dayData;
            setDateRange();


        }
    };


    private void setDateRange() {
        nowY = DateUtil.getYear();
        nowM = DateUtil.getMonth();
        nowD = DateUtil.getDay();
        int[] is;
        try {
            if (startY == 0) is = DateUtil.getWeekDay(nowY, nowM, nowD);
            else
                is = DateUtil.getWeekDay(startY, startM, startD);
            endY = is[3];
            endM = is[4];
            endD = is[5];
            startY = is[0];
            startM = is[1];
            startD = is[2];

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (startY == endY)
            tvDate.setText(startY + "年" + startM + "月" + startD + "-" + endM + "月" + endD + "日");
        else
            tvDate.setText(startY + "年" + startM + "月" + startD + "-" + endY + "年" + endM + "月" + endD + "日");
        selDate = DateUtil.getYYYY_MM_DD(startY, startM, startD);
    }

}
