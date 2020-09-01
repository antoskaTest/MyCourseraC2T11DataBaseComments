package com.courseraandroid.myfirstappcoursera.album;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.courseraandroid.myfirstappcoursera.comments.CommentsFragment;
import com.courseraandroid.myfirstappcoursera.db.MusicDao;
import com.courseraandroid.myfirstappcoursera.model.Album;
import com.courseraandroid.myfirstappcoursera.model.AlbumSong;
import com.courseraandroid.myfirstappcoursera.model.Song;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class DetailAlbumFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String ALBUM_KEY = "ALBUM_KEY";
    private static final String TAG = "TAG";

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefresher;
    private View mErrorView;
    private Album mAlbum;

    @NonNull
    private final SongsAdapter mSongsAdapter = new SongsAdapter();

    public static DetailAlbumFragment newInstance(Album album) {
        Bundle args = new Bundle();
        args.putSerializable(ALBUM_KEY, album);

        DetailAlbumFragment fragment = new DetailAlbumFragment();
        fragment.setArguments(args);

        return fragment;
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

        mAlbum = (Album) getArguments().getSerializable(ALBUM_KEY);

        getActivity().setTitle(mAlbum.getName());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mSongsAdapter);

        onRefresh();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.comment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionComment:
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, CommentsFragment.newInstance(mAlbum))
                        .addToBackStack(CommentsFragment.class.getName())
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefresh() {
        mRefresher.post(() -> {
            //mRefresher.setRefreshing(true);
            getAlbum();
        });
    }

    @SuppressLint("CheckResult")
    private void getAlbum() {
        MusicDao dao = getMusicDao();
        ApiUtils.getApi()
                .getAlbum(mAlbum.getId())
                .doOnSuccess(new Consumer<Album>() {
                    @Override
                    public void accept(Album album) throws Exception {
                        dao.insertSongs(album.getSongs());
                        int i = 1;
                        for(Song song: album.getSongs()){
                            dao.insertAlbumSong(new AlbumSong(i, album.getId(), song.getId()));
                            i++;
                        }
                    }
                })
                .onErrorReturn(new Function<Throwable, Album>() {
                    @Override
                    public Album apply(Throwable throwable) throws Exception {
                        if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass())) {
                            //загрузка альбомов из бд , если нет интернета
                            Album album = dao.getAlbum(mAlbum.getId());
                            List<Song> songs = dao.getSongByAlbumId(mAlbum.getId());
                            album.setSongs(songs);
                            return album;
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
                .subscribe(new Consumer<Album>() {
                    @Override
                    public void accept(Album album) throws Exception {
                        mErrorView.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mSongsAdapter.addData(album.getSongs(), true);
                    }
                }, new Consumer<Throwable>() {
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
