package com.mygdx.game.gameui;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class JoystickArea extends Group {


    private class AreaListener extends InputListener{

        private final Vector2 tmp = new Vector2();

        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            joystick.setCenterPosition(x, y);
            joystick.parentToLocalCoordinates(tmp.set(x, y));
            jListener.touchDown(event, tmp.x, tmp.y, pointer, button);
            return true;
        }


        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            joystick.parentToLocalCoordinates(tmp.set(x, y));
            jListener.touchUp(event, tmp.x, tmp.y, pointer, button);
            joystick.setDefaultXY();

        }


        public void touchDragged (InputEvent event, float x, float y, int pointer) {
            joystick.parentToLocalCoordinates(tmp.set(x, y));
            jListener.touchDragged(event, tmp.x, tmp.y, pointer);


        }
    }


     private Joystick joystick;
    private JoystickInputListener jListener;

     public JoystickArea(Texture circle, Texture curJoystick){
         joystick = new Joystick(circle, curJoystick);
         addActor(joystick);

         jListener = new JoystickInputListener(joystick);

         addListener(new AreaListener());

         setX(10);
         setY(10);
         setWidth(300);
         setHeight(460);

         debug();
     }

    public float getValueX(){
        return joystick.getValueX();
    }

    public float getValueY(){
        return joystick.getValueY();
    }

    public boolean isJoystickDown(){
        return joystick.isJoystickDown();
    }
}
