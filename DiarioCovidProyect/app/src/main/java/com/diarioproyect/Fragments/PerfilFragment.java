package com.diarioproyect.Fragments;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.diarioproyect.Adapter.PostAdapter;
import com.diarioproyect.Model.Like;
import com.diarioproyect.Model.Post;
import com.diarioproyect.Model.User;
import com.diarioproyect.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class PerfilFragment extends Fragment {

    private ImageView imagen_perfil;
    private TextView username;
    private ProgressBar progressBar;

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    private StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> mPosts;


    private List<String> usernameList;
    private List<String> imgURLList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        imagen_perfil = view.findViewById(R.id.imagen_perfil);
        username = view.findViewById(R.id.username);
        progressBar = view.findViewById(R.id.progressbar);

        storageReference = FirebaseStorage.getInstance().getReference("Uploads");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                username.setText(user.getUsername());

                if(user.getImageURL().equals("default"))
                    imagen_perfil.setImageResource(R.drawable.ic_person_red);
                else
                    Glide.with(PerfilFragment.this).load(user.getImageURL()).into(imagen_perfil);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        imagen_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirImagen();
            }
        });

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        leerPost();
        return view;
    }

    private void abrirImagen(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);

    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(){
        progressBar.setVisibility(View.VISIBLE);

        if(imageUri != null){
            final StorageReference filereference = storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtension(imageUri));

            uploadTask = filereference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw  task.getException();
                    }

                    return filereference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("imageURL", mUri);
                        reference.updateChildren(hashMap);

                        progressBar.setVisibility(View.INVISIBLE);
                        
                    }else{
                        Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }else{
            Toast.makeText(getContext(), "No se ha seleccionado ninguna imagen", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
        && data != null && data.getData() != null){
            imageUri = data.getData();

            if(uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText(getContext(), "En proceso", Toast.LENGTH_SHORT).show();
            }else
                uploadImage();
        }
    }

    private void leerPost(){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
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

                    if(post.getSender().equals(firebaseUser.getUid())){
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

    private boolean comprobarLike(final Post post1){
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
