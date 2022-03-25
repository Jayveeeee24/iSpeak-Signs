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
import java.util.List;

public class SearchCategoryListAdapter extends RecyclerView.Adapter<SearchCategoryListAdapter.ViewHolder> implements Filterable {

    ArrayList<SearchCategoryItem> searchCategoryItems = new ArrayList<>();
    ArrayList<SearchCategoryItem> searchCategoryItemsFull;

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
       holder.searchItemName.setText(searchCategoryItems.get(position).getSearchItemName());

       holder.searchCard.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //TODO make this an actual method of going into the item fragment
           }
       });
    }

    @Override
    public int getItemCount() {
        return searchCategoryItems.size();
    }

    @Override
    public Filter getFilter() {
        return categoryFilter;
    }

    private final Filter categoryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<SearchCategoryItem> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0){
                filteredList.addAll(searchCategoryItemsFull);
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (SearchCategoryItem item : searchCategoryItemsFull) {
                    if(item.getSearchItemName().toLowerCase().contains(filterPattern)){
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
            searchCategoryItems.clear();
            searchCategoryItems.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

    public void setSearchCategoryItems(ArrayList<SearchCategoryItem> searchCategoryItems) {
        this.searchCategoryItems = searchCategoryItems;
        searchCategoryItemsFull = new ArrayList<>(this.searchCategoryItems);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView searchItemName;
        CardView searchCard;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            searchItemName = itemView.findViewById(R.id.search_item_name);
            searchCard = itemView.findViewById(R.id.card_search_item_parent);
        }
    }
}
