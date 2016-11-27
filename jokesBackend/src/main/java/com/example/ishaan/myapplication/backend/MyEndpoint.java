/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.ishaan.myapplication.backend;

import com.example.Joker;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;


/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.ishaan.example.com",
                ownerName = "backend.myapplication.ishaan.example.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    Joker mJoker = new Joker();

    @ApiMethod(name = "joke", httpMethod = ApiMethod.HttpMethod.GET)
    public MyBean joke() {
        MyBean response = new MyBean();
        response.setData(mJoker.getJoke());
        return response;
    }

}
