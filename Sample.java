package ATM_PROJECT;

import java.util.*;
import java.io.*;

public class Sample {
    public static void main(String args[]) throws Exception {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\project\\outputST.csv"));
            writer.flush();
            //writer.write(" \n " + "Date,Status,Amount" + 123 + 988);
            //writer.write("\n"+rs.getDate(2)+","+rs.getString(3)+","+rs.getLong(4));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        

    }

}
