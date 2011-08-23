package br.com.caelum.vraptor.test;

import java.io.File;

public class Teste {
	
	
	public static void main(String[] args) {
		
		File file = new File("webapps/indice/image");
		
		System.out.println(file.getAbsolutePath());
		System.out.println(file.listFiles().length);
		
	}
	
	
	
	

	


}