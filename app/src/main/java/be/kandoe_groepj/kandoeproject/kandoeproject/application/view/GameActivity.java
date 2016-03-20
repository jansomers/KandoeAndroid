package be.kandoe_groepj.kandoeproject.kandoeproject.application.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import be.kandoe_groepj.kandoeproject.R;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.api.CardApi;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.api.CardPositionApi;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.api.SessionApi;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.Card;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.CardPosition;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.Session;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter.listeners.CardClickListener;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter.listeners.CardItemClickListener;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter.adapters.CardPositionAdapter;
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

public class GameActivity extends AppCompatActivity implements OnFinishListener, SocketListener {

    private SocketIOWrapper socketIOWrapper;

    //region View binding
    @Bind(R.id.recyclerCards)
    RecyclerView recyclerView;

    @Bind(R.id.bPlayCard)
    android.support.v7.widget.AppCompatImageButton makeMoveButton;

    @Bind(R.id.circleBoardRelLay)
    RelativeLayout circleLayout;

    @Bind(R.id.playCircle)
    ImageView playCircle;

    @Bind(R.id.sessionName)
    TextView sessionName;
    //endregion

    private ImageView[] priors;
    private CardPositionAdapter adapter;
    private CardApi cardApi;
    private CardPositionApi cardPositionApi;
    private SessionApi sessionApi;
    private final String BASE_URL ="http://10.0.3.2:8080/api/" ;
    private Session session;
    private String selectedCardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        priors = new ImageView[5];
        initSharedPref();
        setContentView(R.layout.game_board);
        ButterKnife.bind(this);
        makeMoveButton.setClickable(false);
        adapter = new CardPositionAdapter(this, new ArrayList<Card>(), new ArrayList<CardPosition>());
        prepareTitle();
        prepareCardRetrofit();
        prepareCardPositionRetrofit();
        prepareCards(this);
        prepareCardPositions(this);
        prepareAdapter();
        prepareSessionApiRetrofit();
        prepareMakeMoveButton();
        if (!isMyTurn()) hideMakeMoveButton();
        socketIOWrapper = new SocketIOWrapper(this, session.getId());
    }

    private void playCard(final String cardId) {
        final CardPosition cardPosition = new CardPosition();
        cardPosition.setCardId(cardId);
        sessionApi.playCard(session.getId(), TokenIO.loadToken(), cardPosition).enqueue(new Callback<CardPosition>() {
            @Override
            public void onResponse(Call<CardPosition> call, Response<CardPosition> response) {
                System.out.println("JASPER NIGGER: " + session.getCurrentPlayerId() + " - " + response.body().getCurrentPlayerId());
                session.setCurrentPlayerId(response.body().getCurrentPlayerId());
                System.out.println("JASPER I DID MOVE!");
                socketIOWrapper.sendMoveCard(cardId, session.getCurrentPlayerId(), Integer.parseInt(adapter.getCardPosition(cardId)) + 1);
            }

            @Override
            public void onFailure(Call<CardPosition> call, Throwable t) {
                System.out.println("jasper not ok");
                t.printStackTrace();
            }
        });
    }

    private void prepareMakeMoveButton() {
        makeMoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedCardId.isEmpty()) {
                    playCard(selectedCardId);
                }
            }
        });
    }

    private void hideMakeMoveButton() {
        makeMoveButton.setVisibility(View.INVISIBLE);
    }

    private void showMakeMoveButton() {
        makeMoveButton.setVisibility(View.VISIBLE);
    }

    private void prepareTitle() {
        session = (Session) getIntent().getSerializableExtra("Session");
        sessionName.setText(session.getName());
    }

    public void initSharedPref() {
        TokenIO.initSharedPreferences(getSharedPreferences("Test", Context.MODE_PRIVATE));
    }

    private void prepareAdapter() {

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new MyScrollingLinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new CardItemClickListener(this, recyclerView, new CardClickListener() {
            @Override
            public void onClick(View view, int position) {
                adapter.selectItem(view, position);
                recyclerView.smoothScrollToPosition(position);
                if (adapter.hasSelectedItem()) {
                    makeMoveButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                    selectedCardId = adapter.getSelectedCardId();
                    if (isMyTurn()) {
                        showMakeMoveButton();
                        makeMoveButton.setClickable(true);
                    }
                    /*prior1.setImageResource(R.mipmap.ic_circle2pos);*/

                }
                showHighlight(adapter.getSelectedCardPosition());
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    private boolean isMyTurn() {
        return session.getCurrentPlayerId().equals(TokenIO.getUserId());
    }

    private void showHighlight(int selectedCardPosition) {
        for (int i = 0; i < 5; i++)
            priors[i].setImageResource(selectedCardPosition == (i + 1) ? R.mipmap.ic_red_circle : R.mipmap.ic_circle2pos);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
        prepareViews();
    }

    private void prepareViews() {
        int[] x = {-15, -5, 0, 10, 30};
        int[] y = {-365, -285, -205, -125, 0};
        for (int i = 0; i < 5; i++) {
            priors[i] = new ImageView(this);
            priors[i].setImageResource(R.mipmap.ic_red_circle);
            int id = getResources().getIdentifier("prior" + (i + 1), "id", getPackageName());
            priors[i].setId(getResources().getIdentifier("prior" + (i + 1), "id", getPackageName()));
            int circleParamWidth = 120;
            int circleParamHeight = 120;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(circleParamWidth + x[i], circleParamHeight + x[i]);
            int centerXofLayoutMin = playCircle.getWidth() / 2;
            int centerYofLayoutMin = playCircle.getHeight() / 2;
            params.leftMargin = centerXofLayoutMin - (circleParamWidth + (i == 0 || i == 4 ? x[i] : 0)) / 2 + y[i];
            params.topMargin = centerYofLayoutMin - (circleParamHeight + (i == 0 || i == 4 ? x[i] : 0)) / 2 + y[i];
            circleLayout.addView(priors[i], params);
        }
    }

    private void prepareCardPositions(final OnFinishListener callback) {
        Session session = (Session) getIntent().getSerializableExtra("Session");
        /*Log.d("CardPreparedata", "Theme id from session: " + session.getThemeId());
        cardApi.getCards(session.getThemeId(), TokenIO.loadToken()).enqueue(new Callback<List<Card>>() {
            @Override
            public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {
                Log.d("Card","IN CARDRESPONSE");;
                if (response.body() != null) adapter.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Card>> call, Throwable t) {
                Log.d("Card", "IN CARDFAILURE");
                Toast.makeText(getBaseContext(), "failed", Toast.LENGTH_LONG);
            }
        });*/

        cardPositionApi.getCardPositions(session.getId(), TokenIO.loadToken()).enqueue(new Callback<List<CardPosition>>() {
            @Override
            public void onResponse(Call<List<CardPosition>> call, Response<List<CardPosition>> response) {
                Log.d("Jan", "In response getcardpositions");
                Log.d("Jan", "Body CardPos size: " + response.body().size());
                if (response.body() != null) {
                    adapter.addAllCardPositions(response.body());
                    adapter.notifyDataSetChanged();
                    callback.finished();
                }
            }

            @Override
            public void onFailure(Call<List<CardPosition>> call, Throwable t) {
                Log.d("Jan", "In failure getcardpositions: " + t.getMessage());
                callback.finished();

            }
        });
    }

    private void prepareCards(final OnFinishListener callback) {
        Session session = (Session) getIntent().getSerializableExtra("Session");
        cardApi.getCards(session.getThemeId(), TokenIO.loadToken()).enqueue(new Callback<List<Card>>() {
            @Override
            public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {
                Log.d("Jan", "In response getcards");
                Log.d("Jan", "Body size Cards: " + response.body().size());
                if (response.body() != null) {
                    adapter.addAllCards(response.body());
                    adapter.notifyDataSetChanged();
                    callback.finished();
                }
            }

            @Override
            public void onFailure(Call<List<Card>> call, Throwable t) {
                Log.d("GameActivity", "In getCards failed : " + t.getMessage());
                callback.finished();
            }
        });
    }

    private void prepareCardRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(
                        GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(Card.class, new TypescriptTypeAdapter<>(Card.class)).create()))
                .build();

        cardApi = retrofit.create(CardApi.class);
    }

    private void prepareCardPositionRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(
                        GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(CardPosition.class, new TypescriptTypeAdapter<>(CardPosition.class)).create()))
                .build();

        cardPositionApi = retrofit.create(CardPositionApi.class);
    }

    private void prepareSessionApiRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(
                        GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(CardPosition.class, new TypescriptTypeAdapter<>(CardPosition.class)).create()))
                .build();

        sessionApi = retrofit.create(SessionApi.class);
    }

    @Override
    public void finished() {

    }

    @Override
    public void notifyMoveCard(final String cardId, String message, final String cardPosition) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.incrementScore(cardId);
                adapter.unselectAll();
                showHighlight(Integer.parseInt(cardPosition));
                showMakeMoveButton();
            }
        });
    }

    @Override
    public void notifyInitCard(boolean isPreGame, String currentPlayerId, String[] cards) {

    }
}
