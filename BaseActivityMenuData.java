package uk.ac.aston.pyzerg.travelBudget;

import java.util.ArrayList;

import android.util.Pair;
import android.view.View.OnClickListener;

// (c) Jonathan Pike
// Replaces the ActionBarSherlock overflow menu

public class BaseActivityMenuData {
	
	protected ArrayList<Pair<String, OnClickListener>> items;
	
	// this class will add defaults such as "export", "preferences"
	// and expose methods for adding additional items in the right place
	
	public BaseActivityMenuData() {
		this.items = new ArrayList<Pair<String, OnClickListener>>();
	}
	
	public ArrayList<Pair<String, OnClickListener>> getItems() {
		return this.items;
	}
	
	public void addItem(String title, OnClickListener action) {
		this.items.add(new Pair<String, OnClickListener>(title, action));
	}

}
