package com.example.nanoformula;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.nanoformula.modelo.constructorsStandings.ConstructorStanding;
import com.example.nanoformula.modelo.driversStandings.Constructor;
import com.example.nanoformula.modelo.driversStandings.DriverStanding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;


public class TemporadaEscuderiaFragment extends Fragment {
    private static final String CONSTRUCTOR_POINTS = "constructors_points";

    private List<String> puntostemp = new ArrayList<>();

    LineChart lineChartPuntosEscuderia;

    public TemporadaEscuderiaFragment() {
        // Required empty public constructor
    }


    public static TemporadaEscuderiaFragment newInstance(ArrayList<String> param) {
        TemporadaEscuderiaFragment fragment = new TemporadaEscuderiaFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(CONSTRUCTOR_POINTS,param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            puntostemp = getArguments().getStringArrayList(CONSTRUCTOR_POINTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_temporada_escuderia, container, false);
        lineChartPuntosEscuderia = root.findViewById(R.id.lineChartPuntosEscuderias);
        createChart();

        return root;
    }

    private void createChart(){
        lineChartPuntosEscuderia.setBackgroundColor(Color.TRANSPARENT);
        lineChartPuntosEscuderia.animateXY(3000,3000);
        lineChartPuntosEscuderia.getAxisRight().setEnabled(false);
        lineChartPuntosEscuderia.getAxisLeft().setAxisMinimum(0);
        lineChartPuntosEscuderia.getAxisLeft().setTextColor(Color.WHITE);

        lineChartPuntosEscuderia.getAxisLeft().setAxisMaximum(Integer.parseInt(puntostemp.get(puntostemp.size()-1).split(";")[1])+20);
        lineChartPuntosEscuderia.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChartPuntosEscuderia.getXAxis().setTextColor(Color.WHITE);
        lineChartPuntosEscuderia.getXAxis().setLabelCount(puntostemp.size(), true);
        lineChartPuntosEscuderia.getXAxis().setDrawGridLines(false);
        lineChartPuntosEscuderia.getAxisLeft().setDrawGridLines(false);
        lineChartPuntosEscuderia.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        lineChartPuntosEscuderia.getLegend().setTextColor(Color.WHITE);
        lineChartPuntosEscuderia.setDrawGridBackground(false);
        lineChartPuntosEscuderia.setDrawBorders(false);
        lineChartPuntosEscuderia.setTouchEnabled(false);
        lineChartPuntosEscuderia.setDragEnabled(false);
        lineChartPuntosEscuderia.setScaleEnabled(false);
        lineChartPuntosEscuderia.setPinchZoom(false);

        ArrayList<Entry> lineData = new ArrayList<>();
        for(int i = 0;i < puntostemp.size();i++){
            String[] roundPuntos =puntostemp.get(i).split(";");
            lineData.add(new Entry(Integer.parseInt(roundPuntos[0]),Integer.parseInt(roundPuntos[1])));
        }
        LineData dataSet = new LineData(new LineDataSet(lineData,"Puntos"));
        dataSet.setValueTextColor(Color.WHITE);
        lineChartPuntosEscuderia.setData(dataSet);
    }
}