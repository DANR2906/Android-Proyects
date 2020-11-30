package com.appdirecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText num1;
    private EditText num2;

    private Button btnSuma;
    private Button btnResta;
    private Button btnMultiplicacion;
    private Button btnDivision;

    private TextView respuesta;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1 = findViewById(R.id.entrada_num1);
        num2 = findViewById(R.id.entrada_num2);

        btnSuma = findViewById(R.id.boton_suma);
        btnResta = findViewById(R.id.boton_resta);
        btnMultiplicacion = findViewById(R.id.boton_multiplicacion);
        btnDivision = findViewById(R.id.boton_division);









        respuesta = findViewById(R.id.texto_respuesta);

    }


    public void boton_sumar(View view){
        sumar(Double.parseDouble(num1.getText().toString()), Double.parseDouble(num2.getText().toString()));
    }

    public void boton_restar(View view){
        resta(Double.parseDouble(num1.getText().toString()), Double.parseDouble(num2.getText().toString()));
    }

    public void boton_mult(View view){
        multiplicacion(Double.parseDouble(num1.getText().toString()), Double.parseDouble(num2.getText().toString()));
    }

    public void boton_division(View view){
        division(Double.parseDouble(num1.getText().toString()), Double.parseDouble(num2.getText().toString()));
    }


    public void sumar(double n1, double n2){
        double aux = n1 + n2;
        respuesta.setText(String.valueOf(aux));
    }

    public void resta(double n1, double n2){
        double aux = n1 - n2;
        respuesta.setText(String.valueOf(aux));
    }

    public void multiplicacion(double n1, double n2){
        double aux = n1 * n2;
        respuesta.setText(String.valueOf(aux));
    }

    public void division(double n1, double n2){
        if(n2 == 0){
            respuesta.setText("0");
        }else{
            double aux = n1 / n2;
            respuesta.setText(String.valueOf(aux));
        }
    }


}
