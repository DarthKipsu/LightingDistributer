
import lightdistributer.domain.InsideCurve;
import lightdistributer.domain.OutsideCurve;
import lightdistributer.domain.StakeInterval;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CurveTest {
	
	public CurveTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void OutsideCurveWithRadiusUnder100() {
		StakeInterval interval1 = new OutsideCurve(0, 500, true, 50, 100);
		StakeInterval interval2 = new OutsideCurve(0, 500, true, 50, 50);
		int sMax1 = interval1.getSmax();
		int sMax2 = interval2.getSmax();
		int expectedSmax = 40;

		assertEquals(expectedSmax, sMax1);
		assertEquals(expectedSmax, sMax2);
	}

	@Test
	public void OutsideCurveWithRadiusOver800() {
		StakeInterval interval1 = new OutsideCurve(0, 500, true, 50, 800);
		StakeInterval interval2 = new OutsideCurve(0, 500, true, 50, 1200);
		int sMax1 = interval1.getSmax();
		int sMax2 = interval2.getSmax();
		int expectedSmax = 50;

		assertEquals(expectedSmax, sMax1);
		assertEquals(expectedSmax, sMax2);
	}

	@Test
	public void OutsideCurveWithRadius200() {
		StakeInterval interval = new OutsideCurve(0, 500, true, 50, 200);
		int sMax = interval.getSmax();
		int expectedSmax = 43;

		assertEquals(expectedSmax, sMax);
	}

	@Test
	public void OutsideCurveWithRadius400() {
		StakeInterval interval = new OutsideCurve(0, 500, true, 50, 400);
		int sMax = interval.getSmax();
		int expectedSmax = 46;

		assertEquals(expectedSmax, sMax);
	}

	@Test
	public void OutsideCurveWithRadius600() {
		StakeInterval interval = new OutsideCurve(0, 500, true, 50, 600);
		int sMax = interval.getSmax();
		int expectedSmax = 48;

		assertEquals(expectedSmax, sMax);
	}

	@Test
	public void InsideCurveWithRadiusUnder100() {
		StakeInterval interval1 = new InsideCurve(0, 500, true, 50, 100);
		StakeInterval interval2 = new InsideCurve(0, 500, true, 50, 50);
		int sMax1 = interval1.getSmax();
		int sMax2 = interval2.getSmax();
		int expectedSmax = 32;

		assertEquals(expectedSmax, sMax1);
		assertEquals(expectedSmax, sMax2);
	}

	@Test
	public void InsideCurveWithRadiusOver800() {
		StakeInterval interval1 = new InsideCurve(0, 500, true, 50, 800);
		StakeInterval interval2 = new InsideCurve(0, 500, true, 50, 1200);
		int sMax1 = interval1.getSmax();
		int sMax2 = interval2.getSmax();
		int expectedSmax = 50;

		assertEquals(expectedSmax, sMax1);
		assertEquals(expectedSmax, sMax2);
	}
	
	@Test
	public void InsideCurveWithRadius200() {
		StakeInterval interval = new InsideCurve(0, 500, true, 50, 200);
		int sMax = interval.getSmax();
		int expectedSmax = 37;

		assertEquals(expectedSmax, sMax);
	}

	@Test
	public void InsideCurveWithRadius400() {
		StakeInterval interval = new InsideCurve(0, 500, true, 50, 400);
		int sMax = interval.getSmax();
		int expectedSmax = 43;

		assertEquals(expectedSmax, sMax);
	}

	@Test
	public void InsideCurveWithRadius600() {
		StakeInterval interval = new InsideCurve(0, 500, true, 50, 600);
		int sMax = interval.getSmax();
		int expectedSmax = 47;

		assertEquals(expectedSmax, sMax);
	}
	
}
