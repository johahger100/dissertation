package com.github.barcodeeye;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by jhager on 2015-03-10.
 */
public interface GetService {
    @GET("/api/Productvalue/{id}")
    Products GetProduct(
            @Path("id") String id
    );
    /*
    Products GetProduct(
            @Path("id") int id
    );*/
}
