package com.diarioproyect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.diarioproyect.Adapter.ComentAdapter;
import com.diarioproyect.Adapter.PostAdapter;
import com.diarioproyect.Model.Comentario;
import com.diarioproyect.Model.Post;
import com.diarioproyect.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ComentariosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ComentAdapter comentAdapter;
    private List<Comentario> mComents;


    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    private List<String> usernameList;
    private List<String> imgURLList;
    private Intent intent;

    private String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Comentarios");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        intent = getIntent();

        postId = intent.getStringExtra("PostID");


        leerComentarios();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_comentarios, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.refresh:
                Intent intent = new Intent(this, ComentariosActivity.class);
                intent.putExtra("PostID", postId);
                finish();
                startActivity(intent);

                return true;

        }
        return false;
    }

    private void leerComentarios(){
        mComents = new ArrayList<>();
        usernameList = new ArrayList<>();
        imgURLList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child("Posts").orderByChild("id").equalTo(postId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    snapshot.getRef().child("Coments").addListenerForSingleValueEvent(new ValueEventListener() {

                           @Override
                           public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                               mComents.clear();
                               usernameList.clear();
                               imgURLList.clear();

                               for(DataSnapshot snapshot1 : dataSnapshot1.getChildren()){

                                   final Comentario comentario = snapshot1.getValue(Comentario.class);
                                   comentario.setComentAdmin(isComentAdmin(comentario));

                                   mComents.add(0,comentario);
                                   leerUsuarios(comentario);

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

    private void leerUsuarios(final Comentario comentario){

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {

                for(DataSnapshot snapshot2 : dataSnapshot2.getChildren()){
                    final User user = snapshot2.getValue(User.class);

                    if(user.getId().equals(comentario.getSender())) {

                        usernameList.add(0, user.getUsername());
                        imgURLList.add(0, user.getImageURL());

                        ComentAdapter comentAdapter = new ComentAdapter(ComentariosActivity.this, mComents, usernameList, imgURLList);
                        recyclerView.setAdapter(comentAdapter);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean isComentAdmin(final Comentario cometario) {


        reference = FirebaseDatabase.getInstance().getReference();
        Query databaseQuery = reference.child("Posts").orderByChild("id").equalTo(postId);

        databaseQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);

                        if (post.getSender().equals(firebaseUser.getUid()))
                            cometario.setComentAdmin(true);

                        else if(cometario.getSender().equals(firebaseUser.getUid()))
                            cometario.setComentAdmin(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return cometario.getComentAdmin();
    }




    public void enviarComentario(View view){
        EditText comentario = findViewById(R.id.comentarios_texto);

        String texto = comentario.getText().toString();


        if(!texto.isEmpty()) {
            subirComentario(texto, firebaseUser.getUid(), postId);
            comentario.setText("");
        }else
            Toast.makeText(this, "Escriba un comentario", Toast.LENGTH_SHORT).show();

    }


    private void subirComentario(String texto, String sender, String postID){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("texto", texto);
        hashMap.put("sender", sender);
        hashMap.put("postID", postID);
        hashMap.put("id", reference.push().getKey());

        Query databaseQuery = reference.child("Posts").orderByChild("id").equalTo(postId);

        databaseQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    snapshot.getRef().child("Coments").push().setValue(hashMap);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }






}
