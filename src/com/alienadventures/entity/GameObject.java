package com.alienadventures.entity;

import com.alienadventures.Camera;
import com.alienadventures.World;
import com.alienadventures.image.ImageType;
import com.alienadventures.util.Rectangle;
import com.alienadventures.util.Vector;

import java.awt.*;

public abstract class GameObject {
	protected ImageType image;
	protected double x, y, width, height;
	protected boolean dead;
	protected Vector vel, acc;
	protected Rectangle hitBox;
	protected World world;
	
	public GameObject(World world) {
		this(world, 0, 0, 0, 0);
	}
	
	public GameObject(World world, double x, double y) {
		this(world, x, y, 0, 0);
	}
	
	public GameObject(World world, double x, double y, double width, double height) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		vel = new Vector(0, 0);
		acc = new Vector(0, 0);
		hitBox = new Rectangle(x, y, width, height);
	}
	
	protected void setWidth(double width) {
		this.width = width;
	}
	
	protected void setHeight(double height) {
		this.height = height;
	}
	
	public Vector getVel() {
		return vel;
	}
	
	public void setVelX(double x) {
		this.vel.x = x;
	}
	
	public void setVel(double x, double y) {
		this.vel.set(x, y);
	}
	
	public void setVelY(double y) {
		this.vel.y = y;
	}
	
	public void setAccX(double x) {
		this.acc.x = x;
	}
	
	public void setAccY(double y) {
		this.acc.y = y;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public double getCenterX() {
		return x + width / 2;
	}
	
	public double getCenterY() {
		return y + height / 2;
	}
	
	protected void updateHitBox() {
		hitBox.setRect(x, y, width, height);
	}
	
	protected void applyVelY() {
		vel.y += acc.y;
		y += vel.y;
	}
	
	protected void applyVelX() {
		applyVelX(World.FRICTION);
	}
	
	protected void applyVelX(double friction) {
		acc.x -= vel.x * friction;
		vel.x += acc.x;
		x += vel.x;
	}
	
	protected void applyGravity() {
		applyGravity(1);
	}
	
	protected void applyGravity(double factor) {
		acc.set(0, World.GRAVITY * factor);
	}
	
	public int screenX(Camera camera) {
		return (int) (camera == null ? this.x : this.x - camera.getOffsetX());
	}
	
	public int screenY(Camera camera) {
		return (int) (camera == null ? this.y : this.y - camera.getOffsetY());
	}
	
	protected boolean collides(GameObject other) {
		return other.hitBox.intersects(hitBox);
	}
	
	public abstract void update();
	
	public abstract void render(Graphics g, Camera camera);
	
	public void drawHitBox(Graphics2D g, Camera camera) {
		g.setColor(Color.GREEN);
		g.setStroke(new BasicStroke(2));
		g.drawRect((int) screenX(camera), (int) screenY(camera), (int) width, (int) height);
	}
}
