package be.kandoe_groepj.kandoeproject.kandoeproject.application.view;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import be.kandoe_groepj.kandoeproject.R;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.api.CardApi;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.Card;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.Session;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter.CardAdapter;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter.CardClickListener;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter.CardItemClickListener;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GameActivity extends AppCompatActivity {

    @Bind(R.id.recyclerCards)
    RecyclerView recyclerView;

    @Bind(R.id.makeMoveFab)
    android.support.v7.widget.AppCompatImageButton makeMoveButton;

    @Bind(R.id.circleBoardRelLay)
    RelativeLayout circleLayout;

    @Bind(R.id.playCircle)
    ImageView playCircle;

    @Bind(R.id.sessionTitle)
    TextView sessionTitle;

    ImageView prior1;
    ImageView prior2;
    ImageView prior3;
    ImageView prior4;
    ImageView prior5;

    CardAdapter adapter;
    CardApi cardApi;
    PositionCircleView positionCircleView;
    private final String BASE_URL ="http://10.0.3.2:8080/api/" ;
    private int circleLayoutMargin = 5*2;
    private int circleParamWidth = 120;
    private int circleParamHeight = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_board);
        ButterKnife.bind(this);

        makeMoveButton.setClickable(false);
        adapter = new CardAdapter(this, new ArrayList<Card>());
        prepareRetrofit();
        prepareData();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new MyScrollingLinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new CardItemClickListener(this, recyclerView, new CardClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(GameActivity.this, "onCardClick" + position, Toast.LENGTH_SHORT).show();
                adapter.selectItem(view, position);
                recyclerView.smoothScrollToPosition(position);
                Log.d("Cardtest", "Item: " + adapter.getOne(position).get_name() + " is selected? " + view.isSelected());
                if (adapter.hasSelectedItem()) {
                    makeMoveButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                    makeMoveButton.setClickable(true);

                    prior1.setImageResource(R.mipmap.ic_circle2pos);


                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
        //CENTER MAX PRIORITY
        ImageView imView = new ImageView(this);
        imView.setImageResource(R.mipmap.ic_red_circle);
        imView.setId(R.id.prior5);
        prior1 = imView;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(circleParamWidth +30, circleParamHeight+30);
        Log.d("Circlelayout", "WIDTH: " + circleLayout.getWidth() + "- LENGTH: " + circleLayout.getHeight());
        int centerXofLayout = playCircle.getWidth()/2;
        int centerYofLayout = playCircle.getHeight()/2;
        params.leftMargin = centerXofLayout - (circleParamWidth +30)/2;
        params.topMargin = centerYofLayout - (circleParamHeight+30)/2;
        circleLayout.addView(imView, params);

        //MIN PRIORITY
        ImageView imViewMin = new ImageView(this);
        imViewMin.setImageResource(R.mipmap.ic_red_circle);
        imViewMin.setId(R.id.prior1);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(circleParamWidth - 15, circleParamHeight-15);
        int centerXofLayoutMin = playCircle.getWidth()/2;
        int centerYofLayoutMin = playCircle.getHeight()/2;
        params2.leftMargin = centerXofLayoutMin - (circleParamWidth-15)/2 - 365;
        params2.topMargin = centerYofLayoutMin - (circleParamHeight-15)/2 - 365;
        circleLayout.addView(imViewMin, params2);

        //2 PRIORITY
        //MIN PRIORITY
        ImageView im2 = new ImageView(this);
        im2.setImageResource(R.mipmap.ic_red_circle);
        im2.setId(R.id.prior2);
        RelativeLayout.LayoutParams para = new RelativeLayout.LayoutParams(circleParamWidth-5,circleParamHeight-5);
        para.leftMargin = centerXofLayoutMin - circleParamWidth/2 - 285;
        para.topMargin = centerYofLayoutMin - circleParamHeight/2 - 285;
        circleLayout.addView(im2, para);

        //3 PRIOR
        ImageView im3 = new ImageView(this);
        im3.setImageResource(R.mipmap.ic_red_circle);
        im3.setId(R.id.prior3);
        RelativeLayout.LayoutParams para4 = new RelativeLayout.LayoutParams(circleParamWidth,circleParamHeight);
        para4.leftMargin = centerXofLayoutMin - circleParamWidth/2 - 205;
        para4.topMargin = centerYofLayoutMin - circleParamHeight/2 - 205;
        circleLayout.addView(im3, para4);

        //4PRIOR
        ImageView im4 = new ImageView(this);
        im4.setImageResource(R.mipmap.ic_red_circle);
        im4.setId(R.id.prior4);
        RelativeLayout.LayoutParams para5 = new RelativeLayout.LayoutParams(circleParamWidth+10,circleParamHeight+10);
        para5.leftMargin = centerXofLayoutMin - (circleParamWidth)/2 - 125;
        para5.topMargin = centerYofLayoutMin - (circleParamHeight)/2 - 125;
        circleLayout.addView(im4, para5);

    }

    private void prepareData() {
        Session session = (Session) getIntent().getSerializableExtra("Session");
        cardApi.getCards(session.get_themeId()).enqueue(new Callback<List<Card>>() {
            @Override
            public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {
                Log.d("TEST","IN CARDRESPONSE ========");
                adapter.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Card>> call, Throwable t) {
                Log.d("TEST", "CARDFAILED==============");
                Toast.makeText(getBaseContext(), "failed", Toast.LENGTH_LONG);
            }
        });
    }

    private void prepareRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        cardApi = retrofit.create(CardApi.class);
    }


}
