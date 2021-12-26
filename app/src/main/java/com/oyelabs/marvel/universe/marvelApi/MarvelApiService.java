package com.oyelabs.marvel.universe.marvelApi;

import com.oyelabs.marvel.universe.models.CharactersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarvelApiService {

    String NAME = "name";
    String API_KEY = "apikey";
    String HASH = "hash";
    String TIMESTAMP = "ts";

    //https://gateway.marvel.com/v1/public/characters?offset=blabla&apikey=blabla&hash=blabla&ts=blabla
    @GET("/v1/public/characters")
    Call<CharactersResponse> obtainListCharacters(
            //@Query("name") String name,
            @Query("offset") int offset,
            @Query(API_KEY) String publickey,
            @Query(HASH) String hash,
            @Query(TIMESTAMP) long timestamp


    );
}
