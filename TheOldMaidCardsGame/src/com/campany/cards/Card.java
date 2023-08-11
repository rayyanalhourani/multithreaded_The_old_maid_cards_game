package com.campany.cards;

public class Card {

    String Symbol;
    String Type;
    String Color;

    public Card(String symbol, String type, String color) {
        Symbol = symbol;
        Type = type;
        Color = color;
    }

    public boolean canRemoved(Card card){
        //check if player can discard 2 cards
        if(this.Color.equals(card.Color) && this.Symbol.equals(card.Symbol)){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Card{" +
                "Symbol='" + Symbol + '\'' +
                ", Type='" + Type + '\'' +
                ", Color='" + Color + '\'' +
                '}';
    }
}
