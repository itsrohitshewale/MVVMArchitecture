package requests;

import java.util.List;

import model.Photo;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApplicationApi {

    @GET("/photos")
    Call<List<Photo>> getAllPhotos();
}
