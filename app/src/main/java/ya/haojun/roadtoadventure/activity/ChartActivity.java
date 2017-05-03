package ya.haojun.roadtoadventure.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.retrofit.GoogleMapService;
import ya.haojun.roadtoadventure.model.GooglePath;
import ya.haojun.roadtoadventure.model.GooglePoint;
import ya.haojun.roadtoadventure.helper.GoogleMapHelper;

public class ChartActivity extends CommonActivity {

    // ui
    private LineChart mChart;

    // data
    private ArrayList<LatLng> list_latLng;
    private GooglePath paths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        // ui reference
        mChart = (LineChart) findViewById(R.id.lc_chart);
        // get extra
        Bundle b = getIntent().getExtras();
        list_latLng = b.getParcelableArrayList("routes");
        // load elevation
        loadElevation();
    }

    private void loadElevation() {
        // GoogleMapService is ready
        GoogleMapService service = GoogleMapService.retrofit.create(GoogleMapService.class);
        // getPaths is ready
        Map<String, String> map = new HashMap<>();
        map.put("locations", GoogleMapHelper.getLocationsQueryString(list_latLng));
        map.put("key", getString(R.string.google_maps_key));
        Call<GooglePath> call = service.getElevations(map);
        showLoadingDialog();
        call.enqueue(new Callback<GooglePath>() {
            @Override
            public void onResponse(Call<GooglePath> call, Response<GooglePath> response) {
                dismissLoadingDialog();
                paths = response.body();
                if (paths != null) {
                    initChart();
                }
            }

            @Override
            public void onFailure(Call<GooglePath> call, Throwable t) {
                dismissLoadingDialog();
            }
        });
    }

    private void initChart() {
        float yMax = -99999;
        float yMin = 99999;

        for (GooglePoint p : paths.getResults()) {
            if (p.getElevation() > yMax) {
                yMax = p.getElevation();
            } else if (p.getElevation() < yMin) {
                yMin = p.getElevation();
            }
        }
        yMax *= 1.1;
        yMin *= 0.9;

        // background color
        mChart.setViewPortOffsets(0, 0, 0, 0);
        mChart.setBackgroundColor(ContextCompat.getColor(this, R.color.chart_line_background));
        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        mChart.setMaxHighlightDistance(300);

        XAxis x = mChart.getXAxis();
        x.setEnabled(false);

        YAxis y = mChart.getAxisLeft();
        y.setAxisMaximum(yMax);
        y.setAxisMinimum(yMin);
        y.setTypeface(Typeface.DEFAULT);
        y.setLabelCount(3, false);
        y.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.WHITE);

        mChart.getAxisRight().setEnabled(false);

        // add data
        setData();

        mChart.getLegend().setEnabled(false);

        mChart.animateXY(1000, 1000);

        // dont forget to refresh the drawing
        mChart.invalidate();
    }

    private void setData() {

        ArrayList<Entry> yVals = new ArrayList<>();

        for (int i = 0; i < paths.getResults().size(); i++) {
            yVals.add(new Entry(i, paths.getResults().get(i).getElevation()));
        }

        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//            set1.setMode(LineDataSet.Mode.LINEAR);
//            set1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
//            set1.setMode(LineDataSet.Mode.STEPPED);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
//            set1.setCircleRadius(4f);
//            set1.setCircleColor(Color.WHITE);
            //set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(ContextCompat.getColor(this, R.color.chart_line_fill_background));
            set1.setFillColor(ContextCompat.getColor(this, R.color.chart_line_fill_background));
            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setDrawVerticalHighlightIndicator(false);
//            set1.setFillFormatter(new IFillFormatter() {
//                @Override
//                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
//                    return -10;
//                }
//            });

            // create a data object with the datasets
            LineData data = new LineData(set1);
            data.setValueTypeface(Typeface.DEFAULT);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            mChart.setData(data);
        }
    }
}
