package sg.edu.rp.c347.p05ndpsongs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class SecondActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Songs> songs;
    CustomAdapter customAdapter;
    Button btn;
    Spinner spn;

    ArrayList<String> alSongs = new ArrayList<String>();
    ArrayAdapter<String> aaSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        DBHelper db = new DBHelper(SecondActivity.this);

        songs = new ArrayList<Songs>();
        lv = (ListView)findViewById(R.id.lv);
        btn = (Button)findViewById(R.id.button);
        spn = (Spinner)findViewById(R.id.spn);
        customAdapter = new CustomAdapter(SecondActivity.this, R.layout.row, songs);

        aaSongs = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, alSongs);
        spn.setAdapter(aaSongs);
        songs.addAll(db.getAllNotes());

        for (int i = 0; i < songs.size(); i++) {
            Log.d("Database Content", i +". "+songs.get(i));

            String year = Integer.toString(songs.get(i).getYear());
            aaSongs.add(year);
        }

        aaSongs.notifyDataSetChanged();



        lv.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();
        db.close();



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(SecondActivity.this);
                songs.clear();
                songs.addAll(db.getSongsWith5Stars());
                lv.setAdapter(customAdapter);
                customAdapter.notifyDataSetChanged();
                db.close();
            }
        });



    }
}
