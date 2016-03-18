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
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.Card;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.CardPosition;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.Session;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter.CardClickListener;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter.CardItemClickListener;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter.CardPositionAdapter;
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

public class GameActivity extends AppCompatActivity implements OnFinishListener {

    @Bind(R.id.recyclerCards)
    RecyclerView recyclerView;

    @Bind(R.id.makeMoveFab)
    android.support.v7.widget.AppCompatImageButton makeMoveButton;

    @Bind(R.id.circleBoardRelLay)
    RelativeLayout circleLayout;

    @Bind(R.id.playCircle)
    ImageView playCircle;

    @Bind(R.id.sessionName)
    TextView sessionName;


    ImageView prior1;
    ImageView prior2;
    ImageView prior3;
    ImageView prior4;
    ImageView prior5;

    //CardAdapter adapter;
    CardPositionAdapter adapter;
    CardApi cardApi;
    CardPositionApi cardPositionApi;
    private final String BASE_URL ="http://10.0.3.2:8080/api/" ;
    private int circleLayoutMargin = 5*2;
    private int circleParamWidth = 120;
    private int circleParamHeight = 120;
    Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    makeMoveButton.setClickable(true);
                    /*prior1.setImageResource(R.mipmap.ic_circle2pos);*/


                }
                showHighlight(adapter.getSelectedCardPosition());

            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    private void showHighlight(int selectedCardPosition) {

        switch (selectedCardPosition) {
            case (0): prior1.setImageResource(R.mipmap.ic_circle2pos);
                prior2.setImageResource(R.mipmap.ic_circle2pos);
                prior3.setImageResource(R.mipmap.ic_circle2pos);
                prior4.setImageResource(R.mipmap.ic_circle2pos);
                prior5.setImageResource(R.mipmap.ic_circle2pos);
                break;
            case (1): prior1.setImageResource(R.mipmap.ic_red_circle);
                prior2.setImageResource(R.mipmap.ic_circle2pos);
                prior3.setImageResource(R.mipmap.ic_circle2pos);
                prior4.setImageResource(R.mipmap.ic_circle2pos);
                prior5.setImageResource(R.mipmap.ic_circle2pos);
                break;
            case (2): prior1.setImageResource(R.mipmap.ic_circle2pos);
                prior2.setImageResource(R.mipmap.ic_red_circle);
                prior3.setImageResource(R.mipmap.ic_circle2pos);
                prior4.setImageResource(R.mipmap.ic_circle2pos);
                prior5.setImageResource(R.mipmap.ic_circle2pos);
                break;
            case (3): prior1.setImageResource(R.mipmap.ic_circle2pos);
                prior2.setImageResource(R.mipmap.ic_circle2pos);
                prior3.setImageResource(R.mipmap.ic_red_circle);
                prior4.setImageResource(R.mipmap.ic_circle2pos);
                prior5.setImageResource(R.mipmap.ic_circle2pos);
                break;
            case (4): prior1.setImageResource(R.mipmap.ic_circle2pos);
                prior2.setImageResource(R.mipmap.ic_circle2pos);
                prior3.setImageResource(R.mipmap.ic_circle2pos);
                prior4.setImageResource(R.mipmap.ic_red_circle);
                prior5.setImageResource(R.mipmap.ic_circle2pos);
                break;
            case (5): prior1.setImageResource(R.mipmap.ic_circle2pos);
                prior2.setImageResource(R.mipmap.ic_circle2pos);
                prior3.setImageResource(R.mipmap.ic_circle2pos);
                prior4.setImageResource(R.mipmap.ic_circle2pos);
                prior5.setImageResource(R.mipmap.ic_red_circle);
                break;
            default: prior1.setImageResource(R.mipmap.ic_circle2pos);
                prior2.setImageResource(R.mipmap.ic_circle2pos);
                prior3.setImageResource(R.mipmap.ic_circle2pos);
                prior4.setImageResource(R.mipmap.ic_circle2pos);
                prior5.setImageResource(R.mipmap.ic_circle2pos);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
        prepareViews();


    }

    private void prepareViews() {


        //Priority 1
        prior1 = new ImageView(this);
        prior1.setImageResource(R.mipmap.ic_red_circle);
        prior1.setId(R.id.prior1);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(circleParamWidth - 15, circleParamHeight-15);
        int centerXofLayoutMin = playCircle.getWidth()/2;
        int centerYofLayoutMin = playCircle.getHeight()/2;
        params2.leftMargin = centerXofLayoutMin - (circleParamWidth-15)/2 - 365;
        params2.topMargin = centerYofLayoutMin - (circleParamHeight-15)/2 - 365;
        circleLayout.addView(prior1, params2);

        //Priority 2
        prior2 = new ImageView(this);
        prior2.setImageResource(R.mipmap.ic_red_circle);
        prior2.setId(R.id.prior2);
        RelativeLayout.LayoutParams para = new RelativeLayout.LayoutParams(circleParamWidth-5,circleParamHeight-5);
        para.leftMargin = centerXofLayoutMin - circleParamWidth/2 - 285;
        para.topMargin = centerYofLayoutMin - circleParamHeight/2 - 285;
        circleLayout.addView(prior2, para);

        //Priority 3
        prior3 = new ImageView(this);
        prior3.setImageResource(R.mipmap.ic_red_circle);
        prior3.setId(R.id.prior3);
        RelativeLayout.LayoutParams para4 = new RelativeLayout.LayoutParams(circleParamWidth,circleParamHeight);
        para4.leftMargin = centerXofLayoutMin - circleParamWidth/2 - 205;
        para4.topMargin = centerYofLayoutMin - circleParamHeight/2 - 205;
        circleLayout.addView(prior3, para4);

        //Priority 4
        prior4 = new ImageView(this);
        prior4.setImageResource(R.mipmap.ic_red_circle);
        prior4.setId(R.id.prior4);
        RelativeLayout.LayoutParams para5 = new RelativeLayout.LayoutParams(circleParamWidth+10,circleParamHeight+10);
        para5.leftMargin = centerXofLayoutMin - (circleParamWidth)/2 - 125;
        para5.topMargin = centerYofLayoutMin - (circleParamHeight)/2 - 125;
        circleLayout.addView(prior4, para5);

        //Priority 5
        prior5 = new ImageView(this);
        prior5.setImageResource(R.mipmap.ic_red_circle);
        prior5.setId(R.id.prior5);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(circleParamWidth +30, circleParamHeight+30);
        Log.d("Circlelayout", "WIDTH: " + circleLayout.getWidth() + "- LENGTH: " + circleLayout.getHeight());
        int centerXofLayout = playCircle.getWidth()/2;
        int centerYofLayout = playCircle.getHeight()/2;
        params.leftMargin = centerXofLayout - (circleParamWidth +30)/2;
        params.topMargin = centerYofLayout - (circleParamHeight+30)/2;
        circleLayout.addView(prior5, params);
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

    @Override
    public void finished() {

    }
}
