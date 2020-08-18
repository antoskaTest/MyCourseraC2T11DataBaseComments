package com.courseraandroid.myfirstappcoursera.albums;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.courseraandroid.myfirstappcoursera.ApiUtils;
import com.courseraandroid.myfirstappcoursera.AppDelegate;
import com.courseraandroid.myfirstappcoursera.R;
import com.courseraandroid.myfirstappcoursera.album.DetailAlbumFragment;
import com.courseraandroid.myfirstappcoursera.db.MusicDao;
import com.courseraandroid.myfirstappcoursera.model.Album;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AlbumsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefresher;
    private View mErrorView;

    public static String TAG = "TAG";

    @NonNull
    private final AlbumsAdapter mAlbumAdapter = new AlbumsAdapter(new AlbumsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(Album album) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, DetailAlbumFragment.newInstance(album))
                    .addToBackStack(DetailAlbumFragment.class.getSimpleName())
                    .commit();

        }
    });

    public static AlbumsFragment newInstance() {
        return new AlbumsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.recycler);
        mRefresher = view.findViewById(R.id.refresher);
        mRefresher.setOnRefreshListener(this);
        mErrorView = view.findViewById(R.id.errorView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.albums);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAlbumAdapter);

        onRefresh();
    }

    @Override
    public void onRefresh() {
        mRefresher.post(() -> {
            //mRefresher.setRefreshing(true);
            getAlbums();
        });
    }

    //@SuppressLint("CheckResult")
    private void getAlbums() {

        ApiUtils.getApi()
                .getAlbums()
                .doOnSuccess(new Consumer<List<Album>>() {
                    @Override
                    public void accept(List<Album> albums) throws Exception {
                        Log.d(TAG, "accept:  doOnSuccess");
                        getMusicDao().insertAlbums(albums);
                        Log.d(TAG, "accept:  doOnSuccess");
                    }
                })
                .onErrorReturn(new Function<Throwable, List<Album>>() {
                    @Override
                    public List<Album> apply(Throwable throwable) throws Exception {
                        if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass())) {
                            return getMusicDao().getAlbums();
                        } else {
                            return null;
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mRefresher.setRefreshing(true);
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        mRefresher.setRefreshing(false);
                    }
                })
                .subscribe(new Consumer<List<Album>>() {
                    @Override
                    public void accept(List<Album> albums) throws Exception {
                        mErrorView.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mAlbumAdapter.addData(albums, true);
                    }
                }
                , new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mErrorView.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    }
                });
    }

    private MusicDao getMusicDao() {
        return ((AppDelegate)getActivity().getApplication()).getMusicDatabase().getMusicDao();
    }
}
