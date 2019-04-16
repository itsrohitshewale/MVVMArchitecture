package requests;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.Constants;

public class ApiHelper {

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static ApplicationApi photoApi = retrofit.create(ApplicationApi.class);

    public static ApplicationApi getApplicationApi(){
        return photoApi;
    }
}
