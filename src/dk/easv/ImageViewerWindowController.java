package dk.easv;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import javafx.util.Duration;

public class ImageViewerWindowController implements Initializable
{
    private final List<Image> images = new ArrayList<>();
    private int currentImageIndex = 0;

    private Thread thread;

    @FXML
    Parent root;

    @FXML
    private ImageView imageView;

    @FXML
    private Slider btnSlider;

    private double time = 1000;


    @FXML
    private void handleBtnLoadAction()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image files");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Images",
                "*.png", "*.jpg", "*.gif", "*.tif", "*.bmp"));
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());

        if (!files.isEmpty())
        {
            files.forEach((File f) ->
            {
                images.add(new Image(f.toURI().toString()));
            });
            displayImage();
        }
    }

    @FXML
    private void handleBtnPreviousAction()
    {
        if (!images.isEmpty())
        {
            currentImageIndex =
                    (currentImageIndex - 1 + images.size()) % images.size();
            displayImage();
        }
    }

    @FXML
    private void handleBtnNextAction()
    {
        if (!images.isEmpty())
        {
            currentImageIndex = (currentImageIndex + 1) % images.size();
            displayImage();
        }
    }

    @FXML
    private void handleBtnStartSlideshow() throws InterruptedException {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!images.isEmpty()) {
                    handleBtnNextAction();
                    try {
                        Thread.sleep((long) time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    @FXML
    private void handleBtnStopSlideshow() {
        thread.stop();
    }

    private void displayImage()
    {
        if (!images.isEmpty())
        {
            imageView.setImage(images.get(currentImageIndex));
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                time = btnSlider.getValue()*1000;
            }
        });
    }
}