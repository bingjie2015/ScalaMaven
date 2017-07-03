import java.util.ArrayList;
import java.util.List;

/**
 * Created by hadoop on 17/06/24.
 */
public class Solution {
    public static void  main(String[] args){
        int[] test = {1,2,3};
        List<List<Integer>> aa =permute(test);
        for (List<Integer> a :aa){
            a.forEach(System.out::print);
            List<Integer> b = new ArrayList<>(a);
            System.out.println(b.toString());
            System.out.println();
        }
    }
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> results =  new ArrayList<>();
        List<Integer> part =  new ArrayList<>();

        find(results,part,nums);
        return results;
    }
    public static  void find( List<List<Integer>> list,List<Integer> part, int[] nums){
        if(part.size() == nums.length){
            list.add(new ArrayList<>(part));
        }else{
            for (int i = 0; i < nums.length; i++) {
                if(part.contains(nums[i])) continue;
                part.add(nums[i]);
                find(list,part,nums);
                part.remove(part.size()-1);
            }
        }

    }
//    public static List<List<Integer>> permute(int[] nums) {
//        List<List<Integer>> results =  new ArrayList<>();
//        List<Integer> part =  new ArrayList<>();
//        for (int i = 0; i < nums.length; i++) {
//            part.add(i);
//            for (int j = 0; j <nums.length ; j++) {
//
//                if(i!=j){
//
//                    for (int k = 0; k <nums.length ; k++) {
//                        part.add(nums[i]);
//
//                    }
//                }
//            }
//        }
//    }
}
