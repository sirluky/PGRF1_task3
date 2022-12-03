package objectdata;

import transforms.Point3D;

public class Cuboid extends Solid
{
    public Cuboid()
    {
        // Same as cube but height is 2 times bigger
        vBuffer.add(new Point3D( 0,  0,  0));
        vBuffer.add(new Point3D( 1,  0,  0));
        vBuffer.add(new Point3D( 1,  2,  0));
        vBuffer.add(new Point3D( 0,  2,  0));
        vBuffer.add(new Point3D( 0,  0,  1));
        vBuffer.add(new Point3D( 1,  0,  1));
        vBuffer.add(new Point3D( 1,  2,  1));
        vBuffer.add(new Point3D( 0,  2,  1));


        addIndices(0, 1, 1, 2, 2, 3, 3, 0, 4, 5, 5, 6, 6, 7, 7, 4, 7, 3, 4, 0, 5, 1, 2, 6);
    }
}

