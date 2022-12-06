package cz.uhk.pgrf1.task3.objectdata;

import transforms.*;

import javax.swing.text.html.Option;
import java.util.*;

public class Solid {

    public List<Point3D> vBuffer = new ArrayList<>();
    public List<Integer> iBuffer = new ArrayList<>();

    public HashMap<Integer, Integer> pixelBuffer = new HashMap<>();

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

    public Optional<Integer> getPixel(Integer key) {
        if(pixelBuffer.containsKey(key)) {
            return Optional.of(pixelBuffer.get(key));
        } else {
            return Optional.empty();
        }
    }

    public void addPixel(Integer key, Integer pixel) {
        pixelBuffer.put(key, pixel);
    }

    public void addIndices(Integer... indices) {
        iBuffer.addAll(Arrays.asList(indices));
    }
     

    public void rotate(double angle, char type) {
        // Rotate around axis
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

