package com.example.arif.contacts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Arif on 9/18/2017.
 */

public class Custom_List_Adapter extends BaseAdapter {


        private Context context;private int layout;
        ArrayList< myDataRetriver > textList;
        public Custom_List_Adapter(Context context, int layout, ArrayList< myDataRetriver > textList)
        {
            this.context = context;
            this.layout = layout;
            this.textList = textList;
        }
        @Override
        public int getCount()
        {
            return textList.size();
        }

        @Override
        public Object getItem(int position) {
            return textList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder
        {
            ImageView imageView1;
            TextView textName;
        }
        @Override
        public View getView(int position, View view, ViewGroup parent)
        {
            View row = view;

            ViewHolder holder;

            if (row == null) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layout, null);
                holder= new ViewHolder();
                holder.textName = (TextView) row.findViewById(R.id.Cus_List_TV);
                holder.imageView1 = (ImageView) row.findViewById(R.id.Cus_List_IV);
                row.setTag(holder);

            }
            else
            {
                holder =(ViewHolder) row.getTag();

            }
            final  myDataRetriver  food = textList.get(position);
            holder.textName.setText(food.getName());
            byte[] foodImage = food.getImage();
            final Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0 , foodImage.length);
            holder.imageView1.setImageBitmap(bitmap);

            return row;
        }

}
