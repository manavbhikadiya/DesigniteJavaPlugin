package designite.ui.Charts;

import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.project.Project;
import com.intellij.util.ui.UIUtil;
import designite.lineMarkerProvider.ProjectSmellsInfo;
import designite.lineMarkerProvider.SmellsInfoProvider;
import org.knowm.xchart.*;
import org.knowm.xchart.style.PieStyler;

import java.awt.*;
import java.util.List;

public class MetricPieChart {
    public PieChart getPieChart(List<Number> chartData, List<String> chartLabels) {
        PieChart chart = new PieChartBuilder().width(50).height(50).title("").build();

        Color annotationColor = EditorColorsManager.getInstance().getGlobalScheme().getDefaultForeground();
        Color backgroundColor = UIUtil.getPanelBackground();

        for (int i = 0; i < chartData.size(); i++) {
            chart.addSeries(chartLabels.get(i), chartData.get(i));
        }
        Color[] sliceColors = new Color[] {
                new Color(125, 90, 80),
                new Color(180, 132, 108),
                new Color(229, 178, 153),
                new Color(252, 222, 192),
                new Color(255, 237, 219),
                new Color(237, 205, 187),
                new Color(227, 183, 160),
                new Color(191, 146, 112),
                new Color(141, 123, 104)
        };

        PieStyler styler = chart.getStyler();

        styler.setSeriesColors(sliceColors);
        styler.setToolTipsEnabled(true);
        styler.setToolTipBackgroundColor(backgroundColor);
        styler.setToolTipBorderColor(annotationColor);
        styler.setToolTipHighlightColor(backgroundColor);
        styler.setLegendBackgroundColor(backgroundColor);
        styler.setChartBackgroundColor(backgroundColor);
        styler.setPlotBackgroundColor(backgroundColor);
        styler.setPlotBorderColor(backgroundColor);
        styler.setPlotContentSize(.9);
        styler.setLegendBorderColor(backgroundColor);
        styler.setChartFontColor(annotationColor);
        styler.setDonutThickness(.5);
        chart.getStyler().setLegendVisible(true);
        styler.setDefaultSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);

        return chart;
    }

    public PieChart getDonutPieChart(Project project) {
        PieChart chart = new PieChartBuilder().width(50).height(50).title("").build();

        Color annotationColor = EditorColorsManager.getInstance().getGlobalScheme().getDefaultForeground();
        Color backgroundColor = UIUtil.getPanelBackground();

        ProjectSmellsInfo smellsInfoProvider = SmellsInfoProvider.getInstance(project);

        if(smellsInfoProvider.isProjectAnalyzed()) {
            chart.addSeries("Implementation Smells", smellsInfoProvider.getTotalImplementationSmells());
            chart.addSeries("Design Smells", smellsInfoProvider.getTotalDesignSmells());
            chart.addSeries("Architecture Smells", smellsInfoProvider.getTotalArchitectureSmells());
            chart.addSeries("Test Smells", smellsInfoProvider.getTotalTestSmells());
            chart.addSeries("Testability Smells", smellsInfoProvider.getTotalTestabilitySmells());
        }

        Color[] sliceColors = new Color[] {
                new Color(125, 90, 80),
                new Color(180, 132, 108),
                new Color(229, 178, 153),
                new Color(252, 222, 192),
                new Color(255, 237, 219),
        };
        chart.getStyler().setSeriesColors(sliceColors);
        chart.getStyler().setDonutThickness(.5);
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setAnnotationType(PieStyler.AnnotationType.LabelAndPercentage);
        chart.getStyler().setAnnotationsFontColor(annotationColor);
        chart.getStyler().setAnnotationDistance(1.15);
        chart.getStyler().setPlotContentSize(.9);
        chart.getStyler().setStartAngleInDegrees(90);
        chart.getStyler().setDefaultSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);
        chart.getStyler().setChartBackgroundColor(backgroundColor);
        chart.getStyler().setPlotBackgroundColor(backgroundColor);
        chart.getStyler().setPlotBorderVisible(false);
        chart.getStyler().setChartTitleFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        chart.getStyler().setChartFontColor(annotationColor);
        return chart;
    }
}
