package demo.service;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.commons.io.FilenameUtils;
import org.apache.fontbox.ttf.OTFParser;
import org.apache.fontbox.ttf.OpenTypeFont;
import org.apache.fop.svg.PDFTranscoder;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.apache.pdfbox.util.Matrix;

import com.google.gson.Gson;

import demo.dto.CardInfoDTO;
import demo.dto.ElementDTO;
import demo.dto.SideDTO;
import demo.dto.TemplateDTO;

public class PDFService {
	private static final float WIDTH = 100;

	public File createPDF(String pathFile, CardInfoDTO cardInfo)
			throws IOException, TranscoderException, URISyntaxException {

		Gson gson = new Gson();
		
		TemplateDTO template = gson.fromJson(new FileReader(cardInfo.getDataJson()), TemplateDTO.class);

		File file = new File(pathFile);
		try (PDDocument doc = new PDDocument()) {
			PDPage myPage = new PDPage(PDRectangle.A4);
			doc.addPage(myPage);
			OTFParser otfParser = new OTFParser();

			PDFont font = getFont(doc, otfParser, "A-OTF-ShinGoPro-Light", cardInfo.getUrlFont());

			try (PDPageContentStream cont = new PDPageContentStream(doc, myPage)) {
				cont.setLineWidth(0.1f);

				// draw line horizontal
				float widthPageLine = myPage.getMediaBox().getWidth();
				float lineHorizontal = Constant.LINE_HORIZONTAL * Constant.POINTS_PER_MM;
				drawLineHorizontal(cont, widthPageLine, 5 * Constant.POINTS_PER_MM, lineHorizontal);
				drawLineHorizontal(cont, widthPageLine, 60 * Constant.POINTS_PER_MM, lineHorizontal);
				drawLineHorizontal(cont, widthPageLine, 63 * Constant.POINTS_PER_MM, lineHorizontal);
				drawLineHorizontal(cont, widthPageLine, 118 * Constant.POINTS_PER_MM, lineHorizontal);
				drawLineHorizontal(cont, widthPageLine, 121 * Constant.POINTS_PER_MM, lineHorizontal);
				drawLineHorizontal(cont, widthPageLine, 176 * Constant.POINTS_PER_MM, lineHorizontal);
				drawLineHorizontal(cont, widthPageLine, 179 * Constant.POINTS_PER_MM, lineHorizontal);
				drawLineHorizontal(cont, widthPageLine, 234 * Constant.POINTS_PER_MM, lineHorizontal);
				drawLineHorizontal(cont, widthPageLine, 237 * Constant.POINTS_PER_MM, lineHorizontal);
				drawLineHorizontal(cont, widthPageLine, 292 * Constant.POINTS_PER_MM, lineHorizontal);

				// draw line vertical
				float heightPageLine = myPage.getMediaBox().getHeight();
				float lineVertical = Constant.LINE_VERTICAL * Constant.POINTS_PER_MM;
				drawLineVertical(cont, heightPageLine, 9 * Constant.POINTS_PER_MM, lineVertical);
				drawLineVertical(cont, heightPageLine, 100 * Constant.POINTS_PER_MM, lineVertical);
				drawLineVertical(cont, heightPageLine, 110 * Constant.POINTS_PER_MM, lineVertical);
				drawLineVertical(cont, heightPageLine, 201 * Constant.POINTS_PER_MM, lineVertical);

				float scale = Constant.SVG_WIDTH / 94;

				SideDTO side = template.getSides().stream().filter(s -> s.getSide() == cardInfo.getCardSide())
						.findFirst().get();
				// draw card left
				drawCard(doc, otfParser, cont, cardInfo, scale, side.getElements(), 7.5f, 3.5f);
				drawCard(doc, otfParser, cont, cardInfo, scale, side.getElements(), 7.5f, 61.5f);
				drawCard(doc, otfParser, cont, cardInfo, scale, side.getElements(), 7.5f, 119.5f);
				drawCard(doc, otfParser, cont, cardInfo, scale, side.getElements(), 7.5f, 177.5f);
				drawCard(doc, otfParser, cont, cardInfo, scale, side.getElements(), 7.5f, 235.5f);

				// draw card right
				drawCard(doc, otfParser, cont, cardInfo, scale, side.getElements(), 108.5f, 3.5f);
				drawCard(doc, otfParser, cont, cardInfo, scale, side.getElements(), 108.5f, 61.5f);
				drawCard(doc, otfParser, cont, cardInfo, scale, side.getElements(), 108.5f, 119.5f);
				drawCard(doc, otfParser, cont, cardInfo, scale, side.getElements(), 108.5f, 177.5f);
				drawCard(doc, otfParser, cont, cardInfo, scale, side.getElements(), 108.5f, 235.5f);

				// draw square
				// ve hinh vuong 6mm, vị tri (x, y) = (102,286), toa do tu goc trai duoi man hinh
				cont.addRect(102f * Constant.POINTS_PER_MM, 286f * Constant.POINTS_PER_MM, 6 * Constant.POINTS_PER_MM,
						6 * Constant.POINTS_PER_MM);
				cont.fill();

				// draw footer
				// 10: fontsize chu,
				cont.beginText();
				cont.newLineAtOffset(1.5f * Constant.POINTS_PER_MM, (3.5f - 10 / scale) * Constant.POINTS_PER_MM);
				cont.setFont(font, 10 / scale * Constant.POINTS_PER_MM);
				cont.showText(cardInfo.getOrderId() + cardInfo.getOrderNumber() + cardInfo.getOrderDate());
				cont.endText();

				cont.close();

			}
			doc.save(file);
		}
		return file;
	}

	private void drawCard(PDDocument doc, OTFParser otfParser, PDPageContentStream cont, CardInfoDTO cardInfo,
			float scale, List<ElementDTO> elements, float x, float y)
			throws IOException, TranscoderException {
		for (ElementDTO item : elements) {
			transformBeforeCard(cont, item, x, y, scale);
			drawElement(doc, otfParser, cont, cardInfo, scale, item);
			transformAfterCard(cont, item, scale, x, y);
		}
	}

	private void drawElement(PDDocument doc, OTFParser otfParser, PDPageContentStream cont, CardInfoDTO cardInfo,
			float scale, ElementDTO item) throws IOException, TranscoderException {
		if (item.getItemType() == 1) {
			String content = getCardInfoText(item.getItemName(), cardInfo);
			if (content != null) {
				PDFont font = getFont(doc, otfParser, item.getFont(), cardInfo.getUrlFont());
				drawCardText(cont, font, scale, item, content);
			}
		}
		if (item.getItemType() == 2) {
			File file = getCardInfoImage(item.getItemName(), cardInfo);
			if (file != null) {
				drawCardImage(doc, cont, getImage(file), scale, item);
			}
		}
	}

	private void drawCardText(PDPageContentStream cont, PDFont font, float scale, ElementDTO item, String content)
			throws IOException {
		cont.beginText();
		cont.newLineAtOffset(0, -item.getFontsize() / scale * Constant.POINTS_PER_MM);
		cont.setFont(font, item.getFontsize() / scale * Constant.POINTS_PER_MM);
		cont.showText(content);
		cont.endText();
	}

	private void drawCardImage(PDDocument doc, PDPageContentStream cont, String pathFile, float scale, ElementDTO item)
			throws IOException {
		PDImageXObject pdImage = PDImageXObject.createFromFile(pathFile, doc);
		cont.drawImage(pdImage, 0, -item.getHeightMM() / scale * Constant.POINTS_PER_MM,
				(item.getWidthMM() / scale) * Constant.POINTS_PER_MM,
				(item.getHeightMM() / scale) * Constant.POINTS_PER_MM);
		System.out.println((item.getWidthMM() / scale) * Constant.POINTS_PER_MM);
	}

	private void drawLineHorizontal(PDPageContentStream cont, float widthPage, float cardY, float lineHorizontal)
			throws IOException {
		cont.moveTo(0, cardY);
		cont.lineTo(lineHorizontal, cardY);
		cont.moveTo(widthPage - lineHorizontal, cardY);
		cont.lineTo(widthPage, cardY);
		cont.stroke();
	}

	private void drawLineVertical(PDPageContentStream cont, float heightPage, float cardX, float lineVertical)
			throws IOException {
		cont.moveTo(cardX, 0);
		cont.lineTo(cardX, lineVertical);
		cont.moveTo(cardX, heightPage - lineVertical);
		cont.lineTo(cardX, heightPage);
		cont.stroke();
	}

	private PDFont getFont(PDDocument doc, OTFParser otfParser, String fontName, String url)
			throws IOException {
		try {
			/*OpenTypeFont otf = otfParser.parse(Paths.get(url, fontName + ".otf").toFile());*/
			return PDType0Font.load(doc, otfParser.parse(Paths.get(url, fontName + ".otf").toFile()), false);
		} catch (FileNotFoundException e) {
			return PDType0Font.load(doc,otfParser.parse(Paths.get(url, "A-OTF-ShinGoPro-Light.otf").toFile()),false);
		}
	}

	private String getImage(File file) throws IOException, TranscoderException {

		String fileName = file.getParentFile() + "/" + FilenameUtils.getBaseName(file.getName());
		File pdf = new File(fileName + ".pdf");
		if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("svg")) {
			Transcoder transcoder = new PDFTranscoder();
			TranscoderInput transcoderInput = new TranscoderInput(new FileInputStream(file));
			TranscoderOutput transcoderOutput = new TranscoderOutput(new FileOutputStream(pdf));
			transcoder.transcode(transcoderInput, transcoderOutput);

			PDDocument document = PDDocument.load(pdf);
			PDFRenderer pdfRenderer = new PDFRenderer(document);
			for (int page = 0; page < document.getNumberOfPages(); ++page) {
				BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);

				// suffix in filename will be used as the file format
				ImageIOUtil.writeImage(bim, fileName + ".png", 300);
			}
			document.close();

			return fileName + ".png";
		}

		return file.getAbsolutePath();
	}

	private void transformBeforeCard(PDPageContentStream cont, ElementDTO item, float x, float y, float scale)
			throws IOException {
		cont.transform(Matrix.getRotateInstance(Math.toRadians(item.getRotate()),
				(x + item.getxMM() / scale) * Constant.POINTS_PER_MM,
				(Constant.A4_HEIGHT - y - item.getyMM() / scale) * Constant.POINTS_PER_MM));
	}

	private void transformAfterCard(PDPageContentStream cont, ElementDTO item, float scale, float x, float y)
			throws IOException {
		cont.transform(Matrix.getRotateInstance(-Math.toRadians(item.getRotate()), 0, 0));
		cont.transform(Matrix.getRotateInstance(0, -(x + item.getxMM() / scale) * Constant.POINTS_PER_MM,
				-(Constant.A4_HEIGHT - y - item.getyMM() / scale) * Constant.POINTS_PER_MM));
	}

	private String getCardInfoText(String itemName, CardInfoDTO cardInfo) {
		if (itemName.equals("companyName")) {
			return cardInfo.getCompanyName();
		}
		if (itemName.equals("fullName")) {
			return cardInfo.getFullName();
		}
		return null;
	}

	private File getCardInfoImage(String itemName, CardInfoDTO cardInfo) {
		if (itemName.equals("avatar")) {
			return cardInfo.getAvatar();
		}
		if (itemName.equals("logo")) {
			return cardInfo.getLogo();
		}
		if (itemName.equals("background")) {
			return cardInfo.getBackground();
		}
		return null;
	}
}
