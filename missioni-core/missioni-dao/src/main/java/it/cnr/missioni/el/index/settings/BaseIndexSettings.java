package it.cnr.missioni.el.index.settings;

import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;

import net.jcip.annotations.Immutable;

/**
 * @author Salvia Vito
 */
@Immutable
public class BaseIndexSettings implements GPIndexCreator.GPIndexSettings {

    private final String indexName;
    private final String indexType;

    public BaseIndexSettings(String theIndexName, String theIndexType) {
        this.indexName = theIndexName;
        this.indexType = theIndexType;
    }

    @Override
    public String getIndexName() {
        return this.indexName;
    }

    @Override
    public String getIndexType() {
        return this.indexType;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {"
                + "indexName = " + indexName
                + ", indexType = " + indexType + '}';
    }
}
