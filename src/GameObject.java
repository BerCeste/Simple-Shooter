import java.awt.image.BufferedImage;

/**
 *              Created by Batuhan on 2.05.2017.
 */

abstract class GameObject {
    private double x, y;

    private BufferedImage image;

    private int imageWidth, imageHeight;

    GameObject(BufferedImage image) {
        setImage(image);
    }

    double getX() {
        return x;
    }

    void setX(double x) {
        this.x = x;
    }

    double getY() {
        return y;
    }

    void setY(double y) {
        this.y = y;
    }

    BufferedImage getImage() {
        return image;
    }

    void setImage(BufferedImage image) {
        this.image = image;
    }

    int getImageWidth() {
        return imageWidth;
    }

    void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    int getImageHeight() {
        return imageHeight;
    }

    void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    @Override
    public String toString() {
        return "X: " + x + "\nY: " + y;
    }
}
