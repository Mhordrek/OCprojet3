package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserProfilActivity extends AppCompatActivity {

    @BindView(R.id.avatar)
    ImageView neighbourAvatar;
    @BindView(R.id.avatarname)
    TextView neighbourAvatarName;
    @BindView(R.id.name)
    TextView mUserName;
    @BindView(R.id.address)
    TextView mNeighbourAddress;
    @BindView(R.id.phoneNumber)
    TextView mPhoneNumber;
    @BindView(R.id.url)
    TextView mNeighbourUrl;
    @BindView(R.id.aboutMe)
    TextView mAboutMe;
    @BindView(R.id.backButton)
    ImageButton backButton;
    @BindView(R.id.favoriteButton)
    ImageButton favoriteButton;

    NeighbourApiService mApiService;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("UserProfilActivity","Entering UserProfilActivity");
        setContentView(R.layout.activity_userprofil);
        ButterKnife.bind(this);
        mApiService = DI.getNeighbourApiService();




        Intent intent = this.getIntent();
        // reception des data
        Long Id = intent.getLongExtra("id",-1);
        String AvatarName = intent.getStringExtra("avatarName");
        neighbourAvatarName.setText(AvatarName);
        String name = intent.getStringExtra("name");
        mUserName.setText(name);
        String address = intent.getStringExtra("address");
        mNeighbourAddress.setText(address);
        String phone = intent.getStringExtra("phone");
        mPhoneNumber.setText(phone);
        String url = intent.getStringExtra("url");
        String aboutUser = intent.getStringExtra("aboutUser");
        mAboutMe.setText(aboutUser);

        Glide
                .with(this)
                .load(url)
                .into(neighbourAvatar);

        Neighbour neighbour = new Neighbour(
                Id,
                mUserName.getText().toString(),
                url,
                mNeighbourAddress.getText().toString(),
                mPhoneNumber.getText().toString(),
                mAboutMe.getText().toString()
        );

        mNeighbourUrl.setText("www.facebook.com/"+ mUserName.getText().toString());

        List<Neighbour> favoriteNeighbour = mApiService.getFavoriteNeighbours();


        if(favoriteNeighbour.contains(neighbour)){


            favoriteButton.setBackgroundResource(R.drawable.ic_star_white_24dp);

        }else {

            favoriteButton.setBackgroundResource(R.drawable.ic_star_border_white_24dp);

        }

        // bouton qui permet de revenir à l'activité précédente


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // bouton favori qui permet d'ajouter un voisin à la liste de favori

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Neighbour neighbour = new Neighbour(
                        Id,
                        mUserName.getText().toString(),
                        url,
                        mNeighbourAddress.getText().toString(),
                        mPhoneNumber.getText().toString(),
                        mAboutMe.getText().toString()
                );

                List<Neighbour> favoriteNeighbour = mApiService.getFavoriteNeighbours();

                if(favoriteNeighbour.contains(neighbour)){


                   mApiService.deleteFavoriteNeighbour(neighbour);
                   favoriteButton.setBackgroundResource(R.drawable.ic_star_border_white_24dp);

                }else {

                   mApiService.addFavoriteNeighbour(neighbour);
                   favoriteButton.setBackgroundResource(R.drawable.ic_star_white_24dp);
               }


            }
        });



    }


    }

