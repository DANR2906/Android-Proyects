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

import com.diarioproyect.Adapter.ComentAdapter;
import com.diarioproyect.Adapter.LikeAdapter;
import com.diarioproyect.Model.Comentario;
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

public class LikesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private LikeAdapter likeAdapter;
    private List<Like> mLikes;


    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    private List<String> usernameList;
    private List<String> imgURLList;
    private Intent intent;

    private String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes);

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

        leerLikes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_likes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.refresh:
                Intent intent = new Intent(this, LikesActivity.class);
                intent.putExtra("PostID", postId);
                finish();
                startActivity(intent);

                return true;

        }
        return false;
    }

    private void leerLikes(){
        mLikes = new ArrayList<>();
        usernameList = new ArrayList<>();
        imgURLList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child("Posts").orderByChild("id").equalTo(postId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    snapshot.getRef().child("Likes").addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                            mLikes.clear();
                            usernameList.clear();
                            imgURLList.clear();

                            for(DataSnapshot snapshot1 : dataSnapshot1.getChildren()){

                                final Like like = snapshot1.getValue(Like.class);
                                like.setLikeAdmin(isLikeAdmin(like));

                                mLikes.add(0,like);
                                leerUsuarios(like);

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

    private void leerUsuarios(final Like like){

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {

                for(DataSnapshot snapshot2 : dataSnapshot2.getChildren()){
                    final User user = snapshot2.getValue(User.class);

                    if(user.getId().equals(like.getSender())) {

                        usernameList.add(0, user.getUsername());
                        imgURLList.add(0, user.getImageURL());

                        LikeAdapter likeAdapter = new LikeAdapter(LikesActivity.this, mLikes, usernameList, imgURLList);
                        recyclerView.setAdapter(likeAdapter);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean isLikeAdmin(final Like like) {


        reference = FirebaseDatabase.getInstance().getReference();
        Query databaseQuery = reference.child("Posts").orderByChild("id").equalTo(postId);

        databaseQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);

                    if(like.getSender().equals(firebaseUser.getUid()))
                        like.setLikeAdmin(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return like.isLikeAdmin();
    }
}
