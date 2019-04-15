package com.harrymoreno.httpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.harrymoreno.httpdemo.GitHubRepoAdapter;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        final EditText name = (EditText) findViewById(R.id.input_name);
        final EditText email = (EditText) findViewById(R.id.input_email);
        final EditText age = (EditText) findViewById(R.id.input_age);
        final EditText topics = (EditText) findViewById(R.id.input_topics);
*/

        Button createAccountButton = (Button) findViewById(R.id.btn_signup);
        createAccountButton.setOnClickListener((view) -> {
                               /*
                    name.getText().toString(),
                    email.getText().toString(),
                    Integer.parseInt(age.getText().toString()),
                    topics.getText().toString().split(",")
                    */
            String[] toppings = {"Cheese", "Pepperoni", "Black Olives"};
            User user = new User("Harry", "harry@example.com", 22, toppings);

            sendNetworkRequest(user);
        });

        listView = (ListView) findViewById(R.id.pagination_list);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        GithubClient client = retrofit.create(GithubClient.class);
        Call<List<GithubRepo>> call = client.reposForUser("morenoh149");

        call.enqueue(new Callback<List<GithubRepo>>() {
            @Override
            public void onResponse(Call<List<GithubRepo>> call, Response<List<GithubRepo>> response) {
                List<GithubRepo> repos = response.body();

                listView.setAdapter(new GitHubRepoAdapter(MainActivity.this, repos));
            }

            @Override
            public void onFailure(Call<List<GithubRepo>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendNetworkRequest(User user) {
        // create Retrofit instance
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://httpbin.org/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        // get client & call object for the request
        GithubClient client = retrofit.create(GithubClient.class);
        Call <User> call = client.createAccount(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(MainActivity.this, "Yeah! User-ID:" + response.body().getId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "something went wrong :(", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
