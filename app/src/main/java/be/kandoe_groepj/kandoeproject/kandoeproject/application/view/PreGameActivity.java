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

import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import be.kandoe_groepj.kandoeproject.R;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.api.CardApi;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.api.SessionApi;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.Card;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.CircleSessionCardWrapper;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.CircleSessionMoveResponse;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.Session;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter.adapters.CardAdapter;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter.listeners.CardClickListener;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter.listeners.CardItemClickListener;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter.listeners.OnFinishListener;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.helper.SocketIOWrapper;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.helper.SocketListener;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.helper.TokenIO;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.helper.TypescriptTypeAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreGameActivity extends AppCompatActivity implements OnFinishListener, SocketListener {

    private final String BASE_URL = "http://kandoe.be/api/"; //"http://10.0.3.2:8080/api/";
    private final String TITEL = "KaartSelectie ";

    //region binds
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.recyclerCardsPregame)
    RecyclerView recyclerView;

    @Bind(R.id.swipeCards)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.bSendCardSelection)
    android.support.v7.widget.AppCompatImageButton bSendCardSelection;
    //endregion

    CardAdapter adapter;
    CardApi cardApi;
    SessionApi sessionApi;
    private Session session;
    private SocketIOWrapper socketIOWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSharedPref();
        setContentView(R.layout.activity_pre_game);
        setTitle(TITEL);
        ButterKnife.bind(this);
        configToolbar();

        prepareRetrofit();
        prepareSessionRetrofit();
        prepareData(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCards();
            }
        });
        initButtonClickEvent();
        socketIOWrapper = new SocketIOWrapper(this, session.getId());
        
        checkIfIsMyTurn();
    }

    private void checkIfIsMyTurn() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String userId = TokenIO.getUserId();
                String sessionUserId = session.getCurrentPlayerId();
                if (!TokenIO.getUserId().equals(session.getCurrentPlayerId())) {
                    bSendCardSelection.setVisibility(View.INVISIBLE);
                } else {
                    bSendCardSelection.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void prepareSessionRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(
                        GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(CircleSessionMoveResponse.class, new TypescriptTypeAdapter<>(CircleSessionMoveResponse.class)).create()))
                .build();

        sessionApi = retrofit.create(SessionApi.class);
    }

    private void initButtonClickEvent() {
        bSendCardSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<Card> selectedCards = adapter.getSelectedCards();
                JSONArray array = new JSONArray();
                for (int i = 0; i < selectedCards.size(); i++)
                    array.put(selectedCards.get(i).getId());

                try {
                    System.out.println("JASPER HERE: " + session.getId() + " - " + TokenIO.loadToken() + " - " + array.toString(4));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sessionApi.initCards(session.getId(), TokenIO.loadToken(), array).enqueue(new Callback<CircleSessionMoveResponse>() {
                    @Override
                    public void onResponse(Call<CircleSessionMoveResponse> call, Response<CircleSessionMoveResponse> response) {
                        String[] selectedCardIds = new String[selectedCards.size()];
                        for (int i = 0; i < selectedCardIds.length; i++)
                            selectedCardIds[i] = selectedCards.get(i).getId();
                        socketIOWrapper.sendInitCard(response.body().getCurrentPlayerId(), selectedCardIds, response.body().isRoundEnded());
                    }

                    @Override
                    public void onFailure(Call<CircleSessionMoveResponse> call, Throwable t) {
                        System.out.println("JASPER NICE!!!! NOT");
                    }
                });
                System.out.println("JASPER HERE ALSO: " + session.getId() + " - " + TokenIO.loadToken());
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
        final Session current = (Session) getIntent().getSerializableExtra("Session");
        this.session = current;
        cardApi.getCards(current.getThemeId(), TokenIO.loadToken()).enqueue(new Callback<List<Card>>() {
            @Override
            public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {
                final List<Card> cards = response.body();
                cardApi.getCircleSessionCards(current.getId(), TokenIO.loadToken()).enqueue(new Callback<List<CircleSessionCardWrapper>>() {
                    @Override
                    public void onResponse(Call<List<CircleSessionCardWrapper>> call, Response<List<CircleSessionCardWrapper>> response) {
                        List<CircleSessionCardWrapper> wrappers = response.body();
                        prepareAdapter(cards, wrappers);
                        System.out.println("JASPER NICE");
                    }

                    @Override
                    public void onFailure(Call<List<CircleSessionCardWrapper>> call, Throwable t) {
                        System.out.println("JASPER DAMNIT");
                    }
                });

                callback.finished();
            }

            @Override
            public void onFailure(Call<List<Card>> call, Throwable t) {
                callback.finished();
            }
        });
    }

    private void prepareAdapter(List<Card> cards, List<CircleSessionCardWrapper> wrappers) {
        adapter = new CardAdapter(this, cards, wrappers);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new MyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recyclerView.addOnItemTouchListener(new CardItemClickListener(this, recyclerView, new CardClickListener() {
            @Override
            public void onClick(View view, int position) {
                adapter.selectItem(view, position);
                recyclerView.smoothScrollToPosition(position);
                if (adapter.hasSelectedItem()) {
                    /*makeMoveButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                    selectedCardId = adapter.getSelectedCardId();
                    if (isMyTurn()) {
                        showMakeMoveButton();
                        makeMoveButton.setClickable(true);
                    }*/
                    System.out.println("JASPER ??!! NICE");
                    /*prior1.setImageResource(R.mipmap.ic_circle2pos);*/

                }
              //  showHighlight(adapter.getSelectedCardPosition());
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

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

    @Override
    public void notifyMoveCard(String cardId, String message, String cardPosition) {

    }

    @Override
    public void notifyInitCard(final boolean roundEnded, final String currentPlayerId, final String[] cards) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                session.setCurrentPlayerId(currentPlayerId);
                checkIfIsMyTurn();
                if (roundEnded) {
                    System.out.println("ROUND ENDED NICE");
                    Intent gameIntent = new Intent(PreGameActivity.this, GameActivity.class);
                    gameIntent.putExtra("Session", session);
                    session.setCurrentPlayerId(currentPlayerId);
                    session.setIsPreGame(false);
                    startActivity(gameIntent);
                } else {
                    setCards(cards);
                    System.out.println("ROUND NOT YET ENDED!");
                    if (TokenIO.getUserId().equals(currentPlayerId)) {
                        System.out.println("IT'S YOUR TURN");
                    } else {
                        System.out.println("IT'S NOT YOUR TURN");
                    }
                }
            }
        });
    }

    public void setCards(String[] selectedCards) {
        adapter.setSelectedCards(selectedCards);
    }
}