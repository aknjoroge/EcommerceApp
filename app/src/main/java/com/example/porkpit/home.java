package com.example.porkpit;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.paperdb.Paper;

public class home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    DrawerLayout nav;
    FirebaseAuth fAuth;
    String htm;
    String dataloc ="";
    StorageReference storageReference;
public RecyclerView recyclerView;
RecyclerView.LayoutManager layoutManager;
    FirebaseUser using;
    FirebaseFirestore fStore;
    ImageView himage;
    String userid;
    TextView pname,pmail;


    @Override
    protected void onStart() {

        super.onStart();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Paper.init(this);

        String aq=getIntent().getStringExtra("catvalue");


        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fAuth = FirebaseAuth.getInstance();
recyclerView=findViewById(R.id.recyclermenumain);
recyclerView.setHasFixedSize(true);
layoutManager = new LinearLayoutManager(this);
recyclerView.setLayoutManager(layoutManager);
        using=fAuth.getCurrentUser();
        fStore = FirebaseFirestore.getInstance();
        userid = fAuth.getCurrentUser().getUid();
        storageReference = FirebaseStorage.getInstance().getReference();

dataloc=aq;


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),cart.class));
            }
        });


       nav = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, nav, toolbar, R.string.nav_opener, R.string.nav_closer);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toggle.getDrawerArrowDrawable().setColor(getColor(R.color.white));
        }
        nav.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerview = navigationView.getHeaderView(0);
        pname=headerview.findViewById(R.id.profilename);
        pmail=headerview.findViewById(R.id.profilemail);
        himage=headerview.findViewById(R.id.profileimage);
        String a ="alex";
        String b ="alex";


        StorageReference profileref = storageReference.child("Userprofile/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(himage);
                // Picasso.get().load(uri).into(nav_image);

            }
        });

        loaddetails();

        loadaction();


    }

    private void loadaction() {
        FirestoreRecyclerOptions<products> options = new FirestoreRecyclerOptions.Builder<products>()
                .setQuery(fStore.collection("Products").document("all").collection(dataloc),products.class).build();
        FirestoreRecyclerAdapter<products,productviewholder> adapter= new FirestoreRecyclerAdapter<products, productviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull productviewholder holder, int position, @NonNull final products model) {
                holder.txtpname.setText(model.getName()+".");
                holder.txtpprice.setText("Price: "+model.getPrice());
                holder.txtpdescription.setText("Category: "+model.getCategory());

//htm="https://firebasestorage.googleapis.com/v0/b/porkpit-fe8ac.appspot.com/o/Users%2FbG6dcaszUfaJchQOBGLhxbLMDHG3%2Fprofile.jpg?alt=media&token=206700b5-1258-456c-bf8f-3de94457af3d";


                Picasso.get().load(model.getFilepath()).into(holder.pimage);
                //   Toast.makeText(home.this, htm, Toast.LENGTH_SHORT).show();
                //Toast.makeText(home.this, model.getFilepath(), Toast.LENGTH_SHORT).show();
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(home.this,productdetails.class);
                        intent.putExtra("pid",model.getRandomKey());
                        intent.putExtra("cid",model.getCategory());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public productviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.productitem,parent,false);
                productviewholder holder =new productviewholder(view);
                return holder;



            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }


    private void loaddetails2() {
        pname.setText("alex");
        pmail.setText("alex@porkpit.co.ke");
    }

    private void loaddetails() {

        final DocumentReference documentReference = fStore.collection("users").document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String nametake=documentSnapshot.getString("name");
                String mailtake =documentSnapshot.getString("mail");

              pname.setText(documentSnapshot.getString("name"));
              pmail.setText(documentSnapshot.getString("mail"));
            }
        });







    }

    @Override
    public void onBackPressed() {
        if(nav.isDrawerOpen(GravityCompat.START)){
            nav.closeDrawer(GravityCompat.START);
        }else {super.onBackPressed();}

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_cart:
                startActivity(new Intent(getApplicationContext(),cart.class));

                break;
            case R.id.nav_setting:
startActivity(new Intent(getApplicationContext(),settings.class));
                break;
            case R.id.nav_blog:
              startActivity(new Intent(getApplicationContext(),menu.class));
                break;
            case R.id.nav_about:
                startActivity(new Intent(getApplicationContext(),aboutactivity.class));
                break;
            case R.id.nav_logout:
                Paper.book().write(prevalent.userpasskey,"");
                Paper.book().write(prevalent.useremailkey,"");
             startActivity(new Intent(getApplicationContext(),start.class));
             finish();
                break;


        }
        nav.closeDrawer(GravityCompat.START);

        return true;
    }






}