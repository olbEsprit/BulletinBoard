package kampus.bulletinboard;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    //private ArrayList<String> mDataset;
    private Bulletin mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public TextView mTextView2;


        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.b_title);
            mTextView2 = (TextView) v.findViewById(R.id.b_text);
        }
    }

    public RecyclerAdapter(Bulletin dataset) {
       mDataset = dataset;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mTextView.setText(mDataset.title);
        holder.mTextView2.setText(mDataset.text);

    }

    @Override
    public int getItemCount() {
        return mDataset.hashCode(); //тут было mDataset.position, но у нас такого нет.
    }

    /*
    //Добавление/обновление объявлений прикручивать предположительно сюда
    // Clean all elements of the recycler
    public void clear() {
        mDataset.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<list> list) {
        mDataset.addAll(list);
        notifyDataSetChanged();
    }*/

}