package kampus.bulletinboard;

/**
 * Created by Павел on 12.12.2015.
 */

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface BulletinService {
    //i.e. http://localhost/api/institute/Students
    @GET("/posts/")
    public void getBulletin(Callback<Bulletin[]> callback);

    //i.e. http://localhost/api/institute/Students/1
    //Get student record base on ID
    @GET("/posts/{id}")
    public void getBulletinById(@Path("id") Integer id,Callback<Bulletin> callback);

    //i.e. http://localhost/api/institute/Students/1
    //Delete student record base on ID
    @DELETE("/posts/{id}")
    public void deleteBulletinById(@Path("id") Integer id,Callback<Bulletin> callback);

    //i.e. http://localhost/api/institute/Students/1
    //PUT student record and post content in HTTP request BODY
    @PUT("/posts/{id}")
    public void updateBulletinById(@Path("id") Integer id,@Body Bulletin bulletin,Callback<Bulletin> callback);

    //i.e. http://localhost/api/institute/Students
    //Add student record and post content in HTTP request BODY
    @POST("/posts")
    public void addBulletin(@Body Bulletin bulletin,Callback<Bulletin> callback);
}
