package logic;

public class IO {
    public String GanttOfResourcePrint(Job[] job, int NumberOfJob, int NumberOfResource, int[] TimeVector){
        String text=("\n Eroforras-orientalt Gantt adatok:");
        for (int r=0; r<NumberOfResource; r++){
            text+=("\n " + r+ ". munkahely");
            text+=("\n # \t munka \t indulasi ido \t muveleti ido\t befejezesi ido");
            for (int i=0; i<NumberOfJob; i++){
                text+=("\n "+i+"\t"+TimeVector[i]+"\t"+job[TimeVector[i]].getStartTime()[r]
                        +"\t"+job[TimeVector[i]].getProcedureTime()[r]+"\t"+job[TimeVector[i]].getEndTime()[r]);
            }
        }
        return text;
    }

    public String GanttOfJobPrint(Job[] job, int NumberOfJob, int NumberOfResource, int[] TimeVector){
        String text=("\n Munka-orientalt Gantt adatok:");
        for (int i=0; i<NumberOfJob; i++){
            text+=("\n " + i+ ". munka vegrhajtasa");
            text+=("\n # \t gep \t indulasi ido \t muveleti ido\t befejezesi ido");
            for (int r=0; r<NumberOfResource; r++){
                text+=("\n "+r+"\t"+r+"\t"+job[TimeVector[i]].getStartTime()[r]
                        +"\t"+job[TimeVector[i]].getProcedureTime()[r]+"\t"+job[TimeVector[i]].getEndTime()[r]);
            }
        }
        return text;
    }

    public String PrintResourceCal(Resource[] resources,int NumberOfResource){
        String text=("\n Eroforrasok rendelkezesre atallasi idointervallumai");
        for (int r=0;r<NumberOfResource;r++){
            text+=("\n " + r+ ". eroforras ["+resources[r].getNumberOfCal()+"]");
            text+=("\n # \t Kezdet \t Vege ");
            for (int c=0;c<resources[r].getNumberOfCal();c++){
                text+=("\n "+ c+"\t"+resources[r].getCal()[c].getStart()+"\t"+resources[r].getCal()[c].getEnd());

            }
        }
        return text;
    }

    public void PrintFunction(double[] FunctionValue, int K){
        System.out.println("\n Celfuggvenyek ertekei:");
        for (int k=0;k<K;k++){
            System.out.println("\n Celfuggveny[" +k+"]="+FunctionValue);
        }
    }
}
