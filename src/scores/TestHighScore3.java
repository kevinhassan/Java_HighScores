/**
 * 
 */
package scores;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;
import java.util.Scanner;

/**
 * This is the class TestHighScore which allows to select a fake score from a file for a new player
 * @version 1.0
 * @author Ricoeur-Hassan 
 * @since 2016-05-03 
 */
public class TestHighScore3 {
	/**
	 * @param arg
	 * @exception InputMismatchException if name's type input is incorrect 
	 * @exception NumberFormatException if conversion string to int failed
	 * @exception FileNotFoundException if problem occurred during opening
	 * @exception IOException if problem if problem occurred during reading
	 */
	public static void main (String [] arg){
		ArrayList<Integer> scoreArray= new ArrayList<Integer>();
		String playerName="";
		String splitSeparator=",";
		int playerScore;
		Scanner sc = new Scanner(System.in);
		
		//Création d'un nouvel objet HighScore
		HighScore3 hg = new HighScore3("109183");
		
		//Récupération des 10 meilleurs scores du serveur ThingSpeak
		ArrayList<String[]> scoresList = hg.getScore();
		ArrayList<BestPlayer3> highscoresList = hg.tenBestScores(scoresList);
		
		//Affichage du tableau des 10 meilleurs scores 
		System.out.println("Tableau des meilleurs scores");
		System.out.println("----------------------------");
		
		//Parcours de la liste des meilleurs grâce à un iterateur
		ListIterator itr = highscoresList.listIterator();
		while (itr.hasNext())
		{
			BestPlayer3 result = (BestPlayer3) itr.next();
			System.out.println(result.getPlayer()+" | "+result.getScore());
		}
		
		//Demande à l'utilisateur de rentrer un nom
		try{
			System.out.println("Veuillez rentrer un nom de joueur :");
			playerName = sc.nextLine();
			System.out.println("Bonjour, "+playerName);
		}
		catch (java.util.InputMismatchException e){
            System.out.println("Le nom que vous avez rentré ne correspond pas au type attendu"+e.getMessage());
        }
		
		try {
			//Ouverture du fichier txt contenant les scores
			String file = "scoreSamples.txt";
			BufferedReader br = new BufferedReader(new FileReader(file));
			String ligne = br.readLine();
			//Parcours de toutes les lignes du fichier
			while(ligne!=null){
			      String[] data = ligne.split(splitSeparator);
			      try
			      {
			    	  //Ajout du score à la liste des scores
			    	  scoreArray.add(Integer.parseInt(data[0]));
			      }
			      catch(NumberFormatException e){
			        System.out.println("Problème survenu lors de la conversion de string en int."+e.getMessage());
			      }
			      ligne=br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("Problème survenu dans l'ouverture du fichier"+e.getMessage());
		} catch (IOException ex) {
			System.out.println("Problème survenu dans la lecture du fichier"+ex.getMessage());
		}
		
		//Génération d'un nombre random qui va aller de 0 à taille de liste des scores -1
		Random randomGenerator = new Random();
		int index = randomGenerator.nextInt(scoreArray.size());
		
		playerScore=scoreArray.get(index);
		

		//Parcours de la liste des meilleurs grâce à un iterateur
		ListIterator itr = highscoresList.listIterator();
		boolean inTheTen = false;//True if player's score is in 10 best scores

		//On parcourt tous les scores et si 1 seul est inférieur à celui de notre joueur => c'est gagné !
		while (inTheTen && itr.hasNext()){
			BestPlayer3 bp = (BestPlayer3) itr.next();
			if (playerScore.compareTo(bp) == 1)
			{
				System.out.println("Vous fêtes parti des 10 meilleurs !");
				hg.sendScore(new BestPlayer3(playerName,playerScore));//On le save sur thingSpeak
				inTheTen = true;
			}
		}
		//Affichage du nom et du score random du joueur
		System.out.println("Félicitation "+playerName+", vous avez réalisé un score de "+playerScore);
		
	}
}
