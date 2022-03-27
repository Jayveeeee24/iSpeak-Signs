package com.artemis.ispeaksigns.adapter_list_search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.artemis.ispeaksigns.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SearchWordListAdapter extends RecyclerView.Adapter<SearchWordListAdapter.ViewHolder> implements Filterable {

    ArrayList<SearchWordItem> searchWordItems = new ArrayList<>();
    ArrayList<SearchWordItem> searchWordItemsFull;

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
        holder.searchItemName.setText(searchWordItems.get(position).getItemName());

        holder.cardItemSearchParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("learn_word_item", searchWordItems.get(position).getItemName());
                NavOptions.Builder navBuilder = new NavOptions.Builder();
                navBuilder.setEnterAnim(R.anim.nav_default_enter_anim)
                        .setExitAnim(R.anim.nav_default_exit_anim)
                        .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                        .setPopExitAnim(R.anim.nav_default_pop_exit_anim);
                Navigation.findNavController(view).navigate(R.id.action_nav_search_to_learn_word_item, bundle, navBuilder.build());
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchWordItems.size();
    }

    @Override
    public Filter getFilter() {
        return WordFilter;
    }

    private Filter WordFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<SearchWordItem> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0){
                filteredList.addAll(searchWordItemsFull);
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (SearchWordItem item : searchWordItemsFull) {
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
            searchWordItems.clear();
            searchWordItems.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };



    public void setSearchWordItems(ArrayList<SearchWordItem> searchWordItems) {
        this.searchWordItems = searchWordItems;
        searchWordItemsFull = new ArrayList<>(this.searchWordItems);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView searchItemName;
        CardView cardItemSearchParent;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            searchItemName = itemView.findViewById(R.id.search_item_name);
            cardItemSearchParent = itemView.findViewById(R.id.card_search_item_parent);
        }
    }
}
