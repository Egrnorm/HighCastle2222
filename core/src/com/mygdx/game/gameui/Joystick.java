package com.mygdx.game.gameui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

public class Joystick extends Actor {

    private Texture circle;
    private Texture curJoystick;
    private boolean isJoystickDown = false;
    private float rad = 160;
    private static final float CUR_RADIUS = 20;
    private float inversRad = 0;

    private float curX = 0;
    private float curY = 0;

    private float valueX = 0;
    private float valueY = 0;

    public void setCenterPosition(float x, float y){
        setPosition(x - rad, y - rad);
    }

    public float getValueX(){
        return valueX;
    }

    public float getValueY(){
        return valueY;
    }

    public boolean isJoystickDown(){
        return isJoystickDown;
    }

    private List<JoystickChangedListener> listeners = new ArrayList<JoystickChangedListener>();


    public void addJoystickChangedListener(JoystickChangedListener listener){
        listeners.add(listener);
    }

    public void removeJoystickChangedListener(JoystickChangedListener listener){
        listeners.remove(listener);
    }

    public void clearJoystickChangedListener(){
        listeners.clear();
    }


    public void handleChangeListener(){
        for (JoystickChangedListener listener : listeners){
            listener.changed(valueX, valueY);
        }
    }

    public Joystick(Texture circle, Texture curJoystick){
        this.circle = circle;
        this.curJoystick = curJoystick;
        setDefaultWH();
        setDefaultXY();
        addListener(new JoystickInputListener(this));
        debug();

    }


    public void setTouched(){
        isJoystickDown = true;
    }

    public void setUntouched(){
        isJoystickDown = false;
    }

    public void resetCur(){
        curX = 0;
        curY = 0;
    }

    public void setDefaultWH(){
        setWidth(160);
        setHeight(160);
        rad = 80;
        inversRad = 1 / rad;
    }

    public void setWidth(float w){
        super.setWidth(w);
        super.setHeight(w);
        rad = w / 2;
        inversRad = 1 / rad;
    }

    public void setHeight(float h){
        super.setWidth(h);
        super.setHeight(h);
        rad = h / 2;
        inversRad = 1 / rad;
    }


    public void setDefaultXY(){
        setX(30);
        setY(30);
    }

    public void changeCursor(float x, float y){
        float dx = x - rad;
        float dy = y - rad;
        float length = (float)Math.sqrt(dx *dx + dy*dy);
        if(length < rad){
            this.curX = dx;
            this.curY = dy;
        }
        else{
            float k = rad / length;
            this.curX = dx * k;
            this.curY = dy * k;
        }
        valueX = curX * inversRad;
        valueY = curY * inversRad;
    }

    @Override
    public Actor hit(float x, float y, boolean touchable){
        Actor actor = super.hit(x, y, touchable);
        if(actor == null) return null;
        else{
            float dx = x - rad;
            float dy = y - rad;
            return (dx * dx + dy * dy <= rad * rad) ? this : null;
        }
    }


    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(circle, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        if(isJoystickDown){
            batch.draw(curJoystick,
         this.getX() + rad - CUR_RADIUS + curX,
         this.getY() + rad - CUR_RADIUS + curY,
      2 * CUR_RADIUS,
      2 * CUR_RADIUS);
        }
        else{
            batch.draw(curJoystick,
         this.getX() + rad - CUR_RADIUS,
         this.getY() + rad - CUR_RADIUS,
      2 * CUR_RADIUS,
      2 * CUR_RADIUS);
        }
    }
}
