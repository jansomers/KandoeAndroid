package be.kandoe_groepj.kandoeproject.kandoeproject.session;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.Session;

/**
 * Created by Jan on 3/03/2016.
 */
public class SessionArrayAdapter extends ArrayAdapter<Session> {

    Context mContext;
    int layoutResourceId;
    Session sessions[] = null;


    public SessionArrayAdapter(Context context, int resource, Session[] objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.layoutResourceId = resource;
        this.sessions = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            // inflate the layout
//            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
//            convertView = inflater.inflate(layoutResourceId, parent, false);
//        }
//
//        //object item based on position
//        Session session = sessions[position];
//        System.out.println(session.isOpenOrClosed());
//        TextView sessionName = (TextView) convertView.findViewById(R.id.sessionNameTextView);
//        sessionName.setText("Todo");
//        TextView closed = (TextView) convertView.findViewById(R.id.sessionclosedTextView);
//        ImageView status = (ImageView) convertView.findViewById(R.id.sessionStatus);
//        if (!session.isOpenOrClosed()) {
//            closed.setText("Planned:");
//            status.setImageResource(R.mipmap.ic_comment_planned);
//
//        }
//
//
//        TextView timeremaing = (TextView) convertView.findViewById(R.id.sessionTimeRemainingTextView);
//        timeremaing.setText(session.getTimeRemaining());


        return convertView;

    }
}
