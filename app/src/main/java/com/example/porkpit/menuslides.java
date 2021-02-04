package com.example.porkpit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class menuslides extends AppCompatActivity {
   // lanchermanager lanchermanager;
    ViewPager viewPagerm;
    Button btnNextm;
    int[] layouts;
    madapter zadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuslides);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewPagerm = findViewById(R.id.pager278);
        btnNextm = findViewById(R.id.mnnextBtn);


        layouts = new int[] {
                R.layout.mslide1,
                R.layout.mslide2,
                R.layout.mslide3
        };

        zadapter = new madapter(this,layouts);
        viewPagerm.setAdapter(zadapter);


        viewPagerm.addOnPageChangeListener(viewPagerChangeListener);

        btnNextm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewPagerm.getCurrentItem()+1 < layouts.length){
                    viewPagerm.setCurrentItem(viewPagerm.getCurrentItem()+1);
                }else {
                    startActivity(new Intent(getApplicationContext(),menu.class));
                }
            }
        });

    }
    ViewPager.OnPageChangeListener viewPagerChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            if(i == layouts.length - 1){
                btnNextm.setVisibility(View.VISIBLE);
                btnNextm.setText("Next");


            }else {
                btnNextm.setVisibility(View.INVISIBLE);
                //startActivity(new Intent(getApplicationContext(),menu.class));
            }

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}