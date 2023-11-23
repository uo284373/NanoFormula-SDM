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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;


public class TemporadaPilotoFragment extends Fragment {


    private static final String DRIVER_STANDING = "driver_standing";
    private static final String DRIVER_TEAM = "driver_team";
    private static final String DRIVER_POINTS = "driver_points";
    private static final String RACES_START_POSITION = "driver_start_position";
    private static final String RACES_FINAL_POSITION = "driver_final_position";
    private static final String TEMP_PILOTO = "driver_temp";

    private DriverStanding driverStanding;
    private ArrayList<Constructor> constructors;
    private List<String> puntostemp = new ArrayList<>();
    private List<String> raceStartPosition = new ArrayList<>();
    private List<String> raceFinalPosition = new ArrayList<>();
    private String tempPiloto;

    TextView posPiloto;
    TextView escuderiaPiloto;
    TextView victoriasPiloto;
    TextView puntosPiloto;
    TextView temporadaPiloto;
    LineChart lineChartPuntosPiloto;
    LineChart lineChartPosStartFinal;

    public TemporadaPilotoFragment() {
        // Required empty public constructor
    }


    public static TemporadaPilotoFragment newInstance(DriverStanding param1, ArrayList<Constructor> param2,ArrayList<String> param3,ArrayList<String> param4,ArrayList<String> param5,String param6) {
        TemporadaPilotoFragment fragment = new TemporadaPilotoFragment();
        Bundle args = new Bundle();
        args.putParcelable(DRIVER_STANDING, param1);
        args.putParcelableArrayList(DRIVER_TEAM, param2);
        args.putStringArrayList(DRIVER_POINTS,param3);
        args.putStringArrayList(RACES_START_POSITION,param4);
        args.putStringArrayList(RACES_FINAL_POSITION,param5);
        args.putString(TEMP_PILOTO,param6);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            driverStanding = getArguments().getParcelable(DRIVER_STANDING);
            constructors = getArguments().getParcelableArrayList(DRIVER_TEAM);
            puntostemp = getArguments().getStringArrayList(DRIVER_POINTS);
            raceStartPosition = getArguments().getStringArrayList(RACES_START_POSITION);
            raceFinalPosition = getArguments().getStringArrayList(RACES_FINAL_POSITION);
            tempPiloto = getArguments().getString(TEMP_PILOTO);
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
        temporadaPiloto = root.findViewById(R.id.txTemporadaActual);
        lineChartPuntosPiloto = root.findViewById(R.id.lineChartPuntosPiloto);
        lineChartPosStartFinal = root.findViewById(R.id.lineChartPosPilotoSalidaFin);
        createChartPoints();
        createChartPosStartFinal();

        posPiloto.setText(driverStanding.getPosition());
        escuderiaPiloto.setText(constructors.get(0).getName());
        victoriasPiloto.setText(driverStanding.getWins());
        puntosPiloto.setText(driverStanding.getPoints());
        temporadaPiloto.setText("TEMPORADA "+ tempPiloto);
        return root;
    }

    private void createChartPoints(){
        lineChartPuntosPiloto.setBackgroundColor(Color.TRANSPARENT);
        lineChartPuntosPiloto.animateXY(3000,3000);
        lineChartPuntosPiloto.getAxisRight().setEnabled(false);
        lineChartPuntosPiloto.getAxisLeft().setAxisMinimum(0);
        lineChartPuntosPiloto.getAxisLeft().setTextColor(Color.WHITE);

        lineChartPuntosPiloto.getAxisLeft().setAxisMaximum(Integer.parseInt(puntostemp.get(puntostemp.size()-1).split(";")[1])+20);
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
        lineChartPuntosPiloto.getDescription().setEnabled(false);

        ArrayList<Entry> lineData = new ArrayList<>();
        for(int i = 0;i < puntostemp.size();i++){
            String[] roundPuntos =puntostemp.get(i).split(";");
            lineData.add(new Entry(Integer.parseInt(roundPuntos[0]),Integer.parseInt(roundPuntos[1])));
        }
        LineData dataSet = new LineData(new LineDataSet(lineData,"Puntos"));
        dataSet.setValueTextColor(Color.WHITE);
        lineChartPuntosPiloto.setData(dataSet);
    }

    private void createChartPosStartFinal(){
        lineChartPosStartFinal.setBackgroundColor(Color.TRANSPARENT);
        lineChartPosStartFinal.animateXY(3000,3000);
        lineChartPosStartFinal.getAxisRight().setEnabled(false);
        lineChartPosStartFinal.getAxisLeft().setAxisMinimum(0);
        lineChartPosStartFinal.getAxisLeft().setTextColor(Color.WHITE);

        lineChartPosStartFinal.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChartPosStartFinal.getXAxis().setTextColor(Color.WHITE);
        lineChartPosStartFinal.getXAxis().setLabelCount(raceFinalPosition.size(), true);
        lineChartPosStartFinal.getXAxis().setDrawGridLines(false);
        lineChartPosStartFinal.getAxisLeft().setDrawGridLines(false);
        lineChartPosStartFinal.getAxisLeft().setLabelCount(10,true);
        lineChartPosStartFinal.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        lineChartPosStartFinal.getLegend().setTextColor(Color.WHITE);
        lineChartPosStartFinal.setDrawGridBackground(false);
        lineChartPosStartFinal.setDrawBorders(false);
        lineChartPosStartFinal.setTouchEnabled(false);
        lineChartPosStartFinal.setDragEnabled(false);
        lineChartPosStartFinal.setScaleEnabled(false);
        lineChartPosStartFinal.setPinchZoom(false);
        lineChartPosStartFinal.getDescription().setEnabled(false);

        ArrayList<Entry> lineDataStart = new ArrayList<>();
        for(int i = 0;i < raceStartPosition.size();i++){
            String[] roundStart =raceStartPosition.get(i).split(";");
            lineDataStart.add(new Entry(Integer.parseInt(roundStart[0]),Integer.parseInt(roundStart[1])));
        }
        LineDataSet lineDataSetStartPosition = new LineDataSet(lineDataStart, "Posición de salida");
        lineDataSetStartPosition.setColor(Color.GREEN);
        lineDataSetStartPosition.setDrawValues(false);


        ArrayList<Entry> lineDataFinal = new ArrayList<>();
        for(int i = 0;i < raceFinalPosition.size();i++){
            String[] roundFinal =raceFinalPosition.get(i).split(";");
            lineDataFinal.add(new Entry(Integer.parseInt(roundFinal[0]),Integer.parseInt(roundFinal[1])));
        }
        LineDataSet lineDataSetFinalPosition = new LineDataSet(lineDataFinal, "Posición final");
        lineDataSetFinalPosition.setColor(Color.YELLOW);
        lineDataSetFinalPosition.setDrawValues(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSetStartPosition);
        dataSets.add(lineDataSetFinalPosition);
        LineData lineData1 = new LineData(dataSets);
        lineData1.setValueTextColor(Color.WHITE);
        lineChartPosStartFinal.setData(lineData1);
    }
}