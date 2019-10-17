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
}

