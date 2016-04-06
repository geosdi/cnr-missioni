package it.cnr.missioni.el.index.settings;

import net.jcip.annotations.Immutable;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;

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
