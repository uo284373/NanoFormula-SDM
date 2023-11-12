package com.example.nanoformula;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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


public class TemporadaPilotoFragment extends Fragment {


    private static final String DRIVER_STANDING = "driver_standing";
    private static final String DRIVER_TEAM = "driver_team";
    private static final String DRIVER_POINTS = "driver_points";

    private DriverStanding driverStanding;
    private ArrayList<Constructor> constructors;
    private List<Integer> puntostemp = new ArrayList<>();

    TextView posPiloto;
    TextView escuderiaPiloto;
    TextView victoriasPiloto;
    TextView puntosPiloto;
    LineChart lineChartPuntosPiloto;

    public TemporadaPilotoFragment() {
        // Required empty public constructor
    }


    public static TemporadaPilotoFragment newInstance(DriverStanding param1, ArrayList<Constructor> param2,ArrayList<Integer> param3) {
        TemporadaPilotoFragment fragment = new TemporadaPilotoFragment();
        Bundle args = new Bundle();
        args.putParcelable(DRIVER_STANDING, param1);
        args.putParcelableArrayList(DRIVER_TEAM, param2);
        args.putIntegerArrayList(DRIVER_POINTS,param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            driverStanding = getArguments().getParcelable(DRIVER_STANDING);
            constructors = getArguments().getParcelableArrayList(DRIVER_TEAM);
            puntostemp = getArguments().getIntegerArrayList(DRIVER_POINTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_temporada_piloto, container, false);
        posPiloto = root.findViewById(R.id.txPosicionPiloto);
        escuderiaPiloto = root.findViewById(R.id.txEscuderiaPilotoTemporada);
        victoriasPiloto = root.findViewById(R.id.txVictoriasPilotoTemporada);
        puntosPiloto = root.findViewById(R.id.txPuntosPilotoTemporada);
        lineChartPuntosPiloto = root.findViewById(R.id.lineChartPuntosPiloto);
        createChart();

        posPiloto.setText(driverStanding.getPosition());
        escuderiaPiloto.setText(constructors.get(0).getName());
        victoriasPiloto.setText(driverStanding.getWins());
        puntosPiloto.setText(driverStanding.getPoints());
        return root;
    }

    private void createChart(){
        lineChartPuntosPiloto.setBackgroundColor(Color.TRANSPARENT);
        lineChartPuntosPiloto.animateXY(3000,3000);
        lineChartPuntosPiloto.getAxisRight().setEnabled(false);
        lineChartPuntosPiloto.getAxisLeft().setAxisMinimum(0);
        lineChartPuntosPiloto.getAxisLeft().setTextColor(Color.WHITE);
        lineChartPuntosPiloto.getAxisLeft().setAxisMaximum(puntostemp.get(puntostemp.size()-1) + 20);
        lineChartPuntosPiloto.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChartPuntosPiloto.getXAxis().setTextColor(Color.WHITE);
        lineChartPuntosPiloto.getXAxis().setLabelCount(puntostemp.size(), true);
        lineChartPuntosPiloto.getXAxis().setDrawGridLines(false);
        lineChartPuntosPiloto.getAxisLeft().setDrawGridLines(false);
        lineChartPuntosPiloto.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        lineChartPuntosPiloto.getLegend().setTextColor(Color.WHITE);
        lineChartPuntosPiloto.setDrawGridBackground(false);
        lineChartPuntosPiloto.setDrawBorders(false);
        lineChartPuntosPiloto.setTouchEnabled(false);
        lineChartPuntosPiloto.setDragEnabled(false);
        lineChartPuntosPiloto.setScaleEnabled(false);
        lineChartPuntosPiloto.setPinchZoom(false);

        ArrayList<Entry> lineData = new ArrayList<>();
        for(int i = 0;i < puntostemp.size();i++){
            lineData.add(new Entry(i,puntostemp.get(i)));
        }
        LineData dataSet = new LineData(new LineDataSet(lineData,"Puntos"));
        dataSet.setValueTextColor(Color.WHITE);
        lineChartPuntosPiloto.setData(dataSet);
    }
}