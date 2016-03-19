package be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import be.kandoe_groepj.kandoeproject.R;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.Card;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.CircleSessionCardWrapper;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private LayoutInflater inflater;
    private List<Card> data = Collections.emptyList();
    private Context context;
    private View viewSelected = null;
    private Card cardSelected = null;
    private List<CircleSessionCardWrapper> wrappers;

    public CardAdapter(Context context, List<Card> data, List<CircleSessionCardWrapper> wrappers) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.wrappers = wrappers;
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
        View view = inflater.inflate(R.layout.theme_card_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Card current = data.get(position);
        holder.cardName.setText(current.getName());

        for (int i = 0; i < wrappers.size(); i++) {
            if (wrappers.get(i).getCard().getId().equals(current.getId())) {
                if (wrappers.get(i).isInPlay()) {
                    holder.cardName.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.textTerniary)));
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private List<View> selectedItems = new ArrayList<>();
    private List<Card> selectedCards = new ArrayList<>();

    public List<View> getSelectedItems() {
        return selectedItems;
    }

    public List<Card> getSelectedCards() {
        return selectedCards;
    }

    public void selectItem(View view, int position) {
        for (int i = 0; i < wrappers.size(); i++) {
            if (wrappers.get(i).getCard().getId().equals(data.get(position).getId())) {
                if (!wrappers.get(i).isInPlay()) {
                    if (!selectedItems.contains(view)) {
                        selectedCards.add(data.get(position));
                        ((TextView) view.findViewById(R.id.cardName)).setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorAccent)));
                        selectedItems.add(view);
                    } else {
                        selectedCards.remove(data.get(position));
                        ((TextView) view.findViewById(R.id.cardName)).setTextColor(Color.parseColor("#000000"));
                        selectedItems.remove(view);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public boolean hasSelectedItem() {
        return viewSelected != null;
    }

    public void setSelectedCards(String[] selectedCards) {
        //List<Card> selectedCardsLocal = new ArrayList<>();
        for (String id : selectedCards) {
            for (Card card : data) {
                if (card.getId().equals(id)) {
                    for (int i = 0; i < wrappers.size(); i++) {
                        if (wrappers.get(i).getCard().getId().equals(card.getId())) {
                            wrappers.get(i).setInPlay(true);
                        }
                    }
                }

          //          selectedCardsLocal.add(card);
            }
        }
        //this.selectedCards = selectedCardsLocal;


        notifyDataSetChanged();
        //((TextView) view.findViewById(R.id.cardName)).setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorAccent)));
        //holder.cardName.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.textTerniary)));
    }

    class CardViewHolder extends RecyclerView.ViewHolder {
        TextView cardName;

        public CardViewHolder(View itemView) {
            super(itemView);
            cardName = (TextView) itemView.findViewById(R.id.cardName);
        }
    }
}