package de.devtastisch.particleapi.api.matrix.action;

import de.devtastisch.particleapi.api.matrix.MatrixAction;
import de.devtastisch.particleapi.api.matrix.MatrixLocation;
import de.devtastisch.particleapi.api.matrix.Vector3D;
import de.devtastisch.particleapi.api.particle.ParticleEffect;
import lombok.AllArgsConstructor;
import lombok.Data;
import de.devtastisch.particleapi.api.matrix.Matrix3D;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.awt.*;

/**
 * Implementation from {@link MatrixAction} to draw a line between two relative locations in a {@link Matrix3D}
 */
@RequiredArgsConstructor
@Data
public class DrawLine implements MatrixAction {

    private final ParticleEffect particleEffect;
    private final MatrixLocation location1;
    private final MatrixLocation location2;
    private double resolution = 10;

    @Override
    public void execute(World world, Matrix3D matrix3D) {

        Vector3D v1 = new Vector3D(location1.toVector());
        Vector3D v2 = new Vector3D(location2.toVector());
        Vector3D u = v2.sub(v1).div(resolution);

        for (double i = 1; i <= resolution; i++) {
            Vector3D x = v1.add(u.multiply(i));
            matrix3D.paint(world, new MatrixLocation(x.getX(), x.getY(), x.getZ()), this.particleEffect);
        }

    }

}
