package com.bigbass1997.test;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bigbass1997.test.fonts.FontManager;
import com.bigbass1997.test.world.Cube;
import com.bigbass1997.test.world.World;
import com.bigbass1997.test.world.Object;

public class Main extends ApplicationAdapter {
	
	public World world;
	
	public Stage stage;
	public Label debugLabel;
	
	public int i = 0;
	
	@Override
	public void create(){
		FontManager.addFont("fonts/computer.ttf");
		
		PerspectiveCamera cam = new PerspectiveCamera(67f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(500f, 500f, 500f);
		cam.lookAt(Vector3.Zero);
		cam.near = 1f;
		cam.far = 3000f;
		cam.update();
		
		world = new World(cam);
		//world.addObject("CUBE1", new Cube(50, 50, 50, 100, 0xFFFFFFFF));
		
		//System.out.println(world.objects.get("CUBE1").getPos());
		
		stage = new Stage();
		
		debugLabel = new Label("", new Label.LabelStyle(FontManager.getFont("fonts/computer.ttf", 18).font, Color.BLACK));
		debugLabel.setPosition(10, Gdx.graphics.getHeight() - 45);
		
		stage.addActor(debugLabel);
	}

	@Override
	public void render(){
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(1, 1, 1, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Input input = Gdx.input;
		float speed = 50f * Gdx.graphics.getDeltaTime();
		
		Random rand = new Random();
		world.addObject("OBJECT_" + i, new Cube(
				(rand.nextFloat() * 1000)-500, (rand.nextFloat() * 1000)-500, (rand.nextFloat() * 1000)-500,
				5, Color.argb8888(0, rand.nextFloat() + 0.5f, 0, 1)));
		i+=1;
		
		debugLabel.setText(
				"FPS: " + Gdx.graphics.getFramesPerSecond() + "\n" +
				"#Obs: " + world.objects.size() + "\n" + 
				"Speed: " + (int) (speed * Gdx.graphics.getFramesPerSecond()) + "px/s\n" + 
				"Cam Pos:\n" +
				"  X: " + world.cam.position.x + "\n" +
				"  Y: " + world.cam.position.y + "\n" +
				"  Z: " + world.cam.position.z
		);
		
		stage.draw();

		world.render();
		
		if(input.isKeyPressed(Keys.NUMPAD_8)) world.rotateCam(0, speed/10);
		if(input.isKeyPressed(Keys.NUMPAD_2)) world.rotateCam(0, -speed/10);
		
		world.update(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose(){
		world.dispose();
		stage.dispose();
	}
}
