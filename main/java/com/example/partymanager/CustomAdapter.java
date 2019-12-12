package com.example.partymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> titles;
    ArrayList<String> notes;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, ArrayList<String> titles, ArrayList<String> notes) {
        this.context = context;
        this.titles = titles;
        this.notes = notes;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public void addNote(String title, String note){
        titles.add(title);
        notes.add(note);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.list_item, null);
        TextView title = (TextView) view.findViewById(R.id.text);
        title.setText(titles.get(i));
        return view;
    }
}
