
package usecases;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ClientService;
import utilities.AbstractTest;
import domain.Client;
import domain.Finder;
import domain.KindOfRoom;
import domain.Room;
import domain.Terrain;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class useCaseFinderTest extends AbstractTest {

	@Autowired
	private ClientService	clientService;


	//Servicios

	//REALIZAR UNA BUSQUEDA
	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				//Un cliente hace una búsqueda correcta.
				"client1", "Pumtumbria", Terrain.BEACH, KindOfRoom.TRIPLE, 3, 200.0, 400.0, 60, null
			}, {
				//Un admin intenta hacer una búsqueda, y no sería posible.
				"admin", "Pumtumbria", Terrain.BEACH, KindOfRoom.TRIPLE, 3, 200.0, 400.0, 60, IllegalArgumentException.class
			}, {
				//Un manager intenta hacer una búsqueda, y no sería posible.
				"manager1", "Pumtumbria", Terrain.BEACH, KindOfRoom.TRIPLE, 3, 200.0, 400.0, 60, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (String) testingData[i][1], (Terrain) testingData[i][2], (KindOfRoom) testingData[i][3], (int) testingData[i][4], (Double) testingData[i][5], (Double) testingData[i][6], (int) testingData[i][7],
				(Class<?>) testingData[i][8]);
	}

	protected void template(final String username, final String hotelName, final Terrain terrain, final KindOfRoom kindOfRoom, final int capacity, final Double minimumPrice, final Double maximumPrice, final int valorEsperado, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Client c = this.clientService.findByPrincipal();
			Finder finder;
			if (c.getFinder() == null)
				finder = this.clientService.createFinder(c);
			else
				finder = c.getFinder();

			finder.setHotelName(hotelName);
			finder.setTerrain(terrain);
			finder.setKindOfRoom(kindOfRoom);
			finder.setCapacity(capacity);
			finder.setMinimumPrice(minimumPrice);
			finder.setMaximumPrice(maximumPrice);
			this.clientService.saveFinder(finder);
			final Collection<Room> res = this.clientService.finderRoomsResults();
			Assert.isTrue(res.size() == valorEsperado);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
