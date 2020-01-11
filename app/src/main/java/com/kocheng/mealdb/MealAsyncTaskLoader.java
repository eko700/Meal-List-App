package com.kocheng.mealdb;


import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MealAsyncTaskLoader extends AsyncTaskLoader<ArrayList<MealItem>> {

    private ArrayList<MealItem> mData;
    private boolean mHasResult = false;

    private String mMealName;

    public MealAsyncTaskLoader(final Context context, String mealName) {
        super(context);

        onContentChanged();
        this.mMealName = mealName;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    //show result data
    public void deliverResult(ArrayList<MealItem> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }

    private void onReleaseResources(ArrayList<MealItem> data) {
    }

    @Override
    //get data synchronously
    public ArrayList<MealItem> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MealItem> meals = new ArrayList<>();

        //url format to get JSON value based on title
        String url = "https://www.themealdb.com/api/json/v1/1/search.php?s=" + mMealName;

        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                //load in background
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("meals");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject meal = list.getJSONObject(i);
                        MealItem mealItem = new MealItem(meal);
                        meals.add(mealItem);
                    }
                } catch (Exception e) {
                    //to avoid error parsing value
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //no method if fail
            }
        });
        return meals;
    }
}