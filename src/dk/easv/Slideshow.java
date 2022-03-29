package dk.easv;

import javafx.concurrent.Task;

import java.awt.*;
import java.util.List;

public class Slideshow extends Task<Image> {

    private int currentImageIndex = 0;
    private final List<Image> images;
    private int delay;

    public Slideshow(List<Image> images, int delay) {
        this.images = images;
        this.delay = delay;
    }

    @Override
    protected Image call() throws Exception {
        while (true) {
            if (!images.isEmpty()) {
                currentImageIndex = (currentImageIndex + 1) % images.size();
            }
            Image image = images.get(currentImageIndex);
            this.updateValue(image);
            Thread.sleep(delay * 1000);
        }
    }

    public int getCurrentImageIndex() {
        return currentImageIndex;
    }
}
