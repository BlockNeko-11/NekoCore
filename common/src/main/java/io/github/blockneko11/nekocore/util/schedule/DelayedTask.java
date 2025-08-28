package io.github.blockneko11.nekocore.util.schedule;

public class DelayedTask {
    public static final int DEFAULT_DELAY_TICKS = 20;

    private final Runnable task;
    private final int delayTicks;

    private int started_ticks = 0;
    private boolean shouldRemove = false;

    public DelayedTask(Runnable task, int delayTicks) {
        this.task = task;
        this.delayTicks = delayTicks;
    }

    public void onTick() {
        if (this.started_ticks >= this.delayTicks) {
            this.task.run();
            this.shouldRemove = true;
        }

        this.started_ticks++;
    }

    public boolean shouldBeRemoved() {
        return this.shouldRemove;
    }
}
