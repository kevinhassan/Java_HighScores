package scores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HighScore1 {

	String channelID;
	String serverAdressBegin="https://api.thingspeak.com/channels/";
	String serverAdressRequest="/feeds.csv";
	String splitSeparator=",";
	
	HighScore1(String ID) {
		this.channelID = ID;
	}
	
	ArrayList<String[]> getAllScore()
	{
		//Construction de l'adresse complète du serveur pour récupérer feeds.csv
		String serverAdress=this.serverAdressBegin+this.channelID+this.serverAdressRequest;
		
		ArrayList<String[]> listScore = new ArrayList<String[]>();
		URL myAdress = null;
		try {
			//Création d'un objet URL avec l'adresse du serveur ThingSpeak
			myAdress = new URL(serverAdress);
			try {
				//Ouverture d'une connexion vers le serveur
				InputStream input = myAdress.openStream();
				InputStreamReader inputR = new InputStreamReader(input);
				BufferedReader br=new BufferedReader(inputR);
				
				String ligne;
				ligne=br.readLine();
				int compt=0;
				
				//Parcours du fichier présent sur le serveur et récupération des scores
				while (ligne!=null){
					String[] data = ligne.split(this.splitSeparator);
					
					//On enlève la première ligne car c'est l'entête et on vérifie que la ligne comporte tous les champs pour enlever les lignes vides
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
				System.out.println("Impossible de lire le fichier présent sur le serveur");
			}
		}catch(MalformedURLException me){
		      System.out.println("Impossible d'accès aux fichiers sur le serveur");
		}
		
		return listScore;
		
	}
}
