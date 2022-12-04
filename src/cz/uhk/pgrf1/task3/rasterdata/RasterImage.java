package cz.uhk.pgrf1.task3.rasterdata;


import java.util.Optional;

/**
 * Represents 2D raster data with pixels of the given type
 *
 * @param <P> pixel type
 */
public interface RasterImage<P> {

    /**
     * Returns the number of columns
     *
     * @return
     */
    int getWidth();

    /**
     * Returns the number of rows
     *
     * @return
     */
    int getHeight();

    /**
     * Returns the pixel value at the specified address
     *
     * @param c column address
     * @param r row address
     * @return
     */
    Optional<P> getPixel(final int c, final int r);


    /**
     * Overrides the current pixel value with the new one specified by the given pixel address
     *
     * @param c        column address
     * @param r        row address
     * @param newValue new pixel value
     */
    void setPixel(final int c, final int r, final   P newValue);

    /**
     * Changes all pixel's values to the given value
     *
     * @param newValue
     */
    void clear(final   P newValue);


}