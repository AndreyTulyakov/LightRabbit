package mhyhre.lightrabbit.Scenes.Words;

public class WordTriple {
	
	public String word1, word2, word3;

	public WordTriple(String w1, String w2) {
		this.word1 = new String(w1);
		this.word2 = new String(w2);
		this.word3 = null;
	}

	public WordTriple(String w1, String w2, String w3) {
		this.word1 = new String(w1);
		this.word2 = new String(w2);
		this.word3 = new String(w3);
	}

}
