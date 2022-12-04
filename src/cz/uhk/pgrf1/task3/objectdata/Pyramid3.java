package cz.uhk.pgrf1.task3.objectdata;

import transforms.Point3D;

public class Pyramid3 extends Solid {
    // Pyramid triangular
                     public Pyramid3() {
                         

                           vBuffer.add(new Point3D(0, 0, 0));
                            vBuffer.add(new Point3D(1, 0, 0));
                            vBuffer.add(new Point3D(0.5, 0.5, 0));
                            vBuffer.add(new Point3D(0.5, 0.5, 1));

                            addIndices(0,1,1,2,2,0,3,0,3,1,3,2);
                            
                     }
}
