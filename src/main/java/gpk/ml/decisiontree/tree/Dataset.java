package gpk.ml.decisiontree.tree;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Dataset {
    private List<Sample> samples = new ArrayList<>();

    @Setter
    private List<Feature> features;

    public void addSample(Sample sample) {
        samples.add(sample);
    }

    public void print() {
        samples.forEach(System.out::println);
    }
}
