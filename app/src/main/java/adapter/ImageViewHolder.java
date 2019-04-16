package adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import in.nextgendevelopers.mvvm_architecture.R;

public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView title;
    ImageView image;
    OnCellClickListener onCellClickListener;

    public ImageViewHolder(@NonNull View itemView, OnCellClickListener onCellClickListener) {
        super(itemView);

        this.onCellClickListener = onCellClickListener;

        title = itemView.findViewById(R.id.txtTitle);
        image = itemView.findViewById(R.id.imgview_thumbnail);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onCellClickListener.onCellClick(getAdapterPosition());
    }
}





