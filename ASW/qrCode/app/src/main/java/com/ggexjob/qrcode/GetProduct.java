package com.ggexjob.qrcode;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by rhoorn on 2015-03-11.
 */
public interface GetProduct {
    @GET("/api/{controller}/{id}")
    Products products(
            @Path("controller") String controller,
            @Path("id") String id
    );
}
