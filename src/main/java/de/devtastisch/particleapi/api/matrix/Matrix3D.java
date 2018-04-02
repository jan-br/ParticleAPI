package de.devtastisch.particleapi.api.matrix;

import de.devtastisch.particleapi.api.particle.ParticleEffect;
import de.devtastisch.particleapi.api.scheduler.Scheduler;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.function.Consumer;

public interface Matrix3D {

    /**
     * @return the matrix width
     */
    int getWidth();

    /**
     * @return the matrix height
     */
    int getHeight();

    /**
     * @return the matrix depth
     */
    int getDepth();

    /**
     * @return the matrix location
     */
    Vector getLocation();

    /**
     * Draws particles in a relative coordinate system.
     *
     * @param world World where particles should be visible
     * @param matrixLocation Relative location in the matrix
     * @param particleEffect Type of particle to spawn
     */
    void paint(World world, MatrixLocation matrixLocation, ParticleEffect particleEffect);

    /**
     * @param world constructor param for {@link Location}
     * @param matrixLocation relative location in the matrix
     * @return absolute location in dependence on the matrix location and the given relative location
     */
    Location getMatrixLocationAsBukkit(World world, MatrixLocation matrixLocation);

    /**
     * @param matrixLocation relative location to check
     * @return if the given location is inside the matrix bounds
     */
    boolean isInside(MatrixLocation matrixLocation);

    /**
     * Some examples for {@link MatrixAction} can be found in de.devtastisch.particleapi.api.matrix.action
     * @param world World where particles should be visible
     * @param action action to be executed
     */
    void executeAction(World world, MatrixAction action);

    /**
     * Some examples for {@link MatrixAction} can be found in de.devtastisch.particleapi.api.matrix.action
     * @param world World where particles should be visible
     * @param actions actions to be executed
     */
    void executeActions(World world, MatrixAction... actions);

    /**
     * Schedules a task
     * @param runnable task to execute
     * @param delay the delay to wait after every scheduled tick
     * @return generated {@link Scheduler} object
     */
    Scheduler schedule(Runnable runnable, int delay);

    /**
     * Velocity to set to the whole matrix.
     * Will be handeled in handleVelocity()
     * @param velocity the velocity to set
     */
    void setVelocity(Vector velocity);

    /**
     * Will handle the matrix velocity.
     * Will be called internal
     */
    void handleVelocity();

    /**
     * @param entity Entity the whole matrix should follow
     * @param distance the distance when matrix is triggered to fly to the entity
     */
    void setFollowing(Entity entity, int distance);

    /**
     * @return the matrix velocity
     */
    Vector getVelocity();

}
