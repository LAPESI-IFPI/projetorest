package br.edu.ifpi.rest;

import static br.com.caelum.vraptor.view.Results.json;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.edu.ifpi.opala.indexing.ImageIndexer;
import br.edu.ifpi.opala.indexing.ImageIndexerImpl;
import br.edu.ifpi.opala.utils.Conversor;
import br.edu.ifpi.opala.utils.MetaDocument;
import br.edu.ifpi.opala.utils.ReturnMessage;

@Resource
public class ImageIndexerController {

	private final Result result;

	private static ImageIndexer indexer = ImageIndexerImpl
			.getImageIndexerImpl();

	public ImageIndexerController(Result result) {
		this.result = result;
	}

	@Path("/addImage")
	public void addImage(MetaDocument metaDocument, String image) {

		BufferedImage imagem = null;
		ReturnMessage message;

		if (metaDocument != null && image != null) {

			try {
				imagem = Conversor.byteArrayToBufferedImage(Conversor
						.fileToByteArray(new File(image)));
			} catch (IOException e) {
				e.printStackTrace();
			}

			message = indexer.addImage(metaDocument, imagem);
		} else {
			message = ReturnMessage.PARAMETER_INVALID;
		}

		result.use(json()).withoutRoot().from(message).serialize();
	}

}
