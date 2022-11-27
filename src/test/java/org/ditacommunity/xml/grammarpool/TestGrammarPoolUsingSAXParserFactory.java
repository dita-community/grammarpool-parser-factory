package org.ditacommunity.xml.grammarpool;

import org.apache.xerces.parsers.XMLParser;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xml.resolver.tools.CatalogResolver;
import org.junit.Test;
import org.xml.sax.HandlerBase;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.net.URL;
import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestGrammarPoolUsingSAXParserFactory {

    @Test
    public void getParser() throws Exception {
        URL catalogURL = this.getClass().getClassLoader().getResource("catalog-dita.xml");
        URL topicURL = this.getClass().getClassLoader().getResource("topics/test-topic.dita");

        System.setProperty("xmlcatalogs", catalogURL.toExternalForm());

        GrammarPoolUsingSAXParserFactory factory = (GrammarPoolUsingSAXParserFactory)GrammarPoolUsingSAXParserFactory.newInstance();
        assertNotNull("Expected a factory instance", factory);
        SAXParser parser = factory.newSAXParser();
        assertNotNull("Expected a parser", parser);
        Object pool = parser.getProperty(GrammarPoolUsingSAXParserFactory.FEATURE_GRAMMAR_POOL);
        assertNotNull("Expected a pool object", pool);
        assertTrue("Expected a grammar pool", pool instanceof XMLGrammarPool);
        XMLGrammarPool grammarPool = (XMLGrammarPool)pool;

        Object resolver = parser.getXMLReader().getEntityResolver();
        assertNotNull("Expected a resolver", resolver);
        String url = topicURL.toExternalForm();

        DefaultHandler handler = new DefaultHandler();

        Date date = new Date();
        System.out.println("First parse:" + (new Date()).getTime());
        parser.getXMLReader().parse(url);
        grammarPool.lockPool();
        System.out.println("Second parse:" + (new Date()).getTime());
        parser.getXMLReader().parse(url);
        System.out.println("After second parse:"+ (new Date()).getTime());

    }

}
