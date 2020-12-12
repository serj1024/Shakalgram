package com.ShakalStudio.shakalgram.MVVM;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ShakalStudio.shakalgram.MVVM.Models.Post;
import com.ShakalStudio.shakalgram.MVVM.ViewModels.MainViewModel;
import com.ShakalStudio.shakalgram.MVVM.ViewModels.MainViewModelFactory;
import com.ShakalStudio.shakalgram.MVVM.Views.FullScreenActivity;
import com.ShakalStudio.shakalgram.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private RecyclerView imagesRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSwipeRefreshLayout();
        initMainViewModel();
        initImageRecyclerView();

        mainViewModel.getPostsLiveData().observe(this, new Observer<ArrayList<Post>>() {
            @Override
            public void onChanged(ArrayList<Post> posts) {
                updateData();
            }
        });

        mainViewModel.OnMainImageClicked.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer adapterPosition) {
                switch (mainViewModel.getCurrentPostType(adapterPosition)) {
                    case AD:
                        showAdApp(context);
                        break;
                    case DEFAULT:
                        showFullScreenActivity(adapterPosition);
                        break;
                }
            }

            private void showAdApp(Context context) {
                String packageNameAdApp = getString(R.string.package_name_ad_app);
                Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageNameAdApp);
                try{
                    context.startActivity(intent);
                }
                catch(Exception e){
                    Toast.makeText(context, "No application can handle this request."
                            + " Please install a " + getAppName(packageNameAdApp),  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            private String getAppName(String packageName) {
                String[] splitPackageName = packageName.split("\\.");
                String nameApp = splitPackageName[splitPackageName.length-1];
                String capitalizedName = nameApp.substring(0, 1).toUpperCase() + nameApp.substring(1).toLowerCase();
                return capitalizedName;
            }

            private void showFullScreenActivity(Integer adapterPosition) {
                Intent intent = new Intent(context, FullScreenActivity.class);
                intent.putExtra("index", adapterPosition);
                setResult(RESULT_OK, intent);
                try{
                    startActivity(intent);
                }
                catch(Exception e){
                    Toast.makeText(context, "GG WP",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        mainViewModel.downloadImagesAsync();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateData();
    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
    }

    private void initImageRecyclerView() {
        imagesRecyclerView = findViewById(R.id.recyclerView);
        imagesRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        imagesRecyclerView.setAdapter(new ImageAdapter(mainViewModel));
        imagesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) imagesRecyclerView.getLayoutManager();
                int lastImagePosition = mainViewModel.getPostsCount()-1;
                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == lastImagePosition) {
                    mainViewModel.downloadImagesAsync();
                    swipeRefreshLayout.setRefreshing(true);
                }
            }
        });
    }

    private void initMainViewModel() {
        mainViewModel = new ViewModelProvider(this, new MainViewModelFactory())
                .get(MainViewModel.class);
        //желательно передать контекст через фабрику (хз как)
        context = getBaseContext();
        mainViewModel.setContext(context);
    }

    private void updateData() {
        imagesRecyclerView.getAdapter().notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}