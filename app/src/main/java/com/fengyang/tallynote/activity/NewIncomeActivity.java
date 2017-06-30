package com.fengyang.tallynote.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.fengyang.tallynote.MyApp;
import com.fengyang.tallynote.R;
import com.fengyang.tallynote.model.Income;
import com.fengyang.tallynote.utils.DateUtils;
import com.fengyang.tallynote.utils.DialogUtils;
import com.fengyang.tallynote.utils.LogUtils;
import com.fengyang.tallynote.utils.StringUtils;

import java.util.Calendar;

public class NewIncomeActivity extends BaseActivity{

	private EditText moneyEt, incomeRatioEt, daysEt, dayIncomeEt, finalIncomeEt, remarkEt;
	private TextView durationEt;
	private Calendar calendar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView("记理财", R.layout.activity_income);

		initView();
	}

	private void initView() {
		moneyEt = (EditText) findViewById(R.id.moneyEt);
		incomeRatioEt = (EditText) findViewById(R.id.incomeRatioEt);
		daysEt = (EditText) findViewById(R.id.daysEt);
		dayIncomeEt = (EditText) findViewById(R.id.dayIncomeEt);
		finalIncomeEt = (EditText) findViewById(R.id.finalIncomeEt);
		durationEt = (TextView) findViewById(R.id.durtionEt);
		remarkEt = (EditText) findViewById(R.id.remarkEt);

		calendar = Calendar.getInstance();

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if(v.getId() == R.id.durtionEt) {
			StringUtils.show1Toast(activity, "请选择投资起始日期");
			new DatePickerDialog(activity, mdateListener,
					calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH)).show();

		} else if(v.getId() == R.id.commitNote) {
			String money = StringUtils.formatePrice(moneyEt.getText().toString());
			String incomeRatio = StringUtils.formatePrice(incomeRatioEt.getText().toString());
			String days = StringUtils.formatePrice(daysEt.getText().toString());
			String dayIncome = StringUtils.formatePrice(dayIncomeEt.getText().toString());
			String finalIncome = StringUtils.formatePrice(finalIncomeEt.getText().toString());
			String duration = durationEt.getText().toString();
			String remark = remarkEt.getText().toString();

			if (! TextUtils.isEmpty(money) && !TextUtils.isEmpty(incomeRatio) &&
					! TextUtils.isEmpty(days) && !TextUtils.isEmpty(dayIncome) &&
					! TextUtils.isEmpty(finalIncome) && ! TextUtils.isEmpty(duration)) {
				final Income income = new Income(money, incomeRatio, days, duration, dayIncome, finalIncome,
						 null, null, 0, remark, DateUtils.formatDateTime());
				LogUtils.i("commit", income.toString());
				DialogUtils.showMsgDialog(activity, "新增理财",
						"投入金额:" + StringUtils.showPrice(income.getMoney()) + "\n" +
								"预期年化:" + income.getIncomeRatio() + "\n" +
								"投资期限:" + income.getDays() + "\n" +
								"投资时期:" + income.getDurtion() + "\n" +
								"拟日收益:" + StringUtils.showPrice(income.getDayIncome()) + "\n" +
								"最终收益:" + StringUtils.showPrice(income.getFinalIncome()),
						new DialogUtils.DialogListener(){
							@Override
							public void onClick(View v) {
								super.onClick(v);
								if (MyApp.utils.newIncome(income)) {
									StringUtils.show1Toast(activity, "新增理财成功！");
									finish();
								} else StringUtils.show1Toast(activity, "新增理财失败！");
							}
						}, new DialogUtils.DialogListener(){
							@Override
							public void onClick(View v) {
								super.onClick(v);
							}
						});
			} else {
				StringUtils.show1Toast(activity, "请完善必填信息！");
			}
		}
	}

	private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			monthOfYear = monthOfYear + 1;
			String month;
			if (monthOfYear < 10) month = "0" + monthOfYear;
			else month = "" + monthOfYear;
			String day;
			if (dayOfMonth < 10) day = "0" + dayOfMonth;
			else day = "" + dayOfMonth;

			String curDuration = durationEt.getText().toString();
			if (curDuration.length() > 0) {
				if (curDuration.endsWith("-")) {
					durationEt.setText(curDuration + year + month + day);
				} else {
					StringUtils.show1Toast(activity, "请选择投资终止日期");
					durationEt.setText(year + month + day + "-");
					new DatePickerDialog(activity, mdateListener,
							calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
							calendar.get(Calendar.DAY_OF_MONTH)).show();
				}
			} else {
				StringUtils.show1Toast(activity, "请选择投资终止日期");
				durationEt.setText(year + month + day + "-");
				new DatePickerDialog(activity, mdateListener,
						calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
						calendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		}
	};



}
