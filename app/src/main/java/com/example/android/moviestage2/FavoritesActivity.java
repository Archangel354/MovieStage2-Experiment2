package com.example.android.moviestage2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(FavoritesActivity.this, "Personal Favorites Database", Toast.LENGTH_LONG);

        Spinner mSpinner = (Spinner) findViewById(R.id.spnPopOrRatedOrFavorite);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this,
                R.array.movie_choices, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //  1-18-18  Null pointer exception      mSpinner.setAdapter(spinAdapter);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Personal Favorites Database again", Toast.LENGTH_LONG).show();
        Log.i("ONCREATELOADER... ","FavoritesActivity: " );


    }

}
