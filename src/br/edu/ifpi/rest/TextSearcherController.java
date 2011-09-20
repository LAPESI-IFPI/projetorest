package br.edu.ifpi.rest;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
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
	
		File path = new File("./webapps/indice/text");
		Directory indexDir;
		SearchResult resultado = null;

		try {
			indexDir = FSDirectory.open(path);
			indexManager = new IndexManager(indexDir);
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
		
		String response = new Gson().toJson(resultado);
		result.include("variable", response);
		return resultado;
	}
}
