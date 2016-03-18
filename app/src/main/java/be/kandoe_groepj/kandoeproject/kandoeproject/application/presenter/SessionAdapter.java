package be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import be.kandoe_groepj.kandoeproject.R;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.api.UserApi;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.api.UserApiFactory;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.Session;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.User;
import be.kandoe_groepj.kandoeproject.kandoeproject.helper.TokenIO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jan on 7/03/2016.
 */
public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.SessionViewHolder> implements OnFinishListener {

    private LayoutInflater inflater;
    List<Session> data = Collections.emptyList();
    List<User> usersInCurrent = Collections.emptyList();
    Context context;
    private final String YOUR_TURN = "Jouw Beurt";
    UserApi userApi;

    public SessionAdapter(Context context, List<Session> data) {
        inflater =LayoutInflater.from(context);
        this.data = data;
        this.context =context;
        userApi = UserApiFactory.getApi();
    }

    public void add(Session session){
        data.add(session);
        notifyDataSetChanged();
    }

    public void addAll(List<Session> sessions){
        for (Session session: sessions) {
            if (!session.isStopped()) add(session);
        }
        notifyDataSetChanged();
    }

    public void remove(Session session) {
        data.remove(session);
    }

    public void removeAll(List<Session> sessions) {
        data.removeAll(sessions);
        notifyDataSetChanged();
    }

    public List<Session> getData() {
        return data;
    }
    public Session getOne(int position) {
        return data.get(position);
    }
    @Override
    public SessionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.session_item, parent, false);
        return new SessionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SessionViewHolder holder, int position) {
        Session current = data.get(position);
        getUsersInCurrent(current);
        String currentUsername = getCurrentUserName(current.getCurrentPlayerId());
        holder.sessionTitle.setText(current.getName());
        if (current.isInProgress() && !current.isPreGame()) {
            if (current.getCurrentPlayerId().equals(TokenIO.getUserId())) {
                holder.userTurn.setText(YOUR_TURN);
                holder.statusImg.setImageResource(R.drawable.ic_notifications_on_24dp);
                holder.userTurn.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorAccent)));
            } else {
                holder.userTurn.setText("Other's turn");
                holder.userTurn.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorPrimaryLight)));
                holder.statusImg.setImageResource(R.drawable.ic_games_48px);
            }
        }
        else {
            if (current.isPreGame() && !current.isStopped()) {
                if (current.getCurrentPlayerId().equals(TokenIO.getUserId())) {
                    holder.userTurn.setText(YOUR_TURN);
                    holder.userTurn.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorAccent)));
                    holder.statusImg.setImageResource(R.drawable.ic_event_available_24dp);
                }
                else {
                    holder.userTurn.setText("Other's turn");
                    holder.userTurn.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorPrimaryLight)));
                    holder.statusImg.setImageResource(R.drawable.ic_event_available_24dp);
                    holder.statusImg.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorPrimaryLight)));
                }
            }
        }
    }

    private String getCurrentUserName(String currentPlayerId) {
        for (User user : usersInCurrent) {
            if (currentPlayerId.equals(user.getId()))return user.getName();
        }
        return "No User";
    }

    private void getUsersInCurrent(Session current) {
        String[] userIds = current.getUserIds();
        String output = "[";
        for (int i = 0; i < userIds.length; i++)
            output += "\"" + userIds[i] + "\"" + (i != userIds.length - 1 ? "," : "]");
        System.out.println("JASPER: " + output);

        userApi.getSessionUsers(output).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                usersInCurrent = response.body();
                finished();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("Test", "Failed to get users in current session" + t.getMessage());
                finished();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void finished() {

    }

    class SessionViewHolder extends RecyclerView.ViewHolder {
        TextView sessionTitle;
        ImageView statusImg;
        TextView userTurn;

        public SessionViewHolder(View itemView) {
            super(itemView);
            sessionTitle = (TextView) itemView.findViewById(R.id.sessionTitle);
            statusImg = (ImageView) itemView.findViewById(R.id.sessionStatus);
            userTurn = (TextView) itemView.findViewById(R.id.userTurn);

        }
    }
}
