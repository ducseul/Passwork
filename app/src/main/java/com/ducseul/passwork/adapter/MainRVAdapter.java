package com.ducseul.passwork.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ducseul.passwork.R;
import com.ducseul.passwork.activity.MainActivity;
import com.ducseul.passwork.entity.AccountRecord;
import com.ducseul.passwork.entity.DataObject;
import com.ducseul.passwork.helper.KEY;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainRVAdapter extends RecyclerView.Adapter<MainRVAdapter.CustomeGroupHolder> implements Filterable {

    private MainActivity parent;
    private LayoutInflater mInflater;
    private ArrayList<AccountRecord> dataObjects;

    public MainRVAdapter(MainActivity activity) {
        this.parent = activity;
        this.mInflater = activity.getLayoutInflater();
        this.dataObjects = KEY.CACHE_DATA_OBJECT_MANAGER.getDataInstance().getListData();
    }

    public void setDataObjects(ArrayList<AccountRecord> dataObjects) {
        this.dataObjects = dataObjects;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainRVAdapter.CustomeGroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_list_account, parent, false);
        return new MainRVAdapter.CustomeGroupHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRVAdapter.CustomeGroupHolder holder, int position) {
        holder.account_site.setText(dataObjects.get(holder.getAdapterPosition()).getAccountTitle());
        holder.account_name.setText(dataObjects.get(holder.getAdapterPosition()).getAccountUsername());
        holder.account_description.setText(dataObjects.get(holder.getAdapterPosition()).getAccountDescription());
        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.doViewAccountShowPopup(dataObjects.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataObjects.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<AccountRecord> listFilter = new ArrayList<>();
                listFilter = KEY.CACHE_DATA_OBJECT_MANAGER.getDataInstance().getListData()
                        .parallelStream()
                        .filter(p -> {
                            return (p.getAccountTitle().toLowerCase().contains(constraint.toString().toLowerCase()))
                                    || (p.getAccountUsername().toLowerCase().contains(constraint.toString().toLowerCase()));
                        })
                        .collect(Collectors.toList());
                FilterResults filterResults = new FilterResults();
                filterResults.values = listFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dataObjects  = (ArrayList<AccountRecord>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class CustomeGroupHolder extends RecyclerView.ViewHolder {
        CardView main_layout;
        TextView account_site;
        TextView account_name;
        TextView account_description;

        public CustomeGroupHolder(@NonNull View itemView) {
            super(itemView);
            main_layout = itemView.findViewById(R.id.main_layout);
            account_site = itemView.findViewById(R.id.account_site);
            account_name = itemView.findViewById(R.id.account_name);
            account_description = itemView.findViewById(R.id.account_description);
        }
    }
}
