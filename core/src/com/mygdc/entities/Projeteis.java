package com.mygdc.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;

/**
 *
 * @author tetel
 */
public class Projeteis extends SpaceObject{
    private float vidaTempo; // quanto tempo o projetil continua na tela
    private float vidaCronometro;
    
    private boolean remove; // descobrir se o projetil deve ou não ser removido da lista
    
    public Projeteis(float x, float y, float radians){
        
        this.x = x;
        this.y = y;
        this.radians = radians;
        
        float velocidade = 350;
        dx = MathUtils.cos(radians) * velocidade;
        dy = MathUtils.sin(radians) * velocidade;
        
        width = height = 2;
        
        vidaCronometro = 0;
        vidaTempo = 1; // vai permanecer na tela por 1 segundo
        
    }
    
    public boolean deveRemover(){
        return remove;
    }
    public void update(float dt){
        /*atualizar a posição*/
        x += dx * dt;
        y += dy * dt;
        
        limiteTela(); //vamos envolvê-lo na tela
        
        vidaCronometro += dt;
        if(vidaCronometro >  vidaTempo){
            remove = true;
        }
 
        
    }
    public void draw(ShapeRenderer sr){
        sr.setColor(1, 1, 1, 1);
        sr.begin(ShapeType.Filled);
        sr.circle(x - width / 2, y - height / 2, width / 2);
        sr.end();
        
    }
    
}
