package mhyhre.lightrabbit;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import android.content.res.AssetManager;
import android.util.Log;

public class Dictionary {

	private ArrayList<String> array;
	Random random;

	public Dictionary(String filename) throws IOException {

		String word;
		array = new ArrayList<String>();

		try {
			AssetManager assetManager = MainActivity.Me.getAssetManager();
			InputStreamReader istream = new InputStreamReader(assetManager.open(filename));

			BufferedReader in = new BufferedReader(istream);

			while ((word = in.readLine()) != null) {
				array.add(word);
			}
			in.close();	
			
		} catch (FileNotFoundException e) {
			Log.i(MainActivity.DebugID, "Dictionary::FileNotFoundException: " + e.getMessage());
		} catch (IOException e) {
			Log.i(MainActivity.DebugID, "Dictionary::load:exception: " +e.getMessage());
		}

		random = new Random();

		Log.i(MainActivity.DebugID, "Dictionary::loaded complete (" + array.size()+ ") lines");
	}

	public int size() {
		return array.size();
	}

	public String getWord(int i) {
		if (i >= 0 && i < array.size()) {
			return array.get(i);
		}
		return null;
	}

	public String getRandomWord() {
		return array.get(random.nextInt(array.size()));
	}
}
