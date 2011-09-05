package br.com.caelum.vraptor.test;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.gson.Gson;



import br.edu.ifpi.opala.indexing.ImageIndexer;
import br.edu.ifpi.opala.indexing.ImageIndexerImpl;
import br.edu.ifpi.opala.searching.ImageSearch;
import br.edu.ifpi.opala.searching.SearchResult;
import br.edu.ifpi.opala.searching.SearcherImageImpl;
import br.edu.ifpi.opala.utils.Path;
import br.edu.ifpi.opala.utils.Util;

public class Teste {
	
	private static ImageIndexer indexer = ImageIndexerImpl
	.getImageIndexerImpl();
	static SearcherImageImpl searcher = new SearcherImageImpl();
	static ImageSearch searcher2 = new SearcherImageImpl().getImageSearch();
	static ImageSearch searcher3 = new ImageSearch("E:/Arquivos de Programas/eclipse/webapps/indice/image");
	static Map<String, String> fields = new HashMap<String, String>();
	static List<String> returnedFields = new ArrayList<String>();
	static SearchResult searchResult;
	
	
	
	public static void main(String[] args) throws Exception{
		
		//System.out.println(new File("opala.conf").getCanonicalPath());
		
		File imageIndex = new File(Path.IMAGE_INDEX.getValue());
		System.out.println(imageIndex.getAbsolutePath());
		System.out.println(Util.deleteDir(imageIndex));
		File imageRepository = new File(PathTest.IMAGE_REPOSITORY_TEST.getValue());
		System.out.println(imageRepository.exists());
		System.out.println(imageRepository.listFiles().length);
		fields.put("id", "1");
		returnedFields.add("id");
		searchResult = searcher3.search(ImageIO.read(new File(PathTest.IMAGE_REPOSITORY_TEST.getValue()+"2 igual a 1.jpg")), 10);
		//searchResult = searcher.search(fields, returnedFields, 1, 10, null);
		System.out.println(searchResult.getCodigo());
		System.out.println(PathTest.IMAGE_REPOSITORY_TEST.getValue()+"2 igual a 1.jpg");
		String response = new Gson().toJson(searchResult);
		System.out.println(response);
		
	}
}