package com.diarioproyect.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
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
import com.diarioproyect.ComentariosActivity;
import com.diarioproyect.LikesActivity;
import com.diarioproyect.Model.Like;
import com.diarioproyect.Model.Post;
import com.diarioproyect.PerfilActivity;
import com.diarioproyect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context mContext;
    private List<Post> mPosts;
    private List<String> usernameList;
    private List<String> imgURLList;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;



    public PostAdapter(Context mContext, List<Post> mPosts, List<String> usernameList, List<String> imgURLList) {
        this.mContext = mContext;
        this.mPosts = mPosts;
        this.usernameList = usernameList;
        this.imgURLList = imgURLList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item_post, parent, false);

        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
         final Post post = mPosts.get(position);

            holder.username.setText(usernameList.get(position));

            if(imgURLList.get(position).equals("default"))
                holder.img_perfil.setImageResource(R.drawable.ic_person_red);
            else
                Glide.with(mContext).load(imgURLList.get(position)).into(holder.img_perfil);


            if(post.getMensaje() != null)
                holder.mensaje.setText(post.getMensaje());
            else
                holder.mensaje.setText("*???*");


        if(post.getAnimo() != null){
                if(post.getAnimo().equals("Normal"))
                    holder.animo.setImageResource(R.drawable.ic_cara_normal);
                else if(post.getAnimo().equals("Triste"))
                    holder.animo.setImageResource(R.drawable.ic_cara_triste);
                else if(post.getAnimo().equals("Feliz"))
                    holder.animo.setImageResource(R.drawable.ic_cara_feliz);
            }else
                holder.animo.setImageResource(R.drawable.ic_cara_normal);

        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PerfilActivity.class);
                intent.putExtra("Perfil_Usuario_ID", post.getSender());
                mContext.startActivity(intent);
            }
        });

        holder.enviarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = holder.comentario.getText().toString();
                
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if(!texto.isEmpty()) {
                    subirComentario(texto, firebaseUser.getUid(), post.getId(), position);
                    holder.comentario.setText("");
                }
                else
                    Toast.makeText(mContext, "Escriba un comentario", Toast.LENGTH_SHORT).show();
            }
        });

        holder.verComentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(mContext, ComentariosActivity.class);
                    intent.putExtra("PostID", post.getId());
                    mContext.startActivity(intent);
            }
        });

        reference = FirebaseDatabase.getInstance().getReference();

        Query databaseQuery = reference.child("Posts").orderByChild("id").equalTo(mPosts.get(position).getId());

        databaseQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot1 : dataSnapshot.getChildren()){
                    snapshot1.getRef().child("Coments").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                holder.numComentarios.setText(String.valueOf(dataSnapshot.getChildrenCount()));
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

        holder.eliminar.setContentDescription(post.getId());

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();

        holder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Advertencia al eliminar el post
                AlertDialog.Builder alerta = new AlertDialog.Builder(mContext);
                alerta.setMessage("Si elimina el post no podra recuperarlo");
                alerta.setTitle("Advertencia");

                alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //reference.child("Posts").orderByChild("id").equalTo(holder.eliminar.getContentDescription().toString());
                        //Eliminacion del post

                        Query databaseQuery = reference.child("Posts").orderByChild("id").equalTo(holder.eliminar.getContentDescription().toString());

                        databaseQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                    snapshot.getRef().removeValue();
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

        if(post.isPostAdmin())
            holder.eliminar.setVisibility(View.VISIBLE);
        else
            holder.eliminar.setVisibility(View.INVISIBLE);


        if(post.isEstado_like()) {
            holder.quitarLike.setVisibility(View.VISIBLE);
            holder.darLike.setVisibility(View.INVISIBLE);
        }
        else{
            holder.quitarLike.setVisibility(View.INVISIBLE);
            holder.darLike.setVisibility(View.VISIBLE);
        }





        holder.darLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!post.isEstado_like()){

                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    darLike(firebaseUser.getUid(), post.getId(), position);
                    holder.darLike.setVisibility(View.INVISIBLE);
                    holder.quitarLike.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(mContext, "Ya le dio like a este post anteriormente, porfavor recargue", Toast.LENGTH_SHORT).show();
                    holder.quitarLike.setVisibility(View.VISIBLE);
                    holder.darLike.setVisibility(View.INVISIBLE);
                }
            }
        });

        holder.quitarLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                quitarLike(firebaseUser.getUid(), position);
                holder.darLike.setVisibility(View.VISIBLE);
                holder.quitarLike.setVisibility(View.INVISIBLE);
            }
        });

        holder.verLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LikesActivity.class);
                intent.putExtra("PostID", post.getId());
                mContext.startActivity(intent);
            }
        });

    }






    @Override
    public int getItemCount() {
        if(mPosts.size() == usernameList.size()) {
            return mPosts.size();
        }
        else
            return 0;


    }



    private void subirComentario(final String texto,final String sender,final String postID, int position){
        reference = FirebaseDatabase.getInstance().getReference();

        Query databaseQuery = reference.child("Posts").orderByChild("id").equalTo(mPosts.get(position).getId());

        databaseQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    final HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("texto", texto);
                    hashMap.put("sender", sender);
                    hashMap.put("postID", postID);
                    hashMap.put("id", reference.push().getKey());
                    snapshot.getRef().child("Coments").push().setValue(hashMap);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void darLike(final String sender,final String postID, int position){
        reference = FirebaseDatabase.getInstance().getReference();

        Query databaseQuery = reference.child("Posts").orderByChild("id").equalTo(mPosts.get(position).getId());

        databaseQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    final HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("sender", sender);
                    hashMap.put("postID", postID);
                    hashMap.put("id", reference.push().getKey());
                    snapshot.getRef().child("Likes").push().setValue(hashMap);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void quitarLike(final String sender, int position){
        reference = FirebaseDatabase.getInstance().getReference();

        Query databaseQuery = reference.child("Posts").orderByChild("id").equalTo(mPosts.get(position).getId());

        databaseQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    snapshot.getRef().child("Likes").orderByChild("sender")
                            .equalTo(sender).addListenerForSingleValueEvent(new ValueEventListener() {
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



    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView username;
        private TextView mensaje;
        private ImageView img_perfil;
        private ImageView animo;
        private EditText comentario;
        private Button eliminar;
        private ImageButton enviarComentario;
        private ImageButton verComentarios;
        private TextView numComentarios;
        private ImageButton darLike;
        private ImageButton quitarLike;
        private ImageButton verLikes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            eliminar = itemView.findViewById(R.id.eliminar_post);
            username = itemView.findViewById(R.id.username);
            img_perfil = itemView.findViewById(R.id.imagen_perfil);
            mensaje = itemView.findViewById(R.id.contenido_texto);
            animo = itemView.findViewById(R.id.cara_animo);
            comentario = itemView.findViewById(R.id.comentarios_texto);
            enviarComentario = itemView.findViewById(R.id.boton_enviar_comentario);
            verComentarios = itemView.findViewById(R.id.ver_comentarios);
            numComentarios = itemView.findViewById(R.id.numero_comentarios);
            darLike = itemView.findViewById(R.id.dar_like);
            quitarLike = itemView.findViewById(R.id.quitar_like);
            verLikes = itemView.findViewById(R.id.ver_likes);

        }
    }
}
