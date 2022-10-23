package edu.northeastern.cs5520fa22groupproject.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IPlaceholder {

    @GET("{color}")   // "post/" is the relative Url of your api. We define base Url at a common place later
    Call<RandomColor>  getPostModels(@Path("color") String color);

//    @GET("posts")   // "post/" is the relative Url of your api. We define base Url at a common place later
//    Call<List<PostModel>>  getPostModels();

//    JSONObject json = new JSONObject(result);    // create JSON obj from string
//    JSONObject json2 = json.getJSONObject("info");
}
