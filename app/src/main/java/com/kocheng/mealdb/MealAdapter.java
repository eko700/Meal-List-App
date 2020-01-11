package com.kocheng.mealdb;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MealAdapter extends BaseAdapter {

    private ArrayList<MealItem> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;


    public MealAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<MealItem> items) {
        mData = items;
        notifyDataSetChanged();
    }

    public void addItem(final MealItem item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        //Check null if no value, return 0 no view
        if (mData == null) return 0;
        return mData.size();
    }

    @Override
    public MealItem getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //holder is used to hold value
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.meal_row_list, null);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvCategory = (TextView) convertView.findViewById(R.id.tv_category);
            holder.imgMeal = (ImageView) convertView.findViewById(R.id.image);
            holder.tvArea = (TextView) convertView.findViewById(R.id.tv_detail_area);
            holder.tvInstructions = (TextView) convertView.findViewById(R.id.tv_detail_instructions);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(mData.get(position).getMealName());
        holder.tvCategory.setText(mData.get(position).getMealCategory());
        holder.tvArea.setText(mData.get(position).getMealArea());
        holder.tvInstructions.setText(mData.get(position).getMealInstructions().substring(0,40)+"...");

        Picasso.get().load(mData.get(position).getMealImage()).
                placeholder(context.getResources().getDrawable(R.drawable.no_image)).
                error(context.getResources().getDrawable(R.drawable.no_image)).into(holder.imgMeal);

        return convertView;

    }

    private static class ViewHolder {
        TextView tvName;
        TextView tvCategory;
        ImageView imgMeal;
        TextView tvArea;
        TextView tvInstructions;
    }
}


