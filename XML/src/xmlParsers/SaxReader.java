package xmlParsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;


/**
 * SAX парсер.
 * Класс предназначенный для парсинга xml файла.
 * Проверяет совпадает ли среднее значение оценок студента по предметам с введённой в xml-файл.
 * Ничего особенного в себе класс не содержит, просто демонстрация работы.
 *
 * @see DefaultHandler
 * @author banayaki
 */
public class SaxReader extends DefaultHandler {
    private SAXParser parser;
    /** Будем использовать для сохранения оценок */
    private LinkedList<Integer> marks;
    /** Должен помочь вывести результат парсинга в верном порядке */
    private StringBuilder stringBuilder = new StringBuilder();
    private boolean isWasInAverage;

    public SaxReader() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(true);
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Собственно запускает парсинг указанного файла. По хорошему надо бы проверить существование этого файла,
     * формат. Ноо... Смысл работы вряд ли состоит в этом
     *
     * @param fileName - целевой файл для парсинга
     * @param output - если указан - то вывод будет происходить в этот файл
     */
    public void start(String fileName, String output) {
        try {
            File f = new File(fileName);
            this.parser.parse(f, this);
            if (output == null) {
                System.out.println(stringBuilder.toString());
            } else {
                var writer = new BufferedWriter(new FileWriter(output));
                writer.write(stringBuilder.toString());
                writer.close();
            }
            stringBuilder.delete(0, stringBuilder.length());
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startDocument() {
        stringBuilder.append("Start of document").append('\n');
    }

    @Override
    public void endDocument() {
        stringBuilder.append("End of document").append('\n');
    }

    /**
     * Вызывается при событии: "начало элемента" (кэп)
     * Форматирует строку с сообщением для дальнейшего вывода и совершает некоторые действия при некоторых тегах.
     * Ести студент, то сбрасываем значение фалага и обновляем наш лист с оценками
     * Если встретили аттрибут 'mark' - то сохраним его значение в лист с оценками
     *
     * @param qName - название тега
     * @param attributes - аттрибуты тега
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        int length = attributes.getLength();
        String value;
        String attName;
        switch (qName) {
            case "student":
                isWasInAverage = false;
                stringBuilder.append("\t").append(qName).append('\n');
                marks = new LinkedList<>();
                for (int i = 0; i < length; ++i) {
                    stringBuilder.append("\t\t").append(attributes.getValue(i)).append('\n');
                }
                break;
            case "group":
                stringBuilder.append(qName).append('\n');
                break;
            default:
                stringBuilder.append("\t\t").append(qName).append('\n');
                for (int i = 0; i < length; ++i) {
                    attName = attributes.getQName(i);
                    value = attributes.getValue(i);
                    if (attName.equals("mark")) {
                        marks.add(Integer.parseInt(value));
                    }
                    stringBuilder.append("\t\t\t").append(value).append('\n');
                }
                break;
        }
    }

    /**
     * Формирует сообщение об окончании тега.
     * Так же небольшая проверка налачия тега 'average'
     */
    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals("student") && !isWasInAverage && marks.size() != 0) {
            double average = findAverage();
            stringBuilder.append("!!!Need to add <average>").append(average).append("</average>!!!").append('\n');
            stringBuilder.append("\t").append(qName).append('\n');
        } else if (qName.equals("student")) {
            stringBuilder.append("\t").append(qName).append('\n');
        } else if (qName.equals("group")) {
            stringBuilder.append(qName).append('\n');
        } else {
            stringBuilder.append("\t\t").append(qName).append('\n');
        }
    }

    /**
     * Сюда по сути мы попадаем только дойдя до тега 'average' - а значит пришло время проверять
     * наше среднее арифметическое оценок
     */
    @Override
    public void characters(char[] ch, int start, int length) {
        this.isWasInAverage = true;
        double inputAverage = Double.parseDouble(new String(ch, start, length));
        double realAverage = findAverage();
        if (realAverage == 0) {
            stringBuilder.append("!!!need to remove average tag!!!").append('\n');
        } else if (Math.abs(realAverage - inputAverage) < Utils.EPS) {
            stringBuilder.append("\t\t\t").append(inputAverage).append(" is correct average").append('\n');
        } else {
            stringBuilder.append("!!!correct average is ").append(realAverage).append(" !!!").append('\n');
        }
    }

    @Override
    public void warning(SAXParseException e) {
        stringBuilder.append(e.getMessage()).append('\n');
        e.printStackTrace();
    }

    @Override
    public void error(SAXParseException e) {
        stringBuilder.append(e.getMessage()).append('\n');
        e.printStackTrace();

    }

    @Override
    public void fatalError(SAXParseException e) {
        stringBuilder.append(e.getMessage()).append('\n');
        e.printStackTrace();

    }

    /**
     * @return - выводит среднее значение чисел из листа
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private double findAverage() {
        return marks.stream().mapToDouble(e -> e).average().getAsDouble();
    }

}
