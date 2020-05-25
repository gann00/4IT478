package cz.churchcrm.testframework;

import org.junit.Assert;

public class GridRow {

    public void shouldContain(String record) {


        throw new RuntimeException("record with " + record + "not found");
    }
}
