package cz.uhk.pgrf1.task3.objectdata;

import transforms.Point3D;

public class Axis extends Solid {
    public Axis() {
        // Axis
        vBuffer.add(new Point3D(0, 0, 0));
        vBuffer.add(new Point3D(5, 0, 0));
        vBuffer.add(new Point3D(0, 5, 0));
        vBuffer.add(new Point3D(0, 0, 5));

        addIndices(0, 1, 0, 2, 0, 3);
        addPixel(0,0x0000ff);
        addPixel(2,0xff0000);
        addPixel(4,0x00ff00);

    }
}