package cz.uhk.pgrf1.task3.rasterops;

import cz.uhk.pgrf1.task3.objectdata.Solid;
import cz.uhk.pgrf1.task3.rasterdata.RasterImage;
import transforms.*;

import java.util.List;

public class RenderLine<P> {



    private Liner<P> liner;

    private Mat4PerspRH proj;
    private Mat4 orthoRH;
    private Solid solid;
    private P pixelValue;
    private RasterImage<P> img;
    private Mat4 view;

    public RenderLine(final RasterImage<P> img, final P pixelValue,
                      Liner<P> liner, Mat4 view, Mat4PerspRH proj) {
        this.liner = liner;
        this.proj = proj;
        this.view = view;
        this.img = img;
        this.pixelValue = pixelValue;
    }

    public RenderLine(RasterImage<P> img, final P pixelValue, Liner<P> liner, Mat4 view, Mat4OrthoRH orthoRH) {
        this.img =  img;
        this.pixelValue = pixelValue;
        this.liner = liner;
        this.view = view;
        this.orthoRH = orthoRH;
    }


    public void setView(Mat4 view) {
        this.view = view;
    }

    public void renderScene(List<Solid> scene) {
        for (Solid object : scene) {
            renderSolid(object);
        }
    }

    private Vec3D transform(Vec3D point) {
        return point.mul(new Vec3D(1, -1, 1))
                .add(new Vec3D(1, 1, 0))
                .mul(new Vec3D(img.getWidth() / 2, img.getHeight() / 2, 1));

    }

    public void renderSolid(Solid solid) {


        Mat4 mv = solid.getModel().mul(view).mul(proj);

        for (int i = 0; i < solid.getiBuffer().size(); i = i + 2) {


            int i1 = solid.getiBuffer().get(i);
            int i2 = solid.getiBuffer().get(i + 1);

            Point3D p1 = solid.getvBuffer().get(i1);
            Point3D p2 = solid.getvBuffer().get(i2);

            p1 = p1.mul(mv);
            p2 = p2.mul(mv);

            Point3D a = p1.mul(1 / p1.getW());
            Point3D b = p2.mul(1 / p2.getW());

            Vec3D v1 = transform(new Vec3D(a));
            Vec3D v2 = transform(new Vec3D(b));

            P pixel = pixelValue;
            if(!solid.getPixel(i).isEmpty()) {
                pixel = (P) solid.getPixel(i).get();
            }

            liner.drawLine(img, (int) Math.round(v1.getX()), (int) Math.round(v1.getY()),
                    (int) Math.round(v2.getX()), (int) Math.round(v2.getY()), pixel);

        }
    }


    public void clearIndices() {
        solid.getiBuffer().clear();
    }

    public void setColor(int color) {
        this.pixelValue = (P) Integer.valueOf(color);
    }
}
