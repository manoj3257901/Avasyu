package com.manoj.avasyu;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView username, useremail;
    Button signout;
    ImageView userimage;
    ListView ngolist;
    ArrayList<String> ngo=new ArrayList<>();
    DrawerLayout drawer;
    DatabaseReference ngodb= FirebaseDatabase.getInstance().getReference().child("ngo");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
////        ngolist=findViewById(R.id.ngolv);
//        ngo.add("Element 1");
//        ngo.add("Element 1");
//        ngo.add("Element 1");
//        ngo.add("Element 1");
//        ngo.add("Element 1");
//        ngo.add("Element 1");
//        ngo.add("Element 1");
//        ngo.add("Element 1");
//        CustomAdapter customAdapter=new CustomAdapter();
//        ngolist.setAdapter(customAdapter);
        //navigation drawer
        drawer=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nac_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //navigation drawer


        signout = (Button) navigationView.getHeaderView(0).findViewById(R.id.signout);
        username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.username);
        useremail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.useremail);
        userimage=(ImageView) navigationView.getHeaderView(0).findViewById(R.id.userimage);
        username.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        useremail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        Picasso.get()
                .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl())
                .centerInside()
                .fit()
                .placeholder(R.drawable.avasyu)
                .into(userimage);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(getApplicationContext())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                signout.setEnabled(false);
                                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });





        ngodb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

//
//    class CustomAdapter extends BaseAdapter{
//        @Override
//        public int getCount() {
//
//            return ngo.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return ngo.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
//            convertView=inflater.inflate(R.layout.ngo_list_item, null,true);
//            TextView name,desc;
//            name=convertView.findViewById(R.id.ngoname);
//            desc=convertView.findViewById(R.id.ngodesc);
//            name.setText(ngo.get(position));
//
//            return convertView;
//        }
//    }
}



