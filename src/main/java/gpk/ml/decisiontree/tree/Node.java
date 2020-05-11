package gpk.ml.decisiontree.tree;

import gpk.ml.decisiontree.dataload.dataset.Dataset;
import gpk.ml.decisiontree.dataload.dataset.Sample;
import gpk.ml.decisiontree.dataload.dataset.feature.AbstractFeature;
import gpk.ml.decisiontree.dataload.dataset.feature.NumericFeature;
import lombok.Data;
import lombok.NonNull;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Objects.isNull;

@Data
public class Node {
    private Node parent = null;
    private Node[] childNodes = new Node[2];
    @NonNull
    private Dataset data;
    private AbstractFeature feature;
    private Object value;
    private float bestSplitGiniIndex = 1;
    private int childNodesToSplitIndex = 1;
    private boolean isLeaf = false;
    private String label;

    private final float GINI_THRESHOLD = 0.0F;

    public void setChildNodes(Node[] nodes) {
        for (Node node : nodes) {
            node.setParent(this);
        }
        this.childNodes = nodes;
    }

    public int resolveChildIndex(Object value) {
        if (!(feature instanceof NumericFeature)) {
            throw new UnsupportedOperationException();
        }
        if ((float)value < (float)this.value) {
            return 0;
        } else {
            return 1;
        }
    }

    public Node getChildNodeToSplit() {
        if (childNodesToSplitIndex < 0 || isLeaf) {
            return null;
        }
        return childNodes[childNodesToSplitIndex--];
    }

    public boolean split() {
        float bestSplitGiniIndex = 1;
        AbstractFeature bestSplitFeature = null;
        Object bestSplitValue = null;

        // for each feature
        for (int featurePos = 0; featurePos < data.getFeatures().size(); featurePos++) {
            AbstractFeature feature = data.getFeatures().get(featurePos);

            if (! (feature instanceof NumericFeature)) {
                throw new UnsupportedOperationException();
            } else {
                // NumericFeature
                System.out.println(feature.getName());
                System.out.println(featurePos);
                // for each sample - calculate gini for each numeric value as a bound
                for (Sample sample : data.getSamples()) {
                    float bound = (float)(sample.get(featurePos));
                    SplitedData splitedData1 = new SplitedData(data.getLabelValues());
                    SplitedData splitedData2 = new SplitedData(data.getLabelValues());

                    for (Sample smpl : data.getSamples()) {
                        if ((float) smpl.get(featurePos) <= bound) {
                            splitedData1.data.addSample(smpl);
                            splitedData1.labelToCount.get(smpl.getLabelValue()).incrementAndGet();
                        } else {
                            splitedData2.data.addSample(smpl);
                            splitedData2.labelToCount.get(smpl.getLabelValue()).incrementAndGet();
                        }
                    }

                    if ( splitedData1.data.getSamples().size() == 0 ||
                            splitedData2.data.getSamples().size() == 0 ) {
                        continue;
                    }

                    float totalGiniIndex =
                            ((float)(splitedData1.data.getSamples().size()))/data.getSamples().size()* splitedData1.calcGiniIndex()
                                    + ((float)(splitedData2.data.getSamples().size()))/data.getSamples().size()* splitedData2.calcGiniIndex();

                    System.out.println("total gini index : " + totalGiniIndex);

                    if (totalGiniIndex < bestSplitGiniIndex) {
                        bestSplitGiniIndex = totalGiniIndex;
                        bestSplitFeature = feature;
                        bestSplitValue = bound;
                    }
                }
            }
        }

        System.out.println("best split gini index : " + bestSplitGiniIndex);
        System.out.println("best split feature : " + bestSplitFeature);
        System.out.println("best split value: " + bestSplitValue);

        if (!isNull(parent) && (bestSplitGiniIndex >= parent.getBestSplitGiniIndex()
                                || bestSplitGiniIndex < GINI_THRESHOLD)) {
            // don't split
            isLeaf = true;
            return false;
        }

        System.out.println("hello");

        this.setBestSplitGiniIndex(bestSplitGiniIndex);
        this.setFeature(bestSplitFeature);
        this.setValue(bestSplitValue);
        this.setChildNodes(new Node[] {
                new Node(new Dataset(data.getFeatures(), data.getLabel(), data.getLabelValues())),
                new Node(new Dataset(data.getFeatures(), data.getLabel(), data.getLabelValues()))
        });

        data.getSamples().forEach(sample -> {
            Object value = sample.get(feature.getPos());
            childNodes[resolveChildIndex(value)].getData().addSample(sample);
        });
        for (Node childNode : childNodes) {
            childNode.calcMostPopularLabel();
        }

        data = null;

        System.out.println("splited");

        return true;
    }

    private void calcMostPopularLabel() {
        Map<String, Long> labelToCount = new HashMap<>();
        data.getLabelValues().forEach(labelValue -> {
            labelToCount.put(labelValue, 0L);
        });
        data.getSamples().forEach(sample -> {
            labelToCount.computeIfPresent(sample.getLabelValue(), (k, v) -> v + 1);
        });
        label = maxCountKey(labelToCount);
    }

    public <K, V extends Comparable<V>> K maxCountKey(Map<K, V> map) {
        Entry<K, V> maxEntry = Collections.max(map.entrySet(), Entry.comparingByValue());
        return maxEntry.getKey();
    }

    private static class SplitedData {
        Map<String, AtomicLong> labelToCount = new HashMap<>();
        Dataset data = new Dataset();
        float giniIndex = 1;

        public SplitedData(List<String> labels) {
            labels.forEach(label -> labelToCount.put(label, new AtomicLong(0L)));
        }

        public float calcGiniIndex() {
            for (AtomicLong count : labelToCount.values()) {
                // System.out.println("count.get() : " + count.get());
                // System.out.println("data.getSamples().size() : " + data.getSamples().size());
                giniIndex -= Math.pow((((float)count.get())/data.getSamples().size()), 2);
            }
            return giniIndex;
        }


    }
}
