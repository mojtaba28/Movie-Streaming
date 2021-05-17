package com.example.moviestreaming.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestreaming.Activity.HomeActivity;
import com.example.moviestreaming.Activity.LoginActivity;
import com.example.moviestreaming.Activity.VerifyActivity;
import com.example.moviestreaming.Interface.ActorsResponse;
import com.example.moviestreaming.Interface.GenreResponse;
import com.example.moviestreaming.Interface.MovieResponse;
import com.example.moviestreaming.Interface.SliderResponse;
import com.example.moviestreaming.Model.Actors.ActorItems;
import com.example.moviestreaming.Model.GenreModel;
import com.example.moviestreaming.Model.MovieModel;
import com.example.moviestreaming.Model.SliderModel;
import com.example.moviestreaming.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class API {

    RequestQueue requestQueue;

    //Slider
    String sliderUrl = Constant.MAIN_URL+"getSlider.php";
    List<SliderModel> sliderList = new ArrayList<>();


    //IMDBTopMovie
    String IMDBTopUrl = Constant.MAIN_URL+"getMovie.php?category=top_imdb";
    List<MovieModel> IMDBTopList = new ArrayList<>();


    //NewMovies
    String NewMoviesUrl = Constant.MAIN_URL+"getMovie.php?category=new";
    List<MovieModel> newMoviesList = new ArrayList<>();


    //PopularMovies
    String PopularUrl = Constant.MAIN_URL+"getMovie.php?popular=1";
    List<MovieModel> popularList = new ArrayList<>();


    //Genre
    String genreUrl = Constant.MAIN_URL+"getGenre.php";
    List<GenreModel> genreList = new ArrayList<>();


    //Movie By Genre
    String movieByGenreUrl = Constant.MAIN_URL+"getMovie.php?genre=";
    List<MovieModel> movieByGenreList = new ArrayList<>();

    //Actors
    String actorsUrl = Constant.MAIN_URL+"getActors.php?movie_id=";
    List<ActorItems> actorsList = new ArrayList<>();

    //Search
    String searchUrl = Constant.MAIN_URL+"getMovie.php?search=";
    List<MovieModel> searchList;

    //checkRegister
    String checkRegisterUrl = Constant.MAIN_URL+"checkRegister.php";

    //verifyCode

    //register
    String registerUrl = Constant.MAIN_URL+"register.php";

    //login
    String loginUrl = Constant.MAIN_URL+"login.php";

    String verifyCodeUrl ="https://api.kavenegar.com/v1/"+Constant.API_KEY+"/verify/lookup.json";


//    public void getSlider (final Context context , RequestQueue requestQueue , final List<SliderModel> list, final SliderResponse sliderResponse) {
//
//        this.requestQueue = requestQueue;
//        requestQueue = Volley.newRequestQueue(context);
//        this.sliderList = list;
//
//
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, sliderUrl,
//                null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                try {
//                    JSONArray jsonArray = response.getJSONArray("slider");
//
//                    for (int i =0 ; i<jsonArray.length() ; i++){
//
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                        SliderModel slider = new SliderModel();
//
//                        slider.setId(jsonObject.getInt("id"));
//                        slider.setName(jsonObject.getString("name"));
//                        slider.setImage_name(jsonObject.getString("image_name"));
//                        slider.setTime(jsonObject.getString("time"));
//                        slider.setPublished(jsonObject.getString("published"));
//                        sliderList.add(slider);
//
//                    }
//                    sliderResponse.getSliderList(sliderList,jsonArray.length());
//
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Toast.makeText(context, error.getMessage()+"", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        requestQueue.add(jsonObjectRequest);
//
//    }

    public void getIMDBTopMovies(final Context context, RequestQueue requestQueue , List<MovieModel> list, final MovieResponse movieResponse){

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        this.IMDBTopList = list;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, IMDBTopUrl,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("movies");

                    for (int i =0 ; i<jsonArray.length() ; i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);


                        MovieModel model = new MovieModel();

                        model.setId(jsonObject.getString("id"));
                        model.setName(jsonObject.getString("name"));
                        model.setImage_name(jsonObject.getString("image_name"));
                        model.setTime(jsonObject.getString("time"));
                        model.setPublished(jsonObject.getString("published"));
                        model.setCategory(jsonObject.getString("category"));
                        model.setRate(jsonObject.getString("rate"));
                        model.setImdb_rank(jsonObject.getString("imdb_rank"));
                        model.setDescription(jsonObject.getString("description"));
                        model.setDirector(jsonObject.getString("director"));
                        model.setBox_office(jsonObject.getString("box_office"));
                        model.setBudget(jsonObject.getString("budget"));
                        model.setGenre(jsonObject.getString("genre"));
                        model.setMovie_preview(jsonObject.getString("movie_preview"));
                        model.setMovie_poster(jsonObject.getString("movie_poster"));
                        IMDBTopList.add(model);

                    }
                    movieResponse.getMovie(IMDBTopList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, error.getMessage()+"", Toast.LENGTH_SHORT).show();

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    public void getNewMovies(final Context context, RequestQueue requestQueue , List<MovieModel> list, final MovieResponse movieResponse){

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        this.newMoviesList = list;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, NewMoviesUrl,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("movies");

                    for (int i =0 ; i<jsonArray.length() ; i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);


                        MovieModel model = new MovieModel();

                        model.setId(jsonObject.getString("id"));
                        model.setName(jsonObject.getString("name"));
                        model.setImage_name(jsonObject.getString("image_name"));
                        model.setTime(jsonObject.getString("time"));
                        model.setPublished(jsonObject.getString("published"));
                        model.setCategory(jsonObject.getString("category"));
                        model.setRate(jsonObject.getString("rate"));
                        model.setImdb_rank(jsonObject.getString("imdb_rank"));
                        model.setDescription(jsonObject.getString("description"));
                        model.setDirector(jsonObject.getString("director"));
                        model.setBox_office(jsonObject.getString("box_office"));
                        model.setBudget(jsonObject.getString("budget"));
                        model.setGenre(jsonObject.getString("genre"));
                        model.setMovie_preview(jsonObject.getString("movie_preview"));
                        model.setMovie_poster(jsonObject.getString("movie_poster"));
                        newMoviesList.add(model);

                    }

                    movieResponse.getMovie(newMoviesList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, error.getMessage()+"", Toast.LENGTH_SHORT).show();

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    public void getPopularMovies(final Context context, RequestQueue requestQueue , List<MovieModel> list, final MovieResponse movieResponse){

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        this.popularList = list;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, PopularUrl,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("movies");

                    for (int i =0 ; i<jsonArray.length() ; i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);


                        MovieModel model = new MovieModel();

                        model.setId(jsonObject.getString("id"));
                        model.setName(jsonObject.getString("name"));
                        model.setImage_name(jsonObject.getString("image_name"));
                        model.setTime(jsonObject.getString("time"));
                        model.setPublished(jsonObject.getString("published"));
                        model.setCategory(jsonObject.getString("category"));
                        model.setRate(jsonObject.getString("rate"));
                        model.setImdb_rank(jsonObject.getString("imdb_rank"));
                        model.setDescription(jsonObject.getString("description"));
                        model.setDirector(jsonObject.getString("director"));
                        model.setBox_office(jsonObject.getString("box_office"));
                        model.setBudget(jsonObject.getString("budget"));
                        model.setGenre(jsonObject.getString("genre"));
                        model.setMovie_preview(jsonObject.getString("movie_preview"));
                        model.setMovie_poster(jsonObject.getString("movie_poster"));
                        model.setPopular(jsonObject.getString("popular"));
                        popularList.add(model);
                    }

                    movieResponse.getMovie(popularList);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, error.getMessage()+"", Toast.LENGTH_SHORT).show();

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    public void getGenre (final Context context , RequestQueue requestQueue , final List<GenreModel> list, final GenreResponse genreResponse) {

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        this.genreList = list;



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, genreUrl,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("genre");

                    for (int i =0 ; i<jsonArray.length() ; i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        GenreModel genreModel = new GenreModel();

                        genreModel.setId(jsonObject.getString("id"));
                        genreModel.setName(jsonObject.getString("name"));
                        genreModel.setImage_name(jsonObject.getString("image_name"));
                        genreList.add(genreModel);

                    }
                    genreResponse.getGenreList(genreList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, error.getMessage()+"", Toast.LENGTH_SHORT).show();

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);

    }

    public void getActors(final Context context , RequestQueue requestQueue , final List<ActorItems> list, String movie_id, final ActorsResponse actorsResponse) {

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        this.actorsList = list;
        this.actorsUrl = actorsUrl +movie_id;



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, actorsUrl,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("actors");

                    for (int i =0 ; i<jsonArray.length() ; i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        ActorItems model = new ActorItems();

                        model.setId(jsonObject.getString("id"));
                        model.setName(jsonObject.getString("name"));
                        model.setImage_name(jsonObject.getString("image_name"));
                        model.setMovie_id(jsonObject.getString("movie_id"));
                        actorsList.add(model);

                    }
                    actorsResponse.getActors(actorsList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, error.getMessage()+"", Toast.LENGTH_SHORT).show();

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);

    }

    public void getMovieByGenre (final Context context , RequestQueue requestQueue , final List<MovieModel> list, String genreLink, final MovieResponse movieResponse) {

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        this.movieByGenreList = list;
        this.movieByGenreUrl = movieByGenreUrl +genreLink;



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, movieByGenreUrl,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("movies");

                    for (int i =0 ; i<jsonArray.length() ; i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        MovieModel model = new MovieModel();

                        model.setId(jsonObject.getString("id"));
                        model.setName(jsonObject.getString("name"));
                        model.setImage_name(jsonObject.getString("image_name"));
                        model.setTime(jsonObject.getString("time"));
                        model.setPublished(jsonObject.getString("published"));
                        model.setCategory(jsonObject.getString("category"));
                        model.setRate(jsonObject.getString("rate"));
                        model.setImdb_rank(jsonObject.getString("imdb_rank"));
                        model.setDescription(jsonObject.getString("description"));
                        model.setDirector(jsonObject.getString("director"));
                        model.setBox_office(jsonObject.getString("box_office"));
                        model.setBudget(jsonObject.getString("budget"));
                        model.setGenre(jsonObject.getString("genre"));
                        model.setMovie_preview(jsonObject.getString("movie_preview"));
                        model.setMovie_poster(jsonObject.getString("movie_poster"));
                        model.setPopular(jsonObject.getString("popular"));
                        movieByGenreList.add(model);

                    }
                    movieResponse.getMovie(movieByGenreList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, error.getMessage()+"", Toast.LENGTH_SHORT).show();

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);

    }

    public void SearchMovie (final Context context , RequestQueue requestQueue , String name, final MovieResponse movieResponse) {

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String link= searchUrl +name;


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,link,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("movies");
                    searchList=new ArrayList<>();

                    for (int i =0 ; i<jsonArray.length() ; i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);


                        MovieModel model = new MovieModel();
                        model.setId(jsonObject.getString("id"));
                        model.setName(jsonObject.getString("name"));
                        model.setImage_name(jsonObject.getString("image_name"));
                        model.setTime(jsonObject.getString("time"));
                        model.setPublished(jsonObject.getString("published"));
                        model.setCategory(jsonObject.getString("category"));
                        model.setRate(jsonObject.getString("rate"));
                        model.setImdb_rank(jsonObject.getString("imdb_rank"));
                        model.setDescription(jsonObject.getString("description"));
                        model.setDirector(jsonObject.getString("director"));
                        model.setBox_office(jsonObject.getString("box_office"));
                        model.setBudget(jsonObject.getString("budget"));
                        model.setGenre(jsonObject.getString("genre"));
                        model.setMovie_preview(jsonObject.getString("movie_preview"));
                        model.setMovie_poster(jsonObject.getString("movie_poster"));
                        model.setPopular(jsonObject.getString("popular"));
                        searchList.add(model);

                    }
                    movieResponse.getMovie(searchList);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, error.getMessage()+"", Toast.LENGTH_SHORT).show();

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);

    }

    public void checkRegister(final String phone, Context context,RequestQueue requestQueue ) {

        this.requestQueue=requestQueue;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, checkRegisterUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("exist")){


                    Methods.showSnackBar((Activity)context,context.getResources().getString(R.string.user_already_exist), Color.RED);


                }else if (response.equals("not exist")){

                    Intent intent=new Intent(context,VerifyActivity.class);
                    intent.putExtra("phone",phone);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }else {

                    Methods.showSnackBar((Activity)context,response, Color.RED);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Error" , error.getMessage()+"");

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String , String> params = new HashMap<>();
                params.put("phone" ,phone );
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }


    public void verifyCode(final String phone,final String token,final String template, Context context,RequestQueue requestQueue ) {

        this.requestQueue=requestQueue;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, verifyCodeUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("verifyCode" , response);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("verifyCode" , error.getMessage()+"");
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String , String> params = new HashMap<>();
                params.put("receptor" ,phone );
                params.put("token" ,token );
                params.put("template" ,template );
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

    public void register(final String phone,final String password,final String name,final String lastName, Context context,RequestQueue requestQueue ) {

        this.requestQueue=requestQueue;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, registerUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("successful")){
                    SessionManager sessionManager=new SessionManager(context);
                    sessionManager.setUserName(name);
                    Intent intent=new Intent(context, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);


                }else {

                    Methods.showSnackBar((Activity)context,response, Color.RED);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Error" , error.getMessage()+"");

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String , String> params = new HashMap<>();
                params.put("name" ,name );
                params.put("last_name" ,lastName );
                params.put("password" ,password );
                params.put("phone" ,phone );
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

    public void login(final String phone,final String password, Context context,RequestQueue requestQueue ) {

        this.requestQueue=requestQueue;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                SessionManager sessionManager=new SessionManager(context);

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("user_id");
                    String userId=jsonArray.getJSONObject(0).getString("id");
                    sessionManager.setUserId(userId);
                    Log.i("user_id",userId);





                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (response.contains("successful")){


                    sessionManager.setUserPassword(password);
                    sessionManager.setUserPhone(phone);
                    sessionManager.setLoggedIn(true);
                    Intent intent=new Intent(context, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }else {

                    Methods.showSnackBar((Activity)context,response, Color.RED);
                }


                Log.i("login",response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Error" , error.getMessage()+"");

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String , String> params = new HashMap<>();
                params.put("phone" ,phone );
                params.put("password" ,password );
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }
}
