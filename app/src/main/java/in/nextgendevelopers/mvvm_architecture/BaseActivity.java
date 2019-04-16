package in.nextgendevelopers.mvvm_architecture;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public abstract class BaseActivity extends AppCompatActivity {

    ProgressBar mProgressBar;
    Toolbar mToolbar;

    @Override
    public void setContentView(int layoutResID) {
        RelativeLayout relativeLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout frameLayout = relativeLayout.findViewById(R.id.activity_content);
        mProgressBar = relativeLayout.findViewById(R.id.progress_bar);
        mToolbar = relativeLayout.findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        super.setContentView(relativeLayout);
    }

    public void showProgressBar(boolean visibility) {
        mProgressBar.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }
}
