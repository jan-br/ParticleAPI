package de.devtastisch.particleapi.api.matrix;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.util.Vector;

@Data
@AllArgsConstructor
public class Vector3D {

    private double x;
    private double y;
    private double z;

    public Vector3D(Vector vector){
        this(vector.getX(), vector.getY(), vector.getZ());
    }

    public void add(Vector vector){
        this.x += vector.getX();
        this.y += vector.getY();
        this.z += vector.getZ();
    }

    public Vector3D rotateVector(double x, double y, double z) {
        if (x != 0.0F) {
            double newY = this.y * Math.cos(Math.toRadians(x)) - this.z * Math.sin(Math.toRadians(x));
            double newZ = this.y * Math.sin(Math.toRadians(x)) + this.z * Math.cos(Math.toRadians(x));

            setY(newY);
            setZ(newZ);
        }

        if (y != 0.0F) {
            double newX = this.z * Math.sin(Math.toRadians(y)) + this.x * Math.cos(Math.toRadians(y));
            double newZ = this.z * Math.cos(Math.toRadians(y)) - this.x * Math.sin(Math.toRadians(y));
            setX(newX);
            setZ(newZ);
        }

        if (z != 0.0F) {
            double newX = this.x * Math.cos(Math.toRadians(z)) - this.y * Math.sin(Math.toRadians(z));
            double newY = this.x * Math.sin(Math.toRadians(z)) + this.y * Math.cos(Math.toRadians(z));
            setX(newX);
            setY(newY);
        }
        return this;
    }

    public Vector3D div(double t){
        return new Vector3D(this.x / t, this.y / t, this.z / t);
    }

    public Vector3D multiply(double t){
        return new Vector3D(this.x * t, this.y * t, this.z * t);
    }

    public Vector3D add(Vector3D vector3D){
        return new Vector3D(this.x + vector3D.x, this.y + vector3D.y, this.z + vector3D.z);
    }

    public Vector3D sub(Vector3D vector3D){
        return new Vector3D(this.x - vector3D.x, this.y - vector3D.y, this.z - vector3D.z);
    }

    public Vector toBukkit(){
        return new Vector(x, y, z);
    }

}
