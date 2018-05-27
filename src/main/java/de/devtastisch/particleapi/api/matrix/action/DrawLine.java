package de.devtastisch.particleapi.api.matrix.action;

import de.devtastisch.particleapi.api.matrix.MatrixAction;
import de.devtastisch.particleapi.api.matrix.MatrixLocation;
import de.devtastisch.particleapi.api.particle.ParticleEffect;
import lombok.AllArgsConstructor;
import lombok.Data;
import de.devtastisch.particleapi.api.matrix.Matrix3D;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.awt.*;

/**
 * Implementation from {@link MatrixAction} to draw a line between two relative locations in a {@link Matrix3D}
 */
@AllArgsConstructor
@Data
public class DrawLine implements MatrixAction {

    private ParticleEffect particleEffect;
    private MatrixLocation location1;
    private MatrixLocation location2;

    @Override
    public void execute(World world, Matrix3D matrix3D) {

        MatrixLocation dif = location1.clone().sub(location2);
        for(double i = 0; i <= 1; i+=0.02){
            matrix3D.paint(world, location1.clone().add(dif.clone().multiply(i)), this.particleEffect);
        }

    }

}
