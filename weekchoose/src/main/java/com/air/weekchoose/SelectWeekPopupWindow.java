package com.air.weekchoose;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by ex-android-msky on 16/6/30.
 */
public class SelectWeekPopupWindow {


    private int year;
    private int month;
    private int days;

    private int syear;
    private int smonth;
    private int sdays;
    private int sdaysLiang;

    private int xyear;
    private int xmonth;
    private int xdays;
    private int xdaysLiang;
    //xianshi
    private int day;
    //shiji
    public int dayData;
    public int yearData;
    public int monthData;

    private int daysLiang;
    private int firstDay;
    private PopupWindow popupWindow;
    private Context mContext;
    private View layoutContent;

    private ListView listView;
    private List<int[]> list = new ArrayList<>();
    private OnSelectItemClickListencer onSelectItemClickListencer;
    private int selectPosition = -1;
    private boolean isZxStyle, isCenter;

    private TextView title;

    private boolean hasHead;
    private boolean hasFoot;


    public SelectWeekPopupWindow(Context context, OnSelectItemClickListencer onSelectItemClickListencer, int year, int month, int day) {
        mContext = context;

        this.list = new ArrayList<>();
        this.onSelectItemClickListencer = onSelectItemClickListencer;
        init();

    }

    public void setZXStyle(boolean isCenter) {
        isZxStyle = true;
        this.isCenter = isCenter;
    }

    private void setSX() {

        syear = year;
        xyear = year;
        smonth = month - 1;
        xmonth = month + 1;

        if (smonth == 0) {
            smonth = 12;
            syear--;
        }
        if (xmonth == 13) {
            xmonth = 1;
            xyear++;
        }
        xdays = DateUtil.getMonthDays(xyear, xmonth - 1);
        sdays = DateUtil.getMonthDays(syear, smonth - 1);


    }

    private void getSum() {
        setSX();
        days = DateUtil.getMonthDays(year, month - 1);
        firstDay = DateUtil.getFirstDayWeek17(year, month - 1);
        daysLiang = days;
        daysLiang--;

        list.clear();

        int[] is1 = new int[7];
        for (int i = 0; i < (8 - firstDay); i++) {
            is1[firstDay - 1 + i] = days - daysLiang;
            daysLiang--;
            if (day == is1[firstDay - 1 + i]) {
                selectPosition = 0;
            }
        }
        list.add(is1);
        int[] is2 = new int[7];
        for (int i = 0; i < 7; i++) {
            is2[i] = days - daysLiang;
            daysLiang--;
            if (day == is2[i]) {
                selectPosition = 1;
            }
        }
        list.add(is2);

        int[] is3 = new int[7];
        for (int i = 0; i < 7; i++) {
            is3[i] = days - daysLiang;
            daysLiang--;
            if (day == is3[i]) {
                selectPosition = 2;
            }
        }
        list.add(is3);

        int[] is4 = new int[7];
        for (int i = 0; i < 7; i++) {
            is4[i] = days - daysLiang;
            daysLiang--;
            if (day == is4[i]) {
                selectPosition = 3;
            }
            if (daysLiang < 0) {
                list.add(is4);
                return;
            }

        }
        list.add(is4);


        int[] is5 = new int[7];
        for (int i = 0; i < 7; i++) {
            is5[i] = days - daysLiang;
            daysLiang--;
            if (day == is5[i]) {
                selectPosition = 4;
            }
            if (daysLiang < 0) {
                list.add(is5);
                return;
            }
        }

        list.add(is5);
        if (daysLiang < 0)
            return;

        int[] is6 = new int[7];
        for (int i = 0; i < 7; i++) {
            is6[i] = days - daysLiang;
            daysLiang--;
            if (day == is6[i]) {
                selectPosition = 5;
            }
            if (daysLiang < 0) {
                list.add(is6);
                return;
            }
        }
        list.add(is6);


    }

    private void getSumNextLast() {
        sdaysLiang = 0;
        for (int i = 0; i < 7; i++) {

            if (list.get(0)[i] == 0) {
                sdaysLiang++;
            }

        }
        for (int j = 0; j < sdaysLiang; j++) {

            list.get(0)[j] = sdays - sdaysLiang + 1 + j;

        }
        xdaysLiang = 0;
        for (int i = 0; i < 7; i++) {

            if (list.get(list.size() - 1)[i] == 0) {
                xdaysLiang++;
            }

        }

        int k = xdaysLiang;
        for (int j = 0; j < xdaysLiang; j++) {

            int p = 6 - j;

            list.get(list.size() - 1)[p] = k;
            k--;

        }

    }

    private void init() {
        getSum();
        getSumNextLast();
        hasHead = false;
        hasFoot = false;

        if (layoutContent == null) {
            layoutContent = LayoutInflater.from(mContext).inflate(R.layout.layout_select_week, null);
            listView = (ListView) layoutContent.findViewById(R.id.list_view);
            title = (TextView) layoutContent.findViewById(R.id.titile);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            title.setText(month + "月");
            popupWindow = new PopupWindow(layoutContent, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
            popupWindow.setTouchable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());

            layoutContent.findViewById(R.id.imgup).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    year = syear;
                    month = smonth;
                    selectPosition = -1;
                    if (year == yearData && month == monthData) {
                        day = dayData;
                    } else {
                        day = -1;
                    }
                    init();
                }
            });
            layoutContent.findViewById(R.id.imgdown).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    year = xyear;
                    month = xmonth;
                    selectPosition = -1;
                    if (year == yearData && month == monthData) {
                        day = dayData;
                    } else {
                        day = -1;
                    }
                    init();
                }
            });
            layoutContent.findViewById(R.id.bottom).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });
        } else {

            title.setText(month + "月");
            adapter.notifyDataSetChanged();
        }

    }


    public void diss() {

        popupWindow.dismiss();

    }

    public void show(int year, int month, int day, View targetView, PopupWindow.OnDismissListener onDismissListener) {
        popupWindow.showAsDropDown(targetView);
        this.year = year;
        this.month = month;
        this.day = day;
        this.dayData = day;
        this.yearData = year;
        this.monthData = month;
        init();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
            }
        });
        if (onDismissListener != null)
            popupWindow.setOnDismissListener(onDismissListener);
        listView.setSelection(selectPosition);
    }

    BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.item_select_week, null);

                viewHolder.mImageViewBg = (ImageView) view.findViewById(R.id.bg);
                viewHolder.ts[0] = (TextView) view.findViewById(R.id.t1);
                viewHolder.ts[1] = (TextView) view.findViewById(R.id.t2);
                viewHolder.ts[2] = (TextView) view.findViewById(R.id.t3);
                viewHolder.ts[3] = (TextView) view.findViewById(R.id.t4);
                viewHolder.ts[4] = (TextView) view.findViewById(R.id.t5);
                viewHolder.ts[5] = (TextView) view.findViewById(R.id.t6);
                viewHolder.ts[6] = (TextView) view.findViewById(R.id.t7);


                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //selectPosition = i;
                    dayData = list.get(i)[0];
                    monthData = month;
                    yearData = year;
                    if (i == 0 && hasHead) {
                        if (monthData == 1) {
                            monthData = 12;
                            yearData--;

                        } else {
                            monthData--;
                        }

                    }
                    if (i == list.size() - 1 && hasFoot) {
                        if (monthData == 12) {
                            monthData = 1;
                            yearData++;

                        } else {
                            monthData++;
                        }

                    }
                    onSelectItemClickListencer.onSelectItemClick(view, i);
                    popupWindow.dismiss();
                }
            });

            for (int j = 0; j < 7; j++) {
                viewHolder.ts[j].setAlpha(1f);
                viewHolder.ts[j].setText(list.get(i)[j] + "");

            }
            if (i == 0) {
                for (int j = 0; j < 7; j++) {
                    if (list.get(i)[j] > 10) {
                        hasHead = true;
                        viewHolder.ts[j].setAlpha(0.5f);
                    }

                }
            }
            if (i == list.size() - 1) {
                for (int j = 0; j < 7; j++) {
                    if (list.get(i)[j] < 10) {
                        hasFoot = true;
                        viewHolder.ts[j].setAlpha(0.5f);
                    }

                }

            }


            if (i == selectPosition) {

                viewHolder.mImageViewBg.setVisibility(View.VISIBLE);


            } else {
                viewHolder.mImageViewBg.setVisibility(View.INVISIBLE);

            }
            return view;
        }
    };

    public interface OnSelectItemClickListencer {
        void onSelectItemClick(View view, int position);
    }

    class ViewHolder {
        // public TextView tvName;
        //public ImageView imgView;
        ImageView mImageViewBg;
        TextView[] ts = new TextView[7];
    }

}


