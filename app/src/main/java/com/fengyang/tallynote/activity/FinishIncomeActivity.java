package com.fengyang.tallynote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fengyang.tallynote.R;
import com.fengyang.tallynote.database.IncomeNoteDao;
import com.fengyang.tallynote.model.IncomeNote;
import com.fengyang.tallynote.utils.ContansUtils;
import com.fengyang.tallynote.utils.DialogListener;
import com.fengyang.tallynote.utils.DialogUtils;
import com.fengyang.tallynote.utils.ExcelUtils;
import com.fengyang.tallynote.utils.LogUtils;
import com.fengyang.tallynote.utils.StringUtils;
import com.fengyang.tallynote.utils.ToastUtils;

/**
 * 完成理财
 */
public class FinishIncomeActivity extends BaseActivity {

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

        //设置右上角“完成理财”按钮点击事件
        setRightBtnListener("完成理财", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String finalCash = StringUtils.formatePrice(finalCashEt.getText().toString());
                String finalCashGo = finalCashGoEt.getText().toString();

                if (!TextUtils.isEmpty(finalCash) && !TextUtils.isEmpty(finalCashGo)) {
                    incomeNote.setFinalCash(finalCash);
                    incomeNote.setFinalCashGo(finalCashGo);
                    LogUtils.i("commit", incomeNote.toString());
                    DialogUtils.showMsgDialog(activity, "完成理财\n" +
                                    "投入金额：" + StringUtils.showPrice(incomeNote.getMoney()) +
                                    "\n预期年化：" + incomeNote.getIncomeRatio() +
                                    " %\n投资期限：" + incomeNote.getDays() +
                                    " 天\n投资时段：" + incomeNote.getDurtion() +
                                    " \n拟日收益：" + incomeNote.getDayIncome() +
                                    " 元万/天\n最终收益：" + StringUtils.showPrice(incomeNote.getFinalIncome()) +
                                    "\n投资说明：" + incomeNote.getRemark() +
                                    "\n最终提现：" + StringUtils.showPrice(incomeNote.getFinalCash()) +
                                    "\n提现去处：" + incomeNote.getFinalCashGo(),
                            "提交", new DialogListener() {
                                @Override
                                public void onClick() {
                                    if (IncomeNoteDao.finishIncome(incomeNote)) {
                                        ToastUtils.showSucessLong(activity, "完成理财成功！");
                                        ExcelUtils.exportIncomeNote(null);
                                        sendBroadcast(new Intent(ContansUtils.ACTION_INCOME));
                                        finish();
                                    } else ToastUtils.showErrorLong(activity, "完成理财失败！");
                                }
                            },
                            "返回查看", new DialogListener() {
                                @Override
                                public void onClick() {
                                }
                            });
                } else {
                    ToastUtils.showToast(context, true, "请完善必填信息！");
                }
            }
        });

        incomeNote = (IncomeNote) getIntent().getSerializableExtra("incomeNote");
        income_info = (TextView) findViewById(R.id.income_info);
        income_info.setText("理财ID：" + StringUtils.showPrice(incomeNote.getId()) +
                "投入金额：" + StringUtils.showPrice(incomeNote.getMoney()) +
                "\n预期年化：" + incomeNote.getIncomeRatio() +
                " %\n投资期限：" + incomeNote.getDays() +
                " 天\n投资时段：" + incomeNote.getDurtion() +
                " \n拟日收益：" + incomeNote.getDayIncome() +
                " 元万/天\n最终收益：" + StringUtils.showPrice(incomeNote.getFinalIncome()) +
                "\n投资说明：" + incomeNote.getRemark());
    }

}
