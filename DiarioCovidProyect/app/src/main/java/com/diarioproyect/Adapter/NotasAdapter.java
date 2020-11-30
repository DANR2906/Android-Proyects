package com.diarioproyect.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.diarioproyect.AdminSQLiteOpenHelper;
import com.diarioproyect.BuildConfig;
import com.diarioproyect.CrearNota;
import com.diarioproyect.Model.Nota;
import com.diarioproyect.NotasActivity;
import com.diarioproyect.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class NotasAdapter extends RecyclerView.Adapter<NotasAdapter.ViewHolder> {

    private Context mContext;
    private List<Nota> mNotas;

    public NotasAdapter(Context mContext, List<Nota> mNotas){
        this.mContext = mContext;
        this.mNotas = mNotas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.diario_nota_item, parent, false);

        return new NotasAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Nota nota = mNotas.get(position);
        holder.titulo.setText(nota.getTitulo());
        holder.fecha.setText(nota.getFecha());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CrearNota.class);
                intent.putExtra("Texto", nota.getTexto());
                intent.putExtra("Titulo", nota.getTitulo());
                intent.putExtra("Dia_Nota", nota.getDia());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mNotas.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titulo;
        public TextView fecha;
        public Button borrar_nota;



        public ViewHolder(final View itemView){
            super(itemView);

            titulo = itemView.findViewById(R.id.titulo_nota);
            fecha = itemView.findViewById(R.id.fecha_nota);
            borrar_nota = itemView.findViewById(R.id.borrar_nota);

            borrar_nota.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alerta = new AlertDialog.Builder(mContext);
                    alerta.setMessage("Si elimina la nota no podra recuperarla");
                    alerta.setTitle("Advertencia");

                    alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            borrarNota();
                            Toast.makeText(mContext, "Nota borrada con exito, recargue para ver los cambios", Toast.LENGTH_SHORT).show();
                        }
                    });

                    alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog dialog = alerta.create();

                    dialog.show();
                    }
            });

        }

        public void borrarNota(){
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(mContext, "Notas", null, 1);
            SQLiteDatabase database = admin.getWritableDatabase();

            int aux = database.delete("notas", "Titulo='" + titulo.getText().toString() + "'", null);
            database.close();

        }


    }
}
