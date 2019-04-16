package viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import model.Photo;
import repositories.MainActivityRepository;

public class MainActivityViewModel extends ViewModel {
    private MainActivityRepository mMainActivityRepository;
    private boolean mIsLoading;

    public MainActivityViewModel() {
        mMainActivityRepository = MainActivityRepository.getInstance();
        mIsLoading = false;
    }

    public LiveData<List<Photo>> getPhotos(){
        return mMainActivityRepository.getPhotos();
    }

    public LiveData<Boolean> isLoading(){
        return mMainActivityRepository.isLoading();
    }

    public void photosApi() {
        mIsLoading = true;
        mMainActivityRepository.photosApi();
    }

}
