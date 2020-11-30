package com.diarioproyect.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.diarioproyect.Model.Comentario;
import com.diarioproyect.Model.Like;
import com.diarioproyect.Model.Post;
import com.diarioproyect.PerfilActivity;
import com.diarioproyect.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.ViewHolder> {

    private Context mContext;
    private List<Like> mLikes;
    private List<String> usernameList;
    private List<String> imgURLList;

    private DatabaseReference reference;



    public LikeAdapter(Context mContext, List<Like> mLikes, List<String> usernameList, List<String> imgURLList) {
        this.mContext = mContext;
        this.mLikes = mLikes;
        this.usernameList = usernameList;
        this.imgURLList = imgURLList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item_like, parent, false);

        return new LikeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Like like = mLikes.get(position);

        holder.username.setText(usernameList.get(position));

        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PerfilActivity.class);
                intent.putExtra("Perfil_Usuario_ID", like.getSender());
                mContext.startActivity(intent);
            }
        });

        if(imgURLList.get(position).equals("default"))
            holder.img_perfil.setImageResource(R.drawable.ic_person_red);
        else
            Glide.with(mContext).load(imgURLList.get(position)).into(holder.img_perfil);

        holder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Advertencia al eliminar el like
                AlertDialog.Builder alerta = new AlertDialog.Builder(mContext);
                alerta.setMessage("Desea eliminar la reaccion a este post");
                alerta.setTitle("Advertencia");

                alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Eliminacion del like
                        reference = FirebaseDatabase.getInstance().getReference();

                        Query databaseQuery = reference.child("Posts").orderByChild("id").equalTo(like.getPostID());

                        databaseQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (final DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                    snapshot.getRef().child("Likes").orderByChild("sender")
                                            .equalTo(like.getSender()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot snapshot1: dataSnapshot.getChildren()){
                                                snapshot1.getRef().removeValue();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
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

        if(like.isLikeAdmin())
            holder.eliminar.setVisibility(View.VISIBLE);
        else
            holder.eliminar.setVisibility(View.INVISIBLE);

    }

    @Override
    public int getItemCount() {
        if(mLikes.size() == usernameList.size())
            return mLikes.size();
        else
            return 0;
    }






    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView img_perfil;
        private TextView username;
        private Button eliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_perfil = itemView.findViewById(R.id.imagen_perfil);
            username = itemView.findViewById(R.id.username);
            eliminar = itemView.findViewById(R.id.eliminar_like);


        }
    }

}
