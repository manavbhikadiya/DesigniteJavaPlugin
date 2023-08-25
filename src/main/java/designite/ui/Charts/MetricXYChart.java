package designite.ui.Charts;

import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.UIUtil;
import designite.lineMarkerProvider.ProjectSmellsInfo;
import designite.lineMarkerProvider.SmellsInfoProvider;
import designite.models.TypeMetrics;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.Circle;
import org.knowm.xchart.style.markers.Marker;
import org.knowm.xchart.style.markers.None;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MetricXYChart {
    public XYChart getXYChart(Project project) {
        XYChart chart = new XYChartBuilder()
                .width(50)
                .height(50)
                .title("FanIn, FanOut Correlation")
                .xAxisTitle("Range")
                .yAxisTitle("FanIn & FanOut")
                .build();

        ProjectSmellsInfo smellsInfoProvider = SmellsInfoProvider.getInstance(project);
        List<TypeMetrics> typeMetricsList = smellsInfoProvider.getTypeMetrics();

        List<Integer> fanInData = new ArrayList<>();
        List<Integer> fanOutData = new ArrayList<>();
        List<Integer> xAxisData = new ArrayList<>();
        int count = 1;
        for (int i = 1; i < typeMetricsList.size(); i++) {
            if ((!typeMetricsList.get(i).FANIN.equals("0")) && (!typeMetricsList.get(i).FANOUT.equals("0"))) {
                fanInData.add(Integer.parseInt(typeMetricsList.get(i).FANIN));
                fanOutData.add(Integer.parseInt(typeMetricsList.get(i).FANOUT));
                xAxisData.add(count);
                count++;
            }
        }

        Color annotationColor = EditorColorsManager.getInstance().getGlobalScheme().getDefaultForeground();
        Color backgroundColor = UIUtil.getPanelBackground();

        chart.getStyler().setAnnotationsFontColor(annotationColor);
        chart.getStyler().setPlotContentSize(.9);
        chart.getStyler().setChartFontColor(annotationColor);
        chart.getStyler().setChartBackgroundColor(backgroundColor);
        chart.getStyler().setPlotBackgroundColor(backgroundColor);
        chart.getStyler().setPlotBorderVisible(false);
        chart.getStyler().setAxisTickLabelsColor(annotationColor);
        chart.getStyler().setAxisTickMarksColor(annotationColor);
        chart.getStyler().setHasAnnotations(false);
        chart.getStyler().setPlotGridVerticalLinesVisible(true);
        chart.getStyler().setLegendVisible(true);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setLegendLayout(Styler.LegendLayout.Horizontal);
        chart.getStyler().setLegendBackgroundColor(backgroundColor);
        chart.getStyler().setToolTipsEnabled(true);
        chart.getStyler().setToolTipBackgroundColor(backgroundColor);
        chart.getStyler().setToolTipBorderColor(annotationColor);
        chart.getStyler().setToolTipHighlightColor(backgroundColor);
        chart.getStyler().setToolTipType(Styler.ToolTipType.xAndYLabels);
        chart.getStyler().setMarkerSize(8);
        chart.getStyler().setSeriesMarkers(new Marker[]{new Circle()});

        float maxXAxisData = Collections.max(xAxisData);
        float maxYAxisData = Collections.max(fanInData);

        XYSeries mainSequence = chart.addSeries("Main Sequence", List.of(0.00, maxXAxisData), List.of(maxYAxisData, 0.00));
        mainSequence.setMarker(new None());
        mainSequence.setLineColor(new JBColor(new Color(0xf9c784), new Color(0xf9c784)));
        mainSequence.setLineStyle(new BasicStroke(0.5f));
        mainSequence.setLabel("Main Sequence");

        XYSeries xySeriesFanOut = chart.addSeries("FAN OUT", xAxisData, fanOutData);
        xySeriesFanOut.setMarkerColor(new JBColor(new Color(0x499C54), new Color(0x499C54)));
        xySeriesFanOut.setSmooth(true);
        xySeriesFanOut.setLineStyle(new BasicStroke(0));

        XYSeries xySeriesFanIn = chart.addSeries("FAN IN", xAxisData, fanInData);
        xySeriesFanIn.setMarkerColor(new JBColor(new Color(0xf24c00), new Color(0xf24c00)));
        xySeriesFanIn.setSmooth(true);
        xySeriesFanIn.setLineStyle(new BasicStroke(0));



        return chart;
    }
}
