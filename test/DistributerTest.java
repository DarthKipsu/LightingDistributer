
import java.util.List;
import lightdistributer.domain.Road;
import lightdistributer.logic.Distributer;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DistributerTest {
	private Road road = new Road(50);
	private Distributer distributer = new Distributer(road, 0);	
	
	public DistributerTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
		road.addStraightSection(100, true);
		road.addOutsideCurve(280, true, 500);
		road.addInsideCurve(340, true, 100);
		road.addStraightSection(400, true);
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void countsNeededColumns() {
		int columnCount = distributer.countNeededColumns();
		int expected = 10;

		assertEquals(expected, columnCount);
	}
	
	@Test
	public void countsNeededColumns2() {
		road.addOutsideCurve(650, true, 350);
		int columnCount = distributer.countNeededColumns();
		int expected = 16;

		assertEquals(expected, columnCount);
	}

	@Test
	public void createsAsManyStakesAsNeededColumns() {
		int columnCount = distributer.countNeededColumns();
		List<Integer> stakes = distributer.getStakes();
		System.out.println("stakes: " + stakes);

		assertEquals(columnCount, stakes.size());
	}

	@Test
	public void leaveIntervalEmptyIfNoColumnsAreNeeded() {
		Road road2 = new Road(50);
		road2.addStraightSection(70, true);
		road2.addStraightSection(75, true);
		road2.addStraightSection(95, true);
		Distributer distributer2 = new Distributer(road2, 0);

		int neededColumns = distributer2.countNeededColumns();
		int distributedStakeCount = distributer2.getStakes().size();
		System.out.println("stakes: " + distributer2.getStakes());

		assertEquals(neededColumns, distributedStakeCount);
	}
	
}
