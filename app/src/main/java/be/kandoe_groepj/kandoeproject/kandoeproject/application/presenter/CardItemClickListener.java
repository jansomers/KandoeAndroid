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
public class CardItemClickListener implements RecyclerView.OnItemTouchListener {
    private CardClickListener cardClickListener;
    private GestureDetector gestureDetector;

    public CardItemClickListener(Context context,final RecyclerView recyclerView, final CardClickListener cardClickListener) {
        this.cardClickListener = cardClickListener;
        Log.d("Test","Constructor CardItemClick Invoked");
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && cardClickListener != null) {
                    cardClickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d("Test","onSingleTapp"+e);
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(),e.getY());
        if (child!=null && cardClickListener!=null && gestureDetector.onTouchEvent(e)) {
            cardClickListener.onClick(child,rv.getChildAdapterPosition(child));
        }
        Log.d("CardTest","On intercept invoked" + gestureDetector.onTouchEvent(e) + " " +e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.d("CardTest", "On Touchevent Invoked");
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
