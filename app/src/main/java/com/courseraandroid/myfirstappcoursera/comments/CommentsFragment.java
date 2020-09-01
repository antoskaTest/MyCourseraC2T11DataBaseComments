package com.courseraandroid.myfirstappcoursera.comments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.courseraandroid.myfirstappcoursera.ApiUtils;
import com.courseraandroid.myfirstappcoursera.R;
import com.courseraandroid.myfirstappcoursera.model.Album;
import com.courseraandroid.myfirstappcoursera.model.Comment;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Response;

public class CommentsFragment  extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String ALBUM_KEY = "ALBUM_KEY";
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefresher;
    private View mErrorView;
    private Album mAlbum;
    private final CommentsAdapter mCommentsAdapter = new CommentsAdapter();
    private Button mButSendComment;
    private TextView mTextViewComent;
    private static final String TAG = "TAG";

    public static CommentsFragment newInstance(Album album){
        Bundle bundle = new Bundle();
        bundle.putSerializable(ALBUM_KEY, album);
        CommentsFragment fragment = new CommentsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_comments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.recycler);
        mRefresher = view.findViewById(R.id.refresher);
        mRefresher.setOnRefreshListener(this);
        mErrorView = view.findViewById(R.id.errorView);
        mButSendComment = view.findViewById(R.id.but_comment);
        mTextViewComent = view.findViewById(R.id.et_comment);
        mButSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postComment();
            }
        });
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAlbum = (Album) getArguments().getSerializable(ALBUM_KEY);

        getActivity().setTitle(mAlbum.getName());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mCommentsAdapter);

        onRefresh();
    }

    @Override
    public void onRefresh() {
        mRefresher.post(new Runnable() {
            @Override
            public void run() {
                getComments();
            }
        });
    }

    @SuppressLint("CheckResult")
    private void getComments() {
        ApiUtils.getApi()
                .getCommentsForAlbum(mAlbum.getId())
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
                .subscribe(new Consumer<List<Comment>>() {
                    @Override
                    public void accept(List<Comment> comments) throws Exception {
                        mErrorView.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mCommentsAdapter.addData(comments, true);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mErrorView.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void getComment(int commentId){
        ApiUtils.getApi()
                .getComment(commentId)
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
                .subscribe(new Consumer<Comment>() {
                    @Override
                    public void accept(Comment comment) throws Exception {
                        mCommentsAdapter.addComment(comment);
                        mErrorView.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        //mCommentsAdapter.addComment(comment);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mErrorView.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void postComment() {
        Comment comment = new Comment(mAlbum.getId(), mTextViewComent.getText().toString());
        ApiUtils.getApi()
                .postComment(comment)
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
                        mTextViewComent.setText(null);
                        mTextViewComent.clearFocus();
                    }
                })
                .subscribe(new Consumer<Response<CommentId>>() {
                    @Override
                    public void accept(Response<CommentId> commentResponse) throws Exception {
                        getComment(commentResponse.body().getId());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "accept: " + throwable.getMessage());
                        if (throwable instanceof HttpException) {
                            Response response = ((HttpException) throwable).response();
                            Log.d(TAG, "accept: code = " + response.code());
                            switch (response.code()) {
                                case 201:
                                    showMessage(R.string.ok);
                                    break;
                                case 500:
                                    showMessage(R.string.error_500);
                                    break;
                                case 400:
                                    showMessage(R.string.error_400);
                                    break;
                                case 401:
                                    showMessage(R.string.error_401);
                            }
                            showMessage(R.string.request_error);
                        }
                    }
                });
    }

    private void showMessage(@StringRes int string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_LONG).show();
    }
}
