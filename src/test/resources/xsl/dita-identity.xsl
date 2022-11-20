<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xs="http://www.w3.org/2001/XMLSchema"
  xmlns:ditaarch="http://dita.oasis-open.org/architecture/2005/"
  exclude-result-prefixes="xs ditaarch"
  version="3.0">
  <!-- ===============================================================
       Simple identity transform that filters out DITA attributes
       that should never be in instances (@class, @domains, etc.)
       =============================================================== -->
  
  <!-- Indent so the result is easier to read. In production you would
       not normally do indention.
    -->
  <xsl:output indent="yes"/>
  
  <xsl:mode on-no-match="shallow-copy"
  />
  
  <!-- Explicitly get rid of the ditaarch namespace declaration -->
  <xsl:template match="*">
    <xsl:copy copy-namespaces="no">
      <xsl:apply-templates select="@*, node()"/>
    </xsl:copy>
  </xsl:template>
  
  <!-- Suppress attributes that should never be in instances -->
  <xsl:template match="@class | @domains | @ditaarch:*">
    <!-- Suppress -->
  </xsl:template>
  
</xsl:stylesheet>