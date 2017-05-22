import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 *              Created by Batuhan on 2.05.2017.
 */

final class ImageLoader {
    private ImageLoader() { }

    static BufferedImage loadImage(String path) throws IOException {
        return ImageIO.read(new File(path));
    }
}
