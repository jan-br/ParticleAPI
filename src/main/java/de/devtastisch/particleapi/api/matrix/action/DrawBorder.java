package de.devtastisch.particleapi.api.matrix.action;

import de.devtastisch.particleapi.api.matrix.MatrixAction;
import de.devtastisch.particleapi.api.matrix.MatrixLocation;
import de.devtastisch.particleapi.api.particle.ParticleEffect;
import lombok.AllArgsConstructor;
import lombok.Data;
import de.devtastisch.particleapi.api.matrix.Matrix3D;
import org.bukkit.World;

/**
 * Implementation from {@link MatrixAction} to draw the border in a {@link Matrix3D}
 */
@AllArgsConstructor
@Data
public class DrawBorder implements MatrixAction {

    private ParticleEffect particleEffect;

    @Override
    public void execute(World world, Matrix3D matrix3D) {
        int w = matrix3D.getWidth() / 2;
        int h = matrix3D.getHeight() / 2;
        int d = matrix3D.getDepth() / 2;

        matrix3D.executeActions(
                world,
                new DrawLine(getParticleEffect(), new MatrixLocation(-w, -h, -d), new MatrixLocation(w, -h, -d)),
                new DrawLine(getParticleEffect(), new MatrixLocation(-w, -h, d), new MatrixLocation(w, -h, d)),
                new DrawLine(getParticleEffect(), new MatrixLocation(-w, h, -d), new MatrixLocation(w, h, -d)),
                new DrawLine(getParticleEffect(), new MatrixLocation(-w, h, d), new MatrixLocation(w, h, d)),


                new DrawLine(getParticleEffect(), new MatrixLocation(w, -h, -d), new MatrixLocation(w, h, -d)),
                new DrawLine(getParticleEffect(), new MatrixLocation(-w, -h, -d), new MatrixLocation(-w, h, -d)),
                new DrawLine(getParticleEffect(), new MatrixLocation(-w, -h, d), new MatrixLocation(-w, h, d)),
                new DrawLine(getParticleEffect(), new MatrixLocation(w, -h, d), new MatrixLocation(w, h, d)),

                new DrawLine(getParticleEffect(), new MatrixLocation(-w, -h, -d), new MatrixLocation(-w, -h, d)),
                new DrawLine(getParticleEffect(), new MatrixLocation(w, -h, -d), new MatrixLocation(w, -h, d)),
                new DrawLine(getParticleEffect(), new MatrixLocation(-w, h, -d), new MatrixLocation(-w, h, d)),
                new DrawLine(getParticleEffect(), new MatrixLocation(w, h, -d), new MatrixLocation(w, h, d))

        );


    }

}
