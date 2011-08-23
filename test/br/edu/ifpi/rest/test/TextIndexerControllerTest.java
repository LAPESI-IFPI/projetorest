package br.edu.ifpi.rest.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;

import org.junit.BeforeClass;
import org.junit.Test;

import br.edu.ifpi.opala.utils.MetaDocument;
import br.edu.ifpi.opala.utils.MetaDocumentBuilder;
import br.edu.ifpi.opala.utils.ReturnMessage;
import br.edu.ifpi.opala.utils.Util;

import static org.junit.Assert.*;

import com.google.gson.Gson;

public class TextIndexerControllerTest {

	private String content = "Conteúdo do document a ser indexado nos testes";
	private MetaDocument metadoc = new MetaDocumentBuilder().id("1")
			.title("Título do documento de teste")
			.author("Autor do documento de teste").build();
	
	@BeforeClass
	public static void indexImages() {
		File path = new File("./webapps/indice/text");
		assertTrue(Util.deleteDir(path));
	}
	
	@Test
	public void deveriaIndexarUmDocumentoDeTexto() {

		String endereco = "http://localhost:8080/projetorest/addText?";
		String string = "";

		endereco += "metaDocument.id=" + metadoc.getId()
				+ "&metaDocument.author=" + metadoc.getAuthor()
				+ "&metaDocument.title=" + metadoc.getTitle()
				+ "&metaDocument.format=" + metadoc.getFormat()
				+ "&metaDocument.keywords=" + metadoc.getKeywords()
				+ "&metaDocument.publicationDate="
				+ metadoc.getPublicationDate() + "&content=" + content;

		endereco = endereco.replace(" ", "+");

		string = executar(endereco);
		
		ReturnMessage message = new Gson()
				.fromJson(string, ReturnMessage.class);
		System.out.println(message);
		assertEquals(ReturnMessage.SUCCESS, message);
	}

	@Test
	public void deveriaDevolverDuplicatedIdQuandoTentoIndexarUmDocumentoDuasVezes() {
		String endereco = "http://localhost:8080/projetorest/addText?";
		String string = "";

		endereco += "metaDocument.id=" + metadoc.getId()
				+ "&metaDocument.author=" + metadoc.getAuthor()
				+ "&metaDocument.title=" + metadoc.getTitle()
				+ "&metaDocument.format=" + metadoc.getFormat()
				+ "&metaDocument.keywords=" + metadoc.getKeywords()
				+ "&metaDocument.publicationDate="
				+ metadoc.getPublicationDate() + "&content=" + content;

		endereco = endereco.replace(" ", "+");

		string = executar(endereco);
		
		ReturnMessage message = new Gson()
				.fromJson(string, ReturnMessage.class);
		System.out.println(message);
		assertEquals(ReturnMessage.DUPLICATED_ID, message);
	}
	
	@Test
	public void deveriaDeletarUmDocumento(){
		String endereco = "http://localhost:8080/projetorest/delText?id=1";
		String string = "";
		
		string = executar(endereco);
		
		ReturnMessage message = new Gson()
				.fromJson(string, ReturnMessage.class);
		System.out.println(message);
		assertEquals(ReturnMessage.SUCCESS, message);
	}
	
	@Test
	public void deveriaRetornarIdNotFoundQuandoTentarDeletarUmDocumentoInexistente(){
		String endereco = "http://localhost:8080/projetorest/delText?id=1";
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
