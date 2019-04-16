package adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import in.nextgendevelopers.mvvm_architecture.R;
import model.Photo;

public class PhotosRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Photo> mPhotos;
    private OnCellClickListener mOnCellClickListener;

    public PhotosRecyclerAdapter(OnCellClickListener mOnCellClickListener) {
        this.mOnCellClickListener = mOnCellClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ImageViewHolder(view, mOnCellClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(viewHolder.itemView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(mPhotos.get(i).getThumbnailUrl())
                .into(((ImageViewHolder)viewHolder).image);

        ((ImageViewHolder)viewHolder).title.setText(mPhotos.get(i).getTitle());

    }

    @Override
    public int getItemCount() {
        if(mPhotos != null){
            return mPhotos.size();
        }
        return 0;
    }

    public void setPhotos(List<Photo> photos){
        mPhotos = photos;
        notifyDataSetChanged();
    }
}
