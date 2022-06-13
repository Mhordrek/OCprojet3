package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.app.Activity;
import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    @BindView(R.id.favorite)
    ImageButton favoriteButton;

    NeighbourApiService mApiService;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mNeighbourUrl.setText(url);
        String aboutUser = intent.getStringExtra("aboutUser");
        mAboutMe.setText(aboutUser);

        Glide
                .with(UserProfilActivity.this)
                .load(mNeighbourUrl)
                .into(neighbourAvatar);

        // bouton qui permet de revenir à l'activité précédente


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Neighbour neighbour = new Neighbour(
                        Id,
                        mUserName.getText().toString(),
                        mNeighbourUrl.getText().toString(),
                        mNeighbourAddress.getText().toString(),
                        mPhoneNumber.getText().toString(),
                        mAboutMe.getText().toString()
                );
                mApiService.addFavoriteNeighbour(neighbour);

            }
        });



    }







}