package requests;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import model.Photo;
import retrofit2.Call;
import retrofit2.Response;
import utils.AppExecutors;
import utils.Constants;


public class PhotosApiClient {

    private static final String TAG = "PhotosApiClient";

    private static PhotosApiClient instance;
    private MutableLiveData<List<Photo>> mPhotos;

    private RetrievePhotosRunnable mRetrievePhotosRunnable;
    private MutableLiveData<Boolean> mPhotosRequestTimeout = new MutableLiveData<>();

    public static PhotosApiClient getInstance(){
        if(instance == null){
            instance = new PhotosApiClient();
        }
        return instance;
    }

    private PhotosApiClient(){
        mPhotos = new MutableLiveData<>();
    }

    public LiveData<List<Photo>> getPhotos(){
        return mPhotos;
    }

    public LiveData<Boolean> isPhotosRequestTimedOut(){
        return mPhotosRequestTimeout;
    }

    public void getPhotosApi() {
        if(mRetrievePhotosRunnable != null){
            mRetrievePhotosRunnable = null;
        }
        mRetrievePhotosRunnable = new RetrievePhotosRunnable();
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrievePhotosRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class RetrievePhotosRunnable implements Runnable {
        
        boolean cancelRequest;

        public RetrievePhotosRunnable() {
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getPhotos().execute();
                if(cancelRequest) {
                    return;
                }

                if(response.code() == 200) {
                    List<Photo> list = (List<Photo>) response.body();
                    mPhotos.postValue(list);

                }
                else{
                    String error = response.errorBody().string();
                    mPhotos.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mPhotos.postValue(null);
            }

        }

        private Call<List<Photo>> getPhotos(){
            return ApiHelper.getApplicationApi().getAllPhotos();
        }

        private void cancelRequest() {
            cancelRequest = true;
        }
    }

    public void cancelRequest() {
        if(mRetrievePhotosRunnable != null){
            mRetrievePhotosRunnable.cancelRequest();
        }
    }
}













