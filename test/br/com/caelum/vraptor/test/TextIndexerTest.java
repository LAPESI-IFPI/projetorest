package br.com.caelum.vraptor.test;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import br.edu.ifpi.opala.indexing.NearRealTimeTextIndexer;
import br.edu.ifpi.opala.indexing.TextIndexer;
import br.edu.ifpi.opala.utils.IndexManager;
import br.edu.ifpi.opala.utils.MetaDocument;
import br.edu.ifpi.opala.utils.MetaDocumentBuilder;
import br.edu.ifpi.opala.utils.ReturnMessage;

public class TextIndexerTest {
	
	private static IndexManager indexManager;
	
	private static String CONTENT = "a palavra do document a ser indexado nos testes";
	private static MetaDocument METADOC = new MetaDocumentBuilder()
											.id("7")
											.title("Título do documento de teste")
											.author("Autor do documento de teste")
											.build();
	
	public static void main(String[] args) {
		
		ReturnMessage message = addText(METADOC,CONTENT);
		System.out.println(message);
	}
	
	public static ReturnMessage addText(MetaDocument metaDocument, String content){
		String string = br.edu.ifpi.opala.utils.Path.TEXT_INDEX.getValue();
		System.out.println(string);
		File path = new File(br.edu.ifpi.opala.utils.Path.TEXT_INDEX.getValue());
		Directory indexDir;
		ReturnMessage message = null;
		try {
			indexDir = FSDirectory.open(path);
			indexManager = new IndexManager(indexDir);
			TextIndexer indexer = new NearRealTimeTextIndexer(indexManager);
			message = indexer.addText(metaDocument, content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return message;
	}
}
