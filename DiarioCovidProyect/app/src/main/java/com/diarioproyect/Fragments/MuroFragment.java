package com.diarioproyect.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.diarioproyect.Adapter.PostAdapter;
import com.diarioproyect.MainActivity;
import com.diarioproyect.Model.Comentario;
import com.diarioproyect.Model.Like;
import com.diarioproyect.Model.Post;
import com.diarioproyect.Model.User;
import com.diarioproyect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MuroFragment extends Fragment {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> mPosts;


    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    private List<String> usernameList;
    private List<String> imgURLList;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_muro, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        leerPost();
        return view;
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

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    final Post post = snapshot.getValue(Post.class);
                    post.setPostAdmin(isPostAdmin(post));
                    post.setEstado_like(comprobarLike(post));

                    mPosts.add(0,post);
                    leerUsuarios(post);
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

                            postAdapter = new PostAdapter(getContext(), mPosts, usernameList, imgURLList);
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

    public boolean comprobarLike(final Post post1){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
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
