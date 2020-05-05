package gpk.ml.decisiontree.dataload.dataset.feature;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractFeature {
    @Getter
    final private String name;
    @Getter
    final private int pos;  // position in sample

    @Override
    public String toString() {
        return "(" + pos + ") " + name + " : " + this.getClass().getName();
    }

}
