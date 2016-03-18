package be.kandoe_groepj.kandoeproject.kandoeproject.application.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import be.kandoe_groepj.kandoeproject.R;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.api.CardApi;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.Card;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.Session;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter.CardAdapter;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter.OnFinishListener;
import be.kandoe_groepj.kandoeproject.kandoeproject.helper.TokenIO;
import be.kandoe_groepj.kandoeproject.kandoeproject.helper.TypescriptTypeAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreGameActivity extends AppCompatActivity implements  OnFinishListener{

    private final String BASE_URL ="http://10.0.3.2:8080/api/";
    private final String TITEL = "KaartSelectie ";
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.recyclerCardsPregame)
    RecyclerView recyclerView;

    @Bind(R.id.swipeCards)
    SwipeRefreshLayout swipeRefreshLayout;

    CardAdapter adapter;
    CardApi cardApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSharedPref();
        setContentView(R.layout.activity_pre_game);
        setTitle(TITEL);
        ButterKnife.bind(this);
        configToolbar();

        prepareAdapter();
        prepareRetrofit();
        prepareData(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCards();
            }
        });


    }

    private void refreshCards() {
        adapter.removeAll(adapter.getData());
        prepareData(this);
        onCardsLoadComplete();
    }

    private void onCardsLoadComplete() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void prepareData(final OnFinishListener callback) {
        Session current = (Session) getIntent().getSerializableExtra("Session");
        cardApi.getCards(current.getThemeId(), TokenIO.loadToken()).enqueue(new Callback<List<Card>>() {
            @Override
            public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {
                adapter.addAll(response.body());
                callback.finished();
            }

            @Override
            public void onFailure(Call<List<Card>> call, Throwable t) {
                callback.finished();
            }
        });
    }

    private void prepareAdapter() {
        adapter = new CardAdapter(this, new ArrayList<Card>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new MyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        prepareRetrofit();
    }

    private void prepareRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(
                        GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(Card.class, new TypescriptTypeAdapter<>(Card.class)).create()))
                .build();

        cardApi = retrofit.create(CardApi.class);
    }

    private void configToolbar() {
        setSupportActionBar(toolbar);
    }

    public void initSharedPref() {
        TokenIO.initSharedPreferences(getSharedPreferences("Test", Context.MODE_PRIVATE));
    }

    @Override
    public void finished() {

    }
}
