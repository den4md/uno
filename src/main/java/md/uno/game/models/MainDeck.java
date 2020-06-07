package md.uno.game.models;

import md.uno.game.GameLogic;
import md.uno.game.models.Cards.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainDeck extends Deck
{
    public MainDeck()
    {
        super();
    }

    public MainDeck(ArrayList<Card> cards)
    {
        super(cards);
    }

    public MainDeck(Card card)
    {
        super(card);
    }

    public Card getTop()
    {
        return cards.get(cards.size() - 1);
    }

    @Override
    public void put(Card card)
    {
        if (GameLogic.isPutable(this.getTop(), card))
        {
            cards.add(card);
        }
    }

    public ArrayList<Card> releaseForShuffle()
    {
//        Card topCard = this.stack.get(this.stack.size()-1);
//        this.stack.remove(this.stack.size()-1);
//        ArrayList<Card> releasedCards = (ArrayList<Card>) this.stack.clone();
//        this.stack.clear();
//        return releasedCards;

//        Integer[] array = new Integer[cards.size() - 1];
//        Arrays.setAll(array, p -> p);
//        ArrayList<Integer> orderNumbers = new ArrayList<>(Arrays.asList(array));
//        return releaseSeveral(orderNumbers);

        Collections.reverse(cards);
        return releaseSequence(cards.size()-1);
    }
}