package com.kocheng.mealdb;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;


public class ActivityDetailMeal extends AppCompatActivity {

    public static String EXTRA_NAME = "extra_name";
    public static String EXTRA_INSTRUCTIONS = "extra_overview";
    public static String EXTRA_AREA = "extra_area";
    public static String EXTRA_IMAGE = "extra_image";
    public static String EXTRA_CATEGORY = "extra_category";

    private TextView tvName, tvInstructions, tvArea, tvCategory;
    private ImageView imgMovie;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_meal);
        //get intent
        String name = getIntent().getStringExtra(EXTRA_NAME);
        String category = getIntent().getStringExtra(EXTRA_CATEGORY);
        String area = getIntent().getStringExtra(EXTRA_AREA);
        String instructions = getIntent().getStringExtra(EXTRA_INSTRUCTIONS);
        String image = getIntent().getStringExtra(EXTRA_IMAGE);

        tvName = (TextView) findViewById(R.id.tv_detail_name);
        tvInstructions = (TextView) findViewById(R.id.tv_detail_instructions);
        tvArea = (TextView) findViewById(R.id.tv_detail_area);
        tvCategory = (TextView) findViewById(R.id.tv_detail_category);
        imgMovie = (ImageView) findViewById(R.id.image_meal);

        //pass value using set function
        tvName.setText(name);
        tvArea.setText(area);
        tvInstructions.setText(instructions);
        tvCategory.setText(category);
        Picasso.get().load(image).into(imgMovie);
    }
}