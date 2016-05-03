package scores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * This is the class HighScore which allows to retrieve scores on ThingSpeak server
 * @version 1.0
 * @author Ricoeur-Hassan 
 * @since 2016-05-03
 */
public class HighScore1 {
	
	String channelID;
	String serverAdressBegin="https://api.thingspeak.com/channels/";
	String serverAdressRequest="/feeds.csv";
	String splitSeparator=",";
	
	/**
	 * Constructor of a HighScore class object
	 * 
	 * @param ID a string which is the ID of your ThingSpeak channel
	 */
	public HighScore1(String ID) {
		this.channelID = ID;
	}
	
	/**
	 * Function which allows to download all scores from a ThingSpeak server
	 * 
	 * @exception IOException if file not present on server
	 * @exception MalformedURLException if connection impossible with server
	 * @return ArrayList<String[]> which are couples of scores and players
	 */
	public ArrayList<String[]> getAllScore()
	{
		//Construction de l'adresse compl�te du serveur pour r�cup�rer feeds.csv
		String serverAdress=this.serverAdressBegin+this.channelID+this.serverAdressRequest;
		
		ArrayList<String[]> listScore = new ArrayList<String[]>();
		URL myAdress = null;
		try {
			//Cr�ation d'un objet URL avec l'adresse du serveur ThingSpeak
			myAdress = new URL(serverAdress);
			try {
				//Ouverture d'une connexion vers le serveur
				InputStream input = myAdress.openStream();
				InputStreamReader inputR = new InputStreamReader(input);
				BufferedReader br=new BufferedReader(inputR);
				
				String ligne;
				ligne=br.readLine();
				int compt=0;
				
				//Parcours du fichier pr�sent sur le serveur et r�cup�ration des scores
				while (ligne!=null){
					String[] data = ligne.split(this.splitSeparator);
					
					//On enleve la premi�re ligne car c'est l'ent�te et on v�rifie que la ligne comporte tous les champs pour enlever les lignes vides
					if(compt>0 && data.length==4)
					{
						String[] insertTab = new String[2];
						insertTab[0]=data[2];
						insertTab[1]=data[3];
						listScore.add(insertTab);
					}
					compt++;
					
					ligne=br.readLine();
				}
				br.close(); 
				
				
			} catch (IOException e) {
				System.out.println("Impossible de lire le fichier pr�sent sur le serveur");
			}
		}catch(MalformedURLException me){
		      System.out.println("Impossible d'acc�s aux fichiers sur le serveur");
		}
		
		return listScore;
		
	}
	
	/**
	 * Method which with the getAllscore method retrieves the scores and players, 
	 * and sorts this scores in descendant order and keeps just the 10 best scores
	 * @exception NumberFormatException if conversion string to int failed
	 * @return ArrayList<String[]> Store 10 best couples of scores and players
	 */
	public ArrayList<String[]> getScore()
	{
		ArrayList<String[]> scoreUnranked = this.getAllScore();
		ArrayList<String[]> scoreRanked = new ArrayList<String[]>();
		int compt=0;
		
		//Tant qu'il reste des couples dans la liste des scoreUnranked et qu'on a pas les 10 meilleurs
		while(scoreUnranked.size()>0 && compt<10)
		{
			ListIterator itr = scoreUnranked.listIterator();
			int max= -1;
			String[] cplMax = new String[2];
			
			//On r�cup�re le couple score,player avec le score le plus grand
			while (itr.hasNext()){
				String[] result =(String[]) itr.next();
				try{
					int value = Integer.parseInt(result[0]);
					if(value>max){
						max=value;
						cplMax=result;
					}
			    }
			    catch(NumberFormatException e){
			        System.out.println("Probl�me survenue lors de la conversion de string en int."+e.getMessage());
			    }
			}
			
			//On ajoute ce couple � la liste des scoreRanked et on le supprime des Unranked
			scoreRanked.add(cplMax);
			scoreUnranked.remove(cplMax);
			compt++;
		}
		return scoreRanked;
		
	}
}