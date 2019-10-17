package com.mygdx.estadogame;

import com.mygdx.gerenciador.GameControleEstado;

/**
 *
 * @author tetel
 */
public abstract class GameEstado {
    protected GameControleEstado gce;
    
    protected GameEstado(GameControleEstado gce){
        this.gce = gce;
        init();
    }
    
    public abstract void init();
    public abstract void update(float dt);
    public abstract void draw();
    public abstract void handleInput();
    public abstract void dispose();
    
}
