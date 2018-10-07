package xmlParsers;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class DomWriter {
    private Document document;

    public DomWriter(Document document) {
        this.document = document;
    }

    public void createXmlFromDocument(String outFileName) {
        try {
            DOMSource domSource = new DOMSource(this.document);
            StreamResult outStream = new StreamResult(outFileName);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();

            DocumentType documentType = this.document.getDoctype();
            String systemID = documentType.getSystemId();
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, systemID);
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.transform(domSource, outStream);

        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
