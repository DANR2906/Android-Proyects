package com.example.vacacionesproyecto.NotasApp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacacionesproyecto.NotasApp.CrearNotas;
import com.example.vacacionesproyecto.NotasApp.DataBase.AdminSQLiteOpenHelper;
import com.example.vacacionesproyecto.NotasApp.MainNotas;
import com.example.vacacionesproyecto.NotasApp.Modelo.Nota;
import com.example.vacacionesproyecto.R;

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
        View view = LayoutInflater.from(mContext).inflate(R.layout.nota_item, parent, false);

        return new NotasAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Nota nota = mNotas.get(position);
        holder.titulo.setText(nota.getTitulo());
        holder.fecha.setText(nota.getFecha());

        //Agregar el oyende de accion para abrir la nota y visualizarla.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CrearNotas.class);
                intent.putExtra("Texto", nota.getTexto());
                intent.putExtra("Titulo", nota.getTitulo());
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
        public ImageButton borrar_nota;



        public ViewHolder(final View itemView){
            super(itemView);

            titulo = itemView.findViewById(R.id.titulo_nota);
            fecha = itemView.findViewById(R.id.fecha_nota);
            borrar_nota = itemView.findViewById(R.id.borrar_nota);

            borrar_nota.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    borrarNota();
                    Intent intent = new Intent(mContext, MainNotas.class);
                    MainNotas.auxActivity.finish();
                    mContext.startActivity(intent);
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
