package de.devtastisch.particleapi.api.matrix.action;

import lombok.AllArgsConstructor;
import lombok.Data;
import de.devtastisch.particleapi.api.matrix.Matrix3D;
import de.devtastisch.particleapi.api.matrix.MatrixAction;
import de.devtastisch.particleapi.api.matrix.MatrixLocation;
import de.devtastisch.particleapi.api.particle.ParticleEffect;
import org.bukkit.World;

/**
 * Implementation from {@link MatrixAction} to draw a triangle between three relative locations in a {@link Matrix3D}
 */
@Data
@AllArgsConstructor
public class DrawTriangle implements MatrixAction {

    private ParticleEffect particleEffect;
    private MatrixLocation location1;
    private MatrixLocation location2;
    private MatrixLocation location3;

    @Override
    public void execute(World world, Matrix3D matrix3D) {
        matrix3D.executeActions(world,
                new DrawLine(getParticleEffect(), getLocation1(), getLocation2()),
                new DrawLine(getParticleEffect(), getLocation2(), getLocation3()),
                new DrawLine(getParticleEffect(), getLocation3(), getLocation1())
        );
    }

}
