package com.a2018.mohab.journalapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.a2018.mohab.journalapp.DatabaseUtils.DbManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DiaryActivity extends AppCompatActivity {
    DbManager dbManager;
    Button btnCreateDiary;
    String newDiaryName;
    ConstraintLayout mConstraintLayout;
    ListView dairiesListView;
    List<Diary> diaryList;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        mContext=this;
        dbManager = new DbManager(Namings.Diaries.toString());

        mConstraintLayout = findViewById(R.id.layoutDiaries);
        btnCreateDiary = findViewById(R.id.btnCreateDiary);
        
        btnCreateDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });
        updateTheDiariesListView();
    }

    private void addDiary() {

        String name = newDiaryName;
//        String name = editTextTitle.getText().toString().trim();
//        String genre = spinnerGenres.getSelectedItem().toString();
//
        String id = dbManager.db.push().getKey();
        Diary diary = new Diary(name ,id);
        dbManager.db.child(id).setValue(diary);
        Snackbar.make(mConstraintLayout,"Diary added",Snackbar.LENGTH_SHORT).show();

    }

    private void updateTheDiariesListView() {
        dairiesListView = findViewById(R.id.listViewDiaries);
        diaryList = new ArrayList<>();
        dbManager.db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                diaryList.clear();
                for(DataSnapshot dataSnap : dataSnapshot.getChildren()){
                    diaryList.add(dataSnap.getValue(Diary.class));
                }
                DiaryListAdapter diaryListAdapter = new DiaryListAdapter(DiaryActivity.this,diaryList);
                dairiesListView.setAdapter(diaryListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Snackbar.make(mConstraintLayout,"Operation cancelled!",Snackbar.LENGTH_SHORT);
            }
        });
        dairiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Diary diary = diaryList.get(position);
                Intent diaryEntriesIntent = new Intent(getApplicationContext(),DiaryEntryActivity.class);
                diaryEntriesIntent.putExtra(Namings.diaryId.toString(),diary.diaryId);
                diaryEntriesIntent.putExtra(Namings.diaryName.toString(),diary.getName());
                startActivity(diaryEntriesIntent);
            }
        });
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("^_^ ** Enter the diary name ** ^_^");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newDiaryName = input.getText().toString();
                if(TextUtils.isEmpty(newDiaryName)){
                    Snackbar.make(mConstraintLayout,"I know it's hard , but try picking a name for that new diary of yours :) !",Snackbar.LENGTH_LONG).show();
                }else{
                    addDiary();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

}
