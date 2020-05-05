package gpk.ml.decisiontree.tree;

import gpk.ml.decisiontree.dataload.dataset.Dataset;
import gpk.ml.decisiontree.dataload.dataset.feature.AbstractFeature;
import gpk.ml.decisiontree.dataload.dataset.feature.BooleanFeature;

public class DecisionTree {

    private Node root = new Node();

    public void learn(Dataset data) {
        split(root, data);
    }

    static private void split(Node node, Dataset data) {
        float bestSplitGini = 1;
        AbstractFeature bestSplitFeature = null;
        Object bestSplitValue = null;

        data.getFeatures().forEach(feature -> {
            if (feature instanceof BooleanFeature) {
                // float giniIndex = 1 -
            }
        });

    }

}
