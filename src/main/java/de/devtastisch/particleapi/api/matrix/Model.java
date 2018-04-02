package de.devtastisch.particleapi.api.matrix;

import org.bukkit.World;

public interface Model {

    public void draw(World world, MatrixLocation matrixLocation, Matrix3D matrix3D);

}
