package com.diarioproyect.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.diarioproyect.MainActivity;
import com.diarioproyect.Model.Post;
import com.diarioproyect.Model.User;
import com.diarioproyect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;


public class PostFragment extends Fragment {

    private ImageView img_perfil;
    private TextView username;

    private TextView msg_contenido;
    private TextView estado_animo;

    private ImageButton cara_triste;
    private ImageButton cara_normal;
    private ImageButton cara_feliz;

    private Button btn_postear;

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private Intent intent;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_post, container, false);


        img_perfil = view.findViewById(R.id.imagen_perfil);
        username = view.findViewById(R.id.username);
        msg_contenido = view.findViewById(R.id.contenido_texto);
        estado_animo = view.findViewById(R.id.estado_animo);
        estado_animo.setText("Normal");
        btn_postear = view.findViewById(R.id.P_postear);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final String userid = firebaseUser.getUid();


        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if(user.getImageURL().equals("default"))
                    img_perfil.setImageResource(R.drawable.ic_person_red);
                else
                    Glide.with(PostFragment.this).load(user.getImageURL()).into(img_perfil);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_postear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = msg_contenido.getText().toString();
                String animo = estado_animo.getText().toString();

                if(!mensaje.isEmpty())
                    subirPost(firebaseUser.getUid(), mensaje, animo);
                else
                    Toast.makeText(getContext(), "Rellene los campos", Toast.LENGTH_SHORT).show();

                msg_contenido.setText("");
            }
        });

        cara_triste = view.findViewById(R.id.cara_triste);
        cara_normal = view.findViewById(R.id.cara_normal);
        cara_feliz = view.findViewById(R.id.cara_feliz);
        inicializarFunsionesAnimo();


        return view;
    }

    public void inicializarFunsionesAnimo(){
        cara_triste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estado_animo.setText("Triste");
            }
        });

        cara_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estado_animo.setText("Normal");
            }
        });

        cara_feliz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estado_animo.setText("Feliz");
            }
        });
    }



    private void subirPost(String sender, String mensaje, String estadoAnimo){
        reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", reference.push().getKey());
        hashMap.put("sender", sender);
        hashMap.put("mensaje", mensaje);
        hashMap.put("animo", estadoAnimo);

        reference.child("Posts").push().setValue(hashMap);
    }

}
