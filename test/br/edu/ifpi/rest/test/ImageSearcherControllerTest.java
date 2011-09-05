package br.edu.ifpi.rest.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.junit.Test;
import static org.junit.Assert.*;

import com.google.gson.Gson;

import br.edu.ifpi.opala.searching.SearchResult;
import br.edu.ifpi.opala.utils.ReturnMessage;

public class ImageSearcherControllerTest {

	@Test
	public final void searchIdenticalImages()
			throws Exception {
		String imagem = "E:/Meus Documentos/IFPI/JAVA/projetorest/WebContent/resources/image/1.jpg";

		String endereco = "http://localhost:8080/projetorest/searchImage?limit=10&image=";

		String string = "";

		endereco += imagem;

		endereco = endereco.replace(" ", "+");

		URL url;
		try {
			url = new URL(endereco);
			InputStreamReader inputReader = new InputStreamReader(
					url.openStream());
			BufferedReader bufferedReader = new BufferedReader(inputReader);
			String linha = "";
			while ((linha = bufferedReader.readLine()) != null) {
				string += linha;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		SearchResult resultado = new Gson()
				.fromJson(string, SearchResult.class);

		// então
		assertEquals(resultado.getCodigo(), ReturnMessage.SUCCESS);
		// assertEquals(resultado.getItems().size(),3);

	}



}