package rasterops;

import rasterdata.RasterImage;

public class TrivialLiner<P> implements Liner<P>{

    @Override
    public void drawLine(final  RasterImage<P> img,  int c1,  int r1,
                         int c2,  int r2, final  P pixelValue) {

        final double k = (r2 - r1) / (double) (c2 - c1);
        final double q = r1 - k * c1;


        if (Math.abs(r2 - r1) < Math.abs(c2 - c1)) {
            if (c2 < c1) {
                int pomocna = c1;
                c1 = c2;
                c2 = pomocna;
            }

            if(c1 == c2) {
                for (int c = r1; c <= r2; c++) {
                    img.setPixel(c1, c, pixelValue);
                }
            } else {

                for (int c = c1; c <= c2; c++) {
                    int r = (int) Math.round(k * c + q);
                    img.setPixel(c, r, pixelValue);
                }
            }

        } else {
            if (r2 < r1) {
                int pomocna = r1;
                r1 = r2;
                r2 = pomocna;
            }


            if(c1 == c2) {
                for (int c = r1; c <= r2; c++) {
                    img.setPixel(c1, c, pixelValue);
                }
            } else {
                for (int c = r1; c <= r2; c++) {
                    int r = (int) Math.round((c - q) / k);
                    img.setPixel(r, c, pixelValue);
                }
            }
        }
    }
 
}