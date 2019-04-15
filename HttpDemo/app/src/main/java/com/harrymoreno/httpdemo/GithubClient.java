package com.harrymoreno.httpdemo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GithubClient {
    @GET("/users/{user}/repos")
    Call<List<GithubRepo>> reposForUser(
            @Path("user") String user
    );

    @POST("/status/200")
    Call<User> createAccount(@Body User user);
}