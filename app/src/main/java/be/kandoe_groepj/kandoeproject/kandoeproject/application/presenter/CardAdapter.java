package be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import be.kandoe_groepj.kandoeproject.R;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.Card;

/**
 * Created by Jan on 8/03/2016.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private LayoutInflater inflater;
    List<Card> data = Collections.emptyList();
    Context context;
    View viewSelected =null;
    Card cardSelected =null;

    public CardAdapter(Context context, List<Card> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    public void add(Card card) {
        data.add(card);
        notifyDataSetChanged();
    }

    public void addAll(List<Card> cards) {
        data.addAll(cards);
        notifyDataSetChanged();
    }

    public void remove(Card card) {
        data.remove(card);
    }

    public void removeAll(List<Card> cards) {
        data.removeAll(cards);
        notifyDataSetChanged();
    }

    public List<Card> getData() {
        return data;
    }

    public Card getOne(int position) {
        return data.get(position);
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.session_card_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Card current = data.get(position);
        Log.d("TEST", ("NAME CURRENT====== "+current.get_name()));
        holder.cardName.setText(current.get_name());
        holder.priority.setText(current.get_cardPosition());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void selectItem(View view, int position) {
        if (viewSelected != null) viewSelected.setSelected(false);
        viewSelected = view;
        viewSelected.setSelected(true);
        cardSelected = data.get(position);
        Toast.makeText(context,"Selected: " + cardSelected.get_name(),Toast.LENGTH_SHORT).show();
        notifyDataSetChanged();
    }

    public boolean hasSelectedItem() {
        if (viewSelected!=null) return true;
        return false;
    }

    class CardViewHolder extends RecyclerView.ViewHolder {

        TextView cardName;
        TextView priority;

        public CardViewHolder(View itemView) {
            super(itemView);
            cardName = (TextView) itemView.findViewById(R.id.cardName);
            priority = (TextView) itemView.findViewById(R.id.priority);

        }
    }
}


