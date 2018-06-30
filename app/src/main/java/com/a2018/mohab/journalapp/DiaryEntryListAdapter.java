package com.a2018.mohab.journalapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a2018.mohab.journalapp.DatabaseUtils.DbManager;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class DiaryEntryListAdapter extends ArrayAdapter<DiaryEntry> {
    private Activity mContext;
    private List<DiaryEntry> mList;
    Button btnDeleteEntry;
    Button btnModifyEntry;
    DatabaseReference databaseReference;
    String diaryId;
    public DiaryEntryListAdapter(@NonNull Activity context, @NonNull List<DiaryEntry> entryList,String diaryId) {
        super(context, R.layout.item_diary_entry, entryList);

        this.mContext = context;
        this.mList = entryList;
        this.diaryId = diaryId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = mContext.getLayoutInflater();
        @SuppressLint("ViewHolder")
        View listViewItem = layoutInflater.inflate(R.layout.item_diary_entry,null,true);

        TextView diaryEntryTitleTextView = listViewItem.findViewById(R.id.txtDiaryEntryTitle);
        btnDeleteEntry = listViewItem.findViewById(R.id.btnDeleteDiaryItem);
        btnModifyEntry = listViewItem.findViewById(R.id.btnEditDiaryItem);
        final DiaryEntry currentDiaryEntry = mList.get(position);
        databaseReference = new DbManager(Namings.DiaryEntries.toString()).db.child(diaryId).child(currentDiaryEntry.getEntryId());
        btnDeleteEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.removeValue();
            }
        });
        btnModifyEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog(
                        currentDiaryEntry.getTitle(),
                        currentDiaryEntry.getDescription(),
                        currentDiaryEntry.getEntryId());
            }
        });
        diaryEntryTitleTextView.setText(currentDiaryEntry.getTitle());

        return listViewItem;
    }
    private void showUpdateDialog(final String diaryEntryTitle, String diaryEntryDescription, final String diaryEntryId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Updating the diary entry which title is " + diaryEntryTitle);
        LinearLayout layout = new LinearLayout(mContext);

        // Set up the input
        final EditText description = new EditText(mContext);
        description.setHint("Description");
        description.setText(diaryEntryDescription);
        layout.addView(description);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        description.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(layout);

        // Set up the buttons
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String descriptionText = description.getText().toString();
                updateDiaryEntry(diaryEntryId,diaryEntryTitle,descriptionText);
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
    private void updateDiaryEntry(String diaryEntryId,String title,String description){
        DiaryEntry diaryEntry = new DiaryEntry(diaryEntryId,title,description);
        databaseReference.setValue(diaryEntry);
        Toast.makeText(mContext,"Entry Updated",Toast.LENGTH_LONG).show();
    }
}
