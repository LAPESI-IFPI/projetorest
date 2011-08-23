package br.edu.ifpi.rest;

import static br.com.caelum.vraptor.view.Results.*;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

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

	@Path("/addText")
	public ReturnMessage addText(MetaDocument metaDocument, String content) {

		File path = new File("./webapps/indice/text");
		//File path = new File(br.edu.ifpi.opala.utils.Path.TEXT_INDEX.getValue());
		Directory indexDir;
		ReturnMessage message = null;
		if (metaDocument != null && content != null) {
			try {
				indexDir = FSDirectory.open(path);
				//Directory indexDir = new RAMDirectory();
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
		/*
			HashMap<Integer, String> mensagem = new HashMap<Integer, String>();  
		 
			mensagem.put(message.getCode(), message.getMessage());
			
			Map<String, Object>  mensagem2 = new HashMap<String, Object>();
			mensagem2.put("code", message.getCode());
			mensagem2.put("message", message.getMessage().toString());
			XStream xStream = new XStream(new DomDriver());
            xStream.alias("person", ResultMessage.class);
            System.out.println(xStream.toXML(person));
            */
		result.use(json()).withoutRoot().from(message).serialize();
		return message;
	}
	@Path("/delText")
	public ReturnMessage delText(String id) throws IOException, InterruptedException {
		File path = new File("./webapps/indice/text");
		//File path = new File(br.edu.ifpi.opala.utils.Path.TEXT_INDEX.getValue());
		Directory indexDir;
		ReturnMessage message = null;
		if (id != null) {
			try {
				indexDir = FSDirectory.open(path);
				//Directory indexDir = new RAMDirectory();
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
