package com.github.varenytsiamykhailo.knml.util;

import java.awt.Color;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;


public class Profiler extends ApplicationFrame {

    /**
     * This is here because extending ApplicationFrame requires it.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Timeable defines the methods an object must provide to work with Profiler
     */
    public interface Timeable {
        /*
         * setup is invoked before the clock starts.
         */
        public void setup(int n);

        /*
         * timeMe does whatever operation we are timing.
         */
        public void timeMe(int n);
    }

    private Timeable timeable;

    public Profiler(String title, Timeable timeable) {
        super(title);
        this.timeable = timeable;
    }

    /**
     * Invokes timeIt with a range of `n` from `startN` until runtime exceeds `endMillis`.
     *
     * @param startN
     * @param endMillis
     * @return
     */
    public XYSeries timingLoop(int startN, int endMillis) {
        final XYSeries series = new XYSeries("Time (ms)");

        int n = startN;
        for (int i = 0; i < 20; i++) {
            // run it once to warm up
            timeIt(n);
            timeIt(n);

            // then start timing
            long total = 0;

            int countOfExperiment = 10;
            // run count times and add up total runtime
            for (int j = 0; j < countOfExperiment; j++) {
                long timeItResultTmp = timeIt(n);
                total += timeItResultTmp;
                //System.out.println("total " + i + " = " + timeItResultTmp);
            }
            total = total / countOfExperiment;
            System.out.println("data size = " + n + ", " + "time ms = " + total);

            // don't store data until we get to 4ms
            if (total > 3) {
                series.add(n, total);
            }

            // stop when the runtime exceeds the end threshold
            if (total > endMillis) {
                break;
            }
            // otherwise double the size and continue
            n *= 2;
        }
        return series;
    }

    /**
     * Invokes setup and timeMe on the embedded Timeable.
     *
     * @param n
     * @return
     */
    public long timeIt(int n) {
        timeable.setup(n);
        final long startTime = System.currentTimeMillis();
        timeable.timeMe(n);
        final long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    /**
     * Plots the results.
     *
     * @param series
     */
    public void plotResults(XYSeries series) {
        double slope = estimateSlope(series);
        System.out.println("Estimated slope = " + slope);

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        final JFreeChart chart = ChartFactory.createXYLineChart(
                "",          // chart title
                "",               // domain axis label
                "",                  // range axis label
                dataset,                  // data
                PlotOrientation.VERTICAL,
                false,                     // include legend
                true,
                false
        );

        final XYPlot plot = chart.getXYPlot();
        final NumberAxis domainAxis = new LogarithmicAxis("Problem size (n)");
        final NumberAxis rangeAxis = new LogarithmicAxis("Runtime (ms)");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(rangeAxis);
        chart.setBackgroundPaint(Color.white);
        plot.setOutlinePaint(Color.black);

        TextTitle asymptoticComplexity = new TextTitle( "Asymptotic complexity 'O' = " + slope );
        asymptoticComplexity.setPosition( TextTitle.DEFAULT_POSITION );
        asymptoticComplexity.setHorizontalAlignment( TextTitle.DEFAULT_HORIZONTAL_ALIGNMENT);
        chart.addSubtitle( asymptoticComplexity ); // Here chart is the instance of JFreeChart

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 600));
        setContentPane(chartPanel);
        pack();
        RefineryUtilities.centerFrameOnScreen(this);
        setVisible(true);
    }

    /**
     * Uses simple regression to estimate the slope of the series.
     *
     * @param series
     * @return
     */
    public double estimateSlope(XYSeries series) {
        SimpleRegression regression = new SimpleRegression();

        for (Object item : series.getItems()) {
            XYDataItem xy = (XYDataItem) item;
            regression.addData(Math.log(xy.getXValue()), Math.log(xy.getYValue()));
        }
        return regression.getSlope();
    }
}
