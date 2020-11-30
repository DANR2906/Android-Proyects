package com.example.gastosanuales;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner1;
    private String[] months = new String[12];



    private TextView TVError;

    private BarChart BCGrafic;
    private String[] weeks;
    private int[] sale;


    private int[] color = new int[]{Color.CYAN, Color.rgb(252,160,9), Color.rgb(249,82,80), Color.rgb(253,246, 15), Color.GREEN};


    private boolean TVError_Validate;


    private LinearLayout expenseList;

    private  LinearLayout.LayoutParams lParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT );

    private  LinearLayout.LayoutParams paramsIcons = new LinearLayout.LayoutParams(120,150 );

    private  LinearLayout.LayoutParams lParams4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();


        initializeComponents();


    }

    @Override
    protected void onStart() {
        super.onStart();


        initializeComponents();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            confirmcloseApplication();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //Prueba
    private Chart getSameChart(Chart chart, String description, int textColor, int background, int animateY){
        chart.getDescription().setText(description);
        chart.getDescription().setTextSize(15);
        chart.getDescription().setTextColor(textColor);
        chart.setBackgroundColor(background);
        chart.animateY(animateY);
        legend(chart);
        return chart;
    }

    private void legend(Chart chart){
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<LegendEntry> entries = new ArrayList<>();
        for(int i= 0; i < weeks.length; i++){
            LegendEntry entry = new LegendEntry();
            entry.formColor = color[i];
            entry.label = weeks[i];
            entries.add(entry);
        }

        legend.setCustom(entries);
    }

    private ArrayList<BarEntry> getBarEntries(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i= 0; i < sale.length; i++)
            entries.add(new BarEntry(i,sale[i]));
        return entries;

    }

    private void axisX(XAxis axis){
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(weeks));


    }

    private void axisLeft(YAxis axis){
        axis.setSpaceTop(30);
        axis.setAxisMinimum(0);

    }

    private void axisRight(YAxis axis){
        axis.setEnabled(true);
    }

    public void createCharts(){
        BCGrafic = (BarChart) getSameChart(BCGrafic, "", Color.RED, Color.WHITE, 2500 );
        BCGrafic.setDrawGridBackground(false);
        BCGrafic.setDrawBarShadow(false);
        BCGrafic.setData(getBarData());
        BCGrafic.invalidate();
        axisX(BCGrafic.getXAxis());
        axisLeft(BCGrafic.getAxisLeft());
        axisRight(BCGrafic.getAxisRight());
    }

    private DataSet getData(DataSet dataSet){
        dataSet.setColors(color);
        dataSet.setValueTextSize(Color.WHITE);
        dataSet.setValueTextSize(13);
        return dataSet;
    }

    private BarData getBarData(){
        BarDataSet barDataSet = (BarDataSet) getData(new BarDataSet(getBarEntries(),""));

        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.5f);
        return barData;
    }

    public void setSale(int month){

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "DBAccounting", null, 1);
        SQLiteDatabase database = admin.getReadableDatabase();

        Cursor row = database.rawQuery
                ("SELECT amountFirstWeek, amountSecondWeek, amountThirdWeek, amountFourthWeek, totalAmount " +
                        "FROM expenseSummary WHERE month='" + month + "'", null);
        if(row.moveToFirst()){
            double auxWeek1 = row.getDouble(0);
            double auxWeek2 = row.getDouble(1);
            double auxWeek3 = row.getDouble(2);
            double auxWeek4 = row.getDouble(3);
            double auxMonth = row.getDouble(4);

            sale = new int[]{(int)auxWeek1, (int)auxWeek2, (int)auxWeek3, (int)auxWeek4, (int)auxMonth};

        }else{
            sale = new int[]{0,0,0,0,0};
        }
        row.close();
        database.close();



    }
    //Prueba fin

    private void confirmcloseApplication() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_warning_black_50dp)
                .setTitle(getString(R.string.Main_Alert_APPClose))
                .setCancelable(false)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {// un listener que al pulsar, cierre la aplicacion
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        android.os.Process.killProcess(android.os.Process.myPid()); //Su funcion es algo similar a lo que se llama cuando se presiona el botón "Forzar Detención" o "Administrar aplicaciones", lo cuál mata la aplicación
                        //finish(); Si solo quiere mandar la aplicación a segundo plano
                    }
                }).show();
    }

    public void initializeComponents(){

        initializeMonths();

        spinner1 = findViewById(R.id.Main_SP_Months);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,  months);
        spinner1.setAdapter(adapter1);

        setSpinnerDefaultMonth();

        spinner1.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> spn,
                                               android.view.View v,
                                               int posicion,
                                               long id) {

                        setList(spinner1.getItemAtPosition(posicion).toString());
;
                    }
                    public void onNothingSelected(AdapterView<?> spn) {
                    }
                });

        lParams2.setMargins(0,30,0,0);
        paramsIcons.setMargins(0,20,0,0);
        lParams4.setMargins(10,0,0,0);


        expenseList = findViewById(R.id.Main_LN_ExpensesList);


        TVError = findViewById(R.id.Main_TV_Error);
        TVError.setVisibility(View.INVISIBLE);

        TVError_Validate = true;


        BCGrafic = findViewById(R.id.Main_BarChart_1);

        weeks = new String[]{"1", "2", "3", "4", "Total"};

        makeListbyMonth(getMonthPreference());


    }

    public void setSpinnerDefaultMonth(){
        String spinnerMonth = getMonthPreference();

        if(spinnerMonth.equals(months[0]))
                            spinner1.setSelection(0);

        if(spinnerMonth.equals(months[1]))
                            spinner1.setSelection(1);

        if(spinnerMonth.equals(months[2]))
                            spinner1.setSelection(2);

        if(spinnerMonth.equals(months[3]))
                            spinner1.setSelection(3);

        if(spinnerMonth.equals(months[4]))
                            spinner1.setSelection(4);

        if(spinnerMonth.equals(months[5]))
                            spinner1.setSelection(5);

        if(spinnerMonth.equals(months[6]))
                            spinner1.setSelection(6);

        if(spinnerMonth.equals(months[7]))
                            spinner1.setSelection(7);

        if(spinnerMonth.equals(months[8]))
                            spinner1.setSelection(8);

        if(spinnerMonth.equals(months[9]))
                         spinner1.setSelection(9);

        if(spinnerMonth.equals(months[10]))
                            spinner1.setSelection(10);

        if(spinnerMonth.equals(months[11]))
                            spinner1.setSelection(11);


    }

    public void startCalendar(View view){
        Intent calendar = new Intent(this, CalendarGrafics.class);
        startActivity(calendar);
    }

    public void startSaveExpenses(View view){
        Intent saveExpenses = new Intent(this, SaveExpences.class);
        startActivity(saveExpenses);
    }

    public void initializeMonths(){
        months[0] = getString(R.string.SEXP_Months_1);
        months[1] = getString(R.string.SEXP_Months_2);
        months[2] = getString(R.string.SEXP_Months_3);
        months[3] = getString(R.string.SEXP_Months_4);
        months[4] = getString(R.string.SEXP_Months_5);
        months[5] = getString(R.string.SEXP_Months_6);
        months[6] = getString(R.string.SEXP_Months_7);
        months[7] = getString(R.string.SEXP_Months_8);
        months[8] = getString(R.string.SEXP_Months_9);
        months[9] = getString(R.string.SEXP_Months_10);
        months[10] = getString(R.string.SEXP_Months_11);
        months[11] = getString(R.string.SEXP_Months_12);
    }

    public void makeListbyMonth(String montAux){

        //case 1: month 1
        if(montAux.equals(months[0])){
            setListByMonth(1);
        }
        //case 2: month 2
        if(montAux.equals(months[1])){
            setListByMonth(2);
        }
        //case 3: month 3
        if(montAux.equals(months[2])){
            setListByMonth(3);
        }
        //case 4: month 4
        if(montAux.equals(months[3])){
            setListByMonth(4);
        }
        //case 5: month 5
        if(montAux.equals(months[4])){
            setListByMonth(5);
        }//case 6: month 6
        if(montAux.equals(months[5])){
            setListByMonth(6);
        }//case 7: month 7
        if(montAux.equals(months[6])){
            setListByMonth(7);
        }//case 8: month 8
        if(montAux.equals(months[7])){
            setListByMonth(8);
        }//case 9: month 9
        if(montAux.equals(months[8])){
            setListByMonth(9);
        }
        //case 10: month 10
        if(montAux.equals(months[9])){
            setListByMonth(10);
        }
        //case 11: month 11
        if(montAux.equals(months[10])){
            setListByMonth(11);
        }
        //case 12: month 12
        if(montAux.equals(months[11])){
            setListByMonth(12);
        }



    }


    public void setList(String month){
        makeListbyMonth(month);
        setMonthPreference();
    }



    public void setMonthPreference(){
        SharedPreferences p = getSharedPreferences("monthPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = p.edit();
        editor.putString("ListMonth", getMonth());
        editor.commit();
    }

    public String getMonthPreference(){
        SharedPreferences p = getSharedPreferences("monthPreference", Context.MODE_PRIVATE);
        String month = p.getString("ListMonth", "");
        return month;
    }

    public String getMonth(){
        return spinner1.getSelectedItem().toString();
    }

    public void setListByMonth(int month){

        expenseList.removeAllViews();
        setListFirstWeek(month);
        setListSecondWeek(month);
        setListThirdWeek(month);
        setListFourthWeek(month);
        setSale(month);
        createCharts();

    }





    public void UpdateTVAccountingDB(int month, int week, double value){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "DBAccounting", null, 1);
        SQLiteDatabase database = admin.getReadableDatabase();
        SQLiteDatabase databaseW = admin.getWritableDatabase();

        Cursor row = database.rawQuery
                ("SELECT amountFirstWeek, amountSecondWeek, amountThirdWeek, amountFourthWeek, totalAmount " +
                "FROM expenseSummary WHERE month='" + month + "'", null);

        double auxWeek1 = 0;
        double auxWeek2 = 0;
        double auxWeek3 = 0;
        double auxWeek4 = 0;
        double auxMonth = 0;

        if(row.moveToFirst()){
             auxWeek1 = row.getDouble(0);
             auxWeek2 = row.getDouble(1);
             auxWeek3 = row.getDouble(2);
             auxWeek4 = row.getDouble(3);
             auxMonth = row.getDouble(4);

            switch (week){
                case 1:
                    auxWeek1 -= value;
                    auxMonth -= value;
                    break;
                case 2:
                    auxWeek2 -= value;
                    auxMonth -= value;
                    break;
                case 3:
                    auxWeek3 -= value;
                    auxMonth -= value;
                    break;
                case 4:
                    auxWeek4 -= value;
                    auxMonth -= value;
                    break;

            }


        }

        ContentValues update = new ContentValues();
        update.put("amountFirstWeek", auxWeek1);
        update.put("amountSecondWeek", auxWeek2);
        update.put("amountThirdWeek", auxWeek3);
        update.put("amountFourthWeek",auxWeek4);
        update.put("totalAmount",auxMonth);
        int upd = databaseW.update("expenseSummary", update, "month='" + month + "'", null);

        row.close();
        database.close();



    }





    public void setListFirstWeek(int month){

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this , "DBMonth" + month, null, 1);
        SQLiteDatabase dataBase = admin.getReadableDatabase();


        Cursor week1 = dataBase.query
                ("firstWeek",null,null,null,null,null, null);

        if(week1.moveToFirst()){

            TVError.setVisibility(View.INVISIBLE);
            TVError_Validate = false;
            week1.moveToFirst();

            do{

                String title = week1.getString(week1.getColumnIndex("expenseTitle"));
                String value = week1.getString(week1.getColumnIndex("expenseValue"));
                String date = week1.getString(week1.getColumnIndex("expenseDate"));




                LinearLayout listColumns = new LinearLayout(this);
                listColumns.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        270));

                listColumns.setBackground(getResources().getDrawable(R.drawable.border));

                TextView week = new TextView(this);
                week.setLayoutParams(lParams2);
                week.setText(" \n ");
                week.setTextColor(Color.BLACK);
                week.setTextSize(20);
                week.setBackgroundColor(Color.CYAN);



                TextView expenseInfo = new TextView(this);
                expenseInfo.setLayoutParams(lParams4);

                if(title.length() > 20){
                    String subTitle = title.substring(0,20);
                    subTitle += "...";
                    expenseInfo.setText(getString(R.string.Main_List_Title) + ": " + subTitle + "\n" + getString(R.string.Main_List_Value) +
                            ": $" + value + "\n" + getString(R.string.Main_List_Date) + ": " + date);
                }else{
                    expenseInfo.setText(getString(R.string.Main_List_Title) + ": " + title + "\n" + getString(R.string.Main_List_Value) +
                            ": $" + value + "\n" + getString(R.string.Main_List_Date) + ": " + date);
                }

                expenseInfo.setTextSize(20);
                expenseInfo.setContentDescription("1"+title);
                expenseInfo.setOnClickListener(new TextOnClickListener(month));



                ImageButton clear = new ImageButton(this);
                clear.setLayoutParams(paramsIcons);
                clear.setImageResource(R.drawable.ic_delete_forever_red_50dp);
                clear.setBackground(null);

                clear.setContentDescription("1"+title);
                clear.setOnClickListener(new ButtonsOnClickListener(month));

                ImageButton change = new ImageButton(this);
                change.setLayoutParams(paramsIcons);
                change.setImageResource(R.drawable.ic_edit_gray_50dp);
                change.setBackground(null);

                change.setContentDescription("1"+title);
                change.setOnClickListener(new ButtonsOnClickListenerEdit(month));






                listColumns.addView(clear);
                listColumns.addView(change);
                listColumns.addView(week);
                listColumns.addView(expenseInfo);

                expenseList.addView(listColumns);



            }while(week1.moveToNext());


        }
        dataBase.close();
    }

    public void setListSecondWeek(int month){

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this , "DBMonth" + month, null, 1);
        SQLiteDatabase dataBase = admin.getReadableDatabase();


        Cursor week2 = dataBase.query
                ("secondWeek",null,null,null,null,null, null);

        if(week2.moveToFirst()){

            TVError.setVisibility(View.INVISIBLE);
            TVError_Validate = false;

            week2.moveToFirst();

            do{

                String title = week2.getString(week2.getColumnIndex("expenseTitle"));
                String value = week2.getString(week2.getColumnIndex("expenseValue"));
                String date = week2.getString(week2.getColumnIndex("expenseDate"));


                LinearLayout listColumns = new LinearLayout(this);
                listColumns.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        270));

                listColumns.setBackground(getResources().getDrawable(R.drawable.border));

                TextView week = new TextView(this);
                week.setLayoutParams(lParams2);
                week.setText(" \n ");
                week.setTextColor(Color.BLACK);
                week.setTextSize(20);
                week.setBackgroundColor(Color.rgb(252,160,9));


                TextView expenseInfo = new TextView(this);
                expenseInfo.setLayoutParams(lParams4);
                if(title.length() > 20){
                    String subTitle = title.substring(0,20);
                    subTitle += "...";
                    expenseInfo.setText(getString(R.string.Main_List_Title) + ": " + subTitle + "\n" + getString(R.string.Main_List_Value) +
                            ": $" + value + "\n" + getString(R.string.Main_List_Date) + ": " + date);
                }else{
                    expenseInfo.setText(getString(R.string.Main_List_Title) + ": " + title + "\n" + getString(R.string.Main_List_Value) +
                            ": $" + value + "\n" + getString(R.string.Main_List_Date) + ": " + date);
                }
                expenseInfo.setTextSize(20);
                expenseInfo.setContentDescription("2"+title);
                expenseInfo.setOnClickListener(new TextOnClickListener(month));

                ImageButton clear = new ImageButton(this);
                clear.setLayoutParams(paramsIcons);
                clear.setImageResource(R.drawable.ic_delete_forever_red_50dp);
                clear.setBackground(null);

                clear.setContentDescription("2"+title);
                clear.setOnClickListener(new ButtonsOnClickListener(month));

                ImageButton change = new ImageButton(this);
                change.setLayoutParams(paramsIcons);
                change.setImageResource(R.drawable.ic_edit_gray_50dp);
                change.setBackground(null);

                change.setContentDescription("2"+title);
                change.setOnClickListener(new ButtonsOnClickListenerEdit(month));

                listColumns.addView(clear);
                listColumns.addView(change);
                listColumns.addView(week);
                listColumns.addView(expenseInfo);

                expenseList.addView(listColumns);


            }while(week2.moveToNext());


        }else if(TVError_Validate){
            TVError.setVisibility(View.VISIBLE);
        }

        dataBase.close();
    }

    public void setListThirdWeek(int month){

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this , "DBMonth" + month, null, 1);
        SQLiteDatabase dataBase = admin.getReadableDatabase();


        Cursor week3 = dataBase.query
                ("thirdWeek",null,null,null,null,null, null);

        if(week3.moveToFirst()){

            TVError.setVisibility(View.INVISIBLE);
            TVError_Validate = false;
            week3.moveToFirst();

            do{

                String title = week3.getString(week3.getColumnIndex("expenseTitle"));
                String value = week3.getString(week3.getColumnIndex("expenseValue"));
                String date = week3.getString(week3.getColumnIndex("expenseDate"));


                LinearLayout listColumns = new LinearLayout(this);
                listColumns.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        270));

                listColumns.setBackground(getResources().getDrawable(R.drawable.border));

                TextView week = new TextView(this);
                week.setLayoutParams(lParams2);
                week.setText(" \n ");
                week.setTextColor(Color.BLACK);
                week.setTextSize(20);
                week.setBackgroundColor(Color.rgb(249,82,80));


                TextView expenseInfo = new TextView(this);
                expenseInfo.setLayoutParams(lParams4);
                if(title.length() > 20){
                    String subTitle = title.substring(0,20);
                    subTitle += "...";
                    expenseInfo.setText(getString(R.string.Main_List_Title) + ": " + subTitle + "\n" + getString(R.string.Main_List_Value) +
                            ": $" + value + "\n" + getString(R.string.Main_List_Date) + ": " + date);
                }else{
                    expenseInfo.setText(getString(R.string.Main_List_Title) + ": " + title + "\n" + getString(R.string.Main_List_Value) +
                            ": $" + value + "\n" + getString(R.string.Main_List_Date) + ": " + date);
                }
                expenseInfo.setTextSize(20);
                expenseInfo.setContentDescription("3"+title);
                expenseInfo.setOnClickListener(new TextOnClickListener(month));



                ImageButton clear = new ImageButton(this);
                clear.setLayoutParams(paramsIcons);
                clear.setImageResource(R.drawable.ic_delete_forever_red_50dp);
                clear.setBackground(null);
                clear.setContentDescription("3"+title);
                clear.setOnClickListener(new ButtonsOnClickListener(month));

                ImageButton change = new ImageButton(this);
                change.setLayoutParams(paramsIcons);
                change.setImageResource(R.drawable.ic_edit_gray_50dp);
                change.setBackground(null);

                change.setContentDescription("3"+title);
                change.setOnClickListener(new ButtonsOnClickListenerEdit(month));

                listColumns.addView(clear);
                listColumns.addView(change);
                listColumns.addView(week);
                listColumns.addView(expenseInfo);

                expenseList.addView(listColumns);


            }while(week3.moveToNext());


        }else if(TVError_Validate){
            TVError.setVisibility(View.VISIBLE);
        }

        dataBase.close();
    }

    public void setListFourthWeek(int month){

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this , "DBMonth" + month, null, 1);
        SQLiteDatabase dataBase = admin.getReadableDatabase();



        Cursor week4 = dataBase.query
                ("fourthWeek",null,null,null,null,null, null);

        if(week4.moveToFirst()){

            TVError.setVisibility(View.INVISIBLE);
            TVError_Validate = false;

            week4.moveToFirst();

            do{

                String title = week4.getString(week4.getColumnIndex("expenseTitle"));
                String value = week4.getString(week4.getColumnIndex("expenseValue"));
                String date = week4.getString(week4.getColumnIndex("expenseDate"));


                LinearLayout listColumns = new LinearLayout(this);
                listColumns.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        270));

                listColumns.setBackground(getResources().getDrawable(R.drawable.border));

                TextView week = new TextView(this);
                week.setLayoutParams(lParams2);
                week.setText(" \n ");
                week.setTextColor(Color.BLACK);
                week.setTextSize(20);
                week.setBackgroundColor(Color.rgb(253,246, 15));


                TextView expenseInfo = new TextView(this);
                expenseInfo.setLayoutParams(lParams4);
                if(title.length() > 20){
                    String subTitle = title.substring(0,20);
                    subTitle += "...";
                    expenseInfo.setText(getString(R.string.Main_List_Title) + ": " + subTitle + "\n" + getString(R.string.Main_List_Value) +
                            ": $" + value + "\n" + getString(R.string.Main_List_Date) + ": " + date);
                }else{
                    expenseInfo.setText(getString(R.string.Main_List_Title) + ": " + title + "\n" + getString(R.string.Main_List_Value) +
                            ": $" + value + "\n" + getString(R.string.Main_List_Date) + ": " + date);
                }
                expenseInfo.setTextSize(20);
                expenseInfo.setContentDescription("4"+title);
                expenseInfo.setOnClickListener(new TextOnClickListener(month));


                ImageButton clear = new ImageButton(this);
                clear.setLayoutParams(paramsIcons);
                clear.setImageResource(R.drawable.ic_delete_forever_red_50dp);
                clear.setBackground(null);
                clear.setContentDescription("4"+title);
                clear.setOnClickListener(new ButtonsOnClickListener(month));

                ImageButton change = new ImageButton(this);
                change.setLayoutParams(paramsIcons);
                change.setImageResource(R.drawable.ic_edit_gray_50dp);
                change.setBackground(null);

                change.setContentDescription("4"+title);
                change.setOnClickListener(new ButtonsOnClickListenerEdit(month));

                listColumns.addView(clear);
                listColumns.addView(change);
                listColumns.addView(week);
                listColumns.addView(expenseInfo);



                expenseList.addView(listColumns);


            }while(week4.moveToNext());


        }else if(TVError_Validate){
            TVError.setVisibility(View.VISIBLE);
        }

        dataBase.close();
        TVError_Validate = true;
    }

    public void clearExpenseDBAlert(final String title, final int month){

        AlertDialog.Builder  alert = new AlertDialog.Builder(this);
        alert.setMessage(getString(R.string.Main_Alert_MSG1));
        alert.setTitle(getString(R.string.Main_Alert_Title1));
        alert.setPositiveButton(getString(R.string.Main_Alert_Button1), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clearExpenseBD(title, month);
            }
        });
        alert.setNegativeButton(getString(R.string.Main_Alert_Button2), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        AlertDialog dialog = alert.create();

        dialog.show();



    }

    public void editExpenseDBAlert(final String title, final int month){

        AlertDialog.Builder  alert = new AlertDialog.Builder(this);
        alert.setMessage(getString(R.string.Main_Alert_EditNote));
        alert.setTitle(getString(R.string.Main_Alert_EditNoteTitle));

        final EditText input = new EditText(this);
        input.setHint(getString(R.string.Main_Alert_EditNoteHint));
        alert.setView(input);

        alert.setPositiveButton(getString(R.string.Main_Alert_Button1), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editNoteExpenseBD(title,month, input.getText().toString());
            }
        });
        alert.setNegativeButton(getString(R.string.Main_Alert_Button2), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        AlertDialog dialog = alert.create();

        dialog.show();



    }

    public void showNoteExpenseDBAlert(final String title, final int month){

        AlertDialog.Builder  alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.Main_Alert_NoteTittle) + " " + title.substring(1, title.length()) + "\n");
        alert.setMessage(getString(R.string.Main_Alert_NoteTittle2) + "\n\n" + getNoteExpenseDB(title, month));



        AlertDialog dialog = alert.create();

        dialog.show();



    }

    public String getNoteExpenseDB(String title, int month){
        String output = "";
        char week = title.charAt(0);
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "DBMonth" + month, null, 1);
        SQLiteDatabase database = admin.getReadableDatabase();

        Cursor row;

        switch (week){
            case '1':
                row = database.rawQuery("SELECT expenseNote FROM firstWeek WHERE expenseTitle='" + title.substring(1, title.length()) + "'", null);
                if(row.moveToFirst()){
                    output = row.getString(0);
                }

                break;
            case '2':
                row = database.rawQuery("SELECT expenseNote FROM secondWeek WHERE expenseTitle='" + title.substring(1, title.length()) + "'", null);
                if(row.moveToFirst()){
                    output = row.getString(0);
                }
                break;
            case '3':
                row = database.rawQuery("SELECT expenseNote FROM thirdWeek WHERE expenseTitle='" + title.substring(1, title.length()) + "'", null);
                if(row.moveToFirst()){
                    output = row.getString(0);
                }
                break;
            case '4':
                row = database.rawQuery("SELECT expenseNote FROM fourthWeek WHERE expenseTitle='" + title.substring(1, title.length()) + "'", null);
                if(row.moveToFirst()){
                    output = row.getString(0);
                }
               break;
        }
        if(output.equals("")){
            output = getString(R.string.Main_Alert_DontHaveNote);
        }
        database.close();
        return output;

    }

    public void clearExpenseBD(String title, int month){
        char week = title.charAt(0);


        if(!title.isEmpty()){
            AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "DBMonth" + month, null, 1);
            SQLiteDatabase database = admin.getWritableDatabase();
            SQLiteDatabase databaseR = admin.getReadableDatabase();

            Cursor row;
            double value = 0;

            int aux;
            switch (week){
                case '1':
                    row = databaseR.rawQuery("SELECT expenseValue FROM firstWeek WHERE expenseTitle='" + title.substring(1,title.length()) + "'", null);
                    if(row.moveToFirst()){
                        value = row.getDouble(0);
                        UpdateTVAccountingDB(month, 1, value);
                    }

                    aux = database.delete("firstWeek", "expenseTitle='" + title.substring(1,title.length()) + "'", null);
                    break;
                case '2':
                    row = databaseR.rawQuery("SELECT expenseValue FROM secondWeek WHERE expenseTitle='" + title.substring(1,title.length()) + "'", null);
                    if(row.moveToFirst()){
                        value = row.getDouble(0);
                        UpdateTVAccountingDB(month, 2, value);
                    }

                    aux = database.delete("secondWeek", "expenseTitle='" + title.substring(1,title.length()) + "'", null);
                    break;
                case '3':
                    row = databaseR.rawQuery("SELECT expenseValue FROM thirdWeek WHERE expenseTitle='" + title.substring(1,title.length()) + "'", null);
                    if(row.moveToFirst()){
                        value = row.getDouble(0);
                        UpdateTVAccountingDB(month, 3, value);
                    }

                    aux = database.delete("thirdWeek", "expenseTitle='" + title.substring(1,title.length()) + "'", null);
                    break;
                case '4':
                    row = databaseR.rawQuery("SELECT expenseValue FROM fourthWeek WHERE expenseTitle='" + title.substring(1,title.length()) + "'", null);
                    if(row.moveToFirst()){
                        value = row.getDouble(0);
                        UpdateTVAccountingDB(month, 4, value);
                    }

                    aux = database.delete("fourthWeek", "expenseTitle='" + title.substring(1,title.length()) + "'", null);
                    break;

            }
            database.close();

            expenseList.removeAllViews();
            makeListbyMonth(getMonthPreference());
        }
    }

    public void editNoteExpenseBD(String title, int month, String note){
        char week = title.charAt(0);


        if(!title.isEmpty()){
            AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "DBMonth" + month, null, 1);
            SQLiteDatabase database = admin.getWritableDatabase();

            ContentValues c = new ContentValues();
            c.put("expenseNote", note);
            int aux;
            switch (week){
                case '1':
                    aux = database.update("firstWeek", c, "expenseTitle='" + title.substring(1,title.length()) + "'", null);
                    break;
                case '2':
                    aux = database.update("secondWeek", c, "expenseTitle='" + title.substring(1,title.length()) + "'", null);

                    break;
                case '3':
                    aux = database.update("thirWeek", c, "expenseTitle='" + title.substring(1,title.length()) + "'", null);
                    break;
                case '4':
                    aux = database.update("fourthWeek", c, "expenseTitle='" + title.substring(1,title.length()) + "'", null);
                    break;

            }
            database.close();

            expenseList.removeAllViews();
            makeListbyMonth(getMonthPreference());
        }
    }


    class ButtonsOnClickListener implements View.OnClickListener
    {
        private int month;
        public ButtonsOnClickListener(int month){
            this.month = month;
        }
        @Override
        public void onClick(View view)
        {
            ImageButton button = (ImageButton) view;
            clearExpenseDBAlert(button.getContentDescription().toString(), month);

        }
    };

    class ButtonsOnClickListenerEdit implements View.OnClickListener
    {
        private int month;
        public ButtonsOnClickListenerEdit(int month){
            this.month = month;
        }
        @Override
        public void onClick(View view)
        {
            ImageButton button = (ImageButton) view;
            editExpenseDBAlert(button.getContentDescription().toString(), month);

        }
    };

    class TextOnClickListener implements View.OnClickListener
    {
        private int month;
        public TextOnClickListener(int month){
            this.month = month;
        }
        @Override
        public void onClick(View view)
        {
            TextView tv = (TextView) view;
            showNoteExpenseDBAlert(tv.getContentDescription().toString(), month);

        }
    };

}
