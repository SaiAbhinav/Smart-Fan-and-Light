package com.example.saiab.sfl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class TemperatureGraphActivity extends Fragment {

    public class xvalues implements IAxisValueFormatter {

        private String[] x;

        public xvalues(String[] x) {
            this.x = x;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return x[(int)value];
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_temperature_graph, container, false);

        LineChart mChart = rootView.findViewById(R.id.mChart);
        mChart.getAxisRight().disableGridDashedLine();
        mChart.getXAxis().disableGridDashedLine();
        mChart.getAxisLeft().disableGridDashedLine();
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setPinchZoom(false);
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisLeft().removeAllLimitLines();
        mChart.getAxisLeft().setDrawTopYLabelEntry(false);
        mChart.getAxisLeft().setEnabled(true);
        mChart.getAxisRight().removeAllLimitLines();
        mChart.getAxisRight().setEnabled(false);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.getAxisRight().setDrawGridLines(false);
        mChart.setDrawGridBackground(false);

        ArrayList<Entry> y = new ArrayList<>();
        y.add(new Entry(0,10));
        y.add(new Entry(1,15));
        y.add(new Entry(2,25));
        y.add(new Entry(3,30));
        y.add(new Entry(4,5));
        y.add(new Entry(5,50));

        LineDataSet lineDataSet = new LineDataSet(y,"");
        lineDataSet.setFillAlpha(110);
        lineDataSet.disableDashedLine();
        lineDataSet.setColors(ColorTemplate.MATERIAL_COLORS);


        ArrayList<ILineDataSet> dataset = new ArrayList<>();
        dataset.add(lineDataSet);

        LineData lineData = new LineData(dataset);
        mChart.setData(lineData);
        XAxis xAxis = mChart.getXAxis();
        String [] x = {"jan","feb","mar","apr","jun","jul"};
        xAxis.setValueFormatter(new xvalues(x));
        xAxis.disableGridDashedLine();
        xAxis.setGranularity(1);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        return rootView;
    }
}
