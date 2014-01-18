package Recommendation;

import java.io.*; 
import java.util.ArrayList; 
import java.util.Iterator;
import java.util.List; 

import UI.MyListener;
import UI.MyListener.*;  
import weka.core.*; 
import weka.clusterers.*; 
import weka.filters.*; 
import weka.filters.unsupervised.attribute.AddCluster; 
import weka.filters.unsupervised.attribute.Remove; 
  
public class MusicRecomm { 
  
    public static void main(String[] args) throws FileNotFoundException {
    
    String logged_user =  getCurrentUser();  
    List<Integer> clusterNumsList = new ArrayList<Integer>();   
    int Nclusters =  getNumOfClusters();  
    Instances acData = null;
         
    try { 
        Instances data =  getData(); 
        String[][] instance_splits =  new String[Nclusters][3];      
        Instances rmData = ApplyRemoveFilter(data); 
        acData =  ApplyAddClusterFilter(rmData); //Filter.useFilter(rmData, addcluster); 
          
        //setting class attribute 
        //newData.setClassIndex(0);                             // classify based on user 
                  
        SimpleKMeans kmeans = getKmeansClusterer(Nclusters, rmData); //new SimpleKMeans(); 
          
        //System.out.println("FUNCTION USED :" + kmeans.distanceFunctionTipText()); 
        Instances centroids = kmeans.getClusterCentroids(); 
                  
        ClusterEvaluation eval = new ClusterEvaluation(); 
        eval.setClusterer(kmeans); 
        eval.evaluateClusterer(rmData); 
          
        System.out.println(eval.clusterResultsToString()); 
        System.out.println("\n\nCluster centroids are : " ); 
          
        for(int i=0;i<Nclusters;i++) 
        { 
            instance_splits[i] =  centroids.instance(i).toString().split(","); 
            System.out.println("User:"+instance_splits[i][0]+"is centroid of cluster:" + (i+1) ); 
            if(logged_user.compareTo(instance_splits[i][0]) ==0) 
            { 
                clusterNumsList.add(i+1); 
            } 
        } 
          
       
          
        boolean success = write_to_file(acData); 
          
        if(success==true) 
        { 
              
        } 
          
          
    } 
      
    catch (IOException e) { 
        // TODO Auto-generated catch block 
        e.printStackTrace(); 
    } catch (Exception e) { 
        // TODO Auto-generated catch block 
        e.printStackTrace(); 
    } 
	//Instances data =  getData1();
    Instances data =  acData;
	UserSongTable table = new UserSongTable();
	CosineSimilarity similar= new CosineSimilarity();
	//Fill tblr
	//System.out.println("Loading User-Song table");
	int instanceNo= data.numInstances();
	//String[] userNames =  new String[instanceNo];
	//String[] artistNames =  new String[instanceNo];
	
	for (int i = 0; i < data.numInstances(); i++) {
		String thisClusterName = data.instance(i).stringValue(3);
		if (ClusterNameInList(thisClusterName, clusterNumsList) ) {
			//userNames[i]= data.instance(i).stringValue(0);
			//artistNames[i]= data.instance(i).stringValue(1);
			String userName = data.instance(i).stringValue(0);
			String songName = data.instance(i).stringValue(1);
			table.IncUserSongCount(userName,songName);
			System.out.println(thisClusterName+ " adding user/song "+userName+"/"+songName);
		}
	}
	System.out.println("User-Song table loaded, now constructing vectors...");
	
	int totalusers = table.GetTotalUsers();
	Similarity [] similarities = new Similarity[totalusers-1];
	int simi_index = 0;
	Similarity first = new Similarity() ;
	Similarity second = new Similarity();
	
	int [] vec1 = table.GetUserVector( getCurrentUser());
	
	for(int i=0; i<totalusers; i++) {
		String nextuser = table.GetUser(i);
		
		if (!nextuser.equals(getCurrentUser())) {
			int [] vec2 = table.GetUserVector(nextuser);
		
			similarities[simi_index] = new Similarity();
			similarities[simi_index].user = nextuser;
			similarities[simi_index].value = similar.calculateSimilarity(vec1, vec2);
							
			simi_index++;
		}
		
	}
	
	for(int i=0; i<2; i++) {
		for(int j=0; j<(totalusers-2)-i; j++) {
			if (similarities[j].value > similarities[j+1].value) {
				Similarity temp = similarities[j];
				similarities[j] = similarities[j+1];
				similarities[j+1] = temp;
			}
		}
	}
	
	first = similarities[totalusers-2];
	second = similarities[totalusers-3];
	
	int [] first_vec = table.GetUserVector( first.user);
	int [] second_vec = table.GetUserVector( second.user);
	
	int [] first_song_count = {0,0,0};
	int [] first_song_id = new int[3];
	int [] second_song_count = {0,0,0};
	int [] second_song_id = new int[3];
	
	int totalsongs = table.GetTotalSongs();
	
	for(int i=0; i<totalsongs; i++) {
		
		if (first_vec[i] > first_song_count[0]) {
			first_song_count[0] = first_vec[i];

			first_song_id[0] = i;
		
		}
		else if (first_vec[i] > first_song_count[1]) {
			first_song_count[1] = first_vec[i];
			first_song_id[1] = i;
			
		}
		else if (first_vec[i] > first_song_count[2]) {
			first_song_count[2] = first_vec[i];
			first_song_id[2] = i;
			
		}
		
	}
	
	
	for(int i=0; i<totalsongs; i++) {

		if (second_vec[i] > second_song_count[0]) {
			
			second_song_count[0] = second_vec[i];

			second_song_id[0] = i;
			
		}
		else if (second_vec[i] > second_song_count[1]) {
			second_song_count[1] = second_vec[i];
			second_song_id[1] = i;
		}
		else if (second_vec[i] > second_song_count[2]) {
			second_song_count[2] = second_vec[i];
			second_song_id[2] = i;
			
		}
	}
	System.out.println("Recommendations");
	
	System.out.println("(1) "+table.GetSong(first_song_id[0])+" was played "+first_song_count[0]+" times by "+first.user);
	System.out.println("(2) "+table.GetSong(first_song_id[1])+" was played "+first_song_count[1]+" times by "+first.user);
	System.out.println("(3) "+table.GetSong(first_song_id[2])+" was played "+first_song_count[2]+" times by "+first.user);

	System.out.println("(4) "+table.GetSong(second_song_id[0])+" was played "+second_song_count[0]+" times by "+second.user);
	System.out.println("(5) "+table.GetSong(second_song_id[1])+" was played "+second_song_count[1]+" times by "+second.user);
	System.out.println("(6) "+table.GetSong(second_song_id[2])+" was played "+second_song_count[2]+" times by "+second.user);
	
	
	
	
	//Now apply logic 
	
}

    private static boolean ClusterNameInList(String thisClusterName,
			List<Integer> clusterNumList) {
		// TODO Auto-generated method stub
    	Iterator<Integer> iterator = clusterNumList.iterator();
    	while (iterator.hasNext()) {
    		if (thisClusterName.equals("cluster"+iterator.next()))
    			return true;
    	}
		return false;
	}

	private static Instances getData1() { 
	    BufferedReader reader; 
	    Instances data =null ; 
	   
	    try 
	    { 
	        reader = new BufferedReader(new FileReader("E:/filename.arff")); 
	        data =  new Instances(reader); 
	        reader.close(); 
	    }  
	    catch (FileNotFoundException e)  
	    { 
	    System.out.println("Exception in Getting data from records.arff in getData() \n" + e.toString() );   
	    }  
	    catch (IOException e)  
	    { 
	        System.out.println("Exception in Getting data from records.arff in getData() \n" + e.toString() ); 
	    } 
	      
	    return data; 
    } 
   
      
  
  
    private static SimpleKMeans getKmeansClusterer(int Nclusters, Instances rmData)  
    { 
        String[] kmeansOptions = getKmeansOptions(); 
        SimpleKMeans kmeans = new SimpleKMeans(); 
        try { 
            kmeans.setNumClusters(Nclusters);           //set the number of clusters 
            //kmeans.setPreserveInstancesOrder(true);    // set preserver instances order  
            //kmeans.setMaxIterations(500); 
            kmeans.setOptions(kmeansOptions); 
            kmeans.buildClusterer(rmData);              // build the clusterer   
        }  
        catch (Exception e) { 
            System.out.println("Exception in getKmeansClusterer() :" + e.toString()); 
        }        
          
        return kmeans; 
    } 
  
    private static Instances ApplyAddClusterFilter(Instances rmData)  
    {    
        String[] addcluster_options =  getAddClusterOptions(); 
        AddCluster addcluster = new AddCluster(); 
        Instances acData=null; 
        try { 
            addcluster.setOptions(addcluster_options); 
            addcluster.setInputFormat(rmData);   
            acData =  Filter.useFilter(rmData, addcluster); 
        }  
        catch (Exception e) { 
            System.out.println("Exception in ApplyAddClusterFilter() :" + e.toString()); 
        } 
          
        return acData; 
    } 
  
    private static Instances ApplyRemoveFilter(Instances data) { 
        String[] remove_options = getRemoveOptions(); 
        Remove remove = new Remove();                // new instance of filter 
        Instances rmData =null; 
        try 
        { 
            remove.setOptions(remove_options); 
            remove.setInputFormat(data);              // inform filter about dataset **AFTER** setting options 
            rmData = Filter.useFilter(data, remove);  // apply filter    
        }  
        catch (Exception e)  
        { 
            System.out.println("Exception in ApplyRemoveFilter() :" + e.toString()); 
        }                    
          
        return rmData; 
    } 
  
    private static String[] getRemoveOptions() { 
        String[] remove_options = new String[2]; 
        remove_options[0] = "-R";                             // "range" 
        remove_options[1] = "2,4";                            // date, track - attribute 
          
        return remove_options; 
    } 
  
    private static Instances getData() { 
        BufferedReader reader; 
        Instances data =null ; 
        try 
        { 
            reader = new BufferedReader(new FileReader("E:/records.arff")); 
            data =  new Instances(reader); 
            reader.close(); 
        }  
        catch (FileNotFoundException e)  
        { 
        System.out.println("Exception in Getting data from records.arff in getData() \n" + e.toString() );   
        }  
        catch (IOException e)  
        { 
            System.out.println("Exception in Getting data from records.arff in getData() \n" + e.toString() ); 
        } 
          
        return data; 
    } 
  
    private static int getNumOfClusters() { 
        return 50; 
    } 
  
    private static String[] getAddClusterOptions() { 
        String[] addcluster_options = new String[2]; 
        addcluster_options[0] = "-W";  
        addcluster_options[1] = "weka.clusterers.SimpleKMeans -N 50 -S 10"; 
        return addcluster_options; 
    } 
  
    private static String getCurrentUser() { 
    	//System.out.println("User name in Music reco system is :" + MyListener.getUname());
        return "user_000009"; 
    } 
  
    private static String[] getKmeansOptions() { 
        String[] options = new String[6]; 
        options[0] = "-N"; // max number of iterations 
        options[1] = "50"; 
        options[2] = "-I";  // set initial seeds 
        options[3] = "500"; 
        options[4] = "-S"; 
        options[5] = "10"; 
          
        return options; 
    } 
  
    private static boolean write_to_file(Instances acData) { 
  
        try
        { 
        	File file = new File("E:/filename.arff"); 
       
        if (!file.exists()) { 
            file.createNewFile(); 
        } 
  
        FileWriter fw = new FileWriter(file.getAbsoluteFile()); 
        BufferedWriter bw = new BufferedWriter(fw); 
        bw.write(acData.toString()); 
        bw.close(); 
          
        return true; 
        } 
        catch(Exception ex) 
        { 
            System.out.println("Problem in write_to_file() : " + ex.toString());     
        } 
          
        return false; 
    } 
} 





/**import java.io.*; 
import java.util.ArrayList; 
import java.util.Iterator;
import java.util.List; 

import UI.MyListener;
import UI.MyListener.*;
import weka.core.*; 
import weka.clusterers.*; 
import weka.filters.*; 
import weka.filters.unsupervised.attribute.AddCluster; 
import weka.filters.unsupervised.attribute.Remove; 
  
public class MusicRecomm { 
  
    public static void main(String[] args) throws FileNotFoundException {
    
    String logged_user =  getCurrentUser();  
    List<Integer> clusterNumsList = new ArrayList<Integer>();   
    int Nclusters =  getNumOfClusters();  
    Instances acData = null;
         
    try { 
        Instances data =  getData(); 
        String[][] instance_splits =  new String[Nclusters][3];      
        Instances rmData = ApplyRemoveFilter(data); 
        System.out.println("rmData is : " + rmData);
        acData =  ApplyAddClusterFilter(rmData); //Filter.useFilter(rmData, addcluster); 
        System.out.println("@acData is : " + acData);
        //setting class attribute 
        //newData.setClassIndex(0);                             // classify based on user 
                  
        SimpleKMeans kmeans = getKmeansClusterer(Nclusters, rmData); //new SimpleKMeans(); 
          
        //System.out.println("FUNCTION USED :" + kmeans.distanceFunctionTipText()); 
        Instances centroids = kmeans.getClusterCentroids(); 
                  
        ClusterEvaluation eval = new ClusterEvaluation(); 
        eval.setClusterer(kmeans); 
        eval.evaluateClusterer(rmData); 
          
        System.out.println(eval.clusterResultsToString()); 
        System.out.println("\n\nCluster centroids are : " ); 
          
        for(int i=0;i<Nclusters;i++) 
        { 
            instance_splits[i] =  centroids.instance(i).toString().split(","); 
            System.out.println("User:"+instance_splits[i][0]+"is centroid of cluster:" + (i+1) ); 
            if(logged_user.compareTo(instance_splits[i][0]) ==0) 
            { 
                clusterNumsList.add(i+1); 
            } 
        } 
          
       System.out.print("calling write_to_file\n");
          
        boolean success = write_to_file(acData); 
          
        if(success==true) 
        { 
              
        } 
          
          
    } 
      
    catch (IOException e) { 
        // TODO Auto-generated catch block 
        e.printStackTrace(); 
    } catch (Exception e) { 
        // TODO Auto-generated catch block 
        e.printStackTrace(); 
    } 
	//Instances data =  getData1();
    Instances data =  acData;
	UserSongTable table = new UserSongTable();
	CosineSimilarity similar= new CosineSimilarity();
	//Fill tblr
	//System.out.println("Loading User-Song table");
	int instanceNo= data.numInstances();
	//String[] userNames =  new String[instanceNo];
	//String[] artistNames =  new String[instanceNo];
	
	for (int i = 0; i < data.numInstances(); i++) {
		String thisClusterName = data.instance(i).stringValue(3);
		if (ClusterNameInList(thisClusterName, clusterNumsList) ) {
			//userNames[i]= data.instance(i).stringValue(0);
			//artistNames[i]= data.instance(i).stringValue(1);
			String userName = data.instance(i).stringValue(0);
			String songName = data.instance(i).stringValue(1);
			table.IncUserSongCount(userName,songName);
			System.out.println(thisClusterName+ " adding user/song "+userName+"/"+songName);
		}
	}
	System.out.println("User-Song table loaded, now constructing vectors...");
	
	int totalusers = table.GetTotalUsers();
	Similarity [] similarities = new Similarity[totalusers-1];
	int simi_index = 0;
	Similarity first = new Similarity() ;
	Similarity second = new Similarity();
	
	int [] vec1 = table.GetUserVector( getCurrentUser());
	
	for(int i=0; i<totalusers; i++) {
		String nextuser = table.GetUser(i);
		
		if (!nextuser.equals(getCurrentUser())) {
			int [] vec2 = table.GetUserVector(nextuser);
		
			similarities[simi_index] = new Similarity();
			similarities[simi_index].user = nextuser;
			similarities[simi_index].value = similar.calculateSimilarity(vec1, vec2);
							
			simi_index++;
		}
		
	}
	
	for(int i=0; i<2; i++) {
		for(int j=0; j<(totalusers-2)-i; j++) {
			if (similarities[j].value > similarities[j+1].value) {
				Similarity temp = similarities[j];
				similarities[j] = similarities[j+1];
				similarities[j+1] = temp;
			}
		}
	}
	
	first = similarities[totalusers-2];
	second = similarities[totalusers-3];
	
	int [] first_vec = table.GetUserVector( first.user);
	int [] second_vec = table.GetUserVector( second.user);
	
	int [] first_song_count = {0,0,0};
	int [] first_song_id = new int[3];
	int [] second_song_count = {0,0,0};
	int [] second_song_id = new int[3];
	
	int totalsongs = table.GetTotalSongs();
	
	for(int i=0; i<totalsongs; i++) {
		
		if (first_vec[i] > first_song_count[0]) {
			first_song_count[0] = first_vec[i];

			first_song_id[0] = i;
		
		}
		else if (first_vec[i] > first_song_count[1]) {
			first_song_count[1] = first_vec[i];
			first_song_id[1] = i;
			
		}
		else if (first_vec[i] > first_song_count[2]) {
			first_song_count[2] = first_vec[i];
			first_song_id[2] = i;
			
		}
		
	}
	
	
	for(int i=0; i<totalsongs; i++) {

		if (second_vec[i] > second_song_count[0]) {
			
			second_song_count[0] = second_vec[i];

			second_song_id[0] = i;
			
		}
		else if (second_vec[i] > second_song_count[1]) {
			second_song_count[1] = second_vec[i];
			second_song_id[1] = i;
		}
		else if (second_vec[i] > second_song_count[2]) {
			second_song_count[2] = second_vec[i];
			second_song_id[2] = i;
			
		}
	}
	System.out.println("Recommendations");
	
	System.out.println("(1) "+table.GetSong(first_song_id[0])+" was played "+first_song_count[0]+" times by "+first.user);
	System.out.println("(2) "+table.GetSong(first_song_id[1])+" was played "+first_song_count[1]+" times by "+first.user);
	System.out.println("(3) "+table.GetSong(first_song_id[2])+" was played "+first_song_count[2]+" times by "+first.user);

	System.out.println("(4) "+table.GetSong(second_song_id[0])+" was played "+second_song_count[0]+" times by "+second.user);
	System.out.println("(5) "+table.GetSong(second_song_id[1])+" was played "+second_song_count[1]+" times by "+second.user);
	System.out.println("(6) "+table.GetSong(second_song_id[2])+" was played "+second_song_count[2]+" times by "+second.user);
		
	
	
	//Now apply logic 
	
}

    private static boolean ClusterNameInList(String thisClusterName,
			List<Integer> clusterNumList) {
		// TODO Auto-generated method stub
    	Iterator<Integer> iterator = clusterNumList.iterator();
    	while (iterator.hasNext()) {
    		if (thisClusterName.equals("cluster"+iterator.next()))
    			return true;
    	}
		return false;
	}

	private static Instances getData1() { 
	    BufferedReader reader; 
	    Instances data =null ; 
	   System.out.println("In getData1()");
	    try 
	    { 
	        reader = new BufferedReader(new FileReader("E:/filename.arff")); 
	        data =  new Instances(reader); 
	        reader.close(); 
	    }  
	    catch (FileNotFoundException e)  
	    { 
	    System.out.println("Exception in Getting data from records.arff in getData() \n" + e.toString() );   
	    }  
	    catch (IOException e)  
	    { 
	        System.out.println("Exception in Getting data from records.arff in getData() \n" + e.toString() ); 
	    } 
	      
	    return data; 
    } 
   
      
  
  
    private static SimpleKMeans getKmeansClusterer(int Nclusters, Instances rmData)  
    { 
        String[] kmeansOptions = getKmeansOptions(); 
        SimpleKMeans kmeans = new SimpleKMeans(); 
        try { 
            kmeans.setNumClusters(Nclusters);           //set the number of clusters 
            //kmeans.setPreserveInstancesOrder(true);    // set preserver instances order  
            
            //kmeans.setMaxIterations(500); 
            kmeans.setOptions(kmeansOptions); 
            kmeans.buildClusterer(rmData);              // build the clusterer   
        }  
        catch (Exception e) { 
            System.out.println("Exception in getKmeansClusterer() :" + e.toString()); 
        }        
          
        return kmeans; 
    } 
  
    private static Instances ApplyAddClusterFilter(Instances rmData)  
    {    
        String[] addcluster_options =  getAddClusterOptions(); 
        AddCluster addcluster = new AddCluster(); 
        Instances acData=null; 
        try { 
            addcluster.setOptions(addcluster_options); 
            addcluster.setInputFormat(rmData);   
            acData =  Filter.useFilter(rmData, addcluster); 
        }  
        catch (Exception e) { 
            System.out.println("Exception in ApplyAddClusterFilter() :" + e.toString()); 
        } 
          
        return acData; 
    } 
  
    private static Instances ApplyRemoveFilter(Instances data) { 
        String[] remove_options = getRemoveOptions(); 
        Remove remove = new Remove();                // new instance of filter 
        Instances rmData =null; 
        try 
        { 
            remove.setOptions(remove_options); 
            remove.setInputFormat(data);              // inform filter about dataset **AFTER** setting options 
            rmData = Filter.useFilter(data, remove);  // apply filter    
        }  
        catch (Exception e)  
        { 
            System.out.println("Exception in ApplyRemoveFilter() :" + e.toString()); 
        }                    
          
        return rmData; 
    } 
  
    private static String[] getRemoveOptions() { 
        String[] remove_options = new String[2]; 
        remove_options[0] = "-R";                             // "range" 
        remove_options[1] = "2,4";                            // date, track - attribute 
          
        return remove_options; 
    } 
  
    private static Instances getData() { 
        BufferedReader reader; 
        Instances data =null ; 
        try 
        { 
            reader = new BufferedReader(new FileReader("E:/records.arff")); 
            data =  new Instances(reader); 
            reader.close(); 
        }  
        catch (FileNotFoundException e)  
        { 
        System.out.println("Exception in Getting data from records.arff in getData() \n" + e.toString() );   
        }  
        catch (IOException e)  
        { 
            System.out.println("Exception in Getting data from records.arff in getData() \n" + e.toString() ); 
        } 
          
        return data; 
    } 
  
    private static int getNumOfClusters() { 
        return 50; 
    } 
  
    private static String[] getAddClusterOptions() { 
        String[] addcluster_options = new String[2]; 
        addcluster_options[0] = "-W";  
        addcluster_options[1] = "weka.clusterers.SimpleKMeans -N 50 -S 10"; 
        return addcluster_options; 
    } 
  
    private static String getCurrentUser() { 
    	
    	//System.out.println("uname is :" + MyListener.getUname().toString());
        return "user_000002"; 
    } 
  
    private static String[] getKmeansOptions() { 
        String[] options = new String[6]; 
        options[0] = "-N"; // max number of iterations 
        options[1] = "50"; 
        options[2] = "-I";  // set initial seeds 
        options[3] = "500"; 
        options[4] = "-S"; 
        options[5] = "10"; 
          
        return options; 
    } 
  
    private static boolean write_to_file(Instances acData) { 
  
        try
        { 
        File file = new File("E:/filename.arff"); 
       
        if (!file.exists()) { 
            file.createNewFile(); 
        } 
  
        FileWriter fw = new FileWriter(file.getAbsoluteFile()); 
        BufferedWriter bw = new BufferedWriter(fw); 
        bw.write(acData.toString()); 
        bw.close(); 
          
        return true; 
        } 
        catch(Exception ex) 
        { 
            System.out.println("Problem in write_to_file() : " + ex.toString());     
        } 
          
        return false; 
    } 
} 
***/