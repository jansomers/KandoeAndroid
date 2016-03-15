package be.kandoe_groepj.kandoeproject.kandoeproject.session;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import be.kandoe_groepj.kandoeproject.R;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.Session;
import butterknife.Bind;
import butterknife.ButterKnife;

public class SessionOverviewActivity extends AppCompatActivity {

    private SessionOverviewPresenter presenter;
    @Bind(R.id.sessionOverViewListView)
    ListView sessionOverviewList;
    //private SessionArrayAdapter adapterSession;
    private List<Session> sessionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_overview);
        setTitle("Mijn Sessies");
        ButterKnife.bind(this);
        presenter = new SessionOverviewPresenter();
        presenter.prepareRetrofit();
        sessionList = new ArrayList<>();
        prepareSessionListView(sessionList);
    }

    private List<Session> prepareData() {
        // DUMMY
       /* List<Session> sessionList = presenter.getSessionList();
        System.out.println("name===========");
        System.out.println(sessionList.get(1).getName());
        prepareSessionListView(sessionList);*/
        return presenter.getData();
    }

    private void prepareSessionListView(List<Session> sessionList) {
        System.out.println("JASPER HERE: " + sessionList);
        
//        Session [] sessions = new Session[sessionList.size()];
//        System.out.println("SESSIONLIST SIZE: " + sessionList.size());
//        int i = 0;
//        for (Session session : sessionList) {
//            sessions[i++] = session;
//            System.out.println("groupid: "+session.get_creatorId());
//        }
//
//        System.out.println("this: " + this.toString());
//        System.out.println(R.layout.session_list_item);
//        System.out.println(sessions.toString());
//        adapterSession = new SessionArrayAdapter(this, R.layout.session_list_item, sessions);
//        System.out.println("adapter was made");
//        sessionOverviewList.setAdapter(adapterSession);
//        System.out.println("adapter was set");
    }
}