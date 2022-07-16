package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteFavoriteNeighbourEvent;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class GenericFragment extends Fragment {

    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private RecyclerView mRecyclerView;
    private boolean isFavorite;


    public interface OnItemClickListener {
        void onItemClick( Neighbour neighbour);
    }


    public static GenericFragment newInstance(Boolean isFavorite) {

        Bundle bundle = new Bundle();
        bundle.putBoolean("isfavorite",isFavorite);
        GenericFragment fragment = new GenericFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
           isFavorite = bundle.getBoolean("isfavorite");

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        readBundle(getArguments());

        if(isFavorite){

            view = inflater.inflate(R.layout.fragment_favorite_neighbour_list, container, false);

        }else{

            view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);
        }

        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        return view;

    }

    private void initList() {

        if(isFavorite){

            mNeighbours = mApiService.getFavoriteNeighbours();


        }else{

            mNeighbours = mApiService.getNeighbours();

        }


        mRecyclerView.setAdapter(new GenericRecyclerViewAdapter(mNeighbours, new GenericRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Neighbour neighbour) {

                Intent intent = new Intent(getContext(),UserProfilActivity.class);

                // envoi des data
                //intent.putExtra("isFavorite",isFavorite);
                intent.putExtra("id",neighbour.getId());
                intent.putExtra("avatarName",neighbour.getName());
                intent.putExtra("name",neighbour.getName());
                intent.putExtra("address",neighbour.getAddress());
                intent.putExtra("phone",neighbour.getPhoneNumber());
                intent.putExtra("url",neighbour.getAvatarUrl());
                intent.putExtra("aboutUser",neighbour.getAboutMe());


                getContext().startActivity(intent);

            }
        }, isFavorite));


    }


    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Fired if the user clicks on a delete button
     * @param event
     */


    @Subscribe
    public void onDeleteNeighbour(DeleteNeighbourEvent event) {


        mApiService.deleteNeighbour(event.neighbour);

        initList();
    }

    @Subscribe
    public void onDeleteNeighbour(DeleteFavoriteNeighbourEvent event) {


            mApiService.deleteFavoriteNeighbour(event.neighbour);

        initList();
    }

}




