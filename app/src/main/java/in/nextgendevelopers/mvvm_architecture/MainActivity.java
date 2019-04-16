package in.nextgendevelopers.mvvm_architecture;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import adapter.OnCellClickListener;
import adapter.PhotosRecyclerAdapter;
import model.Photo;
import utils.VerticalSpacingItemDecorator;
import viewmodels.MainActivityViewModel;

public class MainActivity extends BaseActivity implements OnCellClickListener {

    PhotosRecyclerAdapter mAdapter;
    RecyclerView mRecyclerView;
    MainActivityViewModel mMainActivityViewModel;
    List<Photo> mPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Home Page");

        mRecyclerView = findViewById(R.id.photos_list);

        mMainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        initNetworkCall();
        initRecyclerView();
        subscribeObservers();
    }

    private void initRecyclerView(){
        mAdapter = new PhotosRecyclerAdapter(this);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecorator);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initNetworkCall() {
        showProgressBar(true);
        mMainActivityViewModel.photosApi();
    }

    private void subscribeObservers() {
        mMainActivityViewModel.getPhotos().observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(@Nullable List<Photo> photos) {
                if(photos != null) {
                    mPhotos = photos;
                    showProgressBar(false);
                    mAdapter.setPhotos(photos);
                }
            }
        });

    }

    @Override
    public void onCellClick(int position) {
        if (mPhotos != null && mPhotos.size() > 0) {
            Photo photo = mPhotos.get(position);
            Toast.makeText(this, photo.getTitle(), Toast.LENGTH_SHORT).show();
        }
    }
}
