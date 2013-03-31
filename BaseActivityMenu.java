package uk.ac.aston.pyzerg.travelBudget;

import java.util.ArrayList;

import android.graphics.Color;
import android.util.Pair;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;

// (c) Jonathan Pike
// Replaces the ActionBarSherlock overflow menu

public abstract class BaseActivityMenu {
	
	private static BaseActivity cContext;
	private static BaseActivityMenuData cData;
	private static View cView;
	
	public static void construct(BaseActivity context, BaseActivityMenuData data) {

		cContext = context;
		cData = data;
		
	}
	
	public static void clearLastView() {
		
		cView = null;
		
	}
	
	public static void hide() {
		
		if (cView == null) return;
		cView.setVisibility(View.GONE);
		cView = null;
		
	}
	
	public static void show() {
		
		if (cView != null) {
			cView.setVisibility(View.GONE);
			cView = null;
			return;
		}
		
		final View rootView = cContext.findViewById(android.R.id.content);
		LayoutParams backLayout = new LayoutParams(
			rootView.getWidth(), rootView.getHeight());
		
		RelativeLayout back = new RelativeLayout(cContext);
		
		cView = back;
		
		LinearLayout list = new LinearLayout(cContext);
		list.setOrientation(LinearLayout.VERTICAL);
		LayoutParams listLayout = new LayoutParams(
			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		listLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		back.addView(list, listLayout);
		
		ArrayList<Pair<String, OnClickListener>> 
			items = cData.getItems();
		
		final int[] colors = new int[items.size()];
		final TextView[] tvs = new TextView[items.size()];
		
		for (int i = 0; i < items.size(); i++) {
			
			Pair<String, OnClickListener> item = items.get(i);
			
			TextView tv = new TextView(cContext);
			tvs[i] = tv;
			
			tv.setText(item.first);
			tv.setOnClickListener(new ClickHandler(item.second));
			tv.setPadding(40, 20, 40, 20);
			tv.setBackgroundColor(Color.BLACK);			
			tv.setTextColor(Color.WHITE);
			colors[i] = Color.BLACK;
			
			if (i % 2 != 0) {
				tv.setBackgroundColor(Color.rgb(15, 15, 15));
				colors[i] = Color.rgb(15, 15, 15);
			}
			
			tv.setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					for (int i = 0; i < tvs.length; i++) 
						tvs[i].setBackgroundColor(colors[i]);
					v.setBackgroundColor(Color.rgb(40, 120, 144));
					return false;
				}
			});
			
			list.addView(tv);
			
		}
		
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				cView.setVisibility(View.GONE);
				cView = null;
			}			
		});
		
		cContext.getWindow().addContentView(back, backLayout);		
		
	}
	
	static class ClickHandler implements OnClickListener {
		
		private OnClickListener ocl;
		public ClickHandler(OnClickListener ocl) {
			this.ocl = ocl;
		}
		
		public void onClick(View v) {
			cView.setVisibility(View.GONE);
			cView = null;
			this.ocl.onClick(v);
		}
		
	}
	
	public static OnMenuItemClickListener sherlockClickContainer(final OnMenuItemClickListener child) {
		return new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				BaseActivityMenu.hide();
				return child.onMenuItemClick(item);
			}			
		};
	}
	
}

