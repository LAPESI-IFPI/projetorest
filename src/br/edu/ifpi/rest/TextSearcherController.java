package br.edu.ifpi.rest;

import static br.com.caelum.vraptor.view.Results.xml;
import static br.com.caelum.vraptor.view.Results.json;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.edu.ifpi.opala.searching.NearRealTimeTextSearcher;
import br.edu.ifpi.opala.searching.SearchResult;
import br.edu.ifpi.opala.searching.TextSearcher;
import br.edu.ifpi.opala.utils.IndexManager;
import br.edu.ifpi.opala.utils.Metadata;

@Resource
public class TextSearcherController {

	private IndexManager indexManager;

	private final Result result;

	public TextSearcherController(Result result) {
		this.result = result;
	}

	@Path("/searchText")
	public SearchResult searchText(String fields,
			List<String> returnedFields, int batchStart, int batchSize,
			String sortOn, boolean reverse) {
		
		Gson gson = new Gson();
		Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> map = gson.fromJson(fields, type);
		/*
		if (fields!=null){
			result.use(xml()).from(fields).serialize();
			return null;
		}*/
	
		File path = new File("./webapps/indice/text");
		// File path = new
		// File(br.edu.ifpi.opala.utils.Path.TEXT_INDEX.getValue());
		Directory indexDir;
		SearchResult resultado = null;

		try {
			indexDir = FSDirectory.open(path);
			// Directory indexDir = new RAMDirectory();
			indexManager = new IndexManager(indexDir);

			//Map<String, String> fields = new HashMap<String, String>();
			//fields.put(Metadata.CONTENT.getValue(), "palavra");

			//List<String> returnedFields = newRetunedFieldsWithAuthorAndTitle();

			TextSearcher searcher = new NearRealTimeTextSearcher(indexManager);

			resultado = searcher.search(map, returnedFields, batchStart, batchSize,
					sortOn, reverse);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				indexManager.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/*
		 * if (resultado==null){
		 * resultado.setCodigo(ReturnMessage.EMPTY_SEARCHER); }
		 */
		/*
		 * System.out.println(resultado.getCodigo());
		 * System.out.println(resultado.getItems().size());
		 * System.out.println(resultado.getItems().iterator().next()
		 * .getField(Metadata.AUTHOR.getValue()));
		 * System.out.println(resultado.getItems().iterator().next()
		 * .getField(Metadata.TITLE.getValue()));
		 * System.out.println(resultado.getItems().iterator().next()
		 * .getField(Metadata.KEYWORDS.getValue()));
		 */
		String response = new Gson().toJson(resultado);
		result.include("variable", response);
		//result.use(json()).from(resultado).include("items","items.fields").serialize();
		//result.use(json()).withoutRoot().from(response).serialize();
		return resultado;
	}

	private static List<String> newRetunedFieldsWithAuthorAndTitle() {
		List<String> returnedFields = new ArrayList<String>();
		returnedFields.add(Metadata.AUTHOR.getValue());
		returnedFields.add(Metadata.TITLE.getValue());
		return returnedFields;
	}
}
