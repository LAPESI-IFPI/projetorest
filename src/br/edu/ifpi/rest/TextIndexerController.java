package br.edu.ifpi.rest;

import static br.com.caelum.vraptor.view.Results.*;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.edu.ifpi.opala.indexing.NearRealTimeTextIndexer;
import br.edu.ifpi.opala.indexing.TextIndexer;
import br.edu.ifpi.opala.utils.IndexManager;
import br.edu.ifpi.opala.utils.MetaDocument;
import br.edu.ifpi.opala.utils.ReturnMessage;

@Resource
public class TextIndexerController {

	private IndexManager indexManager;

	private final Result result;

	public TextIndexerController(Result result) {
		this.result = result;
	}
	
	@Path("/")
	public void index() {
		result.include("variable", "Projeto REST");
	}

	@Path("/addText")
	public ReturnMessage addText(MetaDocument metaDocument, String content) {

		File path = new File("./webapps/indice/text");
		Directory indexDir;
		ReturnMessage message = null;
		if (metaDocument != null && content != null) {
			try {
				indexDir = FSDirectory.open(path);
				indexManager = new IndexManager(indexDir);
				TextIndexer indexer = new NearRealTimeTextIndexer(indexManager);
				message = indexer.addText(metaDocument, content);
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
		} else {
			message = ReturnMessage.PARAMETER_INVALID;
		}
		result.use(json()).withoutRoot().from(message).serialize();
		return message;
	}
	@Path("/delText")
	public ReturnMessage delText(String id) throws IOException, InterruptedException {
		File path = new File("./webapps/indice/text");
		Directory indexDir;
		ReturnMessage message = null;
		if (id != null) {
			try {
				indexDir = FSDirectory.open(path);
				indexManager = new IndexManager(indexDir);
				TextIndexer indexer = new NearRealTimeTextIndexer(indexManager);
				message = indexer.delText(id);
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
		} else {
			message = ReturnMessage.PARAMETER_INVALID;
		}
		result.use(json()).withoutRoot().from(message).serialize();
		return message;
	}

}
