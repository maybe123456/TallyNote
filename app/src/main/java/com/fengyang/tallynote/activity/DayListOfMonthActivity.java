package com.fengyang.tallynote.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fengyang.tallynote.R;
import com.fengyang.tallynote.adapter.DayNoteAdapter;
import com.fengyang.tallynote.database.DayNoteDao;
import com.fengyang.tallynote.database.MonthNoteDao;
import com.fengyang.tallynote.model.DayNote;
import com.fengyang.tallynote.model.MonthNote;
import com.fengyang.tallynote.utils.StringUtils;
import com.fengyang.tallynote.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuhuihui on 2017/6/27.
 */
public class DayListOfMonthActivity extends BaseActivity {

    private String duration;
    private Spinner spinner;
    private ListView listView;

    private List<DayNote> dayNotes;
    private List<DayNote> list = new ArrayList<>();
    private DayNoteAdapter dayNoteAdapter;

    private TextView info, all, consume, account_out, account_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        duration = getIntent().getStringExtra("duration");
        setContentView(duration, R.layout.activity_day_list);

        //数据
        final List<String> durations = new ArrayList<>();
        List<MonthNote> monthNotes = MonthNoteDao.getMonthNotes();
        for (int i = 0; i < monthNotes.size(); i++) {
            durations.add(monthNotes.get(i).getDuration());
        }
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setVisibility(View.VISIBLE);
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, durations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        for (int i = 0; i < durations.size(); i++) {
            if (durations.get(i).equals(duration)) spinner.setSelection(i);
        }
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        duration = durations.get(position);
                        getAll();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );

        info = (TextView) findViewById(R.id.info);
        all = (TextView) findViewById(R.id.all);
        consume = (TextView) findViewById(R.id.consume);
        account_out = (TextView) findViewById(R.id.account_out);
        account_in = (TextView) findViewById(R.id.account_in);

        listView = (ListView) findViewById(R.id.listView);
        listView.setEmptyView(findViewById(R.id.emptyView));

        getAll();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.all:
                getAll();
                break;
            case R.id.consume:
                getConsume();
                break;
            case R.id.account_out:
                getAccountOut();
                break;
            case R.id.account_in:
                getAccountIn();
                break;
        }
    }

    /**
     * 总账单记录
     */
    private void getAll() {
        all.setTextColor(Color.RED);
        consume.setTextColor(Color.GRAY);
        account_out.setTextColor(Color.GRAY);
        account_in.setTextColor(Color.GRAY);
        dayNotes = DayNoteDao.getDayNotes4History(duration);
        if (dayNotes.size() > 0) {
            info.setText("账单记录：" + dayNotes.size()
                    + "，支出 + 转账 - 转入：" + StringUtils.showPrice(DayNote.getAllSum(duration) + ""));
            dayNoteAdapter = new DayNoteAdapter(activity, dayNotes, false);
            listView.setAdapter(dayNoteAdapter);
        } else {
            ToastUtils.showWarningLong(context, duration + "时段内未找到日账单明细！\n可能是首次月账！");
            finish();
        }
    }

    /**
     * 支出账单记录
     */
    private void getConsume() {
        all.setTextColor(Color.GRAY);
        consume.setTextColor(Color.RED);
        account_out.setTextColor(Color.GRAY);
        account_in.setTextColor(Color.GRAY);
        list.clear();
        Double sum = 0.00;
        for (int i = 0; i < dayNotes.size(); i++) {
            if (dayNotes.get(i).getUseType() == DayNote.consume) {
                list.add(dayNotes.get(i));
                sum += Double.parseDouble(dayNotes.get(i).getMoney());
            }
        }
        info.setText("支出记录：" + list.size() + "，支出金额：" + StringUtils.showPrice(sum + ""));
        dayNoteAdapter = new DayNoteAdapter(activity, list, false);
        listView.setAdapter(dayNoteAdapter);
    }

    /**
     * 转账记录
     */
    private void getAccountOut() {
        all.setTextColor(Color.GRAY);
        consume.setTextColor(Color.GRAY);
        account_out.setTextColor(Color.RED);
        account_in.setTextColor(Color.GRAY);
        list.clear();
        Double sum = 0.00;
        for (int i = 0; i < dayNotes.size(); i++) {
            if (dayNotes.get(i).getUseType() == DayNote.account_out) {
                list.add(dayNotes.get(i));
                sum += Double.parseDouble(dayNotes.get(i).getMoney());
            }
        }
        info.setText("转账记录：" + list.size() + "，转账金额：" + StringUtils.showPrice(sum + ""));
        dayNoteAdapter = new DayNoteAdapter(activity, list, false);
        listView.setAdapter(dayNoteAdapter);
    }

    /**
     * 转入记录
     */
    private void getAccountIn() {
        all.setTextColor(Color.GRAY);
        consume.setTextColor(Color.GRAY);
        account_out.setTextColor(Color.GRAY);
        account_in.setTextColor(Color.RED);
        list.clear();
        Double sum = 0.00;
        for (int i = 0; i < dayNotes.size(); i++) {
            if (dayNotes.get(i).getUseType() == DayNote.account_in) {
                list.add(dayNotes.get(i));
                sum += Double.parseDouble(dayNotes.get(i).getMoney());
            }
        }
        info.setText("转入记录： " + list.size() + "，转入金额：" + StringUtils.showPrice(sum + ""));
        dayNoteAdapter = new DayNoteAdapter(activity, list, false);
        listView.setAdapter(dayNoteAdapter);
    }

}