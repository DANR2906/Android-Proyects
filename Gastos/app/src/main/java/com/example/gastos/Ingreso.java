package com.example.gastos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLInput;
import java.util.Calendar;

public class Ingreso extends AppCompatActivity {


    private EditText et1;
    private EditText et2;
    private EditText et3;
    private EditText et4;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private ImageView iv1;
    private Button b1;
    private ImageButton imgAvatar;
    private static final int idUnica = 5761;
    NotificationCompat.Builder notificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        et1 = findViewById(R.id.INGRESO_edit_txt_titulo);
        et2 = findViewById(R.id.INGRESO_edit_txt_valor);
        et3 = findViewById(R.id.INGRESO_edit_txt_date);
        tv1 = findViewById(R.id.INGRESO_txt_gastos);
        tv2 = findViewById(R.id.INGRESO_txt_restante);
        tv3 = findViewById(R.id.INGRESO_txt_total);
        et4 = findViewById(R.id.INGRESO_edit_txt_Presupuesto);
        iv1 = findViewById(R.id.INGRESO_iv_Barra);
        imgAvatar = findViewById(R.id.INGRESO_img_Avatar);
        b1 = findViewById(R.id.button4);


        b1.setText(R.string.INGRESO_Button_Reini);
        et3.setText(fecha());
        obtenerAvatar();

        obtenerAhorros();
        obtenerGastado();
        obtenerPresupuesto();
        setSemana();
        guardarPresupuestoAux();

        notificacion = new NotificationCompat.Builder(this, String.valueOf(idUnica))
                .setAutoCancel(true)
                .setVibrate(new long[] {100, 250, 100, 500});

    }

    @Override
    protected void onStart() {
        super.onStart();

        et1 = findViewById(R.id.INGRESO_edit_txt_titulo);
        et2 = findViewById(R.id.INGRESO_edit_txt_valor);
        et3 = findViewById(R.id.INGRESO_edit_txt_date);
        tv1 = findViewById(R.id.INGRESO_txt_gastos);
        tv2 = findViewById(R.id.INGRESO_txt_restante);
        tv3 = findViewById(R.id.INGRESO_txt_total);
        et4 = findViewById(R.id.INGRESO_edit_txt_Presupuesto);
        iv1 = findViewById(R.id.INGRESO_iv_Barra);
        imgAvatar = findViewById(R.id.INGRESO_img_Avatar);
        b1 = findViewById(R.id.button4);


        b1.setText(R.string.INGRESO_Button_Reini);
        et3.setText(fecha());
        obtenerAvatar();

        obtenerAhorros();
        obtenerGastado();
        obtenerPresupuesto();
        setSemana();
        guardarPresupuestoAux();


    }



    public String fecha(){
        Calendar calendar = Calendar.getInstance();
        int mes =calendar.get(Calendar.MONTH) + 1;
        String fecha = Integer.toString
                (calendar.get(Calendar.DATE)) + "/" + mes + "/" + Integer.toString(calendar.get(Calendar.YEAR));
        return fecha;
    }

    public void guardar(View view) {

        String titulo = et1.getText().toString();
        String valor = et2.getText().toString();
        String fecha = et3.getText().toString();

        if(!titulo.isEmpty() && !valor.isEmpty() && !fecha.isEmpty()){

            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "BDGastos", null, 1);
            SQLiteDatabase dataBase = admin.getWritableDatabase();
            SQLiteDatabase dataBaseR = admin.getReadableDatabase();

            AdminSQLiteOpenHelper admin2 = new AdminSQLiteOpenHelper(this, "Contabilidad", null, 1);
            SQLiteDatabase dataBase2 = admin2.getReadableDatabase();
            SQLiteDatabase dataBase2W = admin2.getWritableDatabase();


            Cursor c = dataBaseR.rawQuery("SELECT * FROM gastos WHERE tituloGasto='" + titulo + "'", null);

            if(c.moveToFirst()){
                Toast.makeText(this, "El titulo del gasto ya existe", Toast.LENGTH_SHORT).show();
                StringBuilder auxTitulo = new StringBuilder(titulo);
                String aux = c.getString(0);

                if(aux.length() > 3){
                    aux =  aux.substring((aux.length()-2),(aux.length()-1));
                }

                switch(aux){
                    case"1":
                        auxTitulo.delete(auxTitulo.length() - 3, auxTitulo.length());
                        et1.setText(auxTitulo.toString() + "(2)");
                        return;
                    case"2":
                        auxTitulo.delete(auxTitulo.length() - 3, auxTitulo.length());
                        et1.setText(auxTitulo.toString() + "(3)");
                        return;
                    case"3":
                        auxTitulo.delete(auxTitulo.length() - 3, auxTitulo.length());
                        et1.setText(auxTitulo.toString() + "(4)");
                        return;
                    case"4":
                        auxTitulo.delete(auxTitulo.length() - 3, auxTitulo.length());
                        et1.setText(auxTitulo.toString() + "(5)");
                        return;
                    case"5":
                        auxTitulo.delete(auxTitulo.length() - 3, auxTitulo.length());
                        et1.setText(auxTitulo.toString() + "(6)");
                        return;
                    case"6":
                        auxTitulo.delete(auxTitulo.length() - 3, auxTitulo.length());
                        et1.setText(auxTitulo.toString() + "(7)");
                        return;
                    case"7":
                        auxTitulo.delete(auxTitulo.length() - 3, auxTitulo.length());
                        et1.setText(auxTitulo.toString() + "(8)");
                        return;
                    case"8":
                        auxTitulo.delete(auxTitulo.length() - 3, auxTitulo.length());
                        et1.setText(auxTitulo.toString() + "(9)");
                        return;
                    case"9":
                        auxTitulo.delete(auxTitulo.length() - 3, auxTitulo.length());
                        et1.setText(auxTitulo.toString() + "(10)");
                        return;
                    case"10":
                        auxTitulo.delete(auxTitulo.length() - 3, auxTitulo.length());
                        et1.setText(auxTitulo.toString() + "(11)");
                        return;
                    case"11":
                        auxTitulo.delete(auxTitulo.length() - 3, auxTitulo.length());
                        et1.setText(auxTitulo.toString() + "(12)");
                        return;
                    case"12":
                        auxTitulo.delete(auxTitulo.length() - 3, auxTitulo.length());
                        et1.setText(auxTitulo.toString() + "(13)");
                        return;
                    case"13":
                        auxTitulo.delete(auxTitulo.length() - 3, auxTitulo.length());
                        et1.setText(auxTitulo.toString() + "(14)");
                        return;
                    case"14":
                        auxTitulo.delete(auxTitulo.length() - 3, auxTitulo.length());
                        et1.setText(auxTitulo.toString() + "(15)");
                        return;

                    default:
                        et1.setText(auxTitulo.toString() + "(1)");
                        return;

                }
            }

            String semana = "1";
            ContentValues gasto = new ContentValues();
            gasto.put("tituloGasto", titulo);
            gasto.put("valorGasto", valor);
            gasto.put("fechaGasto", fecha);
            dataBase.insert("gastos", null , gasto);
            dataBase.close();

            Cursor fila = dataBase2.rawQuery
                    ("SELECT totalGastado, totalAhorrado, presupuesto FROM contabilidad WHERE semana ='" + semana + "'", null);

            if(fila.moveToFirst()){
                double gastado = fila.getDouble(0);
                double ahorrado = fila.getDouble(1);
                double presupuesto = fila.getDouble( 2);

                gastado += Double.parseDouble(valor);
                ahorrado = presupuesto - gastado;
                double auxBarra = ((gastado*100)/presupuesto);

                if(auxBarra >= 5 && auxBarra < 10){
                    iv1.setImageResource(R.mipmap.barra95);
                }
                if(auxBarra >= 10 && auxBarra < 15){
                    iv1.setImageResource(R.mipmap.barra90);
                }
                if(auxBarra >= 15 && auxBarra < 20){
                    iv1.setImageResource(R.mipmap.barra85);
                }
                if(auxBarra >= 20 && auxBarra < 25){
                    iv1.setImageResource(R.mipmap.barra80);
                }
                if(auxBarra >= 25 && auxBarra < 30){
                    iv1.setImageResource(R.mipmap.barra75);
                }
                if(auxBarra >= 30 && auxBarra < 35){
                    iv1.setImageResource(R.mipmap.barra70);
                }
                if(auxBarra >= 35 && auxBarra < 40){
                    iv1.setImageResource(R.mipmap.barra65);
                }
                if(auxBarra >= 40 && auxBarra < 45){
                    iv1.setImageResource(R.mipmap.barra60);
                }
                if(auxBarra >= 45 && auxBarra < 50){
                    iv1.setImageResource(R.mipmap.barra55);
                }
                if(auxBarra >= 50 && auxBarra < 55){
                    iv1.setImageResource(R.mipmap.barra50);
                }
                if(auxBarra >= 55 && auxBarra < 60){
                    iv1.setImageResource(R.mipmap.barra45);
                }
                if(auxBarra >= 60 && auxBarra < 65){
                    iv1.setImageResource(R.mipmap.barra40);
                }
                if(auxBarra >= 65 && auxBarra < 70){
                    iv1.setImageResource(R.mipmap.barra35);
                }
                if(auxBarra >= 70 && auxBarra < 75){
                    iv1.setImageResource(R.mipmap.barra30);
                }
                if(auxBarra >= 75 && auxBarra < 80){
                    iv1.setImageResource(R.mipmap.barra25);
                }
                if(auxBarra >= 80 && auxBarra < 85){
                    iv1.setImageResource(R.mipmap.barra20);
                }
                if(auxBarra >= 85 && auxBarra < 90){
                    iv1.setImageResource(R.mipmap.barra15);
                }
                if(auxBarra >= 90 && auxBarra < 95){
                    iv1.setImageResource(R.mipmap.barra10);
                }
                if(auxBarra >= 95 && auxBarra < 100){
                    iv1.setImageResource(R.mipmap.barra5);
                }
                if(auxBarra >= 100){
                    iv1.setImageResource(R.mipmap.barra0);
                }





                tv1.setText(getString(R.string.INGRESO_TotalGastado) + ": $" + String.valueOf(gastado));

                tv2.setText(getString(R.string.INGRESO_TotalAhorrado) + ": $" + String.valueOf(ahorrado));

                if(ahorrado < 0){

                    Intent Ingreso = new Intent(this, Ingreso.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, Ingreso, 0);

                    notificacion.setSmallIcon(R.drawable.ic_launcher_background)
                            .setSmallIcon(R.drawable.ic_stat_name)
                            .setTicker("Nuevo Gasto Guardado")
                            .setContentTitle("Precaución")
                            .setContentText("Los gastos superan el presupuesto semanal")
                            .setWhen(System.currentTimeMillis())
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(pendingIntent);





                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    manager.notify(idUnica, notificacion.build());

                    Toast.makeText(this, "Excedio el presupuesto semanal", Toast.LENGTH_SHORT).show();
                    tv2.setTextColor(Color.RED);
                }else{
                    tv2.setTextColor(Color.GRAY);
                }

                ContentValues update = new ContentValues();
                update.put("totalGastado", gastado);
                update.put("totalAhorrado", ahorrado);

                int cantidad = dataBase2W.update("contabilidad", update, "semana='" + semana + "'", null);
                dataBase2W.close();
                dataBase2.close();



            }
            et1.setText("");
            et2.setText("");


        }else {
            Toast.makeText(this, "Rellene los campos", Toast.LENGTH_SHORT).show();

        }

    }

    public void setSemana(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Contabilidad", null, 1);
        SQLiteDatabase dataBase = admin.getWritableDatabase();

        ContentValues gastos = new ContentValues();
        gastos.put("semana", "1");
        gastos.put("totalGastado", "0");
        gastos.put("totalAhorrado", "0");
        gastos.put("presupuesto", "0");
        dataBase.insert("contabilidad", null, gastos);
        dataBase.close();

    }

    public void ActualizarPresupuesto(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Contabilidad", null, 1);
        SQLiteDatabase dataBase = admin.getWritableDatabase();

        String presupuesto = et4.getText().toString();


        if(!presupuesto.isEmpty()){


            String semana = "1";
            ContentValues upgrade = new ContentValues();
            upgrade.put("presupuesto", presupuesto);
            int cantidad = dataBase.update("contabilidad", upgrade, "semana='" + semana + "'", null);
            dataBase.close();

            
            tv3.setText(getString(R.string.INGRESO_PresupuestoMSG) + ": $" + presupuesto);

            guardarPresupuestoAux();
            et4.setText("");

        }else Toast.makeText(this, "Ingrese un nuevo presupuesto", Toast.LENGTH_SHORT).show(); dataBase.close();
    }

    public void obtenerPresupuesto(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Contabilidad", null, 1);
        SQLiteDatabase dataBase = admin.getReadableDatabase();

        Cursor fila = dataBase.rawQuery("SELECT presupuesto FROM contabilidad WHERE semana='" + "1" +"'", null);

        if(fila.moveToFirst()){
            tv3.setText(getString(R.string.INGRESO_PresupuestoMSG) + ": $" + fila.getString(0));
            dataBase.close();
        }else dataBase.close();
    }

    public void obtenerGastado(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Contabilidad", null, 1);
        SQLiteDatabase dataBase = admin.getReadableDatabase();

        Cursor fila = dataBase.rawQuery("SELECT totalGastado FROM contabilidad WHERE semana='" + "1" +"'", null);

        if(fila.moveToFirst()){
            tv1.setText(getString(R.string.INGRESO_TotalGastado) + ": $" + fila.getString(0));
            dataBase.close();
        }else dataBase.close();
    }

    public void obtenerAhorros(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Contabilidad", null, 1);
        SQLiteDatabase dataBase = admin.getReadableDatabase();

        Cursor fila = dataBase.rawQuery("SELECT totalAhorrado FROM contabilidad WHERE semana='" + "1" +"'", null);

        if(fila.moveToFirst()){
            tv2.setText(getString(R.string.INGRESO_TotalAhorrado) + ": $" + fila.getString(0));
            dataBase.close();
        }else dataBase.close();
    }

    public double obtenerPresupuestoDouble(){
        double aux = 0;
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Contabilidad", null, 1);
        SQLiteDatabase dataBase = admin.getReadableDatabase();

        Cursor fila = dataBase.rawQuery("SELECT presupuesto FROM contabilidad WHERE semana='" + "1" +"'", null);

        if(fila.moveToFirst()){
                 aux = fila.getDouble(0);

                dataBase.close();
            return aux;
        }else dataBase.close();

        return aux;
    }

    public void reiniciarSemana(View view){

        copiarBaseDeDatos();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Contabilidad", null, 1);
        SQLiteDatabase dataBase = admin.getWritableDatabase();

        ContentValues upgrade = new ContentValues();
        //upgrade.put("presupuesto", 0);
        upgrade.put("totalAhorrado", obtenerPresupuestoDouble());
        upgrade.put("totalGastado", 0);
        int cantidad = dataBase.update("contabilidad", upgrade, "semana='" + "1" +"'", null);
        dataBase.close();

        tv1.setText(getString(R.string.INGRESO_TotalGastado) + ": $0");
        tv2.setText(getString(R.string.INGRESO_TotalAhorrado) + ": $" + obtenerPresupuestoDouble());
        //tv3.setText("Presupuesto: $0");
        tv2.setTextColor(Color.GRAY);
        iv1.setImageResource(R.mipmap.barra100);


         AdminSQLiteOpenHelper admin2 = new AdminSQLiteOpenHelper(this, "BDGastos", null, 1);
         SQLiteDatabase database2 = admin2.getWritableDatabase();


        database2.delete("gastos",null,null);

        database2.close();




    }

    public void copiarBaseDeDatos(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "BDGastosCopy", null, 1);
        SQLiteDatabase database = admin.getWritableDatabase();

        AdminSQLiteOpenHelper admin2 = new AdminSQLiteOpenHelper(this, "BDGastos", null, 1);
        SQLiteDatabase database2 = admin2.getReadableDatabase();

        //Borrar la base de datos copy que ya existe para evitar fallos
        database.delete("gastosPasados",null,null);

        Cursor fila = database2.query
                ("gastos", null,null,null,null,null,null);

        if(fila.moveToFirst()){
            fila.moveToFirst();

            String auxTitulo = "NUEVA SEMANA";
            ContentValues updateDef = new ContentValues();
            updateDef.put("tituloGasto", auxTitulo);
            updateDef.put("valorGasto", auxTitulo);
            updateDef.put("fechaGasto", auxTitulo);

            database.insert("gastosPasados", null, updateDef);

            do{
                String titulo = fila.getString(fila.getColumnIndex("tituloGasto"));
                String valor = fila.getString(fila.getColumnIndex("valorGasto"));
                String fecha = fila.getString(fila.getColumnIndex("fechaGasto"));


                ContentValues update = new ContentValues();
                update.put("tituloGasto", titulo);
                update.put("valorGasto", valor);
                update.put("fechaGasto", fecha);

                database.insert("gastosPasados", null, update);


            }while(fila.moveToNext());
            database.close();
            database2.close();


        }else {
            database.close();

            database2.close();
        }


    }


    public void guardarPresupuestoAux() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "BDGastos", null, 1);
        SQLiteDatabase dataBase = admin.getWritableDatabase();

        AdminSQLiteOpenHelper admin2 = new AdminSQLiteOpenHelper(this, "Contabilidad", null, 1);
        SQLiteDatabase dataBase2 = admin2.getReadableDatabase();
        SQLiteDatabase dataBase2W = admin2.getWritableDatabase();

        String titulo = "actualizacion Gastos";
        String valor = "0";
        String fecha = et3.getText().toString();

        String semana = "1";
        ContentValues gasto = new ContentValues();
        gasto.put("tituloGasto", titulo);
        gasto.put("valorGasto", valor);
        gasto.put("fechaGasto", fecha);
        dataBase.insert("gastos", null, gasto);
        dataBase.close();

        Cursor fila = dataBase2.rawQuery
                ("SELECT totalGastado, totalAhorrado, presupuesto FROM contabilidad WHERE semana ='" + semana + "'", null);

        if (fila.moveToFirst()) {
            double gastado = fila.getDouble(0);
            double ahorrado = fila.getDouble(1);
            double presupuesto = fila.getDouble(2);

            gastado += Double.parseDouble(valor);
            ahorrado = presupuesto - gastado;
            double auxBarra = ((gastado * 100) / presupuesto);

            if (auxBarra >= 5 && auxBarra < 10) {
                iv1.setImageResource(R.mipmap.barra95);
            }
            if (auxBarra >= 10 && auxBarra < 15) {
                iv1.setImageResource(R.mipmap.barra90);
            }
            if (auxBarra >= 15 && auxBarra < 20) {
                iv1.setImageResource(R.mipmap.barra85);
            }
            if (auxBarra >= 20 && auxBarra < 25) {
                iv1.setImageResource(R.mipmap.barra80);
            }
            if (auxBarra >= 25 && auxBarra < 30) {
                iv1.setImageResource(R.mipmap.barra75);
            }
            if (auxBarra >= 30 && auxBarra < 35) {
                iv1.setImageResource(R.mipmap.barra70);
            }
            if (auxBarra >= 35 && auxBarra < 40) {
                iv1.setImageResource(R.mipmap.barra65);
            }
            if (auxBarra >= 40 && auxBarra < 45) {
                iv1.setImageResource(R.mipmap.barra60);
            }
            if (auxBarra >= 45 && auxBarra < 50) {
                iv1.setImageResource(R.mipmap.barra55);
            }
            if (auxBarra >= 50 && auxBarra < 55) {
                iv1.setImageResource(R.mipmap.barra50);
            }
            if (auxBarra >= 55 && auxBarra < 60) {
                iv1.setImageResource(R.mipmap.barra45);
            }
            if (auxBarra >= 60 && auxBarra < 65) {
                iv1.setImageResource(R.mipmap.barra40);
            }
            if (auxBarra >= 65 && auxBarra < 70) {
                iv1.setImageResource(R.mipmap.barra35);
            }
            if (auxBarra >= 70 && auxBarra < 75) {
                iv1.setImageResource(R.mipmap.barra30);
            }
            if (auxBarra >= 75 && auxBarra < 80) {
                iv1.setImageResource(R.mipmap.barra25);
            }
            if (auxBarra >= 80 && auxBarra < 85) {
                iv1.setImageResource(R.mipmap.barra20);
            }
            if (auxBarra >= 85 && auxBarra < 90) {
                iv1.setImageResource(R.mipmap.barra15);
            }
            if (auxBarra >= 90 && auxBarra < 95) {
                iv1.setImageResource(R.mipmap.barra10);
            }
            if (auxBarra >= 95 && auxBarra < 100) {
                iv1.setImageResource(R.mipmap.barra5);
            }
            if (auxBarra >= 100) {
                iv1.setImageResource(R.mipmap.barra0);
            }


            tv1.setText(getString(R.string.INGRESO_TotalGastado) + ": $" + String.valueOf(gastado));

            tv2.setText(getString(R.string.INGRESO_TotalAhorrado) + ": $" + String.valueOf(ahorrado));

            if (ahorrado < 0) {



                Toast.makeText(this, "Excedio el presupuesto semanal", Toast.LENGTH_SHORT).show();
                tv2.setTextColor(Color.RED);
            } else {
                tv2.setTextColor(Color.GRAY);
            }

            ContentValues update = new ContentValues();
            update.put("totalGastado", gastado);
            update.put("totalAhorrado", ahorrado);

            int cantidad = dataBase2W.update("contabilidad", update, "semana='" + semana + "'", null);
            dataBase2W.close();
            dataBase2.close();

        }

    }

    public void cambiarAvatar(View view){
        Intent avatar = new Intent(this, Avatar.class);
        startActivity(avatar);
    }


    public void obtenerAvatar(){
        SharedPreferences preferencias_avatar = getSharedPreferences("avatar", Context.MODE_PRIVATE);
        String numAvatar = preferencias_avatar.getString("numAvatar", "");
        switch(numAvatar){
            case "1":
                imgAvatar.setImageResource(R.mipmap.avatar1);
                break;
            case "2":
                imgAvatar.setImageResource(R.mipmap.avatar2);
                break;
            case "3":
                imgAvatar.setImageResource(R.mipmap.avatar3);
                break;
            case "4":
                imgAvatar.setImageResource(R.mipmap.avatar4);
                break;
            case "5":
                imgAvatar.setImageResource(R.mipmap.avatar5);
                break;
            case "6":
                imgAvatar.setImageResource(R.mipmap.avatar6);
                break;
            case "7":
                imgAvatar.setImageResource(R.mipmap.avatar7);
                break;
            case "8":
                imgAvatar.setImageResource(R.mipmap.avatar8);
                break;
            case "9":
                imgAvatar.setImageResource(R.mipmap.avatar9);
                break;
            case "10":
                imgAvatar.setImageResource(R.mipmap.avatar10);
                break;

        }
    }

    public void verGastos(View view){
        Intent gastos = new Intent(this, Gastos.class);
        startActivity(gastos);
    }

    public void VerGastosPasados(View view){
        Intent gastosPasados = new Intent(this, Gastos2.class);
        startActivity(gastosPasados);
    }
}
