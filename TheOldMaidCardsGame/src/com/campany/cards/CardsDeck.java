package com.campany.cards;

import java.util.ArrayList;
import java.util.Collections;

public class CardsDeck {
    //init the cards symbols and colors and types
    String symbols[] = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "Joker"};
    String colors[] = {"Red", "Black"};
    String types[] = {"Spades", "Clubs", "Diamonds", "Hearts", "Joker"};
    public ArrayList<Card> cardsDeck = new ArrayList();

    public ArrayList<Card> createBasicCardDeck() throws InterruptedException {


        //create each type of cards separately
        Runnable spades = new CardsGenerator(cardsDeck,symbols, types[0], colors[1]);
        Runnable clubs = new CardsGenerator(cardsDeck,symbols, types[1], colors[1]);
        Runnable diamonds = new CardsGenerator(cardsDeck,symbols, types[2], colors[0]);
        Runnable hearts = new CardsGenerator(cardsDeck,symbols, types[3], colors[0]);
        Runnable joker = new CardsGenerator(cardsDeck,symbols, types[4], colors[1]);

        //assign each type of cards to thread
        Thread th1 = new Thread(spades);
        Thread th2 = new Thread(clubs);
        Thread th3 = new Thread(diamonds);
        Thread th4 = new Thread(hearts);
        Thread th5 = new Thread(joker);

        th1.start();
        th2.start();
        th3.start();
        th4.start();
        th5.start();

        th1.join();
        th2.join();
        th3.join();
        th4.join();
        th5.join();

        //shuffle cards
        Collections.shuffle(cardsDeck);
        return cardsDeck;
    }
}


