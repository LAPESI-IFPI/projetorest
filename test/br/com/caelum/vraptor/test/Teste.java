package br.com.caelum.vraptor.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import br.edu.ifpi.opala.indexing.ImageIndexer;
import br.edu.ifpi.opala.indexing.ImageIndexerImpl;
import br.edu.ifpi.opala.searching.ImageSearch;
import br.edu.ifpi.opala.searching.SearchResult;
import br.edu.ifpi.opala.searching.SearcherImageImpl;

public class Teste {
	
	private static ImageIndexer indexer = ImageIndexerImpl
	.getImageIndexerImpl();
	static SearcherImageImpl searcher = new SearcherImageImpl();
	static ImageSearch searcher2 = new SearcherImageImpl().getImageSearch();
	Map<String, String> fields = new HashMap<String, String>();
	List<String> returnedFields = new ArrayList<String>();
	static SearchResult searchResult;
	
	
	
	public static void main(String[] args) throws Exception{
		
		//searchResult = searcher.search(ImageIO.read(new File(PathTest.IMAGE_REPOSITORY_TEST.getValue()+"1.jpg")), 10);
		searchResult = searcher.search(ImageIO.read(new File("WebContent/resources/image/1.jpg")), 10);
		System.out.println(searchResult.getCodigo());
	}
	
}