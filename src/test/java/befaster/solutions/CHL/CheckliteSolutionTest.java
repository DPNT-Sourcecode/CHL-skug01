package befaster.solutions.CHL;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CheckliteSolutionTest {
    private  CheckliteSolution checkliteSolution;

    @Before
    public void setUp() {

        checkliteSolution = new CheckliteSolution();
    }

    @Test
    public void testA() {
        assertEquals(checkliteSolution.checklite("A"),Integer.valueOf(50));
    }
    @Test
    public void testB() {
        assertEquals(checkliteSolution.checklite("B"),Integer.valueOf(30));
    }
    @Test
    public void testBB() {
        assertEquals(checkliteSolution.checklite("BB"),Integer.valueOf(45));
    }
    public void testBBB() {
        assertEquals(checkliteSolution.checklite("BBB"),Integer.valueOf(75));
    }
    @Test
    public void testC() {
        assertEquals(checkliteSolution.checklite("C"),Integer.valueOf(20));
    }
    @Test
    public void testD() {
        assertEquals(checkliteSolution.checklite("D"),Integer.valueOf(15));
    }
    @Test
    public void testABABCDA() {
        assertEquals(checkliteSolution.checklite("ABABCD"),Integer.valueOf(50+50+45+20+15));
        //System.out.println(checkliteSolution.checklite("ABABCD"));
    }
    @Test
    public void test_() {
        assertEquals(checkliteSolution.checklite("_"),Integer.valueOf(-1));
    }
}




