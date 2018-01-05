import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * 
 * @author Cristhian Benitez
 * @version 12/21/2017
 *
 *          This class is responsible for doing the actual image editing
 */
public class ImageManipulator {

    Image myImage;
    int width;
    int height;


    /**
     * This is our constructor
     * 
     * @param myImage
     * @param width
     * @param height
     */
    public ImageManipulator(Image myImage, int width, int height) {
        this.myImage = myImage;
        this.width = width;
        this.height = height;
        resizeImage();
    }


    /**
     * This method resizes the image
     */
    public void resizeImage() {
        myImage = myImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }


    /**
     * This is a simple getter method
     * 
     * @return the image (new image that is)
     */
    public Image getNewImage() {
        return myImage;
    }


    /**
     * This is for converting over, I need to use buffered images to be able to
     * save them
     * 
     * @return the new image
     */
    public BufferedImage getBufferedImage() {
        BufferedImage buffImage = new BufferedImage(myImage.getWidth(null),
            myImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics graphic = buffImage.getGraphics();
        graphic.drawImage(myImage, 0, 0, null);
        graphic.dispose();
        return buffImage;
    }
}
