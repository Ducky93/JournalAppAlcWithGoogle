package com.a2018.mohab.journalapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DiaryListAdapter extends ArrayAdapter<Diary> {
    private Activity mContext;
    private List<Diary> mList;
    public DiaryListAdapter(@NonNull Activity context, @NonNull List<Diary> diaryList) {
        super(context, R.layout.item_diary, diaryList);

        this.mContext = context;
        this.mList = diaryList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = mContext.getLayoutInflater();
        @SuppressLint("ViewHolder")
        View listViewItem = layoutInflater.inflate(R.layout.item_diary,parent,true);

        TextView diaryNameTxtView = listViewItem.findViewById(R.id.diaryName);
        Diary currentDiary = mList.get(position);
        diaryNameTxtView.setText(currentDiary.getName());
        return listViewItem;
    }
}
