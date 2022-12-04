package cz.uhk.pgrf1.task3.objectdata;

import transforms.Point3D;

public class Axis extends Solid {
    public Axis() {
        // Axis
        vBuffer.add(new Point3D(0, 0, 0));
        vBuffer.add(new Point3D(1, 0, 0));
        vBuffer.add(new Point3D(0, 1, 0));
        vBuffer.add(new Point3D(0, 0, 1));
        addIndices(0, 1, 0, 2, 0, 3);
    }

}
