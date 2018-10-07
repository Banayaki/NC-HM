package xmlParsers;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;

public class DomReader {

    private Document document;
    private StringBuilder stringBuilder;

    public DomReader(String docName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            this.document = builder.parse(docName);
            this.document.normalizeDocument();
            this.stringBuilder = new StringBuilder();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

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
                    if (Math.abs(realAverage - inputAverage) > Utils.EPS) {
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

    public void printDocumentContent() {
        Element elem = document.getDocumentElement();
        stringBuilder.append("Root element ").append(elem.getTagName()).append("\n");
        getChildNodes(elem);
        System.out.println(stringBuilder.toString());
        stringBuilder.delete(0, stringBuilder.length());
    }


    public void printDocumentContent(String filename) {
        Element elem = document.getDocumentElement();
        stringBuilder.append("Root element ").append(elem.getTagName()).append("\n");
        getChildNodes(elem);
        try (Writer writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
