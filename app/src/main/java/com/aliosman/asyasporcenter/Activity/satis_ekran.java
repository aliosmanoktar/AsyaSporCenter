package com.aliosman.asyasporcenter.Activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.aliosman.asyasporcenter.Adapter.satis_tabbed_adapter;
import com.aliosman.asyasporcenter.R;
import com.aliosman.asyasporcenter.Values.IUrunSearch;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class satis_ekran extends AppCompatActivity {
    private SearchView search;
    private IUrunSearch iSearch=null;
    private String TAG = getClass().getName();
    satis_tabbed_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tabbet_satis);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ViewPager viewPager = findViewById(R.id.satis_viewpager);
        adapter=new satis_tabbed_adapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.satis_tabs);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (iSearch==null)
                    iSearch=adapter.search;
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        iSearch=adapter.search;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu_satis,menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        search = (SearchView) item.getActionView();
        search.setQueryHint("Ürün Arama...");
        search.setFocusable(true);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (iSearch==null)
                    iSearch=adapter.search;
                if (iSearch!=null)
                    iSearch.Search(s);
                return false;
            }
        });
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }else if (item.getItemId()==R.id.menu_qr_search){
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            integrator.initiateScan();
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.e(TAG, "onActivityResult: "+"Cancelled");
            } else {
                if(search!=null){
                    search.setQuery(result.getContents().toLowerCase(),true);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}