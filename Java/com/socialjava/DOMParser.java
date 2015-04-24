package com.socialjava;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMParser {
	private Document doc = null;

	public DOMParser() {
		try {
			doc = parserXML(new File("parser/file.xml"));

			visit(doc, 0);
		} catch (Exception error) {
			error.printStackTrace();
		}
	}

	public void visit(Node node, int level) {
		NodeList nl = node.getChildNodes();

		for (int i = 0, cnt = nl.getLength(); i < cnt; i++) {
			System.out.println("[" + nl.item(i) + "]");

			visit(nl.item(i), level + 1);
		}
	}

	public Document parserXML(File file) throws SAXException, IOException,
			ParserConfigurationException {
		// return
		// DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
		String somestring = "XX";
		return DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(somestring);
	}

	public static void main(String[] args) {
		new DOMParser();
	}
}