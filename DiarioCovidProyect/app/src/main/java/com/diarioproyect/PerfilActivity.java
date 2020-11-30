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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.diarioproyect.Adapter.PostAdapter;
import com.diarioproyect.Model.Like;
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
import java.util.List;

public class PerfilActivity extends AppCompatActivity {

    private ImageView imagen_perfil;
    private TextView username;

    private DatabaseReference reference;
    private Intent intent;

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> mPosts;


    private List<String> usernameList;
    private List<String> imgURLList;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Perfil");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        imagen_perfil = findViewById(R.id.imagen_perfil);
        username = findViewById(R.id.username);

        intent = getIntent();
        userID = intent.getStringExtra("Perfil_Usuario_ID");



        reference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                username.setText(user.getUsername());

                if(user.getImageURL().equals("default"))
                    imagen_perfil.setImageResource(R.drawable.ic_person_red);
                else
                    Glide.with(PerfilActivity.this).load(user.getImageURL()).into(imagen_perfil);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(PerfilActivity.this));

        leerPost();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.refresh:
                Intent intent = new Intent(this, PerfilActivity.class);
                intent.putExtra("Perfil_Usuario_ID", userID);
                finish();
                startActivity(intent);

                return true;

        }
        return false;
    }

    private void leerPost(){
        mPosts = new ArrayList<>();
        usernameList = new ArrayList<>();
        imgURLList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Posts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mPosts.clear();
                usernameList.clear();
                imgURLList.clear();

                for (final DataSnapshot snapshot : dataSnapshot.getChildren()){
                    final Post post = snapshot.getValue(Post.class);

                    if(post.getSender().equals(userID)){
                        post.setPostAdmin(isPostAdmin(post));
                        post.setEstado_like(comprobarLike(post));
                        mPosts.add(0,post);
                        leerUsuarios(post);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void leerUsuarios(final Post post){

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                    final User user = snapshot1.getValue(User.class);

                    if (user.getId().equals(post.getSender())) {
                        usernameList.add(0, user.getUsername());
                        imgURLList.add(0, user.getImageURL());

                        postAdapter = new PostAdapter(PerfilActivity.this, mPosts, usernameList, imgURLList);
                        recyclerView.setAdapter(postAdapter);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean isPostAdmin(final Post post1) {
       final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference();
        Query databaseQuery = reference.child("Posts").orderByChild("id").equalTo(post1.getId());

        databaseQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);

                    if (post.getSender().equals(firebaseUser.getUid()))
                        post1.setPostAdmin(true);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return post1.isPostAdmin();
    }

    private boolean comprobarLike(final Post post1){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Posts").orderByChild("id")
                .equalTo(post1.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    snapshot.getRef().child("Likes").orderByChild("sender")
                            .equalTo(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                                Like like = snapshot1.getValue(Like.class);
                                if (like.getSender().equals(firebaseUser.getUid()))
                                    post1.setEstado_like(true);
                                else
                                    post1.setEstado_like(false);
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

        return post1.isEstado_like();
    }
}
