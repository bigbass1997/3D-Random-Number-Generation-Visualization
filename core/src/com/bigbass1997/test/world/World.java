package com.bigbass1997.test.world;

import java.util.Hashtable;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bigbass1997.test.graphics.QuickRender;

public class World {
	
	public Hashtable<String, Object> objects;
	
	public Camera cam;
	
	public ImmediateModeRenderer20 rend;
	public QuickRender quickRend;
	
	public ModelBatch modelBatch;
	public Environment environment;
	
	public World(Camera cam){
		objects = new Hashtable<String, Object>();
		
		this.cam = cam;

		rend = new ImmediateModeRenderer20(false, true, 0);
		quickRend = new QuickRender(cam, rend);
		
		modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
	}
	
	public void addObject(String id, Object object){
		objects.put(id, object);
	}
	
	public void removeObject(String id){
		objects.get(id).dispose();
		objects.remove(id);
	}
	
	public void rotateCam(float theta, float phi){
		theta = (float) Math.toRadians(theta);
		phi = (float) Math.toRadians(phi);
		
		float oldRho = (float) Math.sqrt(Math.pow(cam.position.x, 2) + Math.pow(cam.position.y, 2) + Math.pow(cam.position.z, 2));
		System.out.println("oldRho: " + oldRho);
		
		float x = (float) (oldRho * Math.sin(phi) * Math.cos(theta)); 
		float y = (float) (oldRho * Math.sin(phi) * Math.sin(theta));
		float z = (oldRho * phi);
		System.out.println(x + " | " + y + " | " + z);
		
		float newRho = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
		System.out.println("newRho: " + newRho);
		
		cam.position.set(newRho, theta, phi);
		cam.lookAt(Vector3.Zero);
		cam.update();
	}
	
	public void render(){
		quickRend.beginLines();
		quickRend.line(0, 0, 0, 0xFF0000FF, 500, 0, 0, 0xFF0000FF);
		quickRend.line(0, 0, 0, 0x00FF00FF, 0, 500, 0, 0x00FF00FF);
		quickRend.line(0, 0, 0, 0x0000FFFF, 0, 0, 500, 0x0000FFFF);
		quickRend.endLines();
		
		modelBatch.begin(cam);
		for(Object object : objects.values()){
			object.render();
			modelBatch.render(object.modelInstance, environment);
		}
		modelBatch.end();
	}
	
	public void update(float delta){
		for(Object object : objects.values()){
			object.update(delta);
		}
		
		//cam.rotateAround(Vector3.Zero, Vector3.Y, -10.0f * delta);
		cam.update();
	}
	
	public void dispose(){
		for(Object object : objects.values()){
			object.dispose();
		}
		
		rend.dispose();
	}
}
