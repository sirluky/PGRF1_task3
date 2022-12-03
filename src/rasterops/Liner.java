package rasterops;

import rasterdata.RasterImage;

/**
 * Represents an algorithm for drawing lines on a {@link rasterdata.RasterImage} with pixels of the given type
 * @param <P> pixel type
 */
public interface Liner<P> {

    /**
     * Draws a line onto the given {@link  RasterImage} with the given pixel
     * @param img {@link RasterImage} to be used for drawing
     * @param c1 column address of the start point
     * @param r1 row address of the start point
     * @param c2 column address of the end point
     * @param r2 row address of the end point
     * @param pixelValue new pixel value
     */
    void drawLine(final RasterImage<P> img, final int c1,
                  final int r1, final int c2, final int r2, final P pixelValue);
}
