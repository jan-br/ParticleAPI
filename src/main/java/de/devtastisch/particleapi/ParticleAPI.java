package de.devtastisch.particleapi;

import de.devtastisch.particleapi.api.matrix.MatrixLocation;
import de.devtastisch.particleapi.api.matrix.RotateableMatrix3D;
import de.devtastisch.particleapi.api.matrix.action.DrawBorder;
import de.devtastisch.particleapi.api.matrix.action.DrawCycle;
import de.devtastisch.particleapi.api.matrix.action.DrawLine;
import de.devtastisch.particleapi.api.matrix.action.DrawTriangle;
import de.devtastisch.particleapi.api.particle.ParticleEffect;
import de.devtastisch.particleapi.api.scheduler.Scheduler;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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

        getCommand("test").setExecutor((commandSender, command, label, args) -> {

            Player player = ((Player) commandSender);
            RotateableMatrix3D matrix3D = new RotateableMatrix3D(10, 10, 10, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());


            matrix3D.schedule(new Runnable() {
                int scale = 0;

                public void run() {

                    matrix3D.executeActions(player.getWorld(),
                            new DrawBorder(ParticleEffect.CRIT_MAGIC),
                            new DrawCycle(ParticleEffect.CRIT_MAGIC, new MatrixLocation(0, 0, 0), 5, 1),
                            new DrawLine(ParticleEffect.CRIT_MAGIC, new MatrixLocation(-5, -5, -5), new MatrixLocation(5, 5, 5)),
                            new DrawTriangle(ParticleEffect.FLAME, new MatrixLocation(-5, -5, -5), new MatrixLocation(5, -5, -5), new MatrixLocation(0, 5, 5));

                    matrix3D.paint(player.getWorld(), new MatrixLocation(0, 0, 0), ParticleEffect.BARRIER);
                }
            }, 200);

            return true;
        });


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

