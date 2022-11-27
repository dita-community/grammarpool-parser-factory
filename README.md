# Grammar-pool-providing SAX Parser Factory

Xerces XML SAX parser factory that configures XML Readers with a grammar pool for
caching grammars (DTDs, XSDs) during parsing.

This parser factory is designed to be usable from Saxon's command line using the `-x`
command-line parameter to set the parser factory class ([Saxon Documentation]https://www.saxonica.com/documentation11/#!using-xsl/commandline).

For Saxon, because the `-x` flag does not allow also specifying the `-catalog` parameter, you must
use the `catalogfiles` system property to specify the catalog paths to use.

## Release notes

* 0.9.0 - First release.

## Use with Saxon from command line

To use with Saxon, include the `grammarpool-parser-factory-jar-with-dependencies.jar` JAR in your class path and specify the `-x` parameter to Saxon.

In this example, Saxon has been put in `~/apps/SaxonHE11-4J`, the `grammarpool-parser-factory-jar-with-dependencies.jar` jar has been copied to Saxon's lib/ dir, and the command is run from this project's `source/test/resources` directory. The command puts the output to stdout just to demonstrate that the transform is run:

```
% java -Dxmlcatalogs=$PWD/catalog-dita.xml \
-cp "${HOME}/apps/SaxonHE11-4J/saxon-he-11.4.jar:${HOME}/apps/SaxonHE11-4J/lib/*" \
net.sf.saxon.Transform \
-x:org.ditacommunity.xml.grammarpool.GrammarPoolUsingSAXParserFactory \
-xsl:$PWD/xsl/dita-identity.xsl $PWD//topics/test-topic.dita
<?xml version="1.0" encoding="UTF-8"?>
<concept id="test-topic">
   <title>Test Topic</title>
   <shortdesc>Test parsing of topics</shortdesc>
   <prolog>
      <author>DITA Community</author>
      <critdates>
         <created date="2022-11-04T16:22:59-05:00"/>
         <revised modified="2022-11-04"/>
      </critdates>
      <metadata>
         <keywords/>
      </metadata>
   </prolog>
   <conbody>
      <p>No useful content.</p>
   </conbody>
</concept>
% _
```

Note the use of the Java `-D` parameter to set the value of the `xmlcatalogs` system property used by the parser factory to configure the XML catalog resolver:

```
java -Dxmlcatalogs=$PWD/catalog-dita.xml
```

This use of a system property replaces the normal `-catalog` Saxon parameter, which cannot be used if the Saxon `-x` parameter is specified.

For DITA you would normally use the `catalog-dita.xml` file from whatever Open Toolkit you use to process your DITA source.

## Building

This is a Maven project.

Code under development is merged to the `develop` branch from feature branches. 

Contributions must be via pull request and commits must be signed.

Use `mvn clean install` to build the jar locally. The tests should pass.

To make a release package, use `mvn clean package`

## Release

To deploy to the public Maven repository use this command line:

```
mvn clean deploy
```

(Note: Only the project owner can do this as it requires being able to sign the jar.)