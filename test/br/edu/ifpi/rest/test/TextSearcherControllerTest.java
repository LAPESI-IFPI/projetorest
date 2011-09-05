package br.edu.ifpi.rest.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.*;

import com.google.gson.Gson;

import br.edu.ifpi.opala.searching.SearchResult;
import br.edu.ifpi.opala.utils.Metadata;
import br.edu.ifpi.opala.utils.ReturnMessage;

public class TextSearcherControllerTest {

	@Test
	public final void deveriaEncontarUmDocumentoQueJaFoiIndexado()
			throws Exception {
		Map<String, String> fields = new HashMap<String, String>();
		fields.put(Metadata.CONTENT.getValue(), "testes");

		String json = new Gson().toJson(fields);

		List<String> returnedFields = newRetunedFieldsWithAuthorAndTitle();

		String endereco = "http://localhost:8080/projetorest/searchText?";

		String string = "";

		endereco += "fields=" + json + "&returnedFields="
				+ returnedFields.get(0) + "&returnedFields="
				+ returnedFields.get(1) + "&batchStart=" + 1 + "&batchSize="
				+ 20 + "&reverse=" + true;

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
		assertNotNull(resultado.getItems().iterator().next()
				.getField(Metadata.AUTHOR.getValue()));
		assertNotNull(resultado.getItems().iterator().next()
				.getField(Metadata.TITLE.getValue()));
		assertNull(resultado.getItems().iterator().next()
				.getField(Metadata.KEYWORDS.getValue()));

	}

	@Test
	public final void deveriaRetornarEmptySearcherQuandoNaoEncontrarNadaNaBusca()
			throws Exception {
		Map<String, String> fields = new HashMap<String, String>();
		fields.put(Metadata.CONTENT.getValue(),
				"adfhadsfasdfglhasdfjasdf3431383h123h12ih1");

		List<String> returnedFields = newRetunedFieldsWithAuthorAndTitle();

		String endereco = "http://localhost:8080/projetorest/searchText?";

		String string = "";

		String json = new Gson().toJson(fields);

		endereco += "fields=" + json + "&returnedFields="
				+ returnedFields.get(0) + "&returnedFields="
				+ returnedFields.get(1) + "&batchStart=" + 1 + "&batchSize="
				+ 20 + "&reverse=" + true;

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

		assertEquals(resultado.getCodigo(), ReturnMessage.EMPTY_SEARCHER);
		assertEquals(resultado.getItems().size(), 0);
	}

	@Test
	public final void deveriaRetornarInvalidQueryQuandoFieldsEhUmaListaVazia()
			throws Exception {
		Map<String, String> fields = new HashMap<String, String>();
		List<String> returnedFields = newRetunedFieldsWithAuthorAndTitle();

		String endereco = "http://localhost:8080/projetorest/searchText?";

		String string = "";

		String json = new Gson().toJson(fields);

		endereco += "fields=" + json + "&returnedFields="
				+ returnedFields.get(0) + "&returnedFields="
				+ returnedFields.get(1) + "&batchStart=" + 1 + "&batchSize="
				+ 20 + "&reverse=" + true;

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

		assertEquals(resultado.getCodigo(), ReturnMessage.INVALID_QUERY);
	}

	private List<String> newRetunedFieldsWithAuthorAndTitle() {
		List<String> returnedFields = new ArrayList<String>();
		returnedFields.add(Metadata.AUTHOR.getValue());
		returnedFields.add(Metadata.TITLE.getValue());
		return returnedFields;
	}

}
