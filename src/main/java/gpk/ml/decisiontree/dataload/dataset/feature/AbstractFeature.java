package gpk.ml.decisiontree.dataload.dataset.feature;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public abstract class AbstractFeature {
    @Getter
    final private String name;
    @Getter
    final private int sourcePos;  // position in source file sample
    @Getter
    @Setter
    private int pos;  // position in loaded Dataset Sample

    @Override
    public String toString() {
        return "(" + sourcePos + ") " + name + " : " + this.getClass().getName();
    }

}
