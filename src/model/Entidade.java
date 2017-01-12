package model;

import java.awt.Rectangle;

public abstract class Entidade {
	private int x;
	private int y;
	private int largura;
	private int altura;
	private String img;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}	
	
	public int getLargura() {
		return largura;
	}	
	public void setLargura(int largura) {
		this.largura = largura;
	}
	
	public int getAltura() {
		return altura;
	}	
	public void setAltura(int altura) {
		this.altura = altura;
	}
	
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	public Rectangle getForma(){
		return new Rectangle(getX(), getY(), getLargura(), getAltura());
	}
}
