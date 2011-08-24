package br.edu.ifpi.rest.test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.junit.Test;

import br.edu.ifpi.opala.utils.MetaDocument;
import br.edu.ifpi.opala.utils.MetaDocumentBuilder;
import br.edu.ifpi.opala.utils.ReturnMessage;

import com.google.gson.Gson;


public class ImageIndexerControllerTest {
	
	private String image = "E:/Meus Documentos/IFPI/JAVA/projetorest/WebContent/resources/image/image (6).jpg";
	private MetaDocument metadoc = new MetaDocumentBuilder().id("3")
			.title("Título do documento de teste")
			.author("Autor do documento de teste").build();
	
	@Test
	public void deveriaIndexarUmaImagem() {

		String endereco = "http://localhost:8080/projetorest/addImage?";
		String string = "";

		endereco += "metaDocument.id=" + metadoc.getId()
				+ "&metaDocument.author=" + metadoc.getAuthor()
				+ "&metaDocument.title=" + metadoc.getTitle()
				+ "&metaDocument.format=" + metadoc.getFormat()
				+ "&metaDocument.keywords=" + metadoc.getKeywords()
				+ "&metaDocument.publicationDate="
				+ metadoc.getPublicationDate() + "&image=" + image;

		endereco = endereco.replace(" ", "+");
		
		string = executar(endereco);
		
		ReturnMessage message = new Gson()
				.fromJson(string, ReturnMessage.class);
		System.out.println(message);
		assertEquals(ReturnMessage.SUCCESS, message);
	}
	
	@Test
	public void deveriaDevolverDuplicatedIdQuandoTentoIndexarUmaImagemDuasVezes() {

		String endereco = "http://localhost:8080/projetorest/addImage?";
		String string = "";

		endereco += "metaDocument.id=" + metadoc.getId()
				+ "&metaDocument.author=" + metadoc.getAuthor()
				+ "&metaDocument.title=" + metadoc.getTitle()
				+ "&metaDocument.format=" + metadoc.getFormat()
				+ "&metaDocument.keywords=" + metadoc.getKeywords()
				+ "&metaDocument.publicationDate="
				+ metadoc.getPublicationDate() + "&image=" + image;

		endereco = endereco.replace(" ", "+");
		
		string = executar(endereco);
		
		ReturnMessage message = new Gson()
				.fromJson(string, ReturnMessage.class);
		System.out.println(message);
		assertEquals(ReturnMessage.DUPLICATED_ID, message);
	}
	
	
	
	@Test
	public void deveriaDeletarUmaImagem(){

		String endereco = "http://localhost:8080/projetorest/delImage?id="+metadoc.getId();
		String string = "";
		
		string = executar(endereco);
		
		ReturnMessage message = new Gson()
				.fromJson(string, ReturnMessage.class);
		System.out.println(message);
		assertEquals(ReturnMessage.SUCCESS, message);
	}
	
	@Test
	public void deveriaRetornarIdNotFoundQuandoTentarDeletarUmaImagemInexistente(){
		String endereco = "http://localhost:8080/projetorest/delText?id=asdfjalsdfj";
		String string = "";
		
		string = executar(endereco);
		
		ReturnMessage message = new Gson()
				.fromJson(string, ReturnMessage.class);
		System.out.println(message);
		assertEquals(ReturnMessage.ID_NOT_FOUND, message);
	}
	
	public String executar(String endereco){
		String string = "";
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
		return string;
	}

}
