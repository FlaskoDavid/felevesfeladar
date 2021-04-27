package logic;

public class Algorithm {
    public void Search(Job[] job, Resource[] resources, int NumberOfJob, int NumberOfResource, int[] TimeVector,
                       double[] priority, int K, int cut_mode, int Loop, int Step, int type) {
        int step, loop;
        int[] actSchedule = new int[NumberOfJob], bestschedule = new int[NumberOfJob], zeroschedule = new int[NumberOfJob], bestneighbourschedule = new int[NumberOfJob];
        double[] actualFunction = new double[K], bestfunction = new double[K], bestneighbourfunction = new double[K];
        Function function = new Function();

        function.CopySearch(zeroschedule, TimeVector, NumberOfJob);
        function.CopySearch(bestschedule, TimeVector, NumberOfJob);
        function.FlowShopSimulation(job, NumberOfJob, NumberOfResource, resources, bestschedule, 0, cut_mode);
        function.Evaluate(job, NumberOfJob, NumberOfResource, bestschedule, bestfunction);

        for (step = 1; step <= Step; step++) {
            for (loop = 1; loop <= Loop; loop++) {
                function.Neighbour(zeroschedule, actSchedule, NumberOfJob, type);
                function.FlowShopSimulation(job, NumberOfJob, NumberOfResource, resources, actSchedule, 0, cut_mode);
                function.Evaluate(job, NumberOfJob, NumberOfResource, actSchedule, actualFunction);
                if (loop == 1) {
                    function.CopyFunction(bestneighbourfunction, actualFunction, K);
                    function.CopySearch(bestneighbourschedule, actSchedule, NumberOfJob);
                } else {
                    if (function.WSumRelativeFunctionValue(bestneighbourfunction, actualFunction, priority, K) < 0) {
                        function.CopyFunction(bestneighbourfunction, actualFunction, K);
                        function.CopySearch(bestneighbourschedule, actSchedule, NumberOfJob);
                    }
                }
            }

            function.CopySearch(zeroschedule, bestneighbourschedule, NumberOfJob);
            if (function.WSumRelativeFunctionValue(bestfunction, bestneighbourfunction, priority, K) < 0) {
                function.CopyFunction(bestfunction, bestneighbourfunction, K);
                function.CopySearch(bestschedule, bestneighbourschedule, NumberOfJob);
            }

        }

        function.CopySearch(TimeVector, bestschedule, NumberOfJob);
    }

    public int OperationLoaderWithoutCut(long StarTime, long EndTime, Resource[] resources, int NumberOfResource) {
        int indexofinterval = 0, foundinterval = -1;
        long newStarTime = StarTime, newEndTime = EndTime, size = EndTime - StarTime;

        while (indexofinterval < resources[NumberOfResource].getNumberOfCal()) {
            if (newStarTime < resources[NumberOfResource].getCal()[indexofinterval].getEnd()) {
                newStarTime = Math.max(newStarTime, resources[NumberOfResource].getCal()[indexofinterval].getStart());
                newEndTime = newStarTime + size;
                if (newEndTime <= resources[NumberOfResource].getCal()[indexofinterval].getEnd()) {
                    foundinterval = indexofinterval;
                    break;
                } else {
                    indexofinterval++;
                    if (indexofinterval > resources[NumberOfResource].getNumberOfCal()) {
                        newStarTime = resources[NumberOfResource].getCal()[indexofinterval - 1].getEnd();
                        newEndTime = newStarTime + size;
                        break;
                    }
                    continue;
                }
            }
            indexofinterval++;
        }
        StarTime = newStarTime;
        EndTime = newEndTime;
        return foundinterval;
    }

    public int OperationLoaderWithCut(long StarTime, long EndTime, Resource[] resources, int NumberOfResource) {
        int indexOfInterval = 0, foundInterval = -1;
        long newStarTime = StarTime, newendtime = EndTime, size = EndTime - StarTime, firstPartStart = -1;
        while (indexOfInterval < resources[NumberOfResource].getNumberOfCal()) {
            if (newStarTime < resources[NumberOfResource].getCal()[indexOfInterval].getEnd()) {
                newStarTime = Math.max(newStarTime, resources[NumberOfResource].getCal()[indexOfInterval].getStart());
                newendtime = newStarTime + size;
                if (firstPartStart == -1) {
                    firstPartStart = newStarTime;
                }
                if (newendtime <= resources[NumberOfResource].getCal()[indexOfInterval].getEnd()) {
                    foundInterval = indexOfInterval;
                    break;
                } else {
                    indexOfInterval++;
                    if (indexOfInterval > resources[NumberOfResource].getNumberOfCal()) {

                        newendtime = newStarTime + size;
                        break;
                    }
                    size -= resources[NumberOfResource].getCal()[indexOfInterval - 1].getEnd() - newStarTime;
                    continue;
                }
            }
            indexOfInterval++;
        }
        if (firstPartStart != -1) {
            StarTime = firstPartStart;
        } else {
            StarTime = newStarTime;
        }
        EndTime = newendtime;
        return foundInterval;
    }
}
