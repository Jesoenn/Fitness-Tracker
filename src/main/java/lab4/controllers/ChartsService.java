package lab4.controllers;

import lab4.workout.ExerciseDetail;
import lab4.workout.TrainingSession;
import org.jfree.data.category.DefaultCategoryDataset;

import java.text.SimpleDateFormat;
import java.util.*;

public class ChartsService {
    private String[] chartTypes;

    public ChartsService() {
        chartTypes = new String[]{"Sets", "Reps", "Weight"};
    }

    public DefaultCategoryDataset getDataset(ArrayList<TrainingSession> trainingSessions, String exerciseName, Date startDate, Date endDate, String chartType){
        DefaultCategoryDataset dataset;
        HashMap<String, Integer> collectedData = new HashMap<>();

        startDate=setDateHour(startDate, 0);
        endDate=setDateHour(endDate, 23);
        for(TrainingSession trainingSession : trainingSessions){
            Date sessionDate = trainingSession.getSessionStartTime();
            if(!sessionDate.before(startDate) && !sessionDate.after(endDate)){
                String formattedDate=formatDate(trainingSession.getSessionStartTime());
                int value=0;
                for(ExerciseDetail exerciseDetail : trainingSession.getExercises()){
                    if(exerciseDetail.getExercise().getName().equals(exerciseName)){
                        if(chartType.equals("Sets"))
                            value+=exerciseDetail.getSets();
                        else if(chartType.equals("Reps"))
                            value+=exerciseDetail.getRepetitions();
                        else if(chartType.equals("Weight"))
                            value+=exerciseDetail.getWeight();
                    }
                }
                if(collectedData.containsKey(formattedDate)){
                    collectedData.put(formattedDate, collectedData.get(formattedDate)+value);
                }
                else
                    collectedData.put(formattedDate, value);
                //dataset.addValue(value,chartType,formattedDate);
            }
        }

        dataset=sortDataset(collectedData,chartType);
        return dataset;
    }

    public Date setDateHour(Date date, int time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, time);
        calendar.set(Calendar.MINUTE, time);
        return calendar.getTime();
    }

    public DefaultCategoryDataset sortDataset(HashMap<String, Integer> dataset, String chartType){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        HashMap<Date, Integer> datasetDate = new HashMap<>();
        for(Map.Entry<String, Integer> entry : dataset.entrySet()){
            try{
                Date date = formatter.parse(entry.getKey());
                datasetDate.put(date, entry.getValue());
            } catch(Exception e){
                e.printStackTrace();
            }
        }

        List<Date> sortedDates = new ArrayList<>(datasetDate.keySet());
        Collections.sort(sortedDates);

        DefaultCategoryDataset sortedDataset = new DefaultCategoryDataset();
        for(Date date: sortedDates){
            Integer value=datasetDate.get(date);
            String formattedDate=formatDate(date);

            sortedDataset.addValue(value,chartType,formattedDate);
        }

        return sortedDataset;
    }

    public String formatDate(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }
    public String[] getChartTypes() {
        return chartTypes;
    }
}
