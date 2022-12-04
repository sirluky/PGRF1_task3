package cz.uhk.pgrf1.task3.objectdata;

import transforms.Point3D;

public class Pyramid extends Solid
{

        public Pyramid()
        {
            vBuffer.add(new Point3D( 0,  0,  0));
            vBuffer.add(new Point3D( 1,  0,  0));
            vBuffer.add(new Point3D( 1,  1,  0));
            vBuffer.add(new Point3D( 0,  1,  0));
            vBuffer.add(new Point3D( 0.5,  0.5,  1));


           addIndices(0,1,1,2,2,3,3,0,4,0,4,1,4,2,4,3);
        }
}
 
