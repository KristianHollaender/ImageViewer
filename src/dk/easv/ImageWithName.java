package dk.easv;

import javafx.scene.image.Image;
import java.io.File;

public class ImageWithName {

    private Image image;
    private String imageName;

    public ImageWithName(Image image, File file) {
        this.image = image;
        imageName = file.getName();
    }

    public Image getImage() {
        return image;
    }

    public String getImageName() {
        return imageName;
    }

}
