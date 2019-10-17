package com.mygdc.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;

/**
 *
 * @author tetel
 */
public class Asteroides extends SpaceObject{
     private int type;
     public static final int PEQUENO = 0;
     public static final int MEDIO = 1;
     public static final int GRANDE = 2;
     
     private int numPontos;
     private float[] dists;
     
     private int score;
     
     private boolean remove;
     
     public Asteroides(float x, float y, int type){
         this.x = x;
         this.y = y;
         this.type = type;
         
         if(type == PEQUENO){
             numPontos = 8;
             width = height = 12;
             speed = MathUtils.random(70,100);
             score = 100;
         }
         else if(type == MEDIO){
             numPontos = 10;
             width = height = 20;
             speed = MathUtils.random(50,60);
             score = 50;
         }
         else if(type == GRANDE){
             numPontos = 12;
             width = height = 40;
             speed = MathUtils.random(20,30);
             score = 20;
         }
         
         rotationSpeed = MathUtils.random(-1,1);
         
         radians = MathUtils.random(2 * 3.1415f);
         dx = MathUtils.cos(radians) * speed;
         dy = MathUtils.sin(radians) * speed;

         formax = new float[numPontos];
         formay = new float[numPontos];
         dists = new float[numPontos];
         
         int raio = width / 2;
	 for(int i = 0; i < numPontos; i++) {
             dists[i] = MathUtils.random(raio / 2, raio);
	 }
		
	 setForma();
         
     }

    private void setForma() {
        /*loop que vai setar todos os pontos para os asterioids*/
        float angulo = 0;
            for(int i = 0; i < numPontos; i++) {
		formax[i] = x + MathUtils.cos(angulo + radians) * dists[i];
		formay[i] = y + MathUtils.sin(angulo + radians) * dists[i];
		angulo += 2 * 3.1415f / numPontos;
		}
    }
    
    public int getType(){ return type;}
    public boolean deveRemover(){ return remove;}
    public int getScore(){ return score;}
    
    public void update(float dt){
       x += dx * dt;
       y += dy * dt;
		
       radians += rotationSpeed * dt;
       setForma();
		
       limiteTela(); 
    }
    public void draw(ShapeRenderer sr){
        sr.setColor(1, 1, 1, 1);
	sr.begin(ShapeType.Line);
	for(int i = 0, j = formax.length - 1;i < formax.length;j = i++) {
            sr.line(formax[i], formay[i], formax[j], formay[j]);
	}
	sr.end();
        
    }
             
     
     
}
