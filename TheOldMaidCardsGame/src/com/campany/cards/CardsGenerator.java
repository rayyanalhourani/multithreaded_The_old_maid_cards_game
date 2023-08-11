package com.campany.cards;

import java.util.ArrayList;

public class CardsGenerator implements Runnable{
    public static ArrayList<Card> cardDeck;
    String color;
    String type;
    String symbols[];
    ArrayList<Card> temp=new ArrayList<Card>();

    public CardsGenerator(ArrayList<Card> cardDeck, String[] symbols, String type, String color) {
        this.cardDeck = cardDeck;
        this.color = color;
        this.type = type;
        this.symbols = symbols;
    }

    @Override
    public void run() {
        //create each type of type cards separately and add them to original cards deck
            if (type.equals("Joker")) {
                temp.add(new Card(symbols[13], type, color));
            } else {
                for (int i = 0; i < 13; i++) {
                    temp.add(new Card(symbols[i], type, color));
                }
            }
        synchronized (cardDeck){
            cardDeck.addAll(temp);
        }
    }
}
