import org.w3c.dom.Document;
import xmlParsers.DomReader;
import xmlParsers.DomWriter;
import xmlParsers.SaxReader;

public class Runner {

    public static void main(String[] args) {
        saxTest();
        domTest();
    }

    private static void saxTest() {
        SaxReader reader = new SaxReader();
        reader.start("Example_1.xml", "outputSAX.txt");
    }

    private static void domTest() {
        DomReader reader = new DomReader("Example_1.xml");
        reader.printDocumentContent("outputDOM.txt");
        Document correctedDoc = reader.fixIssues();
        DomWriter writer = new DomWriter(correctedDoc);
        writer.createXmlFromDocument("Example_1_Corrected.xml");
    }
}
