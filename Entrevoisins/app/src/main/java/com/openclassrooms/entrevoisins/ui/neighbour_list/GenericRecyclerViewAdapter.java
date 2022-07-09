package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.events.DeleteFavoriteNeighbourEvent;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GenericRecyclerViewAdapter extends RecyclerView.Adapter<GenericRecyclerViewAdapter.ViewHolder> {

    private final List<Neighbour> mNeighbours;
    private final OnItemClickListener listener;
    private final boolean isFavorite;


    public interface OnItemClickListener {
        void onItemClick( Neighbour neighbour);
    }

    public GenericRecyclerViewAdapter(List<Neighbour> neighbours, OnItemClickListener listener, boolean isFavorite) {
        mNeighbours = neighbours;
        this.listener = listener;
        this.isFavorite = isFavorite;
    }

    @Override
    public GenericRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_neighbour, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GenericRecyclerViewAdapter.ViewHolder holder, int position) {
        Neighbour neighbour = mNeighbours.get(position);
        holder.mNeighbourName.setText(neighbour.getName());
        Glide.with(holder.mNeighbourAvatar.getContext())
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mNeighbourAvatar);

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isFavorite){

                    EventBus.getDefault().post(new DeleteFavoriteNeighbourEvent(neighbour));

                }else{

                    EventBus.getDefault().post(new DeleteNeighbourEvent(neighbour));
                }

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(neighbour);
            }
        });
    }



    @Override
    public int getItemCount() {
        return mNeighbours.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
