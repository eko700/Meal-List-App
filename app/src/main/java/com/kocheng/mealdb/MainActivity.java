package com.kocheng.mealdb;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<ArrayList<MealItem>>{

    private Button btnSearch;
    private ListView lvMovie;
    private EditText etKeyword;
    private ImageView imgMovie;
    private MealAdapter adapter;

    static final String EXTRAS_MOVIE = "extra_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        adapter = new MealAdapter(this);
        adapter.notifyDataSetChanged();

        lvMovie = (ListView) findViewById(R.id.lv_movie);
        etKeyword = (EditText) findViewById(R.id.et_keyword);
        imgMovie = (ImageView) findViewById(R.id.image_meal);

        btnSearch = (Button) findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(movieListener);

        String title = etKeyword.getText().toString();
        lvMovie.setAdapter(adapter);
        lvMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {

                MealItem item = (MealItem) parent.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, ActivityDetailMeal.class);

                intent.putExtra(ActivityDetailMeal.EXTRA_NAME, item.getMealName());
                intent.putExtra(ActivityDetailMeal.EXTRA_INSTRUCTIONS, item.getMealInstructions());
                intent.putExtra(ActivityDetailMeal.EXTRA_AREA, item.getMealArea());
                intent.putExtra(ActivityDetailMeal.EXTRA_IMAGE, item.getMealImage());
                intent.putExtra(ActivityDetailMeal.EXTRA_CATEGORY, item.getMealCategory());

                startActivity(intent);
            }
        });

        //bundle is used to get data when button is clicked
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_MOVIE, title);

        getSupportLoaderManager().initLoader(0, bundle, this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        } else if (id == R.id.nav_abaout) {
            Intent a = new Intent(MainActivity.this, About.class);
            startActivity(a);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public Loader<ArrayList<MealItem>> onCreateLoader(int i, Bundle bundle) {
        String title = "";
        if (bundle != null) {
            title = bundle.getString(EXTRAS_MOVIE);
        }
        return new MealAsyncTaskLoader(this,title);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MealItem>> loader, ArrayList<MealItem> movies) {
        adapter.setData(movies);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MealItem>> loader) {
        adapter.setData(null);
    }

    //listener function if button is clicked, check value based on title
    View.OnClickListener movieListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String title = etKeyword.getText().toString();
            if (TextUtils.isEmpty(title)) {
                return;
            }

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_MOVIE, title);
            getSupportLoaderManager().restartLoader(0, bundle, MainActivity.this);
        }
    };

    public void refresh(MenuItem item) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
