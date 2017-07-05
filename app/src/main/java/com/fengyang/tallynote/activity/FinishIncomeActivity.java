package com.fengyang.tallynote.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fengyang.tallynote.MyApp;
import com.fengyang.tallynote.R;
import com.fengyang.tallynote.model.IncomeNote;
import com.fengyang.tallynote.utils.DialogUtils;
import com.fengyang.tallynote.utils.LogUtils;
import com.fengyang.tallynote.utils.StringUtils;

public class FinishIncomeActivity extends BaseActivity{

	private IncomeNote incomeNote;
	private EditText finalCashEt, finalCashGoEt;
	private TextView income_info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView("完成理财", R.layout.activity_finish_income);

		initView();
	}

	private void initView() {
		finalCashEt = (EditText) findViewById(R.id.finalCashEt);
		finalCashGoEt = (EditText) findViewById(R.id.finalCashGoEt);

		incomeNote = (IncomeNote) getIntent().getSerializableExtra("incomeNote");
		income_info = (TextView) findViewById(R.id.income_info);
		income_info.setText("投入金额： " + StringUtils.showPrice(incomeNote.getMoney())  +
				"\n预期年化： " + incomeNote.getIncomeRatio()  +
				"\n投资期限： " + incomeNote.getDays()  +
				"\n投资时期： " + incomeNote.getDurtion()  +
				"\n拟日收益： " + StringUtils.showPrice(incomeNote.getDayIncome())  +
				"\n最终收益： " + StringUtils.showPrice(incomeNote.getFinalIncome()) +
				"\n投资备注：" + incomeNote.getRemark());
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if(v.getId() == R.id.commitNote) {
			String finalCash = StringUtils.formatePrice(finalCashEt.getText().toString());
			String finalCashGo = finalCashGoEt.getText().toString();

			if (! TextUtils.isEmpty(finalCash) && !TextUtils.isEmpty(finalCashGo)) {
				incomeNote.setFinalCash(finalCash);
				incomeNote.setFinalCashGo(finalCashGo);
				LogUtils.i("commit", incomeNote.toString());
				DialogUtils.showMsgDialog(activity, "完成理财",
						"投入金额：" + StringUtils.showPrice(incomeNote.getMoney())  +
								"\n预期年化：" + incomeNote.getIncomeRatio()  +
								" %\n投资期限：" + incomeNote.getDays()  +
								" 天\n投资时期：" + incomeNote.getDurtion()  +
								" \n拟日收益：" + StringUtils.showPrice(incomeNote.getDayIncome())  +
								" 元万/天\n最终收益：" + StringUtils.showPrice(incomeNote.getFinalIncome()) +
								"\n投资备注：" + incomeNote.getRemark()  +
								"\n最终提现：" + StringUtils.showPrice(incomeNote.getFinalCash())  +
								"\n提现去处：" + incomeNote.getFinalCashGo(),
						new DialogUtils.DialogListener(){
							@Override
							public void onClick(View v) {
								super.onClick(v);
								if (MyApp.utils.finishIncome(incomeNote)) {
									StringUtils.show1Toast(activity, "完成理财成功！");
									finish();
								} else StringUtils.show1Toast(activity, "完成理财失败！");
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



}