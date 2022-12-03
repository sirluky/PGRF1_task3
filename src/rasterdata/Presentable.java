package rasterdata;



/**
 * Represents a data structure capable of presenting itself in a device on the given type
 * @param <D> device type
 */
public interface Presentable<D> {

    /**
     * Present itself in the given device
     * @param device
     * @return
     */
    D present( final D device);
}
