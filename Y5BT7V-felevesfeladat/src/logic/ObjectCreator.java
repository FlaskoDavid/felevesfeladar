package logic;

public class ObjectCreator {
    private final Function function = new Function();
    private long ncalcK =0;

    private TimeWindow CreateTimeWindow(long STime){
       long  Etime=function.Randomisation(100,(int)STime);
        ncalcK =Etime;
        return new TimeWindow(STime,Etime);
    }

    private Job CreateJobs(int i,int NumberOfResources){
            long[] PTime=new long[NumberOfResources];
            long[] STime=new long[NumberOfResources];
            long[] ETime=new long[NumberOfResources];
            long d = function.Randomisation(5000,100);
            for (int j=0;j<NumberOfResources;j++){
                PTime[j]=function.Randomisation(100,1);
                STime[j]=0;
                ETime[j]=0;
            }

        return new Job(i,PTime,STime,ETime,d);

    }
    private Resource CreateResource(int i, int NumberOfResources,int NumberOfJob){
        long[]trTime=new long[NumberOfResources];
        int NCal=(int)function.Randomisation(10,2);
        TimeWindow[] TCal=new TimeWindow[NCal];
        long[][] sTime=new long[NumberOfJob][NumberOfJob];
        long end;
        for (int j=0;j<NumberOfResources;j++){
            trTime[j]=function.Randomisation(20,10);

        }
        for (int k=0;k<NCal;k++){
            if (k==0){
                end=function.Randomisation(30,10);
            }else{
                end= ncalcK+function.Randomisation(30,10);
            }
            TCal[k]=CreateTimeWindow(end);
        }

        for (int j =0 ;j<NumberOfJob;j++){
            for (int k=0;k<NumberOfJob;k++){
                if (j==k){
                    sTime[j][k]=0;
                }else{
                    sTime[j][k]= function.Randomisation(100,10);
                }
            }
        }
        return new Resource(i,trTime,sTime,NCal,TCal);
    }

    public Job[] CreateArrayOfJob(int NumberOfJobs,int NumberOfResources){
        Job[] job=new Job[NumberOfJobs];
        for (int i =0; i<NumberOfJobs;i++){
            job[i]=CreateJobs(i,NumberOfResources);
        }
        return job;
    }
    public Resource[] CreateArrayOfResource(int NumberOfJobs,int NumberOfResources){
        Resource[] resources=new Resource[NumberOfResources];
        for (int i =0; i<NumberOfResources;i++){
            resources[i]=CreateResource(i,NumberOfResources,NumberOfJobs);
                   }
        return resources;
    }

}
