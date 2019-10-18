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
    public void testAAAAA() {
        assertEquals(checkliteSolution.checklite("AAAAA"),Integer.valueOf(200));
        System.out.println(checkliteSolution.checklite("AAAAA"));
    }
    @Test
    public void testAAAAAA() {
        assertEquals(checkliteSolution.checklite("AAAAAA"),Integer.valueOf(200+50));
        System.out.println(checkliteSolution.checklite("AAAAAA"));
    }
    public void testAAAAAAAA() {
        assertEquals(checkliteSolution.checklite("AAAAAAAA"),Integer.valueOf(200+130));
        System.out.println(checkliteSolution.checklite("AAAAAAAA"));
    }
    @Test
    public void test_() {
        assertEquals(checkliteSolution.checklite("_"),Integer.valueOf(-1));
    }
    @Test
    public void testABEE() {
    	System.out.println(checkliteSolution.checklite("ABEE"));
        assertEquals(checkliteSolution.checklite("ABEE"),Integer.valueOf(50+0+40+40));
    }
    @Test
    public void testABAAABAEE() {
    	System.out.println(checkliteSolution.checklite("ABAAABAEE"));
        assertEquals(checkliteSolution.checklite("ABAAABAEE"),Integer.valueOf(200+0+30+40+40));
    }
    @Test
    public void testFF() {
    	System.out.println(checkliteSolution.checklite("FF"));
        assertEquals(checkliteSolution.checklite("FF"),Integer.valueOf(10+10));
    }
    @Test
    public void testFFF() {
    	System.out.println(checkliteSolution.checklite("FFF"));
        assertEquals(checkliteSolution.checklite("FFF"),Integer.valueOf(10+10+0));
    }
    @Test
    public void testRRRQQQ() {
    	System.out.println(checkliteSolution.checklite("RRRQQQ"));
        assertEquals(checkliteSolution.checklite("RRRQQQ"),Integer.valueOf(50+50+50+30+30));
    }
}

