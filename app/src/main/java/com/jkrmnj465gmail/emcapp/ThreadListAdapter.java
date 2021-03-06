package com.jkrmnj465gmail.emcapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//Pretty much the same as SectionAdapter
public class ThreadListAdapter extends RecyclerView.Adapter<ThreadListAdapter.SectionViewHolder> {
    public final static String POST = "com.jkrmnj465gmail.emcapp.POST";
    public final static String NAME = "com.jkrmnj465gmail.emcapp.NAME";
    /*
     * This class has the purpose of making Recycler view useful and using certain mandatory methods.
     * Adapter is in charge of taking raw data (such as a string[]) and sorting it for the Recycler view
     * This gives recycler view info and views that are put into each parent.
     */
    //These hold the variables for the forum sections.
    private String[][] threads;
    private Bitmap[] avatars;

    //A helpful viewholder that gives the adapter access to all of the views and helps with performance
    //Important just to know that any view in the recycler goes here.
    public static final class SectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView thread_name;
        ImageView user_image;
        public MyViewHolderClicks mListener;

        public SectionViewHolder(View v, MyViewHolderClicks listener){
            super(v);
            mListener = listener;
            this.thread_name = (TextView) v.findViewById(R.id.title);
            this.user_image = (ImageView) v.findViewById(R.id.user);
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            mListener.onSectionClick(v, this.getPosition());
        }

        public interface MyViewHolderClicks {
            public void onSectionClick(View caller, int position);
        }
    }

    // Simple constructor
    public ThreadListAdapter(String[][] threads, Bitmap[] avatars) {
        this.threads = threads;
        this.avatars = avatars;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ThreadListAdapter.SectionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.thread_list_viewholder, viewGroup, false);
        // Do things like create the viewholder and return it. Also creates the onclick listener
        ThreadListAdapter.SectionViewHolder vh = new SectionViewHolder(v, new ThreadListAdapter.SectionViewHolder.MyViewHolderClicks() {
            //Modify this to change click behavior
            public void onSectionClick(View caller, int position) {
                Intent intent = new Intent(caller.getContext(),PostActivity.class);
                intent.putExtra(POST, threads[position][0]);
                intent.putExtra(NAME, threads[position][1]);
                caller.getContext().startActivity(intent);
            }
        });
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SectionViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if(threads[0][1].equals("abort")){
            holder.thread_name.setText("An error has occured. Please check your internet connection");
        }
        else {
            holder.thread_name.setText(threads[position][1]);
            holder.user_image.setImageBitmap(avatars[position]);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return threads.length;
    }
}
