package de.devtastisch.particleapi.api.matrix;

import de.devtastisch.particleapi.ParticleAPI;
import de.devtastisch.particleapi.api.particle.ParticleEffect;
import de.devtastisch.particleapi.api.scheduler.Scheduler;
import lombok.*;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.function.Consumer;

/**
 * This is an implementation from {@link Matrix3D}
 * It is capeable to rotate the whole Matrix using Setters for xRot, yRot and zRot.
 * Hint: The Axis won't move while rotating the matrix like you would expect it in OpenGL applications.
 */
@Getter
@Setter
@AllArgsConstructor
public class RotateableMatrix3D implements Matrix3D {

    private float xRot;
    private float yRot;
    private float zRot;
    private int height;
    private int width;
    private int depth;
    private Vector location;
    private double scale;
    private Vector velocity;
    private Entity following;
    private int followingDistance;

    /**
     * Constructs a {@link RotateableMatrix3D}
     * @param height the matrix height
     * @param width the matrix width
     * @param depth the matrix depth
     * @param x the matrix absolute x coordinate
     * @param y the matrix absolute y coordinate
     * @param z the matrix absolute z coordinate
     */
    public RotateableMatrix3D(int height, int width, int depth, double x, double y, double z) {
        this(0, 0, 0, height, width, depth, new Vector(x, y, z), 1, new Vector(0, 0, 0), null, 5);
        this.schedule((matrix3D) -> {
            if (this.getFollowing() != null && this.getFollowing().isValid()) {
                if (this.getFollowing().getLocation().distanceSquared(this.getMatrixLocationAsBukkit(this.getFollowing().getWorld(), new MatrixLocation(0, 0, 0))) > 16) {
                    this.setVelocity(this.getFollowing().getLocation().add(0, 2, 0).toVector().subtract(this.getLocation()));
                }
                this.handleVelocity();
            }
        }, 20);
    }

    public void handleVelocity(){
        this.location.add(this.velocity.clone().multiply(0.03));
        this.velocity.multiply(0.5);
    }

    public void paint(World world, MatrixLocation matrixLocation, ParticleEffect particleEffect) {
        if (!isInside(matrixLocation)) {
            return;
        }
        particleEffect.spawn(getMatrixLocationAsBukkit(world, matrixLocation));

    }

    /**
     *
     * @param world constructor param for {@link Location}
     * @param matrixLocation relative location in the matrix
     * @return absolute location in dependence to xRot, yRot, zRot
     */
    public Location getMatrixLocationAsBukkit(@NonNull World world, MatrixLocation matrixLocation) {
        Vector vector = matrixLocation.toVector();

        if(getXRot() != 0){
            double angle = Math.toRadians(getXRot());
            double dy = (vector.getY() * Math.cos(angle)) - (vector.getZ() *  Math.sin(angle));
            double dz = (vector.getY() * Math.sin(angle)) + (vector.getZ() * Math.cos(angle));
            vector.setY(dy);
            vector.setZ(dz);
        }

        if(getYRot() != 0){
            double angle = Math.toRadians(getYRot());
            double dx = (vector.getX() * Math.cos(angle)) - (vector.getZ() * Math.sin(angle));
            double dz = (vector.getX() * Math.sin(angle)) + (vector.getZ() * Math.cos(angle));
            vector.setX(dx);
            vector.setZ(dz);
        }
        if(getZRot() != 0){
            double angle = Math.toRadians(getZRot());
            double dx = (vector.getX() * Math.cos(angle)) - (vector.getY() * Math.sin(angle));
            double dy = (vector.getX() * Math.sin(angle)) + (vector.getY() * Math.cos(angle));
            vector.setX(dx);
            vector.setY(dy);
        }

        vector.multiply(this.getScale());

        vector.add(getLocation());

        return new Location(world, vector.getX(), vector.getY(), vector.getZ());
    }

    public void executeActions(World world, MatrixAction... actions) {
        for (MatrixAction action : actions) {
            this.executeAction(world, action);
        }
    }

    public Scheduler schedule(Consumer<Matrix3D> consumer, int delay) {
        return new Scheduler(delay) {
            @Override
            public void run() {
                consumer.accept(RotateableMatrix3D.this);
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

    public void setFollowing(Entity entity, int distance){
        this.setFollowing(entity);
        this.setFollowingDistance(distance);
    }


    /*
    Not implemented yet
     */
    public void drawModel(World world, MatrixLocation matrixLocation, Model model){
        model.draw(world, matrixLocation, this);
    }

}
