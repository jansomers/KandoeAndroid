package be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import be.kandoe_groepj.kandoeproject.R;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.Session;

/**
 * Created by Jan on 7/03/2016.
 */
public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.SessionViewHolder> {

    private LayoutInflater inflater;
    List<Session> data = Collections.emptyList();
    Context context;

    public SessionAdapter(Context context, List<Session> data) {
        inflater =LayoutInflater.from(context);
        this.data = data;
        this.context =context;
    }

    public void add(Session session){
        data.add(session);
        notifyDataSetChanged();
    }

    public void addAll(List<Session> sessions){
        data.addAll(sessions);
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
        View view = inflater.inflate(R.layout.session_item, parent,false);
        return new SessionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SessionViewHolder holder, int position) {
        Session current = data.get(position);
        holder.sessionTitle.setText(current.getName());
        if (position == 1) {
            holder.userTurn.setTextColor(context.getResources().getColor(R.color.colorAccent));
            holder.userTurn.setTypeface(null, Typeface.BOLD);
            holder.statusImg.setImageResource(R.drawable.ic_notifications_on_24dp);
            holder.statusImg.setColorFilter(context.getResources().getColor(R.color.colorAccent));
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
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
