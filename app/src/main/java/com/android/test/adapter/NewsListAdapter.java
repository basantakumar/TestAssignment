package com.android.test.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.test.R;
import com.android.test.data.DataModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Basanth on 5/20/2018.
 */
@SuppressWarnings("ALL")
public class NewsListAdapter extends ArrayAdapter<DataModel>{
    private final Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView mTitleTextView;
        TextView mDescriptionTextView;
        ImageView mRowImageView;
        String mUrlString;
    }

    public NewsListAdapter(ArrayList<DataModel> data, Context context) {
        super(context, R.layout.newslist_row, data);
        this.mContext=context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        DataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.newslist_row, parent, false);
            viewHolder.mTitleTextView = (TextView) convertView.findViewById(R.id.title_textView);
            viewHolder.mDescriptionTextView = (TextView) convertView.findViewById(R.id.desc_textView);
            viewHolder.mRowImageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(null!=dataModel) {
            if (!dataModel.getTitle().equalsIgnoreCase("null")) {
                viewHolder.mTitleTextView.setText(dataModel.getTitle());
            } else {
                viewHolder.mTitleTextView.setText(mContext.getResources().getString(R.string.no_title_text));
            }

            if (!dataModel.getDescription().equalsIgnoreCase("null")) {
                viewHolder.mDescriptionTextView.setText(dataModel.getDescription());
            } else {
                viewHolder.mDescriptionTextView.setText(mContext.getResources().getString(R.string.no_desc_text));
            }
            viewHolder.mUrlString = dataModel.getImg_url();
        }
        String TAG = "NewsListAdapter";
        Log.d(TAG, "getView: " + position + ": "+viewHolder.mUrlString);
        /**
         * the following code is used as alternative to Old Image loading library
         * placeholder -- default image
         * error -- if picasso  is not finding the image in the given url ,
         *          then this image is loaded
         */
        Picasso.with(mContext).load(viewHolder.mUrlString).fit().centerCrop()
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .into(viewHolder.mRowImageView);
        return convertView;
    }

}
