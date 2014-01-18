package Recommendation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class UserSongTable {
    HashMap<String, Integer> ustable = new HashMap<String, Integer>();
    HashMap<String, Integer> utable = new HashMap<String, Integer>();
    HashMap<String, Integer> stable = new HashMap<String, Integer>();
    
   

    
	void IncUserSongCount(String user, String song) {
		
		
		String key = user+"+"+song;
		
		if (ustable.containsKey(key)) {
			
			int count = ustable.get(key);
			
			ustable.put(key, count+1);
			
		}
		else{
			ustable.put(key, 1);
			//System.out.println("user:"+user+" occuring first time");
		}
		utable.put(user, 1);
		stable.put(song, 1);
		
	}
	int GetUserSongCount(String user, String song) {
		String key = user+"+"+song;
		if (ustable.containsKey(key)) {

			return ustable.get(key);
		}
		else{

			int value=0;
			return value;
		}
	}
	int GetTotalUsers() {
		return utable.size();
	}
	int GetTotalSongs() {
		return stable.size();
	}
	String GetUser(int UsrID) {
		int ID = 0;
        for (String s : utable.keySet()) { 
        	if (ID == UsrID)
        		return s;
        	ID++;
        } 
        return "";
	}
	String GetSong(int SongID) {
		int ID = 0;
        for (String s : stable.keySet()) { 
        	if (ID == SongID)
        		return s;
        	ID++;
        } 
        return "";
	}
	int[] GetUserVector(String userID) {
		int totalsongs=GetTotalSongs();
		
		 int[] songcounts=new int[totalsongs];;
		for(int i=0; i < totalsongs; i++){
			String songName = GetSong(i);
			int songcount =GetUserSongCount(userID, songName);
			//System.out.println("User:"+userID+" played song:"+songName+" "+songcount+" times");
			songcounts[i]=songcount;
		
		}
		//System.out.println("The numbers are ");

		/*for(int i=0; i < songcounts.length; i++){
		    System.out.print(songcounts[i] + " ");
		}*/
		return songcounts;
	}
}
