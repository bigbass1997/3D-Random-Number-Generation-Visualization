package com.bigbass1997.test;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bigbass1997.test.fonts.FontManager;
import com.bigbass1997.test.world.Cube;
import com.bigbass1997.test.world.Object;
import com.bigbass1997.test.world.World;

public class Main extends ApplicationAdapter {
	
	public World world;
	
	public Stage stage;
	public Label debugLabel;
	
	public int i = 0;
	
	public ArrayList<Vector3> allChangeVecs = new ArrayList<Vector3>();
	public Vector3 avgVec = new Vector3(0,0,0);
	
	@Override
	public void create(){
		FontManager.addFont("fonts/computer.ttf"); //Added font to be used with Debug Text
		
		//create, setup, and pass the camera that will be used. (Perspective or Orthogonal)
		PerspectiveCamera cam = new PerspectiveCamera(67f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(500f, 500f, 500f);
		cam.lookAt(Vector3.Zero);
		cam.near = 1f;
		cam.far = 5000f;
		cam.update();
		
		//Creates new world that holds all the objects that will be rendered and includes camera controls
		world = new World(cam);
		
		//Creates new stage for use with the debug text label
		stage = new Stage();
		
		debugLabel = new Label("", new Label.LabelStyle(FontManager.getFont("fonts/computer.ttf", 18).font, Color.BLACK));
		debugLabel.setPosition(10, Gdx.graphics.getHeight() - 120);
		
		//Adds the debug label to the stage so that it can be rendered/updated
		stage.addActor(debugLabel);
		
		//Pregenerates i number of objects at exact origin position
		float si = 4;
		Random rand = new Random();
		for(int i = 0; i < 2500; i++){
			world.addObject("OBJECT_" + i, new Cube(
					si/2f, si/2f, si/2f,
					si, Color.argb8888(0, 1, 0, 1)));
		}
	}
	
	@Override
	public void render(){
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		float speed = 50f * Gdx.graphics.getDeltaTime(); //allows for equal movement speed no matter the FPS
		
		Random rand = new Random();
		/*world.addObject("OBJECT_" + i, new Cube(
				(rand.nextFloat() * 1000)-500, (rand.nextFloat() * 1000)-500, (rand.nextFloat() * 1000)-500,
				5, Color.argb8888(0, rand.nextFloat() + 0.5f, 0, 1)));*/
		/*world.addObject("OBJECT_" + i, new Cube(
				3f/2f, 3f/2f, 3f/2f,
				3, Color.argb8888(0, 1, 0, 1)));*/
		i+=1;
		
		debugLabel.setText(
				"FPS: " + Gdx.graphics.getFramesPerSecond() + "\n" +
				"#Obs: " + world.objects.size() + "\n" + 
				"Speed: " + (int) (speed * Gdx.graphics.getFramesPerSecond()) + "px/s\n" + 
				"Cam Pos:\n" +
				"  X: " + world.cam.position.x + "\n" +
				"  Y: " + world.cam.position.y + "\n" +
				"  Z: " + world.cam.position.z + "\n" +
				"Cam Dir:\n" +
				"  X: " + world.cam.direction.x + "\n" +
				"  Y: " + world.cam.direction.y + "\n" +
				"  Z: " + world.cam.direction.z + "\n" +
				"Cam Up:\n" +
				"  X: " + world.cam.up.x + "\n" +
				"  Y: " + world.cam.up.y + "\n" +
				"  Z: " + world.cam.up.z + "\n" +
				"AvgVec:\n" +
				"  X: " + avgVec.x + "\n" +
				"  Y: " + avgVec.y + "\n" +
				"  Z: " + avgVec.z + "\n" +
				"  S: " + allChangeVecs.size()
		);
		
		stage.draw();
		world.render();
		allChangeVecs.clear();
		
		float dist = 10f;
		float x,y,z;
		for(Object ob : world.objects.values()){
			x = (rand.nextFloat() * dist) - (dist/2f);
			y = (rand.nextFloat() * dist) - (dist/2f);
			z = (rand.nextFloat() * dist) - (dist/2f);
			ob.addPos(x, y, z);
			allChangeVecs.add(new Vector3(x, y, z));
			/*
			System.out.println("--\\/--");
			System.out.println("X: " + x);
			System.out.println("Y: " + y);
			System.out.println("Z: " + z);*/
		}
		
		world.update(Gdx.graphics.getDeltaTime());
		
		
		
		if(Gdx.input.isKeyPressed(Keys.Z) || true){
			Vector3 avg = new Vector3(0,0,0);
			
			for(Vector3 vec : allChangeVecs){
				avg.x += vec.x;
				avg.y += vec.y;
				avg.z += vec.z;
			}
			avg.x /= allChangeVecs.size();
			avg.y /= allChangeVecs.size();
			avg.z /= allChangeVecs.size();
			
			avg.x = Math.abs(avg.x);
			avg.y = Math.abs(avg.y);
			avg.z = Math.abs(avg.z);
			
			avgVec = avg;
		}
	}
	
	@Override
	public void dispose(){
		world.dispose();
		stage.dispose();
	}
}
