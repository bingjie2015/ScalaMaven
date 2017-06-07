import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hadoop on 17/06/01.
 */
public class Find {
    public static void main(String[] args){
        List<Double> result =readfile("/home/hadoop/Desktop/2016-01-17-pulse.txt");
        List<Double> result1 =readfile("/home/hadoop/Desktop/2016-01-18-pulse.txt");
        System.out.println(result.size());
        System.out.println("Max:"+Collections.max(result));
        System.out.println("MIn:"+Collections.min(result));
        System.out.println("Max:"+Collections.max(result1));
        System.out.println("MIn:"+Collections.min(result1));
    }
    public static List<Double> readfile(String path){
        List<Double> result = new ArrayList<Double>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            //StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                result.add(Double.valueOf(line));
                line = br.readLine();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;

    }
}
