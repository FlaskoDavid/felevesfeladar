package logic;

import javax.swing.*;

public class Controller {
    private final Algorithm algorithm = new Algorithm();
    private final Function function = new Function();
    private final ObjectCreator objectCreator = new ObjectCreator();
    private final IO io = new IO();

    public void callSearch(Job[] job, Resource[] resources, int NumberOfJob, int NumberOfResource, int[] TimeVector,
                           double[] priority, int K, int cut_mode, int Loop, int Step,int type) {
        try {
            algorithm.Search(job, resources, NumberOfJob, NumberOfResource, TimeVector, priority, K, cut_mode, Loop, Step,type);
        } catch (Exception exception) {

            JOptionPane.showMessageDialog(null, "Túl kevés munka a keverés alapú kiválasztáshoz (min 5) vagy ciklus hiba", "Program üzeni", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void callSimulation(Job[] job, Resource[] resources, int NumberOfJob, int NumberOfResource,
                               int[] TimeVector, long Time0, int cut_mode) {
        function.FlowShopSimulation(job, NumberOfJob, NumberOfResource, resources, TimeVector, Time0, cut_mode);
    }

    public void callEvaluate(Job[] job, int NumberOfJob, int NumberOfResource, int[] TimeVector, double[] FunctionValue) {
        function.Evaluate(job, NumberOfJob, NumberOfResource, TimeVector, FunctionValue);
    }

    public Job[] callJobCreator(int NumberOfJobs, int NumberOfResources) {
        return objectCreator.CreateArrayOfJob(NumberOfJobs, NumberOfResources);
    }

    public Resource[] callResourceCreator(int NumberOfJobs, int NumberOfResources) {
        return objectCreator.CreateArrayOfResource(NumberOfJobs, NumberOfResources);
    }

    public boolean goodField(JTextField area, String fieldName) {
        String s = GetText(area);
        boolean leaver = isNull(area, fieldName);
        if (leaver) try {
            Integer.parseInt(s);
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, "Hibás adat " + fieldName, "Program üzenet", JOptionPane.ERROR_MESSAGE);
            leaver = false;
        }
        return leaver;
    }

    private String GetText(JTextField area) {
        return area.getText();
    }

    private boolean isNull(JTextField area, String fieldName) {
        String s = GetText(area);
        if (s.length() > 0) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Üres mező " + fieldName, "Program üzenet", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public void fillArea(Job[] job, int NumberOfJob, int NumberOfResource, int[] TimeVector, Resource[] resources, int mode, JTextArea area) {
        String text = new String();
        switch (mode) {
            case 0:
                text = io.GanttOfJobPrint(job, NumberOfJob, NumberOfResource, TimeVector);
                break;
            case 1:
                text = io.GanttOfResourcePrint(job, NumberOfJob, NumberOfResource, TimeVector);
                break;
            case 2:
                text = io.PrintResourceCal(resources, NumberOfResource);
                break;
        }
        area.setText(text);
    }

    public int[] callTimeVector(int NumberOfJob) {
        return function.CreateTimeVector(NumberOfJob);
    }

    public String intToString(double[] funVal, int i) {
        return String.valueOf(funVal[i]);
    }

}
