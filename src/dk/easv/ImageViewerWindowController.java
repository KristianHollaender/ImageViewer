package dk.easv;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ImageViewerWindowController implements Initializable {

    @FXML
    private Button btnStartSlideshow;
    @FXML
    private Button btnStartSlideshow2;
    @FXML
    private Button btnStopSlideshow;
    @FXML
    private Slider secondsSlider;
    @FXML
    private Label lblImageName;
    @FXML
    private ImageView imageView;

    private final List<ImageWithName> images = new ArrayList<>();
    private ExecutorService executorService = Executors.newCachedThreadPool();

    private Slideshow slideshow1;
    private Slideshow slideshow2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnStartSlideshow.setDisable(false);
        btnStopSlideshow.setDisable(true);
    }

    @FXML
    private void handleBtnLoadAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image files");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Images",
                "*.png", "*.jpg", "*.gif", "*.tif", "*.bmp"));
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());

        if (!files.isEmpty())
        {
            files.forEach((File file) ->
            {
                Image image = new Image(file.toURI().toString());
                images.add(new ImageWithName(image, file));
            });
            displayImage(images.get(0).getImage());
            lblImageName.setText(images.get(0).getImageName());
        }
    }

    @FXML
    public void handleBtnLoadAction2() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image files");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Images",
                "*.png", "*.jpg", "*.gif", "*.tif", "*.bmp"));
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());

        if (!files.isEmpty())
        {
            files.forEach((File file) ->
            {
                Image image = new Image(file.toURI().toString());
                images.add(new ImageWithName(image, file));
            });
            displayImage(images.get(0).getImage());
            lblImageName.setText(images.get(0).getImageName());
        }
    }

    private void displayImage(Image image) {
        imageView.setImage(image);
    }

    @FXML
    private void handleBtnStartSlideshow() {
        int delay = (int) secondsSlider.getValue();

        slideshow1 = new Slideshow(images,delay);
        slideshow1.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            displayImage(newValue.getImage());
            lblImageName.setText(images.get(slideshow1.getCurrentImageIndex()).getImageName());
        });

        slideshow1.setOnCancelled(e -> {
            btnStartSlideshow.setDisable(false);
            btnStopSlideshow.setDisable(true);
            secondsSlider.setDisable(false);
        });

        slideshow1.setOnRunning(e -> {
            btnStartSlideshow.setDisable(true);
            btnStopSlideshow.setDisable(false);
            secondsSlider.setDisable(true);
        });

        executorService.submit(slideshow1);
    }


    public void handleBtnStartSlideshow2() {
        int delay = (int) secondsSlider.getValue();

        slideshow2 = new Slideshow(images,delay);
        slideshow2.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            displayImage(newValue.getImage());
            lblImageName.setText(images.get(slideshow2.getCurrentImageIndex()).getImageName());
        });

        slideshow2.setOnCancelled(e -> {
            btnStartSlideshow2.setDisable(false);
            btnStopSlideshow.setDisable(true);
            secondsSlider.setDisable(false);
        });

        slideshow2.setOnRunning(e -> {
            btnStartSlideshow2.setDisable(true);
            btnStopSlideshow.setDisable(false);
            secondsSlider.setDisable(true);
        });

        executorService.submit(slideshow2);
    }

    public void handleBtnStartBoth() {
        handleBtnStartSlideshow();
        handleBtnStartSlideshow2();
    }

    @FXML
    private void handleBtnStopSlideshow() {
        slideshow1.cancel();
        slideshow2.cancel();
    }

}