package action;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.xml.XMLConstants;

public class XMLParse {
	private static final String USER_DIR = System.getProperty("user.dir");
	private static final String FILENAME = USER_DIR + "/file.xml";

	public String getRandomWord() {
		System.out.println("a: " + FILENAME);
		// Instantiate the Factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			// process XML securely, avoid attacks like XML External Entities (XXE)
			dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

			// parse XML file
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File(FILENAME));

			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			// get <word>
			NodeList list = doc.getElementsByTagName("word");

			Random random = new Random();
			int r = random.nextInt(list.getLength());

			Node node = list.item(r);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				return element.getTextContent();
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}