
import java.util.List;
import lightdistributer.domain.Street;
import lightdistributer.logic.Distributer;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DistributerTest {
	private Street street = new Street(50);
	private Distributer distributer = new Distributer(street, 0);	
	
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
		street.addStraightSection(100, true);
		street.addOutsideCurve(280, true, 500);
		street.addInsideCurve(340, true, 100);
		street.addStraightSection(400, true);
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
		street.addOutsideCurve(650, true, 350);
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
	
}
