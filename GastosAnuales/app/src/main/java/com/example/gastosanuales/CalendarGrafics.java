package com.example.gastosanuales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

public class CalendarGrafics extends AppCompatActivity {

    private TextView graficTitle;


    private String months[] = new String[12];

    private PieChart pieGrafic;
    private String[] pieWeeks;
    private int[] pieSale;


    private int[] pieColor = new int[]
            {Color.CYAN, Color.rgb(252,160,9), Color.rgb(249,82,80), Color.rgb(253,246, 15)};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        getSupportActionBar().hide();

        initializeComponents();


    }

    public void initializeComponents(){

        graficTitle = findViewById(R.id.Calendar_TV_GraficTitle);
        pieGrafic = findViewById(R.id.Calendar_PieChart_Total);

        initializeMonths();

        setPieSale(getMonth());
        createCharts();
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
        for(int i = 0; i < pieWeeks.length; i++){
            LegendEntry entry = new LegendEntry();
            entry.formColor = pieColor[i];
            entry.label = pieWeeks[i];
            entries.add(entry);
        }

        legend.setCustom(entries);
    }

    private ArrayList<PieEntry> getPieEntries(){
        ArrayList<PieEntry> entries = new ArrayList<>();
        for(int i = 0; i < pieSale.length; i++)
            entries.add(new PieEntry(pieSale[i],i));
        return entries;

    }


    public void createCharts(){
        pieGrafic = (PieChart) getSameChart(pieGrafic, "", Color.GRAY, Color.WHITE, 2000 );
        pieGrafic.setHoleRadius(45);
        pieGrafic.setTransparentCircleRadius(10);
        pieGrafic.setData(getPieData());
        pieGrafic.invalidate();
        pieGrafic.setUsePercentValues(true);

    }

    private DataSet getData(DataSet dataSet){
        dataSet.setColors(pieColor);
        dataSet.setValueTextSize(Color.WHITE);
        dataSet.setValueTextSize(13);
        return dataSet;
    }

    private PieData getPieData(){
        PieDataSet pieDataSet = (PieDataSet) getData(new PieDataSet(getPieEntries(),""));

        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueFormatter(new PercentFormatter());
        PieData pieData = new PieData(pieDataSet);
        return pieData;
    }

    public void setPieSale(int month){
        pieWeeks = new String[]
                {getString(R.string.Calendar_BarChartGrafic_Week1),
                getString(R.string.Calendar_BarChartGrafic_Week2),
                getString(R.string.Calendar_BarChartGrafic_Week3),
                getString(R.string.Calendar_BarChartGrafic_Week4)};

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
            //double auxMonth = row.getDouble(4);

            pieSale = new int[]{(int)auxWeek1, (int)auxWeek2, (int)auxWeek3, (int)auxWeek4};

        }else{
            pieSale = new int[]{0,0,0,0};
        }
        row.close();
        database.close();

    }
    //Prueba fin
    public String getMonthPreference(){
        SharedPreferences p = getSharedPreferences("monthPreference", Context.MODE_PRIVATE);
        String month = p.getString("ListMonth", "");
        return month;
    }

    public int getMonth(){
        String month = getMonthPreference();

        //case 1: month 1
        if(month.equals(months[0])) return 1;
        //case 2: month 2
        if(month.equals(months[1])) return 2;
        //case 3: month 3
        if(month.equals(months[2])) return 3;
        //case 4: month 4
        if(month.equals(months[3])) return 4;
        //case 5: month 5
        if(month.equals(months[4])) return 5;
        //case 6: month 6
        if(month.equals(months[5])) return 6;
        //case 7: month 7
        if(month.equals(months[6])) return 7;
        //case 8: month 8
        if(month.equals(months[7])) return 8;
        //case 9: month 9
        if(month.equals(months[8])) return 9;
        //case 10: month 10
        if(month.equals(months[9])) return 10;
        //case 11: month 11
        if(month.equals(months[10])) return 11;
        //case 12: month 12
        if(month.equals(months[11])) return 12;

        return 1;
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

    public void changeGrafic(View view){
        Toast.makeText(this, getString(R.string.Calendar_Alert_ChangeGrafic), Toast.LENGTH_SHORT).show();
    }

}
