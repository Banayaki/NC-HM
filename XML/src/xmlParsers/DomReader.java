package xmlParsers;

import org.w3c.dom.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;

/**
 * DOM parser for XML
 * Обработка xml документа
 *
 * @author banayaki
 */
public class DomReader {

    private Document document;
    private StringBuilder stringBuilder;

    public DomReader(String docName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new MyErrorHandler());
            this.document = builder.parse(docName);
            this.document.normalizeDocument();
            this.stringBuilder = new StringBuilder();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Метод проверяет среднюю оценку и корректирует её
     *
     * @return - скорректированный файл
     */
    public Document fixIssues() {
        LinkedList<Integer> marks;
        NodeList students = document.getElementsByTagName("student");
        for (int i = 0; i < students.getLength(); ++i) {
            marks = new LinkedList<>();
            Element student = (Element) students.item(i);

            NodeList subjects = student.getElementsByTagName("subject");
            for (int j = 0; j < subjects.getLength(); ++j) {
                Element subject = (Element) subjects.item(j);
                marks.add(Integer.parseInt(subject.getAttribute("mark")));
            }

            NodeList average = student.getElementsByTagName("average");
            if (marks.size() != 0) {
                double realAverage = marks.stream().mapToDouble(e -> e).average().getAsDouble();

                if (average.getLength() == 0) {
                    @SuppressWarnings("OptionalGetWithoutIsPresent")
                    Element newElement = document.createElement("average");

                    newElement.appendChild(document.createTextNode(String.valueOf(realAverage)));
                    student.appendChild(newElement);
                } else {
                    double inputAverage = Double.parseDouble(student.getElementsByTagName("average")
                            .item(0).getTextContent());
                    if (Double.compare(realAverage, inputAverage) != 0) {
                        average.item(0).getFirstChild().setTextContent(String.valueOf(realAverage));
                    }
                }
            } else {
                if (average.getLength() != 0) {
                    student.removeChild(average.item(0));
                }
            }
        }
        return this.document;
    }

    public void printDocumentContent(String filename) {
        Element elem = document.getDocumentElement();
        stringBuilder.append("Root element ").append(elem.getTagName()).append("\n");
        getChildNodes(elem);
        if (filename == null) {
            System.out.println(stringBuilder.toString());
        } else {
            try (Writer writer = new BufferedWriter(new FileWriter(filename))) {
                writer.write(stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        stringBuilder.delete(0, stringBuilder.length());
    }

    private void getAttributes(Node node) {
        NamedNodeMap attributes = node.getAttributes();
        for (int att = 0; att < attributes.getLength(); ++att) {
            Node item = attributes.item(att);
            stringBuilder.append("\t").append(item.getNodeName()).append(" = ").append(item.getTextContent()).append("\n");
        }
    }

    private void getNodeContent(Node node) {
        stringBuilder.append("\tNode ").append(node.getNodeName()).append("\n");
        getAttributes(node);
        if (node.getNodeName().equals("average")) {
            String content = node.getTextContent();
            if (content != null) {
                stringBuilder.append("\t").append(content).append("\n");
            }
        }
        getChildNodes(node);
    }

    private void getChildNodes(Node node) {
        NodeList nodes = node.getChildNodes();
        for (int i = 0; i < nodes.getLength(); ++i) {
            Node child = nodes.item(i);
            if (!(child instanceof Element)) continue;
            getNodeContent(child);
        }
    }

    private static class MyErrorHandler implements ErrorHandler {
        public void warning(SAXParseException e) throws SAXException {
            show("Warning", e);
            throw (e);
        }

        public void error(SAXParseException e) throws SAXException {
            show("Error", e);
            throw (e);
        }

        public void fatalError(SAXParseException e) throws SAXException {
            show("Fatal Error", e);
            throw (e);
        }

        private void show(String type, SAXParseException e) {
            System.out.println(type + ": " + e.getMessage());
            System.out.println("Line " + e.getLineNumber() + " Column "
                    + e.getColumnNumber());
            System.out.println("System ID: " + e.getSystemId());
        }
    }
}

