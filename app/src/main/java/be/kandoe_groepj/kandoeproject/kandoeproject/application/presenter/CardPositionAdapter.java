package be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import be.kandoe_groepj.kandoeproject.R;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.Card;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.CardPosition;

public class CardPositionAdapter extends RecyclerView.Adapter<CardPositionAdapter.CardPositionViewHolder> {
    private LayoutInflater inflater;
    private List<Card> cards = Collections.emptyList();
    private List<CardPosition> cardPositions = Collections.emptyList();
    private Context context;
    private View viewSelected = null;
    private CardPosition cardPositionSelected = null;

    public CardPositionAdapter(Context context, List<Card> cards, List<CardPosition> cardPositions) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.cardPositions = cardPositions;
        this.cards = cards;
    }

    @Override
    public CardPositionAdapter.CardPositionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.session_card_item, parent, false);
        return new CardPositionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardPositionAdapter.CardPositionViewHolder holder, int position) {
        CardPosition current = cardPositions.get(position);
        Card matchingCard = getMatchingCard(current.getCardId());
        holder.cardName.setText(matchingCard.getName());
        holder.priority.setText(current.getPosition()+ "");

    }


    private Card getMatchingCard(String cardId) {
        Card matchingCard = new Card();
        for (Card card : cards) {
            if (card.getId().equals(cardId)) matchingCard = card;
        }
        return matchingCard;
    }

    public void unselectAll() {
        if (viewSelected != null) {
            viewSelected.setSelected(false);
            viewSelected = null;
        }
        cardPositionSelected = null;
        notifyDataSetChanged();
    }

    public void selectItem(View view, int position) {
        if (viewSelected != null) viewSelected.setSelected(false);
        viewSelected = view;
        viewSelected.setSelected(true);
        cardPositionSelected = cardPositions.get(position);
        notifyDataSetChanged();
    }

    public boolean hasSelectedItem() {
        return viewSelected != null;
    }
    @Override
    public int getItemCount() {
        return cardPositions.size();
    }

    public void addACard(Card card) {
        cards.add(card);
    }
    public void addAllCards(List<Card> body) {
        cards.addAll(body);
        notifyDataSetChanged();
    }
    public void removeACard(Card card) {
        cards.remove(card);
    }

    public int getSelectedCardPosition() {
        return cardPositionSelected.getPosition();
    }

    public String getSelectedCardId() {
        return cardPositionSelected.getCardId();
    }

    public void removeAllCards() {
        cards.removeAll(cards);
    }

    public void addAllCardPositions(List<CardPosition> body) {
        Log.d("Jan", "cardPosistions to add: " + body.size());
        Log.d("Jan", "cardPositionslist in adapter: " + cardPositions.size());
        cardPositions.addAll(body);
        Log.d("Jan", "cardPositions added to adapter: " + cardPositions.size());
        for (CardPosition cardPosition: cardPositions) {
            Log.d("Jan", "cardposition Id: " + cardPosition.getCardId() + " Position: " + cardPosition.getPosition());
        }
        notifyDataSetChanged();

    }

    private void removeAllCardPositions() {
        cardPositions.removeAll(cardPositions);
    }

    public void incrementScore(String cardId) {
        for (CardPosition cardPosition : cardPositions) {
            if (cardPosition.getCardId().equals(cardId)) {
                cardPosition.setPosition(cardPosition.getPosition() + 1);
                notifyDataSetChanged();
            }
        }
    }

    public String getCardPosition(String cardId) {
        for (CardPosition cardPosition : cardPositions) {
            if (cardPosition.getCardId().equals(cardId)) {
                System.out.println("JASPER HELLO: " + cardPosition.getPosition());
                return String.valueOf(cardPosition.getPosition());
            }
        }
        System.out.println("JASPER HELLO NOT FOUND :'(");
        return "";
    }

    public

    class CardPositionViewHolder extends RecyclerView.ViewHolder {

        TextView cardName;
        TextView priority;

        public CardPositionViewHolder(View itemView) {
            super(itemView);
            cardName = (TextView) itemView.findViewById(R.id.cardName);
            priority = (TextView) itemView.findViewById(R.id.priority);
        }
    }
}
