package lab4.ui;

import com.toedter.calendar.JDateChooser;
import lab4.controllers.Controller;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Date;

public class ViewProgressCharts {
    private int width,height;
    private JDateChooser startDate,endDate;
    private JComboBox<String> exercises,chartType;
    private JButton selectButton;
    public ViewProgressCharts(int width, int height) {
        this.width = width;
        this.height = height;
        addButtons();
    }
    public void addButtons(){
        selectButton = new JButton("Select");
    }


    public JPanel viewProgressMenu(String exerciseName, String chartName, DefaultCategoryDataset dataset, String[] chartTypeList, String[] exerciseList){
        JPanel panel=new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.GRAY);

        if(exerciseList.length==0){
            JLabel exerciseLabel = new JLabel("Exercise list is empty.");
            exerciseLabel.setBounds(10,10,200,30);
            panel.add(exerciseLabel);
        }
        else{
            selectButton.setBounds(740,10,100,30);
            panel.add(selectButton);

            exercises=new JComboBox<>(exerciseList);
            exercises.setBounds(80,10,140,30);
            panel.add(exercises);

            chartType=new JComboBox<>(chartTypeList);
            chartType.setBounds(250,10,70,30);
            panel.add(chartType);

            startDate=new JDateChooser(new Date());
            startDate.setDateFormatString("dd/MM/yyyy");
            startDate.setBounds(380, 10, 140, 30);
            panel.add(startDate);

            endDate=new JDateChooser(new Date());
            endDate.setDateFormatString("dd/MM/yyyy");
            endDate.setBounds(565, 10, 140, 30);
            panel.add(endDate);

            JLabel exerciseLabel=new JLabel("Exercise");
            exerciseLabel.setBounds(10,10,100,30);
            panel.add(exerciseLabel);

            JLabel fromLabel=new JLabel("From");
            fromLabel.setBounds(340,10,100,30);
            panel.add(fromLabel);

            JLabel toLabel=new JLabel("To");
            toLabel.setBounds(540,10,100,30);
            panel.add(toLabel);

            if(exerciseName.isEmpty()){
                JLabel statusLabel = new JLabel("No exercise selected.");
                statusLabel.setBounds(10,50,200,30);
                panel.add(statusLabel);
            }
            else
                viewChart(panel, "Date", "Value", exerciseName+" "+chartName, dataset);
        }


        panel.setPreferredSize(new Dimension(width-30,height-150));
        panel.setBounds(10,60,width-30,height-150);
        return panel;
    }
    public void viewChart(JPanel panel, String xAxis, String yAxis,String chartName, DefaultCategoryDataset dataset){
        JFreeChart chart = ChartFactory.createLineChart(chartName,xAxis,yAxis,dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(10,50,width-30-20,height-150-75);
        panel.add(chartPanel);
    }

    public void setController(Controller controller){
        selectButton.addActionListener(e-> controller.viewChart((String) exercises.getSelectedItem(), startDate.getDate(), endDate.getDate(),(String) chartType.getSelectedItem()) );
    }
}
