package com.mygdc.entities;

import com.mygdx.game.MyGdxGame;

/**
 *
 * @author tetel
 */
public class SpaceObject {
    protected float x;
    protected float y;
    
    protected float dx;
    protected float dy;
    
    protected float radians;
    protected float speed;
    protected float rotationSpeed;
    
    protected int width;
    protected int height;
    
    protected float[] formax;
    protected float[] formay;
    
    public float getx(){return x;}
    public float gety(){return y;}
    
    public float[] getFormax(){return formax;}
    public float[] getFormay(){return formay;}
    
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public boolean cruzar(SpaceObject other ){
        float[] sx = other.getFormax();
        float[] sy = other.getFormay();
        for(int i =0; i < sx.length; i++){
            if(contains(sx[i],sy[i])){
                return true;
            }
        }
        return false;
    }
    
    public boolean contains(float x, float y){
        boolean b =false;
        for(int i = 0, j = formax.length - 1; i< formax.length; j= i++){
            if ((formay[i] > y) != (formay[j] > y)
                    && (x < (formax[j] - formax[i])
                    * (y - formay[i]) / (formay[j] - formay[i])
                    + formax[i])) {
                b = !b;
            }
        }
        return b;
    }
    
    protected void limiteTela(){
        if(x < 0) x = MyGdxGame.WIDTH;
        if(x > MyGdxGame.WIDTH) x = 0;
        if(y < 0) y = MyGdxGame.HEIGHT;
        if(y > MyGdxGame.HEIGHT) y = 0;
        
    }
    
    
}
