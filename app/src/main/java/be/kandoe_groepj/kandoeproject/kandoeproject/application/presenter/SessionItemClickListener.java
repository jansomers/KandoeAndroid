package be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by Jan on 8/03/2016.
 */
public class SessionItemClickListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;
    private SessionClickListener sessionClickListener;

    public SessionItemClickListener(Context context, final RecyclerView recyclerView, final SessionClickListener sessionClickListener) {
        this.sessionClickListener = sessionClickListener;
        Log.d("Test","Constructor SessionItemCLick invoked");
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d("Test","onSingleTapp" + e);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
                if (child!=null && sessionClickListener!=null) {
                    sessionClickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                }
                Log.d("Test","onLongPress"+e);
            }
        });
    }
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(),e.getY());
        if (child!=null && sessionClickListener!=null & gestureDetector.onTouchEvent(e)) {
            sessionClickListener.onClick(child, rv.getChildAdapterPosition(child));

        }
        Log.d("Test","On intercept invoked" + gestureDetector.onTouchEvent(e) + " " + e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.d("Test", "On touchevent invoked");
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
