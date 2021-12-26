package com.oyelabs.marvel.universe;
import static com.oyelabs.marvel.universe.marvelApi.MarvelApiService.HASH;
import static com.oyelabs.marvel.universe.util.Constants.BASE_URL;
import static com.oyelabs.marvel.universe.util.Constants.PUBLIC_KEY;
import static com.oyelabs.marvel.universe.util.Constants.TIMESTAMP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.oyelabs.marvel.universe.Adapter.ListCharactersAdapter;
import com.oyelabs.marvel.universe.marvelApi.MarvelApiService;
import com.oyelabs.marvel.universe.models.CharactersResponse;
import com.oyelabs.marvel.universe.models.Result;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    String  searchTerm;
    int valueRepeat = 3;
    int paginatedValue = 0;
    RecyclerView recyclerView;
    ListCharactersAdapter adapter;
    GridLayoutManager layoutManagerGrid;
    private Retrofit retrofit;
    private static final String TAG = "MARVEL";
    private ListCharactersAdapter listCharactersAdapter;
    private final ArrayList<Result> listAllCharacters = new ArrayList<>();
    private int offset;
    private boolean readyUpdate;

//    private lateinit var binding : ActivityMainBinding

//    private val viewModel : CharactersViewModel by viewModels()
//    var list = arrayListOf<CharacterModel>()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView= findViewById(R.id.characterRecyclerView);
        layoutManagerGrid= new GridLayoutManager(this,2);
        recyclerView.setAdapter(listCharactersAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManagerGrid);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                   /* int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();*/

                    if (readyUpdate) {
                        //if ( totalItemCount>=0) {
                        Log.i(TAG, "last row");
                        readyUpdate = false;
                        offset += 20;
                        obtainData(offset);
                        //}
                    }
                }
            }
        });
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        readyUpdate = true;
        offset = 0;
        obtainData(offset);
    }

    private void obtainData(int offset) {
        MarvelApiService service = retrofit.create(MarvelApiService.class);

        final Call<CharactersResponse> CharactersResponseCall = service.obtainListCharacters(offset, PUBLIC_KEY, HASH, TIMESTAMP);

        CharactersResponseCall.enqueue(new Callback<CharactersResponse>() {
            @Override
            public void onResponse(Call<CharactersResponse> call, Response<CharactersResponse> response) {
                readyUpdate = true;
                if (response.isSuccessful()) {
                    CharactersResponse charactersResponse = response.body();

                    listAllCharacters.addAll(charactersResponse.getData().getResults());

                    listCharactersAdapter.addListCharacters(charactersResponse.getData().getResults());
                } else {
                    Log.e(TAG, "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<CharactersResponse> call, Throwable t) {
                readyUpdate = true;
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.menuSearch);
        SearchView searchView = null;
//        searchView.isSubmitButtonEnabled();

//        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query!=null){
            searchTerm = query;
        }
        if (!searchTerm.isEmpty()) {
            search();
        }
        return true;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText!=null){
            searchTerm = newText;
        }
        if (!searchTerm.isEmpty()) {
            search();
        }
        return true;
    }
    private void search() {

    }
    private ArrayList<Result> filter(String text) {
        ArrayList<Result> filterList = new ArrayList<>();
        try {
            text = text.toLowerCase();
            if (listAllCharacters != null && !listAllCharacters.isEmpty()) {
                for (Result result : listAllCharacters) {
                    String resultString = result.getName().toLowerCase();

                    if (resultString.contains(text)) {
                        filterList.add(result);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filterList;
    }
}

