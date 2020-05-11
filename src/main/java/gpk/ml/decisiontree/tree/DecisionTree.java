package gpk.ml.decisiontree.tree;

import gpk.ml.decisiontree.dataload.dataset.Dataset;
import gpk.ml.decisiontree.dataload.dataset.Sample;

public class DecisionTree {
    private Node root;

    public void learn(Dataset data) {
        root = new Node(data);
        Node currentNode = root;
        currentNode.split();

        while(currentNode != null) {
            Node splitCandidate = currentNode.getChildNodeToSplit();
            if (splitCandidate == null) {
                // go back
                currentNode = currentNode.getParent();
            } else {
                currentNode = splitCandidate;
                boolean split = currentNode.split();
                if (!split) {
                    // go back
                    currentNode = currentNode.getParent();
                }
            }

        }
    }

    public String classify(Sample sample) {
        Node node = root;
        while (!node.isLeaf()) {
            int childIndex = node.resolveChildIndex(sample.get(node.getFeature().getPos()));
            node = node.getChildNodes()[childIndex];
        }
        return node.getLabel();
    }

}
