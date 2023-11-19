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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TemporadaEscuderiaFragment extends Fragment {
    private static final String CONSTRUCTOR_POINTS = "constructors_points";
    private static final String DRIVER_POINTS = "driver_points";

    private List<String> puntostemp = new ArrayList<>();

    private List<String> puntosPilotos = new ArrayList<>();

    LineChart lineChartPuntosEscuderia;

    LineChart lineChartPilotos;

    private final static int[] colors = {Color.MAGENTA, Color.CYAN, Color.GREEN, Color.RED, Color.BLUE, Color.GRAY,  Color.YELLOW};

    public TemporadaEscuderiaFragment() {
        // Required empty public constructor
    }


    public static TemporadaEscuderiaFragment newInstance(ArrayList<String> param, ArrayList<String> puntosPilotos) {
        TemporadaEscuderiaFragment fragment = new TemporadaEscuderiaFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(CONSTRUCTOR_POINTS,param);
        args.putStringArrayList(DRIVER_POINTS, puntosPilotos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            puntostemp = getArguments().getStringArrayList(CONSTRUCTOR_POINTS);
            puntosPilotos = getArguments().getStringArrayList(DRIVER_POINTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_temporada_escuderia, container, false);
        lineChartPuntosEscuderia = root.findViewById(R.id.lineChartPuntosEscuderias);
        lineChartPilotos = root.findViewById(R.id.lineChartComparativaPilotos);
        createChart();
        createChartDrivers();

        return root;
    }

    private void createChart(){
        lineChartPuntosEscuderia.setBackgroundColor(Color.TRANSPARENT);
        lineChartPuntosEscuderia.animateXY(3000,3000);
        lineChartPuntosEscuderia.getAxisRight().setEnabled(false);
        lineChartPuntosEscuderia.getAxisLeft().setAxisMinimum(0);
        lineChartPuntosEscuderia.getAxisLeft().setTextColor(Color.WHITE);

        lineChartPuntosEscuderia.getAxisLeft().setAxisMaximum(Integer.parseInt(puntostemp.get(puntostemp.size()-1).split(";")[1]) * 1.2F);
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
        lineChartPuntosEscuderia.getDescription().setEnabled(false);

        ArrayList<Entry> lineData = new ArrayList<>();
        for(int i = 0;i < puntostemp.size();i++){
            String[] roundPuntos =puntostemp.get(i).split(";");
            lineData.add(new Entry(Integer.parseInt(roundPuntos[0]),Integer.parseInt(roundPuntos[1])));
        }
        LineData dataSet = new LineData(new LineDataSet(lineData,"Puntos"));
        dataSet.setValueTextColor(Color.WHITE);
        lineChartPuntosEscuderia.setData(dataSet);
    }


    private void createChartDrivers(){
        lineChartPilotos.setBackgroundColor(Color.TRANSPARENT);
        lineChartPilotos.animateXY(3000,3000);
        lineChartPilotos.getAxisRight().setEnabled(false);
        lineChartPilotos.getAxisLeft().setAxisMinimum(0);
        lineChartPilotos.getAxisLeft().setTextColor(Color.WHITE);

        lineChartPilotos.getAxisLeft().setAxisMaximum(Integer.parseInt(puntostemp.get(puntostemp.size()-1).split(";")[1]) * 1.2F);
        lineChartPilotos.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChartPilotos.getXAxis().setTextColor(Color.WHITE);
        lineChartPilotos.getXAxis().setLabelCount(puntostemp.size(), true);
        lineChartPilotos.getXAxis().setDrawGridLines(false);
        lineChartPilotos.getAxisLeft().setDrawGridLines(false);
        lineChartPilotos.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        lineChartPilotos.getLegend().setTextColor(Color.WHITE);
        lineChartPilotos.setDrawGridBackground(false);
        lineChartPilotos.setDrawBorders(false);
        lineChartPilotos.setTouchEnabled(false);
        lineChartPilotos.setDragEnabled(false);
        lineChartPilotos.setScaleEnabled(false);
        lineChartPilotos.setPinchZoom(false);
        lineChartPilotos.getDescription().setEnabled(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        int color = 0;
        for(String list : puntosPilotos){
            ArrayList<Entry> lineDataDriver = new ArrayList<>();
            String[] splitList = list.split("-");
            String driver = splitList[0];
            String[] pointsByRace = splitList[1].split(";");

            for(int i = 0;i < pointsByRace.length;i++) {
                String[] roundPoints = pointsByRace[i].split(",");

                if (i == 0) {
                    lineDataDriver.add(new Entry(Integer.parseInt(roundPoints[0]), Integer.parseInt(roundPoints[1])));
                } else {
                    lineDataDriver.add(new Entry(Integer.parseInt(roundPoints[0]), lineDataDriver.get(lineDataDriver.size() - 1).getY() + Integer.parseInt(roundPoints[1])));

                }
            }
            LineDataSet lineDataSetDriver = new LineDataSet(lineDataDriver, driver);
            lineDataSetDriver.setColor(colors[color++]);
            lineDataSetDriver.setDrawValues(false);

            dataSets.add(lineDataSetDriver);
        }

        LineData lineData = new LineData(dataSets);
        lineData.setValueTextColor(Color.WHITE);
        lineChartPilotos.setData(lineData);
    }
}