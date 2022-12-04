package cz.uhk.pgrf1.task3.objectdata;

import transforms.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solid {

    public List<Point3D> vBuffer = new ArrayList<>();
    public List<Integer> iBuffer = new ArrayList<>();

    private Mat4 model = new Mat4Identity();

    public Mat4 getModel() {
        return model;
    }

    public List<Point3D> getvBuffer() {
        return vBuffer;
    }

    public List<Integer> getiBuffer() {
        return iBuffer;
    }

    public void addIndices(Integer... indices) {
        iBuffer.addAll(Arrays.asList(indices));
    }

    public void rotate(double angle, char type) {
        // Rotate around Z axis
        switch (type) {
            case 'Z':
                model = model.mul(new Mat4RotZ(angle));
                break;
            case 'X':
                model = model.mul(new Mat4RotX(angle));
                break;
            case 'Y':
                model = model.mul(new Mat4RotY(angle));
                break;
        }
        model = model.mul(new Mat4RotZ(angle));
    }




}

