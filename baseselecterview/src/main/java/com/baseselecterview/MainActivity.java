package com.baseselecterview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baseselecterview.view.BaseSelectorView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> titles;
    private ArrayList<String> total1;
    private ArrayList<String> total2;
    private ArrayList<String> total3;
    private ArrayList<String> total4;
    private ArrayList<List<String>> total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewGroup vg = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        BaseSelectorView header = new BaseSelectorView(this);
        header.setRootView(vg);
        titles = new ArrayList<>();
        titles.add("津贵");
        titles.add("齐鲁");
        titles.add("广贵");
        titles.add("一带一路");

        total1 = new ArrayList<>();
        total1.add("全部");
        total1.add("银");
        total1.add("铂");
        total1.add("钯");
        total1.add("铜");
        total1.add("铝");
        total1.add("镍");

        total2 = new ArrayList<>();
        total2.add("现货铜");
        total2.add("现货铝");
        total2.add("现货铝");

        total3 = new ArrayList<>();
        total3.add("现货铜");
        total3.add("现货铝");
        total3.add("现货铝");
        total3.add("钯");
        total3.add("铜");
        total3.add("铝");
        total3.add("镍");

        total4 = new ArrayList<>();
        total4.add("银");
        total4.add("银");
        total4.add("银");
        total4.add("银");
        total4.add("银");
        total4.add("银");
        total = new ArrayList<>();
        total.add(total1);
        total.add(total2);
        total.add(total3);
        total.add(total4);
        header.setData(titles, total);

        header.setExchangeAndVariety(new BaseSelectorView.ExchangeAndVariety() {
            @Override
            public void getExchangeAndVariety(int exchange, int variety) {
                Toast.makeText(MainActivity.this, titles.get(exchange) + "**" + total.get(exchange).get(variety), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
