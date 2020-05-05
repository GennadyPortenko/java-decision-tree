package gpk.ml.decisiontree.dataload.dataset;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Sample extends ArrayList<Object> {
    @Getter
    @Setter
    private String labelValue;

    @Override
    public String toString() {
        return super.toString() + " -> " + labelValue;
    }
}
