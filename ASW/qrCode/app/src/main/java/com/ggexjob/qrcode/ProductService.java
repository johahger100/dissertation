package com.ggexjob.qrcode;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by rhoorn on 2015-03-11.
 */
public interface ProductService {
    @GET("/api/Productvalue/{id}")
    Products getproduct(
            @Path("id") String id
    );
}
