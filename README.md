# Grammar-pool-providing SAX Parser Factory

Xerces XML SAX parser factory that configures XML Readers with a grammar pool for
caching grammars (DTDs, XSDs) during parsing.

This parser factory is designed to be usable from Saxon's command line using the `-x`
command-line parameter to set the parser factory class ([Saxon Documentation]https://www.saxonica.com/documentation11/#!using-xsl/commandline).

For Saxon, because the `-x` flag does not allow also specifying the `-catalog` parameter, you must
use the `catalogfiles` system property to specify the catalog paths to use.

## TODO: Document how to use

TBD
