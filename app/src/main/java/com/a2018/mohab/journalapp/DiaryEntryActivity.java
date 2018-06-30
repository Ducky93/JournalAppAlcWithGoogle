package com.a2018.mohab.journalapp;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.a2018.mohab.journalapp.DatabaseUtils.DbManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DiaryEntryActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    ListView diaryEntryListView;
    List<DiaryEntry> entryList = new ArrayList<>();
    ConstraintLayout mConstraintLayout;
    Button addDiaryEntryButton;
    String newDiaryEntryTitle;
    String newDiaryEntryDescription;
    String diaryId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_entry);
        Intent diaryIntent = getIntent();
        diaryId = diaryIntent.getStringExtra(Namings.diaryId.toString());
        String diaryName = diaryIntent.getStringExtra(Namings.diaryName.toString());

        TextView diaryNameTxtView = findViewById(R.id.txtDiaryName);
        diaryNameTxtView.setText(diaryName);
        databaseReference = new DbManager(Namings.DiaryEntries.toString()).db.child(diaryId);

        diaryEntryListView = findViewById(R.id.listViewDiaryEntries);
        addDiaryEntryButton=findViewById(R.id.btnAddNewDiaryEntry);
        mConstraintLayout= findViewById(R.id.diaryEntryConstraintLayout);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                entryList.clear();
                for(DataSnapshot dataSnap : dataSnapshot.getChildren()){
                    entryList.add(dataSnap.getValue(DiaryEntry.class));
                }
                DiaryEntryListAdapter diaryEntryListAdapter = new DiaryEntryListAdapter(
                        DiaryEntryActivity.this,entryList,diaryId);

                diaryEntryListView.setAdapter(diaryEntryListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Snackbar.make(mConstraintLayout,"Operation cancelled!",Snackbar.LENGTH_SHORT);
            }
        });
        addDiaryEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });
    }
    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("^_^ ** Enter the diary entry details ** ^_^");
        LinearLayout layout = new LinearLayout(this);

        // Set up the input
        final EditText title = new EditText(this);
        title.setHint("Title");
        layout.addView(title);
        // Set up the input
        final EditText description = new EditText(this);
        description.setHint("Description");
        layout.addView(description);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        title.setInputType(InputType.TYPE_CLASS_TEXT);
        description.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(layout);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newDiaryEntryTitle = title.getText().toString();
                newDiaryEntryDescription = description.getText().toString();
                if(TextUtils.isEmpty(newDiaryEntryTitle)){
                    Snackbar.make(mConstraintLayout,"I know it's hard , but try picking a name for that new diary entry of yours :) !",Snackbar.LENGTH_LONG).show();
                }else{
                    addDiaryEntry();
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

    private void addDiaryEntry() {
//        String diaryTitle = newDiaryEntryTitle;
        String diaryDescription = newDiaryEntryDescription;

        String id = databaseReference.push().getKey();
        DiaryEntry diaryEntry = new DiaryEntry(id ,newDiaryEntryTitle,diaryDescription);
        databaseReference.child(id).setValue(diaryEntry);

        Snackbar.make(mConstraintLayout,"Diary Entry have been added! :)",Snackbar.LENGTH_LONG).show();
    }
}
