package de.devtastisch.particleapi.api.matrix;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.util.Vector;

@AllArgsConstructor
@Data
public class MatrixLocation {

    private double x;
    private double y;
    private double z;

    public Vector toVector() {
        return new Vector(x, y, z);
    }

    public MatrixLocation add(MatrixLocation matrixLocation) {
        this.x += matrixLocation.getX();
        this.y += matrixLocation.getY();
        this.z += matrixLocation.getZ();
        return this;
    }

    public MatrixLocation clone(){
        return new MatrixLocation(this.x, this.y, this.z);
    }

}
