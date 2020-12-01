package com.ae2dms;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;

class GraphicObject extends Rectangle {
    GraphicObject(GameObject obj) {
        Paint color;
        Image image = null;
        switch (obj) {
            case WALL:
                color = Color.BLACK;
                image = new Image("pic/wall.png");
                break;

            case CRATE:
                color = Color.ORANGE;
                image = new Image("pic/box.png");
                break;

            case DIAMOND:
                color = Color.DEEPSKYBLUE;
                image = new Image("pic/diamond.png");
                if (GameEngine.isDebugActive()) {
                    FadeTransition ft = new FadeTransition(Duration.millis(1000), this);
                    ft.setFromValue(1.0);
                    ft.setToValue(0.2);
                    ft.setCycleCount(Timeline.INDEFINITE);
                    ft.setAutoReverse(true);
                    ft.play();
                }

                break;

            case KEEPER:
                color = Color.RED;
                image = new Image("pic/player.png");
                break;

            case FLOOR:
                color = Color.WHITE;
                break;

            case CRATE_ON_DIAMOND:
                color = Color.DARKCYAN;
                image = new Image("pic/succ.png");
                break;

            default:
                String message = "Error in Level constructor. Object not recognized.";
                GameEngine.logger.severe(message);
                throw new AssertionError(message);
        }

        this.setFill(color);
        if (image != null)
            this.setFill(new ImagePattern(image));
        this.setHeight(30);
        this.setWidth(30);

        if (obj != GameObject.WALL) {
            this.setArcHeight(50);
            this.setArcWidth(50);
        }

        if (GameEngine.isDebugActive()) {
            this.setStroke(Color.RED);
            this.setStrokeWidth(0.25);
        }}}
