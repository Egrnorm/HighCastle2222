package com.mygdx.game.gameui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class JoystickInputListener extends InputListener {

    private Joystick joystick;

    public JoystickInputListener(Joystick joystick){
        this.joystick = joystick;
    }

    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        joystick.setTouched();
        joystick.changeCursor(x,y);
        return true;
    }


    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
        joystick.setUntouched();
    }


    public void touchDragged (InputEvent event, float x, float y, int pointer) {
        joystick.changeCursor(x,y);

        if(joystick.isJoystickDown()){
            joystick.handleChangeListener();
        }
    }

}
