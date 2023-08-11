package com.campany.game;

import com.campany.cards.Card;

import java.util.ArrayList;
import java.util.Random;

public class Player implements Runnable{
    private int id;
    private ArrayList<Card> cards= new ArrayList();
    private Game game;

    public Player(int id,Game game) {
        this.id = id;
        this.game=game;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public Card removeCard(int index){
        return cards.remove(index);
    }

    public void discardCards(){
        synchronized (cards){
        for (int i = 0; i < cards.size()-1; i++) {
            for (int j = i + 1; j < cards.size(); j++) {
                if (cards.get(i).canRemoved(cards.get(j))) {
                    //print the cards that discarded
                    String str = "";
                    str += "--------------------------------- \n";
                    str += ("player id : " + id + "\n");
                    str += ("discard : " + cards.remove(i) + "\n");
                    str += ("with " + cards.remove(j - 1) + "\n");
                    str += "--------------------------------- ";
                    System.out.println(str);
                    i--;
                    break;
                }
            }
        }
        }
    }

    @Override
    public void run() {
        //discard cards before start the game
        discardCards();

        //wait to make sure that all player discard their cards before start the game
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (game) {
            //keep player active until remains 1 player or the player discard all his cards
            while (!cards.isEmpty() && game.getPlayersSize()>1){
                //player wait until his turn come
                while (game.getPlayers().get(game.getTurn()).getId() != id) {
                    try {
                        game.wait();
                        //if there are 1 player the thread closed
                        if (game.getPlayersSize()==1){
                            return;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //select the next player
                Player nextPlayer = game.getNextPlayer();
                //choose a random number of index to take from next player cards
                Random rand = new Random();
                int cardIndex = rand.nextInt(nextPlayer.getCards().size());
                Card removed = nextPlayer.removeCard(cardIndex);
                cards.add(removed);
                //print the description of the move that happened
                System.out.println("---------------------------------");
                System.out.println("player : " +id+ " take a card from player : "+nextPlayer.id);
                System.out.println("the card is : "+removed.toString());
                System.out.println("---------------------------------");

                //discard cards
                discardCards();

                //if there are a cards with next player and the player take it
                //the next player go out from game
                if (nextPlayer.getCards().size()==0){
                    //if the next player is first player the next turn will be the first
                    //index in array.
                int firstPlayer=0;
                if((game.getTurn()+1)%game.getPlayersSize()==0){
                    firstPlayer=1;
                }

                    System.out.println("---------------------------------");
                    System.out.println("player "+game.getPlayers().get((game.getTurn()+1)%game.getPlayersSize()).getId()+" is out of the game");
                    System.out.println("---------------------------------");
                game.removePlayer((game.getTurn()+1)%game.getPlayersSize());
                game.setTurn(game.getTurn()-firstPlayer);
                }

                //if the player discard all his cards he goes out of game
                if(cards.size()==0){
                    System.out.println("---------------------------------");
                    System.out.println("player "+id+" is out of the game");
                    System.out.println("---------------------------------");
                    game.removePlayer(game.getTurn());
                    game.setTurn(game.getTurn()-1);
                }

                //go to next turn and notify all treads
                game.nextTurn();
                game.notifyAll();
            }
        }
    }
}
