package ch.bbcag.jamkart.client;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyEventHandler implements EventHandler<KeyEvent> {

    private boolean isForwardPressed = false;
    private boolean isBackwardPressed = false;
    private boolean isLeftPressed = false;
    private boolean isRightPressed = false;

    private boolean isSpacePressed = false;

    @Override
    public void handle(KeyEvent event) {
        if(event.getEventType() == KeyEvent.KEY_PRESSED) {
            onKeyPressed(event);
        } else {
            onKeyReleased(event);
        }
    }

    private void onKeyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.W) {
            isForwardPressed = true;
        } else if(event.getCode() == KeyCode.S) {
            isBackwardPressed = true;
        } else if(event.getCode() == KeyCode.A) {
            isLeftPressed = true;
        } else if(event.getCode() == KeyCode.D) {
            isRightPressed = true;
        } else if(event.getCode() == KeyCode.SPACE) {
            isSpacePressed = true;
        }
    }

    private void onKeyReleased(KeyEvent event) {
        if(event.getCode() == KeyCode.W) {
            isForwardPressed = false;
        } else if(event.getCode() == KeyCode.S) {
            isBackwardPressed = false;
        } else if(event.getCode() == KeyCode.A) {
            isLeftPressed = false;
        } else if(event.getCode() == KeyCode.D) {
            isRightPressed = false;
        } else if(event.getCode() == KeyCode.SPACE) {
            isSpacePressed = false;
        }
    }

    public boolean isForwardPressed() {
        return isForwardPressed;
    }

    public boolean isBackwardPressed() {
        return isBackwardPressed;
    }

    public boolean isLeftPressed() {
        return isLeftPressed;
    }

    public boolean isRightPressed() {
        return isRightPressed;
    }

    public boolean isSpacePressed() {
        return isSpacePressed;
    }

}
