package com.campany.game;

import com.campany.cards.Card;

import java.util.ArrayList;

class CardsDistributor implements Runnable{
    int id;
    int numOfPlayers;
    ArrayList<Card> deck;
    Player player;

    public CardsDistributor(int id, int numOfPlayers, ArrayList<Card> deck, Player player) {
        this.id = id;
        this.numOfPlayers = numOfPlayers;
        this.deck = deck;
        this.player = player;
    }
    //distribute cards that start from first player then second and like
    //that until all card distributed
    public void run(){
        ArrayList<Card> temp = new ArrayList<Card>();
        for (int i = id; i <deck.size() ; i+=numOfPlayers) {
            temp.add(deck.get(i));
        }
        player.setCards(temp);
    }
}
