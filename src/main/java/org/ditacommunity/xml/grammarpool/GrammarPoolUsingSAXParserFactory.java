package org.ditacommunity.xml.grammarpool;

import org.apache.xerces.util.XMLCatalogResolver;
import org.apache.xerces.util.XMLGrammarPoolImpl;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xml.resolver.CatalogManager;
import org.apache.xml.resolver.tools.CatalogResolver;
import org.apache.xml.resolver.tools.ResolvingXMLReader;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;


/**
 * Configures a SAXParserFactor to use a Xerces grammar pool
 * to cache the DTDs or XSDs of the XML documents parsed by
 * the XML Readers. Uses system property "xmlcatalogs" to
 * specify the list of XML catalog files as for the Saxon
 * -catalog command-line option.
 */
public class GrammarPoolUsingSAXParserFactory extends SAXParserFactory {

    static final String FEATURE_GRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
    //private static final ThreadLocal<XMLGrammarPool> grammarPool = new ThreadLocal<>();
    private static XMLGrammarPool grammarPool = null;
    private static CatalogResolver catalogResolver = null;
    private static CatalogManager catalogManager = null;

    private SAXParserFactory delegate = null;

    public GrammarPoolUsingSAXParserFactory() {
        super();
        this.delegate = SAXParserFactory.newInstance();
    }

    public static SAXParserFactory newInstance() {
        return new GrammarPoolUsingSAXParserFactory();
    };

    @Override
    public SAXParser newSAXParser() throws ParserConfigurationException, SAXException {
        // System.out.println(this.getClass().getSimpleName() + ": Constructing new SAX parser with grammar pool.");
        SAXParser parser = this.delegate.newSAXParser();
        XMLReader reader = parser.getXMLReader();
        CatalogManager manager = getCatalogManager();
        reader.setEntityResolver(new CatalogResolver(manager));
        // Add the grammar pool to the reader
        XMLGrammarPool pool = getGrammarPool();
        try {
            reader.setProperty(FEATURE_GRAMMAR_POOL, pool);
            // logger.info("Using Xerces grammar pool for DTD and schema caching.");
        } catch (final NoClassDefFoundError e) {
            // logger.debug("Xerces not available, not using grammar caching");

        } catch (final SAXNotRecognizedException | SAXNotSupportedException e) {
            // logger.warn("Failed to set Xerces grammar pool for parser: " + e.getMessage());
        }
        return parser;
    }

    static private XMLGrammarPool getGrammarPool() {
        XMLGrammarPool pool = grammarPool; //.get();
        if (pool == null) {
            pool = new XMLGrammarPoolImpl();
            grammarPool = pool; //.set(pool);
        }
        return pool;
    }

    public static synchronized CatalogManager getCatalogManager() {
        if (catalogManager == null) {
            catalogManager = new CatalogManager();
            catalogManager.setIgnoreMissingProperties(true);
            catalogManager.setUseStaticCatalog(false); // We'll use a private catalog.
            catalogManager.setPreferPublic(true);
            String catalogProperty = System.getProperty("xmlcatalogs");
            if (null != catalogProperty) {
                catalogManager.setCatalogFiles(catalogProperty);
            } else {
              // No catalog
            }
            catalogManager.setVerbosity(1);
        }

        return catalogManager;
    }


    @Override
    public void setFeature(String name, boolean value) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
        this.delegate.setFeature(name, value);
    }

    @Override
    public boolean getFeature(String name) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
        return this.delegate.getFeature(name);
    }

}
