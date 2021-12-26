package com.oyelabs.marvel.universe.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.oyelabs.marvel.universe.Adapter.ListCharactersAdapter;
import com.oyelabs.marvel.universe.R;

public class CharactersActivity extends AppCompatActivity {
    ListCharactersAdapter adapter;
    public static final String IV_IMAGE_CHARACTER = "iv_imageCharacter";


    public static void launch(Context context, String name, String description, String imageURL) {
        Intent intent = new Intent(context, CharactersActivity.class);
        intent.putExtra(IV_IMAGE_CHARACTER, imageURL);
        intent.putExtra("tv_characterName", name);
        intent.putExtra("tv_characterDescription", description);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters);
    }

    private void getIncomingIntent() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String imageUrl = getIntent().getStringExtra(IV_IMAGE_CHARACTER);
            String name = getIntent().getStringExtra("tv_characterName");
            String description = getIntent().getStringExtra("tv_characterDescription");
            setCharacter(imageUrl, name, description);

        }
    }

    private void setCharacter(String imageUrl, String chName, String chDescription) {


        TextView tvName = findViewById(R.id.textView);
        tvName.setText(chName);
        TextView tvDescription = findViewById(R.id.textView2);
        if (chDescription != null && !chDescription.equals("")) {
            tvDescription.setText(chDescription);
        } else {
            tvDescription.setText("info_not_available");
        }

        ImageView image = findViewById(R.id.appCompatImageView);
        Glide.with(this)
                //.asBitmap()
                .load(imageUrl)
                .into(image);
    }

}