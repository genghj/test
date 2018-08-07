package com.orange.hessianserver.bean;

import java.io.Serializable;

public class HelloWorld implements Serializable{
	private static final long serialVersionUID = 2303638650074878501L;
	/**
	 * 名字
	 */
	private String name;
	public HelloWorld() {
		
	}
	public HelloWorld(String name) {
		 this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
 
}