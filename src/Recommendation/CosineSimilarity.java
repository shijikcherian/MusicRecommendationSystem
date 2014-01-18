package Recommendation;

public class CosineSimilarity {
	public static String user;
	public  double calculateSimilarity(int[] vec1, int[] vec2) {
		double similarity = 0;

		assert(vec1.length == vec2.length);
		for (int i = 0; i < vec1.length; i++) {
			
				similarity += vec1[i] * vec2[i];
			
	
		}
		
		similarity = similarity / (vectorLength(vec1) * vectorLength(vec2));
		
		return similarity;
	}
	private static double vectorLength(int[] vec) {
		double len = 0;
		for (int i = 0; i < vec.length; i++) {
			len += vec[i] * vec[i];
		}
		len = Math.sqrt(len);
		return len;			
	}

}
