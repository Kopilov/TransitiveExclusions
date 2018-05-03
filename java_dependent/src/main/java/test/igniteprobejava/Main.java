package test.igniteprobejava;

import org.apache.ignite.examples.sql.SqlDdlExample;

public class Main {
    public static void main(String[] args) {
        try {
            new SqlDdlExample().main(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
