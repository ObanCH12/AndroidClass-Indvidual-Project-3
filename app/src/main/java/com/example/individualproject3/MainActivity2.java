package com.example.individualproject3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String MYPREF = "My_PREF_FILE_NAME";
    public static final String EMAIL = "EmailKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        sharedpreferences = getSharedPreferences(MYPREF,
                Context.MODE_PRIVATE);
        String email = sharedpreferences.getString(EMAIL, "");

        List<ParentWithChild> children = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                .dataOrgDAO()
                .getParentWithChildren(email);
        List<String> names = new ArrayList<String>();
        List<Integer> scores=new ArrayList<Integer>();
        for(ParentWithChild ssc : children) {
            for (int i = 0; i < children.size();i++) {
               scores.add(ssc.children.get(i).getLevelsCompleted());
                names.add(ssc.children.get(i).getChildName());
            }

        }


        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0.0);
        graph.getViewport().setMaxY(6.0);
        DataPoint[] data = new DataPoint[scores.size()];
        for (int i = 0; i < scores.size(); i++){
            data[i] = new DataPoint(i, scores.get(i));

        }
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(data);
        graph.addSeries(series);

// styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        series.setSpacing(50);

// draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
//series.setValuesOnTopSize(50);
    }
}

