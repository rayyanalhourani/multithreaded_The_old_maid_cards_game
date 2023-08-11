package com.campany.game;

import com.campany.cards.Card;
import com.campany.cards.CardsDeck;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    CardsDeck cardsDeck = new CardsDeck();
    private int turn=0;
    int numOfPlayers;
    ArrayList<Card> gameCardsDeck;
    private ArrayList<Player> players = new ArrayList<Player>();

    public Game() throws InterruptedException {
        this.gameCardsDeck = cardsDeck.createBasicCardDeck();
    }

    private void createPlayersWithCards() throws InterruptedException {
        ArrayList<Thread> threads = new ArrayList<Thread>();
        //distribute cards on player using many threads
        for(int i=0;i<numOfPlayers;i++){
            players.add(new Player(i,this));
            Runnable task = new CardsDistributor(i,numOfPlayers, gameCardsDeck,players.get(i));
            Thread th = new Thread(task);
            threads.add(th);
            th.start();
        }
        for (Thread thread :threads ) {
            thread.join();
        }
        gameCardsDeck.clear();
    }

    public void startGame() throws InterruptedException {
        ArrayList<Thread> playersThreads = new ArrayList<Thread>();

        enterPlayersNumber();

        createPlayersWithCards();

        //assign each player to a thread
        for (Player player : players) {
            Thread thread = new Thread(player);
            playersThreads.add(thread);
            thread.start();
        }
        for (Thread thread :playersThreads ) {
            thread.join();
        }

        //after all players discard all cards and there are one player that have the joker card
        //print the loser id
        System.out.println("The Old maid is the player with id : " + players.get(0).getId());
    }

    public void enterPlayersNumber(){
        //enter number of players
        Scanner scanner = new Scanner(System.in);
        while (numOfPlayers<2 || numOfPlayers>12) {
            System.out.println("Enter number of players between 2 and 12");
            numOfPlayers = scanner.nextInt();
        }
    }

    public void nextTurn(){
        turn = (turn + 1) % players.size();
    }

    public Player getNextPlayer(){
        return players.get((turn+1)%players.size());
    }

    public void removePlayer(int index){
        players.remove(index);
    }


    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getPlayersSize() {
        return players.size();
    }

}
