package com.diarioproyect.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diarioproyect.Model.Dia;
import com.diarioproyect.NotasActivity;
import com.diarioproyect.R;

import java.util.List;

public class DiaAdapter extends RecyclerView.Adapter<DiaAdapter.ViewHolder> {

    private Context mContext;
    private List<Dia> mDia;

    public DiaAdapter(Context mContext, List<Dia> mDia) {
        this.mContext = mContext;
        this.mDia = mDia;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.diario_item_dia, parent, false);

        return new DiaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Dia dia = mDia.get(position);

        holder.textoDia.setText("Día " + dia.getDia());

        holder.textoFecha.setText(dia.getFecha());

        holder.verDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NotasActivity.class);
                intent.putExtra("Dia_Nota", dia.getDia());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDia.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView bookmark;
        private ImageButton verDia;
        private TextView textoDia;
        private TextView textoFecha;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bookmark = itemView.findViewById(R.id.bookmark);
            verDia = itemView.findViewById(R.id.ver_dia);
            textoDia = itemView.findViewById(R.id.texto_dia);
            textoFecha = itemView.findViewById(R.id.texto_fecha);
            
        }
    }

}
