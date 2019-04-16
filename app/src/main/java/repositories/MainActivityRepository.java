package repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import java.util.List;

import model.Photo;
import requests.PhotosApiClient;

public class MainActivityRepository {
    private static MainActivityRepository instance;
    private PhotosApiClient mPhotosApiClient;
    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    private MediatorLiveData<List<Photo>> mPhotos = new MediatorLiveData<>();

    public static MainActivityRepository getInstance() {
        if(instance == null) {
            instance = new MainActivityRepository();
        }
        return instance;
    }

    private MainActivityRepository() {
        mPhotosApiClient = PhotosApiClient.getInstance();
        initMediators();
    }

    private void initMediators() {
        LiveData<List<Photo>> photosListApiSource = mPhotosApiClient.getPhotos();
        mPhotos.addSource(photosListApiSource, new Observer<List<Photo>>() {
            @Override
            public void onChanged(@Nullable List<Photo> photos) {

                if(photos != null) {
                    mPhotos.setValue(photos);
                    doneLoading();
                }
            }
        });
    }

    private void doneLoading() {
        mIsLoading.setValue(false);
    }

    public LiveData<Boolean> isLoading() {
        return mIsLoading;
    }

    public LiveData<List<Photo>> getPhotos() {
        return mPhotos;
    }

    public void photosApi() {
        mIsLoading.setValue(false);
        mPhotosApiClient.getPhotosApi();
    }


    public void cancelRequest(){
        mPhotosApiClient.cancelRequest();
    }

    public LiveData<Boolean> isPhotosRequestTimedOut(){
        return mPhotosApiClient.isPhotosRequestTimedOut();
    }
}
