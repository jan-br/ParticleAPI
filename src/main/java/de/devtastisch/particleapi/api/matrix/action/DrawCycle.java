package de.devtastisch.particleapi.api.matrix.action;

import de.devtastisch.particleapi.api.matrix.MatrixAction;
import de.devtastisch.particleapi.api.matrix.MatrixLocation;
import de.devtastisch.particleapi.api.particle.ParticleEffect;
import lombok.AllArgsConstructor;
import lombok.Data;
import de.devtastisch.particleapi.api.matrix.Matrix3D;
import org.bukkit.World;

/**
 * Implementation from {@link MatrixAction} to draw a cycle on a relative location in {@link Matrix3D}
 */
@AllArgsConstructor
@Data
public class DrawCycle implements MatrixAction {

    private MatrixLocation matrixLocation;
    private ParticleEffect particleEffect;
    private double radius = 1;
    private double resolutionDegrees = 1;

    @Override
    public void execute(World world, Matrix3D matrix3D) {
        for(double i = 0; i <= 360; i+=resolutionDegrees){
            matrix3D.paint(world, matrixLocation.clone().add(new MatrixLocation(Math.sin(Math.toRadians(i)), Math.cos(Math.toRadians(i)), 0)), getParticleEffect());
        }
    }

}
