package objectdata;

import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solid
{

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

}
