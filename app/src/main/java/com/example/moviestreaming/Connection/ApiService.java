package com.example.moviestreaming.Connection;

import com.example.moviestreaming.Model.ActorModel;
import com.example.moviestreaming.Model.CommentModel;
import com.example.moviestreaming.Model.GenreModel;
import com.example.moviestreaming.Model.MovieModel;
import com.example.moviestreaming.Model.MovieResult;
import com.example.moviestreaming.Model.ResponseModel;
import com.example.moviestreaming.Model.SliderModel;
import com.example.moviestreaming.Model.UserModel;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("register.php")
    Observable<ResponseModel> register(@Query("name") String name,
                          @Query("last_name") String lastName,
                          @Query("password") String password,
                          @Query("phone") String phone);

    @POST("login.php")
    Observable<UserModel> login(@Query("phone") String phone,
                          @Query("password") String password);

    @POST("v1/{ApiKey}/verify/lookup.json")
    Observable<String> verifyCode(@Path("ApiKey") String apiKey,
                            @Query("receptor")String receptor,
                            @Query("token")String token,
                            @Query("template")String template);

    @GET("getActors.php")
    Observable<List<ActorModel>> getActors(@Query("movie_id") String movieId);

    @GET("getComment.php")
    Observable<List<CommentModel>> getComment(@Query("movie_id") String movieId);

    @POST("sendComment.php")
    Observable<ResponseModel> sendComment(
            @Header("token") String token,
            @Query("movie_id") String movieId,
            @Query("comment") String comment,
            @Query("spoil") String spoil);

    @POST("updateProfile.php")
    Observable<ResponseModel> updateProfile(
            @Query("name") String name,
            @Query("last_name") String lastName,
            @Query("user_id") String userId);

    @POST("changePassword.php")
    Observable<ResponseModel> changePassword(
            @Query("user_id") String userId,
            @Query("old_password") String oldPassword,
            @Query("new_password") String newPassword);


    @GET("searchMovie.php")
    Observable<MovieResult> searchMovie(
            @Query("filter") String filter,
            @Query("search") String searchQuery);

    @Multipart
    @POST("uploadProfile.php")
    Observable<ResponseBody> uploadProfile(
            @Header("token") String token,
            @Part MultipartBody.Part image);

    @GET("getProfileImage.php")
    Observable<UserModel> getProfileImage(
            @Header("token")String token);

    @GET("removeProfileImage.php")
    Observable<ResponseModel> removeProfileImage(
            @Header("token")String token);

    @POST("checkRegister.php")
    Observable<ResponseModel> checkRegister(
            @Query("phone") String phone);

    @GET("getSlider.php")
    Observable<List<SliderModel>> getSlider();

    @GET("getMovie.php")
    Observable<List<MovieModel>> getMovie(@Query("category")String category);

    @GET("getMovie.php")
    Observable<List<MovieModel>> getPopularMovie(@Query("popular")String popular);

    @GET("getGenre.php")
    Observable<List<GenreModel>> getGenre();

    @GET("getMovie.php")
    Observable<List<MovieModel>> getMovieByGenre(@Query("genre")String genre);
}
