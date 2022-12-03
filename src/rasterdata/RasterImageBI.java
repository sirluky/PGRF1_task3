package rasterdata;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class RasterImageBI implements RasterImage<Integer>, Presentable<Graphics> {

    private final BufferedImage bufferedImage;

    public RasterImageBI(final int width, final int height) {
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public int getWidth() {
        return bufferedImage.getWidth();
    }

    @Override
    public int getHeight() {
        return bufferedImage.getHeight();
    }


    @Override
    public Optional<Integer> getPixel(final int c, final int r) {
        if (getWidth() > c && getHeight() > r && c >= 0 && r >= 0) {
            return Optional.of(bufferedImage.getRGB(c, r) & 0x00FFFFFF);
        }
        return Optional.empty();



    }
    @Override
    public void setPixel(final int x, final int y, final Integer newValue) {
        if (getWidth() > x && getHeight() > y && x >= 0 && y >= 0) {
            bufferedImage.setRGB(x, y, newValue);
        }
    }

    @Override
    public void clear(final Integer newValue) {
        final Graphics g = bufferedImage.getGraphics();
        if (g != null) {
            g.setColor(new Color(newValue));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    @Override
    public Graphics present(final Graphics device) {
        device.drawImage(bufferedImage, 0, 0, null);
        return device;
    }


}
