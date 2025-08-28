package io.github.blockneko11.nekocore.util.schedule;

import dev.architectury.event.events.common.TickEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class TaskScheduler {
    private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();
    private static final List<DelayedTask> RUN_LATER_TASKS = Collections.synchronizedList(new ArrayList<>());
//    private static final List<IntervalTask> RUN_INTERVAL_TASKS = Collections.synchronizedList(new ArrayList<>());

    public static void runTask(Runnable task) {
        task.run();
    }

    public static CompletableFuture<Void> runTaskAsync(Runnable task) {
        return CompletableFuture.supplyAsync(() -> {
            task.run();
            return null;
        }, EXECUTOR);
    }

    public static void runTaskLater(Runnable task, int delayTicks) {
        RUN_LATER_TASKS.add(new DelayedTask(task, delayTicks));
    }

    public static CompletableFuture<Void> runTaskLaterAsync(Runnable task) {
        return TaskScheduler.runTaskLaterAsync(task, DelayedTask.DEFAULT_DELAY_TICKS);
    }

    public static CompletableFuture<Void> runTaskLaterAsync(Runnable task, int delayTicks) {
        return CompletableFuture.supplyAsync(() -> {
            TaskScheduler.runTaskLater(task, delayTicks);
            return null;
        }, EXECUTOR);
    }

//    public static void runTaskInterval(Runnable task, int intervalTicks) {
//        TaskScheduler.runTaskInterval(task, intervalTicks, IntervalTask.DEFAULT_DELAY_TICKS);
//    }
//
//    public static void runTaskInterval(Runnable task, int intervalTicks, int delayTicks) {
//        RUN_INTERVAL_TASKS.add(new IntervalTask(task, intervalTicks, delayTicks));
//    }

    public static void init() {
        TickEvent.SERVER_POST.register(server -> {
            RUN_LATER_TASKS.forEach(DelayedTask::onTick);
            RUN_LATER_TASKS.removeIf(DelayedTask::shouldBeRemoved);
        });
    }

    private TaskScheduler() {
    }
}
