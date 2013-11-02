package mhyhre.lightrabbit.game.Levels;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import mhyhre.lightrabbit.MainActivity;

@SuppressLint("UseSparseArrays")

public class Dialog {
	
	private String mCaption;
	private boolean mFastMessage;
	
	private Map<Integer, Replic> replics;
	
	
	public Dialog() {
		replics = new HashMap<Integer, Replic>();
	}
	
	public Map<Integer, Replic> getReplicsMap(){
		return replics;
	}
	
	public Replic getReplic(int id){
		
		if (replics.containsKey(id)){
			return replics.get(id);
		}
		
		Log.e(MainActivity.DEBUG_ID, "Dialog::getReplic: invalid id");
		return null;
	}

	public String getCaption() {
		return mCaption;
	}

	public void setCaption(String caption) {
		this.mCaption = caption;
	}

	public boolean isFastMessage() {
		return mFastMessage;
	}

	public void setFastMessage(boolean mFast) {
		this.mFastMessage = mFast;
	}

	public void putReplic(int id, Replic replic)
	{
		replics.put(id, replic);
	}
	

	@Override
	public String toString() {
		
		String result = "DIALOG: \"" + mCaption + "\" FAST:" + mFastMessage + "\n";
		
		for (final Map.Entry<Integer, Replic> entry : replics.entrySet()) {
			result += "\n" + "    Replic ID:"+ entry.getKey() + " " + entry.getValue();
		}
		return result;
	}
}
