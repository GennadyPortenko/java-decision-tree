package gpk.ml.decisiontree.tree;

import gpk.ml.decisiontree.dataload.dataset.feature.AbstractFeature;
import lombok.Data;

@Data
public class Node {
    private Node[] childNodes = new Node[2];

    public void setChildNodes(Node nodes) {
        for (int i = 0; i < this.childNodes.length; i++);

    }

    AbstractFeature feature;
    Object value;
}
