package be.kandoe_groepj.kandoeproject.kandoeproject.application.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import be.kandoe_groepj.kandoeproject.kandoeproject.helper.TypescriptTypeAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SessionActivity extends AppCompatActivity implements OnFinishListener {

    private final String TITEL = "Sessie Overzicht";
    private final String BASE_URL = "http://10.0.3.2:8080/api/";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.recyclerSessions)
    RecyclerView recyclerView;

    @Bind(R.id.swipeSession)
    SwipeRefreshLayout swipeRefreshLayout;

    public SessionAdapter getAdapter() {
        return adapter;
    }

    SessionAdapter adapter;
    SessionApi sessionApi;

    String token;


    public void initSharedPref() {
        TokenIO.initSharedPreferences(getSharedPreferences("Test", Context.MODE_PRIVATE));
    }

    RecyclerView.OnItemTouchListener onItemTouchListener;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSharedPref();
        setContentView(R.layout.activity_session);
        setTitle(TITEL);
        ButterKnife.bind(this);
        //Toolbar settings
        configToolbar();
        token = TokenIO.loadToken();
        Log.d("test", "Loaded token in SessionActivity: " + token);

        recyclerView.setLayoutManager(new MyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshSessions();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter = new SessionAdapter(this,new ArrayList<Session>());
        prepareRetrofit();
        if (!getIntent().getBooleanExtra("isTest", false)) prepareData(this);
        recyclerView.setAdapter(adapter);

        if (onItemTouchListener != null)
            recyclerView.removeOnItemTouchListener(onItemTouchListener);
        onItemTouchListener = new SessionItemClickListener(this, recyclerView, new SessionClickListener() {
            @Override
            public void onClick(View view, int position) {
                Session session = adapter.getOne(position);
                if (!session.isStopped() && !session.isPreGame() && session.isInProgress()) {
                    Intent gameintent = new Intent(SessionActivity.this, GameActivity.class);
                    gameintent.putExtra("Session", session);
                    SessionActivity.this.startActivity(gameintent);
                } else if (!session.isStopped() && session.isPreGame() && session.isInProgress()) {
                    Intent pregameintent = new Intent(SessionActivity.this, PreGameActivity.class);
                    pregameintent.putExtra("Session", session);
                    SessionActivity.this.startActivity(pregameintent);
                } else if (!session.isStopped() && !session.isPreGame()) {
                    Toast.makeText(SessionActivity.this, "Sessie is niet up to date. Refresh of kies een andere", Toast.LENGTH_LONG).show();
                }
                else if (!session.isStopped() && !session.isInProgress() && !session.isPreGame()) {
                    Toast.makeText(SessionActivity.this, "Sessie is niet up to date. Refresh of kies een andere", Toast.LENGTH_LONG);
                }

            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(SessionActivity.this, "onLongClick" + position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.addOnItemTouchListener(onItemTouchListener);
    }

    public void prepareData(final OnFinishListener callback) {
        sessionApi.getUserSessions(token).enqueue(new Callback<List<Session>>() {
            @Override
            public void onResponse(Call<List<Session>> call, Response<List<Session>> response) {
                Log.d("test","BODY SIZE: " + response.body().size());
                Log.d("test", "Token: " + TokenIO.loadToken());
                Log.d("test", "Token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJfdHlwZSI6IndlYiIsIl9uYW1lIjoiUm9iSGVuZHJpY2t4IiwiX2VtYWlsIjoicm9iLmhlbmRyaWNreEBzdHVkZW50LmtkZy5iZSIsIl9pZCI6IjU2ZTAzNzhmOTljNDM2YzdiYjRkNmEwYyJ9.d51772e45d44a99454040bd508f5bfc8d4ff25194a81e94891d47cbd51d0600f");
                adapter.addAll(response.body());
                Log.d("test", "ADAPTER SIZE: " + adapter.getData().size());
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
