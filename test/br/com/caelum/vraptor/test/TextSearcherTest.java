package br.com.caelum.vraptor.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import br.edu.ifpi.opala.searching.NearRealTimeTextSearcher;
import br.edu.ifpi.opala.searching.ResultItem;
import br.edu.ifpi.opala.searching.SearchResult;
import br.edu.ifpi.opala.searching.TextSearcher;
import br.edu.ifpi.opala.utils.IndexManager;
import br.edu.ifpi.opala.utils.Metadata;

public class TextSearcherTest {
	private static IndexManager indexManager;

	public static void main(String[] args) throws IOException {
		SearchResult resultado = null;
		try{
		File path = new File(br.edu.ifpi.opala.utils.Path.TEXT_INDEX.getValue());
		Directory indexDir = FSDirectory.open(path);
		indexManager = new IndexManager(indexDir);

		Map<String, String> fields = new HashMap<String, String>();
		fields.put(Metadata.CONTENT.getValue(), "conteúdo");

		List<String> returnedFields = newRetunedFieldsWithAuthorAndTitle();

		TextSearcher searcher = new NearRealTimeTextSearcher(indexManager);

		resultado = searcher.search(fields, returnedFields, 1, 10,
				Metadata.ID.getValue(), false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try {
				indexManager.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(resultado.getCodigo());
		System.out.println(resultado.getItems().size());
		for (ResultItem result : resultado.getItems()) {
			System.out.println(result.getId());
		}
		
		}

	private static List<String> newRetunedFieldsWithAuthorAndTitle() {
		List<String> returnedFields = new ArrayList<String>();
		returnedFields.add(Metadata.AUTHOR.getValue());
		returnedFields.add(Metadata.TITLE.getValue());
		return returnedFields;
	}

}
