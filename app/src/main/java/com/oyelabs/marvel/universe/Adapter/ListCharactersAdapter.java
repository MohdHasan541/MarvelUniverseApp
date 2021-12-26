package com.oyelabs.marvel.universe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.oyelabs.marvel.universe.activity.CharactersActivity;
import com.oyelabs.marvel.universe.R;
import com.oyelabs.marvel.universe.models.Result;


import java.util.ArrayList;

public class ListCharactersAdapter extends RecyclerView.Adapter<ListCharactersAdapter.ViewHolder> {
    private ArrayList<Result> data;
    private Context context;

    public ArrayList<Result> getData() {
        return data;
    }

    public ListCharactersAdapter(Context context) {
        this.context = context;
        data = new ArrayList<Result>();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_grid, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        //        val list = itemList[position]
//        holder.characterName.text = list.name
//        val imageUrl = "${list.thumbnail}/portrait_xlarge.${list.thumbnailExt}"
//        val listofImages = listOf<Int>(R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4,
//                R.drawable.image6,R.drawable.image7,R.drawable.image8,R.drawable.image5)
//        Glide.with(context).load(imageUrl).placeholder(listofImages[(0..7).random()]).into(holder.thumbnail)
//        holder.cardCharacter.setOnClickListener{
//            val intent = Intent(context, CharacterActivity::class.java)
//            intent.putExtra("id",list.id)
//            context.startActivity(intent)
//
        final Result r = data.get(position);

        viewHolder.tv_characterName.setText(r.getName());

        final String imageURL = String.format("%s/%s.%s", r.getThumbnail().getPath(), "standard_xlarge", r.getThumbnail().getExtension());

        Glide.with(context)
                .load(imageURL)
                //.centerCrop()
                //.crossFade()
                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.iv_imageCharacter);

        viewHolder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, data.get(position), Toast.LENGTH_SHORT).show();
                CharactersActivity.launch(context, r.getName(), r.getDescription(), imageURL);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_imageCharacter;
        TextView tv_characterName;
//        RelativeLayout layout_item;
        LinearLayout layout_item;


        public ViewHolder(View itemView) {
            super(itemView);

            iv_imageCharacter = (ImageView) itemView.findViewById(R.id.imgCharacterImage);
            tv_characterName = (TextView) itemView.findViewById(R.id.txtCharacterName);
            layout_item = (LinearLayout) itemView.findViewById(R.id.charactersLinearLayout);
        }
    }

    public void addListCharacters(ArrayList<Result> listCharacters) {
        data.addAll(listCharacters);
        notifyDataSetChanged();
    }

    public void setFilter(ArrayList<Result> listCharacters) {
        this.data = new ArrayList<>();
        this.data.addAll(listCharacters);
        notifyDataSetChanged();
    }
}
