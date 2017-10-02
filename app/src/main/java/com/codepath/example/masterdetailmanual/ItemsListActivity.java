package com.codepath.example.masterdetailmanual;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.FrameLayout;

import org.json.JSONException;

public class ItemsListActivity extends FragmentActivity implements ItemsListFragment.OnItemSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		APICaller helper = new APICaller();
		try {
			helper.getData();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items);
		determinePaneLayout();
	}

	private void determinePaneLayout() {
		FrameLayout fragmentItemDetail = (FrameLayout) findViewById(R.id.flDetailContainer);
		if (fragmentItemDetail != null) {
			ItemsListFragment fragmentItemsList =
					(ItemsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentItemsList);
			fragmentItemsList.setActivateOnItemClick(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.items, menu);
		return true;
	}

	@Override
	public void onItemSelected(Item item) {
		// launch detail activity using intent
		Intent i = new Intent(this, ItemDetailActivity.class);
		i.putExtra("item", item);
		startActivity(i);
	}
}

