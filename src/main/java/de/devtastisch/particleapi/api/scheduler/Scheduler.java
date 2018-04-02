package de.devtastisch.particleapi.api.scheduler;

import lombok.Getter;
import de.devtastisch.particleapi.ParticleAPI;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;

@Getter
public abstract class Scheduler {

    @Getter
    private static final List<Scheduler> schedulers = new CopyOnWriteArrayList<>();
    private int delay;
    private Future future;

    protected Scheduler(int delay) {
        synchronized (schedulers){
            schedulers.add(this);
        }
        this.delay = delay;

        this.future = ParticleAPI.getInstance().getExecutorService().submit(() -> {
            while (true){
                Scheduler.this.run();
                Thread.sleep(delay);
            }
        });
    }

    public abstract void run();

    public void cancel(){
        this.getFuture().cancel(true);
    }

}
