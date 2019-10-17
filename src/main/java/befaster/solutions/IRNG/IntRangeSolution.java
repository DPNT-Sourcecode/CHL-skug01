package befaster.solutions.IRNG;

import befaster.runner.SolutionNotImplementedException;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class IntRangeSolution {

    public List<Integer> generate(int start_incl, int end_excl) {
        List <Integer> list = new ArrayList<>();
        for ( int i = start_incl; i < end_excl; i++) {
        	list.add(Integer.valueOf(i));
        }
        return list;
    }

}
