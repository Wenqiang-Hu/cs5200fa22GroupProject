package edu.northeastern.cs5520fa22groupproject.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IPlaceholder {

    @GET("{color}")
    Call<RandomColor>  getColorModels(@Path("color") String color);
}
