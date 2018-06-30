package com.a2018.mohab.journalapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    TextInputEditText editTextTitle;
    Button btnLogin;
    Spinner spinnerGenres;
    Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent googleSignInIntent = new Intent(mContext, GoogleSignInActivity.class);
                startActivity(googleSignInIntent);
//                Intent diariesIntent = new Intent(mContext,DiaryActivity.class);
//                //TODO check login first later , we only skipping this for testing functionallity!! remmeber ducky
//                startActivity(diariesIntent);
            }
        });
    }
//    private void addArtist(){
//        String name = editTextTitle.getText().toString().trim();
//        String genre = spinnerGenres.getSelectedItem().toString();
//
//        if(!TextUtils.isEmpty(name)){
//
//            String id = dbManager.databaseArtists.push().getKey();
//
//            Artist artist = new Artist(id ,name,genre);
//
//            dbManager.databaseArtists.child(id).setValue(artist);
//            Toast.makeText(this,"Artist added",Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(this,"Please entry a name!" , Toast.LENGTH_SHORT).show();
//        }
//    }
}
