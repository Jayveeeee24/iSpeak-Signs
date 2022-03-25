package com.artemis.ispeaksigns.adapter_list_search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.artemis.ispeaksigns.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SearchVideoListAdapter extends RecyclerView.Adapter<SearchVideoListAdapter.ViewHolder> implements Filterable {

    ArrayList<SearchVideoItem> searchVideoItems = new ArrayList<>();
    ArrayList<SearchVideoItem> searchVideoItemsFull;

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.itemSearchName.setText(searchVideoItems.get(position).getItemName());

        holder.cardItemSearchParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO MAKE THIS AN ACTUAL COMMAND TO GO TO THE LEARN ITEM FRAGMNENT
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchVideoItems.size();
    }

    @Override
    public Filter getFilter() {
        return VideoFilter;
    }

    private Filter VideoFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<SearchVideoItem> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0){
                filteredList.addAll(searchVideoItemsFull);
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (SearchVideoItem item : searchVideoItemsFull) {
                    if(item.getItemName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            searchVideoItems.clear();
            searchVideoItems.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

    public void setSearchVideoItems(ArrayList<SearchVideoItem> searchVideoItems) {
        this.searchVideoItems = searchVideoItems;
        searchVideoItemsFull = new ArrayList<>(this.searchVideoItems);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemSearchName;
        CardView cardItemSearchParent;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            itemSearchName = itemView.findViewById(R.id.search_item_name);
            cardItemSearchParent = itemView.findViewById(R.id.card_search_item_parent);
        }
    }
}
