package com.example.mytask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewContent extends AppCompatActivity {


    ListView list;
    ContactDb db;
    ArrayList<String> arr1;
    ArrayList<String> arr2;
    ArrayList<String> arr3;
    TextView c_name, c_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_content);


        list = findViewById(R.id.view_data);
        db = new ContactDb(getApplicationContext());
        db.getReadableDatabase();

        arr1 = new ArrayList<>();
        arr2 = new ArrayList<>();


            arr1 = db.extractName();
            arr2 = db.extractNumber();

        if(arr1!=null && arr1.size() > 0 )
        {
            MyAdapter adapter = new MyAdapter();
            list.setAdapter(adapter);
        }



    }


    private class MyAdapter extends BaseAdapter {


        LayoutInflater inflater;

        public MyAdapter() {
            inflater = (LayoutInflater) ViewContent.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public int getCount() {
            return arr1.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder holder = null;

            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.update, null);
                //id = (TextView) view.findViewById(R.id.txtId);
                holder.cName = view.findViewById(R.id.txtName);
                holder.phoneNumber = view.findViewById(R.id.txtPassword);

                holder.cName.setText(arr1.get(position));
                holder.phoneNumber.setText(arr2.get(position));

            } else {
                view.setTag(holder);
            }

            return view;
        }

        @Override
        public int getViewTypeCount() {
            return getCount();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }


    }

    public static class ViewHolder {
        public TextView cName, phoneNumber;

    }
}
