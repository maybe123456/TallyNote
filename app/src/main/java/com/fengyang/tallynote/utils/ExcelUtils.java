package com.fengyang.tallynote.utils;

import android.content.Context;

import com.fengyang.tallynote.MyApp;
import com.fengyang.tallynote.model.DayNote;
import com.fengyang.tallynote.model.IncomeNote;
import com.fengyang.tallynote.model.MonthNote;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelUtils {

    private static String[] dayTitle = {"消费用途", "消费支出（元）", "消费备注", "消费时间"};

    private static String[] monthTitle = {"上次结余（元）", "本次支出（元）", "本次工资（元）", "本次收益（元）", "家用补贴（元）", "本次结余（元）", "实际结余（元）",
            "月结期间", "月结备注", "记录时间"};

    private static String[] incomeTitle = {"投入金额(万元)", "预期年化（%）", "投资期限（天）", "投资时期", "拟日收益（元/万天）", "最终收益（元）",
            "最终提现（元）", "提现去处", "完成状态", "投资备注", "记录时间"};

    private static List<DayNote> dayNotes;
    private static List<MonthNote> monthNotes;
    private static List<IncomeNote> incomeNotes;

    /**
     * 日账单表
     *
     * @param callBackExport
     */
    public static void exportDayNote(ICallBackExport callBackExport) {
        try {
            File file = new File(FileUtils.dirPath + "/daynote_" + DateUtils.formatDate4fileName() + ".xls");
            if (!file.exists()) file.createNewFile();
            WritableWorkbook writebook = Workbook.createWorkbook(file);

            // 创建工作表
            WritableSheet day_sheet = writebook.createSheet("日账单", 0);

            //添加表头
            for (int i = 0; i < dayTitle.length; i++) {
                day_sheet.addCell(new Label(i, 0, dayTitle[i]));//列，行
            }
            //写入数据
            writeSheet(MyApp.DAY, day_sheet);
            writebook.write();
            writebook.close();
            if (callBackExport != null) callBackExport.callback(true, file.getAbsolutePath());

        } catch (Exception e) {
            if (callBackExport != null) callBackExport.callback(false, null);
            LogUtils.i("Exception", e.toString());
        }

    }

    /**
     * 月账单表
     *
     * @param callBackExport
     */
    public static void exportMonthNote(ICallBackExport callBackExport) {
        try {

            File file = new File(FileUtils.dirPath + "/monthnote_" + DateUtils.formatDate4fileName() + ".xls");
            if (!file.exists()) file.createNewFile();
            WritableWorkbook writebook = Workbook.createWorkbook(file);


            // 创建工作表
            WritableSheet month_sheet = writebook.createSheet("月账单", 0);

            //添加表头
            for (int i = 0; i < monthTitle.length; i++) {
                month_sheet.addCell(new Label(i, 0, monthTitle[i]));//列，行
            }

            //写入数据
            writeSheet(MyApp.MONTH, month_sheet);
            writebook.write();
            writebook.close();

            if (callBackExport != null) callBackExport.callback(true, file.getAbsolutePath());

        } catch (Exception e) {
            if (callBackExport != null) callBackExport.callback(false, null);
            LogUtils.i("Exception", e.toString());
        }

    }

    /**
     * 理财记录表
     *
     * @param callBackExport
     */
    public static void exportIncomeNote(ICallBackExport callBackExport) {
        try {
            File file = new File(FileUtils.dirPath + "/incomenote_" + DateUtils.formatDate4fileName() + ".xls");
            if (!file.exists()) file.createNewFile();
            WritableWorkbook writebook = Workbook.createWorkbook(file);

            // 创建工作表
            WritableSheet income_sheet = writebook.createSheet("理财记录", 0);

            //添加表头
            for (int i = 0; i < incomeTitle.length; i++) {
                income_sheet.addCell(new Label(i, 0, incomeTitle[i]));//列，行
            }

            //写入数据
            writeSheet(MyApp.INCOME, income_sheet);
            writebook.write();
            writebook.close();

            if (callBackExport != null) callBackExport.callback(true, file.getAbsolutePath());

        } catch (Exception e) {
            if (callBackExport != null) callBackExport.callback(false, null);
            LogUtils.i("Exception", e.toString());
        }

    }

    /**
     * 导出所有账单到一个Excel中
     *
     * @param callBackExport
     */
    public static void exportAll(ICallBackExport callBackExport) {
        try {

            File file = new File(FileUtils.dirPath + "/tallynote_" + DateUtils.formatDate4fileName() + ".xls");
            if (!file.exists()) file.createNewFile();
            WritableWorkbook writebook = Workbook.createWorkbook(file);

            //初始化日账单工作表
            WritableSheet day_sheet = writebook.createSheet("日账单", 0);
            for (int i = 0; i < dayTitle.length; i++) {
                day_sheet.addCell(new Label(i, 0, dayTitle[i]));//列，行
            }

            //初始化月账单工作表
            WritableSheet month_sheet = writebook.createSheet("月账单", 1);
            for (int i = 0; i < monthTitle.length; i++) {
                month_sheet.addCell(new Label(i, 0, monthTitle[i]));//列，行
            }

            //初始化理财记录工作表
            WritableSheet income_sheet = writebook.createSheet("理财记录", 2);
            for (int i = 0; i < incomeTitle.length; i++) {
                income_sheet.addCell(new Label(i, 0, incomeTitle[i]));//列，行
            }

            writeSheet(MyApp.DAY, day_sheet);
            writeSheet(MyApp.MONTH, month_sheet);
            writeSheet(MyApp.INCOME, income_sheet);
            writebook.write();//只能执行一次
            writebook.close();

            if (callBackExport != null) callBackExport.callback(true, file.getAbsolutePath());

        } catch (Exception e) {
            if (callBackExport != null) callBackExport.callback(false, null);
            LogUtils.i("Exception", e.toString());
        }

    }

    /**
     * 写入数据
     *
     * @param type
     * @param sheet
     */
    private static void writeSheet(int type, WritableSheet sheet) {
        try {
            String tag = "writeSheet";
            dayNotes = MyApp.utils.getDayNotes();
            monthNotes = MyApp.utils.getMonNotes();
            incomeNotes = MyApp.utils.getIncomes();

            switch (type) {
                case MyApp.DAY:
                    LogUtils.i(tag, dayNotes.size() + "---" + dayNotes.toString());
                    if (dayNotes.size() > 0) {
                        for (int i = 0; i < dayNotes.size(); i++) {
                            sheet.addCell(new Label(0, i + 1, dayNotes.get(i).getUsage()));
                            sheet.addCell(new Label(1, i + 1, dayNotes.get(i).getMoney()));
                            sheet.addCell(new Label(2, i + 1, dayNotes.get(i).getRemark()));
                            sheet.addCell(new Label(3, i + 1, dayNotes.get(i).getTime()));
                        }
                    }
                    break;

                case MyApp.MONTH:
                    LogUtils.i(tag, monthNotes.size() + "---" + monthNotes.toString());
                    if (monthNotes.size() > 0) {
                        for (int i = 0; i < monthNotes.size(); i++) {
                            sheet.addCell(new Label(0, i + 1, monthNotes.get(i).getLast_balance()));
                            sheet.addCell(new Label(1, i + 1, monthNotes.get(i).getPay()));
                            sheet.addCell(new Label(2, i + 1, monthNotes.get(i).getSalary()));
                            sheet.addCell(new Label(3, i + 1, monthNotes.get(i).getIncome()));
                            sheet.addCell(new Label(4, i + 1, monthNotes.get(i).getHomeuse()));
                            sheet.addCell(new Label(5, i + 1, monthNotes.get(i).getBalance()));
                            sheet.addCell(new Label(6, i + 1, monthNotes.get(i).getActual_balance()));
                            sheet.addCell(new Label(7, i + 1, monthNotes.get(i).getDuration()));
                            sheet.addCell(new Label(8, i + 1, monthNotes.get(i).getRemark()));
                            sheet.addCell(new Label(9, i + 1, monthNotes.get(i).getTime()));
                        }
                    }
                    break;

                case MyApp.INCOME:
                    LogUtils.i(tag, incomeNotes.size() + "---" + incomeNotes.toString());
                    if (incomeNotes.size() > 0) {
                        for (int i = 0; i < incomeNotes.size(); i++) {
                            sheet.addCell(new Label(0, i + 1, incomeNotes.get(i).getMoney()));
                            sheet.addCell(new Label(1, i + 1, incomeNotes.get(i).getIncomeRatio()));
                            sheet.addCell(new Label(2, i + 1, incomeNotes.get(i).getDays()));
                            sheet.addCell(new Label(3, i + 1, incomeNotes.get(i).getDurtion()));
                            sheet.addCell(new Label(4, i + 1, incomeNotes.get(i).getDayIncome() + ""));
                            sheet.addCell(new Label(5, i + 1, incomeNotes.get(i).getFinalIncome()));
                            sheet.addCell(new Label(6, i + 1, incomeNotes.get(i).getFinalCash()));
                            sheet.addCell(new Label(7, i + 1, incomeNotes.get(i).getFinalCashGo()));
                            if (incomeNotes.get(i).getFinished() == 0)
                                sheet.addCell(new Label(8, i + 1, "未完结"));
                            else sheet.addCell(new Label(8, i + 1, "已完结"));
                            sheet.addCell(new Label(9, i + 1, incomeNotes.get(i).getRemark()));
                            sheet.addCell(new Label(10, i + 1, incomeNotes.get(i).getTime()));
                        }
                    }
                    break;

            }
        } catch (Exception e) {
        }
    }

    /**
     * 导出结果回调
     */
    public interface ICallBackExport {
        void callback(boolean sucess, String fileName);
    }

    /**
     * 导入本地Excel文件到数据库
     */
    public static void importExcel(Context context, String filePath) {
        String tag = "importExcel";
        try {
            Workbook book = Workbook.getWorkbook(new File(filePath));
            int num = book.getNumberOfSheets();
            LogUtils.i(tag, "表单数：" + num);
            for (int i = 0; i < num; i++) {
                Sheet sheet = book.getSheet(i);
                int rows = sheet.getRows();
                int cols = sheet.getColumns();//列
                String sheetName = sheet.getName();
                LogUtils.i(tag, "第" + (i + 1) + "个表单名：" + sheetName + ",表单行数：" + rows + "表单列数：" + cols);

                if (sheetName.contains("日")) { //日账单解析
                    dayNotes = new ArrayList<>();
                    dayNotes.clear();
                    for (int j = 1; j < rows; j ++) {//行
                        dayNotes.add(new DayNote(
                                sheet.getCell(0, j).getContents(),
                                sheet.getCell(1, j).getContents(),
                                sheet.getCell(2, j).getContents(),
                                sheet.getCell(3, j).getContents()));
                    }
                    LogUtils.i(tag, dayNotes.size() + "---" + dayNotes.toString());
                    if (dayNotes.size() > 0) if (MyApp.utils.newDNotes(dayNotes)) {
                        StringUtils.show1Toast(context, "导入成功！日账单新增" + incomeNotes.size() + "条数据");
                    } else StringUtils.show1Toast(context, "导入失败！原因：Excel文件结构不符！请检查后重试");

                } else if (sheetName.contains("月")) { //月账单解析
                    monthNotes = new ArrayList<>();
                    monthNotes.clear();
                    for (int j = 1; j < rows; j ++) {//行
                        monthNotes.add(new MonthNote(
                                sheet.getCell(0, j).getContents(),
                                sheet.getCell(1, j).getContents(),
                                sheet.getCell(2, j).getContents(),
                                sheet.getCell(3, j).getContents(),
                                sheet.getCell(4, j).getContents(),
                                sheet.getCell(5, j).getContents(),
                                sheet.getCell(6, j).getContents(),
                                sheet.getCell(7, j).getContents(),
                                sheet.getCell(8, j).getContents(),
                                sheet.getCell(9, j).getContents()));
                    }
                    LogUtils.i(tag, monthNotes.size() + "---" + monthNotes.toString());
                    if (monthNotes.size() > 0) if (MyApp.utils.newMNotes(monthNotes)) {
                        StringUtils.show1Toast(context, "导入成功！月账单新增" + incomeNotes.size() + "条数据");
                    } else StringUtils.show1Toast(context, "导入失败！原因：Excel文件结构不符！请检查后重试");
                    
                } else if (sheetName.contains("理财")) { //理财记录解析
                    incomeNotes = new ArrayList<>();
                    incomeNotes.clear();
                    for (int j = 1; j < rows; j ++) {//行
                        incomeNotes.add(new IncomeNote(
                                sheet.getCell(0, j).getContents(),
                                sheet.getCell(1, j).getContents(),
                                sheet.getCell(2, j).getContents(),
                                sheet.getCell(3, j).getContents(),
                                sheet.getCell(4, j).getContents(),
                                sheet.getCell(5, j).getContents(),
                                sheet.getCell(6, j).getContents(),
                                sheet.getCell(7, j).getContents(),
                                (sheet.getCell(8, j).getContents().contains("已")) ? 1 : 0,
                                sheet.getCell(9, j).getContents(),
                                sheet.getCell(10, j).getContents()));
                    }
                    LogUtils.i(tag, incomeNotes.size() + "---" + incomeNotes.toString());
                    if (incomeNotes.size() > 0) if (MyApp.utils.newINotes(incomeNotes)) {
                        StringUtils.show1Toast(context, "导入成功！理财记录新增" + incomeNotes.size() + "条数据");
                    } else StringUtils.show1Toast(context, "导入失败！原因：Excel文件结构不符！请检查后重试");
                } else {
                    StringUtils.show1Toast(context, "导入失败！原因：非本APP导出的文件！请检查后重试");
                }

            }
            book.close();

        } catch (Exception e) {
            System.out.println(e);
            StringUtils.show1Toast(context, "导入失败！原因：非Excel格式的文件！请检查后重试");
        }
    }
}