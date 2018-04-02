package de.devtastisch.particleapi;

import de.devtastisch.particleapi.api.scheduler.Scheduler;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class ParticleAPI extends JavaPlugin {

    @Getter
    private static ParticleAPI instance;
    private ExecutorService executorService;

    /**
     * Preparing everything... *-*
     */
    @Override
    public void onEnable() {
        instance = this;
        this.executorService = Executors.newCachedThreadPool();
    }

    /**
     * Just cleaning up a few things for a server reload c:
     */
    @Override
    public void onDisable() {
        synchronized (Scheduler.getSchedulers()) {
            Scheduler.getSchedulers().forEach(Scheduler::cancel);
        }
    }
}

