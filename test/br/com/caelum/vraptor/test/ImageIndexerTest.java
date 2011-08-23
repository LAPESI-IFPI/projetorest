package br.com.caelum.vraptor.test;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



import br.edu.ifpi.opala.indexing.ImageIndexer;
import br.edu.ifpi.opala.indexing.ImageIndexerImpl;
import br.edu.ifpi.opala.utils.Conversor;
import br.edu.ifpi.opala.utils.MetaDocument;
import br.edu.ifpi.opala.utils.Path;
import br.edu.ifpi.opala.utils.ReturnMessage;
import br.edu.ifpi.opala.utils.Util;

import com.sun.image.codec.jpeg.ImageFormatException;

public class ImageIndexerTest {
	
	static MetaDocument metaDocument;
	static BufferedImage image;
	private static ImageIndexer indexer = ImageIndexerImpl.getImageIndexerImpl();
	
	private int addImage(MetaDocument metaDocument, byte[] imageByte) {
		if (metaDocument.getId().equals(""))
			return ReturnMessage.EMPTY_ID.getCode();
		if (new String(imageByte).trim().equals(""))
			return ReturnMessage.EMPTY_CONTENT.getCode();

		BufferedImage imageBuf = null;
		try {
			imageBuf = Conversor.byteArrayToBufferedImage(imageByte);
		} catch (ImageFormatException e1) {
			return ReturnMessage.FORMAT_EXCEPTION.getCode();
		}
		return ImageIndexerImpl.getImageIndexerImpl().addImage(metaDocument,
				imageBuf).getCode();

	}

	public static void indexImages() {
		File imageIndex = new File(Path.IMAGE_INDEX.getValue());
		Util.deleteDir(imageIndex);
		File imageRepository = new File(PathTest.IMAGE_REPOSITORY_TEST.getValue());
		System.out.println(imageRepository.exists());
		System.out.println(imageRepository.listFiles().length);

		metaDocument = new MetaDocument();
		metaDocument.setTitle("Documento de teste");
		metaDocument.setId("doc1");

		try {
			image = Conversor.byteArrayToBufferedImage(Conversor
					.fileToByteArray(new File(PathTest.IMAGE_REPOSITORY_TEST
							.getValue()+"image (6).jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void addImage() {
		ReturnMessage message;
		
		//File imageIndex = new File("webapps/indice/image");
		//Util.deleteDir(imageIndex);
		File imageRepository = new File("E:/Meus Documentos/IFPI/JAVA/projetorest/WebContent/resources/image");
		System.out.println(imageRepository.exists());
		System.out.println(imageRepository.listFiles().length);

		
		try {
			image = Conversor.byteArrayToBufferedImage(Conversor
					.fileToByteArray(new File("E:/Meus Documentos/IFPI/JAVA/projetorest/WebContent/resources/image/image (6).jpg")));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		metaDocument = new MetaDocument();
		metaDocument.setTitle("Documento de teste2");
		metaDocument.setId("doc2");
		
		
		
		message = indexer.addImage(metaDocument, image);
		System.out.println(message);
	}
	
	public static void testIndexacaoImagemGrande(){
		File fileImagemGrande = new File(PathTest.IMAGE_REPOSITORY_TEST.getValue()+"image (13).bmp");
		ImageIndexer indexer = ImageIndexerImpl.getImageIndexerImpl();
		
			MetaDocument metaDocument = new MetaDocument();
			metaDocument.setId(fileImagemGrande.getName());
			ReturnMessage result = null;
			try {
				BufferedImage buf = ImageIO.read(fileImagemGrande);
				if (buf != null)
				result = indexer.addImage(metaDocument, buf);
			} catch (Exception e) {
				System.out.println(fileImagemGrande.getName()+" ERROR " + result);
				e.printStackTrace();
			}

			assertEquals(ReturnMessage.SUCCESS, result);
	}
	
	public static void main(String[] args) {
		addImage();
		//testIndexacaoImagemGrande();
	}
	
}
