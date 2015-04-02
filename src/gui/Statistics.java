package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.util.Rotation;
/*
 * NOTES:
 * 
 * as of now, this panel recieves two debug arrays instead of real information for the medlemmer over time graph.
 */
public class Statistics extends JPanel {
	
	private double[][] dataMedlemmer;
	private double[][] dataStotte;
	
	private int srKvinner;
	private int srMenn;
	private int jrKvinner;
	private int jrMenn;
	private int medlemTotal;
	
	private JPanel infoPanel;
	private JPanel grafpanel;
	private JPanel tidsPanel;
	private JPanel topPanel;
	
	private GraphRefresh graphRefresh;
	
	Statistics(double[][] dataMedlemmer,double[][] dataStotte,int medlemTotal,int srKvinner,int srMenn,int jrKvinner,int jrMenn) {
		this.dataMedlemmer = dataMedlemmer;
		this.dataStotte = dataStotte;
		this.srKvinner =srKvinner;
		this.srMenn = srMenn;
		this.jrKvinner = jrKvinner;
		this.jrMenn = jrMenn;
		this.medlemTotal = medlemTotal;
		
		
		
		
		setLayout(new GridLayout(2,1));
		createPanels();
		
		
		
		/*
		 * adds the chartsPanels to the gridPanels.
		 */
		infoPanel.add(createMemberStatistics());
		infoPanel.add(createStatisticsGraphPanel());
		add(createOverTimeChart());
		
		
	}
	
	
	/*
	 * method that creates the dataset for the timechart.
	 */
	XYDataset createMedlemTidDataset() {
		
		//the dataset that will be returned.
		DefaultXYDataset ds = new DefaultXYDataset();
		
		//to add another datacollection for the chart supply a name and double array of doubles.
		ds.addSeries("Medlemmer", dataMedlemmer);
		ds.addSeries("støttemedlemmer",dataStotte);
		
		return ds;
	}
	
	
	public ChartPanel createOverTimeChart() {
		/*
		 * creates the charts for this panel.
		 */
		JFreeChart timeChart = ChartFactory.createXYLineChart("Medlem Per År", "Medlemmer", "År", createMedlemTidDataset(),PlotOrientation.VERTICAL,true,true,false);
		XYPlot xyPlot = timeChart.getXYPlot();
		
		
		//changes the numbers so that they are shown on the chart without the decimals.
		ValueAxis rangeAxis = xyPlot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		
		ValueAxis domainAxis = xyPlot.getDomainAxis();
		domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		
		/*
		 * creates shapes at the datapoints, makes it easier to look at.
		 */
		XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer();
		xylineandshaperenderer.setSeriesLinesVisible(0, true);
		xylineandshaperenderer.setSeriesShapesVisible(0, true);
		xylineandshaperenderer.setSeriesLinesVisible(1, true);
		xylineandshaperenderer.setSeriesShapesVisible(1, true);
		xylineandshaperenderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
		xylineandshaperenderer.setDefaultEntityRadius(6);
		xyPlot.setRenderer(xylineandshaperenderer);
		
		ChartPanel timeChartPanel = new ChartPanel(timeChart);
		timeChartPanel.setMouseWheelEnabled(true);
		return timeChartPanel;
	}
	
	/*
	 * creating the panels
	 * 
	 * TODO: might want to change this to gridbagLayout. looks like the timechart could benefit visually from a bigger panel size.
	 * 			can't tell before i have implemented the other statistics for the demography panel.
	 */
	public void createPanels() {
		
		infoPanel = new JPanel(new GridLayout(1,2));
		infoPanel.setBackground(Color.BLACK);
		
		add(infoPanel);
	}
	
	public JPanel createMemberStatistics() {
		JPanel statPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
		int inset1 = 2;
		int inset2 = 1;
		int inset3 = 0;
		int inset4 = 5;
		
		
		JLabel totalMembers = new JLabel("Medlemmer:");
		JLabel srMembers = new JLabel("Senior:");
		JLabel srKvinner = new JLabel("Senior Kvinner:");
		JLabel srMenn = new JLabel("Senior Menn:");
		JLabel jrMembers = new JLabel("Junior:");
		JLabel jrKvinner = new JLabel("Junior kvinner:");
		JLabel jrMenn = new JLabel("Junior Menn:");
		
		JTextField totalMembersField = new JTextField(Integer.toString(medlemTotal),5);
		JTextField srMembersField = new JTextField(Integer.toString(this.srKvinner + this.srMenn),5);
		JTextField srKvinnerField = new JTextField(Integer.toString(this.srKvinner),5);
		JTextField srMennField = new JTextField(Integer.toString(this.srMenn),5);
		JTextField jrMembersField = new JTextField(Integer.toString(this.jrKvinner + this.jrMenn),5);
		JTextField jrKvinnerField = new JTextField(Integer.toString(this.jrKvinner),5);
		JTextField jrMennField = new JTextField(Integer.toString(this.jrMenn),5);
		
		totalMembersField.setEditable(false);
		srMembersField.setEditable(false);
		srKvinnerField.setEditable(false);
		srMennField.setEditable(false);
		jrMembersField.setEditable(false);
		jrKvinnerField.setEditable(false);
		jrMennField.setEditable(false);
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.insets = new Insets(inset1,inset2,inset3,inset4);
		
		/*
		 * labels
		 */
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		
		statPanel.add(totalMembers,gc);
		gc.gridy++;
		
		statPanel.add(srMembers,gc);
		gc.gridy++;
		
		statPanel.add(srKvinner,gc);
		gc.gridy++;
		
		statPanel.add(srMenn,gc);
		gc.gridy++;
		
		statPanel.add(jrMembers,gc);
		gc.gridy++;
		
		statPanel.add(jrKvinner,gc);
		gc.gridy++;
		
		statPanel.add(jrMenn,gc);
		gc.gridy++;
		
		
		/*
		 * textFields
		 */
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		
		gc.gridx++;
		gc.gridy = 0;
		
		statPanel.add(totalMembersField,gc);
		gc.gridy++;
		
		statPanel.add(srMembersField,gc);
		gc.gridy++;
		
		statPanel.add(srKvinnerField,gc);
		gc.gridy++;
		
		statPanel.add(srMennField,gc);
		gc.gridy++;
		
		statPanel.add(jrMembersField,gc);
		gc.gridy++;
		
		statPanel.add(jrKvinnerField,gc);
		gc.gridy++;
		
		statPanel.add(jrMennField,gc);
		gc.gridy++;
		
		
		

		return statPanel;
	}
	
	/*
	 * creates a chartPanel with a piechart and fills it with a pie dataset and returns it.
	 */
	public ChartPanel createStatisticsGraphPanel() {
		
		
		JFreeChart pieChart = ChartFactory.createPieChart3D("Medlemmer", createPieDataset());
		
		PiePlot3D pieplot3d = (PiePlot3D)pieChart.getPlot();
		pieplot3d.setDarkerSides(true);
		pieplot3d.setStartAngle(290D);
		pieplot3d.setDirection(Rotation.CLOCKWISE);
		pieplot3d.setForegroundAlpha(0.5F);
		pieplot3d.setNoDataMessage("No data to display");
		
		
		ChartPanel graphPanel = new ChartPanel(pieChart);
		graphPanel.setMouseWheelEnabled(true);
		
		return graphPanel;
	}
	
	
	/*
	 * creates a pie dataset and returns it for use in a piechart
	 */
	public PieDataset createPieDataset() {
		DefaultPieDataset pieSet = new DefaultPieDataset();
		
		pieSet.setValue("Kvinner senior", srKvinner);
		pieSet.setValue("Kvinner junior",jrKvinner );
		pieSet.setValue("Menn senior", srMenn );
		pieSet.setValue("Menn junior", jrMenn);
		
		
		return pieSet;
	}
	
	public void refreshData() {
		graphRefresh.refresh();
		infoPanel.removeAll();
		infoPanel.add(createMemberStatistics());
		infoPanel.add(createStatisticsGraphPanel());
		removeAll();
		add(infoPanel);
		add(createOverTimeChart());
		
		revalidate();
		infoPanel.revalidate();
		
	}


	public double[][] getDataMedlemmer() {
		return dataMedlemmer;
	}


	public void setDataMedlemmer(double[][] dataMedlemmer) {
		this.dataMedlemmer = dataMedlemmer;
	}


	public double[][] getDataStotte() {
		return dataStotte;
	}


	public void setDataStotte(double[][] dataStotte) {
		this.dataStotte = dataStotte;
	}


	public int getSrKvinner() {
		return srKvinner;
	}


	public void setSrKvinner(int srKvinner) {
		this.srKvinner = srKvinner;
	}


	public int getSrMenn() {
		return srMenn;
	}


	public void setSrMenn(int srMenn) {
		this.srMenn = srMenn;
	}


	public int getJrKvinner() {
		return jrKvinner;
	}


	public void setJrKvinner(int jrKvinner) {
		this.jrKvinner = jrKvinner;
	}


	public int getJrMenn() {
		return jrMenn;
	}


	public void setJrMenn(int jrMenn) {
		this.jrMenn = jrMenn;
	}


	public int getMedlemTotal() {
		return medlemTotal;
	}


	public void setMedlemTotal(int medlemTotal) {
		this.medlemTotal = medlemTotal;
	}
	
	public void setGraphRefresh(GraphRefresh graphListener) {
		graphRefresh = graphListener;
	}
	

}

















