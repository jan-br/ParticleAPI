package de.devtastisch.particleapi.api.matrix;

import com.google.common.util.concurrent.AtomicDouble;
import de.devtastisch.particleapi.ParticleAPI;
import de.devtastisch.particleapi.api.particle.ParticleEffect;
import de.devtastisch.particleapi.api.scheduler.Scheduler;
import lombok.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.security.auth.callback.Callback;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * This is an implementation from {@link Matrix3D}
 * It is capeable to rotate the whole Matrix using Setters for xRot, yRot and zRot.
 * Hint: The Axis won't move while rotating the matrix like you would expect it in OpenGL applications.
 */
@AllArgsConstructor
@Getter
@Setter
public class RotateableMatrix3D implements Matrix3D {

    private AtomicDouble xRot;
    private AtomicDouble yRot;
    private AtomicDouble zRot;
    private AtomicDouble xPiv;
    private AtomicDouble yPiv;
    private AtomicDouble zPiv;
    private int height;
    private int width;
    private int depth;
    private Vector location;
    private double scale;
    private Vector velocity;
    private Entity following;
    private int followingDistance;
    private final List<UUID> target = new ArrayList<>();


    /**
     * Constructs a {@link RotateableMatrix3D}
     *
     * @param height the matrix height
     * @param width  the matrix width
     * @param depth  the matrix depth
     * @param x      the matrix absolute x coordinate
     * @param y      the matrix absolute y coordinate
     * @param z      the matrix absolute z coordinate
     */
    public RotateableMatrix3D(int height, int width, int depth, double x, double y, double z) {
        this(new AtomicDouble(), new AtomicDouble(), new AtomicDouble(), new AtomicDouble(), new AtomicDouble(), new AtomicDouble(), height, width, depth, new Vector(x, y, z), 1, new Vector(0, 0, 0), null, 5);
        this.schedule(() -> {
            if (this.getFollowing() != null && this.getFollowing().isValid()) {
                if (this.getFollowing().getLocation().distanceSquared(this.getMatrixLocationAsBukkit(this.getFollowing().getWorld(), new MatrixLocation(0, 0, 0))) > 16) {
                    this.setVelocity(this.getFollowing().getLocation().add(0, 2, 0).toVector().subtract(this.getLocation()));
                }
                this.handleVelocity();
            }
        }, 20);
    }

    public void addTarget(Player player) {
        synchronized (target) {
            if (!target.contains(player.getUniqueId())) {
                target.add(player.getUniqueId());
            }
        }
    }

    public void clearTarget() {
        synchronized (target) {
            this.target.clear();
        }
    }

    public float getXRot() {
        return ((float) xRot.get());
    }

    public float getYRot() {
        return ((float) yRot.get());
    }

    public float getZRot() {
        return ((float) zRot.get());
    }

    public float getXPiv() {
        return ((float) xPiv.get());
    }

    public float getYPiv() {
        return ((float) yPiv.get());
    }

    public float getZPiv() {
        return ((float) zPiv.get());
    }

    public void setXRot(float rot) {
        xRot.set(rot);
    }

    public void setYRot(float rot) {
        yRot.set(rot);
    }

    public void setZRot(float rot) {
        zRot.set(rot);
    }

    public void setXPiv(float rot) {
        xPiv.set(rot);
    }

    public void setYPiv(float rot) {
        yPiv.set(rot);
    }

    public void setZPiv(float rot) {
        zPiv.set(rot);
    }

    public void removeTarget(Player player) {
        synchronized (target) {
            if (target.contains(player.getUniqueId())) {
                target.remove(player.getUniqueId());
            }
        }
    }

    public void handleVelocity() {
        this.location.add(this.velocity.clone().multiply(0.03));
        this.velocity.multiply(0.5);
    }

    public void paint(World world, MatrixLocation matrixLocation, ParticleEffect particleEffect) {
        this.paintVectored(world, matrixLocation, new Vector(0, 0, 0), particleEffect);
    }

    public void paint(World world, Color color, MatrixLocation matrixLocation, ParticleEffect particleEffect) {
        if (!isInside(matrixLocation)) {
            return;
        }
        if (target.isEmpty()) {
            particleEffect.spawn(getMatrixLocationAsBukkit(world, matrixLocation), true, color);
        } else {
            new ArrayList<>(this.target).forEach(uuid -> {
                Player player = Bukkit.getPlayer(uuid);
                if (player != null && player.isOnline()) {
                    particleEffect.spawn(player, getMatrixLocationAsBukkit(world, matrixLocation), true, color);
                } else {
                    synchronized (this.target) {
                        if (this.target.contains(uuid)) {
                            this.target.remove(uuid);
                        }
                    }
                }
            });
        }
    }


    public void paintVectored(World world, MatrixLocation matrixLocation, Vector vector, ParticleEffect particleEffect) {
        if (!isInside(matrixLocation)) {
            return;
        }
        if (target.isEmpty()) {
            particleEffect.spawn(getMatrixLocationAsBukkit(world, matrixLocation), rotateVectorToMatrix(vector), true);
        } else {
            new ArrayList<>(this.target).forEach(uuid -> {
                Player player = Bukkit.getPlayer(uuid);
                if (player != null && player.isOnline()) {
                    particleEffect.spawn(player, getMatrixLocationAsBukkit(world, matrixLocation), rotateVectorToMatrix(vector), true);
                } else {
                    synchronized (this.target) {
                        if (this.target.contains(uuid)) {
                            this.target.remove(uuid);
                        }
                    }
                }
            });
        }
    }

    public Vector rotateVectorToMatrix(Vector vector){
        return rotateVector(vector, getXRot(), getYRot(), getZRot());
    }

    public Vector rotateVector(Vector oldVector, float x, float y, float z) {
        Vector vector = new Vector(oldVector.getX(), oldVector.getY(), oldVector.getZ());
        if (x != 0.0F) {
            double ry = (vector.getY() * Math.cos(Math.toRadians(x))) - (vector.getZ() * Math.sin(Math.toRadians(x)));
            double rz = (vector.getY() * Math.sin(Math.toRadians(x))) + (vector.getZ() * Math.cos(Math.toRadians(x)));
            vector.setY(ry);
            vector.setZ(rz);
        }
        if (y != 0.0F) {
            double rx = (vector.getZ() * Math.sin(Math.toRadians(y))) + (vector.getX() * Math.cos(Math.toRadians(y)));
            double rz = (vector.getZ() * Math.cos(Math.toRadians(y))) - (vector.getX() * Math.sin(Math.toRadians(y)));
            vector.setZ(rz);
            vector.setX(rx);
        }
        if (z != 0.0F) {
            double rx = (vector.getX() * Math.cos(Math.toRadians(z))) - (vector.getY() * Math.sin(Math.toRadians(z)));
            double ry = (vector.getX() * Math.sin(Math.toRadians(z))) + (vector.getY() * Math.cos(Math.toRadians(z)));
            vector.setX(rx);
            vector.setY(ry);
        }
        System.out.println(vector);
        return vector;
    }

    /**
     * @param world          constructor param for {@link Location}
     * @param matrixLocation relative location in the matrix
     * @return absolute location in dependence to xRot, yRot, zRot
     */
    public Location getMatrixLocationAsBukkit(@NonNull World world, MatrixLocation matrixLocation) {
        Vector vector = matrixLocation.toVector();
        vector.add(new Vector(this.getXPiv(), this.getYPiv(), this.getZPiv()));
        vector = rotateVectorToMatrix(vector);

        vector.add(getLocation());

        return new Location(world, vector.getX(), vector.getY(), vector.getZ());
    }

    /**
     * @param matrixLocation relative location in the matrix
     * @return Vector in dependence to xRot, yRot, zRot
     */
    public Vector getMatrixLocationAsVector(MatrixLocation matrixLocation) {
        Vector vector = matrixLocation.toVector();
        vector.add(new Vector(this.getXPiv(), this.getYPiv(), this.getZPiv()));
        vector = rotateVectorToMatrix(vector);

        vector.add(getLocation());
        return vector;
    }

    public void executeActions(World world, MatrixAction... actions) {
        for (MatrixAction action : actions) {
            this.executeAction(world, action);
        }
    }

    public Scheduler schedule(Runnable runnable, int delay) {
        return new Scheduler(delay) {
            @Override
            public void run() {
                runnable.run();
            }
        };
    }

    public Scheduler schedule(Consumer<Scheduler> consumer, int delay) {
        return new Scheduler(delay) {
            @Override
            public void run() {
                consumer.accept(this);
            }
        };
    }

    public void executeAction(World world, MatrixAction action) {
        ParticleAPI.getInstance().getExecutorService().execute(() -> action.execute(world, this));
    }

    public boolean isInside(MatrixLocation matrixLocation) {
        return matrixLocation.getX() >= getWidth() / -2 && matrixLocation.getX() <= getWidth() / 2 &&
                matrixLocation.getY() >= getHeight() / -2 && matrixLocation.getY() <= getHeight() / 2 &&
                matrixLocation.getZ() >= getDepth() / -2 && matrixLocation.getZ() <= getDepth() / 2;
    }

    public void setFollowing(Entity entity, int distance) {
        this.setFollowing(entity);
        this.setFollowingDistance(distance);
    }

    public void rotateMatrix(Vector vector){
        this.rotateMatrix(((float) vector.getX()), ((float) vector.getY()), ((float) vector.getZ()));
    }

    public void rotateMatrix(float x, float y, float z){
        this.setXRot(this.getXRot() + x);
        this.setYRot(this.getYRot() + y);
        this.setZRot(this.getZRot() + z);
    }


    /*
    Not implemented yet
     */
    public void drawModel(World world, MatrixLocation matrixLocation, Model model) {
        model.draw(world, matrixLocation, this);
    }

}
