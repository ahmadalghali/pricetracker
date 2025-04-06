package uk.nhsbsa.pricetracker.services;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledFuture;

@Service
public class SchedulingService {

    private final TaskScheduler taskScheduler;

    public SchedulingService(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    public void schedule(Runnable runnable, CronTrigger cronTrigger) {

        ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(runnable, cronTrigger);

        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
    }


}
