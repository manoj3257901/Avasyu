package com.manoj.avasyu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class SignUp extends AppCompatActivity {
    DatabaseReference admin= FirebaseDatabase.getInstance().getReference().child("admin");
    private static final int MY_REQUEST_CODE =2811 ; //Any number
    List<AuthUI.IdpConfig> providers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init providers
        providers = Arrays.asList(
                //google builder,
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.EmailBuilder().build()

        );
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            showSignInOptions();
        }
        else {
            admin.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        startActivity(new Intent(getApplicationContext(),AdminPage.class));
                        finish();
                    }
                    else {
                        Intent homeIntent = new Intent(getApplicationContext(), HomePage.class);
                        startActivity(homeIntent);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    private void showSignInOptions() {
        //noinspection deprecation
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
//                        .setTheme(R.style.SignIn)
                        //.setLogo(R.drawable.avasyu)
                        .build(),MY_REQUEST_CODE
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==MY_REQUEST_CODE)
        {
            IdpResponse response=IdpResponse.fromResultIntent(data);
            if(resultCode!=RESULT_OK)
            {
                Toast.makeText(getApplicationContext(),""+response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
            else
            {

                admin.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            startActivity(new Intent(getApplicationContext(),AdminPage.class));
                            finish();
                        }
                        else {
                            Intent homeIntent = new Intent(getApplicationContext(), HomePage.class);
                            startActivity(homeIntent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        }
    }
}


