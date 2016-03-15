package be.kandoe_groepj.kandoeproject.kandoeproject.application.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import be.kandoe_groepj.kandoeproject.R;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.api.SessionApi;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.Session;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter.OnFinishListener;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter.SessionAdapter;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter.SessionClickListener;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter.SessionItemClickListener;
import be.kandoe_groepj.kandoeproject.kandoeproject.helper.TokenIO;
import be.kandoe_groepj.kandoeproject.kandoeproject.login.TypescriptTypeAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SessionActivity extends AppCompatActivity implements OnFinishListener {

    private final String BASE_URL = "http://10.0.3.2:8080/api/";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Bind(R.id.recyclerSessions)
    private RecyclerView recyclerView;

    @Bind(R.id.swipeSession)
    SwipeRefreshLayout swipeRefreshLayout;

    public SessionAdapter getAdapter() {
        return adapter;
    }

    SessionAdapter adapter;
    SessionApi sessionApi;


    public void initSharedPref() {
        TokenIO.initSharedPreferences(getPreferences(Context.MODE_PRIVATE));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSharedPref();
        setContentView(R.layout.activity_session);
        ButterKnife.bind(this);
        //Toolbar settings
        configToolbar();

        adapter = new SessionAdapter(this,new ArrayList<Session>());
        prepareRetrofit();
        if (!getIntent().getBooleanExtra("isTest", false)) prepareData(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new MyLinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView.addOnItemTouchListener(new SessionItemClickListener(this, recyclerView, new SessionClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(SessionActivity.this, "onClick"+position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SessionActivity.this, GameActivity.class);
                Session session = adapter.getOne(position);
                intent.putExtra("Session",session);
                SessionActivity.this.startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(SessionActivity.this, "onLongClick"+position, Toast.LENGTH_SHORT).show();


            }
        }));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshSessions();
            }
        });


    }

    public void prepareData(final OnFinishListener callback) {
        sessionApi.getUserSessions(TokenIO.loadToken()).enqueue(new Callback<List<Session>>() {
            @Override
            public void onResponse(Call<List<Session>> call, Response<List<Session>> response) {
                adapter.addAll(response.body());
                adapter.notifyDataSetChanged();
                callback.finished();
            }

            @Override
            public void onFailure(Call<List<Session>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getBaseContext(), "failed", Toast.LENGTH_LONG).show();
                callback.finished();
            }
        });
    }

    private void refreshSessions() {
        adapter.removeAll(adapter.getData());
        prepareData(this);
        onSessionsLoadComplete();
    }

    private void onSessionsLoadComplete() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void prepareRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(
                        GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(Session.class, new TypescriptTypeAdapter<>(Session.class)).create()))
                .build();

        sessionApi = retrofit.create(SessionApi.class);
    }


    private void configToolbar() {
        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
//        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void finished() {

    }
}
