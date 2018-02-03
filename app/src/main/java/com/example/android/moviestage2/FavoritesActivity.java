package com.example.android.moviestage2;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.moviestage2.data.MovieContract;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    /** Adapter for the gridview of personal favorite movies */
    FavoriteCursorAdapter favoriteCursorAdapter;
    private ArrayList favoritesList;
    public GridView favoriteGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favoriteGridView = (GridView) findViewById(R.id.favoritesGrid);
// Find and set empty view on the GridView, so that it only shows when the grid has 0 movies.
        View emptyView = findViewById(R.id.empty_view);
        favoriteGridView.setEmptyView(emptyView);

        favoriteCursorAdapter = new FavoriteCursorAdapter(this, null);
        favoriteGridView.setAdapter(favoriteCursorAdapter);



        Toast.makeText(FavoritesActivity.this, "Personal Favorites Database", Toast.LENGTH_LONG);

        Spinner mSpinner = (Spinner) findViewById(R.id.spnPopOrRatedOrFavorite);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this,
                R.array.movie_choices, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //  1-18-18  Null pointer exception      mSpinner.setAdapter(spinAdapter);
        Toast.makeText(this, "Personal Favorites Database again", Toast.LENGTH_LONG).show();
        Log.i("ONCREATELOADER... ","FavoritesActivity: " );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_favorites, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertProduct();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                //deleteAllProducts();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Helper method to insert hardcoded product data into the database. For debugging purposes only.
     */
    private void insertProduct() {

        //String defaultUriString = "drawable/grocerycart.png";
        //int resID = getResources().getIdentifier("grocerycart" , "drawable", getPackageName());

        //Uri imageUri = Uri.parse(defaultUriString);

        //imageUri =  getUriToDrawable(this, resID);

        // Create a ContentValues object where column names are the keys,
        // and hammer's attributes are the values.
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE, "hammer");
        //values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, "10");
        // values.put(ProductEntry.COLUMN_PRODUCT_PRICE, "555.99");
        //values.put(ProductEntry.COLUMN_PRODUCT_VENDOR, "Home Depot");
        //values.put(ProductEntry.COLUMN_PRODUCT_IMAGE, String.valueOf(imageUri)); // 8/2/17 9:44AM program did not crash yet

        // Insert a new row for hammer into the provider using the ContentResolver.
        // Use the {@link ProductEntry#CONTENT_URI} to indicate that we want to insert
        // into the products database table.
        // Receive the new content URI that will allow us to access the hammer's data in the future.
        Uri newUri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
