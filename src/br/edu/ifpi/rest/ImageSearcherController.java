package br.edu.ifpi.rest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import com.google.gson.Gson;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.edu.ifpi.opala.searching.ImageSearch;
import br.edu.ifpi.opala.searching.SearchResult;
import br.edu.ifpi.opala.utils.Conversor;

@Resource
public class ImageSearcherController {

	private final Result result;

	static ImageSearch searcher = new ImageSearch("webapps/indice/image");
	SearchResult resultado;

	public ImageSearcherController(Result result) {
		this.result = result;
	}

	@Path("/searchImage")
	public void searchImage(String image, int limit) throws IOException {

		BufferedImage imagem = null;

		try {
			imagem = Conversor.byteArrayToBufferedImage(Conversor
					.fileToByteArray(new File(image)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		resultado = searcher.search(imagem, limit);
		String response = new Gson().toJson(resultado);

		result.include("variable",response);

	}

}
