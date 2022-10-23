package edu.northeastern.cs5520fa22groupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.List;

import edu.northeastern.cs5520fa22groupproject.model.IPlaceholder;
import edu.northeastern.cs5520fa22groupproject.model.RandomColor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AtYourService extends AppCompatActivity {

    EditText inputText;
    private Retrofit retrofit;
    String url;
    String color;
    IPlaceholder api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_your_service);

        inputText = (EditText) findViewById(R.id.color);
        Button btSubmit = findViewById(R.id.submit);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = inputText.getText().toString();
                getPost();
            }
        });

        url = "https://x-colors.herokuapp.com/";

        retrofit = new Retrofit.Builder()
                .baseUrl("https://x-colors.herokuapp.com/api/random/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(IPlaceholder.class);
    }

    private void getPost(){
        Call<RandomColor> call = api.getPostModels(color);
        call.enqueue(new Callback<RandomColor>() {
            @Override
            public void onResponse(Call<RandomColor> call, Response<RandomColor> response) {
                if(!response.isSuccessful()){
                    Log.d("faild", "Call failed!" + response.code());

                }

                Log.d("Success", "Call Successed!");
                RandomColor rc = response.body();
//                Log.d("random color: ", rc.toString());
                Log.d("random color: ", rc.getRgb());
                ConstraintLayout li=(ConstraintLayout) findViewById(R.id.Layout_ays);
                li.setBackgroundColor(Color.parseColor(rc.getHex()));
            }

            @Override
            public void onFailure(Call<RandomColor> call, Throwable t) {
                Log.d("onfailure: ", "xxx" );
            }
        });
    }

}