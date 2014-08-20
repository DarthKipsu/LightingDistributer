
import java.util.List;
import lightdistributer.domain.Road;
import lightdistributer.logic.Distributer;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DistributerTest {
	private Road defaultRoad = new Road(50);
	private Distributer distributer = new Distributer(defaultRoad, 0);	
	
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
		defaultRoad.addStraightSection(100, true);
		defaultRoad.addOutsideCurve(280, true, 500);
		defaultRoad.addInsideCurve(340, true, 100);
		defaultRoad.addStraightSection(400, true);
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void countsNeededColumns() {
		int columnCount = distributer.getNeededColumns();
		int expected = 10;

		assertEquals(expected, columnCount);
	}
	
	@Test
	public void countsNeededColumns2() {
		defaultRoad.addOutsideCurve(650, true, 350);
		int columnCount = distributer.getNeededColumns();
		int expected = 16;

		assertEquals(expected, columnCount);
	}

	@Test
	public void createsAsManyStakesAsNeededColumns() {
		int columnCount = distributer.getNeededColumns();
		List<Integer> stakes = distributer.getStakes();
		System.out.println("stakes (1): " + stakes);

		assertEquals(columnCount, stakes.size());
	}

	@Test
	public void leaveIntervalEmptyIfNoColumnsAreNeeded() {
		Road road = new Road(50);
		road.addStraightSection(70, true);
		road.addStraightSection(75, true);
		road.addStraightSection(95, true);
		Distributer distributer2 = new Distributer(road, 0);

		int neededColumns = distributer2.getNeededColumns();
		int distributedStakeCount = distributer2.getStakes().size();
		System.out.println("stakes (2): " + distributer2.getStakes());

		assertEquals(neededColumns, distributedStakeCount);
	}
	
	@Test
	public void dontPlaceColumnsOnRestrictedAreas() {
		Road road = new Road(50);
		road.addStraightSection(44, true);
		road.addStraightSection(48, false);
		road.addStraightSection(90, true);
		Distributer distributer2 = new Distributer(road, 0);

		int secondStake = distributer2.getStakes().get(1);
		boolean stakeNotOnRestrictedGeometry = secondStake <= 44 || secondStake > 48;
		System.out.println("stakes (3): " + distributer2.getStakes());

		assertTrue(stakeNotOnRestrictedGeometry);
		assertEquals(3, distributer2.getStakes().size());
	}
	
}
