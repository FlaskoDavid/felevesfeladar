package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Function {
    private final Algorithm algorithm = new Algorithm();
    private Random rand = new Random();

    public long Randomisation(int start, int end) {

        return end + (long) (Math.random() * start);
    }

    public void Evaluate(Job[] job, int NumberOfJob, int NumberOfResource, int[] TimeVector, double[] FunctionValue) {
        long CompletionTimeMax;
        long LatenessIni;
        long TardinessIni;
        long TardinessMax = 0;
        long TardinessSum = 0;
        long UnityPenaltySum = 0;

        for (int i = 0; i < NumberOfJob; i++) {

            LatenessIni = job[i].getEndTime()[NumberOfResource - 1] - job[i].getD();
            TardinessIni = Math.max(0, LatenessIni);
            if (i == 0) {
                TardinessMax = TardinessIni;
            } else {
                if (TardinessMax < TardinessIni) {
                    TardinessMax = TardinessIni;
                }
            }

            TardinessSum += TardinessIni;
            if (TardinessIni > 0) {
                UnityPenaltySum++;
            }
        }
        CompletionTimeMax = job[TimeVector[NumberOfJob - 1]].getEndTime()[NumberOfResource - 1];

        FunctionValue[0] = CompletionTimeMax;
        FunctionValue[1] = TardinessMax;
        FunctionValue[2] = TardinessSum;
        FunctionValue[3] = UnityPenaltySum;

    }

    public void CopySearch(int[] TimeVector1, int[] TimeVector2, int NumberOfJob) {
        for (int i = 0; i < NumberOfJob; i++) {
            TimeVector1[i] = TimeVector2[i];
        }
    }

    public void CopyFunction(double[] ObjectFunction1, double[] ObjectFunction2, int K) {
        for (int i = 0; i < K; i++) {
            ObjectFunction1[i] = ObjectFunction2[i];
        }
    }

    public void Neighbour(int[] TimeVectorIn0, int[] TimeVectorInActual, int NumberOfJob, int type) {
        CopySearch(TimeVectorInActual, TimeVectorIn0, NumberOfJob);


        switch (type) {
            case 0:
                swap(TimeVectorIn0, TimeVectorInActual, NumberOfJob);
                break;
            case 1:
                insert(TimeVectorIn0, TimeVectorInActual, NumberOfJob);
                break;
            case 2:
                scramble(TimeVectorIn0, TimeVectorInActual, NumberOfJob);
                break;
            case 3:
                inverse(TimeVectorIn0, TimeVectorInActual, NumberOfJob);
                break;
            case 4:
                orderOx(TimeVectorIn0, TimeVectorInActual, NumberOfJob);
                break;
        }

    }

    private void swap(int[] TimeVectorIn0, int[] TimeVectorInActual, int NumberOfJob) {
        int x, y;
        x = rand.nextInt(NumberOfJob);
        y = rand.nextInt(NumberOfJob);

        TimeVectorInActual[x] = TimeVectorIn0[y];
        TimeVectorInActual[y] = TimeVectorIn0[x];

    }

    private void insert(int[] TimeVectorIn0, int[] TimeVectorInActual, int NumberOfJob) {
        int x, y;
        x = rand.nextInt(NumberOfJob);
        y = rand.nextInt(NumberOfJob);


        for (int i = 0; i < NumberOfJob; i++) {
            if (i == Math.min(x, y) + 1) {
                TimeVectorInActual[i] = TimeVectorIn0[Math.max(x, y)];
            } else if (i == Math.max(x, y)) {
                if (x == 11 && y == 11) {
                    TimeVectorInActual[i] = TimeVectorIn0[Math.min(x, y)];
                } else {
                    TimeVectorInActual[i] = TimeVectorIn0[Math.min(x, y) + 1];
                }

            } else {
                TimeVectorInActual[i] = TimeVectorIn0[i];
            }
        }
    }

    private void scramble(int[] TimeVectorIn0, int[] TimeVectorInActual, int NumberOfJob) {

        int x = rand.nextInt(NumberOfJob - 4);
        int scrambles[] = new int[4];
        List<Integer> shu = new ArrayList<Integer>();

        for (int i = x; i < x + 4; i++) {
            shu.add(TimeVectorIn0[i]);
        }
        Collections.shuffle(shu, new Random(4));

        for (int i = 0; i < 4; i++) {
            scrambles[i] = shu.get(i);
        }
        int y = 0;
        for (int i = x; i < x + 4; i++) {
            TimeVectorInActual[i] = scrambles[y];
            y++;
        }

    }

    private void inverse(int[] TimeVectorIn0, int[] TimeVectorInActual, int NumberOfJob) {
        int x, y;
        x = rand.nextInt(NumberOfJob);
        y = rand.nextInt(NumberOfJob);
        List<Integer> shu = new ArrayList<Integer>();
        for (int i = Math.min(x, y); i < Math.max(x, y) + 1; i++) {
            shu.add(TimeVectorIn0[i]);
        }
        Collections.reverse(shu);
        int z = 0;
        for (int i = Math.min(x, y); i < Math.max(x, y) + 1; i++) {
            TimeVectorInActual[i] = shu.get(z);
            z++;
        }
    }

    private void orderOx(int[] TimeVectorIn0, int[] TimeVectorInActual, int NumberOfJob) {
        int x = rand.nextInt(NumberOfJob);
        int y = rand.nextInt(NumberOfJob);
        List<Integer> shu = new ArrayList<Integer>();
        for (int i = 0; i < NumberOfJob; i++) {
            if (Math.min(x, y) >= i || i >= Math.max(x, y)) {
                shu.add(TimeVectorIn0[i]);
            }else{
                TimeVectorInActual[i] = TimeVectorIn0[i];
            }
        }

        Collections.reverse(shu);
        int z = 0;
        for (int i = 0; i < NumberOfJob; i++) {
            if (Math.min(x, y) >= i || i >= Math.max(x, y)) {
                TimeVectorInActual[i] = shu.get(z);
                z++;
            }

        }
    }


    public double WSumRelativeFunctionValue(double[] FunXSolutionVector, double[] FunYSolutionVector, double[] priority, int K) {
        double FunctionValue = 0;
        double a, b;
        double D;

        for (int k = 0; k < K; k++) {
            a = FunXSolutionVector[k];
            b = FunYSolutionVector[k];

            if (Math.max(a, b) == 0) {
                D = 0;
            } else {
                D = (b - a) / Math.max(a, b);
            }
            FunctionValue += priority[k] * D;
        }
        return FunctionValue;
    }

    public void FlowShopSimulation(Job[] job, int NumberOfJob, int NumberOfResource, Resource[] resources,
                                   int[] TimeVector, long Time0, int cut_mode) {
        for (int i = 0; i < NumberOfJob; i++) {
            for (int r = 0; r < NumberOfResource; r++) {
                if (i == 0) {
                    if (r == 0) {
                        job[TimeVector[i]].getStartTime()[r] = Time0;
                    } else {
                        job[TimeVector[i]].getStartTime()[r] = job[TimeVector[i]].getEndTime()[r - 1] +
                                resources[r].getTransportTime()[r - 1];

                    }
                    job[TimeVector[i]].getEndTime()[r] = job[TimeVector[i]].getStartTime()[r] +
                            resources[r].getSetTime()[0][TimeVector[i]] +
                            job[TimeVector[i]].getProcedureTime()[r];
                } else {
                    if (r == 0) {
                        job[TimeVector[i]].getStartTime()[r] = job[TimeVector[i - 1]].getEndTime()[r];
                    } else {
                        job[TimeVector[i]].getStartTime()[r] = job[TimeVector[i]].getEndTime()[r - 1] +
                                resources[r].getTransportTime()[r - 1] +
                                job[TimeVector[i - 1]].getEndTime()[r];

                    }
                    job[TimeVector[i]].getEndTime()[r] = job[TimeVector[i]].getStartTime()[r] +
                            resources[r].getSetTime()[0][TimeVector[i]] +
                            job[TimeVector[i]].getProcedureTime()[r];
                }
                OperationLoaderWithMode(job[TimeVector[i]].getStartTime()[r], job[TimeVector[i]].getEndTime()[r], resources, r, cut_mode);
            }
        }
    }

    public int OperationLoaderWithMode(long StarTime, long EndTime, Resource[] resources, int NumberOfResource, int cut_mode) {
        int returnValue;
        if (cut_mode == 1) {
            returnValue = algorithm.OperationLoaderWithoutCut(StarTime, EndTime, resources, NumberOfResource);
        } else {
            returnValue = algorithm.OperationLoaderWithCut(StarTime, EndTime, resources, NumberOfResource);
        }
        return returnValue;
    }

    public int[] CreateTimeVector(int NumberOfJob) {
        int[] vector = new int[NumberOfJob];
        for (int i = 0; i < NumberOfJob; i++) {
            vector[i] = i;
        }
        return vector;
    }
}
