package com.isumalab.learn.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.isumalab.learn.R;
import com.isumalab.learn.activities.CourseOverviewActivity;
import com.isumalab.learn.models.SearchCourseItem;

import java.util.ArrayList;

public class SearchSectionListDataAdapter extends RecyclerView.Adapter<SearchSectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<SearchCourseItem> itemsList;
    private Context mContext;

    public SearchSectionListDataAdapter(Context context, ArrayList<SearchCourseItem> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        SearchCourseItem singleItem = itemsList.get(i);

        holder.tvTitle.setText(singleItem.getName());


       /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle;

        protected ImageView itemImage;


        public SingleItemRowHolder(View view) {
            super(view);

            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SearchCourseItem searchCourseItem = itemsList.get(getLayoutPosition());
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(view.getContext(), CourseOverviewActivity.class);
                    bundle.putString("name", searchCourseItem.getName());
                    bundle.putString("code", searchCourseItem.getCode());
                    intent.putExtras(bundle);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}